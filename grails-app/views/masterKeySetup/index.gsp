<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'masterKeySetup.label', default: 'MasterKeySetup')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-masterKeySetup" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${masterKeySetupList}">
                <div class="container">
                    <table class="table table-striped">
                        <thead>
                        <th>Key Value</th>
                        <th>Key Name</th>
                        <th>Key Type</th>
                        </thead>
                        <tbody>
                            <g:each in="${masterKeySetupList}" var="masterKey">
                                <tr>
                                    <td>${masterKey?.keyValue}</td>
                                    <td>${masterKey?.keyName}</td>
                                    <td>${masterKey?.keyType}</td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
            </g:if>
            <div class="pagination">
                <g:paginate total="${masterKeySetupCount ?: 0}" />
            </div>
        </div>
    </body>
</html>