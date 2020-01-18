<%@ page import="com.diarobo.MasterKeySetup; com.diarobo.enums.MasterKeyValue" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
    <ol class="breadcrumb">
        <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
        <li><a href="${createLink(controller: 'history', action: 'educationalStatus')}">Educational Status</a></li>
        <li class="active">Create</li>
    </ol>
    <div class="row">
        <div class="col-md-12">
            <g:if test='${flash.message}'>
                <div class="errorHandler alert alert-danger">
                    <i class="fa fa-remove-sign"></i>
                    ${flash.message}
                </div>
            </g:if>
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Educational Status</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <g:form controller="history" method="POST">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFive"></div>
                    <div class="panel-body padding-top-form">
                        <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                        <g:select name="highestEducation"
                                  from="${educationList}"
                                  value="${highestEducation}"
                                  optionKey="id" optionValue="name"
                                  noSelection="${['null':'Select Highest Education']}"
                                  class="form-control"/>
                             <br>
                        </div>
                    </div>
                </div>
                <div class="form-group text-center">
                        <g:actionSubmit value="Save" class="btn btn-default cancel-btn" action="saveEducationalStatus" tabindex="7"></g:actionSubmit>
                        <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Save & Next</button>
                </div>
                </g:form>
            </div>
        </div>
    </div>
    <script>
        jQuery(function ($) {

        });
    </script>
</body>
</html>