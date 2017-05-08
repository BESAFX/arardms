app.controller('contactCreateUpdateCtrl', ['ContactService', 'FileUploader', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'contact',
    function (ContactService, FileUploader, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, contact) {

        if (contact) {
            $scope.contact = contact;
        } else {
            $scope.contact = {};
        }

        $scope.title = title;

        $scope.action = action;

        $scope.submit = function () {
            switch ($scope.action) {
                case 'create' :
                    ContactService.create($scope.contact).then(function (data) {
                        $scope.contact = {};
                        $scope.from.$setPristine();
                    });
                    break;
                case 'update' :
                    ContactService.update($scope.contact).then(function (data) {
                        $scope.contact = data;
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        var uploader = $scope.uploader = new FileUploader({
            url: 'uploadContactLogo'
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
            $scope.contact.photo = response;
        };

    }]);