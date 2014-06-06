package gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Abonnement;

/**
 *
 * @author Goys
 */
@Stateless
public class GestionnaireAbonnements {

    /**
     *
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Crée les abonnements
     */
    public void creerAbonnements() {
        creerAbonnement(0.0, "Gratuit", 0);
        creerAbonnement(3.0, "Week-end", 48);
        creerAbonnement(12.99, "1 mois", 720);
        creerAbonnement(60.0, "1 an", 8640);
        creerAbonnement(200.0, "A vie", 0);
    }

    /**
     * Crée un abonnement
     *
     * @param prix Le prix
     * @param nom Le nom
     * @param duree La durée (en h)
     * @return L'abonnement créé
     */
    public Abonnement creerAbonnement(double prix, String nom, int duree) {
        Abonnement a = new Abonnement(prix, nom, duree);
        em.persist(a);

        return a;
    }

    /**
     * Renvoie l'abonnement spécifié par le <nom>
     *
     * @param nom Le nom de l'abonnement
     * @return L'abonnement trouvé
     */
    public Abonnement getAboByName(String nom) {

        Query q = em.createQuery("select a from Abonnement a where a.nom = :nom");
        q.setParameter("nom", nom);

        return (Abonnement) q.getSingleResult();
    }

    /**
     * Renvoie l'abonnement recherché par son <id>
     *
     * @param id L'id de l'abonnement
     * @return L'abonnement trouvé
     */
    public Abonnement getAboById(int id) {
        return em.find(Abonnement.class, id);
    }

    /**
     * Renvoie une liste d'abonnements comprenant tous les abonnements, classés
     * par ordre d'id
     *
     * @return La liste des abonnements
     */
    public Collection<Abonnement> getAllAbos() {
        Query q = em.createQuery("select a from Abonnement a ORDER BY a.id");

        return q.getResultList();
    }
}
