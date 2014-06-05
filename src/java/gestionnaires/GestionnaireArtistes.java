package gestionnaires;

import java.util.Collection;
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

    public void addMorceau(Artiste a, Morceau m) {
        Collection<Morceau> cm = a.getMorceaux();
        cm.add(m);
        a.setMorceaux(cm);

        em.merge(a);
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
