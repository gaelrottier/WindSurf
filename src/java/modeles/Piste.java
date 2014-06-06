package modeles;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Goys
 */
@Entity
public class Piste implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     *
     */
    @OneToOne
    private Artiste artiste;

    /**
     *
     */
    @OneToOne
    private Instrument instrument;

    /**
     *
     */
    private String nom;

    /**
     *
     */
    @ManyToOne
    private Morceau morceau;
    //Numéro de la piste

    /**
     *
     */
        private int num;

    /**
     *
     */
    private int difficulte;

    /**
     *
     * @return
     */
    public int getNum() {
        return num;
    }

    /**
     *
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     *
     * @return
     */
    public Morceau getMorceau() {
        return morceau;
    }

    /**
     *
     * @return
     */
    public int getDifficulte() {
        return difficulte;
    }

    /**
     *
     * @param difficulte
     */
    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    /**
     *
     * @param morceau
     */
    public void setMorceau(Morceau morceau) {
        this.morceau = morceau;
    }

    /**
     *
     * @return
     */
    public Artiste getArtiste() {
        return artiste;
    }

    /**
     *
     * @param artiste
     */
    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    /**
     *
     * @return
     */
    public Instrument getInstrument() {
        return instrument;
    }

    /**
     *
     * @param instrument
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    /**
     *
     * @param nom
     * @param difficulte
     */
    public Piste(String nom, int difficulte) {
        this.nom = nom;
        this.difficulte = difficulte;
    }

    /**
     *
     */
    public Piste() {

    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Piste)) {
            return false;
        }
        Piste other = (Piste) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modeles.Piste[ id=" + id + " ]";
    }

}
