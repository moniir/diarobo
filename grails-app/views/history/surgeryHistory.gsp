<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>

<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'history', action: 'diabetic')}">Surgery History</a></li>
    <li class="active">Create</li>
</ol>
    <div class="row" ng-app="surgeryHistory">
        <div class="col-md-12">
            <div class="panel panel-info" ng-controller="surgeryHistory as surgeryHistory">
                    <div class="panel-heading">
                        <div class="panel-title panel-title-left">Surgery History</div>
                        <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                    </div>
                <div class="panel panel-default">
                    <div class="panel-body padding-top-form">
                        <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                            <div class="alert alert-success" ng-show="showSuccessAlert">
                                <button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
                                {{successTextAlert}}
                            </div>
                            <div class="row">
                                <div class="col-md-12 ">
                                    <div class="col-md-8">
                                        <input type="text" ng-keypress="surgeryHistory.addSurgeryEnter($event)" ng-model="surgeryHistory.description" class="form-control" placeholder="Surgery History"/><br>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <input ng-click="surgeryHistory.addSurgery()" class="btn btn-default center-block" value="+ Add more"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <ul ng-repeat="surgery in surgeryHistory.surgeryHistoryList">
                                    <li class="unBulletLi">
                                        <i class="fa fa-edit" ng-click="surgeryHistory.editSurgery(surgery)"></i>
                                        <i class="fa fa-close" ng-click="surgeryHistory.removeSurgery(surgery)"></i>
                                        &nbsp;<span class="{{surgery.done ? '' : 'hidden'}}">{{surgery.description}}</span>
                                        <input type="text" class="{{surgery.done ? 'hidden' : ''}}" ng-model="surgery.description" value="{{surgery.description}}"/>
                                        <i class="{{surgery.done ? 'hidden' : ''}} fa fa-check" ng-click="surgeryHistory.updateSurgery(surgery)"></i>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                    <div class="form-group text-center">
                        <button class="btn btn-default cancel-btn" ng-click="surgeryHistory.saveSurgery()" tabindex="7">Save</button>
                        <button class="btn btn-default cancel-btn" tabindex="7">Save & Next</button>
                    </div>
                </div>
            </div>
        </div>
<script>
    angular.module('surgeryHistory', [])
        .controller('surgeryHistory', function($http, $scope) {
            $scope.switchBool = function (value) {
                $scope[value] = !$scope[value];
            };
            var surgeryHistory = this;
            surgeryHistory.surgeryHistoryList = [
                <g:each in="${surgeryList}" var="surgery" status="i">
                <g:if test="${i != 0}" >
                ,
                </g:if>
                {description: '${surgery.description}', done: true, id: ${surgery.id}}
                </g:each>
            ];
            surgeryHistory.addSurgery = function () {
                $scope.showSuccessAlert = false;
                if(surgeryHistory.description) {
                    surgeryHistory.surgeryHistoryList.push({description: surgeryHistory.description, done: true, id: null});
                    surgeryHistory.description = '';
                }
            };
            surgeryHistory.removeSurgery = function (surgery) {
                var index = surgeryHistory.surgeryHistoryList.indexOf(surgery);
                surgeryHistory.surgeryHistoryList.splice(index,1);
            };
            surgeryHistory.editSurgery = function (surgery) {
                surgery.done = false;
            };
            surgeryHistory.updateSurgery = function (surgery) {
                surgery.done = true;
            };
            surgeryHistory.addSurgeryEnter = function(keyEvent) {
                if (keyEvent.which === 13)
                    surgeryHistory.addSurgery();
            };
            surgeryHistory.saveSurgery = function() {
                var dataObj = {
                    surgeryHistoryList : JSON.stringify(surgeryHistory.surgeryHistoryList)
                };
                $http.post('${createLink(controller: 'history', action: 'saveSurgeryHistory')}', dataObj) .then(function(response) {
                    $scope.successTextAlert = response.data.message;
                    $scope.showSuccessAlert = true;
                });
            };
        });
</script>
</body>
</html>