app.factory("InsideOperationService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/insideOperation/findAll").then(function (response) {
                    return response.data;
                })
            },
            findOne: function (id) {
                return $http.get("/api/insideOperation/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (insideOperation) {
                return $http.post("/api/insideOperation/create", insideOperation).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/insideOperation/delete/" + id);
            },
            update: function (insideOperation) {
                return $http.put("/api/insideOperation/update", insideOperation).then(function (response) {
                    return response.data;
                });
            },
            count: function () {
                return $http.get("/api/insideOperation/count").then(function (response) {
                    return response.data;
                });
            },
            fetchTableData: function () {
                return $http.get("/api/insideOperation/fetchTableData").then(function (response) {
                    return response.data;
                });
            },
            fetchTableDataSummery: function () {
                return $http.get("/api/insideOperation/fetchTableDataSummery").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);