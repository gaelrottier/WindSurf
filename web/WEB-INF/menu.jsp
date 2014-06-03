<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">Accueil</a>
        </div>
        <div class="navbar-collapse collapse">
            <div class="row">
                <c:if test="${not empty sessionScope.login}">
                    <h4 class="col-lg-2 col-lg-offset-5 welcome">Bonjour ${sessionScope.login}.</h4>
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
                    <div class="pull-right">
                        <c:import url="/WEB-INF/signin.jsp"></c:import>
                            <span class="menu-inscription">
                                <a href="signup.jsp" class="btn btn-info">Inscription</a>
                                <a href="ServletAbos" class="btn btn-default">Voir les abonnements</a>
                            </span>
                        </div>
                </c:if>
            </div>
        </div>
    </div>
</div>