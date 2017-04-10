app.controller("outgoingOperationCtrl", ['CompanyService', 'BranchService', 'OperationService', 'ModalProvider', 'FileService', '$scope', '$rootScope', '$log', '$http', '$state', '$timeout',
    function (CompanyService ,BranchService, OperationService, ModalProvider, FileService, $scope, $rootScope, $log, $http, $state, $timeout) {

        $scope.selected = {};

        $scope.operations = [];

        $scope.setSelected = function (object) {
            if (object) {
                angular.forEach($scope.operations, function (operation) {
                    if (object.obj0 == operation.obj0) {
                        $scope.selected = operation;
                        return operation.isSelected = true;
                    } else {
                        return operation.isSelected = false;
                    }
                });
            }
        };

        $scope.filter = function () {
            $rootScope.showNotify("البريد الصادر", "جاري تحميل البريد الصادر، فضلاً انتظر قليلاً", "warning", "fa-envelope");
            var search = [];

            search.push('structure=');
            search.push('Outgoing');
            search.push('&');

            OperationService.filterEnhanced(search.join("")).then(function (data) {
                $scope.operations = data;
                $scope.setSelected(data[0]);
                $rootScope.showNotify("البريد الصادر", "تم التحميل بنجاح، يمكنك متابعة عملك الآن", "success", "fa-envelope");
            });
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