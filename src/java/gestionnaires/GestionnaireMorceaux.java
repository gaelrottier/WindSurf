package gestionnaires;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;
import modeles.Genre;
import modeles.Instrument;
import modeles.Morceau;

@Stateless
public class GestionnaireMorceaux {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GestionnaireGenres gestionnaireGenres;

    @EJB
    private GestionnaireArtistes gestionnaireArtistes;

    public Morceau creerMorceau(String titre, int nbPistes, int annee, String url) {
        Morceau m = new Morceau(titre, nbPistes, annee, url);

        em.persist(m);

        return m;
    }

    public void setArtiste(Morceau m, Artiste a) {;
        m.setArtiste(a);

        em.merge(m);

        gestionnaireArtistes.addMorceau(a, m);
    }

    public Collection<Morceau> searchMorceau(String search) {
        Query q = em.createQuery("select m from Morceau m where lower(m.titre) LIKE :titre");
        q.setParameter("titre", "%" + search.toLowerCase() + "%");

        return (Collection<Morceau>) q.getResultList();
    }

    public void addInstrument(Morceau m, Instrument i) {
        Collection<Instrument> ins = m.getInstruments();

        if (!ins.contains(i)) {
            m = (Morceau) em.find(Morceau.class, m.getId());

            ins.add(i);
            m.setInstruments(ins);

            em.merge(m);
        }
    }

    public void setNbPistes(Morceau m, int nb) {
        m.setNbPistes(nb);

        em.merge(m);
    }

    public void setGenre(Morceau m, Genre g) {
        m.setGenre(g);

        em.merge(m);

        gestionnaireGenres.addMorceau(g, m);
    }

    public Morceau getMorceauById(int id) {
        return em.find(Morceau.class, id);
    }

    public Morceau getMorceau(String titre) {
        Query q = em.createQuery("select m from Morceau m where lower(m.titre) = :titre");
        q.setParameter("titre", titre.toLowerCase());

        return (Morceau) q.getSingleResult();
    }
}
