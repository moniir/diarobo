<g:form action="saveDosDonts" controller="diabeticRule" method="post">
    <input type="hidden" name="masterId" id="masterId" value="${generalRuleId?:basicInfo?.id}"/>
    <input type="hidden" name="moreDosDescription" id="moreDosDescription" value=""/>
    <input type="hidden" name="moreDontsDescription" id="moreDontsDescription" value=""/>
    <div class="row" ng-form style="margin-left: 5px;">
        <div class="form-group row">
            <label class="col-md-3" for="dosDescription">Dos</label>
            <div class="col-md-6">
                <input type="text" class="form-control" placeholder="Dos" id="dosDescription" name="dosDescription" >
            </div>
            <div class="col-md-1">
                <button class="btn btn-success btn-add-Dos" type="button">
                    <span class="glyphicon glyphicon-plus"></span>
                </button>
            </div>
        </div>
        <div id="addMoreDos"></div>

        <div class="form-group row">
            <label class="col-md-3" for="dontsDescription">DONTs</label>
            <div class="col-md-6">
                <input type="text" class="form-control" placeholder="DONTs" id="dontsDescription" name="dontsDescription" >
            </div>

            <div class="col-md-1">
                <button class="btn btn-success btn-add-Donts" type="button">
                    <span class="glyphicon glyphicon-plus"></span>
                </button>
            </div>
        </div>
        <div id="addMoreDonts"></div>
    </div>
        <div class="form-group text-center">
            <button class="btn btn-default" type="submit" >Save & Next</button>
        </div>

</g:form>
<script>
    $(document).ready(function () {
       var dosMoreDescription = [];
       var  dontsMoreDescription = [];

       <g:each in="${editDos}" var="dos">
        var dosvalue = '${dos.description}';
        var dosDescription = new Object();
        dosDescription.description = dosvalue;
        dosDescription.id = '${dos.id}';
        dosMoreDescription.push(dosDescription);
         $("#moreDosDescription").val(JSON.stringify(dosMoreDescription));
        addMore(dosvalue, dosMoreDescription,'Dos',function (html){
            $("#addMoreDos").append(html);
        });
        </g:each>

      <g:each in="${editDonts}" var="donts">
        var dontsvalue = '${donts.description}';
        var dontsText = new Object();
        dontsText.description =  dontsvalue;
        dontsText.id = '${donts.id}';
        dontsMoreDescription.push(dontsText);
        $("#moreDontsDescription").val(JSON.stringify(dontsMoreDescription));
        addMore(dontsvalue, dontsMoreDescription,'DoNTs',function (html){
            $("#addMoreDonts").append(html);
        })
    </g:each>


        $(".btn-add-Dos").click(function () {
         var description = $("#dosDescription").val();
          if(description!=''){
              var dosText = new Object();
              dosText.description =  description;
              dosText.id = null;
              dosMoreDescription.push(dosText);
              $("#moreDosDescription").val(JSON.stringify(dosMoreDescription));

              addMore(description, dosMoreDescription,'Dos',function (html){
                  $("#addMoreDos").append(html);
              })
            $("#dosDescription").val('');
            }else {
             showErrorMsg('Please Fill up field');
         }
        });

        $(document).on("click", ".btn-remove-dos", function() {
             var idx =  parseInt($(this).closest('.form-group').find('.indexDosPosition').text());
             dosMoreDescription.splice(idx, 1)
             $("#moreDosDescription").val(JSON.stringify(dosMoreDescription));
             $(this).closest('.form-group').remove();

        });


        $(".btn-add-Donts").click(function () {
            var description = $("#dontsDescription").val();
            if(description!=''){
                var dontsText = new Object();
                dontsText.description =  description;
                dontsText.id =  null;
                dontsMoreDescription.push(dontsText);
                $("#moreDontsDescription").val(JSON.stringify(dontsMoreDescription));
                addMore(description, dontsMoreDescription,'DoNTs',function (html){
                   $("#addMoreDonts").append(html);
                })

                $("#dontsDescription").val('');
            }else {
                showErrorMsg('Please Fill up field');
            }
        });

        $(document).on("click", ".btn-remove-donts", function() {
            var idx =  parseInt($(this).closest('.form-group').find('.indexDosPosition').text());
            dontsMoreDescription.splice(idx, 1);
            $("#moreDontsDescription").val(JSON.stringify(dontsMoreDescription));
            $(this).closest('.form-group').remove();

        });



    });

 var addMore = function(txt, arry, label, callbackfnc){
     var html;
       if(typeof callbackfnc =='function'){
            html ='<div class="form-group row">'
                   +'<label class="col-md-3" >'+label+'</label>'
                   +'<div class="col-md-6">'
                   +'<label class="indexDosPosition" style="display: none">'+(arry.length-1)+'</label>'
                   +'<input type="text" class="form-control"  readonly value="'+txt+'">'
                   +'</div>'
                   +'<div class="col-md-1">'
                  if(label==="Dos")   {
                      html+='<button class="btn btn-danger btn-remove-dos" type="button">'
                   }
                  if(label==="DoNTs"){
                    html+='<button class="btn btn-danger btn-remove-donts" type="button">'
                  }
           html+='<span class="glyphicon glyphicon-remove"></span>'
           html+='</button>'
           html+='</div>'
           html+='</div>'
           callbackfnc.call(this,html);
       }
 }



</script>
