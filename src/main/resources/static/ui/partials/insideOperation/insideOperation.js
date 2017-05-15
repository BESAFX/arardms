app.controller("insideOperationCtrl", ['InsideOperationService', 'ModalProvider', '$scope', '$rootScope', '$log', '$http', '$state', '$timeout',
    function (InsideOperationService, ModalProvider, $scope, $rootScope, $log, $http, $state, $timeout) {

        $scope.selected = {};

        $scope.fetchTableData = function () {
            InsideOperationService.fetchTableData().then(function (data) {
                $scope.insideOperations = data;
                $scope.setSelected(data[0]);
            })
        };

        $scope.setSelected = function (object) {
            if (object) {
                angular.forEach($scope.insideOperations, function (insideOperation) {
                    if (object.id == insideOperation.id) {
                        $scope.selected = insideOperation;
                        return insideOperation.isSelected = true;
                    } else {
                        return insideOperation.isSelected = false;
                    }
                });
            }
        };

        $scope.reload = function () {
            $state.reload();
        };

        $scope.openCreateModel = function (direction) {
            ModalProvider.openInsideOperationCreateModel(direction);
        };

        $scope.openUpdateModel = function (insideOperation) {
            if (insideOperation) {
                ModalProvider.openInsideOperationUpdateModel(insideOperation);
                return;
            }
            ModalProvider.openInsideOperationUpdateModel($scope.selected);
        };

        $scope.delete = function (insideOperation) {
            if (insideOperation) {
                InsideOperationService.remove(insideOperation);
                return;
            }
            InsideOperationService.remove($scope.selected);
        };

        $scope.rowMenu = [
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-plus-square-o fa-lg"></span> انشاء معاملة صادرة</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.openCreateModel('Outgoing');
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-plus-square-o fa-lg"></span> انشاء معاملة واردة</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.openCreateModel('Incoming');
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-edit fa-lg"></span> تعديل</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.openUpdateModel($itemScope.insideOperation);
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-minus-square-o fa-lg"></span> حذف</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.delete($itemScope.insideOperation);
                }
            }
        ];

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 1500);

    }]);