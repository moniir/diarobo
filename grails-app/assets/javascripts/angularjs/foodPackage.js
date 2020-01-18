/**
 * Created by rakib on 2/17/2017.
 */
var app = angular.module('foodPackage', []);
app.controller('foodPackageController', function($http, $scope) {
    var package = this;
    package.foodList = [];
    package.measurementUnit = 'Piece';
    var foodPackageId = $('#foodPackageId').val();
    if(foodPackageId) {
       $http.get('/foodPackage/getFoodListByPackage?foodPackageId=' + foodPackageId).then(function(response) {
            if(!response.data.hasError) {
                package.foodList = response.data.foodList;
            }
       });
    }

    package.edit = function (food, index) {
        if(food) {
            package.index = index;
            package.foodName = food.foodName;
            package.foodLibraryId = food.foodLibraryId;
            package.quantity = food.quantity;
            package.measurementUnit = food.measurementUnit;
        }
    };

    package.addFood = function () {
        if(package.foodName && package.quantity && package.measurementUnit) {
            var food = {
                foodName: package.foodName,
                foodLibraryId: package.foodLibraryId,
                quantity: package.quantity,
                measurementUnit: package.measurementUnit
            };
            if((package.index != 0 && !package.index) || package.index == "") {
                package.foodList.push(food);
            } else {
                package.foodList[package.index] = food;
            }
            package.foodName = '';
            package.quantity = '';
            package.index = '';
            package.foodLibraryId = '';
        }
    };

    package.removeFood = function(food) {
        var index = package.foodList.indexOf(food);
        package.foodList.splice(index,1);
    };

    package.savePackage = function () {
        if(package.foodList && package.packageName) {
            var dataObj = {
                foodPackageId: $('#foodPackageId').val(), packageName: package.packageName, foodList: JSON.stringify(package.foodList)
            };
            $http.post('/foodPackage/create', dataObj) .then(function(response) {
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