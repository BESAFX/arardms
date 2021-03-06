/**
 * angular-chosen-localytics - Angular Chosen directive is an AngularJS Directive that brings the Chosen jQuery in a Angular way
 * @version v1.3.0
 * @link http://github.com/leocaseiro/angular-chosen
 * @license MIT
 */
(function () {
    var e = [].indexOf || function (e) {
            for (var t = 0, n = this.length; n > t; t++)if (t in this && this[t] === e)return t;
            return -1
        };
    angular.module("localytics.directives", []), angular.module("localytics.directives").directive("chosen", ["$timeout", function (t) {
        var n, r, i, s;
        return r = /^\s*([\s\S]+?)(?:\s+as\s+([\s\S]+?))?(?:\s+group\s+by\s+([\s\S]+?))?\s+for\s+(?:([\$\w][\$\w]*)|(?:\(\s*([\$\w][\$\w]*)\s*,\s*([\$\w][\$\w]*)\s*\)))\s+in\s+([\s\S]+?)(?:\s+track\s+by\s+([\s\S]+?))?$/, n = ["persistentCreateOption", "createOptionText", "createOption", "skipNoResults", "noResultsText", "allowSingleDeselect", "disableSearchThreshold", "disableSearch", "enableSplitWordSearch", "inheritSelectClasses", "maxSelectedOptions", "placeholderTextMultiple", "placeholderTextSingle", "searchContains", "singleBackstrokeDelete", "displayDisabledOptions", "displaySelectedOptions", "width", "includeGroupLabelInSelected", "maxShownResults"], s = function (e) {
            return e.replace(/[A-Z]/g, function (e) {
                return "_" + e.toLowerCase()
            })
        }, i = function (e) {
            var t;
            if (angular.isArray(e))return 0 === e.length;
            if (angular.isObject(e))for (t in e)if (e.hasOwnProperty(t))return !1;
            return !0
        }, {
            restrict: "A", require: "?ngModel", priority: 1, link: function (a, l, o, d) {
                var u, c, f, h, p, g, b, v, S, y, w;
                return a.disabledValuesHistory = a.disabledValuesHistory ? a.disabledValuesHistory : [], l = $(l), l.addClass("localytics-chosen"), p = a.$eval(o.chosen) || {}, angular.forEach(o, function (t, r) {
                    return e.call(n, r) >= 0 ? o.$observe(r, function (e) {
                        return p[s(r)] = "{{" === String(l.attr(o.$attr[r])).slice(0, 2) ? e : a.$eval(e), S()
                    }) : void 0
                }), b = function () {
                    return l.addClass("loading").attr("disabled", !0).trigger("chosen:updated")
                }, v = function () {
                    return l.removeClass("loading"), angular.isDefined(o.disabled) ? l.attr("disabled", o.disabled) : l.attr("disabled", !1), l.trigger("chosen:updated")
                }, u = null, c = !1, f = function () {
                    var e;
                    return u ? l.trigger("chosen:updated") : (t(function () {
                        u = l.chosen(p).data("chosen")
                    }), angular.isObject(u) ? e = u.default_text : void 0)
                }, S = function () {
                    return c ? l.attr("data-placeholder", u.results_none_found).attr("disabled", !0) : l.removeAttr("data-placeholder"), l.trigger("chosen:updated")
                }, d ? (g = d.$render, d.$render = function () {
                    return g(), f()
                }, l.on("chosen:hiding_dropdown", function () {
                    return a.$apply(function () {
                        return d.$setTouched()
                    })
                }), o.multiple && (w = function () {
                    return d.$viewValue
                }, a.$watch(w, d.$render, !0))) : f(), o.$observe("disabled", function () {
                    return l.trigger("chosen:updated")
                }), o.ngOptions && d ? (h = o.ngOptions.match(r), y = h[7], a.$watchCollection(y, function (e, n) {
                    var r;
                    return r = t(function () {
                        return angular.isUndefined(e) ? b() : (c = i(e), v(), S())
                    })
                }), a.$on("$destroy", function (e) {
                    return "undefined" != typeof timer && null !== timer ? t.cancel(timer) : void 0
                })) : void 0
            }
        }
    }])
}).call(this);