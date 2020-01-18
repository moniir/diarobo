<!DOCTYPE html>
<html lang="en">
<head>
    <title>Forget Password</title>
    <meta name="layout" content="logintpl"/>
</head>
<body>
<div class="container">
    <div class="panel-start" id="panel-custom">
        <div id="loginbox" class="col-md-5 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Forget Password</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body" >
                    <div class="input-margin-bottom"></div>
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <form id="forgetPassword" class="form-horizontal" role="form" action='${createLink(controller: 'login', action: 'forgetPassword')}' method='POST'>
                        <div class="col-md-12">
                            <div class="input-group input-margin-bottom">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input id="username" type="text" class="form-control" name="username" value="" placeholder="Mobile Number">
                            </div>
                            <button class="btn btn-primary col-md-12 col-sm-12 col-xs-12" type="submit">Request password reset</button>
                            <div>
                                <div class="col-md-6 col-sm-6 col-xs-6"><a href="${createLink(action: 'auth')}">Sign in</a></div>
                                <div class="col-md-6 col-sm-6 col-xs-6"><a class="floatRight" href="${createLink(action: 'registration')}">Register now</a></div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
