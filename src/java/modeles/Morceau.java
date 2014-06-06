package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Goys
 */
@Entity
public class Morceau implements Serializable {

    /**
     *
     */
    @OneToOne
    private Genre genre;

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
    private String titre;

    /**
     *
     */
    private int nbPistes;

    /**
     *
     */
    @OneToMany
    private Collection<Piste> pistes = new ArrayList();
    // N'est pas utilisé par l'application

    /**
     *
     */
        @OneToMany
    private Collection<Instrument> instruments = new ArrayList<>();

    /**
     *
     */
    @OneToOne
    private Artiste artiste;

    /**
     *
     */
    private int annee;

    /**
     *
     */
    private String url;

    /**
     *
     */
    public Morceau() {

    }

    /**
     *
     * @param titre
     * @param nbPistes
     * @param annee
     * @param url
     */
    public Morceau(String titre, int nbPistes, int annee, String url) {
        this.titre = titre;
        this.nbPistes = nbPistes;
        this.annee = annee;
        this.url = url;
    }

    /**
     *
     * @return
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     *
     * @param genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     *
     * @return
     */
    public String getTitre() {
        return titre;
    }

    /**
     *
     * @return
     */
    public Collection<Piste> getPistes() {
        return pistes;
    }

    /**
     *
     * @param pistes
     */
    public void setPistes(Collection<Piste> pistes) {
        this.pistes = pistes;
    }

    /**
     *
     * @param titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     *
     * @return
     */
    public int getNbPistes() {
        return nbPistes;
    }

    /**
     *
     * @param nbPistes
     */
    public void setNbPistes(int nbPistes) {
        this.nbPistes = nbPistes;
    }

    /**
     *
     * @return
     */
    public Collection<Instrument> getInstruments() {
        return instruments;
    }

    /**
     *
     * @param instruments
     */
    public void setInstruments(Collection<Instrument> instruments) {
        this.instruments = instruments;
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
    public int getAnnee() {
        return annee;
    }

    /**
     *
     * @param annee
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof Morceau)) {
            return false;
        }
        Morceau other = (Morceau) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modeles.Morceau[ id=" + id + " ]";
    }

}
