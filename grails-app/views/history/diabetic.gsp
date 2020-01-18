<%@ page import="com.diarobo.admin.GroupItem; com.diarobo.enums.DiabeticType; com.diarobo.enums.DiseaseType; com.diarobo.enums.DiseaseCategory; com.diarobo.PatientDisease" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'history', action: 'diabetic')}">Diabetic History</a></li>
    <li class="active">Create</li>
</ol>
<div class="row ">
    <div class="col-md-12">
        <g:if test='${flash.message}'>
            <div class="errorHandler alert alert-danger">
                <i class="fa fa-remove-sign"></i>
                ${flash.message}
            </div>
        </g:if>
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-left">Diabetic History</div>
                <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_blank">${message(code:'app.project.name.title' )}</a></div>
            </div>
            <div class="panel-body padding-top-form">
                <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                    <div class="row">
                        <div class="col-md-12">
                        <legend>Family Diabetic History</legend>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <span><strong>Who has/had diabetes?</strong></span>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <select id="familyHistory" multiple="multiple">
                                        <g:each in="${familyRelation}" var="family">
                                            <g:if test="${family.previousPresence == true}">
                                                <option value="${family.id}" selected>${family.name}</option>
                                            </g:if>
                                            <g:else>
                                                <option value="${family.id}">${family.name}</option>
                                            </g:else>
                                        </g:each>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <legend>Patient Diabetic History</legend>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <span><strong>Diabetic Type</strong></span>
                            </div>
                            <div class="col-md-8">
                            <select class="form-control" id="diabeticTye">
                                <option value="">Select Diabetic Type</option>
                                <g:each in="${com.diarobo.enums.DiabeticType.values()}" var="diabeticTyp">
                                    <g:if test="${diabeticHistory?.diabeticType == diabeticTyp.key}">
                                        <option value="${diabeticTyp.key}" selected>${diabeticTyp.value}</option>
                                    </g:if>
                                    <g:else>
                                        <option value="${diabeticTyp.key}">${diabeticTyp.value}</option>
                                    </g:else>
                                </g:each>
                            </select>
                            </div>
                         </div>
                     </div><br>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                 <span><strong>Microvascular related complications</strong></span>
                            </div>
                            <div class="col-md-8">
                                <div class="col-md-2 leftRightNoPadding"><Strong>Typical</Strong></div>
                                <div class="col-md-10 leftRightNoPadding">
                                    <div class="form-group">
                                        <select class="form-control" id="typicalMicrovascular" multiple="multiple">
                                            <g:each in="${microvascularDisease}" var="diabeticDisease">
                                                <g:set var="priviouslyPresent" value="${false}"></g:set>
                                                <g:each in="${PatientDisease.findAllByActiveStatusAndDiabeticHistoryIdAndParentTypeAndGrandParentType(true, diabeticHistory?.id, DiseaseType.TIPICAL.value, DiseaseCategory.MICROVASCULAR.value)}" var="previousDisease">
                                                    <g:if test="${previousDisease?.diseaseId == diabeticDisease?.id}">
                                                        <g:set var="priviouslyPresent" value="${true}"></g:set>
                                                    </g:if>
                                                </g:each>
                                                <g:if test="${priviouslyPresent}">
                                                    <option value="${diabeticDisease.id}" selected>${diabeticDisease.name}</option>
                                                </g:if>
                                                <g:else>
                                                    <option value="${diabeticDisease.id}">${diabeticDisease.name}</option>
                                                </g:else>
                                            </g:each>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2 leftRightNoPadding"><Strong>Atypical</Strong></div>
                                <div class="col-md-10 leftRightNoPadding">
                                    <div class="form-group">
                                        <select class="form-control" id="atypicalMicrovascular" multiple="multiple">
                                            <g:each in="${microvascularDisease}" var="diabeticDisease">
                                                <g:set var="priviouslyPresent" value="${false}"></g:set>
                                                <g:each in="${PatientDisease.findAllByActiveStatusAndDiabeticHistoryIdAndParentTypeAndGrandParentType(true, diabeticHistory?.id, DiseaseType.ATIPICAL.value, DiseaseCategory.MICROVASCULAR.value)}" var="previousDisease">
                                                    <g:if test="${previousDisease?.diseaseId == diabeticDisease?.id}">
                                                        <g:set var="priviouslyPresent" value="${true}"></g:set>
                                                    </g:if>
                                                </g:each>
                                                <g:if test="${priviouslyPresent}">
                                                    <option value="${diabeticDisease.id}" selected>${diabeticDisease.name}</option>
                                                </g:if>
                                                <g:else>
                                                    <option value="${diabeticDisease.id}">${diabeticDisease.name}</option>
                                                </g:else>
                                            </g:each>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <span><strong>Macrovascular related complications</strong></span>
                            </div>
                            <div class="col-md-8">
                                <div class="col-md-2 leftRightNoPadding"><Strong>Typical</Strong></div>
                                <div class="col-md-10 leftRightNoPadding">
                                    <div class="form-group">
                                        <select class="form-control" id="typicalMacrovascular" multiple="multiple">
                                            <g:each in="${macrovascularDisease}" var="diabeticDisease">
                                                <g:set var="priviouslyPresent" value="${false}"></g:set>
                                                <g:each in="${PatientDisease.findAllByActiveStatusAndDiabeticHistoryIdAndParentTypeAndGrandParentType(true, diabeticHistory?.id, DiseaseType.TIPICAL.value, DiseaseCategory.MACROVASCULAR.value)}" var="previousDisease">
                                                    <g:if test="${previousDisease?.diseaseId == diabeticDisease?.id}">
                                                        <g:set var="priviouslyPresent" value="${true}"></g:set>
                                                    </g:if>
                                                </g:each>
                                                <g:if test="${priviouslyPresent}">
                                                    <option value="${diabeticDisease.id}" selected>${diabeticDisease.name}</option>
                                                </g:if>
                                                <g:else>
                                                    <option value="${diabeticDisease.id}">${diabeticDisease.name}</option>
                                                </g:else>
                                            </g:each>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2 leftRightNoPadding"><Strong>Atypical</Strong></div>
                                <div class="col-md-10 leftRightNoPadding">
                                    <div class="form-group">
                                        <select class="form-control" id="atypicalMacrovascular" multiple="multiple">
                                            <g:each in="${macrovascularDisease}" var="diabeticDisease">
                                                <g:set var="priviouslyPresent" value="${false}"></g:set>
                                                <g:each in="${PatientDisease.findAllByActiveStatusAndDiabeticHistoryIdAndParentTypeAndGrandParentType(true, diabeticHistory?.id, DiseaseType.ATIPICAL.value, DiseaseCategory.MACROVASCULAR.value)}" var="previousDisease">
                                                    <g:if test="${previousDisease?.diseaseId == diabeticDisease?.id}">
                                                        <g:set var="priviouslyPresent" value="${true}"></g:set>
                                                    </g:if>
                                                </g:each>
                                                <g:if test="${priviouslyPresent}">
                                                    <option value="${diabeticDisease.id}" selected>${diabeticDisease.name}</option>
                                                </g:if>
                                                <g:else>
                                                    <option value="${diabeticDisease.id}">${diabeticDisease.name}</option>
                                                </g:else>
                                            </g:each>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <span><strong>Disease Associated with Diabetics</strong></span>
                            </div>
                            <div class="col-md-5 leftRightNoPadding">
                                <input id="diabeticDisease" data-id="" onkeypress="inputOnEnterKeyPressDiabeticHistory($('#diabeticDiseaseList'),$('#diabeticDisease'),event)" name="diabeticDisease" type="text" class="form-control typeahead"/>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <button onclick="addElementToListDiabeticHistory($('#diabeticDiseaseList'), $('#diabeticDisease'))" class="btn btn-default center-block">+ Add more</button>
                                </div>
                            </div>
                            <div class="col-md-3"></div>
                            <div class="col-md-9">
                                <div id="diabeticDiseaseList">
                                    <ul>
                                        <g:each in="${diabeticAssociatedDisease}" var="associatedDisease">
                                            <li class="element-li"><i class="fa fa-close"></i> <span class="newText" data-id="${associatedDisease?.diseaseId}">${GroupItem.findById(associatedDisease?.diseaseId).name}</span></li>
                                        </g:each>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group text-center">
                <button class="btn btn-default cancel-btn" tabindex="7" onclick="saveDiabeticHistory()">Save</button>
                <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Save & Next</button>
             </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('#diabeticDiseaseList').on('click', 'ul li .fa-close', function () {
        $(this).closest('li').remove();
    });
    $('#typicalMicrovascular').multiselect({includeSelectAllOption: true, buttonWidth: '100%'});
    $('#atypicalMicrovascular').multiselect({includeSelectAllOption: true, buttonWidth: '100%'});
    $('#typicalMacrovascular').multiselect({includeSelectAllOption: true, buttonWidth: '100%'});
    $('#atypicalMacrovascular').multiselect({includeSelectAllOption: true, buttonWidth: '100%'});
    $('#familyHistory').multiselect({includeSelectAllOption: true, buttonWidth: '100%'});

    var diseaseSource = new Bloodhound({
        datumTokenizer: function(datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "${g.createLink(controller: 'history', action: 'searchDiseasesByKeyword')}?q=%QUERY",
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
    var diseaseSearch = $('.typeahead').typeahead({
            minLength: 2
        },
        {
            name: 'diseaseData',
            source: diseaseSource,
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
            diseaseSearch.typeahead('val',datum.value);
            $('#diabeticDisease').attr('data-id', datum.id);
        });
    function saveDiabeticHistory() {
        var diabeticHistory = {}, familyHistory = {};
        if($('#typicalMicrovascular').val()) {
            $('#typicalMicrovascular').val().forEach(function (element) {
                var disease = {'diseaseId' : element, 'parentType' : '${DiseaseType.TIPICAL.value}', 'grandParentType' : '${DiseaseCategory.MICROVASCULAR.value}'};
                diabeticHistory['${DiseaseType.TIPICAL.value}' + element] = disease;
            });
        }
        if($('#atypicalMicrovascular').val()) {
            $('#atypicalMicrovascular').val().forEach(function (element) {
                var disease = {'diseaseId' : element, 'parentType' : '${DiseaseType.ATIPICAL.value}', 'grandParentType' : '${DiseaseCategory.MICROVASCULAR.value}'};
                diabeticHistory['${DiseaseType.ATIPICAL.value}' + element] = disease;
            });
        }
        if($('#typicalMacrovascular').val()) {
            $('#typicalMacrovascular').val().forEach(function (element) {
                var disease = {'diseaseId' : element, 'parentType' : '${DiseaseType.TIPICAL.value}', 'grandParentType' : '${DiseaseCategory.MACROVASCULAR.value}'};
                diabeticHistory['${DiseaseType.TIPICAL.value}' + element] = disease;
            });
        }
        if($('#atypicalMacrovascular').val()) {
            $('#atypicalMacrovascular').val().forEach(function (element) {
                var disease = {'diseaseId' : element, 'parentType' : '${DiseaseType.ATIPICAL.value}', 'grandParentType' : '${DiseaseCategory.MACROVASCULAR.value}'};
                diabeticHistory['${DiseaseType.ATIPICAL.value}' + element] = disease;
            });
        }
        if($('#familyHistory').val()) {
            $('#familyHistory').val().forEach(function (element) {
                familyHistory[element] = element;
            });
        }

        $('#diabeticDiseaseList').find('ul li .newText').each(function (element) {
            var associatedDisease = $(this).attr('data-id');
            if (associatedDisease) {
                var disease = {'diseaseId' : associatedDisease, 'parentType' : null, 'grandParentType' : '${DiseaseCategory.DIABETIC_ASSOCIATED.value}'};
                diabeticHistory[associatedDisease] = disease;
            }
        });

        var data ={};
        if(familyHistory) {
            data['familyHistory'] = familyHistory;
        }
        data['diabeticHistory'] = diabeticHistory;
        var diabeticTye = $('#diabeticTye').val();
        if(diabeticTye) {
            data['diabeticTye'] = diabeticTye;
        }
        if(data) {
            window.location.href = '<g:createLink controller="history" action="saveDiabetic" />?data=' + JSON.stringify(data);
        }
    }
</script>
</body>
</html>
