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
            switch ($scope.action) {
                case 'create' :
                    console.info($scope.operationType);
                    OperationTypeService.create($scope.operationType).then(function (data) {
                        $scope.operationType = {};
                        $scope.from.$setPristine();
                    });
                    break;
                case 'update' :
                    OperationTypeService.update($scope.operationType).then(function (data) {
                        $scope.operationType = data;
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);