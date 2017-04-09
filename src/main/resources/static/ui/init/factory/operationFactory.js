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
            }
        };
    }]);