<g:form action="saveComplications" controller="diabeticRule" method="post">
    <input type="hidden" name="masterId" id="masterId" value="${generalRuleId?:basicInfo?.id}"/>
    <input type="hidden" name="moreComplications" id="moreComplications" value=""/>
    <div class="row" ng-form style="margin-left: 5px;">
        <div class="form-group row">
            <label class="col-md-3" for="complication">Complications</label>
            <div class="col-md-6">
                <input type="text" class="form-control" placeholder="Complications" id="complication" name="complication">
            </div>
            <div class="col-md-1">
                <button class="btn btn-success btn-add-complication" type="button">
                    <span class="glyphicon glyphicon-plus"></span>
                </button>
            </div>
        </div>
        <div id="moreComplication">
    </div>

    </div>
        <div class="form-group text-center">
            <button class="btn btn-default">Save</button>
        </div>
    </g:form>
<script>
    $(document).ready(function () {
        var moreComplications = [];

        <g:each in="${complicationList}" var="complication">
        var txt = '${complication.description}';
        var complicationText = new Object();
        complicationText.complication =  txt;
        complicationText.id =  '${complication.id}';
        moreComplications.push(complicationText);
        $("#moreComplications").val(JSON.stringify(moreComplications));
        addMoreComplication(txt,moreComplications,'Complications',function (html) {
            $("#moreComplication").append(html);
        })

        </g:each>

        $(".btn-add-complication").click(function () {
            var complication = $("#complication").val();
            if(complication!=''){
                var complicationText = new Object();
                complicationText.complication =  complication;
                complicationText.id =  null;
                moreComplications.push(complicationText);
                $("#moreComplications").val(JSON.stringify(moreComplications));
                addMoreComplication(complication,moreComplications,'Complications',function (html) {
                    $("#moreComplication").append(html);
                })

                $("#complication").val('');
            }else {
                showErrorMsg('Please Fill up field');
            }
        });

        $(document).on("click", ".btn-remove-complication", function() {
            var idx =  parseInt($(this).closest('.form-group').find('.indexPosition').text());
            moreComplications.splice(idx, 1)
            $("#moreComplications").val(JSON.stringify(moreComplications));
            $(this).closest('.form-group').remove();

        });
    })

    var addMoreComplication = function(txt, arry, label, callbackfnc){
        var html;
        if(typeof callbackfnc =='function'){
            var html ='<div class="form-group row">'
                    +'<label class="col-md-3" >'+label+'</label>'
                    +'<div class="col-md-6">'
                    +'<label class="indexPosition" style="display: none">'+(arry.length-1)+'</label>'
                    +'<input type="text" class="form-control" placeholder="Dos"  readonly value="'+txt+'">'
                    +'</div>'
                    +'<div class="col-md-1">'
                    +'<button class="btn btn-danger btn-remove-complication" type="button">'
                    +'<span class="glyphicon glyphicon-remove"></span>'
                    +'</button>'
                    +'</div>'
                    +'</div>'
            callbackfnc.call(this,html);
        }
    }
</script>

