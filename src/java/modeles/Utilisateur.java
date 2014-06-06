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
import utils.MD5Hash;

@Entity
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private String password;
    @OneToOne
    private Abonnement abo;
    private Long dateSouscription;
    @OneToMany
    private Collection<Morceau> achats = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(final String login, final String password) {
        this.login = login;
        this.password = encrypt(password);
    }
    
    public Collection<Morceau> getAchats() {
        return achats;
    }

    public void setAchats(Collection<Morceau> achats) {
        this.achats = achats;
    }

    public Long getDateSouscription() {
        return dateSouscription;
    }

    public void setDateSouscription(Long dateSouscription) {
        this.dateSouscription = dateSouscription;
    }

    public Abonnement getAbo() {
        return abo;
    }

    public void setAbo(Abonnement abo) {
        this.abo = abo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    private String encrypt(String str) {
        return MD5Hash.encrypt(str);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utilisateurs.modeles.Utilisateur[ id=" + id + " ]";
    }

}
