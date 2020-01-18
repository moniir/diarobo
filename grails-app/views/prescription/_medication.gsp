<div>
    <div ng-form>
        <div class="row margin-bottom-small">
            <input type="number" class="form-control hidden" ng-model="advancePrescription.indexDrug">
            <input type="number" class="form-control hidden" ng-model="advancePrescription.drugId">
            <div class="col-md-2">
                <select class="form-control" ng-model="advancePrescription.drugType">
                    <g:each in="${drugTypes}" var="drugType">
                        <option value="${drugType?.id}">${drugType.name}</option>
                    </g:each>
                </select>
            </div>
            <div class="col-md-5">
                <input type="text" class="form-control typeahead" id="brandName"  ng-model="advancePrescription.medicineName" placeholder="Medicine">
            </div>
            <div class="col-md-2">
                <select class="form-control" ng-model="advancePrescription.drugWeight">
                    <g:each in="${drugWeightList}" var="drugWeight">
                        <option value="${drugWeight?.id}">${drugWeight.name}</option>
                    </g:each>
                </select>
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" ng-model="advancePrescription.drugPeriod" placeholder="Period">
            </div>
        </div>
        <div class="row margin-bottom-small">
            <div class="col-md-3">
                <select class="form-control" ng-model="advancePrescription.drugTime" ng-change="advancePrescription.setDrugTime()">
                    <g:each in="${drugTimeList}" var="drugTime">
                        <option value="${drugTime?.id}">${drugTime.name}</option>
                    </g:each>
                </select>
            </div>
            <div class="col-md-2">
                <label class="radio-inline" for="radiosAm">
                    <input type="radio" ng-model="advancePrescription.medicineAmBm" value="After Meal" checked="checked">
                    AM
                </label>
                <label class="radio-inline" for="radiosBm">
                    <input type="radio" ng-model="advancePrescription.medicineAmBm" value="Before Meal">
                    BM
                </label>
            </div>
            <div class="col-md-1 padding-margin-left-right-bootstrap-small">
                <input type="number" class="form-control" ng-model="advancePrescription.quantity1">
            </div>
            <div class="col-md-1 padding-margin-left-right-bootstrap-small">
                <input type="number" class="form-control" ng-model="advancePrescription.quantity2">
            </div>
            <div class="col-md-1 padding-margin-left-right-bootstrap-small">
                <input type="number" class="form-control" ng-model="advancePrescription.quantity3">
            </div>
            <div ng-show="prescription.drugTimes[advancePrescription.drugTime] > 3">
                <div class="col-md-1 padding-margin-left-right-bootstrap-small">
                    <input type="number" class="form-control" ng-model="advancePrescription.quantity4">
                </div>
            </div>
            <div ng-show="prescription.drugTimes[advancePrescription.drugTime] > 4">
                <div class="col-md-1 padding-margin-left-right-bootstrap-small">
                    <input type="number" class="form-control" ng-model="advancePrescription.quantity5">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <textarea class="form-control" ng-model="advancePrescription.instruction" placeholder="Instraction"></textarea>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group text-center">
                <input type="submit" class="btn btn-default center-block" ng-click="advancePrescription.addDrug()" value="+ Add more"/>
            </div>
        </div>

        <div class="row margin-bottom-small" ng-repeat="drug in advancePrescription.drugList">
            <div class="col-md-12">
                <i class="fa fa-edit" ng-click="advancePrescription.editDrug(drug, $index)"></i>
                <i ng-click="advancePrescription.removeDrug(drug)" class="fa fa-remove padding-left-small"></i>
                <span class="padding-left-small">
                    {{$index + 1}}. {{drug.drugType}} - {{drug.medicineName}} - {{drug.drugWeight}} {{drug.period ? ' ( ' + drug.period + ' )' : ''}}
                </span>
            </div>
            <div class="col-md-12">
                <div class="padding-left-tab">
                    <div class="col-md-12">
                        {{prescription.drugTimes[drug.drugTime] < 4 ? drug.quantity1 + ' + ' + drug.quantity2 + ' + ' + drug.quantity3 : ''}}
                        {{prescription.drugTimes[drug.drugTime] == 4 ? drug.quantity1 + ' + ' + drug.quantity2 + ' + ' + drug.quantity3 + ' + ' + drug.quantity4 : ''}}
                        {{prescription.drugTimes[drug.drugTime] == 5 ? drug.quantity1 + ' + ' + drug.quantity2 + ' + ' + drug.quantity3 + ' + ' + drug.quantity4 + ' + ' + drug.quantity5 : ''}}
                        ({{drug.medicineAmBm}})
                        {{drug.instruction ? '(' + drug.instruction + ')' : ''}}
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group text-center">
            <button class="btn btn-default" ng-click="advancePrescription.saveMedication()" tabindex="7" >Save</button>
            <button class="btn btn-default" tabindex="7">Save & Next</button>
        </div>
    </div>
</div>
<script>

</script>