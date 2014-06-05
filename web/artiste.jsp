<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <c:if test="${sessionScope.res.class != 'modeles.Artiste'}">
        <c:redirect url="index.jsp"></c:redirect>
    </c:if>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Artiste - ${sessionScope.res.nom}" />
    </c:import>    
    <body>
        <c:import url="/WEB-INF/menu.jsp"></c:import>
        <c:if test="${not empty sessionScope.res}">
            <div class="container">
                <div class="row disconnected">
                    <div class="col-lg-6">
                        <h1>Artiste : <strong>${sessionScope.res.nom}</strong></h1>
                    </div>
                </div>
                <div class="row">
                    <table class="table table-responsive table-striped">
                        <thead>
                        <td>Chanson</td>
                        <td>Genre</td>
                        <td>Nombre de pistes</td>
                        <td>Ann&eacute;e</td>
                        <td>URL</td>
                        </thead>
                        <tbody>
                            <c:forEach var="morceau" items="${sessionScope.res.morceaux}">
                                <tr>
                                    <td><a href="ServletResultatRecherche?t=Morceaux&q=${morceau.id}">${morceau.titre}</a></td>
                                    <td><a href="ServletResultatRecherche?t=Genres&q=${morceau.genre.id}">${morceau.genre.nom}</a></td>
                                    <td>${morceau.nbPistes}</td>
                                    <td>${morceau.annee}</td>
                                    <td>${morceau.url}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </c:if>
    </body>
</html>
