var app = angular.module('prescription', []);
var prescription;
var medication;
var advancePrescription;
var requiredMessage = 'Please Fill all information & try again.';
app.controller('prescriptionController', function($http, $scope) {
    prescription = this;
    prescription.profileImage = '/assets/demo_profile_picture.png';

    prescription.patientNameOrMobile;
    prescription.patientAge;
    prescription.patientWeight;
    prescription.patientGender;
    prescription.patientBmi;
    prescription.patientDiabeticType;
    prescription.patientBp;
    prescription.doctorName;
    prescription.phoneNumber;
    prescription.doctorDesignation;
    prescription.doctorHospitalChamber;

    prescription.prescriptionDate;
    prescription.patientUsername;
    prescription.doctorId;
    prescription.id;
    prescription.prescriptionList = [];

    prescription.drugTimes = [];
    prescription.drugTimes['OD'] = 1;
    prescription.drugTimes['BD'] = 2;
    prescription.drugTimes['TDS'] = 3;
    prescription.drugTimes['QDS'] = 4;
    prescription.drugTimes['5 Times'] = 5;

    prescription.showPackageDetails = function (packageId) {
        $http.get('/prescription/getDetailsPackage?packageId=' + packageId).then(function (response) {
                messageModal('', response.data, '');
        });
    };

    prescription.showExercisePackageDetails = function (packageId) {
        $http.get('/prescription/getExercisePackageDetails?packageId=' + packageId).then(function (response) {
            messageModal('', response.data, '');
        });
    };

    prescription.setActive = function (prescriptionId) {
        $http.get('/prescription/setActivePrescription?prescriptionId='+ prescriptionId + '&patientUserName=' + prescription.patientUsername) .then(function(response) {
            if(!response.data.hasError) {
                showSuccessMsg(response.data.message);
            } else {
                showErrorMsg(response.data.message);
            }
        });
    };

    prescription.copyPrescription = function (prescriptionId) {
        $http.get('/prescription/copyPrescription?prescriptionId='+ prescriptionId) .then(function(response) {
            if(!response.data.hasError) {
                showSuccessMsg(response.data.message);
            } else {
                showErrorMsg(response.data.message);
            }
        });
    };
});

