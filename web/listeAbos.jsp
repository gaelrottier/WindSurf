<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Choix d&apos;un abonnement"></c:param>
    </c:import>
    <body>
        <c:if test="${sessionScope['message'] == 'erreur'}">
            <div class="alert alert-danger">
                Une erreur est survenue.
            </div>
        </c:if>
        <c:if test="${sessionScope['message'] == 'aboUpdated'}">
            <div class="alert alert-success">
                Votre abonnement a &eacute;t&eacute; mis &agrave; jour.
            </div>
        </c:if>
        <c:remove var="message" scope="session"/>

        <c:import url="/WEB-INF/menu.jsp"></c:import>
        <c:if test="${empty sessionScope['listeAbos']}">
            <c:redirect url="ServletAbos"></c:redirect>
        </c:if>
        <c:if test="${not empty sessionScope['userAbo']}">
            <div class="row">
                <div class="col-lg-2 col-lg-offset-1"><br>
                    <p>
                        <h3>Votre abonnement : </h3><h2><c:out value="${sessionScope.userAbo.nom}"></c:out></h2>
                    </p>
                </div>
            </div>
        </c:if>

        <div class="row">
            <div class="col-lg-1"></div>
            <c:forEach var="a" items="${sessionScope['listeAbos']}">
                <form action="ServletAbos" method="post">
                    <button class="col-lg-2">
                        <h1>${a.nom}</h1>
                        <h1>${a.prix}<span class="glyphicon glyphicon-euro"></span></h1>
                    </button>
                    <input type="hidden" name="action" value="choixAbo">
                    <input type="hidden" name="abo" value="${a.id}">
                </form>
            </c:forEach>
        </div>

    </body>
</html>