app.controller('incomingOperationCreateUpdateCtrl',
    ['OperationTypeService', 'CompanyService', 'RegionService', 'BranchService', 'DepartmentService', 'PersonService', 'OperationService', 'FileUploader', 'FileService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'idType', 'operation',
        function (OperationTypeService, CompanyService, RegionService, BranchService, DepartmentService, PersonService, OperationService, FileUploader, FileService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, idType, operation) {

            $timeout(function () {
                CompanyService.findAll().then(function (data) {
                    $scope.companies = data;
                    RegionService.findAll().then(function (data) {
                        $scope.regions = data;
                        BranchService.findAll().then(function (data) {
                            $scope.branches = data;
                            DepartmentService.findAll().then(function (data) {
                                $scope.departments = data;
                                PersonService.findPersons().then(function (data) {
                                    $scope.persons = data;
                                    PersonService.findContacts().then(function (data) {
                                        $scope.contacts = data;
                                    });
                                });
                            })
                        })
                    });
                });

                OperationTypeService.findAll().then(function (data) {
                    $scope.operationTypes = data;
                });

            }, 1500);

            if (operation) {
                $scope.operation = operation;
            } else {
                $scope.operation = {};
            }

            $scope.title = title;

            $scope.action = action;

            $scope.idType = idType;

            switch (idType){
                case 'Branch':
                    BranchService.fetchTableData().then(function (data) {
                        $scope.myBranches = data;
                    });
                    break;
            }

            $scope.submit = function () {
                $rootScope.showNotify("المعاملات", "جاري القيام بالعملية، فضلاً انتظر قليلاً", "warning", "fa-exchange");
                switch ($scope.action) {
                    case 'create' :
                        OperationService.create($scope.operation).then(function (data) {
                            $scope.operation = {};
                            $scope.form.$setPristine();
                            $rootScope.showNotify("المعاملات", "تم القيام بالعملية بنجاح، يمكنك اضافة معاملة آخرى الآن", "success", "fa-exchange");
                        });
                        break;
                    case 'update' :
                        OperationService.update($scope.operation).then(function (data) {
                            $scope.operation = data;
                            $rootScope.showNotify("المعاملات", "تم القيام بالعملية بنجاح، يمكنك متابعة عملك الآن", "success", "fa-exchange");
                        });
                        break;
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

            var uploader = $scope.uploader = new FileUploader({
                url: 'uploadFile'
            });

            uploader.filters.push({
                name: 'syncFilter',
                fn: function (item, options) {
                    return this.queue.length < 10;
                }
            });


            uploader.filters.push({
                name: 'asyncFilter',
                fn: function (item, options, deferred) {
                    setTimeout(deferred.resolve, 1e3);
                }
            });

            uploader.onAfterAddingFile = function (fileItem) {
                uploader.uploadAll();
            };

        }]);