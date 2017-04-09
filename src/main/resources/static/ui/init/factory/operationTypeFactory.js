app.factory("OperationTypeService", ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/operationType/findAll").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/operationType/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (operationType) {
                return $http.post("/api/operationType/create", operationType).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/operationType/delete/" + id);
            },
            update: function (operationType) {
                return $http.put("/api/operationType/update", operationType).then(function (response) {
                    return response.data;
                });
            },
            count: function () {
                return $http.get("/api/operationType/count").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);