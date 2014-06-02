package gestionnaires;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modeles.Artiste;

@Stateless
public class GestionnaireArtistes {
    
    @PersistenceContext
    private EntityManager em;
    
    public void setArtiste(String nom, String resume, String photo){
        Artiste a = new Artiste(nom, resume, photo);
        
        em.persist(a);
    }

}
