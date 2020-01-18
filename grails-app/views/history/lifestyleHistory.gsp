<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
<body>
    <ol class="breadcrumb">
        <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
        <li><a href="${createLink(controller: 'history', action: 'lifestyleHistory')}">Lifestyle History</a></li>
        <li class="active">Create</li>
    </ol>
    <div class="row">
        <div class="col-md-12">
            <g:if test='${flash.message}'>
                <div class="errorHandler alert alert-danger">
                    <i class="fa fa-remove-sign"></i>
                    ${flash.message}
                </div>
            </g:if>
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">Lifestyle History</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
            <div class="panel-body padding-top-form">
                <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Food Habit</strong></span>
                            </div>
                            <div class="col-md-9">
                                <div class="form-group">
                                     <span><strong>Breakfast</strong></span>
                                </div>
                                <div>
                                    <div class="form-group">
                                        <label><input type="radio" name="regularIrregular" value="regular"> Regular Package</label>
                                        <label><input type="radio" name="regularIrregular" value="irregular"> Irregular package</label>
                                    </div>
                                    <div class="package regular">
                                        <div class="radio">
                                            <label><input type="radio" name="package">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="package"> Package 2: Bred, milk, apple with unit</label>
                                        </div>
                                    </div>
                                    <div class="package irregular">
                                        <div class="radio">
                                            <label><input type="radio" name="package">Package 1: biskit, tea, sweet with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="package"> Package 2: pizza, burgure, Banana with unit</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                <div class='input-group date' id='breakfastTime'>
                                    <input type='text' class="form-control" placeholder="Breakfast Time"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                                </div>
                                <div class="form-group">
                                    <span><strong>Snack 1</strong></span>
                                </div>
                                <div>
                                    <div class="form-group">
                                        <label><input type="radio" name="regularIrregular" value="snack1regular"> Regular Package</label>
                                        <label><input type="radio" name="regularIrregular" value="snack1irregular"> Irregular package</label>
                                    </div>
                                    <div class="snack1Package snack1regular">
                                        <div class="radio">
                                            <label><input type="radio" name="snack1Package">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="snack1Package"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                    <div class="snack1Package snack1irregular">
                                        <div class="radio">
                                            <label><input type="radio" name="snack1Package">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="snack1Package"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                <div class='input-group date' id='snack1Time'>
                                    <input type='text' class="form-control" placeholder="Snack 1 Time"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                                </div>
                                <div class="form-group">
                                    <span><strong>Lunch</strong></span>
                                </div>
                                <div>
                                    <div class="form-group">
                                        <label><input type="radio" name="regularIrregular" value="lunchRegular"> Regular Package</label>
                                        <label><input type="radio" name="regularIrregular" value="lunchIrregular"> Irregular package</label>
                                    </div>
                                    <div class="lunchPackage lunchRegular">
                                        <div class="radio">
                                            <label><input type="radio" name="lunchPackage">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="lunchPackage"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                    <div class="lunchPackage lunchIrregular">
                                        <div class="radio">
                                            <label><input type="radio" name="lunchPackage">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="lunchPackage"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                <div class='input-group date' id='lunchTime'>
                                    <input type='text' class="form-control" placeholder="Lunch Time"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                                </div>

                                <div class="form-group">
                                    <span><strong>Snack 2</strong></span>
                                </div>
                                <div>
                                    <div class="form-group">
                                        <label><input type="radio" name="regularIrregular" value="snack2Regular"> Regular Package</label>
                                        <label><input type="radio" name="regularIrregular" value="snack2Irregular"> Irregular package</label>
                                    </div>
                                    <div class="snack2Package snack2Regular">
                                        <div class="radio">
                                            <label><input type="radio" name="snack2Package">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="snack2Package"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                    <div class="snack2Package snack2Irregular">
                                        <div class="radio">
                                            <label><input type="radio" name="snack2Package">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="snack2Package"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class='input-group date' id='snack2Time'>
                                        <input type='text' class="form-control" placeholder="Snack 2"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-time"></span>
                                        </span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <span><strong>Dinner</strong></span>
                                </div>
                                <div>
                                    <div class="form-group">
                                        <label><input type="radio" name="regularIrregular" value="dinnerRegular"> Regular Package</label>
                                        <label><input type="radio" name="regularIrregular" value="dinnerIrregular"> Irregular package</label>
                                    </div>
                                    <div class="dinnerPackage dinnerRegular">
                                        <div class="radio">
                                            <label><input type="radio" name="dinnerPackage">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="dinnerPackage"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                    <div class="dinnerPackage dinnerIrregular">
                                        <div class="radio">
                                            <label><input type="radio" name="dinnerPackage">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="dinnerPackage"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class='input-group date' id='dinnerTime'>
                                        <input type='text' class="form-control" placeholder="Snack 2"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-time"></span>
                                        </span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <span><strong>Other Food</strong></span>
                                </div>
                                <div>
                                    <div class="form-group">
                                        <label><input type="radio" name="regularIrregular" value="otherFoodRegular"> Regular Package</label>
                                        <label><input type="radio" name="regularIrregular" value="otherFoodIrregular"> Irregular package</label>
                                    </div>
                                    <div class="otherFoodPackage otherFoodRegular">
                                        <div class="radio">
                                            <label><input type="radio" name="otherFoodPackage">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="otherFoodPackage"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                    <div class="otherFoodPackage otherFoodIrregular">
                                        <div class="radio">
                                            <label><input type="radio" name="otherFoodPackage">Package 1: Bred, vegetable, Banana with unit</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="otherFoodPackage"> Package 2: Bred, vegetable, Banana with unit</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class='input-group date' id='otherFoodTime'>
                                        <input type='text' class="form-control" placeholder="Snack 2"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-time"></span>
                                        </span>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Sweet Habit</strong></span>
                            </div>
                            <div class="col-md-9">
                                <div class="form-group">
                                  <span><strong>Type</strong></span>
                                </div>
                                <div class="form-group">
                                    <label class="checkbox-inline"><input type="checkbox" value="sweet" name="sweet" id="sweet">Sweet</label>
                                    <label class="checkbox-inline"><input type="checkbox" value="tea" name="tea" id="tea">Tea</label>
                                </div>
                                <span><strong>Taking Sweet?</strong></span>
                                <div class="radio">
                                    <div class="form-group">
                                        <label><input type="radio" name="takingSweet" value="yes"> Yes</label>
                                        <label><input type="radio" name="takingSweet" value="no"> No</label>
                                    </div>
                                    <div class="takingSweetFeature yes">
                                        <div class="radio">
                                            <label><input type="radio" name="takingSweetFeature">Regular</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="takingSweetFeature">Irregular</label>
                                        </div>
                                    </div>
                                </div>
                                <p><strong>Weekly amount</strong></p>
                                <div class="form-group">
                                    <input type="text" class="form-control" name="weeklyAmount" id="weeklyAmount" placeholder="Piece or gram"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Exercise</strong></span>
                            </div>
                            <div class="col-md-9">
                                <span><strong>Time</strong></span>
                                            <div class="checkbox">
                                                <div class="form-group">
                                                    <label><input type="checkbox" value="sweet" name="exerciseMorning" id="exerciseMorning">Morning</label>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class='input-group date' id='exerciseMorningTime'>
                                                    <input type='text' class="form-control" placeholder="Time"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-time"></span>
                                                    </span>
                                                </div>
                                            </div>
                                <span><strong>Type</strong></span>
                                <div class="form-group">
                                    <label class="checkbox-inline"><input type="checkbox" value="walk" name="walk">Walk</label>
                                    <label class="checkbox-inline"><input type="checkbox" value="swimming" name="swimming">Swimming</label>
                                    <label class="checkbox-inline"><input type="checkbox" value="running" name="running">Running</label>
                                </div>

                                    <div class="checkbox">
                                        <div class="form-group">
                                          <label><input type="checkbox" value="tea" name="exerciseEvening" id="exerciseEvening">Evening</label>
                                        </div>
                                        <div class="form-group">
                                            <div class='input-group date' id='exerciseEveningTime'>
                                                <input type='text' class="form-control" placeholder="Time"/>
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-time"></span>
                                                </span>
                                            </div>
                                        </div>
                                     </div>
                                <span><strong>Type</strong></span>
                                <div class="form-group">
                                    <label class="checkbox-inline"><input type="checkbox" value="walk" name="walk" id=walk">Walk</label>
                                    <label class="checkbox-inline"><input type="checkbox" value="swimming" name="swimming" id="swimming">Swimming</label>
                                    <label class="checkbox-inline"><input type="checkbox" value="running" name="running" id="running">Running</label>
                                </div>
                                <div class="col-md-6 col-lg-offset-3">
                                    <div class="form-group">
                                        <button onclick="addElementToList($('#notApplicableList'), $('#notApplicable'))" class="btn btn-default center-block">+ Add more</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Sleeping Hour</strong></span>
                            </div>
                            <div class="col-md-9">
                                <div class="form-group">
                                     <span><strong>Bed Time</strong></span>
                                </div>
                                <div class="form-group">
                                    <div class='input-group date' id='bedTime'>
                                        <input type='text' class="form-control" placeholder="Bed Time"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-time"></span>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <span><strong>How Long</strong></span>
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" name="howLong" id="howLong" placeholder="Hour">
                                </div>
                                <div class="form-group">
                                    <span><strong>Sleeping Quality</strong></span>
                                </div>
                                <div class="form-group">
                                    <div class="btn-group">
                                        <label id="sleepSelect" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
                                            <span id="text">Select</span> <span class="caret"></span>
                                        </label>
                                        <ul class="dropdown-menu" id="sleepingQuality">
                                            <li><a class="my_data_flag" id="soundSleep"> Sound Sleep </a></li>
                                            <li><a class="my_data_flag" id="partlySound"> Partly Sound </a></li>
                                        </ul>
                                    </div>
                                </div>
                              </div>
                            </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Smoking</strong></span>
                            </div>
                            <div class="col-md-9">
                                <div class="radio">
                                    <div class="form-group">
                                        <label><input type="radio" name="smoking" value="yes"> Yes</label>
                                        <label><input type="radio" name="smoking" value="no"> No</label>
                                    </div>
                                </div>
                                <p><span>If yes?</span></p>
                                <p><span>Average Stick in a day</span></p>
                                <div class="form-group">
                                  <input type="text" class="form-control" placeholder="Quantity">
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="col-md-3">
                                <span><strong>Alkoholik</strong></span>
                            </div>
                            <div class="col-md-9">
                                <div class="radio">
                                    <div class="form-group">
                                        <label><input type="radio" name="takingAlkohol" value="yes"> Yes</label>
                                        <label><input type="radio" name="takingAlkohol" value="no"> No</label>
                                    </div>
                                    <div class="alkoholikFeature yes">
                                        <div class="radio">
                                            <label><input type="radio" name="alkoholikFeature"> Regular</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="alkoholikFeature"> Irregular</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class='wrapper text-center'>
                        <div class="btn-group">
                            <div class="form-group">
                                <button type="button" class="btn btn-default">Save</button>
                                <button type="button" class="btn btn-default">Save & Next</button>
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
            $('#breakfastTime').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#snack1Time').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#lunchTime').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#snack2Time').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#dinnerTime').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#otherFoodTime').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#exerciseMorningTime').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#exerciseEveningTime').datetimepicker({
                format: 'LT'
            });
        });

        $(function () {
            $('#bedTime').datetimepicker({
                format: 'LT'
            });
        });

        $('.dropdown-toggle').dropdown();
        $('#sleepingQuality li > a').click(function(){
            if (this.text !== ' Select ') {
                $('#text').text($(this).html());
                $('.my_data_flag').removeClass('active');
                $('#sleepSelect').addClass('active');
            }
        });
    </script>

    </body>
</html>
