app.factory("OutsideOperationService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/outsideOperation/findAll").then(function (response) {
                    return response.data;
                })
            },
            findOne: function (id) {
                return $http.get("/api/outsideOperation/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (outsideOperation) {
                return $http.post("/api/outsideOperation/create", outsideOperation).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/outsideOperation/delete/" + id);
            },
            update: function (outsideOperation) {
                return $http.put("/api/outsideOperation/update", outsideOperation).then(function (response) {
                    return response.data;
                });
            },
            count: function () {
                return $http.get("/api/outsideOperation/count").then(function (response) {
                    return response.data;
                });
            },
            fetchTableData: function () {
                return $http.get("/api/outsideOperation/fetchTableData").then(function (response) {
                    return response.data;
                });
            },
            fetchTableDataSummery: function () {
                return $http.get("/api/outsideOperation/fetchTableDataSummery").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);