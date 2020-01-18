<div ng-controller="basicInformationController as basicInformation">
    <div class="row" ng-form>
        <div class="col-md-3">
            <span>Doctor Name</span>
        </div>
        <div class="col-md-9">
            <div class="form-group">
                <input type="text" class="form-control typeahead" placeholder="Doctor Name" id="doctorName" ng-model="basicInformation.doctorName">
            </div>
        </div>
        <div class="col-md-3">
            <span>Phone/Cell Number</span>
        </div>
        <div class="col-md-9">
            <div class="form-group">
                <input type="text" class="form-control typeahead" placeholder="Phone/Cell Number" id="doctorPhoneNumber" ng-model="basicInformation.doctorPhoneNumber">
            </div>
        </div>
        <div class="col-md-3">
            <span>Designation</span>
        </div>
        <div class="col-md-9">
            <div class="form-group">
                <input type="text" ng-model="basicInformation.doctorDesignation" id="doctorDesignation" class="typeahead form-control" placeholder="Designation">
            </div>
        </div>
        <div class="col-md-3">
            <span>Hospital/Chamber </span>
        </div>
        <div class="col-md-9">
            <div class="form-group">
                <input type="text" class="typeahead form-control" id="doctorHospitalChamber" ng-model="basicInformation.doctorHospitalChamber" placeholder="Hospital/Chamber">
            </div>
        </div>
        <div class="col-md-3">
            <span>Patient Name</span>
        </div>
        <div class="col-md-9">
            <div class="form-group">
                <input type="text" class="form-control typeahead" id="patientNameOrMobile" ng-blur="basicInformation.setPatient()" ng-model="basicInformation.patientNameOrMobile" placeholder="Patient Name">
            </div>
        </div>
        <div class="col-md-3">
        </div>
        <div class="col-md-1">
            <span>Weight</span>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <input type="text" class="form-control" ng-model="basicInformation.patientWeight" placeholder="Weight">
            </div>
        </div>
        <div class="col-md-1">
            <span>BMI</span>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <input type="text" class="form-control" ng-model="basicInformation.patientBmi" placeholder="BMI">
            </div>
        </div>
        <div class="col-md-1">
            <span>BP</span>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <input type="text" class="form-control" ng-model="basicInformation.patientBp" ng-blur="basicInformation.setBp()" placeholder="BP">
            </div>
        </div>
        <div class="form-group text-center">
            <button class="btn btn-default" tabindex="7" ng-click="basicInformation.saveBasicInfo()" >Save</button>
            <button class="btn btn-default" tabindex="7">Save & Next</button>
        </div>
    </div>
</div>