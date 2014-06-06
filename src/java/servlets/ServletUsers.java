/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import gestionnaires.GestionnaireUtilisateurs;

/**
 *
 * @author Goys
 */
@WebServlet(name = "ServletUsers", urlPatterns = {"/ServletUsers"})
public class ServletUsers extends HttpServlet {

    /**
     *
     */
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * Si le paramètre "action" de la requete a pour valeur "creerUtilisateur",
     * un utilisateur est créé dans la bdd avec les login et password(qui sera
     * crypté) saisis. Redirige ensuite l'utilisateur sur la page d'index.
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
        String message = "";
        String forwardTo = "index.jsp?";

        if (action != null) {
            switch (action) {
                case "creerUtilisateur": {
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");

                    if (!gestionnaireUtilisateurs.exists(login)) {
                        gestionnaireUtilisateurs.creerUtilisateur(login, password);
                        message = "inscrit";
                    } else {
                        forwardTo = "signup.jsp";
                        message = "existe";
                    }
                    break;
                }
                default:
                    break;
            }
        }

        HttpSession session = request.getSession(false);
        session.setAttribute("message", message);
        response.sendRedirect(forwardTo);
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
