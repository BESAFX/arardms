app.factory("ContactService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/contact/findAll").then(function (response) {
                    return response.data;
                });
            },
            findAllSummery: function () {
                return $http.get("/api/contact/findAllSummery").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/contact/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (contact) {
                return $http.post("/api/contact/create", contact).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/contact/delete/" + id);
            },
            update: function (contact) {
                return $http.put("/api/contact/update", contact).then(function (response) {
                    return response.data;
                });
            }
        };
    }]);