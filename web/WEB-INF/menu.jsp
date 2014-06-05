<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="menu">
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp">Accueil</a>
            </div>
            <div class="navbar-collapse collapse">
                <div class="row">
                    <div class="col-lg-5 menu-inscription" role="search">
                        <input type="text" onfocus="showData(this.value);" onblur="$('#rechercheResults').hide();" id="recherche" class="form-control" placeholder="Rechercher un artiste, une musique, un instrument ou un genre">
                        <select id="rechercheResults" class="form-control" onchange="searchResultSelected();" onfocus="$(this).show();" onblur="$(this).hide();" style="display:none;">
                        </select>
                    </div>

                    <c:if test="${not empty sessionScope.login}">
                        <h4 class="col-lg-2 welcome">Bonjour ${sessionScope.login}.</h4>
                        <span class="menu-inscription">
                            <a class="col-lg-2 btn btn-default" href="ServletAbos" >Changer d&apos;abonnement</a>
                        </span>
                        <form action="Signout" method="post">
                            <input type="hidden" name="action" value="signout">
                            <span class="menu-inscription">
                                <button class="btn btn-success col-lg-2" type="submit">D&eacute;connexion</button>
                            </span>
                        </form>
                    </c:if>
                    <c:if test="${empty sessionScope.login}">
                        <div >
                            <c:import url="/WEB-INF/signin.jsp"></c:import>
                                <span class="menu-inscription col-lg-3">
                                    <a href="signup.jsp" class="btn btn-info">Inscription</a>
                                    <a href="ServletAbos" class="btn btn-default">Voir les abonnements</a>
                                </span>
                            </div>
                    </c:if>
                </div>
            </div>
        </div>

    </div>

</div>