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

/**
 *
 * @author Goys
 */
@Entity
public class Utilisateur implements Serializable {

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
    private String login;

    /**
     *
     */
    private String password;

    /**
     *
     */
    @OneToOne
    private Abonnement abo;

    /**
     *
     */
    private Long dateSouscription;

    /**
     *
     */
    @OneToMany
    private Collection<Morceau> achats = new ArrayList<>();

    /**
     *
     */
    public Utilisateur() {
    }

    /**
     *
     * @param login
     * @param password
     */
    public Utilisateur(final String login, final String password) {
        this.login = login;
        this.password = encrypt(password);
    }
    
    /**
     *
     * @return
     */
    public Collection<Morceau> getAchats() {
        return achats;
    }

    /**
     *
     * @param achats
     */
    public void setAchats(Collection<Morceau> achats) {
        this.achats = achats;
    }

    /**
     *
     * @return
     */
    public Long getDateSouscription() {
        return dateSouscription;
    }

    /**
     *
     * @param dateSouscription
     */
    public void setDateSouscription(Long dateSouscription) {
        this.dateSouscription = dateSouscription;
    }

    /**
     *
     * @return
     */
    public Abonnement getAbo() {
        return abo;
    }

    /**
     *
     * @param abo
     */
    public void setAbo(Abonnement abo) {
        this.abo = abo;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    /**
     *
     * @param str
     * @return
     */
    private String encrypt(String str) {
        return MD5Hash.encrypt(str);
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
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
