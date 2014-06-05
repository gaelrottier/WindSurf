package gestionnaires;

import java.util.Collection;
import java.util.List;
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

@Stateless
public class GestionnaireMorceaux {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GestionnaireGenres gestionnaireGenres;

    @EJB
    private GestionnaireArtistes gestionnaireArtistes;

    public Morceau creerMorceau(String titre, int nbPistes, int annee) {
        Morceau m = new Morceau(titre, nbPistes, annee, "http://fr.wikipedia.org/wiki/" + titre);

        em.persist(m);

        return m;
    }

    public void addPiste(Morceau m, Piste p) {
        Collection<Piste> pc = m.getPistes();

        if (!pc.contains(p)) {
            pc.add(p);
            m.setPistes(pc);

            em.merge(m);
        }
    }

    public Collection<Piste> getPistes(Morceau m) {
        return m.getPistes();
    }

    public void setArtiste(Morceau m, Artiste a) {;
        m.setArtiste(a);

        em.merge(m);

        gestionnaireArtistes.addMorceau(a, m);
    }

    public Collection<Morceau> searchMorceau(String search) {
        Query q = em.createQuery("select m from Morceau m where lower(m.titre) LIKE :titre");
        q.setParameter("titre", "%" + search.toLowerCase() + "%");

        return (Collection<Morceau>) q.getResultList();
    }

    public void addInstrument(Morceau m, Instrument i) {
        Collection<Instrument> ins = m.getInstruments();

        if (!ins.contains(i)) {
            m = (Morceau) em.find(Morceau.class, m.getId());

            ins.add(i);
            m.setInstruments(ins);

            em.merge(m);
        }
    }

    public void setNbPistes(Morceau m, int nb) {
        m.setNbPistes(nb);

        em.merge(m);
    }

    public void setGenre(Morceau m, Genre g) {
        m.setGenre(g);

        em.merge(m);

        gestionnaireGenres.addMorceau(g, m);
    }

    public Morceau getMorceauById(int id) {
        return em.find(Morceau.class, id);
    }

    public Collection<Morceau> getMorceauxByArtistePaginated(Artiste a, int page) {
        Query q = em.createQuery("select m from Morceau m where m.artiste = :a");
        q.setParameter("a", a);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    public Collection<Piste> getPistesByMorceauPaginated(Morceau m, int page) {
        Query q = em.createQuery("select m.pistes from Morceau m where m = :m");
        q.setParameter("m", m);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    public Collection<Morceau> getMorceauxByGenrePaginated(Genre g, int page) {
        Query q = em.createQuery("select m from Morceau m where m.genre = :g");
        q.setParameter("g", g);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    public Collection<Morceau> getMorceauxByInstrumentsPaginated(Instrument i, int page) {
        Query q = em.createQuery("select DISTINCT(m) from Morceau m join m.pistes pistes join pistes.instrument instrument where instrument = :i");
        q.setParameter("i", i);

        int start = page * 10 - 10;

        q.setMaxResults(10);
        q.setFirstResult(start);

        return q.getResultList();
    }

    public Morceau getMorceau(String titre) {
        Query q = em.createQuery("select m from Morceau m where lower(m.titre) = :titre");
        q.setParameter("titre", titre.toLowerCase());

        return (Morceau) q.getSingleResult();
    }
}
