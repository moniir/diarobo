<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
    <body>
    <ol class="breadcrumb">
        <li><a href="${createLink(uri: '/')}">DIAROBO</a></li>
        <li class="active">Food Details</li>
    </ol>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-list">Food Details</div>
                </div>
                <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <g:if test='${flash.message}'>
                                    <div class="errorHandler alert alert-danger">
                                        <i class="fa fa-remove-sign"></i>
                                        ${flash.message}
                                    </div>
                                </g:if>
                                <div class="col-md-6"><br/>
                                    <span><strong>${foodLibrary.name}</strong></span><br/>
                                    <g:if test="${foodLibrary}">
                                        <img id="showImage" class="food-details col-md-offset-1" src="${createLink(controller: 'food', action: 'showImage', id: "${foodLibrary?.id}")}" alt="Library image"/>
                                    </g:if>
                                    <g:else>
                                        <img class="food-details col-md-offset-1" id="showImage" src="${assetPath(src: 'banana.jpg')}" alt=""/>
                                    </g:else>
                                        <div class="col-md-12">
                                            <span><strong><u>Nutrition(s)</u></strong></span><br/>
                                            <span>
                                                <ul>
                                                    <g:each in="${nutritionList}" var="nutrition" >
                                                        <li>${nutrition?.name}</li>
                                                    </g:each>
                                                </ul>
                                            </span>
                                        </div>
                                </div>
                                <div class="col-md-6"><br/>
                                    <span><strong><u>Quantity: ${foodLibrary?.approximateWeight} ${weightUnit}</u></strong></span><br/>
                                    <span>
                                        <ul>
                                            <g:each in="${compositionList}" var="composition" >
                                                <li>${composition?.name} ${composition?.quantity} ${weightUnit}</li>
                                            </g:each>
                                        </ul>
                                    </span>
                                </div>
                            </div>

                        </div>
                  </div>
                    <div class="form-group  text-center">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default ">< Preview</button>
                            <button type="button" class="btn btn-default pull-right "> Next ></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
