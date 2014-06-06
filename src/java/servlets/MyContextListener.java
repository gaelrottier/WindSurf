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
import gestionnaires.GestionnaireGenres;
import gestionnaires.GestionnaireInstruments;
import gestionnaires.GestionnaireUtilisateurs;
import gestionnaires.GestionnaireMorceaux;
import gestionnaires.GestionnairePistes;
import java.util.List;
import modeles.Artiste;
import modeles.Genre;
import modeles.Instrument;
import modeles.Morceau;
import modeles.Piste;

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

    @EJB
    private GestionnaireInstruments gestionnaireInstruments;

    @EJB
    private GestionnairePistes gestionnairePistes;

    @EJB
    private GestionnaireGenres gestionnaireGenres;

    /**
     * Crée les Abonnements, un Utilisateur, les Genres, et parse le Json.
     * Ajoute toutes ces données dans la bdd
     * @param sce Le contexte de l'application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        gestionnaireAbonnements.creerAbonnements();
        gestionnaireUtilisateurs.creerUtilisateur("admin", "admin");
        gestionnaireGenres.creerGenres();

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
    }

    /**
     * Ajoute les données contenues dans <Json> dans la base de données, 
     * découpées selon le nom de l'artiste, le titre de la chanson,
     * les instruments des pistes et les pistes.
     * Attribue aussi un genre à chaque chanson.
     * @param Json Les données à insérer dans la bdd (Correspondant au contenu
     * du fichier "/WEB-INF/musique.json"
     */
    private void fillDBFromJson(JSONArray Json) {

        JSONObject morceau;
        String morceauFullNom, morceauTitre, artiste, piste, instrument, genre;
        Pattern p;
        Matcher m;
        List<String> composition;
        Artiste a;
        Instrument in;
        Morceau mc;
        Genre g;
        Piste pi;
        int nbPistes;

        for (int i = 0; i < Json.size(); i++) {

            morceau = (JSONObject) Json.get(i);
            morceauFullNom = morceau.get("nom").toString();

            // Trouve la partie du String correspondant à "<String> - "
            p = Pattern.compile("^.*( \\- )");
            m = p.matcher(morceauFullNom);

            if (m.find()) {
                artiste = m.group(0).replace(" - ", "");
                a = gestionnaireArtistes.creerArtiste(artiste, "", "");

                morceauTitre = morceauFullNom.substring(m.end());
                mc = gestionnaireMorceaux.creerMorceau(morceauTitre, 0, 2000);

                genre = "";
                int jsonSizeSurCinq = Json.size() / 5;
                if (i < jsonSizeSurCinq) {
                    genre = "Rock";
                } else if (i < jsonSizeSurCinq * 2) {
                    genre = "Rap";
                } else if (i < jsonSizeSurCinq * 3) {
                    genre = "Reggae";
                } else if (i < jsonSizeSurCinq * 4) {
                    genre = "Blues";
                } else {
                    genre = "Variété";
                }
                g = gestionnaireGenres.getGenre(genre);

                gestionnaireMorceaux.setGenre(mc, g);
                gestionnaireGenres.addMorceau(g, mc);

                gestionnaireMorceaux.setArtiste(mc, a);
                gestionnaireArtistes.addMorceau(a, mc);

                // Trouve la partie du String correspondant à "<String><espace OU point OU un chiffre>"
                p = Pattern.compile(".*?(\\s|\\.|[0-9])");
                composition = (List<String>) morceau.get("composition");

                nbPistes = 0;

                for (String compo : composition) {
                    m = p.matcher(compo);
                    nbPistes++;

                    if (m.find()) {
                        instrument = m.group(0).toString();
                        instrument = instrument.substring(0, instrument.length() - 1);

                        in = gestionnaireInstruments.creerInstrument(instrument);
                        gestionnaireMorceaux.addInstrument(mc, in);
                        gestionnaireInstruments.addMorceau(in, mc);

                        piste = compo.substring(instrument.length() + 1);
                        if (Character.isDigit(piste.charAt(0))) {
                            piste = piste.substring(2);
                        }
                        pi = gestionnairePistes.creerPiste(piste, a, in, 3);
                        gestionnairePistes.setMorceau(pi, mc);
                        gestionnaireMorceaux.addPiste(mc, pi);
                        gestionnairePistes.setNumero(pi, nbPistes);
                    }
                }

                gestionnaireMorceaux.setNbPistes(mc, nbPistes);
            }
        }
    }
}
