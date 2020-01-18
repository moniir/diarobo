<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
</head>
<body>
<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
    <li><a href="${createLink(controller: 'food', action: 'index')}">Food Item</a></li>
    <li class="active">List</li>
</ol>


<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title panel-title-list ">List Item
                    <span class="pull-right"><a href="${createLink(controller: 'food', action: 'create')}"><i class="fa fa-plus" aria-hidden="true"></i></a></span>
                </div>
            </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped table-hover table-bordered" id="list-table">
                <thead>
                <tr>
                    <th>SL NO</th>
                    <th>Name</th>
                    <th>Net Weight</th>
                    <th>Measurement Unit</th>
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
    $('#list-table').dataTable({
        "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-3'l><'col-md-2'i><'col-md-7'p>>",
        "bAutoWidth": true,
        "scrollX": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "aaSorting": [0, 'asc'],
        "sAjaxSource": "${g.createLink(controller:'food', action:'list')}",
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

    $('#list-table').on('click', 'a.delete-reference', function (e) {
        var selectRow = $(this).parents('tr');
        var confirmDel = confirm("Are you sure?");
        if (confirmDel == true) {
            var control = this;
            var referenceId = $(control).attr('referenceId');
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: "${g.createLink(controller: 'food',action: 'delete')}?id=" + referenceId,
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


    $('#list-table').on('click', 'a.details-reference', function (e) {
        var control = this;
        var referenceId = $(control).attr('referenceId');
        var url = "${g.createLink(controller: 'food',action: 'foodDetails')}/" + referenceId;
        window.open(url);
        e.preventDefault();
    });


    $('#list-table').on('click', 'a.edit-reference', function (e) {
        var control = this;
        var referenceId = $(control).attr('referenceId');
        jQuery.ajax({
            type: 'POST',
            dataType: 'JSON',
            url: "${g.createLink(controller: 'food', action: 'edit')}?id=" + referenceId,
            success: function (data, textStatus) {
                if (data.isError == false) {
                   /* $('#id').val('');
                    $('#id').val(data.obj.id);*/

                    $("#id").val(data.obj.id)
                    $('#hiddenId').val(data.obj.id);
                    $('#foodGroup').val(data.obj.groupItem);
                    $('#name').val(data.obj.name);
                    $('#weightMeasure').val(data.obj.weightMeasure);
                    $('#measureUnit').val(data.obj.measureUnit);
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
        actionButtons += '<span class="col-md-3 no-padding"><a href="${createLink(controller: 'food', action: 'edit')}?foodId=' + aData.DT_RowId + '" title="Edit">';
        actionButtons += '<span class="green glyphicon glyphicon-edit"></span>';
        actionButtons += '</a></span>';
        actionButtons += '<span class="col-md-3 no-padding"><a href="#" referenceId="' + aData.DT_RowId + '" class="delete-reference" title="Delete">';
        actionButtons += '<span class="red glyphicon glyphicon-trash"></span>';
        actionButtons += '</a></span>';
        actionButtons += '<span class="col-md-3 no-padding"><a href="#" referenceId="' + aData.DT_RowId + '" class="details-reference" title="Details">';
        actionButtons += '<span class="glyphicon glyphicon-list-alt"></span>';
        actionButtons += '</a></span>';
        return actionButtons;
    }
</script>
</body>
</html>




