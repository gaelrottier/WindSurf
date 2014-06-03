package gestionnaires;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modeles.Artiste;
import modeles.Morceau;

@Stateless
public class GestionnaireMorceaux {

    @PersistenceContext
    private EntityManager em;

    public Morceau creerMorceau(String titre, int nbPistes, int annee, String url){
        Morceau m = new Morceau(titre, nbPistes, annee, url);
        
        em.persist(m);
        
        return m;
    }
    
    public void setArtiste(Morceau m, Artiste a){;
        Morceau mf = (Morceau) em.find(Morceau.class, m.getId());
        
        mf.setArtiste(a);
        
        em.persist(mf);
    }
    
    public Morceau getMorceau(String titre){
        Query q = em.createQuery("select m from Morceau m where m.titre = :titre");
        q.setParameter("titre", titre);
        
        return (Morceau) q.getSingleResult();
    }
}
