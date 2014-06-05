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

    public void creerGenres() {
        setGenre("Rock");
        setGenre("Rap");
        setGenre("Reggae");
        setGenre("Blues");
        setGenre("Variété");
    }

    public Genre setGenre(String nom) {
        Genre g;

        if (exists(nom)) {
            g = getGenre(nom);
        } else {
            g = new Genre(nom);
            em.persist(g);
        }

        return g;
    }

    public void addMorceau(Genre g, Morceau m) {
        Collection<Morceau> cm = g.getMorceaux();
        cm.add(m);
        g.setMorceaux(cm);

        em.merge(g);
    }

    public Collection<Genre> searchGenre(String search) {
        Query q = em.createQuery("select g from Genre g where lower(g.nom) LIKE :nom");
        q.setParameter("nom", "%" + search.toLowerCase() + "%");

        return (Collection<Genre>) q.getResultList();
    }

    public Genre getGenre(String nom) {
        Query q = em.createQuery("select g from Genre g where lower(g.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return (Genre) q.getSingleResult();

    }

    private boolean exists(String nom) {
        Query q = em.createQuery("select g from Genre g where lower(g.nom) = :nom");
        q.setParameter("nom", nom.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
