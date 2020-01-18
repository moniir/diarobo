
<div class="row" ng-app="exercisePackage">
    <div class="col-lg-12" ng-controller="exercisePackageController as package">
        <div class="panel-body padding-top-form" ng-form>
            <div class="row">
                <div class="col-md-12">
                    <form class="form-horizontal" id="regForm">
                        <div class="form-group">
                            <div class="col-sm-2">
                                <label for="name" class="control-label">Package Name</label>
                            </div>
                            <div class="col-sm-7">
                                <input type="number" class="hidden" value="${exercisePackageId}" id="exercisePackageId" >
                                <input type="text" class="form-control" ng-model="package.packageName" id="packageName" placeholder="Package Name">
                            </div>
                            <div class="col-md-3">
                                <label class="control-label">Calorie burn: <span>{{ package.measurementCalorieBurn}}</span> Calorie</label>
                                <input class="hidden" ng-model="package.calorieBurn" id="calorieBurn">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-2">
                                <label  class="control-label">Exercise Name</label>
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control typeahead"  ng-model="package.exerciseName"  id="name" placeholder="Exercise Name">
                            </div>
                            <div class="col-md-1">
                                <label class="control-label">Duration</label>
                            </div>
                            <div class="col-sm-2">
                                <input type="number" class="hidden" id="exerciseLibraryId" ng-model="package.exerciseLibraryId">
                                <input type="number" class="hidden" ng-model="package.index">
                                <input type="text" class="form-control" ng-model="package.quantity" id="quantity" placeholder="Duration">
                            </div>
                            <div class="col-sm-1">
                                <input class="hidden" ng-model="package.measurementUnit" id="measurementUnit">
                                <span><label  class="control-label">{{package.measurementUnit}}</label></span>

                            </div>
                            <div class="col-sm-2">
                                <button ng-click="package.addExercise()">+ Add more</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-4 col-md-offset-2">
                                <div class="padding-top-form">
                                    <div class="col-md-12" ng-repeat="exercise in package.exerciseList">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-12">
                                                    <i class="fa fa-edit" ng-click="package.editExercisePackage(exercise, $index)"></i>
                                                    <i ng-click="package.removeExercise(exercise)" class="fa fa-remove padding-left-small"></i>
                                                    <span class="padding-left-small" >
                                                        {{$index + 1}} . {{exercise.exerciseName}} - {{exercise.quantity}} {{exercise.measurementUnit}}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-2 col-md-offset-6">
                                <button class="btn btn-success btn-block" tabindex="7" ng-click="package.savePackage()">Save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>


<script>
    $( document ).ready(function() {
        $('#packageName').val('${packageName}');
        angular.element($('#packageName')).triggerHandler('input');
        var exerciseNameSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'exercisePackage', action: 'searchExerciseName')}?q=%QUERY",
                transform: function(response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function(talentdata) {
                        return {
                            value: talentdata.name,
                            id: talentdata.id,
                            weightMeasure: talentdata.weightMeasure,
                            calorieBurn: talentdata.calorieBurn,
                        };
                    });
                }
            }
        });

        var exerciseNameSearch = $('#name').typeahead({
                    minLength: 2
                },
                {
                    name: 'exerciseNameData',
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
                    exerciseNameSearch.typeahead('val', datum.value);
                    $('#exerciseLibraryId').val(datum.id);
                    if(datum.weightMeasure == 'Duration') {
                        $('#measurementUnit').val('Hour');
                        angular.element($('#measurementUnit')).triggerHandler('input');
                    } else {
                        $('#measurementUnit').val(datum.weightMeasure);
                        angular.element($('#measurementUnit')).triggerHandler('input');
                    }
                    $('#calorieBurn').val(datum.calorieBurn);
                    angular.element($('#calorieBurn')).triggerHandler('input');
                    angular.element($('#exerciseLibraryId')).triggerHandler('input');
                    angular.element($('#name')).triggerHandler('input');
                });
    });
</script>