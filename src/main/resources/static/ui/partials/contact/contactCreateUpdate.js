app.controller('contactCreateUpdateCtrl', ['PersonService', 'FileUploader', 'NotificationProvider', 'FileService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'contact',
    function (PersonService, FileUploader, NotificationProvider, FileService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, contact) {

        if (contact) {
            $scope.contact = contact;
        } else {
            $scope.contact = {};
        }

        $scope.title = title;

        $scope.action = action;

        $scope.submit = function () {
            $rootScope.showNotify("جهات الاتصال", "جاري القيام بالعملية، فضلاً انتظر قليلاً", "warning", "fa-user");
            switch ($scope.action) {
                case 'create' :
                    console.info($scope.contact);
                    PersonService.createContact($scope.contact).then(function (data) {
                        $scope.contact = {};
                        $scope.from.$setPristine();
                        $rootScope.showNotify("جهات الاتصال", "تم القيام بالعملية بنجاح، يمكنك اضافة حساب مستخدم آخر الآن", "success", "fa-user");
                    });
                    break;
                case 'update' :
                    PersonService.updateContact($scope.contact).then(function (data) {
                        $scope.contact = data;
                        $rootScope.showNotify("جهات الاتصال", "تم القيام بالعملية بنجاح، يمكنك متابعة عملك الآن", "success", "fa-user");
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        var uploader = $scope.uploader = new FileUploader({
            url: 'uploadContactLogo'
        });

        uploader.filters.push({
            name: 'syncFilter',
            fn: function (item, options) {
                console.log('syncFilter');
                return this.queue.length < 10;
            }
        });

        uploader.filters.push({
            name: 'asyncFilter',
            fn: function (item, options, deferred) {
                console.log('asyncFilter');
                setTimeout(deferred.resolve, 1e3);
            }
        });

        uploader.onAfterAddingFile = function (fileItem) {
            uploader.uploadAll();
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.contact.photo = response;
        };

    }]);