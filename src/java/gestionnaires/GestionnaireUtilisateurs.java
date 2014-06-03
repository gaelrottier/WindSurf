package gestionnaires;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Abonnement;
import modeles.Utilisateur;

@Stateless
public class GestionnaireUtilisateurs {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GestionnaireAbonnements gestionnaireAbonnements;

    //Nombre de millisecondes dans 1 heure
    private final int MS_IN_H = 3600000;

    public Utilisateur creerUtilisateur(String login, String password) {
        Utilisateur u = new Utilisateur(login, password);
        Abonnement a = gestionnaireAbonnements.getAboByName("Gratuit");
        u.setAbo(a);
        Date dateSouscription = new Date();
        u.setDateSouscription(dateSouscription.getTime());
        em.persist(u);
        return u;
    }

    public boolean connect(String login, String password) {
        Query q = em.createQuery("select u from Utilisateur u where (u.login) = :login and u.password = :password");
        q.setParameter("login", login.toLowerCase());
        q.setParameter("password", utils.MD5Hash.encrypt(password));

        return q.getResultList().size() > 0;
    }

    public Abonnement getAbo(String login) {
        Query q = em.createQuery("select u.abo from Utilisateur u where lower(u.login) = :login");
        q.setParameter("login", login);

        Abonnement userAbo = (Abonnement) q.getSingleResult();

        if (!"Gratuit".equals(userAbo.getNom()) && !"A vie".equals(userAbo.getNom())) {
            Date actualDate = new Date();
            if (actualDate.getTime() > getUserAboDate(login) + userAbo.getDuree() * MS_IN_H) {
                setAbo(login, "Gratuit");
            }
        }

        return userAbo;
    }

    public void setAbo(String login, String abo) {
        Utilisateur u = getUser(login);
        u.setAbo(gestionnaireAbonnements.getAboByName("Gratuit"));
        em.persist(u);
    }

    public void setAbo(String login, Abonnement abo) {
        Utilisateur u = getUser(login);
        u.setAbo(abo);
        em.persist(u);
    }

    public Utilisateur getUser(String login) {
        Query q = em.createQuery("select u from Utilisateur u where lower(u.login) = :login");
        q.setParameter("login", login);

        return (Utilisateur) q.getSingleResult();
    }

    public long getUserAboDate(String login) {
        Query q = em.createQuery("select u.dateSouscription from Utilisateur u where lower (u.login) = :login");
        q.setParameter("login", login);

        return (long) q.getSingleResult();
    }

    public boolean exists(String login) {
        Query q = em.createQuery("select u from Utilisateur u where lower(u.login) = :login");
        q.setParameter("login", login.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
