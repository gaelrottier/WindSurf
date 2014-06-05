<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Accueil" />
    </c:import>    
    <body>
        <c:if test="${sessionScope.message == 'inscrit'}">
            <div class="alert alert-success">
                Votre inscription a &eacute;t&eacute; valid&eacute;e.
            </div>
        </c:if>
        <c:if test="${sessionScope.message == 'badIds'}">
            <div class="alert alert-danger">
                Vos indentifiants sont erron&eacute;s.
            </div>
        </c:if>
        <c:if test="${sessionScope.message == 'connecte'}">
            <div class="alert alert-success">
                Vous &ecirc;tes  connect&eacute;.
            </div>
        </c:if>
        <c:if test="${sessionScope.message == 'deco'}">
            <div class="alert alert-info disconnected">
                Vous avez &eacute;t&eacute; d&eacute;connect&eacute;.
            </div>
        </c:if>
        <c:remove var="message" scope="session"/>

        <c:import url="/WEB-INF/menu.jsp"></c:import>
    </body>
</html>
