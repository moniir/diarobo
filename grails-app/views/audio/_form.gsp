<p id="errorMessage" class="errorHandler alert alert-danger hidden"></p>
<input type="number" id="exerciseId" class="hidden" value="${audioInstance?.id}">
<div class="form-group" id="showName">
    <label class="col-md-2 control-label" for="name">Audio Name</label>
    <div class="col-md-8">
        <input id="name" name="name" value="${audioInstance?.name}" type="text" placeholder="Name"  class="form-control" required="">
    </div>
</div>
<div class="form-group">
    <label class="col-md-2 control-label" for="uploadAudio">Audio</label>
    <div class="col-md-8">
        <input id="uploadAudio" name="uploadAudio" type="file"  class="form-control input-md" required="">
       %{-- <g:if test="${exerciseLibraryInstance != null}">
            <input id="image" name="image" type="file"  class="form-control input-md" required="">
        </g:if>
        <g:else>
            <input id="image" name="image" type="file"  class="form-control input-md" required="">
        </g:else>--}%
    </div>
</div>


<!-- Text input-->


<script>
    function saveExercise(type) {
        var name = $('#name').val();
        var id = $('#exerciseId').val();
       // var uploadAudio = $('#uploadAudio').val();
        var dataWithImage = new FormData();
        dataWithImage.append('id', id);
        dataWithImage.append('name', name);
        dataWithImage.append('uploadAudio', $("#uploadAudio")[0].files[0]);

        if(name) {
            $.ajax({
                url: type == 'create' ? "${createLink(controller:'audio', action: 'create')}" : "${createLink(controller:'audio', action: 'update')}",
                data: dataWithImage,
                type: 'post',
                processData : false,
                contentType : false,
                success: function(res){
                    $('#errorMessage').html(res.message);
                    $('#errorMessage').removeClass('hidden');
                },
                error: function(res){
                    $('#errorMessage').html(res.error());
                    $('#errorMessage').removeClass('hidden');
                }
            });
        } else {
            $('#errorMessage').html('Please input all information & try again.');
            $('#errorMessage').removeClass('hidden');
        }
    }

</script>



