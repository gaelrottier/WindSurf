package servlets;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import utils.JSON;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import gestionnaires.GestionnaireAbonnements;
import gestionnaires.GestionnaireArtistes;
import gestionnaires.GestionnaireUtilisateurs;
import gestionnaires.GestionnaireMorceaux;

@WebListener
public class MyContextListener implements ServletContextListener {

    @EJB
    private GestionnaireAbonnements gestionnaireAbonnements;

    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

    @EJB
    private GestionnaireArtistes gestionnaireArtistes;

    @EJB
    private GestionnaireMorceaux gestionnaireMorceaux;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        gestionnaireAbonnements.creerAbonnements();
        gestionnaireUtilisateurs.creerUtilisateur("admin", "admin");

        try {
            JSON Json = new JSON(sce.getServletContext());
            JSONArray JsonData = Json.ParseJSON();
            fillDBFromJson(JsonData);
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

    private void fillDBFromJson(JSONArray Json) {

        for (int i = 0; i < Json.size(); i++) {

            JSONObject morceau = (JSONObject) Json.get(i);

            String morceauFullNom = morceau.get("nom").toString();

            // Trouve la partie du String correspondant à "<String> - "
            Pattern p = Pattern.compile("^.*( \\- )");
            Matcher m = p.matcher(morceauFullNom);

            if (m.find()) {
                String artiste = m.group(0).replace(" - ", "");
                gestionnaireArtistes.creerArtiste(artiste, "", "");

                String morceauTitre = morceauFullNom.substring(m.end());
                gestionnaireMorceaux.creerMorceau(morceauTitre, 0, 2000, "");
                
                gestionnaireMorceaux.setArtiste(gestionnaireMorceaux.getMorceau(morceauTitre), gestionnaireArtistes.getArtiste(artiste));
            }

        }
    }
}
