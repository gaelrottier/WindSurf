package servlets;

import gestionnaires.GestionnaireMorceaux;
import gestionnaires.GestionnaireUtilisateurs;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modeles.Morceau;
import modeles.Utilisateur;

/**
 *
 * @author Goys
 */
@WebServlet(name = "ServletAchat", urlPatterns = {"/ServletAchat"})
public class ServletAchat extends HttpServlet {

    /**
     *
     */
    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

    /**
     *
     */
    @EJB
    private GestionnaireMorceaux gestionnaireMorceaux;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Si l'utilisateur est connecté, ajoute le morceau défini par "idM" dans sa
     * liste d'achats.
     *
     * Renvoie ensuite sur la page d'accueil
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idMorceau = request.getParameter("idM");
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (idMorceau != null && login != null) {
            Utilisateur u = gestionnaireUtilisateurs.getUser(login);
            Morceau m = gestionnaireMorceaux.getMorceauById(Integer.parseInt(idMorceau));

            gestionnaireUtilisateurs.setMorceau(u, m);

            session.setAttribute("message", "achat");
            session.setAttribute("morceau", m.getTitre());
        }

        response.sendRedirect("index.jsp");

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
