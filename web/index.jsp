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
                Vos identifiants sont erron&eacute;s.
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
        <c:if test="${sessionScope.message == 'achat'}">
            <div class="alert alert-success">
                Le morceau ${sessionScope.morceau} a bien &eacute;t&eacute; ajout&eacute; &agrave; votre compte.
            </div>
        </c:if>
        <c:remove var="message" scope="session"/>

        <c:import url="/WEB-INF/menu.jsp"></c:import>

        <div class="container disconnected">
            <div class="row">
                <h1 class="col-lg-offset-2" style="color: #843534; font-size: 10em;"><strong>Bienvenue</strong></h1>
            </div>
            <div class="row">
                <span class="glyphicon glyphicon-arrow-up col-lg-offset-5" style="font-size: 5em;"></span>
                <p class="col-lg-offset-1">
                    <h1>Vous pouvez rechercher des artistes, des chansons, des genres de musique
                        et des instruments dans la barre de recherche situ&eacute;e en haut de la page.</h1>
                </p>
            </div>
        </div>
    </body>
</html>
