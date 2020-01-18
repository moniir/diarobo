 <g:form action="saveBasic" controller="diabeticRule" method="post">
  <input type="hidden" name="basicInfoId" id="basicInfoId" value="${basicInfo?.id}"/>
 <div class="row" ng-form style="margin-left: 5px;">
            <div class="form-group row">
                <label class="col-md-3" for="diabeticsType">Type of diabetics</label>
                <div class="col-md-6">
                    <g:select class="form-control"  name="diabeticType" id="diabeticsType"
                              from='${com.diarobo.enums.DiabeticType.values()}' enums="true"
                              optionKey="key" optionValue="value" value="${basicInfo?.diabeticType}">
                    </g:select>
                 </div>
            </div>

            <div class="form-group row">
                <label class="col-md-3" for="bmiRangeFrom">BMI range</label>
                <div class="col-md-2">
                    <input type="text" required class="form-control numericfloat" placeholder="BMI From" id="bmiRangeFrom"  name="bmiRangeFrom" value="${basicInfo?.bmiRangeFrom}">
                </div>
                <label class="col-md-1" for="bmiRangeTo">To</label>
                <div class="col-md-2">
                    <input type="text" required class="form-control numericfloat" placeholder="BMI To" id="bmiRangeTo"  name="bmiRangeTo" value="${basicInfo?.bmiRangeTo}">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-md-3" for="ageRangeFrom">Age range</label>
                <div class="col-md-2">
                    <input type="text" required class="form-control numericfloat" placeholder="Age From" id="ageRangeFrom"  name="ageRangeFrom" value="${basicInfo?.ageRangeFrom}">
                </div>
                <label class="col-md-1" for="ageRangeTo">To</label>
                <div class="col-md-2">
                    <input type="text" required class="form-control numericfloat" placeholder="Age To" id="ageRangeTo"  name="ageRangeTo" value="${basicInfo?.ageRangeTo}">
                </div>
            </div>
    </div>


        <div class="form-group text-center">
            <button class="btn btn-default" type="submit">Save & Next</button>
        </div>
</g:form>

