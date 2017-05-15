app.controller('insideOperationCreateUpdateCtrl', ['InsideOperationService', 'OperationTypeService', 'BranchService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'direction', 'insideOperation',
    function (InsideOperationService, OperationTypeService, BranchService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, direction, insideOperation) {

        $timeout(function () {
            OperationTypeService.findAll().then(function (data) {
                $scope.operationTypes = data;
            });
            if (direction === 'Outgoing') {
                BranchService.fetchTableDataSummery().then(function (data) {
                    $scope.branchesFrom = data;
                });
                BranchService.findAllSummery().then(function (data) {
                    $scope.branchesTo = data;
                });
            } else {
                BranchService.findAllSummery().then(function (data) {
                    $scope.branchesFrom = data;
                });
                BranchService.fetchTableDataSummery().then(function (data) {
                    $scope.branchesTo = data;
                });
            }
        }, 1500);

        if (insideOperation) {
            $scope.insideOperation = insideOperation;
        } else {
            $scope.insideOperation = {};
        }

        $scope.title = title;

        $scope.action = action;

        $scope.direction = direction;

        $scope.insideOperation.direction = direction;

        $scope.submit = function () {
            switch ($scope.action) {
                case 'create' :
                    InsideOperationService.create($scope.insideOperation).then(function (data) {
                        $scope.insideOperation = {};
                        $scope.form.$setPristine();
                    });
                    break;
                case 'update' :
                    InsideOperationService.update($scope.insideOperation).then(function (data) {
                        $scope.insideOperation = data;
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);