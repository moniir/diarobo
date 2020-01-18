<table class="table table-striped table-hover table-bordered">
    <thead>
    <tr>
        <th>Type</th>
        <th>Package</th>
        <th>Image</th>
        <th>Date Time</th>
        <th>Feedback Status</th>
    </tr>
    </thead>
    <tbody>
    <g:set var="reminderDate"></g:set>
    <g:each in="${reminderList}" var="reminder" status="it">
        <tr>
            <td>${reminder?.libraryType?.replace("_library", "")}</td>
            <td>
                <g:each in="${foodPackageList}" var="foodPackage">
                    <g:if test="${foodPackage.id == reminder?.packageId}">
                            ${foodPackage.packageName}
                    </g:if>
                </g:each>
            </td>
            <td><img src="${createLink(controller: "diaroboApi", action: "showImage", params: [libraryId: reminder?.libraryId, libraryType: reminder?.libraryType])}" width="100" height="100"></td>
            <td>
                ${reminder?.dateTime.format('dd MMM, yyyy hh:mm a')}
            </td>
            <td>${reminder?.yesFeedback}</td>
        </tr>
    </g:each>
    </tbody>
</table>