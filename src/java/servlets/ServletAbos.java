/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gestionnaires.GestionnaireAbonnements;
import gestionnaires.GestionnaireUtilisateurs;
import java.io.IOException;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modeles.Abonnement;

/**
 *
 * @author Goys
 */
@WebServlet(name = "ServletAbos", urlPatterns = {"/ServletAbos"})
public class ServletAbos extends HttpServlet {

    @EJB
    private GestionnaireAbonnements gestionnaireAbonnements;
    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

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
     * Récupère la liste des abonnements présents en bdd et l'insère dans un
     * cookie "listeAbos".
     *
     * Récupère aussi l'abonnement de l'utilisateur (dans le cas où il est
     * connecté) et l'insère dans un cookie "userAbo"
     *
     * Redirige ensuite sur la page listant les abonnements.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Collection<Abonnement> abos = gestionnaireAbonnements.getAllAbos();
        session.setAttribute("listeAbos", abos);

        if (session.getAttribute("login") != null) {
            Abonnement userAbo = gestionnaireUtilisateurs.getAbo(session.getAttribute("login").toString());
            session.setAttribute("userAbo", userAbo);
        }

        response.sendRedirect("listeAbos.jsp");

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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String message = "erreur";

        if (action != null) {
            if (action.equals("choixAbo")) {
                int idAbo = Integer.parseInt(request.getParameter("abo"));
                if (idAbo >= 1 && idAbo <= 5) {
                    if (session.getAttribute("login") != null) {
                        String login = session.getAttribute("login").toString();
                        Abonnement a = gestionnaireAbonnements.getAboById(idAbo);
                        gestionnaireUtilisateurs.setAbo(login, a);

                        message = "aboUpdated";
                    }
                }
            }
        }

        session.setAttribute("message", message);
        response.sendRedirect("ServletAbos");
    }
}
