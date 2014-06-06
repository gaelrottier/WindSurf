package gestionnaires;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;
import modeles.Instrument;
import modeles.Morceau;
import modeles.Piste;

@Stateless
public class GestionnairePistes {

    @PersistenceContext
    EntityManager em;

    /**
     * Crée une piste
     *
     * @param nom Le nom de la piste
     * @param a L'artiste de la piste
     * @param i L'instrument de la piste
     * @param difficulte La difficulté de la piste
     * @return La piste créée
     */
    public Piste creerPiste(String nom, Artiste a, Instrument i, int difficulte) {
        Piste p = new Piste(nom, difficulte);

        p.setArtiste(a);
        p.setInstrument(i);

        em.persist(p);

        return p;
    }

    /**
     * Définit le numéro de la piste
     *
     * @param p La piste à mettre à jour
     * @param num Le numéro de la piste
     */
    public void setNumero(Piste p, int num) {
        p.setNum(num);

        em.merge(p);
    }

    /**
     * Renvoie la piste correspondant aux paramètres recherchés. L'artiste a été
     * rajouté dans la requête, au cas où plusieurs pistes auraient le même nom.
     * (En se basant sur le fait qu'un nom de piste est unique pour un artiste)
     *
     * @param nom Le nom de la piste
     * @param a L'artiste de la piste
     * @return La piste trouvée
     */
    public Piste getPiste(String nom, Artiste a) {
        Query q = em.createQuery("select p from Piste p where lower(p.nom) = :nom and p.artiste = :a");
        q.setParameter("nom", nom.toLowerCase());
        q.setParameter("a", a);

        return (Piste) q.getSingleResult();
    }

    /**
     * Récupère le numéro de la piste fournie en paramètre
     *
     * @param p La piste dont on veut le numéro
     * @return Le numéro de la piste
     */
    public int getNumero(Piste p) {
        return em.find(Piste.class, p.getId()).getNum();
    }

    /**
     * Définit le morceau correspondant à la piste et met à jour l'entité en bdd
     *
     * @param p La piste à mettre à jour
     * @param m Le morceau à ajouter à la piste
     */
    public void setMorceau(Piste p, Morceau m) {
        p.setMorceau(m);

        em.merge(p);
    }
}
