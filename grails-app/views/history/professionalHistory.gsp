<%@ page import="com.diarobo.MasterKeySetup; com.diarobo.enums.MasterKeyValue" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>

<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'history', action: 'professionalHistory')}">Professional History</a></li>
    <li class="active">Create</li>
</ol>
<div class="row" ng-app="professionalHistory">
    <asset:javascript src="vendor/angularJs/angular.min.js"/>
    <div class="col-md-12">
        <div class="panel panel-info" ng-controller="professionalHistory as profession">
            <div class="panel-heading">
                <div class="panel-title panel-title-left">Professional History</div>
                <div class="panel-title-right"><a href="${createLink(uri: '/')}"
                                                  target="_self">${message(code: 'app.project.name.title')}</a>
                </div>
            </div>
            <div ng-form>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingTwo"></div>
                    <div class="panel-body padding-top-form">
                        <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                            <div class="alert alert-success" ng-show="showSuccessAlert">
                                <button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
                                {{successTextAlert}}
                            </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                               <span><strong>Job Industry:</strong></span>
                            </div>
                            <div class="col-md-9">
                                <g:each in="${MasterKeySetup.findAllByKeyType(MasterKeyValue.JOB_INDUSTRY.value)}" var="eachJobIndustry">
                                    <label class="radio-inline">
                                        <input type="radio" ng-model="profession.jobIndustry" value="${eachJobIndustry?.keyValue}">${eachJobIndustry?.keyValue}
                                    </label>
                                </g:each>
                                <br> <br>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Nature of Job:</strong></span>
                            </div>
                            <div class="col-md-9">
                                <g:each in="${MasterKeySetup.findAllByKeyType(MasterKeyValue.JOB_NATURE.value)}" var="jobNature">
                                    <label class="radio-inline">
                                        <input type="radio" ng-model="profession.jobNature" value="${jobNature?.keyValue}">${jobNature?.keyValue}
                                    </label>
                                </g:each>
                                <br> <br>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Duration:</strong></span>
                            </div>
                            <div class="col-md-3">
                                <input type="text" ng-model="profession.fromDate" class="fromDate form-control" placeholder="from"/>
                            </div>
                            <div class="col-md-3 {{profession.currentJob ? 'hidden' : ''}}">
                                <input type="text" ng-model="profession.toDate" class="toDate form-control" placeholder="to"/>
                            </div>
                            <div class="col-md-3">
                                <label><input type="checkbox" ng-model="profession.currentJob" value="current"> continue current job</label>
                            </div>
                            <br> <br>
                        </div>
                        <div class="form-group">
                            <button ng-click="profession.addProfession()" class="btn btn-default center-block">+ Add more</button>
                        </div>
                        <br> <br>
                        <div class="col-md-12">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Job Industry</th>
                                    <th>Job Nature</th>
                                    <th>Duration</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody ng-repeat="eachProfession in profession.professionList" >
                                <tr>
                                    <td>
                                        <span class="{{eachProfession.done ? '' : 'hidden'}}">{{eachProfession.jobIndustry}}</span>
                                        <g:each in="${MasterKeySetup.findAllByKeyType(MasterKeyValue.JOB_INDUSTRY.value)}" var="eachJobIndustry">
                                            <label class="radio-inline {{eachProfession.done ? 'hidden' : ''}}">
                                                <input type="radio" ng-model="eachProfession.jobIndustry" value="${eachJobIndustry?.keyValue}">${eachJobIndustry?.keyValue}
                                            </label>
                                        </g:each>
                                    </td>
                                    <td>
                                        <span class="{{eachProfession.done ? '' : 'hidden'}}">{{eachProfession.jobNature}}</span>
                                        <g:each in="${MasterKeySetup.findAllByKeyType(MasterKeyValue.JOB_NATURE.value)}" var="jobNature">
                                            <label class="radio-inline {{eachProfession.done ? 'hidden' : ''}}">
                                                <input type="radio" ng-model="eachProfession.jobNature" value="${jobNature?.keyValue}">${jobNature?.keyValue}
                                            </label>
                                        </g:each>
                                    </td>
                                    <td>
                                        <span class="{{eachProfession.done ? '' : 'hidden'}}">{{eachProfession.fromDate}} - {{eachProfession.currentJob ? 'continue current job' : eachProfession.toDate}}</span>
                                        <div class="{{eachProfession.done ? 'hidden' : ''}}">
                                            <div class="col-md-4">
                                                <input type="text" ng-model="eachProfession.fromDate" class="fromDate form-control" placeholder="from"/>
                                            </div>
                                            <div class="col-md-4 {{eachProfession.currentJob ? 'hidden' : ''}}">
                                                <input type="text" ng-model="eachProfession.toDate" class="toDate form-control" placeholder="to"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label><input type="checkbox" ng-model="eachProfession.currentJob" value="current"> continue current job</label>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <i class="{{eachProfession.done ? '' : 'hidden'}} fa fa-edit" ng-click="profession.editProfession(eachProfession)"></i>
                                        <i class="{{eachProfession.done ? '' : 'hidden'}} fa fa-close" ng-click="profession.removeProfession(eachProfession)"></i>
                                        <i class="{{eachProfession.done ? 'hidden' : ''}} fa fa-check" ng-click="profession.updateProfession(eachProfession)"></i>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
                <div class="form-group text-center">
                    <button class="btn btn-default cancel-btn" ng-click="profession.saveProfession()" tabindex="7">Save</button>
                    <button class="btn btn-default cancel-btn" tabindex="7">Save & Next</button>
                </div>
            </div>
         </div>
     </div>
 </div>
