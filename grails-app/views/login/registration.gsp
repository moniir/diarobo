<%@ page import="com.diarobo.MasterKeySetup; com.diarobo.enums.MasterKeyValue" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Registration</title>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="layout" content="logintpl"/>
    <meta name="google-signin-client_id" content="269148155663-evtuijm4dduk1onv8tti24vi8619jdfk.apps.googleusercontent.com">
</head>
<body>
<script>
    var profile = null;
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
        if(profile == null && !firstTime) {
            profile = googleUser.getBasicProfile();
            var nickName =  profile.getName().split(' ')[0];
            var fullName =  profile.getName();
            var userAccountId = profile.getId();
            var providerName = 'GOOGLE';
            if(nickName && fullName && userAccountId && providerName) {
                registrationWithProvider(nickName, fullName, userAccountId, providerName);
            } else {
                alert('Something error. Please try again');
            }
            firstTime = false;
        }
    }
    function googleLogin() {
            if(!thirthPartyCookieSupport) {
                alert('You have to enable third-party cookies and site data for google login.')
            } else if(profile && !firstTime){
                var nickName =  profile.getName().split(' ')[0];
                var fullName =  profile.getName();
                var userAccountId = profile.getId();
                var providerName = 'GOOGLE';
                if(nickName && fullName && userAccountId && providerName) {
                    registrationWithProvider(nickName, fullName, userAccountId, providerName);
                } else {
                    alert('Something error. Please try again');
                }
            }
        firstTime = false;
    }
    //facebook registration
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
                alert('You are not logged in.');
            } else {
                alert('You are not logged into Facebook.');
            }
        }, {scope: 'email'});
    }


    function registrationWithProvider(nickName, fullName, userAccountId, providerName) {
        $.post("${createLink(controller: 'login', action: 'facebookGoogleRegistration')}", {nickName: nickName, fullName: fullName, userAccountId: userAccountId, providerName: providerName}, function(result){
            if(result.hasError == true) {
                alert(result.message);
            } else {
                modal('Registration: More Info', result, yesFunction, noFunction, '');
            }
        });
    }

    function yesFunction() {
    }

    function noFunction() {
    }


    // getting basic user info
    function getFacebookInfo() {
        FB.api('/me', 'GET', {fields: 'first_name,last_name,name,id'}, function(response) {
            var nickName = response.first_name;
            var fullName = response.name;
            var userAccountId = response.id;
            var providerName = 'FACEBOOK';
            if(nickName && fullName && userAccountId && providerName) {
                registrationWithProvider(nickName, fullName, userAccountId, providerName);
            } else {
                alert('Something error. Please try again');
            }
        });
    }
</script>
<div class="container">
    <div class="panel-start" id="panel-custom">
        <div id="registrationBox" class="col-md-5 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Registration</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body" >
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <g:render template="/layouts/errors" bean="${userCommand?.errors}"/>
                    <form class="form-horizontal" role="form" action="${createLink(controller: 'login', action: 'registration')}" method="POST">
                        <div class="margin-bottom-small panel-padding-left">
                            <div class="g-signin2" data-onsuccess="onSignIn" onclick="googleLogin()"></div>
                        </div>
                        <div class="margin-bottom-small panel-padding-left" onclick="loginFacebook()">
                            <i class="fa fa-facebook-square fa-2x" aria-hidden="true"></i> Register with Facebook
                        </div>
                        <h4 class="text-center">OR</h4>
                        <div class="input-group input-margin-bottom ${hasErrors(bean: userCommand, field: 'nickName', 'has-error')}">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" class="form-control" name="nickName" value="${userCommand?.nickName}" placeholder="Nick Name">
                        </div>
                        <div class="input-group input-margin-bottom ${hasErrors(bean: userCommand, field: 'fullName', 'has-error')}">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" class="form-control" name="fullName" value="${userCommand?.fullName}" placeholder="Full Name">
                        </div>
                        <div class="input-group input-margin-bottom ${hasErrors(bean: userCommand, field: 'username', 'has-error')}">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                            <input id="username" type="text" class="form-control" name="username" value="${userCommand?.username}" placeholder="Mobile Number">
                        </div>
                        <div class="form-group ${hasErrors(bean: userCommand, field: 'userType', 'has-error')}">
                            <label class="panel-padding-left">AS</label>
                            <g:each in="${userTypes}" var="userType">
                                <div class="radio-inline">
                                    <label><input type="radio" name="userType" value="${userType.id}"
                                        <g:if test="${userCommand?.userType == userType}">checked="checked"</g:if>>${userType.name}</label>
                                </div>
                            </g:each>
                        </div>
                            <button type="submit" class="btn btn-primary col-md-12 col-sm-12 col-xs-12">Register Now</button>
                        <div>
                            <div class="col-md-12 col-sm-12 col-xs-12 text-center"><a href="${createLink(action: 'auth')}">Sign in</a></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<g:render template="/layouts/modal"></g:render>
<iframe src="https://mindmup.github.io/3rdpartycookiecheck/start.html" style="display:none" />
</body>
</html>
