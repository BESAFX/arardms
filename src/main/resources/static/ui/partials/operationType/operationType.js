app.controller("operationTypeCtrl", ['OperationTypeService', 'ModalProvider', '$scope', '$rootScope', '$state', '$timeout',
    function (OperationTypeService, ModalProvider, $scope, $rootScope, $state, $timeout) {

        $scope.selected = {};

        $scope.fetchTableData = function () {
            OperationTypeService.findAll().then(function (data) {
                $scope.operationTypes = data;
                $scope.setSelected(data[0]);
            });
        };

        $scope.setSelected = function (object) {
            if (object) {
                angular.forEach($scope.operationTypes, function (operationType) {
                    if (object.id == operationType.id) {
                        $scope.selected = operationType;
                        return operationType.isSelected = true;
                    } else {
                        return operationType.isSelected = false;
                    }
                });
            }
        };

        $scope.reload = function () {
            $state.reload();
        };

        $scope.openCreateModel = function () {
            ModalProvider.openOperationTypeCreateModel();
        };

        $scope.openUpdateModel = function (operationType) {
            if (operationType) {
                ModalProvider.openOperationTypeUpdateModel(operationType);
                return;
            }
            ModalProvider.openOperationTypeUpdateModel($scope.selected);
        };

        $scope.delete = function (operationType) {
            if (operationType) {
                OperationTypeService.remove(operationType);
                return;
            }
            OperationTypeService.remove($scope.selected);
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
                    $scope.openUpdateModel($itemScope.operationType);
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-minus-square-o fa-lg"></span> حذف</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.delete($itemScope.operationType);
                }
            }
        ];

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 1500);

    }]);