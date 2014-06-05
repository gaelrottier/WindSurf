/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gestionnaires.GestionnaireArtistes;
import gestionnaires.GestionnaireGenres;
import gestionnaires.GestionnaireInstruments;
import gestionnaires.GestionnaireMorceaux;
import java.io.IOException;
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

/**
 *
 * @author Goys
 */
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
        String t = request.getParameter("t");
        String q = request.getParameter("q");
        String forwardTo = "index.jsp";
        String page = request.getParameter("page");
        HttpSession session = request.getSession();
        int p = 1;

        if (page != null) {
            p = Integer.parseInt(page);
        }

        System.out.println("Page  : "+p);
        if (t != null && q != null) {
            int rch = Integer.parseInt(q);

            switch (t) {
                case "Artistes":
                    Artiste a = gestionnaireArtistes.getArtisteByIdPaginated(rch, p);
                    forwardTo = "artiste.jsp";
                    session.setAttribute("res", a);
                    // Je ne sais pas pourquoi, mais si je ne fais pas de foreach sur la collection, elle n'est pas instanciée
                    for (Morceau mc : a.getMorceaux()) {
                    }
                    break;
                case "Morceaux":
                    Morceau m = gestionnaireMorceaux.getMorceauByIdPaginated(rch, p);
                    forwardTo = "morceau.jsp";
                    session.setAttribute("res", m);
                    for (Instrument in : m.getInstruments()) {
                    }
                    break;
                case "Instruments":
                    Instrument i = gestionnaireInstruments.getInstrumentByIdPaginated(rch, p);
                    forwardTo = "listeResultats.jsp";
                    session.setAttribute("res", i);
                    for (Morceau mc : i.getMorceaux()) {
                    }
                    break;
                case "Genres":
                    Genre g = gestionnaireGenres.getGenreByIdPaginated(rch, p);
                    forwardTo = "listeResultats.jsp";
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
