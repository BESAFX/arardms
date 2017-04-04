angular.module("toggle-switch", ["ng"]).directive("toggleSwitch", ["$compile", function ($compile) {
    return {
        restrict: "EA",
        replace: !0,
        require: "ngModel",
        scope: {isDisabled: "=", onLabel: "@", offLabel: "@", knobLabel: "@", html: "=", onChange: "&"},
        template: '<div class="ats-switch" ng-click="toggle()" ng-keypress="onKeyPress($event)" ng-class="{ \'disabled\': isDisabled }" role="switch" aria-checked="{{!!model}}"><div class="switch-animate" ng-class="{\'switch-off\': !model, \'switch-on\': model}"><span class="switch-left"></span><span class="knob"></span><span class="switch-right"></span></div></div>',
        compile: function (element, attrs) {
            return angular.isUndefined(attrs.onLabel) && (attrs.onLabel = "On"), angular.isUndefined(attrs.offLabel) && (attrs.offLabel = "Off"), angular.isUndefined(attrs.knobLabel) && (attrs.knobLabel = " "), angular.isUndefined(attrs.isDisabled) && (attrs.isDisabled = !1), angular.isUndefined(attrs.html) && (attrs.html = !1), angular.isUndefined(attrs.tabindex) && (attrs.tabindex = 0), function (scope, iElement, iAttrs, ngModel) {
                iElement.attr("tabindex", attrs.tabindex), scope.toggle = function () {
                    scope.isDisabled || (scope.model = !scope.model, ngModel.$setViewValue(scope.model)), scope.onChange()
                };
                var spaceCharCode = 32;
                scope.onKeyPress = function ($event) {
                    $event.charCode != spaceCharCode || $event.altKey || $event.ctrlKey || $event.metaKey || scope.toggle()
                }, ngModel.$formatters.push(function (modelValue) {
                    return modelValue
                }), ngModel.$parsers.push(function (viewValue) {
                    return viewValue
                }), ngModel.$viewChangeListeners.push(function () {
                    scope.$eval(attrs.ngChange)
                }), ngModel.$render = function () {
                    scope.model = ngModel.$viewValue
                };
                var bindSpan = function (span, html) {
                    span = angular.element(span);
                    var bindAttributeName = html === !0 ? "ng-bind-html" : "ng-bind";
                    span.removeAttr("ng-bind-html"), span.removeAttr("ng-bind"), angular.element(span).hasClass("switch-left") && span.attr(bindAttributeName, "onLabel"), span.hasClass("knob") && span.attr(bindAttributeName, "knobLabel"), span.hasClass("switch-right") && span.attr(bindAttributeName, "offLabel"), $compile(span)(scope, function (cloned, scope) {
                        span.replaceWith(cloned)
                    })
                }, bindSwitch = function (iElement, html) {
                    angular.forEach(iElement[0].children[0].children, function (span, index) {
                        bindSpan(span, html)
                    })
                };
                scope.$watch("html", function (newValue) {
                    bindSwitch(iElement, newValue)
                })
            }
        }
    }
}]);