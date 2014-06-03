package gestionnaires;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;

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
        Query q = em.createQuery("select a from Artiste a where a.nom = :nom");
        q.setParameter("nom", nom);

        return (Artiste) q.getSingleResult();

    }

    private boolean exists(String nom) {
        Query q = em.createQuery("select a from Artiste a where a.nom = :nom");
        q.setParameter("nom", nom);

        return q.getResultList().size() > 0;
    }
}
