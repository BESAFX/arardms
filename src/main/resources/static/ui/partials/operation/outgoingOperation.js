app.controller("outgoingOperationCtrl", ['CompanyService', 'BranchService', 'OperationService', 'OperationTypeService', 'ModalProvider', 'FileService', '$scope', '$rootScope', '$log', '$http', '$state', '$timeout', '$uibModal',
    function (CompanyService, BranchService, OperationService, OperationTypeService, ModalProvider, FileService, $scope, $rootScope, $log, $http, $state, $timeout, $uibModal) {

        $scope.selected = {};

        $scope.buffer = {};

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

        $scope.openFetchByBranch = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/operation/operationFilter.html',
                controller: 'operationFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                resolve: {
                    title: function () {
                        return 'عرض صادر الفروع';
                    },
                    fromType: function () {
                        return 'Branch';
                    }
                }
            });

            modalInstance.result.then(function (buffer) {
                $rootScope.showNotify("البريد الصادر", "جاري تحميل صادر الفروع، فضلاً انتظر قليلاً", "warning", "fa-envelope");
                var search = [];

                if (buffer.operationType) {
                    search.push('operationType=');
                    search.push(buffer.operationType.id);
                    search.push('&');
                }
                if (buffer.fromId) {
                    search.push('fromId=');
                    search.push(buffer.fromId.id);
                    search.push('&');
                }
                if (buffer.title) {
                    search.push('title=');
                    search.push(buffer.title);
                    search.push('&');
                }
                if (buffer.deliveryManFrom) {
                    search.push('deliveryManFrom=');
                    search.push(buffer.deliveryManFrom);
                    search.push('&');
                }
                if (buffer.deliveryManTo) {
                    search.push('deliveryManTo=');
                    search.push(buffer.deliveryManTo);
                    search.push('&');
                }
                if (buffer.deliveryAddress) {
                    search.push('deliveryAddress=');
                    search.push(buffer.deliveryAddress);
                    search.push('&');
                }
                if (buffer.deliveryWay) {
                    search.push('deliveryWay=');
                    search.push(buffer.deliveryWay);
                    search.push('&');
                }
                if (buffer.importance) {
                    search.push('importance=');
                    search.push(buffer.importance);
                    search.push('&');
                }
                if (buffer.codeFrom) {
                    search.push('codeFrom=');
                    search.push(buffer.codeFrom);
                    search.push('&');
                }
                if (buffer.codeTo) {
                    search.push('codeTo=');
                    search.push(buffer.codeTo);
                    search.push('&');
                }
                if (buffer.startDateTo) {
                    search.push('startDateTo=');
                    search.push(buffer.startDateTo.getTime());
                    search.push('&');
                }
                if (buffer.startDateFrom) {
                    search.push('startDateFrom=');
                    search.push(buffer.startDateFrom.getTime());
                    search.push('&');
                }

                search.push('structure=');
                search.push('Outgoing');

                OperationService.filterEnhanced(search.join("")).then(function (data) {
                    $scope.operations = data;
                    $scope.setSelected(data[0]);
                    $rootScope.showNotify("البريد الصادر", "تم تحميل صادر الفروع بنجاح", "success", "fa-envelope");
                });
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
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

            OperationTypeService.findAll().then(function (data) {
                $scope.operationTypes = data;
            });

            BranchService.fetchTableData().then(function (data) {
                $scope.branches = data;
            });

        }, 1500);

    }]);