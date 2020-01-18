<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>

<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'history', action: 'drugHistory')}">Previews Drug History</a></li>
    <li class="active">Create</li>
</ol>

<div ng-app="previousDrugHistory">
    <asset:javascript src="vendor/angularJs/angular.min.js"/>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info" ng-controller="drugHistory as drug">
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Previews Drug History</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <form ng-submit="drug.addDrug()">
                    <div class="panel-body padding-top-form">
                        <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                            <div class="alert alert-success" ng-show="showSuccessAlert">
                                <button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
                                {{successTextAlert}}
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-2">
                                        <select class="form-control" ng-model="drug.drugType">
                                            <g:each in="${drugTypes}" var="drugType">
                                                <option value="${drugType.id}">${drugType.name}</option>
                                            </g:each>
                                        </select>
                                    </div>

                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="Drug Name" ng-model="drug.name">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" name="fromDate" class="form-control fromDate" placeholder="from" ng-model="drug.drugTakenFrom">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" class="form-control toDate" name="toDate" placeholder="to" ng-model="drug.drugTakenTo"/>
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="Frequency" ng-model="drug.drugFrequency"/>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="form-group">
                                <input type="submit" class="btn btn-default center-block" value="+ Add more"/>
                            </div>
                            <div class="col-md-12">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Drug Type</th>
                                        <th>Drug Name</th>
                                        <th>Taken From</th>
                                        <th>Taken To</th>
                                        <th>Frequency</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody ng-repeat="eachDrug in drug.drugList" >
                                    <tr>
                                        <td>
                                            <span class="{{eachDrug.done ? '' : 'hidden'}}">{{eachDrug.drugType}}</span>
                                            <select class="form-control {{eachDrug.done ? 'hidden' : ''}}" ng-model="eachDrug.drugType">
                                                <g:each in="${drugTypes}" var="drugType">
                                                    <option value="${drugType.id}">${drugType.name}</option>
                                                </g:each>
                                            </select>
                                        </td>
                                        <td>
                                            <span class="{{eachDrug.done ? '' : 'hidden'}}">{{eachDrug.name}}</span>
                                            <input type="text" class="form-control {{eachDrug.done ? 'hidden' : ''}}" placeholder="Drug Name" ng-model="eachDrug.name">
                                        </td>
                                        <td>
                                            <span class="{{eachDrug.done ? '' : 'hidden'}}">{{eachDrug.drugTakenFrom }}</span>
                                            <input type="text" name="fromDate" class="form-control fromDate {{eachDrug.done ? 'hidden' : ''}}" placeholder="from" ng-model="eachDrug.drugTakenFrom">
                                        </td>
                                        <td>
                                            <span class="{{eachDrug.done ? '' : 'hidden'}}">{{eachDrug.drugTakenTo}}</span>
                                            <input type="text" class="form-control toDate {{eachDrug.done ? 'hidden' : ''}}" name="toDate" placeholder="to" ng-model="eachDrug.drugTakenTo"/>
                                        </td>
                                        <td>
                                            <span class="{{eachDrug.done ? '' : 'hidden'}}">{{eachDrug.drugFrequency}}</span>
                                            <input type="text" class="form-control {{eachDrug.done ? 'hidden' : ''}}" placeholder="Frequency" ng-model="eachDrug.drugFrequency"/>
                                        </td>
                                        <td><i class="{{eachDrug.done ? '' : 'hidden'}} fa fa-edit" ng-click="drug.editDrugHistory(eachDrug)"></i>
                                            <i class="{{eachDrug.done ? '' : 'hidden'}} fa fa-close" ng-click="drug.removeDrugHistory(eachDrug)"></i>
                                            <i class="{{eachDrug.done ? 'hidden' : ''}} fa fa-check" ng-click="drug.updateDrugHistory(eachDrug)"></i>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                       </div>
                   </div>
                    <div class="form-group text-center">
                        <button class="btn btn-default cancel-btn" ng-click="drug.saveDrugHistory()" tabindex="7" type="reset">Save</button>
                        <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Save & Next</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
<script>
    angular.module('previousDrugHistory', [])
        .controller('drugHistory', function($http, $scope) {
            $scope.switchBool = function (value) {
                $scope[value] = !$scope[value];
            };
            var drugHistory = this;
            drugHistory.drugList = [
            <g:each in="${drugList}" var="drug" status="i">
                <g:if test="${i != 0}" >
                    ,
                </g:if>
                {drugType: '${drug.drugType}', name: '${drug.drugName}', drugTakenFrom: '${drug.drugTakenFrom}', drugTakenTo: '${drug.drugTakenTo}', drugFrequency: '${drug.drugFrequency}', id: ${drug.id}, done: true}
            </g:each>
            ];
            drugHistory.addDrug = function () {
                $scope.showSuccessAlert = false;
                if(drugHistory.name && drugHistory.drugType && drugHistory.drugTakenFrom && drugHistory.drugTakenTo && drugHistory.drugFrequency) {
                    drugHistory.drugList.push({drugType: drugHistory.drugType, name: drugHistory.name, drugTakenFrom: drugHistory.drugTakenFrom, drugTakenTo: drugHistory.drugTakenTo, drugFrequency: drugHistory.drugFrequency, done: true, id: null});
                    drugHistory.name = '';
                    drugHistory.drugTakenFrom = '';
                    drugHistory.drugTakenTo = '';
                    drugHistory.drugFrequency = '';
                }
            };
            drugHistory.editDrugHistory = function (drug) {
                drug.done = false;
            };
            drugHistory.updateDrugHistory = function (drug) {
                drug.done = true;
            };
            drugHistory.removeDrugHistory = function (drug) {
                var index = drugHistory.drugList.indexOf(drug);
                drugHistory.drugList.splice(index,1);
            };
            drugHistory.saveDrugHistory = function() {
                var dataObj = {
                    drugList : JSON.stringify(drugHistory.drugList)
                };
                $http.post('${createLink(controller: 'history', action: 'saveDrugHistory')}', dataObj) .then(function(response) {
                    $scope.successTextAlert = response.data.message;
                    $scope.showSuccessAlert = true;
                });
            }
        });
    jQuery(function ($) {
        $(".fromDate").datepicker({
            format: "mm-yyyy",
            viewMode: "months",
            minViewMode: "months"
        });
        $(".toDate").datepicker({
            format: "mm-yyyy",
            viewMode: "months",
            minViewMode: "months"
        });
    });
</script>
</div>
</body>
</html>