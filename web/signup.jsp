<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Inscription"/>
    </c:import>
    <body>
        <c:import url="/WEB-INF/menu.jsp"></c:import>

        <c:if test="${sessionScope['message'] == 'existe'}">
            <div class="alert alert-danger" style="text-align: center;">
                D&eacute;sol&eacute;, ce nom d&apos;utilisateur est d&eacute;j&agrave; pris.
            </div>
            <c:remove var="message" scope="session"/>
        </c:if>
        <c:if test="${empty sessionScope['login']}">
            <label for="inscr">Inscription</label>
            <form action="ServletUsers" id="inscr" method="post">
                <label for="login">Entrez un nom d&apos;utilisateur :</label>
                <input type="text" placeholder="Entrez un nom d&apos;utilisateur" id="login" name="login" required>
                <label for="password">Entrez un mot de passe :</label>
                <input type="password" placeholder="Entrez un mot de passe" id="password" name="password" required>
                <input type="hidden" name="action" value="creerUtilisateur">
                <button type="submit">S&apos;inscrire</button>
            </form>
        </c:if>
        <c:if test="${not empty sessionScope['login']}">
            <c:redirect url="index.jsp"></c:redirect>
        </c:if>
    </body>
</html>