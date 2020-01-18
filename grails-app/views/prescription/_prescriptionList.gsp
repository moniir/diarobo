<div class="row">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>No</th>
                <th>Date</th>
                <th>Doctor Name</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <tr ng-repeat="prescriptionElement in prescription.prescriptionList">
                <td class="{{prescriptionElement.activeStatus}}-STATUS">{{$index + 1}}</td>
                <td class="{{prescriptionElement.activeStatus}}-STATUS">{{prescriptionElement.prescriptionDate | date:"dd/MM/yyyy"}}</td>
                <td class="{{prescriptionElement.activeStatus}}-STATUS">{{prescriptionElement.doctorName}}</td>
                <td class="{{prescriptionElement.activeStatus}}-STATUS">{{prescriptionElement.activeStatus}}</td>
                <td class="{{prescriptionElement.activeStatus}}-STATUS">
                    <i class="fa fa-edit"></i>
                    <i class="fa fa-files-o" aria-hidden="true" ng-click="prescription.copyPrescription(prescriptionElement.id)"></i>
                    <i class="fa fa-print" data-toggle="modal" data-target="#prescriptionModal"></i>
                    <i class="fa fa-check" ng-click="prescription.setActive(prescriptionElement.id)"></i>
                </td>
            </tr>
        </tbody>
    </table>
</div>