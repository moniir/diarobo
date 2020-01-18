<div>
<div ng-form>
    <div class="row">
        <div class="col-md-12">
            <label  data-toggle="collapse" href="#mealDetails" aria-expanded="false" aria-controls="mealDetails">
                <h4><strong><u>Meal</u></strong></h4>
            </label>
        </div>
    </div>
    <div class="row">
        <div class="collapse" id="mealDetails">
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
    <div class="row">
        <div class="col-md-12">
            <label data-toggle="collapse" href="#exerciseDetails" aria-expanded="false" aria-controls="exerciseDetails">
                <h4><strong><u>Exercise</u></strong></h4>
            </label>
        </div>
    </div>
    <div class="row">
        <div class="collapse" id="exerciseDetails">
            <div class="col-md-12">
                <input type="number" class="hidden" ng-model="advancePrescription.indexExercise">
                <input type="number" class="hidden" ng-model="advancePrescription.exerciseId">
                <div class="col-md-2">
                    <span><strong>Package</strong></span>
                </div>
                <div class="col-md-10">
                    <div class="form-group">
                        <select id="recommendExercise" class="form-control" ng-model="advancePrescription.recommendExercise" style="width:100%">
                            <g:each in="${exerciseList}" var="exercise">
                                <option value="${exercise?.id}">${exercise?.name}</option>
                            </g:each>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="col-md-2">
                    <span><strong>Time</strong></span>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group date">
                            <input type='text' class="form-control" placeholder="Time" ng-model="advancePrescription.time"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <span><strong>Period</strong></span>
                </div>
                <div class="col-md-2">
                    <input type="number" class="form-control" ng-model="advancePrescription.period" placeholder="Period">
                </div>
                <div class="col-md-2">
                    <span><strong>Hours/Times</strong></span>
                </div>
            </div>
            <div class="col-md-12 input-margin-bottom">
                <button class="btn btn-default" tabindex="7" ng-click="advancePrescription.addExercise()">Add</button>
            </div>
            <div ng-repeat="exercise in advancePrescription.exerciseList" class="margin-bottom-small col-md-12">
                <div class="col-md-2">
                    <i class="fa fa-edit" ng-click="advancePrescription.edit(exercise, $index)"></i>
                    <i ng-click="advancePrescription.remove(exercise)" class="fa fa-remove padding-left-small"></i>
                </div>
                <div class="col-md-8">
                    <div class="col-md-3"><strong>{{$index +1}}. Package</strong></div>
                    <div class="col-md-9">
                        <span>{{exercise.exerciseText}}</span>
                    </div>
                    <div class="col-md-3"><strong>Time: </strong>{{exercise.time}}</div>
                    <div class="col-md-3"><strong>Period: </strong>{{exercise.period}}</div>
                </div>
                <div class="col-md-2">
                    <span class="glyphicon glyphicon-list-alt" ng-click="prescription.showExercisePackageDetails(exercise.exerciseId)"></span>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group text-center">
        <button class="btn btn-default" tabindex="7" ng-click="advancePrescription.saveRecommend()">Save</button>
        <button class="btn btn-default" tabindex="7">Save & Next</button>
    </div>
</div>
</div>
