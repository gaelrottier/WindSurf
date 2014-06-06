package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Goys
 */
@Entity
public class Artiste implements Serializable {

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
    private String resume;

    /**
     *
     */
    private String photo;

    /**
     *
     */
    @OneToMany(mappedBy = "artiste")
    private Collection<Morceau> morceaux = new ArrayList<>();

    /**
     *
     */
    public Artiste() {

    }

    /**
     *
     * @param nom
     * @param resume
     * @param photo
     */
    public Artiste(String nom, String resume, String photo) {
        this.nom = nom;
        this.resume = resume;
        this.photo = photo;
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
    public String getResume() {
        return resume;
    }

    /**
     *
     * @param resume
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     *
     * @return
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
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
    public String toString() {
        return "modeles.Artiste[ id=" + id + " ]";
    }

}