app.controller('basicInformationController', function($http, $scope) {
    var basicInfo = this;
    advancePrescription.breakfastTime = medicationTryTimes[0];
    advancePrescription.lunchTime = medicationTryTimes[1];
    advancePrescription.dinnerTime = medicationTryTimes[2];
    advancePrescription.beforeLunchTime = mealSnackTimes[0];
    advancePrescription.beforeDinnerTime = mealSnackTimes[1];
    advancePrescription.beforeSleepTime = mealSnackTimes[2];
    basicInfo.setBp = function () {
        prescription.patientBp = basicInfo.patientBp;
    };

    basicInfo.setPatient = function () {
        if(basicInfo.patientNameOrMobile) {
            var patientNamePhoneNumber = basicInfo.patientNameOrMobile.split('-');
            username = patientNamePhoneNumber[patientNamePhoneNumber.length -1];
            if(username) {
                prescription.patientUsername = username;
                var dataObj = {
                    username : username
                };
                $http.post('/prescription/getPatientDetails', dataObj) .then(function(response) {
                    if(response.data.age) {
                        var responsedata = response.data;
                        prescription.profileImage = '/prescription/showProfileImage?patientProfileId=' + responsedata.patientProfileId;
                        prescription.patientNameOrMobile = basicInfo.patientNameOrMobile;
                        prescription.patientAge = responsedata.age;
                        basicInfo.patientWeight = prescription.patientWeight = responsedata.weight;
                        prescription.patientGender = responsedata.gender;
                        basicInfo.patientBmi = prescription.patientBmi = responsedata.bmi;
                        prescription.patientBp = basicInfo.patientBp;
                        prescription.prescriptionDate = responsedata.prescriptionDate ? responsedata.prescriptionDate : new Date();
                        prescription.id = responsedata.prescriptionId;
                        prescription.patientDiabeticType = responsedata.diabeticType;
                        prescription.prescriptionList = responsedata.prescriptionList ? responsedata.prescriptionList : []
                        advancePrescription.drugList = responsedata.medication ? responsedata.medication : [];
                        if(responsedata.hasOwnProperty('recommendMeal')) {
                            var responseMeal = responsedata.recommendMeal;
                            if(responseMeal.breakfastTime) {
                                advancePrescription.breakfastTime = responseMeal.breakfastTime;
                            }
                            if(responseMeal.lunchTime) {
                                advancePrescription.lunchTime = responseMeal.lunchTime;
                            }
                            if(responseMeal.dinnerTime) {
                                advancePrescription.dinnerTime = responseMeal.dinnerTime;
                            }
                            if(responseMeal.beforeLunchTime) {
                                advancePrescription.beforeLunchTime = responseMeal.beforeLunchTime;
                            }
                            if(responseMeal.beforeDinnerTime) {
                                advancePrescription.beforeDinnerTime = responseMeal.beforeDinnerTime;
                            }
                            if(responseMeal.beforeSleepTime) {
                                advancePrescription.beforeSleepTime = responseMeal.beforeSleepTime;
                            }
                            if(responseMeal.breakfastPackage) {
                                advancePrescription.breakfastPackage = responseMeal.breakfastPackage;
                            }
                            if(responseMeal.beforeLunchPackage) {
                                advancePrescription.beforeLunchPackage = responseMeal.beforeLunchPackage;
                            }
                            if(responseMeal.lunchPackage) {
                                advancePrescription.lunchPackage = responseMeal.lunchPackage;
                            }
                            if(responseMeal.beforeDinnerPackage) {
                                advancePrescription.beforeDinnerPackage = responseMeal.beforeDinnerPackage;
                            }
                            if(responseMeal.dinnerPackage) {
                                advancePrescription.dinnerPackage = responseMeal.dinnerPackage;
                            }
                            if(responseMeal.dinnerPackage) {
                                advancePrescription.dinnerPackage = responseMeal.dinnerPackage;
                            }
                            if(responseMeal.beforeSleepPackage) {
                                advancePrescription.beforeSleepPackage = responseMeal.beforeSleepPackage;
                            }
                        }
                        if(responsedata.hasOwnProperty('exerciseList')) {
                            advancePrescription.exerciseList = responsedata.exerciseList ? responsedata.exerciseList : [];
                        }
                    }
                });
            }
        }
    };

    basicInfo.saveBasicInfo = function () {
        if(basicInfo.doctorName && basicInfo.doctorPhoneNumber && basicInfo.doctorDesignation
            && basicInfo.doctorHospitalChamber
            && basicInfo.patientWeight && basicInfo.patientBmi && basicInfo.patientBp) {

            var dataObj = {
                prescriptionId: prescription.id,
                patientUsername: prescription.patientUsername,
                prescriptionDate: prescription.prescriptionDate,
                doctorName : basicInfo.doctorName,
                doctorPhoneNumber : basicInfo.doctorPhoneNumber,
                doctorDesignation : basicInfo.doctorDesignation,
                doctorHospitalChamber : basicInfo.doctorHospitalChamber
            };
            $http.post('/prescription/saveBasicInformation', dataObj) .then(function(response) {
                if(!response.data.hasError) {
                    prescription.doctorId = response.data.doctorId;
                    prescription.doctorName = basicInfo.doctorName;
                    prescription.phoneNumber = basicInfo.doctorPhoneNumber;
                    prescription.doctorDesignation = basicInfo.doctorDesignation;
                    prescription.doctorHospitalChamber = basicInfo. doctorHospitalChamber;
                    prescription.id =  response.data.prescriptionId;
                    prescription.prescriptionDate = response.data.prescriptionDate;
                } else {
                    showErrorMsg(response.data.message);
                }
            });
        } else {
            showInfoMsg(requiredMessage);
        }
    };
});

