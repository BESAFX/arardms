app.factory("PersonService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/person/findAll").then(function (response) {
                    return response.data;
                });
            },
            findPersons: function () {
                return $http.get("/api/person/findPersons").then(function (response) {
                    return response.data;
                });
            },
            findContacts: function () {
                return $http.get("/api/person/findContacts").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/person/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (person) {
                return $http.post("/api/person/create", person).then(function (response) {
                    return response.data;
                });
            },
            createContact: function (contact) {
                return $http.post("/api/person/createContact", contact).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/person/delete/" + id);
            },
            update: function (person) {
                return $http.put("/api/person/update", person).then(function (response) {
                    return response.data;
                });
            },
            updateContact: function (person) {
                return $http.put("/api/person/updateContact", person).then(function (response) {
                    return response.data;
                });
            },
            findActivePerson: function () {
                return $http.get("/api/person/findActivePerson").then(function (response) {
                    return response.data;
                });
            },
            findActivePersonSummery: function () {
                return $http.get("/api/person/findActivePersonSummery").then(function (response) {
                    return response.data;
                });
            },
            findActivePersonManager: function () {
                return $http.get("/api/person/findActivePersonManager").then(function (response) {
                    return response.data;
                });
            },
            findActivePersonManagerSummery: function () {
                return $http.get("/api/person/findActivePersonManagerSummery").then(function (response) {
                    return response.data;
                });
            },
            findAuthorities: function () {
                return $http.get("/api/person/findAuthorities").then(function (response) {
                    return response.data;
                });
            },
            findPersonUnderMe: function () {
                return $http.get("/api/person/findPersonUnderMe").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);