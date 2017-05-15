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
            size: 'lg',
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
            size: 'lg',
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

    this.openCompanyHeavyWorkModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/company/companyHeavyWork.html',
            controller: 'companyHeavyWorkCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'انشاء دفعة من الشركات';
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
            size: 'lg',
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
            size: 'lg',
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

    this.openBranchHeavyWorkModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/branch/branchHeavyWork.html',
            controller: 'branchHeavyWorkCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'انشاء دفعة من الفروع';
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
     * OperationType Model                                        *
     *                                                            *
     *************************************************************/
    this.openOperationTypeCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/operationType/operationTypeCreateUpdate.html',
            controller: 'operationTypeCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'اضافة أنواع معاملات جديدة';
                },
                action: function () {
                    return 'create';
                },
                operationType: function () {
                    return undefined;
                }
            }
        });
    };

    this.openOperationTypeUpdateModel = function (operationType) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/operationType/operationTypeCreateUpdate.html',
            controller: 'operationTypeCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات أنواع المعاملات';
                },
                action: function () {
                    return 'update';
                },
                operationType: function () {
                    return operationType;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * InsideOperation Model                                      *
     *                                                            *
     *************************************************************/
    this.openInsideOperationCreateModel = function (direction) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/insideOperation/insideOperationCreateUpdate.html',
            controller: 'insideOperationCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                title: function () {
                   if(direction === 'Outgoing'){
                       return 'انشاء معاملة صادرة جديدة';
                   }else{
                       return 'انشاء معاملة واردة جديدة';
                   }
                },
                action: function () {
                    return 'create';
                },
                direction: function () {
                    return direction;
                },
                insideOperation: function () {
                    return undefined;
                }
            }
        });
    };

    this.openInsideOperationUpdateModel = function (insideOperation) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/insideOperation/insideOperationCreateUpdate.html',
            controller: 'insideOperationCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            size : 'lg',
            resolve: {
                title: function () {
                    if(insideOperation.direction === 'Outgoing'){
                        return 'تعديل بيانات المعاملة الصادرة';
                    }else{
                        return 'تعديل بيانات المعاملة الواردة';
                    }
                },
                action: function () {
                    return 'update';
                },
                insideOperation: function () {
                    return insideOperation;
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
            size: 'lg',
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
            size: 'lg',
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

    this.openPersonHeavyWorkModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/person/personHeavyWork.html',
            controller: 'personHeavyWorkCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'انشاء دفعة من المستخدمين';
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Contact Model                                               *
     *                                                            *
     *************************************************************/
    this.openContactCreateModel = function () {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contact/contactCreateUpdate.html',
            controller: 'contactCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                title: function () {
                    return 'اضافة جهة اتصال جديدة';
                },
                action: function () {
                    return 'create';
                },
                contact: function () {
                    return undefined;
                }
            }
        });
    };

    this.openContactUpdateModel = function (contact) {
        $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contact/contactCreateUpdate.html',
            controller: 'contactCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                title: function () {
                    return 'تعديل بيانات جهة اتصال';
                },
                action: function () {
                    return 'update';
                },
                contact: function () {
                    return contact;
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
            size: 'lg',
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
            size: 'lg',
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

