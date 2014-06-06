package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Artiste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String resume;
    private String photo;
    @OneToMany(mappedBy = "artiste")
    private Collection<Morceau> morceaux = new ArrayList<>();

    public Artiste() {

    }

    public Artiste(String nom, String resume, String photo) {
        this.nom = nom;
        this.resume = resume;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Collection<Morceau> getMorceaux() {
        return morceaux;
    }

    public void setMorceaux(Collection<Morceau> morceaux) {
        this.morceaux = morceaux;
    }

    @Override
    public String toString() {
        return "modeles.Artiste[ id=" + id + " ]";
    }

}
