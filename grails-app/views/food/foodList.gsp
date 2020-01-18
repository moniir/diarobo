<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Home</title>
    </head>
    <body>
    <h1 class="page-header">AL: - Food list/dashboard</h1>
    <div class="row">
        <div class="col-md-9 col-md-offset-2 col-sm-10 col-sm-offset-1">
            <g:if test='${flash.message}'>
                <div class="errorHandler alert alert-danger">
                    <i class="fa fa-remove-sign"></i>
                    ${flash.message}
                </div>
            </g:if>
            <div class="panel panel-info" >
                <div class="panel-heading">
                    <div class="panel-title panel-title-left">AL: - Food list/dashboard</div>
                    <div class="panel-title-right"><a href="${createLink(uri: '/')}" target="_self">${message(code:'app.project.name.title' )}</a></div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-12">
                        <div class="input-group form-group">
                            <input type="text" class="form-control" placeholder="Search for...">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button"><i class="fa fa-search" aria-hidden="true"></i></button>
                            </span>
                         </div>
                            <nav aria-label="Page navigation">
                                <ul class="pagination food-search">
                                    <li><a href="#">A</a></li>
                                    <li><a href="#">B</a></li>
                                    <li><a href="#">C</a></li>
                                    <li><a href="#">D</a></li>
                                    <li><a href="#">E</a></li>
                                    <li><a href="#">E</a></li>
                                    <li><a href="#">F</a></li>
                                    <li><a href="#">G</a></li>
                                    <li><a href="#">H</a></li>
                                    <li><a href="#">I</a></li>
                                    <li><a href="#">J</a></li>
                                    <li><a href="#">K</a></li>
                                    <li><a href="#">L</a></li>
                                    <li><a href="#">M</a></li>
                                    <li><a href="#">N</a></li>
                                    <li><a href="#">O</a></li>
                                    <li><a href="#">P</a></li>
                                    <li><a href="#">Q</a></li>
                                    <li><a href="#">R</a></li>
                                    <li><a href="#">S</a></li>
                                    <li><a href="#">T</a></li>
                                    <li><a href="#">U</a></li>
                                    <li><a href="#">V</a></li>
                                    <li><a href="#">W</a></li>
                                    <li><a href="#">X</a></li>
                                    <li><a href="#">Y</a></li>
                                    <li><a href="#">Z</a></li>
                                </ul>
                            </nav>
                     </div>
                        <div class="row food-image">
                            <div class="col-md-12">
                                <div class="col-md-6 banana"><br/><br/>
                                    <span><strong>Banana</strong></span><br/>
                                    <img class="food-search col-md-offset-1" src="${assetPath(src: 'banana.jpg')}" alt=""><br/>
                                    <span class="col-md-offset-2">100 g | Calories 110</span>
                                </div>
                                <div class="col-md-6"><br/><br/>
                                    <span><strong>Mango</strong></span><br/>
                                    <img class="food-search col-md-offset-1" src="${assetPath(src: 'mango.jpg')}" alt=""><br/>
                                    <span class="col-md-offset-2">100 g | Calories 44</span>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="col-md-6"><br/><br/>
                                    <span><strong>Cucumber</strong></span><br/>
                                    <img class="food-search col-md-offset-1" src="${assetPath(src: 'cucumbers.jpg')}" alt=""><br/>
                                    <span class="col-md-offset-2">100 g | Calories 15</span>
                                </div>
                                <div class="col-md-6 form-group"><br/><br/>
                                    <span><strong>Tomato</strong></span><br/>
                                    <img class="food-search col-md-offset-1" src="${assetPath(src: 'tomato.jpg')}" alt=""><br/>
                                    <span class="col-md-offset-2">100 g | Calories 15</span>
                                </div>
                            </div>

                        </div>

                  </div>
                    <div class="form-group pull-right">
                        <nav aria-label="Page navigation">
                            <ul class="pagination food-search">
                                <li><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li><a href="#">6</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </body>
</html>
