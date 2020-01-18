/**
 * Created by rakib on 12/2/2016.
 */
var count;
function executeUrl() {
    var button = event.target;
    var url = button.getAttribute("data-url");
    window.location.href = url;
}

function inputOnEnterKeyPress(elementList, inputField, event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        addElementToList(elementList, inputField);
    }
}

function addElementToList(elementList, inputField) {
    var inputFieldValue = inputField.val();
    divChildCount(elementList)
    if (inputFieldValue) {
        $(elementList).append(
            '<div class="form-group">' +
            '<label class="col-md-2 control-label"><i class="fa fa-close"></i></label>' +
            '<div class="col-md-4"><input type="number" class="hidden id" value="0"><input id="nutritions" name="nutritions" type="text" class="nutrition form-control" value="' + inputFieldValue + '">' +
            '</div></div>'
        );
        inputField.val('');
    }
    return false;
}

function inputOnEnterKeyPressComposition(elementList, nameField, weightField, event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        addElementToListComposition(elementList, nameField, weightField);
    }
}


function addElementToListComposition(elementList, nameField, weightField) {
    var nameValue = nameField.val();
    var weightValue = weightField.val();
    divChildCount(elementList)
    if (nameValue && weightValue) {
        $(elementList).append(
            '<div class="form-group">' +
            '<input type="hidden" name="compositions" value="' + count + '"/>' +
            '<label class="col-md-2 control-label"><i class="fa fa-close"></i></label>' +
            '<div class="col-md-2">' +
            '<input id="compositionName' + count + '" name="compositionName' + count + '" type="text" class="form-control" value="' + nameValue + '">' +
            '</div>' +
            '<div class="col-md-2">' +
            '<input id="compositionWeight' + count + '" name="compositionWeight' + count + '" type="text" class="form-control" value="' + weightValue + '">' +
            '</div></div>'
        );
        nameField.val('');
        weightField.val('');
    }
    return false;
}

var divChildCount = function (divId) {
    if (typeof count === "undefined") {
        count = $(divId).children().length;
    } else {
        count++
    }
}

function addElementToProfessionalList(elementList, inputField) {
    var inputFieldValue = inputField.val();
    if (inputFieldValue) {
        elementList.find('ul').append(
            '<li class="element-li"><i class="fa fa-close"></i> <span class="newText">' + inputFieldValue + ' </span></li>'
        );
        inputField.val('');
    }
}


function inputOnEnterKeyPressDiabeticHistory(elementList, inputField, event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        addElementToListDiabeticHistory(elementList, inputField);
    }
}

function addElementToListDiabeticHistory(elementList, inputField) {
    var inputFieldValue = inputField.val();
    var data_id = inputField.attr('data-id');
    if (inputFieldValue) {
        elementList.find('ul').append(
            '<li class="element-li"><i class="fa fa-close"></i> <span class="newText" data-id="' + data_id + '">' + inputFieldValue + ' </span></li>'
        );
        inputField.val('');
    }
}



/*Modal of any conformation message
 * title is the Modal title,
 * msg is the modal body,
 * yesFunction is the OK button action,
 * noFunction is the Cancel button action,
 * Should have styleClass in the page for css*/
function modal(title, msg, yesFunction, noFunction, yesButtonShow, noButtonShow, styleClass) {
    var $confirm = $("#confirmYesNoModal");
    $confirm.addClass(styleClass);
    $confirm.modal('show');
    $confirm.find(".modal-title").html(title);
    $confirm.find(".modal-body").html(msg);
    if(yesButtonShow) {
        $("#btnYesConfirmYesNoModal").removeClass('hidden');
        $("#btnYesConfirmYesNoModal").off('click').click(function() {
            yesFunction();
            $confirm.modal("hide");
        });
    }
    if(noButtonShow) {
        $("#btnNoConfirmYesNoModal").removeClass('hidden');
        $("#btnNoConfirmYesNoModal").off('click').click(function() {
            noFunction();
            $confirm.modal("hide");
        });
    }
}


function messageModal(title, msg, styleClass) {
    var $confirm = $("#messageModal");
    if(styleClass) {
        $confirm.addClass(styleClass);
    }
    $confirm.find(".modal-title").html(title);
    $confirm.find(".modal-body").html(msg);
    $confirm.modal('show');
}


var search ={
    searchFromArray: function(array, key, value) {
        for(var i = 0 ; i< array.length; i++){
            if(array[i][key] == value)
                return array[i];
        }
        return null;
    },
    searchIndexFromArray: function(array, key, value) {
        for(var i = 0 ; i< array.length; i++){
            if(array[i][key] == value)
                return i;
        }
        return null;
    }
};
