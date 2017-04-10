app.factory("OperationService", ['$http', '$log', function ($http, $log) {
    return {
        findAll: function () {
            return $http.get("/api/operation/findAll").then(function (response) {
                return response.data;
            });
        },
        findOne: function (id) {
            return $http.get("/api/operation/findOne/" + id).then(function (response) {
                return response.data;
            });
        },
        create: function (operation) {
            return $http.post("/api/operation/create", operation).then(function (response) {
                return response.data;
            });
        },
        createOperationAttach: function (operationId, file) {
            var fd = new FormData();
            fd.append('file', file);
            return $http.post("/api/operation/createOperationAttach/" + operationId, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                return response.data;
            });
        },
        remove: function (id) {
            return $http.delete("/api/operation/delete/" + id);
        },
        update: function (operation) {
            return $http.put("/api/operation/update", operation).then(function (response) {
                return response.data;
            });
        },
        count: function () {
            return $http.get("/api/operation/count").then(function (response) {
                return response.data;
            });
        },
        fetchTableData: function () {
            return $http.get("/api/operation/fetchTableData").then(function (response) {
                return response.data;
            });
        },
        filter: function (search) {
            return $http.get("/api/operation/filter?" + search).then(function (response) {
                return response.data;
            });
        },
        filterEnhanced: function (search) {
            return $http.get("/api/operation/filterEnhanced?" + search).then(function (response) {
                return response.data;
            });
        }
    };
}]);