app.controller("contactCtrl", ['ContactService', 'ModalProvider', '$scope', '$rootScope', '$state', '$timeout',
    function (ContactService, ModalProvider, $scope, $rootScope, $state, $timeout) {

        $scope.selected = {};

        $scope.fetchTableData = function () {
            ContactService.findAll().then(function (data) {
                $scope.contacts = data;
                $scope.setSelected(data[0]);
            });
        };

        $scope.setSelected = function (object) {
            if (object) {
                angular.forEach($scope.contacts, function (contact) {
                    if (object.id == contact.id) {
                        $scope.selected = contact;
                        return contact.isSelected = true;
                    } else {
                        return contact.isSelected = false;
                    }
                });
            }
        };

        $scope.reload = function () {
            $state.reload();
        };

        $scope.openCreateModel = function () {
            ModalProvider.openContactCreateModel();
        };

        $scope.openUpdateModel = function (contact) {
            if (contact) {
                ModalProvider.openContactUpdateModel(contact);
                return;
            }
            ModalProvider.openContactUpdateModel($scope.selected);
        };

        $scope.delete = function (contact) {
            if (contact) {
                ContactService.remove(contact);
                return;
            }
            ContactService.remove($scope.selected);
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
                    $scope.openUpdateModel($itemScope.contact);
                }
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-minus-square-o fa-lg"></span> حذف</div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.delete($itemScope.contact);
                }
            }
        ];

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 1500);

    }]);