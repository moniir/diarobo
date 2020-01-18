<div class="col-md-12">
    <h2>Package Name: ${foodPackageName}</h2>
</div>
<div class="col-md-12">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>No</th>
            <th>Exercise Name</th>
            <th>Exercise Image</th>
            <th>Exercise Duration/Hour</th>
        </tr>
        </thead>
        <tbody>
            <g:each in="${exerciseList}" var="exercise" status="i">
                <tr>
                    <td>${i+1}</td>
                    <td>${exercise?.exerciseName}</td>
                    <td><img src="${createLink(controller: 'exercise', action: 'showImage', id: "${exercise?.exerciseLibraryId}")}" height="100" width="100"></td>
                    <td>${exercise?.quantity} ${exercise?.measurementUnit}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>