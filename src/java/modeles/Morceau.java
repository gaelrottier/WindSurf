/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

@Entity
public class Morceau implements Serializable {

    @OneToOne
    private Genre genre;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String titre;
    private int nbPistes;
    @OneToMany
    private Collection<Piste> pistes = new ArrayList();
    @OneToMany
    private Collection<Instrument> instruments = new ArrayList<>();
    @OneToOne
    private Artiste artiste;
    private int annee;
    private String url;

    public Morceau() {

    }

    public Morceau(String titre, int nbPistes, int annee, String url) {
        this.titre = titre;
        this.nbPistes = nbPistes;
        this.annee = annee;
        this.url = url;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getTitre() {
        return titre;
    }

    public Collection<Piste> getPistes() {
        return pistes;
    }

    public void setPistes(Collection<Piste> pistes) {
        this.pistes = pistes;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNbPistes() {
        return nbPistes;
    }

    public void setNbPistes(int nbPistes) {
        this.nbPistes = nbPistes;
    }

    public Collection<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Collection<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

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
