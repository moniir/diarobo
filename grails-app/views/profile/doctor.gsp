<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
    <body>
        <h1 class="page-header">Profile</h1>

        <div class="row">
            <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title panel-title-left">doctor</div>
                        <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                    </div>
                    <div class="panel-body panel-body-start" >
                        <g:if test='${flash.message}'>
                            <div class="errorHandler alert alert-danger">
                                <i class="fa fa-remove-sign"></i>
                                ${flash.message}
                            </div>
                        </g:if>


                        <div class="row">
                            <div class="col-md-3 ">Name</div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="name" placeholder="Name"/>
                            </div>
                        </div> </br>

                        <div class="row">
                            <div class="col-md-3 ">BMDC Reg ID</div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="bmdcRegId" placeholder="BMDC Reg ID"/>
                            </div>
                        </div></br>

                        <div class="row">
                            <div class="col-md-3 ">Date of Birth</div>
                            <div class="col-md-9">
                                <input type="date" class="form-control" id="birthDate" placeholder="dd/mm/yyyy"/>
                            </div>
                        </div></br>

                        <div class="row">
                            <div class="col-md-3 ">Gender</div>
                            <div class="col-md-9">
                                <form action="" id="gender">
                                    <input type="radio" name="gender" value="male"> Male
                                    <input type="radio" name="gender" value="female"> Female
                                    <input type="radio" name="gender" value="other"> Other
                                </form>
                            </div>
                        </div></br>


                    <div class="row">
                                <div class="col-md-3 ">Present Address</div>
                                 <div class="col-md-9">
                                    <input type="text" class="form-control" id="preAddLine1" placeholder="Address Line 1"/></br>
                                        <input type="text" class="form-control" id="preAddLine2" placeholder="Address Line 2"/></br>
                                     <div class="row">
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="prePostOffice" placeholder="Post Office"/>
                                    </div>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" id="preUpazila" placeholder="Upazila"/>
                                        </div>
                                         </div></br>
                                     <div class="row">
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="preDistrict" placeholder="District"/>
                                    </div>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="prePostCode" placeholder="Post Code"/>
                                      </div>
                                    </div>
                                </div>
                             </div></br>

                    <div class="row">
                        <div class="col-md-3 ">Permanent Address</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="perAddLine1" placeholder="Address Line 1"/></br>
                            <input type="text" class="form-control" id="perAddLine2" placeholder="Address Line 2"/></br>
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="perPostOffice" placeholder="Post Office"/>
                                </div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="perUpazila" placeholder="Upazila"/>
                                </div>
                            </div></br>
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="perDistrict" placeholder="District"/>
                                </div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="perPostCode" placeholder="Post Code"/>
                                </div>
                            </div>
                        </div>
                    </div></br>

                        <div class="row">
                              <div class="col-md-3 ">Phone/Mobile</div>
                              <div class="col-md-9">
                            <input type="text" class="form-control" id="mobileNo" placeholder="Phone/Mobile"/>
                        </div>
                      </div></br>

                    <div class="row">
                        <div class="col-md-3 ">E-mail</div>
                        <div class="col-md-9">
                            <input type="email" class="form-control" id="email" placeholder="E-mail"/>
                        </div>
                    </div></br>

                    <div class="row">
                        <div class="col-md-3">Social/Skype/ WhatsApp</div>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="social" placeholder="Social/Skype/WhatsApp"/>
                        </div>
                    </div></br>

                    <div class="row">
                        <div class="col-md-3 ">Photo</div>
                        <div class="col-md-9">
                            <input type="file" class="filestyle" data-input="false">
                        </div>
                    </div></br>

                        <div class="form-group">
                            <div class="col-md-offset-7 col-lg-5">
                                <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Save</button>
                                <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Save & Next</button>
                                <button class="btn btn-default cancel-btn" tabindex="7" type="reset">Cancel</button>
                            </div>
                        </div>

                </div>
               </div>
             </div>
            </div>

    <script>
        jQuery(function ($) {
            $("#birthDate").datepicker({
                format: 'dd/mm/yyyy',
                autoclose: true
            });

            $(":file").filestyle({input: false});
            });

    </script>
    </body>
</html>
