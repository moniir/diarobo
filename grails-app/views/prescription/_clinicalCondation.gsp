<div ng-controller="clinicalConditionController as clinic">
    <div ng-form>
        <div class="row">
            <div class="col-md-12">
                <h4><strong>Glucose in blood</strong></h4>
            </div>
            <div class="col-md-3">
                <span>Before Breakfast</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="fasting" ng-model="clinic.beforeBreakfast" placeholder="Before Breakfast">
                </div>
            </div>
            <div class="col-md-3">
                <span>After Breakfast</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="afterBreakfast" ng-model="clinic.afterBreakfast" placeholder="After Breakfast">
                </div>
            </div>
            <div class="col-md-3">
                <span>Before Lunch</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="beforeLunch" ng-model="clinic.beforeLunch" placeholder="Before Lunch">
                </div>
            </div>
            <div class="col-md-3">
                <span>After Lunch</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="afterLunch" ng-model="clinic.afterLunch" placeholder="After Lunch">
                </div>
            </div>
            <div class="col-md-3">
                <span>Before Dinner</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="beforeDinner" ng-model="clinic.beforeDinar" placeholder="Before Dinner">
                </div>
            </div>
            <div class="col-md-3">
                <span>After Dinner</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="afterDinner" ng-model="clinic.afterDinar" placeholder="After Dinner">
                </div>
            </div>
            <div class="col-md-3">
                <span>Random</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="random" ng-model="clinic.randomTime" placeholder="Random">
                </div>
            </div>
            <div class="col-md-3">
                <span>HBAIC</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="hbaic" ng-model="clinic.hbaic" placeholder="HBAIC">
                </div>
            </div>
            <div class="col-md-3">
                <span>OGTT</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="ogtt" ng-model="clinic.ogtt" placeholder="OGTT">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h4><strong>Urine</strong></h4>
            </div>
            <div class="col-md-3">
                <span>Sugar</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="sugar" ng-model="clinic.sugar" placeholder="Sugar">
                </div>
            </div>
            <div class="col-md-3">
                <span>Albumin</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="albumin" ng-model="clinic.albumin" placeholder="Albumin">
                </div>
            </div>
            <div class="col-md-3">
                <span>Acetone</span>
            </div>
            <div class="col-md-9">
                <div class="form-group">
                    <input type="text" class="form-control" id="acetone" ng-model="clinic.acetone" placeholder="Acetone">
                </div>
            </div>
        </div>
        <div class="form-group text-center">
            <button class="btn btn-default" tabindex="7" ng-click="clinic.save()">Save</button>
            <button class="btn btn-default" tabindex="7">Save & Next</button>
        </div>
    </div>
</div>