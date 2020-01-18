<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Notification</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li class="active">Notification List</li>
</ol>
<div class="row">
    <div class="col-md-12 inLine-block list-header">
        <div class="floatLeft">
            <g:form controller="notificationScheduler" action="index" class="inLine-block navbar-form navbar-right" method="GET">
                <div class="form-group inLine-block">
                    <div class="inLine-block">
                        <g:textField name="patientUserName" value="${patientUserName}" type="text" class="form-control hidden" id="patientUserName"/>
                        <input type="text" value="${patientUserNameMobile}" class="form-control typeahead" id="patientNameOrMobile" placeholder="Search Patient">
                    </div>
                    <div class='input-group date inLine-block' style="padding-top: 11px">
                        <input type='text' class="form-control" name="date" value="${selectedDate?.format('dd/MM/yyyy')}"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-time"></span>
                        </span>
                    </div>
                    <div class="inLine-block">
                        <g:select name="type" class="form-control" style="margin-top: 11px" from="${['Food', 'Medicine', 'Exercise']}" keys="${['food_library', 'medicine_library', 'exercise_library']}" value="${type}"
                                  noSelection="['':'-Select Library-']"/>
                    </div>
                </div>
                <g:actionSubmit value="Search" class="btn btn-default" style="margin-top: 11px" action="index"></g:actionSubmit>
            </g:form>
        </div>
    </div>
    <div class="col-md-12">
        <div class="floatRight pagination topBottomNoPadding">
            <g:paginate total="${reminderCount ?: 0}" max="20" params="[patientUserName: patientUserName, date: selectedDate?.format('dd/MM/yyyy')]"/>
        </div>
    </div>
</div>
<div class="padding-top-form">
    <g:render template="notificationList" model="[reminderList: reminderList, exercisePackageList: exercisePackageList]"></g:render>

</div>
<script>
    $(function () {
        autoSearch.searchPatient($('#patientNameOrMobile'), $('#patientUserName'), false);
        $('.date').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });
</script>
</body>
</html>