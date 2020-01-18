<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
    <body>
        <ol class="breadcrumb">
            <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
            <li><a href="${createLink(controller: 'exercise', action: 'index')}">Exercise</a></li>
            <li class="active">Create</li>
        </ol>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                    <div class="panel-title panel-title-list">Exercise Create
                        <span class="pull-right"><a title="Back" href="${createLink(controller: 'exercise', action: 'index')}"><i class="fa fa-reply" aria-hidden="true"></i></a></span>
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
                                <g:render template="form" model="[measurementUnits: measurementUnits, exerciseTypes: exerciseTypes]"></g:render>
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="submit"></label>
                                    <div class="col-md-8">
                                        <button id="submit" onclick="saveExercise('create');" name="submit" class="btn btn-primary">Submit</button>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
