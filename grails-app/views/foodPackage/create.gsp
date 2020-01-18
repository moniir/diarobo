<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Food Package</title>
    <asset:javascript src="angularjs/foodPackage.js"/>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'foodPackage', action: 'index')}">FoodPackage</a></li>
    <li class="active">Create</li>
</ol>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title panel-title-list">FoodPackage` Details
                    <span class="pull-right"><a title="Back" href="${createLink(controller: 'foodPackage', action: 'index')}">
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
                        <fieldset class="form-horizontal">
                            <g:render template="form" model="[foodPackageId: foodPackageId, packageName: packageName, foodPackageElementList: foodPackageElementList]"></g:render>
                        </fieldset>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
