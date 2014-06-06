package gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Instrument;
import modeles.Morceau;

/**
 *
 * @author Goys
 */
@Stateless
public class GestionnaireInstruments {

    /**
     *
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Crée un instrument
     *
     * @param nom Le nom de l'instrument
     * @return L'instrument créé
     */
    public Instrument creerInstrument(String nom) {
        Instrument i;

        if (exists(nom)) {
            i = getInstrument(nom);
        } else {
            i = new Instrument(nom.toLowerCase());
            em.persist(i);
        }

        return i;
    }

    /**
     * Ajoute un morceau à l'instrument spécifié, puis le met à jour dans la bdd
     *
     * @param i L'instrument à mettre à jour
     * @param m Le morceau à ajouter
     */
    public void addMorceau(Instrument i, Morceau m) {
        Collection<Morceau> mc = i.getMorceaux();

        if (!mc.contains(m)) {
            mc.add(m);

            i.setMorceaux(mc);

            em.merge(i);
        }
    }

    /**
     * Renvoie la liste des morceaux possédant l'instrument i
     *
     * @param i L'instrument dont on veut les morceaux
     * @return La liste des morceaux récupérée
     */
    public Collection<Morceau> getMorceaux(Instrument i) {
        return i.getMorceaux();
    }

    /**
     * Renvoie la liste des instruments dont le nom contient <search>
     *
     * @param search La chaîne de caractères à comparer
     * @return La liste des instruments récupérée
     */
    public Collection<Instrument> searchInstrument(String search) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) LIKE :nom");
        q.setParameter("nom", "%" + search.toLowerCase() + "%");

        return (Collection<Instrument>) q.getResultList();
    }

    /**
     * Renvoie l'instrument correspondant au <nom>
     *
     * @param nom Le nom de l'instrument
     * @return L'instrument trouvé
     */
    public Instrument getInstrument(String nom) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return (Instrument) q.getSingleResult();

    }

    /**
     * Renvoie l'instrument correspondant à l'id
     *
     * @param id L'id de l'instrument
     * @return L'instrument récupéré
     */
    public Instrument getInstrumentById(int id) {
        return em.find(Instrument.class, id);
    }

    /**
     * Vérifie si un instrument existe dans la base de données
     *
     * @param nom Le nom de l'instrument
     * @return True s'il existe, False s'il n'existe pas
     */
    private boolean exists(String nom) {
        Query q = em.createQuery("select i from Instrument i where lower(i.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
