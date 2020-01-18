<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title><g:layoutTitle/> <g:message code="header.title.app.name"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>
</head>
    <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-content" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${createLink(uri: '/')}"><g:message code="app.project.name.title"/></a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${createLink(controller: 'logout')}">logout</a></li>
                </ul>
                <form class="navbar-form navbar-right">
                    <input type="text" class="form-control" placeholder="Search...">
                </form>
            </div>
        </div>
    </nav>
    <div class="nav-side-menu">
        %{--<div class="brand">
            <form>
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>--}%
        <div class="menu-list" style="padding-top: 10px;">
            <ul id="menu-content" class="menu-content collapse out">
                <sec:access expression="hasRole('ROLE_PATIENT')">
                    <li>
                        <a href="${createLink(uri: '/')}">
                            <i class="fa fa-dashboard fa-lg"></i> Dashboard
                        </a>
                    </li>
                    <li data-toggle="collapse" data-target="#products" id="patientProfile" class="collapsed active">
                        <a href="#"><i class="fa fa-gift fa-lg"></i> Patient Profile <span class="arrow"></span></a>
                    </li>
                    <ul class="sub-menu collapse in" id="products">
                        <li class="${params.controller == 'profile' && params.action == 'index' ? 'active' : ''}">
                            <a href="${createLink(controller: 'profile')}">Profile</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'diabetic' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'diabetic')}">Diabetic History</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'drugHistory' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'drugHistory')}">Previews Drug History</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'surgeryHistory' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'surgeryHistory')}">Surgery History</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'socialFinancialStatus' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'socialFinancialStatus')}">Social/financial Status</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'professionalHistory' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'professionalHistory')}">Professional History</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'educationalStatus' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'educationalStatus')}">Educational Status</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'pastIllnessHistory' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'pastIllnessHistory')}">Past-Illness History</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'lifestyleHistory' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'lifestyleHistory')}">Lifestyle History</a>
                        </li>
                        <li class="${params.controller == 'history' && params.action == 'status' ? 'active' : ''}">
                            <a href="${createLink(controller: 'history', action: 'status')}">Status</a>
                        </li>
                    </ul>
                    <li class="visible-xs">
                        <a href="${createLink(controller: 'logout')}">
                            <i class="fa fa-dashboard fa-lg"></i> Log out
                        </a>
                    </li>
                </sec:access>
                <sec:access expression="hasRole('ROLE_ADMIN')">
                    <li>
                        <a href="${createLink(uri: '/')}">
                            <i class="fa fa-dashboard fa-lg"></i> Dashboard
                        </a>
                    </li>
                    <li data-toggle="collapse" data-target="#products" id="patientProfile" class="collapsed active">
                        <a href="#"><i class="fa fa-gift fa-lg"></i>Library<span class="arrow"></span></a>
                    </li>
                    <ul class="sub-menu collapse in" id="products">
                        <li class="${params.controller == 'food' && (params.action == 'index' || params.action == 'create' || params.action == 'foodDetails') ? 'active' : ''}">
                            <a href="${createLink(controller: 'food', action: 'index')}">Foods</a>
                        </li>
                        <li class="${params.controller == 'exercise' && (params.action == 'index' || params.action == 'create') ? 'active' : ''}">
                            <a href="${createLink(controller: 'exercise', action: 'index')}">Exercise</a>
                        </li>
                        <li class="${params.controller == 'medicine' && (params.action == 'index' || params.action == 'create') ? 'active' : ''}">
                            <a href="${createLink(controller: 'medicine', action: 'index')}">Medicine</a>
                        </li>
                        <li class="${params.controller == 'audio' && (params.action == 'index' || params.action == 'create') ? 'active' : ''}">
                            <a href="${createLink(controller: 'audio', action: 'index')}">Notification Audio</a>
                        </li>
                        <li class="${params.controller == 'diabeticRule' && (params.action == 'index' || params.action == 'create' ||  params.action == 'editDiabeticRule') ? 'active' : ''}">
                            <a href="${createLink(controller: 'diabeticRule', action: 'index')}">Diabetic Rule</a>
                        </li>
                        <li class="${params.controller == 'foodPackage' && (params.action == 'index') ? 'active' : ''}">
                            <a href="${createLink(controller: 'foodPackage', action: 'index')}">Food Package</a>
                        </li>
                        <li class="${params.controller == 'exercisePackage' && (params.action == 'index') ? 'active' : ''}">
                            <a href="${createLink(controller: 'exercisePackage', action: 'index')}">Exercise Package</a>
                        </li>
                        <li class="${params.controller == 'prescription' && params.action == 'index' ? 'active' : ''}">
                            <a href="${createLink(controller: 'prescription', action: 'index')}">Prescription</a>
                        </li>
                        %{--<li class="${params.controller == 'notificationScheduler' && params.action == 'index' ? 'active' : ''}">
                            <a href="${createLink(controller: 'notificationScheduler', action: 'index')}">Scheduler Notifications</a>
                        </li>--}%
                        <li class="${params.controller == 'masterKeySetup' && params.action == 'index' ? 'active' : ''}">
                            <a href="${createLink(controller: 'masterKeySetup', action: 'index')}">Master Key Setup</a>
                        </li>
                        %{--<li class="${params.controller == 'generalRule' && params.action == 'index' ? 'active' : ''}">--}%
                            %{--<a href="${createLink(controller: 'generalRule', action: 'index')}">General Rule</a>--}%
                        %{--</li>--}%
                        %{--<li class="${params.controller == 'food' && params.action == 'foodList' ? 'active' : ''}">--}%
                            %{--<a href="${createLink(controller: 'food', action: 'foodList')}">Food List</a>--}%
                        %{--</li>--}%
                        %{--<li class="${params.controller == 'food' && params.action == 'foodDetails' ? 'active' : ''}">--}%
                            %{--<a href="${createLink(controller: 'food', action: 'foodDetails')}">Food Details</a>--}%
                        %{--</li>--}%

                    </ul>
                    <li class="${params.controller == 'groupItem' && params.action == 'index' ? 'active' : ''}">
                        <a href="${createLink(controller: 'groupItem', action: 'index')}">
                            <i class="fa fa-user fa-lg"></i> Group Item
                        </a>
                    </li>
                    <li class="${params.controller == 'singleItem' && params.action == 'index' ? 'active' : ''}">
                        <a href="${createLink(controller: 'singleItem', action: 'index')}">
                            <i class="fa fa-users fa-lg"></i> Single Item
                        </a>
                    </li>
                    </li>
                    <li class="visible-xs">
                        <a href="${createLink(controller: 'logout')}">
                            <i class="fa fa-dashboard fa-lg"></i> Log out
                        </a>
                    </li>
                </sec:access>
            </ul>
        </div>
    </div>
    <div class="container" id="main">
        <div class="clearfix" style="padding-top: 10px"/>
        <g:layoutBody/>
    </div>
    <script>
        $(document).ready(function(){
            jQuery(function ($) {
                $('ul.menu-content >ul.sub-menu >li.active:first').parent().show();
                $('ul.menu-content >ul.sub-menu >li.active:first').parent().parent().children('a.pushActive').addClass('active');
            });
        });
    </script>

    <div id="load_popup_modal_show_id" class="modal fade" id=""  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    </body>

</html>