app.controller('clinicalConditionController', function($http, $scope) {
    var clinic = this;
    clinic.save = function() {
        if(prescription.doctorId && prescription.patientUsername && prescription.prescriptionDate) {
            var dataObj = {
                doctorId: prescription.doctorId,
                patientUsername: prescription.patientUsername,
                beforeBreakfast: clinic.beforeBreakfast,
                afterBreakfast: clinic.afterBreakfast,
                beforeLunch: clinic.beforeLunch,
                afterLunch: clinic.afterLunch,
                beforeDinar: clinic.beforeDinar,
                afterDinar: clinic.afterDinar,
                randomTime: clinic.randomTime,
                hbaic: clinic.hbaic,
                ogtt: clinic.ogtt,
                sugar: clinic.sugar,
                albumin: clinic.albumin,
                acetone: clinic.acetone,
                prescriptionId: prescription.id,
                prescriptionDate: prescription.prescriptionDate
            };

            $http.post('/prescription/saveClinicalCondition', dataObj) .then(function(response) {
                if(!response.data.hasError) {
                    showSuccessMsg(response.data.message);
                } else {
                    showErrorMsg(response.data.message);
                }
            });
        } else {
            showInfoMsg('Please First fill up basic information & try again');
        }
    };

});


app.controller('referToController', function($http, $scope) {
    var referTo = this;
    referTo.specialistList = [];

    referTo.setSpecialistId = function () {
        referTo.id = 0;
    };

    referTo.addSpecialist = function () {
        addToList();
    };

    referTo.saveReferTo = function () {
        if(prescription.doctorId && prescription.patientUsername && prescription.prescriptionDate) {
            if (referTo.specialistList.length > 0) {
                var dataObj = {
                    specialistList: JSON.stringify(referTo.specialistList),
                    doctorId: prescription.doctorId,
                    patientUsername: prescription.patientUsername,
                    prescriptionId: prescription.id,
                    prescriptionDate: prescription.prescriptionDate
                }

                $http.post('/prescription/saveReferTo', dataObj).then(function (response) {
                    if (!response.data.hasError) {
                        showSuccessMsg(response.data.message);
                    } else {
                        showErrorMsg(response.data.message);
                    }
                });
            } else {
                showInfoMsg('Please add refer to & try again.')
            }
        } else {
            showInfoMsg('Please First fill up basic information & try again');
        }
    };

    function addToList() {
        if(referTo.name) {
            var specialist = {name: referTo.name, id: referTo.id};
            referTo.specialistList.push(specialist);
        } else {
            showInfoMsg('Please input specialist name & try again.')
        }
    }
});

