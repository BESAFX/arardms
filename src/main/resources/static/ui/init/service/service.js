app.service('ModalProvider', ['$uibModal', '$log', function ($uibModal, $log) {
    /**************************************************************
     *                                                            *
     * Company Model                                              *
     *                                                            *
     *************************************************************/
    this.openCompanyCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/company/companyCreateUpdate.html',
            controller: 'companyCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'اضافة شركة جديدة';
                },
                action: function () {
                    return 'create';
                },
                company: function () {
                    return undefined;
                }
            }
        });
    };

    this.openCompanyUpdateModel = function (company) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/company/companyCreateUpdate.html',
            controller: 'companyCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'تعديل بيانات شركة';
                },
                action: function () {
                    return 'update';
                },
                company: function () {
                    return company;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Region Model                                               *
     *                                                            *
     *************************************************************/
    this.openRegionCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/region/regionCreateUpdate.html',
            controller: 'regionCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'اضافة منطقة جديدة';
                },
                action: function () {
                    return 'create';
                },
                region: function () {
                    return undefined;
                }
            }
        });
    };

    this.openRegionUpdateModel = function (region) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/region/regionCreateUpdate.html',
            controller: 'regionCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات منطقة';
                },
                action: function () {
                    return 'update';
                },
                region: function () {
                    return region;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Branch Model                                               *
     *                                                            *
     *************************************************************/
    this.openBranchCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/branch/branchCreateUpdate.html',
            controller: 'branchCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'اضافة فرع جديد';
                },
                action: function () {
                    return 'create';
                },
                branch: function () {
                    return undefined;
                }
            }
        });
    };

    this.openBranchUpdateModel = function (branch) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/branch/branchCreateUpdate.html',
            controller: 'branchCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'تعديل بيانات فرع';
                },
                action: function () {
                    return 'update';
                },
                branch: function () {
                    return branch;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Department Model                                           *
     *                                                            *
     *************************************************************/
    this.openDepartmentCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/department/departmentCreateUpdate.html',
            controller: 'departmentCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'اضافة قسم جديد';
                },
                action: function () {
                    return 'create';
                },
                department: function () {
                    return undefined;
                }
            }
        });
    };

    this.openDepartmentUpdateModel = function (department) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/department/departmentCreateUpdate.html',
            controller: 'departmentCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات قسم';
                },
                action: function () {
                    return 'update';
                },
                department: function () {
                    return department;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Employee Model                                             *
     *                                                            *
     *************************************************************/
    this.openEmployeeCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/employee/employeeCreateUpdate.html',
            controller: 'employeeCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'اضافة موظف جديد';
                },
                action: function () {
                    return 'create';
                },
                employee: function () {
                    return undefined;
                }
            }
        });
    };

    this.openEmployeeUpdateModel = function (employee) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/employee/employeeCreateUpdate.html',
            controller: 'employeeCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات موظف';
                },
                action: function () {
                    return 'update';
                },
                employee: function () {
                    return employee;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Person Model                                               *
     *                                                            *
     *************************************************************/
    this.openPersonCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/person/personCreateUpdate.html',
            controller: 'personCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'اضافة مستخدم جديد';
                },
                action: function () {
                    return 'create';
                },
                person: function () {
                    return undefined;
                }
            }
        });
    };

    this.openPersonUpdateModel = function (person) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/person/personCreateUpdate.html',
            controller: 'personCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'تعديل بيانات مستخدم';
                },
                action: function () {
                    return 'update';
                },
                person: function () {
                    return person;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Team Model                                                 *
     *                                                            *
     *************************************************************/
    this.openTeamCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/team/teamCreateUpdate.html',
            controller: 'teamCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'اضافة مجموعة جديدة';
                },
                action: function () {
                    return 'create';
                },
                team: function () {
                    return undefined;
                }
            }
        });
    };

    this.openTeamUpdateModel = function (team) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/team/teamCreateUpdate.html',
            controller: 'teamCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                title: function () {
                    return 'تعديل بيانات مجموعة';
                },
                action: function () {
                    return 'update';
                },
                team: function () {
                    return team;
                }
            }
        });
    };

    this.openPersonsReportModel = function (persons) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/report/person/personsIn.html',
            controller: 'personsInCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                persons: function () {
                    return persons;
                }
            }
        });
    };

}]);

app.service('NotificationProvider', ['$http', function ($http) {

    this.notifyOne = function (code, title, message, type, receiver) {
        $http.post("/notifyOne?"
            + 'code=' + code
            + '&'
            + 'title=' + title
            + '&'
            + 'message=' + message
            + '&'
            + 'type=' + type
            + '&'
            + 'receiver=' + receiver);
    };
    this.notifyAll = function (code, title, message, type) {
        $http.post("/notifyAll?"
            + 'code=' + code
            + '&'
            + 'title=' + title
            + '&'
            + 'message=' + message
            + '&'
            + 'type=' + type
        );
    };
    this.notifyAllExceptMe = function (code, title, message, type) {
        $http.post("/notifyAllExceptMe?"
            + 'code=' + code
            + '&'
            + 'title=' + title
            + '&'
            + 'message=' + message
            + '&'
            + 'type=' + type
        );
    };

}]);

