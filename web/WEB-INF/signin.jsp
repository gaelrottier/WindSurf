<form class="col-lg-3" action="Signin" method="post">
    <div class="form-group">
        <input type="text" name="login" placeholder="Nom d&apos;utilisateur" class="form-control" required>
        <input type="password" name="password" placeholder="Mot de passe" class="form-control" required>
    </div>
    <input type="hidden" name="action" value="connect">
    <button type="submit" class="btn btn-success">Connexion</button>
</form>