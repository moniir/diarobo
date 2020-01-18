<!DOCTYPE html>
<html lang="en">
<head>
    <title>Cell Number Verification</title>
    <meta name="layout" content="logintpl"/>
</head>
<body>
<div class="container">
    <div class="panel-start" id="panel-custom">
        <div id="loginbox" class="col-md-5 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title  panel-title-left">Cell Number verification</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body" >
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <g:render template="/layouts/errors" bean="${cellNumberVerificationCommand?.errors}"/>
                    <form id="verifyPhone" class="form-horizontal" role="form" action="${createLink(controller: 'login', action: 'phoneVerification')}" method='POST'>
                        <div class="input-margin-bottom panel-padding-left">
                           <p>A text message with a verification code was just sent to ${cellNumberVerificationCommand.username}</p>
                        </div>
                        <div class="input-group input-margin-bottom ${hasErrors(bean: cellNumberVerificationCommand, field: 'username', 'has-error')}">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                            <input id="username" type="text" class="form-control" name="username"
                                   value="${cellNumberVerificationCommand.username}" placeholder="Cell Number">
                        </div>
                        <div class="input-group input-margin-bottom ${hasErrors(bean: cellNumberVerificationCommand, field: 'username', 'has-error')}">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="verificationCode" type="text" class="form-control" value="${cellNumberVerificationCommand.verificationCode}" name="verificationCode" placeholder="Enter a verification code">
                        </div>
                            <button class="btn btn-primary col-md-12 col-sm-12 col-xs-12" type="submit">Done</button>
                        <div>
                            <div class="col-md-12 col-sm-12 col-xs-12">Haven't received the code yet? <a href="javascript:void(0)" onclick="resendVerificationCode(${cellNumberVerificationCommand.username})">Resend code</a></div>
                        </div>
                    </form>
                    <script>
                        function resendVerificationCode(user) {
                            var userStr = '' + user;
                            var firstDigit = userStr.substring(0, 1);
                            if(firstDigit != '0') {
                                userStr = '0' + userStr;
                            }
                            <g:remoteFunction controller="login" action="reSendVerificationCode" params="{'username': userStr}"/>
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
