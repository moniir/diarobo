<div ng-form>
    <h2>Meal Plan</h2>
    <div class="row">
        <div id="mealDetails">
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
                    <div class="form-group">
                        <div class="input-group date" id="breakfastTime">
                            <input type='text' class="form-control" placeholder="Breakfast Time" ng-model="advancePrescription.breakfastTime"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
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
                    <div class="form-group">
                        <div class='input-group date' id='snack1Time'>
                            <input type='text' class="form-control" placeholder="Snack 1 Time" ng-model="advancePrescription.beforeLunchTime"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
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
                    <div class="form-group">
                        <div class='input-group date' id='lunchTime'>
                            <input type='text' class="form-control" placeholder="Lunch Time" ng-model="advancePrescription.lunchTime"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
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
                    <div class="form-group">
                        <div class='input-group date' id='snack2Time'>
                            <input type='text' class="form-control" placeholder="Snack 2" ng-model="advancePrescription.beforeDinnerTime"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
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
                    <div class="form-group">
                        <div class='input-group date' id='dinnerTime'>
                            <input type='text' class="form-control" placeholder="Dinner Time" ng-model="advancePrescription.dinnerTime"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <span><strong>Before Sleep (At least 1.5-2 hours after dinner)</strong></span>
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
                    <div class="form-group">
                        <div class='input-group date' id='otherFoodTime'>
                            <input type='text' class="form-control" placeholder="Snack 3" ng-model="advancePrescription.beforeSleepTime"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <h2>Exercise Plan</h2>
    <div class="row">
        <div id="exerciseDetails">
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
    <div class="form-group text-center">
        <button class="btn btn-default" tabindex="7" ng-click="advancePrescription.saveRecommend()">Update Meal & Exercise</button>
    </div>
</div>



<h2>Medication</h2>
<div ng-form>
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
            <div class="col-md-12">
                <div class="{{drug.drugTime1 ? '' : 'hidden'}} col-md-2 padding-margin-left-right-bootstrap-small"><input  type="text" class="form-control" ng-model="drug.drugTime1"></div>
                <div class="{{drug.drugTime2 ? '' : 'hidden'}} col-md-2 padding-margin-left-right-bootstrap-small"><input  type="text" class="form-control" ng-model="drug.drugTime2"></div>
                <div class="{{drug.drugTime3 ? '' : 'hidden'}} col-md-2 padding-margin-left-right-bootstrap-small"><input  type="text" class="form-control" ng-model="drug.drugTime3"></div>
                <div class="{{drug.drugTime4 ? '' : 'hidden'}} col-md-2 padding-margin-left-right-bootstrap-small"><input  type="text" class="form-control" ng-model="drug.drugTime4"></div>
                <div class="{{drug.drugTime5 ? '' : 'hidden'}} col-md-2 padding-margin-left-right-bootstrap-small"><input  type="text" class="form-control" ng-model="drug.drugTime5"></div>
            </div>
        </div>
    </div>
    <div class="form-group text-center">
        <button class="btn btn-default" ng-click="advancePrescription.saveMedication()" tabindex="7" >Update Medication</button>
    </div>
</div>