app.controller('advancePrescriptionController', function($http, $scope, $controller) {
    advancePrescription = this;
    advancePrescription.exerciseList = [];
    advancePrescription.indexExercise = '';
    advancePrescription.exerciseId = '';

    advancePrescription.remove = function (exercise) {
        var index = advancePrescription.exerciseList.indexOf(exercise);
        advancePrescription.exerciseList.splice(index,1);
    };

    advancePrescription.edit = function (exercise, index) {
        advancePrescription.recommendExercise = exercise.exerciseId + "";
        advancePrescription.time = exercise.time;
        advancePrescription.period = exercise.period;
        advancePrescription.indexExercise = index;
        advancePrescription.exerciseId = exercise.id;
        $('#recommendExercise').val(exercise.exerciseId);
    };

    advancePrescription.addExercise = function () {
        if(advancePrescription.recommendExercise && advancePrescription.time && advancePrescription.period) {
            var exercise = {
                id: null,
                exerciseId: advancePrescription.recommendExercise,
                exerciseText: $("#recommendExercise option:selected").html(),
                time: advancePrescription.time,
                period: advancePrescription.period
            };
            if(checkNull(advancePrescription.indexExercise)) {
                advancePrescription.exerciseList.push(exercise);
            } else {
               advancePrescription.exerciseList[advancePrescription.indexExercise] = exercise;
            }
            advancePrescription.recommendExercise = '';
            advancePrescription.time = '';
            advancePrescription.period = '';
            advancePrescription.indexExercise = '';
            advancePrescription.exerciseId = '';
        }
    };

    advancePrescription.saveRecommend = function () {
        if(prescription.id) {
            if(advancePrescription.breakfastPackage && advancePrescription.beforeLunchPackage &&
                advancePrescription.lunchPackage && advancePrescription.beforeDinnerPackage && advancePrescription.dinnerPackage
                && advancePrescription.beforeSleepPackage && advancePrescription.exerciseList.length > 0 ) {
                var dataObj = {
                    breakfastPackage: advancePrescription.breakfastPackage,
                    breakfastTime: advancePrescription.breakfastTime,
                    beforeLunchPackage: advancePrescription.beforeLunchPackage,
                    beforeLunchTime: advancePrescription.beforeLunchTime,
                    lunchPackage: advancePrescription.lunchPackage,
                    lunchTime: advancePrescription.lunchTime,
                    beforeDinnerPackage: advancePrescription.beforeDinnerPackage,
                    beforeDinnerTime: advancePrescription.beforeDinnerTime,
                    dinnerPackage: advancePrescription.dinnerPackage,
                    dinnerTime: advancePrescription.dinnerTime,
                    beforeSleepPackage: advancePrescription.beforeSleepPackage,
                    beforeSleepTime: advancePrescription.beforeSleepTime,
                    exerciseList: advancePrescription.exerciseList,
                    patientUsername: prescription.patientUsername,
                    prescriptionId: prescription.id,
                    doctorId: prescription.doctorId,
                    prescriptionDate: prescription.prescriptionDate
                };
                $http.post('/prescription/saveRecommend', dataObj).then(function (response) {
                    if (!response.data.hasError) {
                        showSuccessMsg(response.data.message);
                    } else {
                        showErrorMsg(response.data.message);
                    }
                });
            } else {
                showInfoMsg('Please First fill all information & try again.');
            }
        } else {
            showInfoMsg('Please First fill up basic information & try again');
        }
    };

    advancePrescription.drugList = [];
    resetForm();
    advancePrescription.indexDrug ='';
    advancePrescription.editDrug = function (drug, index) {
        advancePrescription.drugType = drug.drugType;
        advancePrescription.drugWeight =drug.drugWeight;
        advancePrescription.drugTime = drug.drugTime;
        advancePrescription.quantity1 = drug.quantity1;
        advancePrescription.quantity2 = drug.quantity2;
        advancePrescription.quantity3 = drug.quantity3;
        advancePrescription.quantity4 = drug.quantity4;
        advancePrescription.quantity5 = drug.quantity5;
        advancePrescription.drugTime1 = drug.drugTime1;
        advancePrescription.drugTime2 = drug.drugTime2;
        advancePrescription.drugTime3 = drug.drugTime3;
        advancePrescription.drugTime4 = drug.drugTime4;
        advancePrescription.drugTime5 = drug.drugTime5;
        advancePrescription.medicineAmBm = drug.medicineAmBm;
        advancePrescription.drugPeriod = drug.period;
        advancePrescription.medicineName = drug.medicineName;
        advancePrescription.indexDrug = index;
        advancePrescription.drugId = drug.id;
    };

    advancePrescription.addDrug = function () {
        if(advancePrescription.drugType && advancePrescription.drugWeight  && advancePrescription.drugTime && advancePrescription.medicineAmBm &&
            advancePrescription.drugPeriod && advancePrescription.medicineName) {
            var drug ={
                id: advancePrescription.drugId,
                drugType: advancePrescription.drugType,
                medicineName: advancePrescription.medicineName,
                drugWeight: advancePrescription.drugWeight,
                period: advancePrescription.drugPeriod,
                drugTime: advancePrescription.drugTime,
                quantity1: advancePrescription.quantity1,
                quantity2: advancePrescription.quantity2,
                quantity3: advancePrescription.quantity3,
                quantity4: advancePrescription.quantity4,
                quantity5: advancePrescription.quantity5,
                medicineAmBm: advancePrescription.medicineAmBm,
                instruction: advancePrescription.instruction,
                drugTime1: advancePrescription.drugTime1,
                drugTime2: advancePrescription.drugTime2,
                drugTime3: advancePrescription.drugTime3,
                drugTime4: advancePrescription.drugTime4,
                drugTime5: advancePrescription.drugTime5
            };
            if(checkNull(advancePrescription.indexDrug)) {
                advancePrescription.drugList.push(drug);
            } else {
                advancePrescription.drugList[advancePrescription.indexDrug] =drug;
            }
            resetForm();
        } else {
            showInfoMsg(requiredMessage);
        }
    };

    advancePrescription.removeDrug = function (drug) {
        var index = advancePrescription.drugList.indexOf(drug);
        advancePrescription.drugList.splice(index,1);
    };

    advancePrescription.setDrugTime = function () {
        setDrugTime();
    };

    function setDrugTime() {
        if(prescription.drugTimes[advancePrescription.drugTime] == 1) {
            advancePrescription.quantity1 = advancePrescription.quantity1 != 0 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity2 = 0;
            advancePrescription.quantity3 = 0;
            advancePrescription.quantity4 = 0;
            advancePrescription.quantity5 = 0;
            advancePrescription.drugTime1 = medicationTryTimes[0];
            advancePrescription.drugTime2 =  '';
            advancePrescription.drugTime3 =  '';
            advancePrescription.drugTime4 =  '';
            advancePrescription.drugTime5 =  '';
        }
        if(prescription.drugTimes[advancePrescription.drugTime] == 2) {
            advancePrescription.quantity1 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity2 = 0;
            advancePrescription.quantity3 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity4 = 0;
            advancePrescription.quantity5 = 0;
            advancePrescription.drugTime1 = medicationTryTimes[0];
            advancePrescription.drugTime2 =  '';
            advancePrescription.drugTime3 = medicationTryTimes[2];
            advancePrescription.drugTime4 =  '';
            advancePrescription.drugTime5 =  '';
        }
        if(prescription.drugTimes[advancePrescription.drugTime] == 3) {
            advancePrescription.quantity1 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity2 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity3 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity4 = 0;
            advancePrescription.quantity5 = 0;
            advancePrescription.drugTime1 = medicationTryTimes[0];
            advancePrescription.drugTime2 = medicationTryTimes[1];
            advancePrescription.drugTime3 = medicationTryTimes[2];
            advancePrescription.drugTime4 =  '';
            advancePrescription.drugTime5 =  '';
        }
        if(prescription.drugTimes[advancePrescription.drugTime] == 4) {
            advancePrescription.quantity1 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity2 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity3 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity4 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity5 = 0;
            advancePrescription.drugTime1 = medicationFourTimes[0];
            advancePrescription.drugTime2 = medicationFourTimes[1];
            advancePrescription.drugTime3 = medicationFourTimes[2];
            advancePrescription.drugTime4 = medicationFourTimes[3];
            advancePrescription.drugTime5 =  '';
        }
        if(prescription.drugTimes[advancePrescription.drugTime] == 5) {
            advancePrescription.quantity1 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity2 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity3 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity4 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.quantity5 = advancePrescription.quantity1 ? advancePrescription.quantity1 : 1;
            advancePrescription.drugTime1 = medicationFiveTimes[0];
            advancePrescription.drugTime2 = medicationFiveTimes[1];
            advancePrescription.drugTime3 = medicationFiveTimes[2];
            advancePrescription.drugTime4 = medicationFiveTimes[3];
            advancePrescription.drugTime5 = medicationFiveTimes[4];
        }
    }

    function resetForm() {
        advancePrescription.drugType ='Tablet';
        advancePrescription.drugWeight ='500mg';
        advancePrescription.drugTime ='TDS';
        setDrugTime();
        advancePrescription.medicineAmBm = 'After Meal';
        advancePrescription.drugPeriod = 'Continue';
        advancePrescription.medicineName = '';
        advancePrescription.indexDrug = '';
        advancePrescription.drugId = '';
    }

    advancePrescription.saveMedication = function () {
        if(prescription.doctorId && prescription.patientUsername && prescription.prescriptionDate) {
            if (advancePrescription.drugList.length > 0) {
                var dataObj = {
                    doctorId: prescription.doctorId,
                    patientUsername: prescription.patientUsername,
                    prescriptionDate: prescription.prescriptionDate,
                    prescriptionId: prescription.id,
                    drugList: JSON.stringify(advancePrescription.drugList)
                };
                $http.post('/prescription/saveMedication', dataObj).then(function (response) {
                    if (!response.data.hasError) {
                        showSuccessMsg(response.data.message);
                    } else {
                        showErrorMsg(response.data.message);
                    }
                });
            } else {
                showInfoMsg('Please First add some medication & try again.');
            }
        } else {
            showInfoMsg('Please First fill up basic information & try again.');
        }
    };

    function checkNull(value) {
        if(value == undefined || value == null || value == '') {
            return true;
        } else {
            return false;
        }
    }
});

