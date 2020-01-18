<ol class="breadcrumb">
    <li><a href="${createLink(uri: '/')}"><g:message code="app.project.name.title"/></a></li>
    <g:if test="${bread1Url}">
        <li>
            <a  href="${bread1Url}">${bread1Text}</a>
        </li>
    </g:if>
    <g:if test="${bread2Url}">
        <li>
            <a  href="${bread2Url}">${bread2Text}</a>
        </li>
    </g:if>
    <g:if test="${bread3Url}">
        <li>
            <a  href="${bread3Url}">${bread3Text}</a>
        </li>
    </g:if>
    <li class="active">${breadTitle}</li>
</ol>
