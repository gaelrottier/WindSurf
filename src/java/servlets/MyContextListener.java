package servlets;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import Utils.JSON;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class MyContextListener implements ServletContextListener {

    @EJB
    private gestionnaires.GestionnaireAbonnements gestionnaireAbonnements;

    @EJB
    private gestionnaires.GestionnaireUtilisateurs gestionnaireUtilisateurs;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        gestionnaireAbonnements.creerAbonnements();
        gestionnaireUtilisateurs.creerUtilisateur("admin", "admin");
        try {
            JSON Json = new JSON();
            Json.ParseJSON(sce.getServletContext());
        } catch (IOException ex) {
            Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("login");
        sce.getServletContext().removeAttribute("userAbo");
        sce.getServletContext().removeAttribute("listeAbos");
    }
}
