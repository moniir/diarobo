<div id="prescriptionModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content prescription-preview-body">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h2 class="modal-title">Prescription Preview</h2>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-6">
                            <div class="row"><h3>{{prescription.patientNameOrMobile}}</h3></div>
                            <div class="row">{{prescription.patientAge ? 'Age: ' + prescription.patientAge + ', ' : ''}} {{prescription.patientWeight ? ' Weight: ' + prescription.patientWeight + ', ' : ''}} {{prescription.patientGender ? ' Gender: ' + prescription.patientGender + ', ' : ''}}</div>
                            <div class="row">{{prescription.patientBmi ? 'BMI: ' + prescription.patientBmi + ', ' : ''}} {{prescription.patientBp ? ' BP: ' + prescription.patientBp + ', ' : ''}} {{prescription.patientDiabeticType ? 'Diabetic Type: ' + prescription.patientDiabeticType + ', ': ''}}</div>
                            <div class="row">{{prescription.prescriptionDate ? 'Date: ' : ''}} {{prescription.prescriptionDate | date:'dd/MM/yyyy'}}</div>
                        </div>
                        <div class="col-md-6">
                            <div class="row"><h3 class="floatRight">{{prescription.doctorName}}</h3></div>
                            <div class="row"><span class="floatRight">{{prescription.doctorDesignation}}</span></div>
                            <div class="row"><span class="floatRight">{{prescription.doctorHospitalChamber}}</span></div>
                            <div class="row"><span class="floatRight">{{prescription.phoneNumber ? 'Mobile/Phone: ' + prescription.phoneNumber : ''}}</span></div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <hr>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Medication</h4>
                        <div class="row margin-bottom-small" ng-repeat="drug in advancePrescription.drugList">
                            <div class="col-md-12">
                                <span class="padding-left-small">
                                    {{$index + 1}}. {{drug.drugType}} - {{drug.medicineName}} - {{drug.drugWeight}} {{drug.period ? ' ( ' + drug.period + ' )' : ''}}
                                </span>
                            </div>
                            <div class="col-md-12">
                                <div class="col-md-12">
                                    {{prescription.drugTimes[drug.drugTime] < 4 ? drug.quantity1 + ' + ' + drug.quantity2 + ' + ' + drug.quantity3 : ''}}
                                    {{prescription.drugTimes[drug.drugTime] == 4 ? drug.quantity1 + ' + ' + drug.quantity2 + ' + ' + drug.quantity3 + ' + ' + drug.quantity4 : ''}}
                                    {{prescription.drugTimes[drug.drugTime] == 5 ? drug.quantity1 + ' + ' + drug.quantity2 + ' + ' + drug.quantity3 + ' + ' + drug.quantity4 + ' + ' + drug.quantity5 : ''}}
                                    ({{drug.medicineAmBm}})
                                    {{drug.instruction ? '(' + drug.instruction + ')' : ''}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Meal Plan</h4>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Breakfast</strong></span>
                            </div>
                            <div class="col-md-5">
                                <select class="selectpicker form-control form-group" ng-model="advancePrescription.breakfastPackage">
                                    <g:each in="${foodPackageList}" var="foodPackage">
                                        <option value="${foodPackage?.id}">${foodPackage?.packageName}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showPackageDetails(advancePrescription.breakfastPackage)"></span>
                            </div>
                            <div class="col-md-3">
                                <span>{{advancePrescription.breakfastTime}}</span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Snack Before Lunch</strong></span>
                            </div>
                            <div class="col-md-5">
                                <select class="selectpicker form-control form-group" ng-model="advancePrescription.beforeLunchPackage">
                                    <g:each in="${foodPackageList}" var="foodPackage">
                                        <option value="${foodPackage?.id}">${foodPackage?.packageName}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showPackageDetails(advancePrescription.beforeLunchPackage)"></span>
                            </div>
                            <div class="col-md-3">
                                <span>{{advancePrescription.beforeLunchTime}}</span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Lunch</strong></span>
                            </div>
                            <div class="col-md-5">
                                <select class="selectpicker form-control form-group" ng-model="advancePrescription.lunchPackage">
                                    <g:each in="${foodPackageList}" var="foodPackage">
                                        <option value="${foodPackage?.id}">${foodPackage?.packageName}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showPackageDetails(advancePrescription.lunchPackage)"></span>
                            </div>
                            <div class="col-md-3">
                                <span>{{advancePrescription.lunchTime}}</span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Snack Before Dinner</strong></span>
                            </div>
                            <div class="col-md-5">
                                <select class="selectpicker form-control form-group" ng-model="advancePrescription.beforeDinnerPackage">
                                    <g:each in="${foodPackageList}" var="foodPackage">
                                        <option value="${foodPackage?.id}">${foodPackage?.packageName}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showPackageDetails(advancePrescription.beforeDinnerPackage)"></span>
                            </div>
                            <div class="col-md-3">
                                <span>{{advancePrescription.beforeDinnerTime}}</span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Dinner</strong></span>
                            </div>
                            <div class="col-md-5">
                                <select class="selectpicker form-control form-group" ng-model="advancePrescription.dinnerPackage">
                                    <g:each in="${foodPackageList}" var="foodPackage">
                                        <option value="${foodPackage?.id}">${foodPackage?.packageName}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showPackageDetails(advancePrescription.dinnerPackage)"></span>
                            </div>
                            <div class="col-md-3">
                                <span>{{advancePrescription.dinnerTime}}</span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-12">
                                <span><strong>Before Sleep (At least 1.5-2 hours after dinner)</strong></span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3"></div>
                            <div class="col-md-5">
                                <select class="selectpicker form-control form-group" ng-model="advancePrescription.beforeSleepPackage">
                                    <g:each in="${foodPackageList}" var="foodPackage">
                                        <option value="${foodPackage?.id}">${foodPackage?.packageName}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showPackageDetails(advancePrescription.beforeSleepPackage)"></span>
                            </div>
                            <div class="col-md-3">
                                <span>{{advancePrescription.beforeSleepTime}}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Exercise Plan</h4>
                        <div ng-repeat="exercise in advancePrescription.exerciseList" class="margin-bottom-small col-md-12">
                            <div class="col-md-2"><strong>{{$index +1}}. Type</strong></div>
                            <div class="col-md-6">
                                <span>{{exercise.exerciseText}}</span>
                            </div>
                            <div class="col-md-4">
                                <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showExercisePackageDetails(exercise.exerciseId)"></span>
                            </div>
                            <div class="col-md-3"><strong>Time: </strong>{{exercise.time}}</div>
                            <div class="col-md-3"><strong>Period: </strong>{{exercise.period}}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>