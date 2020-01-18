<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'groupItem', action: 'index')}">List Item</a></li>
    <li class="active">Create</li>
</ol>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-list">Group Item
                    <span class="pull-right"><a title="Back" href="${createLink(controller: 'singleItem', action: 'index')}"><i class="fa fa-reply" aria-hidden="true"></i></a></span>
                </div>
            </div>
            <div class="panel-body">
                <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                    <g:if test='${flash.message}'>
                        <div class="errorHandler alert alert-danger">
                            <i class="fa fa-remove-sign"></i>
                            ${flash.message}
                        </div>
                    </g:if>
                <g:form class="form-horizontal" controller="singleItem" action="create" method="POST">
                    <div class="col-md-12" id="group-item-holder">
                        <fieldset>
                            <div class="form-group"></div>
                            <div class="form-group ${hasErrors(bean: groupItemInstance, field: 'libraryType', 'has-error')}">
                                <label class="col-md-3 control-label" for="groupType" style="text-align: left">Type</label>
                                <div class="col-md-9">
                                    <g:select class="form-control" id="groupType" name='libraryType' value="" tabindex="1" from='${groupTypeList}'
                                              noSelection="${['': 'Select group type...']}"
                                              optionKey="id" optionValue="name" required=""></g:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label" for="name" style="text-align: left">Key Name</label>
                                <div class="col-md-9">
                                    <input id="keyName" name="keyName" type="text" value="" class="form-control input-md" required="">
                                </div>
                            </div>

                            <div class="form-group ${hasErrors(bean: groupItemInstance, field: 'name', 'has-error')}">
                                <label class="col-md-3 control-label" for="name" style="text-align: left">Name</label>
                                <div class="col-md-9">
                                    <input id="name" name="name" type="text" value="${groupItemInstance?.name}" class="form-control input-md" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"></label>
                                <div class="col-md-9">
                                    <g:actionSubmit value="SUBMIT" action="create" class="btn btn-primary"></g:actionSubmit>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                 </g:form>
              </div>
            </div>
        </div>
    </div>
</div>

<script>
    jQuery(function ($) {
        $('#groupType').on('change', function (e) {
            var groupType =$('#groupType').val();
            if (groupType) {
                groupTypeUrl = "${g.createLink(controller: 'remote', action: 'listGrupItemDropDown')}?libraryType="+groupType;
                loadGroupItem(groupTypeUrl, $('#groupParent'));
            }
            $('#groupParent').val("").trigger("change");


        });

        function loadGroupItem(groupUrl, itemCtrl){
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: groupUrl,
                success: function (data, textStatus) {
                    var $itemCtrl = $(itemCtrl);
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

    });


</script>

</body>
</html>