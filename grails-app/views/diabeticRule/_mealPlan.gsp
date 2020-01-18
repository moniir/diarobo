<g:form action="saveMealPlan" controller="diabeticRule" method="post">
 <input type="hidden" name="masterId" id="masterId" value="${generalRuleId?:basicInfo?.id}"/>
 <input type="hidden" name="mealPlanId" id="mealPlanId" value="${mealPlan?.id}"/>

 <div class="row" style="margin-left: 5px;">
     <div class="form-group row">
         <label class="col-md-3" for="breakFastFoodId">Breakfast</label>
         <div class="col-md-6">
             <g:select class="form-control many-to-one"  name='breakFastFoodId' id="breakFastFoodId"
                        noSelection="${['':'Select Package....']}"
                       from='${com.diarobo.api.RemoteController.foodPackageList()}'
                       optionKey="id" optionValue="packageName"
                       value="${mealPlan?.breakFastFoodId}" />
         </div>
         <div  class="col-md-3">
             <div class="input-group date timePicker">
                 <input type="text" name="breakFastFoodTime" id="breakFastFoodTime" required class="form-control" placeholder="Time" value="${mealPlan?.breakFastFoodTime}"/>
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-time"></span>
                 </span>
             </div>
         </div>
     </div>
     <div class="form-group row">
         <label class="col-md-3" for="snackBeforeLunchFoodId">Snack Before Lunch</label>
       <div class="col-md-6">
             <g:select class="form-control"  name='snackBeforeLunchFoodId' id="snackBeforeLunchFoodId" required="required"
                       noSelection="${['':'Select Package....']}"
                       from='${com.diarobo.api.RemoteController.foodPackageList()}'
                       optionKey="id" optionValue="packageName" value="${mealPlan?.snackBeforeLunchFoodId}"/>

         </div>
     <div  class="col-md-3">
             <div class="input-group date timePicker">
                 <input type="text" name="snackBeforeLunchFoodTime" id="snackBeforeLunchFoodTime" required class="form-control" placeholder="Time" value="${mealPlan?.snackBeforeLunchFoodTime}"/>
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-time"></span>
                 </span>
             </div>
         </div>
     </div>

     <div class="form-group row">
         <label class="col-md-3" for="lunchFoodId">Lunch</label>
         <div class="col-md-6">
             <g:select class="form-control"  name='lunchFoodId' required="required"
                       noSelection="${['':'Select Package....']}"
                       from='${com.diarobo.api.RemoteController.foodPackageList()}'
                       optionKey="id" optionValue="packageName" value="${mealPlan?.lunchFoodId}" />
         </div>
         <div  class="col-md-3">
             <div class="input-group date timePicker">
                 <input type="text" name="lunchFoodTime" required class="form-control" placeholder="Time" value="${mealPlan?.lunchFoodTime}" />
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-time"></span>
                 </span>
             </div>
         </div>
     </div>

     <div class="form-group row">
         <label class="col-md-3" for="snackBeforeDinnerFoodId">Snack Before Dinner</label>
         <div class="col-md-6">
             <g:select class="form-control"  name='snackBeforeDinnerFoodId' required="required"
                       noSelection="${['':'Select Package....']}"
                       from='${com.diarobo.api.RemoteController.foodPackageList()}'
                       optionKey="id" optionValue="packageName" value="${mealPlan?.snackBeforeDinnerFoodId}"/>

         </div>
         <div  class="col-md-3">
             <div class="input-group date timePicker">
                 <input type="text" name="snackBeforeDinnerFoodTime" required class="form-control" placeholder="Time" value="${mealPlan?.snackBeforeDinnerFoodTime}"/>
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-time"></span>
                 </span>
             </div>
         </div>
     </div>

     <div class="form-group row">
         <label class="col-md-3" for="dinnerFoodId">Dinner</label>
         <div class="col-md-6">
             <g:select class="form-control"  name='dinnerFoodId' required="required"
                       noSelection="${['':'Select Package....']}"
                       from='${com.diarobo.api.RemoteController.foodPackageList()}'
                       optionKey="id" optionValue="packageName" value="${mealPlan?.dinnerFoodId}" />
         </div>
         <div  class="col-md-3">
             <div class="input-group date timePicker">
                 <input type="text" name="dinnerFoodTime" class="form-control" placeholder="Time" value="${mealPlan?.dinnerFoodTime}"/>
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-time"></span>
                 </span>
             </div>
         </div>
     </div>

     <div class="form-group">
         <label class="col-md-12">Before Sleep (At least 1.5-2 hours after dinner)</label>
     </div>
     <div class="form-group row">
         <label class="col-md-3" for="beforeSleepFoodId"></label>
         <div class="col-md-6">
             <g:select class="form-control"  name='beforeSleepFoodId' required="required"
                       noSelection="${['':'Select Package....']}"
                       from='${com.diarobo.api.RemoteController.foodPackageList()}'
                       optionKey="id" optionValue="packageName" value="${mealPlan?.beforeSleepFoodId}"/>
         </div>
         <div  class="col-md-3">
             <div class="input-group date timePicker" >
                 <input type="text" name="beforeSleepFoodTime" required class="form-control" placeholder="Time" value="${mealPlan?.beforeSleepFoodTime}"/>
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-time"></span>
                 </span>
             </div>
         </div>
     </div>
    </div>
        <div class="form-group text-center">
            <button class="btn btn-default" type="submit">Save & Next</button>
        </div>

</g:form>
<script>
    $(function () {
        $('.timePicker').datetimepicker({
            format: 'LT'
        });
    });
</script>
