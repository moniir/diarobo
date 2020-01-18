/**
 * Created by Imran hossain on 06/04/2017.
 */
var app = angular.module('exercisePackage', []);
app.controller('exercisePackageController', function($http, $scope) {
    var package = this;
    package.exerciseList = [];
    package.measurementUnit = 'Hour';
    package.calorieBurn=0;
    package.measurementCalorieBurn=0;
    var exercisePackageId = $('#exercisePackageId').val();
    if(exercisePackageId) {
        $http.get('/exercisePackage/getExerciseListByPackage?exercisePackageId=' + exercisePackageId).then(function(response) {
            if(!response.data.hasError) {
                package.exerciseList = response.data.exerciseList;
            }
        });
    }
    package.addExercise = function () {
        if(package.exerciseName && package.quantity && package.measurementUnit) {
            var exercise = {
                exerciseName: package.exerciseName,
                exerciseLibraryId: package.exerciseLibraryId,
                quantity: package.quantity,
                measurementUnit: package.measurementUnit
            };
            if((package.index != 0 && !package.index) || package.index == "") {
                package.exerciseList.push(exercise);
                package.measurementCalorieBurn += (package.quantity * package.calorieBurn);
            } else {
                package.exerciseList[package.index] = exercise;
            }
            package.index = '';
            package.exerciseName = '';
            package.quantity = '';
        }
    };

    package.editExercisePackage = function (exercise, index) {
        if(exercise) {
            package.index = index;
            package.exerciseName = exercise.exerciseName;
            package.exerciseLibraryId = exercise.exerciseLibraryId;
            package.quantity = exercise.quantity;
            package.measurementUnit = exercise.measurementUnit;
        }
    };

    package.removeExercise = function(exercise) {
        var index = package.exerciseList.indexOf(exercise);
        package.exerciseList.splice(index,1);
        package.measurementCalorieBurn =  package.measurementCalorieBurn - (exercise.quantity * package.calorieBurn);
    };

    package.savePackage = function () {
        if(package.exerciseList && package.packageName) {
            var dataObj = {
                exercisePackageId: $('#exercisePackageId').val(), packageName: package.packageName, exerciseList: JSON.stringify(package.exerciseList)
            };
            $http.post('/exercisePackage/create', dataObj) .then(function(response) {
                if(!response.data.hasError) {
                    showSuccessMsg(response.data.message);
                } else {
                    showSuccessMsg(response.data.message);
                }
            });
        } else {
            showErrorMsg('Please Fill all information & try again.');
        }
    };

});