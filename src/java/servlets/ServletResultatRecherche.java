package servlets;

import gestionnaires.GestionnaireArtistes;
import gestionnaires.GestionnaireGenres;
import gestionnaires.GestionnaireInstruments;
import gestionnaires.GestionnaireMorceaux;
import gestionnaires.GestionnaireUtilisateurs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modeles.Artiste;
import modeles.Genre;
import modeles.Instrument;
import modeles.Morceau;
import modeles.Piste;
import modeles.Utilisateur;

@WebServlet(name = "ServletResultatRecherche", urlPatterns = {"/ServletResultatRecherche"})
public class ServletResultatRecherche extends HttpServlet {

    @EJB
    private GestionnaireArtistes gestionnaireArtistes;

    @EJB
    private GestionnaireInstruments gestionnaireInstruments;

    @EJB
    private GestionnaireGenres gestionnaireGenres;

    @EJB
    private GestionnaireMorceaux gestionnaireMorceaux;

    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Récupère l'Artiste, l'Instrument, le Genre ou le Morceau (défini par le
     * paramètre "t"), ayant pour id la valeur du paramètre "q".
     *
     * Récupère aussi les pistes (dans le cas d'un morceau) ou les morceaux
     * (dans le cas d'un genre, d'un instrument ou d'un artiste) correspondant à
     * la recherche, de manière paginée (la page est définie par le paramètre
     * "p")
     *
     * Renvoie ensuite sur la page affichant les résultats.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String t = request.getParameter("t");
        String q = request.getParameter("q");
        String forwardTo = "index.jsp";
        String page = request.getParameter("page");
        HttpSession session = request.getSession();
        int p = 1;

        if (page != null) {
            p = Integer.parseInt(page);
        }

        if (t != null && q != null) {
            int rch = Integer.parseInt(q);
            String login = (String) session.getAttribute("login");
            Utilisateur u = null;
            if (login != null) {
                u = gestionnaireUtilisateurs.getUser((String) session.getAttribute("login"));
            }
            Collection<Morceau> achats = new ArrayList<>();
            Collection<Morceau> morceaux = new ArrayList<>();

            switch (t) {
                case "Artistes":
                    Artiste a = gestionnaireArtistes.getArtisteById(rch);
                    morceaux = gestionnaireMorceaux.getMorceauxByArtistePaginated(a, p);
                    if (login != null) {
                        achats = gestionnaireUtilisateurs.getAchatsByArtiste(u, a);
                        session.setAttribute("achats", achats);
                    }
                    forwardTo = "artiste.jsp";
                    session.setAttribute("morceaux", morceaux);
                    session.setAttribute("res", a);
                    // Je ne sais pas pourquoi, mais si je ne fais pas de foreach sur la collection, elle n'est pas instanciée
                    for (Morceau mc : a.getMorceaux()) {
                    }
                    break;
                case "Morceaux":
                    Morceau m = gestionnaireMorceaux.getMorceauById(rch);
                    Collection<Piste> pistes = gestionnaireMorceaux.getPistesByMorceauPaginated(m, p);
                    forwardTo = "morceau.jsp";
                    session.setAttribute("pistes", pistes);
                    session.setAttribute("res", m);
                    for (Instrument in : m.getInstruments()) {
                    }
                    break;
                case "Instruments":
                    Instrument i = gestionnaireInstruments.getInstrumentById(rch);
                    morceaux = gestionnaireMorceaux.getMorceauxByInstrumentsPaginated(i, p);
                    if (login != null) {
                        achats = gestionnaireUtilisateurs.getAchatsByInstrument(u, i);
                        session.setAttribute("achats", achats);

                    }
                    forwardTo = "listeResultats.jsp";
                    session.setAttribute("morceaux", morceaux);
                    session.setAttribute("res", i);
                    for (Morceau mc : i.getMorceaux()) {
                    }
                    break;
                case "Genres":
                    Genre g = gestionnaireGenres.getGenreById(rch);
                    morceaux = gestionnaireMorceaux.getMorceauxByGenrePaginated(g, p);
                    if (login != null) {
                        achats = gestionnaireUtilisateurs.getAchatsByGenre(u, g);
                        session.setAttribute("achats", achats);
                    }
                    forwardTo = "listeResultats.jsp";
                    session.setAttribute("morceaux", morceaux);
                    session.setAttribute("res", g);
                    for (Morceau mc : g.getMorceaux()) {
                    }
                    break;
                default:
                    break;
            }
            session.setAttribute("page", p);
        }

        response.sendRedirect(forwardTo);
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
