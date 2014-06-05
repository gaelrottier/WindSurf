package gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Instrument;
import modeles.Morceau;

@Stateless
public class GestionnaireInstruments {

    @PersistenceContext
    EntityManager em;

    public Instrument creerInstrument(String nom, int difficulte) {
        Instrument i;

        if (exists(nom)) {
            i = getInstrument(nom);
        } else {
            i = new Instrument(nom.toLowerCase(), difficulte);
            em.persist(i);
        }

        return i;
    }

    public void addMorceau(Instrument i, Morceau m) {
        Collection<Morceau> mc = i.getMorceaux();

        if (!mc.contains(m)) {
            mc.add(m);

            i.setMorceaux(mc);

            em.merge(i);
        }
    }

    public Collection<Morceau> getMorceaux(Instrument i) {
        return i.getMorceaux();
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

    public Instrument getInstrumentById(int id) {
        return em.find(Instrument.class, id);
    }

    private boolean exists(String nom) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
