<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'groupItem', action: 'index')}">Group Item</a></li>
    <li class="active">List</li>
</ol>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-list">Group Item
                    <span class="pull-right"><a href="${createLink(controller: 'groupItem', action: 'create')}"><i class="fa fa-plus" aria-hidden="true"></i></a></span>
                </div>
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover table-bordered" id="list-table">
                        <thead>
                            <tr>
                                <th>SL</th>
                                <th>Group Type</th>
                                <th>Parent</th>
                                <th>Name</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    var groupItemList = $('#list-table').DataTable({
        "sDom": "<'row'<'col-md-4 groupType-holder'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-3'l><'col-md-2'i><'col-md-7'p>>",
        "bAutoWidth": true,
        "scrollX": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "aaSorting": [0, 'asc'],
        "sAjaxSource": "${g.createLink(controller:'groupItem', action:'list')}",
        "fnServerParams": function (aoData) {
            aoData.push({ "name": "groupType", "value": $('#groupType').val() })
        },

        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            if (aData.DT_RowId == undefined) {
                return true;
            }
            $('td:eq(4)', nRow).html(getActionButtons(nRow, aData));
            return nRow;
        },
        "aoColumns": [
            null,
            null,
            null,
            null,
            null
        ]
    });
    $('#list-table_wrapper div.groupType-holder').html('<select id="groupType" class="form-control" name="groupType"><option value="">All Group</option><g:each in="${groupTypeList}" var="groupItem"><option value="${groupItem.id}">${groupItem.name}</option> </g:each></select>');
    $('#groupType').on('change', function (e) {
        groupItemList.draw();
    });

    $('#list-table').on('click', 'a.delete-reference', function (e) {
        var selectRow = $(this).parents('tr');
        var confirmDel = confirm("Are you sure?");
        if (confirmDel == true) {
            var control = this;
            var referenceId = $(control).attr('referenceId');
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: "${g.createLink(controller: 'groupItem',action: 'delete')}?id=" + referenceId,
                success: function (data, textStatus) {
                    if (data.isError == false) {
                       /* groupItemList.draw();*/
                        $("#list-table").DataTable().row(selectRow).remove().draw(false);
                        showSuccessMsg(data.message);
                    } else {
                        showErrorMsg(data.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }
        e.preventDefault();
    });

    $('#list-table').on('click', 'a.edit-reference', function (e) {
        var control = this;
        var referenceId = $(control).attr('referenceId');
        jQuery.ajax({
            type: 'POST',
            dataType: 'JSON',
            url: "${g.createLink(controller: 'groupItem',action: 'edit')}?id=" + referenceId,
            success: function (data, textStatus) {
                if (data.isError == false) {
                    clearForm('#create-form');
                    $('#id').val('');
                    $('#id').val(data.obj.id);
                    $('#itemType').val(data.obj.itemType ? data.obj.itemType.name : '');
                    $('#parentId').val(data.obj.parentId);
                    $('#name').val(data.obj.name);
                } else {
                    showErrorMsg(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
        e.preventDefault();
    });

    function getActionButtons(nRow, aData) {
        var actionButtons = "";
        actionButtons += '<span class="col-md-3 no-padding"><a href="#" referenceId="' + aData.DT_RowId + '" class="edit-reference" title="Edit">';
        actionButtons += '<span class="green glyphicon glyphicon-edit"></span>';
        actionButtons += '</a></span>';
        actionButtons += '<span class="col-md-3 no-padding"><a href="#" referenceId="' + aData.DT_RowId + '" class="delete-reference" title="Delete">';
        actionButtons += '<span class="red glyphicon glyphicon-trash"></span>';
        actionButtons += '</a></span>';
        return actionButtons;
    }
</script>

</body>
</html>




