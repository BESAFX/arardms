app.controller("outgoingOperationCtrl", ['OperationService', 'ModalProvider', 'FileService', '$scope', '$rootScope', '$log', '$http', '$state', '$timeout',
    function (OperationService, ModalProvider, FileService, $scope, $rootScope, $log, $http, $state, $timeout) {

        $scope.selected = {};

        $scope.setSelected = function (object) {
            if (object) {
                angular.forEach($scope.operations, function (operation) {
                    if (object.id == operation.id) {
                        $scope.selected = operation;
                        return operation.isSelected = true;
                    } else {
                        return operation.isSelected = false;
                    }
                });
            }
        };

        $scope.reload = function () {
            $state.reload();
        };

        $scope.openCreateFromBranchModel = function () {
            ModalProvider.openOutgoingOperationFromBranchCreateModel();
        };

        $scope.openUpdateModel = function (operation) {
            if (operation) {
                ModalProvider.openOutgoingOperationUpdateModel(operation);
                return;
            }
            ModalProvider.openOutgoingOperationUpdateModel($scope.selected);
        };

        $scope.delete = function (operation) {
            if (operation) {
                OperationService.remove(operation);
                return;
            }
            OperationService.remove($scope.selected);
        };

        $scope.rowMenu = [
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-plus-square-o fa-lg"></span> اضافة</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.openCreateModel();
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-edit fa-lg"></span> تعديل</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.openUpdateModel($itemScope.operation);
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-minus-square-o fa-lg"></span> حذف</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.delete($itemScope.operation);
                }
            }
        ];

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 1500);

    }]);