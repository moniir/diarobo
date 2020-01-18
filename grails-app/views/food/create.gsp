<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Food</title>
    <style>
    .compositionName {
        padding-left: 10px;
    }
    </style>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'food', action: 'index')}">Edit</a></li>
    <li class="active">Create</li>
</ol>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-list">Food
                    <span class="pull-right"><a title="Back" href="${createLink(controller: 'food', action: 'index')}"><i class="fa fa-reply" aria-hidden="true"></i></a></span>
                </div>
            </div>
            <div class="panel-body">
            <g:form controller="food" action="save" method="POST" enctype="multipart/form-data">
                <div class="col-md-12 col-sm-12">
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                    <fieldset class="form-horizontal">
                        <g:render template="form" model="[measurementUnits: measurementUnits, netWeightUnits: netWeightUnits, type: 'create']"></g:render>
                    </fieldset>
                </div>
            </g:form>
            </div>
        </div>
    </div>
</div>


<script>
    $( document ).ready(function() {
        initNumeric();
        $('#nutritionList').on('click', 'div  label .fa-close', function () {
            $(this).closest('label').parent('div').remove();
        });
        $('#compositionList').on('click', 'div  label .fa-close', function () {
            $(this).closest('label').parent('div').remove();
        });

        var foodGroupSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'food', action: 'searchNameByKeyword')}?q=%QUERY",
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
        var foodGroupSearch = $('#foodGroup').typeahead({
                    minLength: 2
            },
            {
                name: 'foodGroupData',
                source: foodGroupSource,
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
                foodGroupSearch.typeahead('val', datum.value);
            });

        var foodSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'food', action: 'searchFoodName')}?q=%QUERY",
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
        var foodSearch = $('#name').typeahead({
                minLength: 2
            },
            {
                name: 'foodData',
                source: foodSource,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}}-Already added</p></div>')
                }
            })
            .on('typeahead:selected', function(obj, datum){
                foodSearch.typeahead('val', '');
            });

            var foodNutritionSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'food', action: 'searchNutritionName')}?q=%QUERY",
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
        var foodNutritionSearch = $('#nutrition').typeahead({
                minLength: 2
            },
            {
                name: 'foodNutritionData',
                source: foodNutritionSource,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}}-Already added</p></div>')
                }
            })
            .on('typeahead:selected', function(obj, datum){
                foodNutritionSearch.typeahead('val', datum.value);
            });


        var foodCompositionSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'food', action: 'searchCompositionName')}?q=%QUERY",
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
        var foodCompositionSearch = $('#compositionName').typeahead({
                minLength: 2
            },
            {
                name: 'foodCompostionData',
                source: foodCompositionSource,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}}-Already added</p></div>')
                }
            })
            .on('typeahead:selected', function(obj, datum){
                foodCompositionSearch.typeahead('val',  datum.value);
            });
    });
</script>
</body>
</html>