$(function () {
    var patientMobileOrNameSource = autoSearch.getPatientUsernameWithMobileSource();

    var doctorSource = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "/prescription/searchDoctorByName?q=%QUERY",
            transform: function (response) {
                // Map the remote source JSON array to a JavaScript object array
                return $.map(response.results, function (talentdata) {
                    return {
                        value: talentdata.name + '-' + talentdata.phoneNumber,
                        name: talentdata.name,
                        phoneNumber: talentdata.phoneNumber,
                        designation: talentdata.designation,
                        hospitalName: talentdata.hospitalName
                    };
                });
            }
        }
    });

    var specialistSource = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "/prescription/searchSpecialist?q=%QUERY",
            transform: function (response) {
                // Map the remote source JSON array to a JavaScript object array
                return $.map(response.results, function (talentdata) {
                    return {
                        value: talentdata.name,
                        id: talentdata.id
                    };
                });
            }
        }
    });

    var specialistSearch = $('#specialist').typeahead({
            minLength: 2
        },
        {
            name: 'specialistData',
            source: specialistSource,
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
            specialistSearch.typeahead('val', datum.value);
            $('#specialistId').val(datum.id);
            angular.element($('#specialist')).triggerHandler('input');
            angular.element($('#specialistId')).triggerHandler('input');
        });

    var doctorNameSearch = $('#doctorName').typeahead({
            minLength: 2
        },
        {
            name: 'doctorNameData',
            source: doctorSource,
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
            doctorNameSearch.typeahead('val', datum.name);
            $('#doctorHospitalChamber').val(datum.hospitalName);
            $('#doctorDesignation').val(datum.designation);
            $('#doctorPhoneNumber').val(datum.phoneNumber);
            angular.element($('#doctorName')).triggerHandler('input');
            angular.element($('#doctorHospitalChamber')).triggerHandler('input');
            angular.element($('#doctorDesignation')).triggerHandler('input');
            angular.element($('#doctorPhoneNumber')).triggerHandler('input');
        });

    var doctorPhoneNumberSearch = $('#doctorPhoneNumber').typeahead({
            minLength: 2
        },
        {
            name: 'doctorPhoneNumberSearchData',
            source: doctorSource,
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
            doctorPhoneNumberSearch.typeahead('val', datum.phoneNumber);
            $('#doctorHospitalChamber').val(datum.hospitalName);
            $('#doctorDesignation').val(datum.designation);
            $('#doctorName').val(datum.name);
            angular.element($('#doctorName')).triggerHandler('input');
            angular.element($('#doctorHospitalChamber')).triggerHandler('input');
            angular.element($('#doctorDesignation')).triggerHandler('input');
            angular.element($('#doctorPhoneNumber')).triggerHandler('input');
        });

    autoSearch.searchEngine($('#patientNameOrMobile'), patientMobileOrNameSource, true);

    var medicineNameSource = new Bloodhound({
        datumTokenizer: function(datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: "/prescription/searchMedicineName?q=%QUERY",
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

    var medicineNameSearch = $('#brandName').typeahead({
            minLength: 2
        },
        {
            name: 'brand' +
            'NameData',
            source: medicineNameSource,
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
            medicineNameSearch.typeahead('val', datum.value);
        });
});


