<%@ page import="com.diarobo.MasterKeySetup; com.diarobo.District; com.diarobo.enums.MasterKeyValue" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
<body>
    <ol class="breadcrumb">
        <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
        <li><a href="${createLink(controller: 'profile', action: 'index')}">Profile</a></li>
        <li class="active">Create</li>
    </ol>
        <g:form controller="profile" action="saveProfile" method="POST" enctype="multipart/form-data" onsubmit="changeIfNoImage()">
            <input type="number" class="form-control hidden" name="user" value="${patientProfileInstance?.user?.id}">
            <input type="number" class="form-control hidden" name="id" value="${patientProfileInstance?.id}">
           <div class="row">
            <div class="col-md-12">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title panel-title-left">Profile</div>
                        <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                    </div>
                <div class="panel-body" >
                    <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                        <g:if test='${flash.message}'>
                            <div class="errorHandler alert alert-danger">
                                <i class="fa fa-remove-sign"></i>
                                ${flash.message}
                            </div>
                        </g:if>
                        </br>
                        <div class="row">
                            <g:if test="${hasPicture}">
                                <img id="showImage" class="center-image img-circle-customize" src="${createLink(controller: 'profile', action: 'showProfileImage', id: "${patientProfileInstance?.id}")}" alt="Profile image" width="180" height="180" />
                            </g:if>
                            <g:else>
                                <img class="center-image img-circle-customize" id="showImage" src="${assetPath(src: 'demo_profile_picture.png')}" alt="Profile image"/>
                            </g:else>
                        </div> </br>
                        <div class="row ${hasErrors(bean: patientProfileInstance, field: 'user.fullName', 'has-error')}">
                            <div class="col-md-3">Name</div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="name" value="${patientProfileInstance?.user?.fullName}" placeholder="Name"/>
                            </div>
                        </div> </br>

                        <div class="row ${hasErrors(bean: patientProfileInstance, field: 'fathersName', 'has-error')}">
                            <div class="col-md-3 ">Father's Name</div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="fathersName" value="${patientProfileInstance?.fathersName}" placeholder="Father's Name"/>
                            </div>
                        </div></br>

                        <div class="row ${hasErrors(bean: patientProfileInstance, field: 'mothersName', 'has-error')}">
                            <div class="col-md-3 ">Mother's Name</div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="mothersName" value="${patientProfileInstance?.mothersName}" placeholder="Mother's Name"/>
                            </div>
                        </div></br>

                        <div class="row ${hasErrors(bean: patientProfileInstance, field: 'commonProfile.dateOfBirth', 'has-error')}">
                            <div class="col-md-3 ">Date of Birth</div>
                            <div class="col-md-9">
                                <g:datePicker name="dateOfBirth" class="form-control" precision="day"
                                              value="${patientProfileInstance?.dateOfBirth}"
                                              placeholder="dd/mm/yyyy"/>
                            </div>
                        </div></br>

                        <div class="row ${hasErrors(bean: patientProfileInstance, field: 'gender', 'has-error')}">
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="gender">Gender</label>
                                <div class="col-md-9">
                                    <g:each in="${MasterKeySetup.findAllByKeyType(MasterKeyValue.GENDER.value)}" var="gender">
                                        <label class="radio-inline" for="${gender.keyValue}">
                                            <g:if test="${patientProfileInstance?.gender == gender.id}">
                                                <input name="gender" id="${gender.keyValue}" value="${gender.id}" type="radio" checked="checked">
                                                ${gender.keyValue}
                                            </g:if>
                                            <g:else>
                                                <input name="gender" id="${gender.keyValue}" value="${gender.id}" type="radio">
                                                ${gender.keyValue}
                                            </g:else>
                                        </label>
                                    </g:each>
                                </div>
                            </div>
                        </div></br>

                        <div class="row ${hasErrors(bean: patientProfileInstance, field: 'height', 'has-error')}">
                            <div class="col-md-3 ">Height</div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="height" value="${patientProfileInstance?.height}" placeholder="Height in inch"/>
                        </div>
                    </div></br>

                    <div class="row ${hasErrors(bean: patientProfileInstance, field: 'weight', 'has-error')}">
                        <div class="col-md-3 ">Weight</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="weight" value="${patientProfileInstance?.weight}" placeholder="Weight in kg"/>
                        </div>
                    </div></br>

                    <div class="row">
                        <div class="col-md-3 ">Present Address</div>
                         <div class="col-md-9">
                            <input type="text" class="form-control" name="presentAddressLine" value="${patientProfileInstance?.presentAddressLine}" placeholder="Address Line"/></br>
                             <g:select name="presentDistrict"
                                       from="${District.list()}"
                                       value="${patientProfileInstance?.presentDistrict?.id}"
                                       optionKey="id" optionValue="name"
                                       noSelection="${['null':'Select District']}"
                                       class="form-control"/>
                         </div>
                    </div></br>

                    <div class="row">
                        <div class="col-md-3 ">Permanent Address</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="permanentAddressLine" value="${patientProfileInstance?.permanentAddressLine}" placeholder="Address Line"/></br>
                            <g:select name="permanentDistrict"
                                      from="${District.list()}"
                                      value="${patientProfileInstance?.permanentDistrict?.id}"
                                      optionKey="id" optionValue="name"
                                      noSelection="${['null':'Select District']}"
                                      class="form-control"/>
                        </div>
                    </div></br>

                    <div class="row ${hasErrors(bean: patientProfileInstance, field: 'user.name', 'has-error')}">
                          <div class="col-md-3 ">Phone/Mobile</div>
                          <div class="col-md-9">
                        <input type="text" class="form-control" name="user.username" value="${patientProfileInstance?.user?.username}" placeholder="Phone/Mobile" readonly/>
                    </div>
                  </div></br>

                <div class="row">
                    <div class="col-md-3">Social Media Identity</div>
                    <div class="col-md-9">
                        <div class="col-md-3 ${hasErrors(bean: patientProfileInstance, field: 'facebook', 'has-error')}">Facebook</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control margin-bottom-small" name="facebook" value="${patientProfileInstance?.facebook}" placeholder="facebook url" />
                        </div>
                        <div class="col-md-3 ${hasErrors(bean: patientProfileInstance, field: 'skype', 'has-error')}">Skype</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control margin-bottom-small" name="skype" value="${patientProfileInstance?.skype}" placeholder="Skype User Name" />
                        </div>
                        <div class="col-md-3 ${hasErrors(bean: patientProfileInstance, field: 'whatsApp', 'has-error')}">Whats App</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="whatsApp" value="${patientProfileInstance?.whatsApp}" placeholder="Whats App User Name" />
                        </div>
                    </div>
                </div></br>


                <div class="row">
                    <div class="col-md-3 ">Profile Picture</div>
                    <div class="col-md-9">
                        <input type="file" name="profilePicture" id="profilePicture" class="btn btn-default btn-file" />
                        <span id="imageUploadError"></span>
                    </div>
                </div></br>

                <div class="form-group text-center">
                        <g:actionSubmit value="Save" action="saveProfile" class="btn btn-default cancel-btn" tabindex="7"/>
                        <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Save & Next</button>
                </div>
            </div>
            </div>
           </div>
         </div>
     </div>
        </g:form>

    <script>
        jQuery(function ($) {
            function changeIfNoImage() {
                var file = $('#profilePicture')[0].files[0];
                if (!file){
                    $('#profilePicture').removeAttr('name');
                }
            }

            $("#profilePicture").change(function(){
                var ext = this.value.match(/\.(.+)$/)[1];
                $('#imageUploadError').text('');
                $('#showImage').removeAttr('src');
                $('#showImage').removeAttr('width');
                $('#showImage').removeAttr('height');
                $('#showImage').addClass('hidden');
                switch(ext)
                {
                    case 'jpg':
                    case 'png':
                        readURL(this);
                        break;
                    default:
                        $('#imageUploadError').text('Not allowed. Please select only jpg/png image.');
                        this.value='';
                }
            });

            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        imageData = e.target.result;
                        $('#showImage').attr('src', imageData).width(180).height(180);
                        $('#showImage').removeClass('hidden');
                    }
                    reader.readAsDataURL(input.files[0]);
                }
            }
            });

    </script>
    </body>
</html>
