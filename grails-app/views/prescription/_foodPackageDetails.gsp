<div class="col-md-12">
    <h2>Package Name: ${foodPackageName}</h2>
</div>
<div class="col-md-12">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>No</th>
            <th>Food Name</th>
            <th>Food Image</th>
            <th>Food Compositions</th>
            <th>Food Quantity</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${foodList}" var="food" status="i">
            <tr>
                <td>${i+1}</td>
                <td>${food?.foodName}</td>
                <td><img src="${createLink(controller: 'food', action: 'showImage', id: "${food?.foodLibraryId}")}" height="100" width="100"></td>
                <td>
                    <g:each in="${food?.compositionList}" var="composition" status="index">
                        <p>${index + 1}. ${composition?.name}: ${composition?.quantity} ${food?.weightMeasure}</p>
                    </g:each>
                </td>
                <td>${food?.quantity} ${food?.measurementUnit}</td>
            </tr>
        </g:each>

        </tbody>
    </table>
</div>