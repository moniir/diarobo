<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="layout" content="logintpl"/>
    <meta name="google-signin-client_id" content="269148155663-evtuijm4dduk1onv8tti24vi8619jdfk.apps.googleusercontent.com">
    <script>
        var profile;
        var firstTime = true;
        var thirthPartyCookieSupport = false;
        var receiveMessage = function (evt) {
            if (evt.data === 'MM:3PCunsupported') {
                thirthPartyCookieSupport = false;
            } else if (evt.data === 'MM:3PCsupported') {
                thirthPartyCookieSupport = true;
            }
        };
        window.addEventListener("message", receiveMessage, false);
        function onSignIn(googleUser) {
            profile = googleUser.getBasicProfile();
            if(!firstTime && profile) {
                var userAccountId = profile.getId();
                var providerName = 'GOOGLE';
                if(userAccountId && providerName) {
                    loginWithProvider(userAccountId, providerName);
                } else {
                    alert('Something error. Please try again');
                }
            }
        }
        function googleLogin() {
            firstTime = false;
            if(!thirthPartyCookieSupport) {
                alert('You have to enable third-party cookies and site data for google login.')
            } else if(profile){
                var providerName = 'GOOGLE';
                var userAccountId = profile.getId();
                loginWithProvider(userAccountId, providerName);
            }
        }
        //facebook login
        // initialize and setup facebook js sdk
        window.fbAsyncInit = function() {
            FB.init({
                appId      : '758725804280459',
                xfbml      : true,
                cookie     : true,
                version    : 'v2.5'
            });
        };
        (function(d, s, id){
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {return;}
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));

        // login with facebook with extra permissions
        function loginFacebook() {
            FB.login(function(response) {
                if (response.status === 'connected') {
                    getFacebookInfo();
                } else if (response.status === 'not_authorized') {
                    alert('We are not logged in.');
                } else {
                    alert('You are not logged into Facebook.');
                }
            }, {scope: 'email'});
        }
        // getting basic user info
        function getFacebookInfo() {
            FB.api('/me', 'GET', {fields: 'first_name,last_name,name,id'}, function(response) {
                var nickName = response.first_name;
                var fullName = response.name;
                var userAccountId = response.id;
                var providerName = 'FACEBOOK';
                if(nickName && fullName && userAccountId && providerName) {
                    loginWithProvider(userAccountId, providerName);
                } else {
                    alert('Something error. Please try again');
                }
            });
        }
        function loginWithProvider(userAccountId, providerName) {
            window.location.href= "${createLink(controller: 'login', action: 'facebookGoogleLogin')}?userAccountId=" + userAccountId + '&providerName=' + providerName;
        }
    </script>
</head>
<body>
<div class="container">
    <div class="panel-start" id="panel-custom">
        <div id="loginbox" class="col-md-5 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Login</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body" >
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <form id="loginform" class="form-horizontal" role="form" action='${postUrl}' method='POST' id='loginForm'>
                        <div class="margin-bottom-small panel-padding-left">
                            <div class="g-signin2" data-onsuccess="onSignIn" onclick="googleLogin()"></div>
                        </div>
                        <div class="margin-bottom-small panel-padding-left" onclick="loginFacebook()">
                            <i class="fa fa-facebook-square fa-2x" aria-hidden="true"></i> Sign in with Facebook
                        </div>
                        <h4 class="text-center">OR</h4>
                        <div class="input-group input-margin-bottom">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                            <input id="username" type="text" class="form-control" name="username" value="" placeholder="Mobile Number">
                        </div>
                        <div class="input-group input-margin-bottom">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="password" type="password" class="form-control" name="password" placeholder="password">
                        </div>
                            <button class="btn btn-primary col-md-12 col-sm-12 col-xs-12" type="submit">Login</button>
                        <div>
                            <div class="col-md-6 col-sm-6 col-xs-6"><a href="${createLink(action: 'forgetPassword')}">Forgot password?</a></div>
                            <div class="col-md-6 col-sm-6 col-xs-6"><a class="floatRight" href="${createLink(action: 'registration')}">Register now</a></div>
                        </div>
                    </form>
                </div>
                <iframe src="https://mindmup.github.io/3rdpartycookiecheck/start.html" style="display:none" />
            </div>
        </div>
    </div>
</div>
</body>
</html>
