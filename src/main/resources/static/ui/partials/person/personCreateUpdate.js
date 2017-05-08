app.controller('personCreateUpdateCtrl', ['TeamService', 'PersonService', 'BranchService', 'FileUploader', 'NotificationProvider', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'person',
    function (TeamService, PersonService, BranchService, FileUploader, NotificationProvider, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, person) {

        $timeout(function () {
            TeamService.findAllSummery().then(function (data) {
                $scope.teams = data;
            });
            BranchService.fetchTableDataSummery().then(function (data) {
                $scope.branches = data;
            });
        }, 2000);

        if (person) {
            $scope.person = person;
        } else {
            $scope.person = {};
        }

        $scope.title = title;

        $scope.action = action;

        $scope.submit = function () {
            switch ($scope.action) {
                case 'create' :
                    PersonService.create($scope.person).then(function (data) {
                        $scope.person = {};
                        $scope.from.$setPristine();
                    });
                    break;
                case 'update' :
                    PersonService.update($scope.person).then(function (data) {
                        $scope.person = data;
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        var uploader = $scope.uploader = new FileUploader({
            url: 'uploadUserPhoto'
        });

        uploader.filters.push({
            name: 'syncFilter',
            fn: function (item, options) {
                console.log('syncFilter');
                return this.queue.length < 10;
            }
        });

        uploader.filters.push({
            name: 'asyncFilter',
            fn: function (item, options, deferred) {
                console.log('asyncFilter');
                setTimeout(deferred.resolve, 1e3);
            }
        });

        uploader.onAfterAddingFile = function (fileItem) {
            uploader.uploadAll();
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.person.photo = response;
        };

    }]);