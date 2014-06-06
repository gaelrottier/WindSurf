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
import modeles.Piste;

/**
 *
 * @author Goys
 */
@Stateless
public class GestionnaireMorceaux {

    /**
     *
     */
    @PersistenceContext
    private EntityManager em;

    /**
     *
     */
    @EJB
    private GestionnaireGenres gestionnaireGenres;

    /**
     *
     */
    @EJB
    private GestionnaireArtistes gestionnaireArtistes;

    /**
     * Crée un morceau en base de données
     *
     * @param titre Le titre du morceau
     * @param nbPistes Le nombre de pistes du morceaux
     * @param annee L'année du morceau
     * @return Le morceau créé
     */
    public Morceau creerMorceau(String titre, int nbPistes, int annee) {
        Morceau m = new Morceau(titre, nbPistes, annee, "http://fr.wikipedia.org/wiki/" + titre);

        em.persist(m);

        return m;
    }

    /**
     * Ajoute une piste au morceau et le met à jour dans la bdd
     *
     * @param m Le morceau à mettre à jour
     * @param p La piste à ajouter
     */
    public void addPiste(Morceau m, Piste p) {
        Collection<Piste> pc = m.getPistes();

        if (!pc.contains(p)) {
            pc.add(p);
            m.setPistes(pc);

            em.merge(m);
        }
    }

    /**
     * Renvoie la liste des pistes d'un morceau
     *
     * @param m Le morceau dont on veut les pistes
     * @return La liste des pistes du morceau
     */
    public Collection<Piste> getPistes(Morceau m) {
        return m.getPistes();
    }

    /**
     * Attribue un artiste au morceau, et le met à jour dans la bdd
     *
     * @param m Le morceau à mettre à jour
     * @param a L'artiste à ajouter au morceau
     */
    public void setArtiste(Morceau m, Artiste a) {;
        m.setArtiste(a);

        em.merge(m);

        gestionnaireArtistes.addMorceau(a, m);
    }

    /**
     * Renvoie la liste des morceaux dont le titre contient <search>
     *
     * @param search Le titre à rechercher
     * @return La liste des morceaux correspondant
     */
    public Collection<Morceau> searchMorceau(String search) {
        Query q = em.createQuery("select m from Morceau m where lower(m.titre) LIKE :titre");
        q.setParameter("titre", "%" + search.toLowerCase() + "%");

        return (Collection<Morceau>) q.getResultList();
    }

    /**
     * Ajoute un instrument au morceau
     *
     * @param m Le morceau à mettre à jour
     * @param i L'instrument à ajouter
     */
    public void addInstrument(Morceau m, Instrument i) {
        Collection<Instrument> ins = m.getInstruments();

        if (!ins.contains(i)) {
            m = (Morceau) em.find(Morceau.class, m.getId());

            ins.add(i);
            m.setInstruments(ins);

            em.merge(m);
        }
    }

    /**
     * Attribue un nombre de pistes au morceau
     *
     * @param m Le morceau à mettre à jour
     * @param nb Le nombre de pistes à définir
     */
    public void setNbPistes(Morceau m, int nb) {
        m.setNbPistes(nb);

        em.merge(m);
    }

    /**
     * Attribue un genre au morceau
     *
     * @param m Le morceau à mettre à jour
     * @param g Le genre à attribuer
     */
    public void setGenre(Morceau m, Genre g) {
        m.setGenre(g);

        em.merge(m);

        gestionnaireGenres.addMorceau(g, m);
    }

    /**
     * Renvoie le morceau correspondant à l'id fourni
     *
     * @param id L'id du morceau
     * @return Le morceau correspondant à l'id
     */
    public Morceau getMorceauById(int id) {
        return em.find(Morceau.class, id);
    }

    /**
     * Renvoie la liste des morceaux de l'artiste spécifié, paginée selon le
     * paramètre <page>, et sur 10 résultats maximum
     *
     * @param a L'artiste des morceaux
     * @param page La page à récupérer
     * @return La liste des morceaux récupérée
     */
    public Collection<Morceau> getMorceauxByArtistePaginated(Artiste a, int page) {
        Query q = em.createQuery("select m from Morceau m where m.artiste = :a");
        q.setParameter("a", a);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    /**
     * Renvoie la liste des pistes du morceau spécifié, paginée selon le
     * paramètre <page>, et sur 10 résultats maximum
     *
     * @param m Le morceau possédant les pistes
     * @param page La page à récupérer
     * @return La liste des pistes récupérée
     */
    public Collection<Piste> getPistesByMorceauPaginated(Morceau m, int page) {
        Query q = em.createQuery("select m.pistes from Morceau m where m = :m");
        q.setParameter("m", m);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    /**
     * Renvoie la liste des morceaux du genre spécifié, paginée selon le
     * paramètre <page>, et sur 10 résultats maximum
     *
     * @param g Le genre des morceaux
     * @param page La page à récupérer
     * @return La liste des morceaux récupérée
     */
    public Collection<Morceau> getMorceauxByGenrePaginated(Genre g, int page) {
        Query q = em.createQuery("select m from Morceau m where m.genre = :g");
        q.setParameter("g", g);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    /**
     * Renvoie la liste des morceaux dont au moins une piste possède
     * l'instrument spécifié, paginée selon le paramètre <page>, et sur 10
     * résultats maximum
     *
     * @param i L'instrument des pistes
     * @param page La page à récupérer
     * @return La liste des morceaux récupérée
     */
    public Collection<Morceau> getMorceauxByInstrumentsPaginated(Instrument i, int page) {
        Query q = em.createQuery("select DISTINCT(m) from Morceau m join m.pistes pistes join pistes.instrument instrument where instrument = :i");
        q.setParameter("i", i);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    /**
     * Récupère le morcaeu correspondant au titre spécifié
     *
     * @param titre Le titre du morceau
     * @return Le morceau récupéré
     */
    public Morceau getMorceau(String titre) {
        Query q = em.createQuery("select m from Morceau m where lower(m.titre) = :titre");
        q.setParameter("titre", titre.toLowerCase());

        return (Morceau) q.getSingleResult();
    }
}
