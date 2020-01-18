var autoSearch = {
    getPatientUsernameWithMobileSource : function () {
        var patientSource = new Bloodhound({
            datumTokenizer: function (datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "/prescription/searchPatientByNameOrPhoneNumber?q=%QUERY",
                transform: function (response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function (talentdata) {
                        return {
                            value: talentdata.fullName + '-' + talentdata.username
                        };
                    });
                }
            }
        });
        return patientSource;
    },

    getPatientUsernameByUsernameOrMobile : function () {
        var patientSource = new Bloodhound({
            datumTokenizer: function (datum) {
                return Bloodhound.tokenizers.whitespace(datum.value);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                wildcard: '%QUERY',
                url: "/prescription/searchPatientByNameOrPhoneNumber?q=%QUERY",
                transform: function (response) {
                    // Map the remote source JSON array to a JavaScript object array
                    return $.map(response.results, function (talentdata) {
                        return {
                            value: talentdata.fullName + '-' + talentdata.username,
                            user: talentdata.username
                        };
                    });
                }
            }
        });
        return patientSource;
    },

    searchEngine : function (element, source, needTrigger) {
        var search = element.typeahead({
                minLength: 2
            },
            {
                name: 'data',
                source: source,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}}</p></div>')
                }
            })
            .on('typeahead:selected', function (obj, datum) {
                search.typeahead('val', datum.value);
                if(needTrigger) {
                    angular.element(element).triggerHandler('input');
                }
            });
    },
    searchPatient : function (element, usernameElement, needTrigger) {
        var search = element.typeahead({
                minLength: 2
            },
            {
                name: 'data',
                source: autoSearch.getPatientUsernameByUsernameOrMobile(),
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        'No results matching your search',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><p>{{value}}</p></div>')
                }
            })
            .on('typeahead:selected', function (obj, datum) {
                if(usernameElement) {
                    usernameElement.val(datum.user);
                    if(needTrigger) {
                        angular.element(usernameElement).triggerHandler('input');
                    }
                }
                search.typeahead('val', datum.value);
                if(needTrigger) {
                    angular.element(element).triggerHandler('input');
                }
            });
    }

};