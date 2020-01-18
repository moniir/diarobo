<div class="form-group">
    <label class="col-md-2 control-label" for="genericName">Generic Name</label>
    <div class="col-md-8">
        <g:if test="${type == 'create'}">
            <input id="genericName" name="genericName" type="text" value="${brand?.medGeneric?.genericName}"
                   placeholder="generic Name" class="form-control typeahead">

        </g:if>
        <g:else>
            <input name="genericName" type="text" placeholder="generic name" readonly
                   class="form-control typeahead" required="" value="${brand?.medGeneric?.genericName}">
        </g:else>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label" for="companyName">Company</label>
    <div class="col-md-3" id="companyHolder">
        <g:if test="${type == 'create'}">
            <input id="companyName" name="companyName" type="text" value="${brand?.medCompany?.companyName}"
                   placeholder="Name" class="form-control typeahead">
        </g:if>
        <g:else>
            <input id="companyName" name="companyName" type="text" value="${brand?.medCompany?.companyName}"
                   placeholder="Name" class="form-control typeahead" readonly>
        </g:else>
    </div>
    <div class="col-md-3">
        <div>
            <input id="brandName" name="brandName" type="text" value="${brand?.brandName}"
                   placeholder="Brand Name" class="form-control typeahead">
        </div>
    </div>
</div>
<input name="id" value="${brand?.id}" class="hidden">
<div class="form-group">
    <label class="col-md-2 control-label" for="image">Image</label>
    <div class="col-md-8">
        <g:if test="${type == 'create'}">
            <input id="image" name="image" type="file" class="form-control input-md"
                   required="">
        </g:if>
        <g:else>
            <input id="image" name="image" type="file" class="form-control input-md">
        </g:else>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label">Medicine Type</label>
    <div class="col-md-8">
        <g:each var="medicineList" in="${medicineAdminList}">
            <label class="radio-inline">
                <g:if test="${medicineList?.id == brand?.medGeneric?.medicineType}">
                    <g:radio id="${medicineList?.id}" type="radio" name="medicineType" checked="checked" value="${medicineList?.id}"/>
                </g:if>
                <g:else>
                    <g:radio id="${medicineList?.id}" type="radio" name="medicineType" value="${medicineList?.id}"/>
                </g:else>
                ${medicineList?.name}
            </label>
        </g:each>
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label" for="sideEffect">Side Effects</label>
    <div class="col-md-8">
        <textarea name="sideEffect" placeholder="Write Text here..."
                  class="form-control input-md" required="">${brand?.medGeneric?.sideEffect}</textarea>
    </div>
</div>

<script>
    $( document ).ready(function() {
        var companyNameSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'medicine', action: 'searchCompanyName')}?q=%QUERY",
                transform: function(response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function(talentdata) {
                        return {
                            value: talentdata.companyName
                        };
                    });
                }
            }
        });
        var companyNameSearch = $('#companyName').typeahead({
                minLength: 2
            },
            {
                name: 'companyNameData',
                source: companyNameSource,
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
                companyNameSearch.typeahead('val', datum.value);
            });


        var genericNameSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'medicine', action: 'searchGenericName')}?q=%QUERY",
                transform: function(response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function(talentdata) {
                        return {
                            value: talentdata.genericName,
                            type: talentdata.medicineType
                        };
                    });
                }
            }
        });


        var genericNameSearch = $('#genericName').typeahead({
                minLength: 2
            },
            {
                name: 'genericNameData',
                source: genericNameSource,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}} - {{type}}</p></div>')
                }
            })
            .on('typeahead:selected', function(obj, datum){
                genericNameSearch.typeahead('val', datum.value);
            });


        var brandNameSource = new Bloodhound({
            datumTokenizer: function(datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "${g.createLink(controller: 'medicine', action: 'searchBrandName')}?q=%QUERY",
                transform: function(response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function(talentdata) {
                        return {
                            value: talentdata.brandName
                        };
                    });
                }
            }
        });

        var brandNameSearch = $('#brandName').typeahead({
                minLength: 2
            },
            {
                name: 'brand' +
                'NameData',
                source: brandNameSource,
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
                brandNameSearch.typeahead('val', datum.value);
            });


    });
</script>