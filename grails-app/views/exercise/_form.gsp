<p id="errorMessage" class="errorHandler alert alert-danger hidden"></p>
<input type="number" id="exerciseId" class="hidden" value="${exerciseLibraryInstance?.id}">
<div class="form-group" id="showName">
    <label class="col-md-2 control-label" for="itemName">Name</label>
    <div class="col-md-8">
        <input id="name" name="name" value="${exerciseLibraryInstance?.name}" type="text" placeholder="Name"  class="form-control" required="">
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label" for="image">Image</label>
    <div class="col-md-8">
        <g:if test="${exerciseLibraryInstance != null}">
            <input id="image" name="image" type="file"  class="form-control input-md" required="">
        </g:if>
        <g:else>
            <input id="image" name="image" type="file"  class="form-control input-md" required="">
        </g:else>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label">Measurement Unit</label>
    <div class="col-md-8">
        <g:each in="${measurementUnits}" var="netWeightUnit">
            <label class="radio-inline">
                <g:if test="${exerciseLibraryInstance?.weightMeasure == netWeightUnit?.id}">
                    <input type="radio" name="weightMeasure" value="${netWeightUnit?.id}" checked>
                </g:if>
                <g:else>
                    <input type="radio" name="weightMeasure" value="${netWeightUnit?.id}">
                </g:else>
                ${netWeightUnit?.name}
            </label>
        </g:each>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label">Type</label>
    <div class="col-md-8">
        <g:each in="${exerciseTypes}" var="measurementUnit">
            <label class="radio-inline">
                <g:if test="${exerciseLibraryInstance?.measureUnit == measurementUnit?.id}">
                    <input type="radio" name="measureUnit" value="${measurementUnit?.id}" checked>
                </g:if>
                <g:else>
                    <input type="radio" name="measureUnit" value="${measurementUnit?.id}">
                </g:else>
                ${measurementUnit?.name}
            </label>
        </g:each>
    </div>
</div>
<!-- Text input-->
<div class="form-group">
    <label class="col-md-2 control-label" for="calorieBurn">Calorie burn</label>
    <div class="col-md-3">
        <input id="calorieBurn" name="calorieBurn" type="number" value="${exerciseLibraryInstance?.calorieBurn}" placeholder="total calorie" class="form-control input-md" required="">
    </div>
    <label class="col-md-2 control-label" for="perInMeasure">Per</label>
    <div class="col-md-3">
        <input id="perInMeasure" name="perInMeasure" type="number" value="${exerciseLibraryInstance?.perInMeasure}" placeholder="per" class="form-control input-md" required="">
    </div>
</div>

<div class="form-group">
    <label class="col-md-2 control-label" for="notApplicable">Not Applicable</label>
    <div class="col-md-6">
        <input id="notApplicable"  onkeypress="inputOnEnterKeyPress($('#notApplicableList'),$('#notApplicable'),event)" type="text" placeholder="Not Applicable" class="form-control notApplicable" required="">
    </div>
    <div class="col-md-2">
        <div class="form-group">
            <button onclick="addElementToList($('#notApplicableList'), $('#notApplicable'))" class="btn btn-default center-block">+ Add more</button>
        </div>
    </div>
</div>
<div id="notApplicableList">
    <g:each in="${natApplicableList}" var="notApplicable">
        <div class="form-group">
            <label class="col-md-2 control-label"><i class="fa fa-close"></i></label>
            <div class="col-md-4">
                <input type="number" class="hidden id" value="${notApplicable?.id}">
                <input id="nutritions" name="nutritions" type="text" class="nutrition form-control" value="${notApplicable?.name}">
            </div>
        </div>
    </g:each>
</div>


<script>
    function saveExercise(type) {
        var name = $('#name').val();
        var id = $('#exerciseId').val();
        var calorieBurn = $('#calorieBurn').val();
        var perInMeasure = $('#perInMeasure').val();
        var weightMeasure = $("input[name='weightMeasure']:checked").val();
        var measureUnit = $("input[name='measureUnit']:checked").val();
        var notApplicable = '';
        $('#notApplicableList').find('.nutrition').each(function () {
            var notApplicableText = $(this).val();
            if(notApplicableText) {
                notApplicable += notApplicableText + '~' ;
            }
        });
        var notApplicableIds = '';
        $('#notApplicableList').find('.id').each(function () {
            var notApplicableId = $(this).val();
            if(notApplicableId) {
                notApplicableIds += notApplicableId + '~' ;
            }
        });
        var dataWithImage = new FormData();
        dataWithImage.append('id', id);
        dataWithImage.append('name', name);
        dataWithImage.append('calorieBurn', calorieBurn);
        dataWithImage.append('perInMeasure', perInMeasure);
        dataWithImage.append('weightMeasure', weightMeasure);
        dataWithImage.append('measureUnit', measureUnit);
        dataWithImage.append('notApplicable', notApplicable);
        dataWithImage.append('notApplicableIds', notApplicableIds);
        dataWithImage.append('image', $("#image")[0].files[0]);
        if(name && calorieBurn && perInMeasure && weightMeasure && measureUnit) {
            $.ajax({
                url: type == 'create' ? "${createLink(controller:'exercise', action: 'create')}" : "${createLink(controller:'exercise', action: 'update')}",
                data: dataWithImage,
                type: 'post',
                processData : false,
                contentType : false,
                success: function(res){
                    $('#errorMessage').html(res.message);
                    $('#errorMessage').removeClass('hidden');
                },
                error: function(res){
                    $('#errorMessage').html(res.error());
                    $('#errorMessage').removeClass('hidden');
                }
            });
        } else {
            $('#errorMessage').html('Please input all information & try again.');
            $('#errorMessage').removeClass('hidden');
        }
    }

    $('#notApplicableList').on('click', '.fa-close', function () {
        $(this).closest('.form-group').remove();
    });

    var exerciseNameSource = new Bloodhound({
        datumTokenizer: function(datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "${g.createLink(controller: 'exercise', action: 'searchNameByKeyword')}?q=%QUERY",
            transform: function(response) {
                // Map the remote source JSON array to a JavaScript object array
                return $.map(response.results, function(talentdata) {
                    return {
                        value: talentdata.name
                    };
                });
            }
        }
    });
    var exerciseNameSearch = $('#name').typeahead({
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
        });

    var exerciseNotApplicableSource = new Bloodhound({
        datumTokenizer: function(datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "${g.createLink(controller: 'exercise', action: 'searchNotApplicableByKeyword')}?q=%QUERY",
            transform: function(response) {
                // Map the remote source JSON array to a JavaScript object array
                return $.map(response.results, function(talentdata) {
                    return {
                        value: talentdata.name
                    };
                });
            }
        }
    });
    var exerciseNotApplicableSearch = $('#notApplicable').typeahead({
            minLength: 2
        },
        {
            name: 'foodNameData',
            source: exerciseNotApplicableSource,
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
            exerciseNotApplicableSearch.typeahead('val',datum.value);
        });
</script>



