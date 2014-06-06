package gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Genre;
import modeles.Morceau;

@Stateless
public class GestionnaireGenres {

    @PersistenceContext
    EntityManager em;

    /**
     * Crée les genres
     */
    public void creerGenres() {
        creerGenre("Rock");
        creerGenre("Rap");
        creerGenre("Reggae");
        creerGenre("Blues");
        creerGenre("Variété");
    }

    /**
     * Crée un genre
     *
     * @param nom Le nom du genre
     * @return Le genre créé
     */
    public Genre creerGenre(String nom) {
        Genre g;

        if (exists(nom)) {
            g = getGenre(nom);
        } else {
            g = new Genre(nom);
            em.persist(g);
        }

        return g;
    }

    /**
     * S'il n'est pas déjà présent dans la liste des morceaux du genre, ajoute
     * un morceau à cette liste
     *
     * @param g Le genre à mettre à jour
     * @param m Le morceau à ajouter
     */
    public void addMorceau(Genre g, Morceau m) {
        Collection<Morceau> cm = g.getMorceaux();

        if (!cm.contains(m)) {
            cm.add(m);
            g.setMorceaux(cm);

            em.merge(g);
        }
    }

    /**
     * Renvoie la liste des genres dont le nom contient <search>
     *
     * @param search La chaîne de caractères à rechercher
     * @return La liste des genres récupérée
     */
    public Collection<Genre> searchGenre(String search) {
        Query q = em.createQuery("select g from Genre g where lower(g.nom) LIKE :nom");
        q.setParameter("nom", "%" + search.toLowerCase() + "%");

        return (Collection<Genre>) q.getResultList();
    }

    /**
     * Renvoie le genre dont l'id correspond à <id>
     *
     * @param id L'id à rechercher
     * @return Le genre récupéré
     */
    public Genre getGenreById(int id) {
        return em.find(Genre.class, id);
    }

    /**
     * REnvoie le genre dont le nom correspond à <nom>
     *
     * @param nom Le nom du genre
     * @return Le genre récupéré
     */
    public Genre getGenre(String nom) {
        Query q = em.createQuery("select g from Genre g where lower(g.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return (Genre) q.getSingleResult();

    }

    /**
     * Vérifie si le genre existe dans la bdd
     *
     * @param nom Le nom du genre
     * @return True s'il existe, False s'il n'existe pas
     */
    private boolean exists(String nom) {
        Query q = em.createQuery("select g from Genre g where lower(g.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