<script>
    angular.module('professionalHistory', [])
        .controller('professionalHistory', function($http, $scope) {
            $scope.switchBool = function (value) {
                $scope[value] = !$scope[value];
            };
            var professionalHistory = this;
            professionalHistory.professionList = [
                <g:each in="${professionalHistoryList}" var="professional" status="i">
                <g:if test="${i != 0}" >
                ,
                </g:if>
                {jobNature: '${professional.jobNature}', jobIndustry: '${professional.jobIndustry}', fromDate: '${professional.fromDate}', toDate: '${professional.toDate}', currentJob: ${professional.currentJob}, id: ${professional.id}, done: true}
                </g:each>
            ];
            professionalHistory.addProfession = function () {
                $scope.showSuccessAlert = false;
                if(professionalHistory.jobIndustry && professionalHistory.jobNature && professionalHistory.fromDate && (professionalHistory.currentJob || professionalHistory.toDate)) {
                    professionalHistory.professionList.push({jobNature: professionalHistory.jobNature, jobIndustry: professionalHistory.jobIndustry, fromDate: professionalHistory.fromDate, toDate: professionalHistory.toDate, currentJob: (professionalHistory.currentJob ? true: false), done: true, id: null});
                    professionalHistory.jobNature = '';
                    professionalHistory.jobIndustry = '';
                    professionalHistory.fromDate = '';
                    professionalHistory.toDate = '';
                    professionalHistory.currentJob = false;
                }
            };
            professionalHistory.editProfession = function (profession) {
                profession.done = false;
            };
            professionalHistory.updateProfession = function (profession) {
                profession.done = true;
            };
            professionalHistory.removeProfession = function (profession) {
                var index = professionalHistory.professionList.indexOf(profession);
                professionalHistory.professionList.splice(index,1);
            };
            professionalHistory.saveProfession = function() {
                var dataObj = {
                    professionList : JSON.stringify(professionalHistory.professionList)
                };
                $http.post('${createLink(controller: 'history', action: 'saveProfessionalHistory')}', dataObj) .then(function(response) {
                    $scope.successTextAlert = response.data.message;
                    $scope.showSuccessAlert = true;
                });
            }
        });
    jQuery(function ($) {
        $(".fromDate").datepicker({
            format: 'dd/mm/yyyy',
            autoclose: true
        })
        $(".toDate").datepicker({
            format: 'dd/mm/yyyy',
            autoclose: true
        })
    });
</script>
</body>
</html>