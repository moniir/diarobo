<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
<body>
    <ol class="breadcrumb">
        <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
        <li><a href="${createLink(controller: 'history', action: 'pastIllnessHistory')}">Past-Illness History</a></li>
        <li class="active">Create</li>
    </ol>

        <div class="row" ng-app="pastIllnessHistory">
            <asset:javascript src="vendor/angularJs/angular.min.js"/>
            <div class="col-md-12">
                <div class="panel panel-info" ng-controller="pastIllness as pastIllness">
                    <div ng-form>
                        <div class="errorHandler alert alert-danger">
                            <p id="message"></p>
                        </div>
                        <div class="panel-heading">
                            <div class="panel-title panel-title-left">Past-Illness History</div>
                            <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_blank">${message(code:'app.project.name.title' )}</a></div>
                        </div>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-4" id="whoTheySaw">
                                            <input type="text" ng-model="pastIllness.drugName" ng-keypress="pastIllness.addPastIllnessEnter($event)" class="form-control typeahead" placeholder="Enter Past Diseases"/><br>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <input class="btn btn-default center-block" ng-click="pastIllness.addIllness()" value="+ Add more"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <ul ng-repeat="illness in pastIllness.illnessList">
                                        <li class="history">
                                            <i class="fa fa-edit" ng-click="pastIllness.editIllness(illness)"></i>
                                            <i class="fa fa-close" ng-click="pastIllness.removeIllness(illness)"></i>
                                            &nbsp;<span class="{{illness.done ? '' : 'hidden'}}">{{illness.drugName}}</span>
                                            <input type="text" class="{{illness.done ? 'hidden' : ''}}" ng-model="illness.drugName" value="{{illness.drugName}}"/>
                                            <i class="{{illness.done ? 'hidden' : ''}} fa fa-check" ng-click="pastIllness.updateIllness(illness)"></i>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                        <div class="form-group text-center">
                            <button ng-click="pastIllness.saveIllness()" class="btn btn-default cancel-btn" tabindex="7">Save</button>
                            <button class="btn btn-default cancel-btn" tabindex="7">Save & Next</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <script>
        angular.module('pastIllnessHistory', [])
            .controller('pastIllness', function($http) {
                var pastIllness = this;
                pastIllness.illnessList = [
                    <g:each in="${pastIllnessList}" var="pastIllness" status="i">
                        <g:if test="${i != 0}" >
                        ,
                        </g:if>
                        {drugName: '${pastIllness.drugName}', done: true, id: ${pastIllness.id}}
                    </g:each>
                ];
                pastIllness.addPastIllnessEnter = function(keyEvent) {

                    if (keyEvent.which === 13)
                        pastIllness.addIllness();
                };
                pastIllness.addIllness = function () {
                    $scope.showSuccessAlert = false;
                    if(pastIllness.drugName) {
                        pastIllness.illnessList.push({drugName: pastIllness.drugName, done: true, id: null});
                        pastIllness.drugName = '';
                    }
                };
                pastIllness.removeIllness = function (illness) {
                    var index = pastIllness.illnessList.indexOf(illness);
                    pastIllness.illnessList.splice(index,1);
                };
                pastIllness.editIllness = function (illness) {
                    illness.done = false;
                };
                pastIllness.updateIllness = function (illness) {
                    illness.done = true;
                };
                pastIllness.saveIllness = function() {
                    var dataObj = {
                        illnessList : JSON.stringify(pastIllness.illnessList)
                    };
                    $http.post('${createLink(controller: 'history', action: 'savePastIllness')}', dataObj) .then(function(response) {
                        $scope.successTextAlert = response.data.message;
                        $scope.showSuccessAlert = true;
                    });
                };
            });
        var talentSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'history', action: 'searchDiseasesByKeyword')}?q=%QUERY",
                transform: function(response) {
                    return $.map(response.results, function(talentdata) {
                        return {
                            value: talentdata.name
                        };
                    });
                }
            }
        });
        var whoTheySawSearch = $('#whoTheySaw .typeahead').typeahead({
                minLength: 2
            },
            {
                name: 'whoTheySawData',
                source: talentSource,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}}</p></div>')
                }
            })
            .on('typeahead:selected', function(obj, datum){
                whoTheySawSearch.typeahead('val',datum.value);
            });
    </script>
    </body>
</html>
