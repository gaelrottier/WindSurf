package gestionnaires;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;
import modeles.Morceau;

@Stateless
public class GestionnaireArtistes {

    @PersistenceContext
    private EntityManager em;

    public Artiste creerArtiste(String nom, String resume, String photo) {
        Artiste a;

        if (exists(nom)) {
            a = getArtiste(nom);
        } else {
            a = new Artiste(nom, resume, photo);

            em.persist(a);
        }

        return a;
    }

    public Artiste getArtiste(String nom) {
        Query q = em.createQuery("select a from Artiste a where lower(a.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return (Artiste) q.getSingleResult();

    }

    public Artiste getArtisteById(int id) {
        return em.find(Artiste.class, id);
    }

    public Artiste getArtisteByIdPaginated(int id, int page) {
        Artiste a = em.find(Artiste.class, id);
        int fromIndex = page * 10 - 10;
        int toIndex = page * 10;

        if (page * 10 > a.getMorceaux().size()) {
            toIndex = page * 10 - a.getMorceaux().size();
        }

        Collection<Morceau> morceaux = ((List) a.getMorceaux()).subList(fromIndex, toIndex);

        a.setMorceaux(morceaux);

        return a;
    }

    public void addMorceau(Artiste a, Morceau m) {
        Collection<Morceau> cm = a.getMorceaux();
        if (!cm.contains(m)) {
            cm.add(m);
            a.setMorceaux(cm);

            em.merge(a);
        }
    }

    public Collection<Artiste> searchArtiste(String search) {
        Query q = em.createQuery("select a from Artiste a where lower(a.nom) LIKE :nom");
        q.setParameter("nom", "%" + search.toLowerCase() + "%");

        return (Collection<Artiste>) q.getResultList();
    }

    private boolean exists(String nom) {
        Query q = em.createQuery("select a from Artiste a where lower(a.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
