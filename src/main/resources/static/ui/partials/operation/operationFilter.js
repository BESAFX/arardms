app.controller('operationFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'fromType',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance, title, fromType) {

        $scope.modalTitle = title;
        $scope.buffer.fromType = fromType;

        $scope.clear = function () {
            $scope.buffer = {};
            $scope.buffer.fromType = fromType;
        };

        $scope.submit = function () {
            $uibModalInstance.close($scope.buffer);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);