var app = angular.module('Application',
    [
        'ui.router',
        'ngAnimate',
        'toggle-switch',
        'jcs-autoValidate',
        'fm',
        'ngSanitize',
        'counter',
        'FBAngular',
        'ui.bootstrap',
        'angularCSS',
        'smart-table',
        'lrDragNDrop',
        'nya.bootstrap.select',
        'localytics.directives',
        'angularFileUpload',
        'ngLoadingSpinner',
        'ngStomp',
        'luegg.directives',
        'monospaced.elastic',
        'pageslide-directive',
        'ui.bootstrap.contextMenu',
        'kdate',
        'ui.sortable',
        'timer'
    ]);

app.factory('errorInterceptor', ['$q', '$rootScope', '$location', '$log',
    function ($q, $rootScope, $location, $log) {
        return {
            request: function (config) {
                return config || $q.when(config);
            },
            requestError: function (request) {
                return $q.reject(request);
            },
            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (response) {
                if (response && response.status === 404) {
                }
                if (response && response.status >= 500) {
                    $rootScope.showTechnicalNotify("الدعم الفني", response.data.message, "error", "fa-ban");
                }
                return $q.reject(response);
            }
        };
    }]);

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$cssProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider, $cssProvider, $httpProvider) {

        $urlRouterProvider.otherwise("/menu");

        $locationProvider.html5Mode(true);

        $httpProvider.interceptors.push('errorInterceptor');

        angular.extend($cssProvider.defaults, {
            container: 'head',
            method: 'append',
            persist: true,
            preload: true,
            bustCache: true
        });

        /**************************************************************
         *                                                            *
         * Home State                                                 *
         *                                                            *
         *************************************************************/
        $stateProvider.state("home", {
            url: "/home",
            views: {
                '': {
                    templateUrl: "/ui/partials/home/home.html",
                    controller: "homeCtrl"
                }
            }
        });

        /**************************************************************
         *                                                            *
         * Menu State                                                 *
         *                                                            *
         *************************************************************/
        $stateProvider.state("menu", {
            url: "/menu",
            templateUrl: "/ui/partials/menu/menu.html",
            controller: "menuCtrl"
        });

        /**************************************************************
         *                                                            *
         * Company State                                              *
         *                                                            *
         *************************************************************/
        $stateProvider.state("company", {
            url: "/company",
            templateUrl: "/ui/partials/company/company.html",
            controller: "companyCtrl"
        });

        /**************************************************************
         *                                                            *
         * Branch State                                               *
         *                                                            *
         *************************************************************/
        $stateProvider.state("branch", {
            url: "/branch",
            templateUrl: "/ui/partials/branch/branch.html",
            controller: "branchCtrl"
        });

        /**************************************************************
         *                                                            *
         * Employee State                                             *
         *                                                            *
         *************************************************************/
        $stateProvider.state("employee", {
            url: "/employee",
            templateUrl: "/ui/partials/employee/employee.html",
            controller: "employeeCtrl"
        });

        /**************************************************************
         *                                                            *
         * Person State                                               *
         *                                                            *
         *************************************************************/
        $stateProvider.state("person", {
            url: "/person",
            templateUrl: "/ui/partials/person/person.html",
            controller: "personCtrl"
        });

        /**************************************************************
         *                                                            *
         * Contact State                                              *
         *                                                            *
         *************************************************************/
        $stateProvider.state("contact", {
            url: "/contact",
            templateUrl: "/ui/partials/contact/contact.html",
            controller: "contactCtrl"
        });

        /**************************************************************
         *                                                            *
         * OperationType State                                        *
         *                                                            *
         *************************************************************/
        $stateProvider.state("operationType", {
            url: "/operationType",
            templateUrl: "/ui/partials/operationType/operationType.html",
            controller: "operationTypeCtrl"
        });

        /**************************************************************
         *                                                            *
         * InsideOperation State                                      *
         *                                                            *
         *************************************************************/
        $stateProvider.state("insideOperation", {
            url: "/insideOperation",
            templateUrl: "/ui/partials/insideOperation/insideOperation.html",
            controller: "insideOperationCtrl"
        });

        /**************************************************************
         *                                                            *
         * OutsideOperation State                                     *
         *                                                            *
         *************************************************************/
        $stateProvider.state("outsideOperation", {
            url: "/outsideOperation",
            templateUrl: "/ui/partials/outsideOperation/outsideOperation.html",
            controller: "outsideOperationCtrl"
        });

        /**************************************************************
         *                                                            *
         * Team State                                                 *
         *                                                            *
         *************************************************************/
        $stateProvider.state("team", {
            url: "/team",
            templateUrl: "/ui/partials/team/team.html",
            controller: "teamCtrl"
        });

        /**************************************************************
         *                                                            *
         * Help State                                                 *
         *                                                            *
         *************************************************************/
        $stateProvider.state("help", {
            url: "/help",
            templateUrl: "/ui/partials/help/help.html",
            controller: "helpCtrl"
        });

        /**************************************************************
         *                                                            *
         * Profile State                                              *
         *                                                            *
         *************************************************************/
        $stateProvider.state("profile", {
            url: "/profile",
            templateUrl: "/ui/partials/profile/profile.html",
            controller: "profileCtrl"
        });
    }
]);


