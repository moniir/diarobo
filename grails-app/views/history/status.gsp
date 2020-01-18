<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
<body>
    <ol class="breadcrumb">
        <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
        <li><a href="${createLink(controller: 'history', action: 'diabetic')}">Profile Status</a></li>
        <li class="active">List</li>
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
                        <div class="panel-title panel-title-left">Profile Status</div>
                        <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                    </div>
                <div class="panel-body padding-top-form">
                    <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                        <div class="row">
                            <div class="col-md-12">
                                <i class="fa fa-user" aria-hidden="true"></i> Patient/caregiver name
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-5">
                                    Basic Profile:   (75%)
                                </div>
                                <div class="col-md-7">
                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:75%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-5">
                                    Caregiver’ Profile (50%)
                                </div>
                                <div class="col-md-7">
                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:50%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-5">
                                    Emergency Contact (0%)
                                </div>
                                <div class="col-md-7">
                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:0%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-5">
                                    History (45%)
                                </div>
                                <div class="col-md-7">
                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:45%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-5">
                                    Diabetic History (75%)
                                </div>
                                <div class="col-md-7">
                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:75%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
           </div>
        </div>
    </div>
        <script>
            jQuery(function ($) {
                $("#startDate").datepicker({
                    format: 'dd/mm/yyyy',
                    autoclose: true
                })
                $("#endDate").datepicker({
                    format: 'dd/mm/yyyy',
                    autoclose: true
                })
                });
        </script>
    </body>
</html>
