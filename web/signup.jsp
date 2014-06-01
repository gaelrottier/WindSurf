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
            <!-- G&eacute;n&eacute;r&eacute; via http://bootsnip.com/forms?version=3 !-->
            <form class="form-horizontal" action="ServletUsers" method="post">
                <fieldset>

                    <!-- Form Name -->
                    <legend class="col-md-offset-3 col-md-6">Inscription</legend>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="login">Nom d&apos;utilisateur</label>  
                        <div class="col-md-4">
                            <input id="login" name="login" type="text" placeholder="Entrez un nom d&apos;utilisateur" class="form-control input-md" required="">
                        </div>
                    </div>

                    <!-- Password input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="password">Mot de passe</label>
                        <div class="col-md-4">
                            <input id="password" name="password" type="password" placeholder="Entrez un mot de passe" class="form-control input-md" required="">
                        </div>
                    </div>

                    <!-- Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for=""></label>
                        <div class="col-md-4">
                            <button class="btn btn-success">Valider</button>
                        </div>
                    </div>
                    <input type="hidden" name="action" value="creerUtilisateur">
                </fieldset>
            </form>
        </c:if>
        <c:if test="${not empty sessionScope['login']}">
            <c:redirect url="index.jsp"></c:redirect>
        </c:if>
    </body>
</html>