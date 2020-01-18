<p id="errorMessage" class="errorHandler alert alert-danger hidden"></p>
<div class="form-group"></div>
<!-- Text input-->
<div class="form-group">
    <label for="foodGroup" class="col-md-2 control-label">
        Food Group
    </label>
    <div class="col-sm-9 col-md-9 col-lg-9">
        <g:if test="${type == 'create'}">
            <input name="foodGroup" id="foodGroup" value="${foodLibraryInstance?.groupItem?.name}" type="text" placeholder="Food Group" class="form-control input-md typeahead custom-typeahead-control">
        </g:if>
        <g:else>
            <input name="foodGroup" id="foodGroup" value="${foodLibraryInstance?.groupItem?.name}" type="text" placeholder="Food Group" class="form-control input-md typeahead custom-typeahead-control" readonly>
        </g:else>
    </div>
</div>

<div class="form-group">
    <label class="col-md-2 control-label" for="name">Name</label>
    <div class="col-md-8">
        <g:if test="${type == 'create'}">
            <input id="name" name="name" type="text" value="${foodLibraryInstance?.name}" placeholder="Name" class="form-control input-md typeahead" required="">
        </g:if>
        <g:else>
            <input id="name" name="name" type="text" value="${foodLibraryInstance?.name}" placeholder="Name" class="form-control  input-md typeahead" required="" readonly>
        </g:else>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label" for="foodImage">Image</label>
    <div class="col-md-8">
        <g:if test="${type == 'create'}">
            <input id="foodImage" name="foodImage" type="file" class="form-control input-md" required="">
        </g:if>
        <g:else>
            <input id="foodImage" name="foodImage" type="file" class="form-control input-md">
            <input name="id" type="number" class="form-control hidden" value="${foodLibraryInstance?.id}">
        </g:else>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label">Net Weight</label>
    <div class="col-md-8">
        <g:each in="${netWeightUnits}" var="netWeightUnit">
            <label class="radio-inline">
                <g:if test="${foodLibraryInstance?.weightMeasure == netWeightUnit?.id}">
                    <input type="radio" name="weightMeasure" id="weightMeasure" checked="checked" value="${netWeightUnit?.id}">
                </g:if>
                <g:else>
                    <input type="radio" name="weightMeasure" id="weightMeasure" value="${netWeightUnit?.id}">
                </g:else>

                ${netWeightUnit?.name}
            </label>
        </g:each>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label">Measurement Unit</label>
    <div class="col-md-3">
        <g:each in="${measurementUnits}" var="measurementUnit">
            <label class="radio-inline">
                <g:if test="${foodLibraryInstance?.measureUnit == measurementUnit?.id}">
                    <input type="radio" name="measureUnit" id="measureUnit" checked="checked" value="${measurementUnit?.id}">
                </g:if>
                <g:else>
                    <input type="radio" name="measureUnit" id="measureUnit" value="${measurementUnit?.id}">
                </g:else>
                ${measurementUnit?.name}
            </label>
        </g:each>
    </div>
    <div class="col-md-5">
        <input id="approximateWeight" name="approximateWeight" value="${foodLibraryInstance?.approximateWeight}" placeholder="Approx. Weight" type="text" class="form-control numericfloat" required="">
    </div>
</div>

<div class="form-group">
    <label class="col-md-2 control-label" for="nutrition">Nutrition</label>
    <div class="col-md-6">
        <input id="nutrition" onkeypress="inputOnEnterKeyPress($('#nutritionList'),$('#nutrition'),event)" name="nutrition" type="text" placeholder="Nutrition" class="form-control typeahead">
    </div>
    <div class="col-md-2">
        <div class="form-group">
            <a href="javascript:void(0)" onclick="addElementToList($('#nutritionList'), $('#nutrition'))" class="btn btn-default">
                + Add more
            </a>
        </div>
    </div>
</div>
<div id="nutritionList">
    <g:each in="${nutritionList}" var="nutrition">
        <div class="form-group">
            <label class="col-md-2 control-label"><i class="fa fa-close"></i></label>
            <div class="col-md-4">
                <input id="nutritions" name="nutritions" type="text" class="nutrition form-control typeahead-nutrition input-md" value="${nutrition?.name}">
            </div>
        </div>
    </g:each>
</div>

<div class="form-group">
    <label class="col-md-2 control-label" for="compositionName">Composition</label>
    <div class="col-md-3">
        <input id="compositionName" name="compositionName"  type="text" placeholder="Name" class="form-control  input-md typeahead" >
    </div>
    <div class="col-md-3">
        <input id="compositionWeight" name="compositionWeight"   type="text" placeholder="Weight" class="form-control input-md numericfloat">
    </div>
    <div class="col-lg-2">
        <div class="form-group">
            <a href="javascript:void(0)" onclick="addElementToListComposition($('#compositionList'), $('#compositionName'), $('#compositionWeight'))" class="btn btn-default">
                + Add more
            </a>
        </div>
    </div>
</div>

<div id="compositionList">
    <g:each in="${compositionList}" var="composition" status="compositionStatus">
        <div class="form-group">
            <input type="hidden" class="compositionCount" name="compositions" value="${compositionStatus + 1}"/>
            <label class="col-md-2 control-label"><i class="fa fa-close"></i></label>
            <div class="col-md-2">
                <input id="compositionName${compositionStatus + 1}" name="compositionName${compositionStatus + 1}" type="text" class="form-control" value="${composition?.name}">
            </div>
            <div class="col-md-2">
                <input id="compositionWeight${compositionStatus + 1}" name="compositionWeight${compositionStatus + 1}" type="text" class="form-control" value="${composition?.quantity}">
            </div>
        </div>
    </g:each>
</div>

<!-- Button -->
<div class="form-group" id="actionBtns">
    <label class="col-md-2 control-label"></label>
    <div class="col-md-8">
        <button class="btn btn-primary submitBtn">SUBMIT</button>
    </div>
</div>