<div class="modal-dialog">
    <div class="modal-content">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-left">Group Item</div>
                <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
            </div>
            <div class="panel-body">
                <form class="form-horizontal"  role="form" id="createFormModal">
                    <div class="col-md-12" id="group-item-holder">
                        <fieldset>
                            <div class="form-group"></div>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="groupType" style="text-align: left">Group Type</label>
                                <div class="col-md-9">
                                    <select class="form-control" id="groupType" name="itemType">
                                        <option value="">Select Parent Item</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="groupParent" style="text-align: left">Has Parent Item</label>
                                <div class="col-md-1">
                                    <div class="customCheckbox">
                                        <label style="font-size: 1.5em">
                                            <input type="checkbox" class="pChk" name="check1">
                                            <span class="cr"><i class="cr-icon fa fa-check"></i></span>
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-8" id="parentDivItem" style="display: none">
                                    <select class="form-control" id="groupParent" name="parentId" value="">
                                        <option value="">Select Parent Item</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="name" style="text-align: left">Name</label>
                                <div class="col-md-9">
                                    <input id="name" name="name" type="text" value="" class="form-control input-md" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"></label>
                                <div class="col-md-9">
                                    <button type="button" class="btn btn-primary" id="addGropTypebtn">Add</button>
                                    <button class="btn  btn-default" data-dismiss="modal" aria-hidden="true" id="modalCanbtn">Cancel</button>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    jQuery(function ($) {
        groupTypedropdown = "${g.createLink(controller: 'remote',action: 'groupTypeDropDown')}"
        ajaxBindDropdown(groupTypedropdown, $('#groupType'));

        $(document).on('change', '#groupType', function(e){
            var groupType =$('#groupType').val();
            if (groupType) {
                groupTypeUrl = "${g.createLink(controller: 'remote',action: 'listGrupItemDropDown')}?itemType="+groupType;
                ajaxBindDropdown(groupTypeUrl, $('#groupParent'));
            }
            $('#groupParent').val("").trigger("change");
        });

      $('.pChk').click(function() {
            if( $(this).is(':checked')) {
                $("#parentDivItem").show();
            } else {
                $("#parentDivItem").hide();
            }
        });

        $(document).on('click',"#addGropTypebtn",function(){
            $.ajax({
                url: "${createLink(controller: 'remote',action: 'addGroupType')}",
                type: 'post',
                dataType: "json",
                data: $("#createFormModal").serialize(),
                success: function (data) {
                    if (data.isError == false) {
                        showSuccessMsg(data.message);
                         $("#modalCanbtn").click();

                    } else {
                        showErrorMsg(data.message);
                    }

                },
                failure: function (data) {
                }
            })
        })
    });


</script>



