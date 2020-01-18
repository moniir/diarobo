<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'medicine', action: 'index')}">Medicine</a></li>
    <li class="active">Create</li>
</ol>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title panel-title-list">Medicine Details
                    <span class="pull-right"><a title="Back" href="${createLink(controller: 'medicine', action: 'index')}">
                    <i class="fa fa-reply" aria-hidden="true"></i></a></span>
                </div>
            </div>
            <div class="panel-body">
                <div class="col-md-12 col-sm-12">
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <g:form controller="medicine" action="update" method="POST" enctype="multipart/form-data">
                        <fieldset class="form-horizontal">
                        <g:render template="form" model="[medicineAdminList: medicineAdminList, brand: brand, type: 'edit']"></g:render>
                        <div class="form-group">
                            <label class="col-md-4 control-label"></label>
                            <div class="col-md-8">
                                <g:actionSubmit value="Update" action="update" class="btn btn-primary"></g:actionSubmit>
                            </div>
                        </div>
                        </fieldset>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
