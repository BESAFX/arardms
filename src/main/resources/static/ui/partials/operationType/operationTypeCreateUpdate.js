app.controller('operationTypeCreateUpdateCtrl', ['OperationTypeService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'operationType',
    function (OperationTypeService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, operationType) {

        if (operationType) {
            $scope.operationType = operationType;
        } else {
            $scope.operationType = {};
        }

        $scope.title = title;

        $scope.action = action;

        $scope.submit = function () {
            $rootScope.showNotify("أنواع المعاملات", "جاري القيام بالعملية، فضلاً انتظر قليلاً", "warning", "fa-user");
            switch ($scope.action) {
                case 'create' :
                    console.info($scope.operationType);
                    OperationTypeService.create($scope.operationType).then(function (data) {
                        $scope.operationType = {};
                        $scope.from.$setPristine();
                        $rootScope.showNotify("أنواع المعاملات", "تم القيام بالعملية بنجاح، يمكنك اضافة حساب مستخدم آخر الآن", "success", "fa-user");
                    });
                    break;
                case 'update' :
                    OperationTypeService.update($scope.operationType).then(function (data) {
                        $scope.operationType = data;
                        $rootScope.showNotify("أنواع المعاملات", "تم القيام بالعملية بنجاح، يمكنك متابعة عملك الآن", "success", "fa-user");
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);