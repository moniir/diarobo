<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Exercise Package</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'exercisePackage', action: 'index')}">Exercise Package </a></li>
    <li class="active">List</li>
</ol>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-list ">Exercise Package Details
                    <span class="pull-right"><a href="${createLink(controller: 'exercisePackage', action: 'create')}"><i class="fa fa-plus" aria-hidden="true"></i></a></span>
                </div>
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover table-bordered" id="list-table">
                        <thead>
                        <tr>
                            <th>SL NO</th>
                            <th>Package Name</th>
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
    $('#list-table').DataTable({
        "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-3'l><'col-md-2'i><'col-md-7'p>>",
        "bAutoWidth": true,
        "scrollX": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "aaSorting": [0, 'asc'],
        "sAjaxSource": "${g.createLink(controller:'exercisePackage', action:'list')}",
        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            if (aData.DT_RowId == undefined) {
                return true;
            }
            $('td:eq(2)', nRow).html(getActionButtons(nRow, aData));
            return nRow;
        },
        "aoColumns": [
            null,
            null,
            null
        ]
    });

    $('#list-table').on('click', 'a.edit-reference', function (e) {
        var control = this;
        var referenceId = $(control).attr('referenceId');
        window.location.href="${createLink(controller: 'exercisePackage', action: 'create')}?exercisePackageId=" + referenceId;
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
                url: "${g.createLink(controller: 'exercisePackage',action: 'delete')}?id=" + referenceId,
                success: function (data, textStatus) {
                    if (data.isError == false) {
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