<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <c:if test="${empty sessionScope.res}">
        <c:redirect url="index.jsp"></c:redirect>
    </c:if>
    <c:if test="${sessionScope.res.class != 'class modeles.Morceau'}">
        <c:redirect url="index.jsp"></c:redirect>
    </c:if>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Chanson - ${sessionScope.res.titre}" />
    </c:import>
    <body>
        <c:import url="/WEB-INF/menu.jsp">
        </c:import>
        <div class="container">
            <c:if test="${empty sessionScope.login}">
                <c:set var="disco" value="disconnected"></c:set>
            </c:if>
            <c:if test="${not empty sessionScope.login}">
                <c:set var="disco" value=""></c:set>
            </c:if>
            <div class="row ${disco}">
                <div class="col-lg-3">
                    <h1>Artiste : <strong>${sessionScope.res.artiste.nom}</strong></h1>
                </div>
                <div class="col-lg-6">
                    <h1>Chanson : <strong>${sessionScope.res.titre}</strong></h1>
                </div>
                <div class="col-lg-2">
                    <h3>Genre : <strong><a href="ServletResultatRecherche?t=Genres&q=${sessionScope.res.genre.id}">${sessionScope.res.genre.nom}</a></h3>
                </div>
                <div class="col-lg-1">
                    <h4>${sessionScope.res.nbPistes} pistes</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3">
                    <h3>Ann&eacute;e : <strong>${sessionScope.res.annee}</strong></h3>
                </div>
                <div class="col-lg-3">
                    <h4><a href="${sessionScope.res.url}" target="_blank">Lien de la chanson</a></h4>
                </div>
            </div>
            <div class="row">
                <table class="table table-striped">
                    <thead>
                    <td>Piste n°</td>
                    <td>Instrument</td>
                    <td>Nom de la piste</td>
                    </thead>
                    <tbody>
                        <c:forEach var="piste" items="${sessionScope.res.pistes}">
                            <tr>
                                <td>${piste.num}</a></td>
                                 <td><a href="ServletResultatRecherche?t=Instruments&q=${piste.instrument.id}">${piste.instrument.nom}</a></td>
                                <td>${piste.nom}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>

    </body>
</html>