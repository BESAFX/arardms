app.controller('operationCreateUpdateCtrl', ['OperationService', 'FileUploader', 'FileService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'operation',
    function (OperationService, FileUploader, FileService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, operation) {

        if (operation) {
            $scope.operation = operation;
        } else {
            $scope.operation = {};
        }

        $scope.title = title;

        $scope.action = action;

        $scope.submit = function () {
            $rootScope.showNotify("المعاملات", "جاري القيام بالعملية، فضلاً انتظر قليلاً", "warning", "fa-exchange");
            switch ($scope.action) {
                case 'create' :
                    OperationService.create($scope.operation).then(function (data) {
                        $scope.operation = {};
                        $scope.form.$setPristine();
                        $rootScope.showNotify("المعاملات", "تم القيام بالعملية بنجاح، يمكنك اضافة معاملة آخرى الآن", "success", "fa-exchange");
                    });
                    break;
                case 'update' :
                    OperationService.update($scope.operation).then(function (data) {
                        $scope.operation = data;
                        $rootScope.showNotify("المعاملات", "تم القيام بالعملية بنجاح، يمكنك متابعة عملك الآن", "success", "fa-exchange");
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        var uploader = $scope.uploader = new FileUploader({
            url: 'uploadFile'
        });

        uploader.filters.push({
            name: 'syncFilter',
            fn: function (item, options) {
                return this.queue.length < 10;
            }
        });


        uploader.filters.push({
            name: 'asyncFilter',
            fn: function (item, options, deferred) {
                setTimeout(deferred.resolve, 1e3);
            }
        });

        uploader.onAfterAddingFile = function (fileItem) {
            uploader.uploadAll();
        };

    }]);