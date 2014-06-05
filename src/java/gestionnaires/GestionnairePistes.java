package gestionnaires;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;
import modeles.Instrument;
import modeles.Piste;

@Stateless
public class GestionnairePistes {

    @PersistenceContext
    EntityManager em;

    public void setPiste(String nom, Artiste a, Instrument i) {
        Piste p = new Piste(nom);

        p.setArtiste(a);
        p.setInstrument(i);

        em.persist(p);
    }

    public Piste getPiste(String nom, Artiste a) {
        Query q = em.createQuery("select p from Piste p where lower(p.nom) = :nom and p.artiste = :a");
        q.setParameter("nom", nom.toLowerCase());
        q.setParameter("a", a);
        
        return (Piste) q.getSingleResult();
    }
}
