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
            <h1>Genre : <strong>${sessionScope.res.nom}</strong></h1>
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
            <c:if test="${not empty sessionScope.login}">
                <td>Acheter</td>
            </c:if>
            </thead>
            <tbody>
                <c:forEach var="morceau" items="${sessionScope.morceaux}">
                    <tr>
                        <td><a href="ServletResultatRecherche?t=Morceaux&q=${morceau.id}">${morceau.titre}</a></td>
                        <td><a href="ServletResultatRecherche?t=Artistes&q=${morceau.artiste.id}">${morceau.artiste.nom}</a></td>
                        <td>${morceau.annee}</td>
                        <td>${morceau.nbPistes}</td>
                        <td><a href="${morceau.url}" target="_blank">Wikip&eacute;dia</td>
                        <c:if test="${not empty sessionScope.login}">
                            <td>
                                <c:if test="${sessionScope.achats.contains(morceau)}">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </c:if>
                                <c:if test="${!sessionScope.achats.contains(morceau)}">
                                    <a href="ServletAchat?idM=${morceau.id}"><span class="glyphicon glyphicon-euro"></span></a>
                                    </c:if>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <c:set var="previous" value=""></c:set>
        <c:set var="next" value=""></c:set>

        <c:if test="${empty sessionScope.page || sessionScope.page == 1}">
            <c:set var="previous" value="disabled"></c:set>
        </c:if>
        <c:if test="${sessionScope.morceaux.size() < 10}">
            <c:set var="next" value="disabled"></c:set>
        </c:if>
        <ul class="pager">
            <c:if test="${previous == 'disabled'}">
                <li class="previous disabled">
                    <a href="#">
                        Page pr&eacute;c&eacute;dente
                    </a>
                </li>
            </c:if>
            <c:if test="${empty previous}">
                <li class="previous">
                    <a href="ServletResultatRecherche?t=Genres&q=${sessionScope.res.id}&page=${sessionScope.page - 1}">
                        &larr; Page pr&eacute;c&eacute;dente
                    </a>
                </li>
            </c:if>
            <c:if test="${next == 'disabled'}">
                <li class="next disabled">
                    <a href="#">
                        Page suivante
                    </a>
                </li>
            </c:if>
            <c:if test="${empty next}">
                <li class="next">
                    <a href="ServletResultatRecherche?t=Genres&q=${sessionScope.res.id}&page=${sessionScope.page + 1}">
                        Page suivante &rarr;
                    </a>
                </li>
            </c:if>
        </ul>

    </div>
</div>