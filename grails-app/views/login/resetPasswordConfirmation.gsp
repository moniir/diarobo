<%@ page import="com.diarobo.MasterKeySetup; com.diarobo.enums.MasterKeyValue" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reset Password Confirm</title>
    <meta name="layout" content="logintpl"/>
</head>
<body>
<div class="container">
    <div class="panel-start" id="panel-custom">
        <div id="registrationBox" class="col-md-5 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Reset Password confirmation</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body" >
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <div class="margin-bottom-small panel-padding-left">
                        <p>Your password has been reset!</p>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 text-center"><a href="${createLink(controller: 'login', action: 'auth')}">Sign in</a></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
