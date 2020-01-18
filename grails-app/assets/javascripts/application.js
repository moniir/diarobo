// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require vendor/jquery/jquery-2.2.0.min
//= require vendor/jquery/jquery.validate
//= require vendor/jquery/jquery.numeric
//= require vendor/jquery/jquery.dataTables.min
//= require moment
//= require vendor/bootstrap/bootstrap.min
//= require vendor/bootstrap/bootstrap-datepicker
//= require vendor/bootstrap/bootstrap-datetimepicker.min
//= require vendor/bootstrap/bootstrap-growl.min
//= require vendor/bootstrap/bootstrap-multiselect
//= require vendor/bootstrap/dataTables.bootstrap.min
//= require vendor/typeahead/handlebars-v4.0.5
//= require vendor/typeahead/typeahead.bundle
//= require vendor/select2/select2
//= require vendor/angularJs/angular.min
//= require custom
//= require common

//= require_self
//= require vendor/angularJs/angular.min
//= require autoSearch


$.fn.select2.defaults.set( "theme", "bootstrap" );

if (typeof jQuery !== 'undefined') {
    (function($) {
        $(document).ajaxStart(function() {
            $('#spinner').fadeIn();
        }).ajaxStop(function() {
            $('#spinner').fadeOut();
        });
     })(jQuery);
}
var showSuccessMsg=function(msg){
    showMsg("success","glyphicon glyphicon-ok","<strong> Success</strong><br/>", msg);
}
var showErrorMsg=function(msg){
    showMsg("danger","glyphicon glyphicon-remove", "<strong> Error</strong><br/>", msg);
}
var showInfoMsg=function(msg){
    showMsg("info","glyphicon glyphicon-info-sign","<strong> Info</strong><br/>", msg);
}

function showMsg(type, icon, title, msg){
    $.growl(false, {
        allow_dismiss: true,
        animate: {
            enter: 'animated bounceIn',
            exit: 'animated bounceOut'
        },
        delay:1000,
        offset: 5,
        command: 'closeAll'
    });
    $.growl({
            icon: icon,
            title:title ,
            message: msg
        },
        {
            type:type,
            placement:{
                from:'bottom',
                align:'right'
            }
        });
}
var ajaxBindDropdown=function(url, dropdownId){
    jQuery.ajax({
        type: 'POST',
        dataType: 'JSON',
        url: url,
        success: function (data, textStatus) {
            var $itemCtrl = $(dropdownId);
            $itemCtrl.find('option:gt(0)').remove();
            if (data.isError == false) {
                $.each(data.groupItemList,function(key, value)
                {
                    $itemCtrl.append('<option value=' + value.id + '>' + value.name + '</option>');
                });
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
var glModal = function(url,callbackglab) {

    var $modal=$('#load_popup_modal_show_id').load(url,function(){
        $modal.modal('show');
        if(typeof callbackglab == 'function'){
            callbackglab.call(this, $modal);
        }
    });
}

var initNumeric=function(){
    $(".numericfloat").numeric({decimalPlaces: 2 });
}


