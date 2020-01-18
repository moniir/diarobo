<input type="hidden" name="masterId" id="masterId" value="${generalRuleId?:basicInfo?.id}"/>
<div ng-app="exercisePlan">
    <div ng-controller="exercisePlanController as exercise">
        <div class="row" ng-form style="margin-left: 5px;">
            <div class="form-group row">
                <input type="number" id="exerciseId" ng-model="exercise.exerciseId" class="hidden">
                <label class="col-md-3" for="relatedExercise">Related Exercise</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="exerciseName" placeholder="Exercise Name" ng-model="exercise.exerciseName">
                </div>
                <label class="col-md-2" for="calorieBurn">Period</label>
                <div class="col-md-2">
                    <input type="number" class="form-control" placeholder="Period in hour/step" ng-model="exercise.period">
                </div>
                <div class="col-md-1">
                    <button class="btn btn-success btn-add" type="button" ng-click="exercise.addExercise()">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>
        </div>
        <div class="row" ng-form style="margin-left: 5px;" ng-repeat="eachExercise in exercise.exerciseList">
            <div class="form-group row">
                <input type="number" ng-model="eachExercise.exerciseId" class="hidden">
                <label class="col-md-3" for="relatedExercise">Related Exercise</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" readonly placeholder="Exercise Name" ng-model="eachExercise.exerciseName">
                </div>
                <label class="col-md-2" for="calorieBurn">Period</label>
                <div class="col-md-2">
                    <input type="number" class="form-control" placeholder="Period in hour/step" ng-model="eachExercise.period">
                </div>
                <div class="col-md-1">
                    <button class="btn btn-success btn-add" type="button" ng-click="exercise.removeExercise(eachExercise)">
                        <span class="glyphicon glyphicon-remove"></span>
                    </button>
                </div>
            </div>
        </div>

        <div class="form-group text-center">
            <button class="btn btn-default" tabindex="7" ng-click="exercise.saveExercise()" >Save</button>
        </div>
    </div>
</div>
<script>
    var savedExerciseList = [];
    <g:each in="${exercisePlaList}" var="exercise">
        savedExerciseList.push({
            generalRuleId: ${exercise.generalRuleId},
            exerciseName: '${exercise.exerciseName}',
            exerciseId: ${exercise.exerciseId},
            period: ${exercise.period}
        });
    </g:each>
    var app = angular.module('exercisePlan', []);
    app.controller('exercisePlanController', function($http, $scope) {
        var exercise = this;
        exercise.exerciseId = 0;
        exercise.exerciseList = savedExerciseList.length > 0 ? savedExerciseList :[];
        exercise.addExercise = function () {
            if(exercise.exerciseId != 0 && exercise.period && $('#masterId').val()) {
                exercise.exerciseList.push(
                    {
                        generalRuleId: $('#masterId').val(),
                        exerciseName: exercise.exerciseName,
                        exerciseId: exercise.exerciseId,
                        period: exercise.period
                    }
                );
                exercise.exerciseId = 0;
                exercise.period = '';
                exercise.exerciseName = '';
            } else {
                showErrorMsg('Please Fill up field');
            }
        };
        exercise.removeExercise = function (exerciseDeleted) {
            var index = exercise.exerciseList.indexOf(exerciseDeleted);
            exercise.exerciseList.splice(index,1);
        };
        exercise.saveExercise = function () {
            if($('#masterId').val() != null && $('#masterId').val() != undefined && $('#masterId').val() != '') {
                if(exercise.exerciseList.length > 0) {
                    var dataObj = {
                        masterId: $('#masterId').val(),
                        exerciseList: JSON.stringify(exercise.exerciseList)
                    };
                    $http.post('/diabeticRule/saveExercisePlan', dataObj).then(function (response) {
                        if (!response.data.hasError) {
                            showSuccessMsg(response.data.message);
                        } else {
                            showErrorMsg(response.data.message);
                        }
                    });
                }
            } else {
                activeTab(1);
                showErrorMsg('Start From Basic Information');
            }
        };
    });
    var exerciseNameSource = new Bloodhound({
        datumTokenizer: function(datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "${g.createLink(controller: 'exercisePackage', action: 'searchNameByKeyword')}?q=%QUERY",
            transform: function(response) {
                // Map the remote source JSON array to a JavaScript object array
                return $.map(response.results, function(talentdata) {
                    return {
                        value: talentdata.packageName,
                        id: talentdata.id
                    };
                });
            }
        }
    });

    var exerciseNameSearch = $('#exerciseName').typeahead({
            minLength: 2
        },
        {
            name: 'foodNameData',
            source: exerciseNameSource,
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
            exerciseNameSearch.typeahead('val',datum.value);
            $('#exerciseId').val(datum.id);
            angular.element($('#exerciseName')).triggerHandler('input');
            angular.element($('#exerciseId')).triggerHandler('input');
        });
</script>

