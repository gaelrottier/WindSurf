package gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;
import modeles.Morceau;

/**
 *
 * @author Goys
 */
@Stateless
public class GestionnaireArtistes {

    /**
     *
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Crée un artiste
     *
     * @param nom Son nom
     * @param resume Son résumé
     * @param photo Sa photo
     * @return L'artiste créé
     */
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

    /**
     * Renvoie l'artiste dont le nom correspond au <nom>
     *
     * @param nom Le nom de l'artiste
     * @return L'artiste récupéré
     */
    public Artiste getArtiste(String nom) {
        Query q = em.createQuery("select a from Artiste a where lower(a.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return (Artiste) q.getSingleResult();

    }

    /**
     * Renvoie l'artiste dont l'id correspond à l'<id>
     *
     * @param id L'id de l'artiste
     * @return L'artiste récupéré
     */
    public Artiste getArtisteById(int id) {
        return em.find(Artiste.class, id);
    }

    /**
     * S'il n'existe pas déjà dedans, ajoute un morceau à la liste des morceaux
     * de l'artiste
     *
     * @param a L'artiste auquel on veut ajouter un morceau
     * @param m Le morceau à ajouter
     */
    public void addMorceau(Artiste a, Morceau m) {
        Collection<Morceau> cm = a.getMorceaux();
        if (!cm.contains(m)) {
            cm.add(m);
            a.setMorceaux(cm);

            em.merge(a);
        }
    }

    /**
     * Renvoie les artistes dont le nom contient <search>
     *
     * @param search La chaîne de caractères à rechercher
     * @return La liste des artistes correspondant à la recherche
     */
    public Collection<Artiste> searchArtiste(String search) {
        Query q = em.createQuery("select a from Artiste a where lower(a.nom) LIKE :nom");
        q.setParameter("nom", "%" + search.toLowerCase() + "%");

        return (Collection<Artiste>) q.getResultList();
    }

    /**
     * Vérifie si l'artiste existe dans la bdd
     *
     * @param nom Le nom de l'artiste
     * @return True s'il existe, False s'il n'existe pas
     */
    private boolean exists(String nom) {
        Query q = em.createQuery("select a from Artiste a where lower(a.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
