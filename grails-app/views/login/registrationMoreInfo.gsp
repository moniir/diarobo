<g:form controller="login" action="registrationProviderMoreInfo">
    <input class="hidden" name="userAccountId" type="text" value="${userAccountId}">
    <div class="input-group input-margin-bottom">
        <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
        <input id="username" type="text" class="form-control" name="username" value="" placeholder="Mobile Number">
    </div>
    <div class="form-group">
        <label class="panel-padding-left">AS</label>
        <g:each in="${userTypes}" var="userType">
            <div class="radio-inline">
                <label><input type="radio" name="userType" value="${userType.id}">${userType.name}</label>
            </div>
        </g:each>
    </div>
    <g:actionSubmit value="Registration" action="registrationProviderMoreInfo"></g:actionSubmit>
</g:form>