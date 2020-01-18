<div class="row" ng-app="foodPackage">
    <div class="col-lg-12" ng-controller="foodPackageController as package">
        <div class="panel-body padding-top-form" ng-form>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-2">
                        <span>Food Name</span>
                    </div>
                    <div class="col-md-3">
                        <input type="number" class="hidden" value="${foodPackageId}" id="foodPackageId" >
                        <input type="number" class="hidden" id="foodLibraryId" ng-model="package.foodLibraryId">
                        <input type="number" class="hidden" ng-model="package.index">
                        <input type="text" class="form-control typeahead"  ng-model="package.foodName"  id="name" placeholder="Food Name">
                    </div>
                    <div class="col-md-1">
                        <span>Quantity</span>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <input type="number" class="form-control" ng-model="package.quantity" id="quantity" placeholder="Quantity">
                        </div>
                    </div>
                    <div class="col-md-1">
                        <span>{{package.measurementUnit}}</span>
                    </div>
                    <div class="col-md-2">
                        <button ng-click="package.addFood()">+ Add more</button>
                    </div>
                </div>
                <div class="col-md-12" ng-repeat="food in package.foodList">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-2"></div>
                            <div class="col-md-10">
                                <i class="fa fa-edit" ng-click="package.edit(food, $index)"></i>
                                <i ng-click="package.removeFood(food)" class="fa fa-remove padding-left-small"></i>
                                <span class="padding-left-small">{{$index + 1}} . {{food.foodName}} - {{food.quantity}} {{food.measurementUnit}}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="col-md-2">
                        <span>Package Name</span>
                    </div>
                    <div class="col-md-10">
                        <div class="form-group">
                            <input type="text" class="form-control" ng-model="package.packageName" id="packageName" placeholder="Package Name">
                        </div>
                    </div>
                </div>
                <div class="form-group text-center">
                    <button class="btn btn-default" tabindex="7" ng-click="package.savePackage()">Save</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $( document ).ready(function() {
        $('#packageName').val('${packageName}');
        angular.element($('#packageName')).triggerHandler('input');
        var foodNameSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'foodPackage', action: 'searchFoodName')}?q=%QUERY",
                transform: function(response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function(talentdata) {
                        return {
                            value: talentdata.name,
                            id: talentdata.id
                        };
                    });
                }
            }
        });
        var foodNameSearch = $('#name').typeahead({
                minLength: 2
            },
            {
                name: 'foodNameData',
                source: foodNameSource,
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
                foodNameSearch.typeahead('val', datum.value);
                $('#foodLibraryId').val(datum.id);
                angular.element($('#foodLibraryId')).triggerHandler('input');
                angular.element($('#name')).triggerHandler('input');
            });
    });
</script>