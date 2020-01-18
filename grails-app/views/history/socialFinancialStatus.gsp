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
    <li><a href="${createLink(controller: 'history', action: 'socialFinancialStatus')}">Social/financial Status</a></li>
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
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title panel-title-left">Social/financial Status</div>
                <div class="panel-title-right"><a href="${createLink(uri: '/')}"
                                                  target="_self">${message(code: 'app.project.name.title')}</a></div>
            </div>
            <g:form controller="history" action="saveSocialFinancialStatus" method="POST">
                <div class="panel panel-default">
                    <div class="panel-body padding-top-form">
                        <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                            <g:each in="${MasterKeySetup.findAllByKeyType(MasterKeyValue.FINANCIAL_STATUS.value)}" var="socialFinancial">
                                <div class="radio">
                                    <label>
                                        <g:if test="${socialFinancialStatus}">
                                            <g:if test="${socialFinancialStatus == socialFinancial?.id}">
                                                <input type="radio" name="socialFinancial" value="${socialFinancial?.id}" checked="checked"> Monthly income: ${socialFinancial?.keyValue}
                                            </g:if>
                                            <g:else>
                                                <input type="radio" name="socialFinancial" value="${socialFinancial?.id}"> Monthly income: ${socialFinancial?.keyValue}
                                            </g:else>
                                        </g:if>
                                        <g:else>
                                            <input type="radio" name="socialFinancial" value="${socialFinancial?.id}"> Monthly income: ${socialFinancial?.keyValue}
                                        </g:else>
                                    </label>
                                </div>
                            </g:each>
                        </div>
                    </div>
                </div>

                <div class="form-group text-center">
                    <g:actionSubmit value="Save" action="saveSocialFinancialStatus" class="btn btn-default cancel-btn" tabindex="7"></g:actionSubmit>
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