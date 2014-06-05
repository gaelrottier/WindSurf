package gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Instrument;

@Stateless
public class GestionnaireInstruments {

    @PersistenceContext
    EntityManager em;

    public Instrument setInstrument(String nom, int difficulte) {
        Instrument i;

        if (exists(nom)) {
            i = getInstrument(nom);
        } else {
            i = new Instrument(nom.toLowerCase(), difficulte);
            em.persist(i);
        }

        return i;
    }

    public Collection<Instrument> searchInstrument(String search) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) LIKE :nom");
        q.setParameter("nom", "%" + search.toLowerCase() + "%");

        return (Collection<Instrument>) q.getResultList();
    }

    public Instrument getInstrument(String nom) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return (Instrument) q.getSingleResult();

    }

    private boolean exists(String nom) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
