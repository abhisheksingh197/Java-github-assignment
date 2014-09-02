<!DOCTYPE html>
<html lang="en">
    <head><title>Custom Login Page</title></head>
    <body>
        <form method="post" action="/login">
            <fieldset>
                <legend>Please Login</legend>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>        
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
                <div class="form-actions">
                    <button type="submit" class="btn">Log in</button>
                </div>
            </fieldset>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </body>
</html>
