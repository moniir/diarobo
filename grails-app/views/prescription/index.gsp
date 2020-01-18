<%@ page import="com.diarobo.enums.ActiveStatus" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <script>
        var medicationTryTimes = [];
        var mealSnackTimes = [];
        var medicationFourTimes = [];
        var medicationFiveTimes = [];
        var exerciseTimes = [];
        <g:each in="${medicationTryTimes}" var="item" status="i">
            medicationTryTimes[${i}] = '${item}';
        </g:each>
        <g:each in="${mealSnackTimes}" var="item" status="i">
            mealSnackTimes[${i}] = '${item}';
        </g:each>
        <g:each in="${medicationFourTimes}" var="item" status="i">
            medicationFourTimes[${i}] = '${item}';
        </g:each>
        <g:each in="${medicationFiveTimes}" var="item" status="i">
            medicationFiveTimes[${i}] = '${item}';
        </g:each>
        <g:each in="${exerciseTimes}" var="item" status="i">
            exerciseTimes[${i}] = '${item}';
        </g:each>
    </script>
    <asset:javascript src="angularjs/prescription.js"/>
    <title>Prescription</title>
</head>
<body>
    <div class="row" ng-app="prescription">
        <div class="col-md-12" ng-controller="prescriptionController as prescription">
            <div class="panel panel-info" ng-form>
                <input class="hidden" type="number" ng-model="prescription.id" value="${prescriptionId}">
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Prescription</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body padding-top-form">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <img class="center-image img-circle-customize" ng-src="{{prescription.profileImage}}" alt="Profile image"/>
                                </div>
                            </div>
                            <div class="col-md-9">
                                <div class="col-md-6">
                                    <div class="row"><h3>{{prescription.patientNameOrMobile}}</h3></div>
                                    <div class="row">{{prescription.patientAge ? 'Age: ' + prescription.patientAge + ', ' : ''}} {{prescription.patientWeight ? ' Weight: ' + prescription.patientWeight + ', ' : ''}} {{prescription.patientGender ? ' Gender: ' + prescription.patientGender + ', ' : ''}}</div>
                                    <div class="row">{{prescription.patientBmi ? 'BMI: ' + prescription.patientBmi + ', ' : ''}} {{prescription.patientBp ? ' BP: ' + prescription.patientBp + ', ' : ''}} {{prescription.patientDiabeticType ? 'Diabetic Type: ' + prescription.patientDiabeticType + ', ': ''}}</div>
                                    <div class="row">{{prescription.prescriptionDate ? 'Date: ' : ''}} {{prescription.prescriptionDate | date:'dd/MM/yyyy'}}</div>
                                </div>
                                <div class="col-md-6">
                                    <div class="row"><h3>{{prescription.doctorName}}</h3></div>
                                    <div class="row">{{prescription.doctorDesignation}}</div>
                                    <div class="row">{{prescription.doctorHospitalChamber}}</div>
                                    <div class="row">{{prescription.phoneNumber ? 'Mobile/Phone: ' + prescription.phoneNumber : ''}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3 navMenu">
                                 <ul class="nav nav-pills nav-stacked">
                                     <li class="active"><a data-toggle="tab" href="#basicInformation">Basic Information</a></li>
                                     <li><a data-toggle="tab" href="#clinicalConditionList">Clinical Condition</a></li>
                                     <li><a data-toggle="tab" href="#medicationList">Medication</a></li>
                                     <li><a data-toggle="tab" href="#referToList">Refer to</a></li>
                                     <li><a data-toggle="tab" href="#recommendsList">Recommends</a></li>
                                     <li><a data-toggle="tab" href="#advice">Advice</a></li>
                                     <li><a data-toggle="tab" href="#prescriptionList">Prescription List</a></li>
                                     <li><a data-toggle="tab" href="#advancePrescription">Advance Prescription</a></li>
                                 </ul>
                            </div>
                            <div class="col-md-9">

                                <div class="tab-content" ng-controller="advancePrescriptionController as advancePrescription">
                                    <div id="basicInformation" class="tab-pane fade in active">
                                        <g:render template="basicInformation"></g:render>
                                    </div>
                                    <div id="clinicalConditionList" class="tab-pane fade">
                                        <g:render template="clinicalCondation"></g:render>
                                    </div>
                                     <div id="medicationList" class="tab-pane fade">
                                         <div class="form-group">
                                             <g:render template="medication" model="[drugTypes: drugTypes, drugWeightList: drugWeightList]"></g:render>
                                         </div>
                                     </div>
                                    <div id="advice" class="tab-pane fade">
                                        <g:render template="advice"></g:render>
                                    </div>
                                    <div id="recommendsList" class="tab-pane fade">
                                        <g:render template="recommends" model="[exerciseList: exerciseList]"></g:render>
                                    </div>
                                    <div id="referToList" class="tab-pane fade">
                                        <g:render template="referTo"></g:render>
                                     </div>
                                    <div id="prescriptionList" class="tab-pane fade">
                                        <g:render template="prescriptionList"></g:render>
                                    </div>
                                    <div id="advancePrescription" class="tab-pane fade">
                                        <g:render template="advancePrescription" model="[exerciseList: exerciseList]"></g:render>
                                    </div>
                                    <div id="prescriptionView">
                                        <g:render template="previewPrescription"></g:render>
                                    </div>
                                    <g:render template="/layouts/messageModal"></g:render>
                                </div>
                            </div>
                        </div>
                     </div>
                </div>
            </div>
        </div>
    </div>
<script>
    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="regular"){
                $(".package").not(".regular").hide();
                $(".regular").show();
            }
            if($(this).attr("value")=="irregular"){
                $(".package").not(".irregular").hide();
                $(".irregular").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="snack1regular"){
                $(".snack1Package").not(".snack1regular").hide();
                $(".snack1regular").show();
            }
            if($(this).attr("value")=="snack1irregular"){
                $(".snack1Package").not(".snack1irregular").hide();
                $(".snack1irregular").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="lunchRegular"){
                $(".lunchPackage").not(".lunchRegular").hide();
                $(".lunchRegular").show();
            }
            if($(this).attr("value")=="lunchIrregular"){
                $(".lunchPackage").not(".lunchIrregular").hide();
                $(".lunchIrregular").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="snack2Regular"){
                $(".snack2Package").not(".snack2Regular").hide();
                $(".snack2Regular").show();
            }
            if($(this).attr("value")=="snack2Irregular"){
                $(".snack2Package").not(".snack2Irregular").hide();
                $(".snack2Irregular").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="dinnerRegular"){
                $(".dinnerPackage").not(".dinnerRegular").hide();
                $(".dinnerRegular").show();
            }
            if($(this).attr("value")=="dinnerIrregular"){
                $(".dinnerPackage").not(".dinnerIrregular").hide();
                $(".dinnerIrregular").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="otherFoodRegular"){
                $(".otherFoodPackage").not(".otherFoodRegular").hide();
                $(".otherFoodRegular").show();
            }
            if($(this).attr("value")=="otherFoodIrregular"){
                $(".otherFoodPackage").not(".otherFoodIrregular").hide();
                $(".otherFoodIrregular").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="yes"){
                $(".takingSweetFeature").not(".yes").hide();
                $(".yes").show();
            }
        });
    });

    $(document).ready(function(){
        $('input[type="radio"]').click(function(){
            if($(this).attr("value")=="yes"){
                $(".alkoholikFeature").not(".yes").hide();
                $(".yes").show();
            }
        });
    });

    $(function () {
        $('.date').datetimepicker({
            format: 'LT'
        }).on("dp.change", function(ev) {
            var input = $(ev.currentTarget).find('input').val(moment(new Date(ev.date)).format("LT"));
            var v = input.val();
            var v = input.val();
            angular.element(input).triggerHandler('input');
        });
    });
</script>
</body>
</html>
