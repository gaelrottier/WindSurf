<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
    <c:if test="${empty sessionScope.login}">
        <c:set var="disco" value="disconnected"></c:set>
    </c:if>
    <c:if test="${not empty sessionScope.login}">
        <c:set var="disco" value=""></c:set>
    </c:if>
    <div class="row ${disco}">
        <div class="col-lg-6">
            <h1>Instrument : <strong>${sessionScope.res.nom}</strong></h1>
        </div>
        <div class="col-lg-6">
            <h3>Cette page affiche les morceaux de musique poss&eacute;dant au moins
                une piste correspondant &agrave; l&apos;instrument cherch&eacute;</h3>
        </div>
    </div>
    <div class="row">
        <table class="table table-responsive table-striped">
            <thead>
            <td>Morceau</td>
            <td>Artiste</td>
            <td>Ann&eacute;e</td>
            <td>Nombre de pistes</td>
            <td>URL</td>
            </thead>
            <tbody>
                <c:forEach var="morceau" items="${sessionScope.res.morceaux}">
                    <tr>
                        <td><a href="ServletResultatRecherche?t=Morceaux&q=${morceau.id}">${morceau.titre}</a></td>
                        <td><a href="ServletResultatRecherche?t=Artistes&q=${morceau.artiste.id}">${morceau.artiste.nom}</a></td>
                        <td>${morceau.annee}</td>
                        <td>${morceau.nbPistes}</td>
                        <td><a href="${morceau.url}" target="_blank">Wikip&eacute;dia</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
</div>