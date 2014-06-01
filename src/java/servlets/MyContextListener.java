package servlets;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("login");
        sce.getServletContext().removeAttribute("userAbo");
        sce.getServletContext().removeAttribute("listeAbos");
    }
}
