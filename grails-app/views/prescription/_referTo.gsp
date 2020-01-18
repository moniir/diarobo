<div ng-controller="referToController as referTo">
    <div ng-form>
        <div class="row input-margin-bottom">
            <div class="col-md-12">
                <div class="col-md-9">
                    <input type="text" class="form-control typeahead" ng-focus="referTo.setSpecialistId()" placeholder="Specialist Name" id="specialist" ng-model="referTo.name">
                    <input type="text" class="form-control hidden" id="specialistId" ng-model="referTo.id">
                </div>
                <div class="col-md-3">
                    <input type="submit" class="btn btn-default center-block" ng-click="referTo.addSpecialist()" value="+ Add more"/>
                </div>
            </div>
        </div>
        <div class="row input-margin-bottom">
            <div class="col-md-12" ng-repeat="specialist in referTo.specialistList">
                <div class="col-md-1"></div>
                <div class="col-md-11">
                    {{$index + 1}}. {{specialist.name}}
                </div>
            </div>
        </div>
        <div class="form-group text-center">
            <button class="btn btn-default" tabindex="7" ng-click="referTo.saveReferTo()" >Save</button>
            <button class="btn btn-default" tabindex="7">Save & Next</button>
        </div>
    </div>
</div>