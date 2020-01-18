<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
<h2 class="sub-header">Patient List</h2>
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Phone Number</th>
            <th>Name</th>
            <th>Date of Birth</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${patientList}" var="patient">
            <tr>
                <td>${patient?.phoneNumber}</td>
                <td>${patient?.fullName}</td>
                <td>${patient?.dateOfBirth}</td>
                <td><a href="${createLink(controller: 'notificationScheduler', action: 'index', params: [patientUserName: patient?.phoneNumber, date: new Date().format('dd/MM/YYYY')])}"><i class="fa fa-bell" aria-hidden="true"></i></a>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
