package servlets;

import gestionnaires.GestionnaireArtistes;
import gestionnaires.GestionnaireGenres;
import gestionnaires.GestionnaireInstruments;
import gestionnaires.GestionnaireMorceaux;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modeles.Artiste;
import modeles.Genre;
import modeles.Instrument;
import modeles.Morceau;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "ServletRecherche", urlPatterns = {"/ServletRecherche"})
public class ServletRecherche extends HttpServlet {

    @EJB
    GestionnaireArtistes gestionnaireArtistes;

    @EJB
    GestionnaireMorceaux gestionnaireMorceaux;

    @EJB
    GestionnaireGenres gestionnaireGenres;

    @EJB
    GestionnaireInstruments gestionnaireInstruments;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * Regarde, pour chaque Artiste, Instrument, Piste et Morceau, si son
     * nom/titre contient la chaîne de caractères présente dans le paramètre
     * "recherche". Si une occurrence est trouvée, elle est ajoutée à la liste
     * des résultats.
     *
     * Dans le cas des artistes, si une correspondance est trouvée dans la bdd,
     * les morceaux le concernant sont aussi ajoutés à la liste des résultats,
     * en évitant les doublons.
     *
     * Renvoie ensuite les résultats en Json sur le PrintWriter de la
     * <response>, car cette servlet est destinée à être utilisée en Ajax.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recherche = request.getParameter("recherche");

        if (recherche != null) {
            Collection<Artiste> artistes = gestionnaireArtistes.searchArtiste(recherche);
            Collection<Morceau> morceaux = gestionnaireMorceaux.searchMorceau(recherche);
            Collection<Genre> genres = gestionnaireGenres.searchGenre(recherche);
            Collection<Instrument> instruments = gestionnaireInstruments.searchInstrument(recherche);

            //Récupérer les morceaux des artistes correspondant à la recherche
            for (Artiste art : artistes) {
                for (Morceau morc : art.getMorceaux()) {
                    if (!morceaux.contains(morc)) {
                        morceaux.add(morc);
                    }
                }
            }

            JSONObject results = new JSONObject();

            JSONArray result = new JSONArray();
            JSONObject res = new JSONObject();

            Instrument i;
            for (Iterator<Instrument> it = instruments.iterator(); it.hasNext();) {
                i = it.next();

                res.put("id", i.getId());
                res.put("nom", i.getNom());

                result.add(res);

                res = new JSONObject();
            }

            results.put("Instruments", result);

            /**
             * Si les objets result et res ne sont pas ré-instanciés après
             * chaque boucle, le Json de retour ne contiendra dans chaque clé
             * ("Artistes", "Morceaux", "Genres", "Instruments")que les valeurs
             * de la dernière boucle effectuée (ici "Artistes").
             */
            result = new JSONArray();
            res = new JSONObject();
            Genre g;
            for (Iterator<Genre> it = genres.iterator(); it.hasNext();) {
                g = it.next();

                res.put("id", g.getId());
                res.put("nom", g.getNom());

                result.add(res);

                res = new JSONObject();
            }

            results.put("Genres", result);

            result = new JSONArray();
            res = new JSONObject();
            Morceau m;
            for (Iterator<Morceau> it = morceaux.iterator(); it.hasNext();) {
                m = it.next();

                res.put("id", m.getId());
                res.put("nom", m.getTitre());

                result.add(res);

                res = new JSONObject();
            }

            results.put("Morceaux", result);

            result = new JSONArray();
            res = new JSONObject();
            Artiste a;
            for (Iterator<Artiste> it = artistes.iterator(); it.hasNext();) {
                a = it.next();

                res.put("id", a.getId());
                res.put("nom", a.getNom());

                result.add(res);

                res = new JSONObject();
            }

            results.put("Artistes", result);

            response.getWriter().println(results);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
