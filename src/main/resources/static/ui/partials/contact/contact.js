app.controller("contactCtrl", ['PersonService', 'ModalProvider', 'FileService', '$scope', '$rootScope', '$state', '$timeout',
    function (PersonService, ModalProvider, FileService, $scope, $rootScope, $state, $timeout) {

        $scope.selected = {};

        $scope.fetchTableData = function () {
            $rootScope.showNotify("جهات الاتصال", "فضلاً انتظر قليلاً حتى الانتهاء من تحميل جهات الاتصال", "warning", "fa-user");
            PersonService.findContacts().then(function (data) {
                $scope.contacts = data;
                $scope.setSelected(data[0]);
                $rootScope.showNotify("جهات الاتصال", "تم الانتهاء من تحميل البيانات المطلوبة بنجاح، يمكنك متابعة عملك الآن", "success", "fa-user");
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

        $scope.openReportContactsModel = function () {
            ModalProvider.openContactsReportModel($scope.contacts);
        };

        $scope.delete = function (contact) {
            if (contact) {
                PersonService.remove(contact);
                return;
            }
            PersonService.remove($scope.selected);
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
            },
            {
                html: '<div style="cursor: pointer;padding: 10px"><span class="fa fa-print fa-lg"></span> طباعة تقرير مختصر </div>',
                enabled: function () {
                    return true
                },
                click: function ($itemScope, $event, value) {
                    $scope.openReportContactsModel();
                }
            }
        ];

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 1500);

    }]);