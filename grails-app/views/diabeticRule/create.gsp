<html>
<head>
    <meta name="layout" content="main"/>
    <title>General Rule</title>
</head>
<body>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-heading">
                    <div class="panel-title panel-title-list">Diabetic General Rule
                        <span class="pull-right"><a title="Back" href="${createLink(controller: 'diabeticRule', action: 'index')}"><i class="fa fa-reply" aria-hidden="true"></i></a></span>
                    </div>
                </div>
            </div>
            <div class="panel-body padding-top-form">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3 navMenu">
                            <ul class="nav nav-pills nav-stacked">
                                <li class="active"><a data-toggle="tab" href="#basicInformation">Basic</a></li>
                                <li><a data-toggle="tab" href="#mealPlan">Meal plan</a></li>
                                <li><a data-toggle="tab" href="#dosDonts">DOs & DONTs</a></li>
                                <li><a data-toggle="tab" href="#complications">Complications</a></li>
                                <li><a data-toggle="tab" href="#exercisePlan">Exercise Plan</a></li>
                            </ul>
                        </div>
                        <div class="col-md-9">
                            <div class="message-content">
                            <g:if test="${flash.error}">
                                <div class="errorHandler alert alert-danger">
                                    <i class="fa fa-remove-sign"></i>
                                    ${flash.error}
                                </div>
                            </g:if>
                            <g:if test="${flash.message}">
                                <div class="errorHandler alert alert-success">
                                    <i class="fa fa-remove-sign"></i>
                                    ${flash.message}
                                </div>
                            </g:if>
                            </div>
                            <div class="tab-content">
                                <div id="basicInformation" class="tab-pane fade in active">
                                    <g:render template="basicInformation"></g:render>
                                </div>
                                <div id="mealPlan" class="tab-pane fade">
                                    <g:render template="mealPlan"></g:render>
                                </div>
                                <div id="dosDonts" class="tab-pane fade">
                                    <g:render template="dosDonts"></g:render>
                                </div>
                                <div id="complications" class="tab-pane fade">
                                    <g:render template="complications"></g:render>
                                </div>
                                <div id="exercisePlan" class="tab-pane fade">
                                    <g:render template="exercisePlan"></g:render>
                                </div>
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
        initNumeric();
        activeTab("${step}");
        $( ".message-content" ).fadeOut(5000);

 });

   function activeTab(step){
        switch (step) {
           case '1':
              $('.nav-pills a[href="#basicInformation"]').tab('show');
               break;
           case '2':
                $('.nav-pills a[href="#mealPlan"]').tab('show');
                break;
           case '3':
               $('.nav-pills a[href="#dosDonts"]').tab('show');
               break;
           case '4':
               $('.nav-pills a[href="#complications"]').tab('show');
               break;
            case '5':
                $('.nav-pills a[href="#exercisePlan"]').tab('show');
                break;
           default:
               $('.nav-pills a[href="#basicInformation"]').tab('show');
       }
    };
</script>
</body>
</html>
