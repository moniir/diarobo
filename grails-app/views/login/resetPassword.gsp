<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reset Password</title>
    <meta name="layout" content="logintpl"/>
</head>
<body>
<div class="container">
    <div class="panel-start" id="panel-custom">
        <div id="loginbox" class="col-md-5 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Reset Password</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body" >
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <g:render template="/layouts/errors" bean="${resetPasswordCommand?.errors}"/>
                    <form id="resetPassword" class="form-horizontal" role="form" action='${createLink(controller: 'login', action: 'resetPassword')}' method='POST'>
                        <g:if test="${resetPasswordCommand != null}">
                            <g:hiddenField name="username" value="${resetPasswordCommand?.username}"/>
                        </g:if>
                        <g:else>
                            <g:hiddenField name="username" value="${username}"/>
                        </g:else>

                        <div class="col-md-12">
                            <div class="input-group input-margin-bottom ${hasErrors(bean: resetPasswordCommand, field: 'verificationCode', 'has-error')}">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input id="verificationCode" type="password" class="form-control" value="${resetPasswordCommand?.verificationCode}" name="verificationCode" placeholder="Verification Code">
                            </div>
                            <div class="input-group input-margin-bottom ${hasErrors(bean: resetPasswordCommand, field: 'newPassword', 'has-error')}">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input id="newPassword" type="password" class="form-control" value="${resetPasswordCommand?.newPassword}" name="newPassword" placeholder="New Password">
                            </div>
                            <div class="input-group input-margin-bottom ${hasErrors(bean: resetPasswordCommand, field: 'confirmNewPassword', 'has-error')}">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input id="confirmNewPassword" type="password" class="form-control" value="${resetPasswordCommand?.confirmNewPassword}" name="confirmNewPassword" placeholder="Confirm New Password">
                            </div>
                            <button class="btn btn-primary col-md-12 col-sm-12 col-xs-12" type="submit">Reset password</button>
                            <div class="col-md-12 col-sm-12 col-xs-12">Haven't received the code yet? <a href="javascript:void(0)" onclick="resendVerificationCode(${resetPasswordCommand?.username})">Resend code</a></div>
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
