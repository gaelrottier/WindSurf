package gestionnaires;

import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Abonnement;
import modeles.Artiste;
import modeles.Genre;
import modeles.Instrument;
import modeles.Morceau;
import modeles.Utilisateur;

/**
 *
 * @author Goys
 */
@Stateless
public class GestionnaireUtilisateurs {

    /**
     *
     */
    @PersistenceContext
    private EntityManager em;

    /**
     *
     */
    @EJB
    private GestionnaireAbonnements gestionnaireAbonnements;

    //Nombre de millisecondes dans 1 heure

    /**
     *
     */
        private final int MS_IN_H = 3600000;

    /**
     * Crée un utilisateur dans la base de données avec comme login <login>,
     * comme mot de passe <password>, etlui attribue un abonnement "Gratuit", en
     * lui donnant une date de souscription correspondant à l'heure de création
     * de l'utilisateur
     *
     * @param login Le login de l'utilisateur
     * @param password Le mot de passe de l'utilisateur. Il sera crypté
     * @return L'utilisateur créé
     */
    public Utilisateur creerUtilisateur(String login, String password) {
        Utilisateur u = new Utilisateur(login, password);
        Abonnement a = gestionnaireAbonnements.getAboByName("Gratuit");

        u.setAbo(a);
        Date dateSouscription = new Date();

        u.setDateSouscription(dateSouscription.getTime());

        em.persist(u);
        return u;
    }

    /**
     * Récupère les morceaux achetés par l'utilisateur ayant pour artiste <a>
     *
     * @param u L'utilisateur
     * @param a L'artiste à rechercher
     * @return La liste des morceaux achetés par l'utlisateur
     */
    public Collection<Morceau> getAchatsByArtiste(Utilisateur u, Artiste a) {
        Query q = em.createQuery("select u.achats from Utilisateur u join u.achats achats join achats.artiste artiste where u = :u and artiste = :a");
        q.setParameter("u", u);
        q.setParameter("a", a);

        return q.getResultList();
    }

    /**
     * Récupère les morceaux achetés par l'utilisateur ayant pour genre <g>
     *
     * @param u L'utilisateur
     * @param g Le genre
     * @return L liste des morceaux achetés par l'utilisateur
     */
    public Collection<Morceau> getAchatsByGenre(Utilisateur u, Genre g) {
        Query q = em.createQuery("select u.achats from Utilisateur u join u.achats achats join achats.genre genre where u = :u and genre = :g");
        q.setParameter("u", u);
        q.setParameter("g", g);

        return q.getResultList();
    }

    /**
     * Récupère les morceaux achetés par l'utilisateur dont au moins une piste a
     * pour instrument <i>
     *
     * @param u L'utilisateur
     * @param i L'instrument
     * @return L liste des morceaux achetés par l'utilisateur
     */
    public Collection<Morceau> getAchatsByInstrument(Utilisateur u, Instrument i) {
        Query q = em.createQuery("select u.achats from Utilisateur u join u.achats achats join achats.pistes pistes join pistes.instrument instrument where u = :u and instrument = :i");
        q.setParameter("u", u);
        q.setParameter("i", i);

        return q.getResultList();
    }

    /**
     * Si l'utilisateur n'a pas déjà acheté le morceau <m>, ajoute celui-ci dans
     * la collection de ses achats.
     *
     * @param u L'utilisateur
     * @param m Le morceau
     */
    public void setMorceau(Utilisateur u, Morceau m) {
        Utilisateur uf = em.find(Utilisateur.class, u.getId());

        Collection<Morceau> morceaux = uf.getAchats();

        if (!morceaux.contains(m)) {
            morceaux.add(m);
        }

        uf.setAchats(morceaux);

        em.merge(uf);
    }

    /**
     * Vérifie si les <login> et <password> correspondent à une entrée de la
     * base de données. Renvoie un booléen correspondant à cette assertion
     *
     * @param login Le login à tester
     * @param password Le mot de passe à tester
     * @return True si l'utilisateur existe, False s'il n'existe pas
     */
    public boolean connect(String login, String password) {
        Query q = em.createQuery("select u from Utilisateur u where lower(u.login) = :login and u.password = :password");
        q.setParameter("login", login.toLowerCase());
        q.setParameter("password", utils.MD5Hash.encrypt(password));

        return q.getResultList().size() > 0;
    }

    /**
     * Récupère l'abonnement de l'utilisateur et le renvoie.
     *
     * De plus, si l'abonnement est arrivé à expiration, il est remplacé en bdd
     * par un abonnement "Gratuit".
     *
     * @param login Le login de l'utilisateur
     * @return L'abonnement de l'utilisateur
     */
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

    /**
     * Définit l'abonnement de l'utilisateur
     *
     * @param login Le login de l'utilisateur
     * @param abo L'abonnement à insérer. Il peut avoir les valeurs suivantes :
     * "Gratuit", "Week-end", "1 mois", "1 an", "A vie".
     */
    public void setAbo(String login, String abo) {
        Utilisateur u = getUser(login);
        u.setAbo(gestionnaireAbonnements.getAboByName(abo));
        em.persist(u);
    }

    /**
     * Définit l'abonnement de l'utilisateur
     *
     * @param login Le login de l'utilisateur
     * @param abo L'abonnement à insérer
     */
    public void setAbo(String login, Abonnement abo) {
        Utilisateur u = getUser(login);
        u.setAbo(abo);
        em.persist(u);
    }

    /**
     * Renvoie l'utilisateur défini par le <login>
     *
     * @param login Le login de l'utilisateur
     * @return L'utilisateur
     */
    public Utilisateur getUser(String login) {
        Query q = em.createQuery("select u from Utilisateur u where lower(u.login) = :login");
        q.setParameter("login", login.toLowerCase());

        return (Utilisateur) q.getSingleResult();
    }

    /**
     * Récupère la date de souscription à l'abonnement de l'utilisateur
     *
     * @param login Le login de l'utilisateur
     * @return La date de souscription, en millisecondes
     */
    public long getUserAboDate(String login) {
        Query q = em.createQuery("select u.dateSouscription from Utilisateur u where lower (u.login) = :login");
        q.setParameter("login", login.toLowerCase());

        return (long) q.getSingleResult();
    }

    /**
     * Vérifie si l'utilisateur existe en bdd
     *
     * @param login Le login à rechercher
     * @return True s'il existe, False s'il n'existe pas
     */
    public boolean exists(String login) {
        Query q = em.createQuery("select u from Utilisateur u where lower(u.login) = :login");
        q.setParameter("login", login.toLowerCase());

        return q.getResultList().size() > 0;
    }
}
