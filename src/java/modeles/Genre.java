package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Goys
 */
@Entity
public class Genre implements Serializable {

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
    private String nom;

    /**
     *
     */
    @ManyToMany
    private Collection<Morceau> morceaux = new ArrayList<>();

    /**
     *
     */
    public Genre(){
        
    }
    
    /**
     *
     * @param nom
     */
    public Genre(String nom){
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
    public Collection<Morceau> getMorceaux() {
        return morceaux;
    }

    /**
     *
     * @param morceaux
     */
    public void setMorceaux(Collection<Morceau> morceaux) {
        this.morceaux = morceaux;
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
        if (!(object instanceof Genre)) {
            return false;
        }
        Genre other = (Genre) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modeles.Genre[ id=" + id + " ]";
    }

}
