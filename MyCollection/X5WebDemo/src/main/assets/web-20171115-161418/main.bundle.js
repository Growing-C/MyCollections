webpackJsonp(["main"],{

/***/ "../../../../../src/$$_gendir lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../src/$$_gendir lazy recursive";

/***/ }),

/***/ "../../../../../src/app/app-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_home_loading_loading_component__ = __webpack_require__("../../../../../src/app/home/loading/loading.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_home_read_card_read_card_component__ = __webpack_require__("../../../../../src/app/home/read-card/read-card.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__home_main_menu_main_menu_component__ = __webpack_require__("../../../../../src/app/home/main-menu/main-menu.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__outpatient_outpatient_payment_record_outpatient_payment_record_component__ = __webpack_require__("../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__shared_payment_amount_select_payment_amount_select_component__ = __webpack_require__("../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__shared_payment_type_select_payment_type_select_component__ = __webpack_require__("../../../../../src/app/shared/payment-type-select/payment-type-select.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__shared_payment_payment_component__ = __webpack_require__("../../../../../src/app/shared/payment/payment.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__shared_payment_result_payment_result_component__ = __webpack_require__("../../../../../src/app/shared/payment-result/payment-result.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__inpatient_inpatient_login_inpatient_login_component__ = __webpack_require__("../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__shared_error_page_error_page_component__ = __webpack_require__("../../../../../src/app/shared/error-page/error-page.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__shared_error_wait_error_wait_component__ = __webpack_require__("../../../../../src/app/shared/error-wait/error-wait.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__home_banner_banner_component__ = __webpack_require__("../../../../../src/app/home/banner/banner.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};















var routes = [
    { path: '', redirectTo: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].loading, pathMatch: 'full' },
    // {path: '', redirectTo: PathLibrary.errorWait, pathMatch: 'full'},
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].loading, component: __WEBPACK_IMPORTED_MODULE_3_app_home_loading_loading_component__["a" /* LoadingComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].readCard, component: __WEBPACK_IMPORTED_MODULE_4_app_home_read_card_read_card_component__["a" /* ReadCardComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].payment, component: __WEBPACK_IMPORTED_MODULE_9__shared_payment_payment_component__["a" /* PaymentComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].paymentResult, component: __WEBPACK_IMPORTED_MODULE_10__shared_payment_result_payment_result_component__["a" /* PaymentResultComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].mainMenu, component: __WEBPACK_IMPORTED_MODULE_5__home_main_menu_main_menu_component__["a" /* MainMenuComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].outpatientPaymentRecord, component: __WEBPACK_IMPORTED_MODULE_6__outpatient_outpatient_payment_record_outpatient_payment_record_component__["a" /* OutpatientPaymentRecordComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].paymentAmountSelect, component: __WEBPACK_IMPORTED_MODULE_7__shared_payment_amount_select_payment_amount_select_component__["a" /* PaymentAmountSelectComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].paymentTypeSelect, component: __WEBPACK_IMPORTED_MODULE_8__shared_payment_type_select_payment_type_select_component__["a" /* PaymentTypeSelectComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].inpatientLogin, component: __WEBPACK_IMPORTED_MODULE_11__inpatient_inpatient_login_inpatient_login_component__["a" /* InpatientLoginComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].errorPage, component: __WEBPACK_IMPORTED_MODULE_12__shared_error_page_error_page_component__["a" /* ErrorPageComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].errorWait, component: __WEBPACK_IMPORTED_MODULE_13__shared_error_wait_error_wait_component__["a" /* ErrorWaitComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].banner, component: __WEBPACK_IMPORTED_MODULE_14__home_banner_banner_component__["a" /* BannerComponent */] },
];
var AppRoutingModule = (function () {
    function AppRoutingModule() {
    }
    return AppRoutingModule;
}());
AppRoutingModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* RouterModule */].forRoot(routes, {
                enableTracing: false,
                useHash: true
            })
        ],
        exports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* RouterModule */]]
    })
], AppRoutingModule);

//# sourceMappingURL=app-routing.module.js.map

/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<router-outlet></router-outlet>\r\n<div class=\"status-show\">\r\n  <app-status-info></app-status-info>\r\n</div>\r\n<app-loading *ngIf=\"loadingShow\"></app-loading>\r\n"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_shared_error_dialog_error_dialog_component__ = __webpack_require__("../../../../../src/app/shared/error-dialog/error-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var AppComponent = (function () {
    function AppComponent(dialog, errorService, routerService, loadingService, systemService) {
        this.dialog = dialog;
        this.errorService = errorService;
        this.routerService = routerService;
        this.loadingService = loadingService;
        this.systemService = systemService;
    }
    AppComponent.prototype.ngOnInit = function () {
        this.infoStart();
        this.loadingStart();
    };
    AppComponent.prototype.ngOnDestroy = function () {
        this.infoStop();
        this.loadingStop();
    };
    AppComponent.prototype.infoStart = function () {
        var _this = this;
        this.alertObservable = this.errorService.getError();
        this.alertSubscription = this.alertObservable.subscribe(function (value) {
            _this.popAlertView(value);
            console.log('-- update error! --', value);
        }, function (error) {
            console.log('-- error! --');
        }, function () {
            console.log('-- end! --');
        });
    };
    AppComponent.prototype.infoStop = function () {
        if (this.alertSubscription) {
            this.alertSubscription.unsubscribe();
        }
    };
    // 弹出警告框
    AppComponent.prototype.popAlertView = function (infoMessage) {
        var _this = this;
        this.infoMessage = infoMessage;
        var dialogRef = this.dialog.open(__WEBPACK_IMPORTED_MODULE_2_app_shared_error_dialog_error_dialog_component__["a" /* ErrorDialogComponent */], {
            width: '8.3333rem',
            data: {
                errorInfo: infoMessage.data,
                autoCloseTime: infoMessage.autoCloseTime
            }
        });
        dialogRef.afterClosed().subscribe(function (result) {
            if (!_this.infoMessage.noRouter) {
                if (_this.infoMessage.routerInfo) {
                    // 跳到指定位置
                    _this.routerService.goTo(_this.infoMessage.routerInfo.commands, _this.infoMessage.routerInfo.extras);
                }
                else {
                    // 后退到主菜单界面，并清除用户信息
                    _this.systemService.backMainMenu();
                }
            }
        });
    };
    AppComponent.prototype.loadingStart = function () {
        var _this = this;
        this.loadingObservable = this.loadingService.getLoading();
        this.loadingSubscription = this.loadingObservable.subscribe(function (value) {
            _this.loadingShow = value;
            console.log('-- update loading! --', value);
        }, function (error) {
            console.log('-- error! --');
        }, function () {
            console.log('-- end! --');
        });
    };
    AppComponent.prototype.loadingStop = function () {
        if (this.loadingSubscription) {
            this.loadingSubscription.unsubscribe();
        }
    };
    return AppComponent;
}());
AppComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-root',
        template: __webpack_require__("../../../../../src/app/app.component.html"),
        styles: [__webpack_require__("../../../../../src/app/app.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__["a" /* RouterService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_system_service__["a" /* SystemService */]) === "function" && _e || Object])
], AppComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/@angular/platform-browser.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/@angular/platform-browser/animations.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_common_http__ = __webpack_require__("../../../common/@angular/common/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_routing_module__ = __webpack_require__("../../../../../src/app/app-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_assets_lib_amfe_flexible_2_2_1_index_min_js__ = __webpack_require__("../../../../../src/assets/lib/amfe-flexible-2.2.1/index.min.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_assets_lib_amfe_flexible_2_2_1_index_min_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_9_assets_lib_amfe_flexible_2_2_1_index_min_js__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__core_core_module__ = __webpack_require__("../../../../../src/app/core/core.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__home_home_module__ = __webpack_require__("../../../../../src/app/home/home.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__inpatient_inpatient_module__ = __webpack_require__("../../../../../src/app/inpatient/inpatient.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__outpatient_outpatient_module__ = __webpack_require__("../../../../../src/app/outpatient/outpatient.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
















var AppModule = (function () {
    function AppModule(systemService) {
        this.systemService = systemService;
        this.systemService.init();
    }
    return AppModule;
}());
AppModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["NgModule"])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_8__app_component__["a" /* AppComponent */],
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["h" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_7__app_routing_module__["a" /* AppRoutingModule */],
            __WEBPACK_IMPORTED_MODULE_6__angular_material__["b" /* MatButtonModule */],
            __WEBPACK_IMPORTED_MODULE_6__angular_material__["c" /* MatCheckboxModule */],
            __WEBPACK_IMPORTED_MODULE_6__angular_material__["i" /* MatRadioModule */],
            __WEBPACK_IMPORTED_MODULE_6__angular_material__["f" /* MatDialogModule */],
            __WEBPACK_IMPORTED_MODULE_6__angular_material__["h" /* MatExpansionModule */],
            __WEBPACK_IMPORTED_MODULE_4__angular_http__["b" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_5__angular_common_http__["c" /* HttpClientModule */],
            __WEBPACK_IMPORTED_MODULE_5__angular_common_http__["b" /* HttpClientJsonpModule */],
            __WEBPACK_IMPORTED_MODULE_14__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_10__core_core_module__["a" /* CoreModule */],
            __WEBPACK_IMPORTED_MODULE_11__home_home_module__["a" /* HomeModule */],
            __WEBPACK_IMPORTED_MODULE_12__inpatient_inpatient_module__["a" /* InpatientModule */],
            __WEBPACK_IMPORTED_MODULE_13__outpatient_outpatient_module__["a" /* OutpatientModule */]
        ],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_8__app_component__["a" /* AppComponent */]],
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_15_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_15_app_core_services_system_service__["a" /* SystemService */]) === "function" && _a || Object])
], AppModule);

var _a;
//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ "../../../../../src/app/core/bases/base-service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BaseService; });
// service 基类
var BaseService = (function () {
    function BaseService() {
    }
    /**
     * 初始化，为了保证实例能被创建
     */
    BaseService.prototype.init = function () {
    };
    return BaseService;
}());

//# sourceMappingURL=base-service.js.map

/***/ }),

/***/ "../../../../../src/app/core/core.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CoreModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_request_service__ = __webpack_require__("../../../../../src/app/protocol/server/server-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_public_http_service__ = __webpack_require__("../../../../../src/app/core/public/http.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_auth_service__ = __webpack_require__("../../../../../src/app/core/services/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_back_service__ = __webpack_require__("../../../../../src/app/core/services/back.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_services_info_service__ = __webpack_require__("../../../../../src/app/core/services/info.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_app_core_services_timer_service__ = __webpack_require__("../../../../../src/app/core/services/timer.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_app_core_utility_app_error_handler__ = __webpack_require__("../../../../../src/app/core/utility/app-error-handler.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
















var CoreModule = (function () {
    function CoreModule() {
    }
    return CoreModule;
}());
CoreModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"]
        ],
        declarations: [],
        providers: [
            __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_request_service__["a" /* ServerRequestService */],
            __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */],
            __WEBPACK_IMPORTED_MODULE_5_app_core_services_auth_service__["a" /* AuthService */],
            __WEBPACK_IMPORTED_MODULE_6_app_core_services_back_service__["a" /* BackService */],
            __WEBPACK_IMPORTED_MODULE_7_app_core_services_error_service__["a" /* ErrorService */],
            __WEBPACK_IMPORTED_MODULE_8_app_core_services_loading_service__["a" /* LoadingService */],
            __WEBPACK_IMPORTED_MODULE_4_app_core_public_http_service__["a" /* HttpService */],
            __WEBPACK_IMPORTED_MODULE_9_app_core_services_info_service__["a" /* InfoService */],
            __WEBPACK_IMPORTED_MODULE_10_app_core_services_log_service__["a" /* LogService */],
            __WEBPACK_IMPORTED_MODULE_11_app_core_services_router_service__["a" /* RouterService */],
            __WEBPACK_IMPORTED_MODULE_12_app_core_services_system_service__["a" /* SystemService */],
            __WEBPACK_IMPORTED_MODULE_13_app_core_services_timer_service__["a" /* TimerService */],
            __WEBPACK_IMPORTED_MODULE_14_app_core_services_user_service__["a" /* UserService */],
            { provide: __WEBPACK_IMPORTED_MODULE_0__angular_core__["ErrorHandler"], useClass: __WEBPACK_IMPORTED_MODULE_15_app_core_utility_app_error_handler__["a" /* AppErrorHandler */] }
        ],
    })
], CoreModule);

//# sourceMappingURL=core.module.js.map

/***/ }),

/***/ "../../../../../src/app/core/libs/cover-item-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CoverItemLibrary; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_app_core_objects_cover_item__ = __webpack_require__("../../../../../src/app/core/objects/cover-item.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
// 封面 item 数据定义


var CoverItemLibrary = (function () {
    function CoverItemLibrary() {
    }
    CoverItemLibrary.itemData = function () {
        var coverData = [];
        coverData.push(new __WEBPACK_IMPORTED_MODULE_0_app_core_objects_cover_item__["a" /* CoverItem */](1, 'assets/images/icon_outpatient-payment.png', '门诊待缴费查询', __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].outpatientPaymentRecord));
        coverData.push(new __WEBPACK_IMPORTED_MODULE_0_app_core_objects_cover_item__["a" /* CoverItem */](2, 'assets/images/icon_hospital-pay-cost.png', '住院缴费', __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].inpatientLogin));
        coverData.push(new __WEBPACK_IMPORTED_MODULE_0_app_core_objects_cover_item__["a" /* CoverItem */](3, 'assets/images/icon_one-card-recharge.png', '一卡通充值', __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentAmountSelect));
        // coverData.push(new CoverItem(4, 'assets/images/4.png',  '健康护理', PathLibrary.video));
        // coverData.push(new CoverItem(4, 'assets/images/4.png',  '健康护理', PathLibrary.login));
        return coverData;
    };
    return CoverItemLibrary;
}());

CoverItemLibrary.type1 = 1; // 住院缴费
CoverItemLibrary.type2 = 2; // 费用清单
CoverItemLibrary.type3 = 3; // 门诊预约
CoverItemLibrary.type4 = 4; // 健康护理
CoverItemLibrary.type5 = 5; // 门诊缴费查询
CoverItemLibrary.type6 = 6; // 住院缴费
CoverItemLibrary.type7 = 7; // 一卡通充值
//# sourceMappingURL=cover-item-library.js.map

/***/ }),

/***/ "../../../../../src/app/core/libs/info-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InfoLibrary; });
// 提示信息定义，显示给用户看的文字
var InfoLibrary = (function () {
    function InfoLibrary() {
    }
    return InfoLibrary;
}());

InfoLibrary.getConfigFail = '配置信息获取失败';
InfoLibrary.getTokenFail = '设备连接服务器失败';
InfoLibrary.getUserInfoFromServerFail = '从服务器获取用户信息失败';
InfoLibrary.getOutpatientPayRecordsFromServerFail = '从服务器获取门诊缴费记录（未缴费用）失败';
InfoLibrary.createOrderFail = '缴费下单（生成预订单）失败';
InfoLibrary.queryOrderStatusFail = '查询订单状态失败';
InfoLibrary.getInpatientInfoFromServerFail = '从服务器获取住院病人的信息';
InfoLibrary.paymentAmountError = '请输入正确的金额'; // 内部保留使用
InfoLibrary.networkError = '网络连接错误'; // 内部保留使用
InfoLibrary.outpatientNumError = '获取信息失败，请重试或检查就诊卡是否正确'; // 内部保留使用
InfoLibrary.inpatientNumError = '住院号不正确，请重新输入'; // 内部保留使用
//# sourceMappingURL=info-library.js.map

/***/ }),

/***/ "../../../../../src/app/core/libs/mode-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ModeLibrary; });
// 应用运行模式
var ModeLibrary = (function () {
    function ModeLibrary() {
    }
    return ModeLibrary;
}());

ModeLibrary.modeProduction = '0'; //  产品模式，设备上运行，不显示调试信息
ModeLibrary.modeDeviceDebug = '10'; //  设备调试模式，设备上运行，且显示调试信息
ModeLibrary.modeUserDebug = '50'; //  用户调试模式，设备上运行，使用读卡器但锁定为指定用户，且显示调试信息
ModeLibrary.modeShellWebDebug = '55'; //  用户调试模式，设备上运行，不使用读卡器，且显示调试信息
ModeLibrary.modeWebDebug = '99'; //  开发调试模式，脱壳运行，全模拟，不与壳交互
//# sourceMappingURL=mode-library.js.map

/***/ }),

/***/ "../../../../../src/app/core/libs/path-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PathLibrary; });
// 路由路径名定义
var PathLibrary = (function () {
    function PathLibrary() {
    }
    return PathLibrary;
}());

PathLibrary.cover = 'cover';
PathLibrary.miniPay = 'miniPay';
PathLibrary.paymentMethodSelect = 'paymentMethodSelect';
PathLibrary.payment = 'payment';
PathLibrary.paymentResult = 'paymentResult';
PathLibrary.outpatientAppointment = 'outpatientAppointment';
PathLibrary.paymentRecords = 'paymentRecords';
PathLibrary.paymentRecordDetails = 'paymentRecordDetails';
PathLibrary.loading = 'loading';
PathLibrary.ad = 'ad'; // 广告
PathLibrary.readCard = 'readCard/:nextPath'; // 读卡
PathLibrary.readCardPath = 'readCard/';
PathLibrary.readCardParameter = 'nextPath';
PathLibrary.mainMenu = 'mainMenu';
PathLibrary.outpatientPaymentRecord = 'outpatientPaymentRecord';
PathLibrary.paymentAmountSelect = 'paymentAmountSelect';
PathLibrary.paymentTypeSelect = 'paymentTypeSelect';
PathLibrary.inpatientLogin = 'inpatientLogin';
PathLibrary.errorPage = 'errorPage'; // 出错后卡死的UI
PathLibrary.errorWait = 'errorWait'; // 出错后等待稍后自动重试的UI
PathLibrary.banner = 'banner';
//# sourceMappingURL=path-library.js.map

/***/ }),

/***/ "../../../../../src/app/core/libs/pay-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PayLibrary; });
// 支付相关常量定义
var PayLibrary = (function () {
    function PayLibrary() {
    }
    return PayLibrary;
}());

PayLibrary.dataSrcOneCardPay = 'eCardPay'; // 缴费类型：一卡通充值
PayLibrary.dataSrcInpatient = 'prepaySrc'; // 缴费类型：一卡通充值
PayLibrary.payTypeWeChatQRCode = 'wechatQrcode'; // 支付渠道：微信扫码支付
PayLibrary.payTypeALiPayQRCode = 'alipayQrcode'; // 支付渠道：支付宝扫码支付
PayLibrary.orderStatusWaitPay = '1'; // 待支付
PayLibrary.orderStatusNotStartToHIS = '2'; // 已支付正在通知HIS中
PayLibrary.orderStatusPaying = '6'; // 正在支付
PayLibrary.orderStatusFailedToHIS = '7'; // 支付成功通知HIS失败
PayLibrary.orderStatusSucToHIS = '9'; // 支付成功通知HIS成功
PayLibrary.PaySuccess = 'success'; // 支付结果：支付成功通知HIS成功
PayLibrary.PayFail = 'fail'; // 支付结果：支付失败
PayLibrary.FailedToHIS = 'HISFail'; // 支付结果：支付失败通知HIS失败
//# sourceMappingURL=pay-library.js.map

/***/ }),

/***/ "../../../../../src/app/core/libs/public-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PublicLibrary; });
// 公共常量的定义
var PublicLibrary = (function () {
    function PublicLibrary() {
    }
    return PublicLibrary;
}());

PublicLibrary.httpResponseSuccess = 'success'; // 访问服务器请求成功，正常状态
PublicLibrary.httpResponseTokenTimeout = 'token_timeout'; // 访问服务器请求失败，token 超时
//# sourceMappingURL=public-library.js.map

/***/ }),

/***/ "../../../../../src/app/core/mock/mock-shell.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MockShell; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_app_core_public_shell_utility__ = __webpack_require__("../../../../../src/app/core/public/shell-utility.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_mode_library__ = __webpack_require__("../../../../../src/app/core/libs/mode-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__ = __webpack_require__("../../../../../src/app/protocol/server/server-api-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-api-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_protocol_shell_output_get_config__ = __webpack_require__("../../../../../src/app/protocol/shell/output-get-config.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_protocol_shell_output_read_magnetic_card__ = __webpack_require__("../../../../../src/app/protocol/shell/output-read-magnetic-card.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_assets_external_external_js__ = __webpack_require__("../../../../../src/assets/external/external.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_assets_external_external_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_assets_external_external_js__);
// 模拟外壳数据







var MockShell = (function () {
    function MockShell() {
    }
    MockShell.mock = function (type, data) {
        switch (type) {
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].getConfig:
                MockShell.mockShell2WebGetConfig();
                break;
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readMagneticCard:
                MockShell.mockShell2WebReadMagneticCard();
                break;
            default:
                console.error('get web2shell data with code: ' + type + ', and data: ' + data);
        }
    };
    // ------------
    // 获取配置相关
    MockShell.mockShell2WebGetConfig = function () {
        MockShell.mockShell2WebGetConfigSuc();
        // MockShell.mockShell2WebGetConfigErr();
    };
    MockShell.mockShell2WebGetConfigSuc = function () {
        // 模拟异步行为
        setTimeout(function () {
            var outputGetConfig = MockShell.getDemoHardwareInfo();
            var data = __WEBPACK_IMPORTED_MODULE_0_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2web(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc, outputGetConfig);
            responseService(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].getConfig, data);
        }, 500);
    };
    // private static mockShell2WebGetConfigErr() {
    //   // 模拟异步行为
    //   setTimeout(() => {
    //     const data: string = ShellUtility.generateString2web(ShellApiLibrary.statusErr, null);
    //     responseService(ShellApiLibrary.getConfig, data);
    //   }, 500);
    // }
    MockShell.getDemoHardwareInfo = function () {
        var outputGetConfig = new __WEBPACK_IMPORTED_MODULE_4_app_protocol_shell_output_get_config__["a" /* OutputGetConfig */]();
        outputGetConfig.server = MockShell.server_WEB;
        outputGetConfig.deviceID = '';
        outputGetConfig.hospitalID = MockShell.hospitalID_AQEY;
        outputGetConfig.macAddress = 'aa:bb:cc:dd:ee';
        // outputGetConfig.macAddress = '68:F7:28:68:A7:9C';
        outputGetConfig.download = 'http://d2.witon.us/minipay/dev/test/download/dev/aqsdermyyadmin/download.json';
        outputGetConfig.webVersion = '0.1.5';
        outputGetConfig.mode = __WEBPACK_IMPORTED_MODULE_1_app_core_libs_mode_library__["a" /* ModeLibrary */].modeWebDebug;
        return outputGetConfig;
    };
    // ------------
    // 直接模拟外壳读卡数据，适合无壳状态下，用于主动触发模拟读卡动作
    MockShell.mockShell2WebReadMagneticCard = function () {
        // 模拟异步行为
        setTimeout(function () {
            var data = MockShell.mockReadCardData();
            responseService(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readMagneticCard, data);
        }, 100);
    };
    // ------------
    // 模拟环境数据，适合有壳状态下
    MockShell.mockReadCardData = function () {
        var outputReadMagneticCard = new __WEBPACK_IMPORTED_MODULE_5_app_protocol_shell_output_read_magnetic_card__["a" /* OutputReadMagneticCard */]();
        switch (__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId) {
            case MockShell.hospitalID_AQEY:
                outputReadMagneticCard.cardID = '00321508';
                // outputReadMagneticCard.cardID = '17010650';
                break;
            default:
                console.error('hospitalID not found: ' + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId);
        }
        var data = __WEBPACK_IMPORTED_MODULE_0_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2web(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc, outputReadMagneticCard);
        return data;
    };
    return MockShell;
}());

MockShell.hospitalID_AQEY = 'aqsdermyyadmin'; // 安庆二院
MockShell.server_WEB = 'http://web.witontek.com/eHospital2/'; // web
MockShell.server_CYT = 'http://192.168.1.165:8080/eHospital2/'; // 陈远桃
MockShell.server_LWL = 'http://192.168.1.161:8080/eHospital2/'; // 廖文蕾
MockShell.server_PRO = 'http://ehospital.witontek.com/eHospital2/'; // 生产环境
//# sourceMappingURL=mock-shell.js.map

/***/ }),

/***/ "../../../../../src/app/core/objects/auth-info.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthInfo; });
// 设备登陆信息，将会持续保存
var AuthInfo = (function () {
    function AuthInfo() {
    }
    return AuthInfo;
}());

//# sourceMappingURL=auth-info.js.map

/***/ }),

/***/ "../../../../../src/app/core/objects/cover-item.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CoverItem; });
// 封面上菜单项数据格式定义
var CoverItem = (function () {
    function CoverItem(itemType, imageURL, title, path) {
        this.itemType = itemType;
        this.imageURL = imageURL;
        this.title = title;
        this.path = path;
    }
    return CoverItem;
}());

//# sourceMappingURL=cover-item.js.map

/***/ }),

/***/ "../../../../../src/app/core/objects/info-message.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InfoMessage; });
// 信息消息
var InfoMessage = (function () {
    function InfoMessage(data, status, routerInfo) {
        if (status === void 0) { status = 1; }
        if (routerInfo === void 0) { routerInfo = null; }
        this.autoCloseTime = 0; // 打开后自动关闭前持续的时间，0为不会自动关闭，大于零则为持续时间，单位毫秒
        this.noRouter = false; // 关闭时是否无须跳转到其他UI，如为 true 则不会跳转
        this.data = data;
        this.status = status;
        this.routerInfo = routerInfo;
    }
    return InfoMessage;
}());

InfoMessage.statusNormal = 1; // 普通信息
InfoMessage.statusError = 2; // 错误信息
//# sourceMappingURL=info-message.js.map

/***/ }),

/***/ "../../../../../src/app/core/objects/router-info.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RouterInfo; });
// 路由信息
var RouterInfo = (function () {
    function RouterInfo(commands, extras) {
        if (extras === void 0) { extras = null; }
        this.commands = commands;
        this.extras = extras;
    }
    return RouterInfo;
}());

//# sourceMappingURL=router-info.js.map

/***/ }),

/***/ "../../../../../src/app/core/objects/user-info.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserInfo; });
// 用户信息
var UserInfo = (function () {
    function UserInfo() {
    }
    /**
     * 判断是否存在用户信息
     * @returns {boolean}
     */
    UserInfo.prototype.hasPatientInfo = function () {
        var hasInfo = false;
        if ((this.outputQueryOutPatientInfo) || (this.outputQueryInHospitalPatient)) {
            hasInfo = true;
        }
        return hasInfo;
    };
    /**
     * 清除用户信息
     */
    UserInfo.prototype.clearPatientInfo = function () {
        this.outputQueryOutPatientInfo = null;
        this.outputQueryInHospitalPatient = null;
    };
    return UserInfo;
}());

//# sourceMappingURL=user-info.js.map

/***/ }),

/***/ "../../../../../src/app/core/public/device-parameter.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DeviceParameter; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_app_core_libs_mode_library__ = __webpack_require__("../../../../../src/app/core/libs/mode-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
// 系统参数


var DeviceParameter = (function () {
    function DeviceParameter() {
    }
    Object.defineProperty(DeviceParameter, "isInfoDebugMode", {
        get: function () {
            return this._isInfoDebugMode;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DeviceParameter, "isReadCardDebugMode", {
        get: function () {
            return this._isReadCardDebugMode;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DeviceParameter, "isShellDebugMode", {
        get: function () {
            return this._isShellDebugMode;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DeviceParameter, "isUserDebugMode", {
        get: function () {
            return this._isUserDebugMode;
        },
        enumerable: true,
        configurable: true
    });
    /**
     * 设置产品运行模式
     * @param {string} mode
     */
    DeviceParameter.setMode = function (mode) {
        switch (mode) {
            case __WEBPACK_IMPORTED_MODULE_0_app_core_libs_mode_library__["a" /* ModeLibrary */].modeProduction:
                DeviceParameter.enableProductionMode();
                break;
            case __WEBPACK_IMPORTED_MODULE_0_app_core_libs_mode_library__["a" /* ModeLibrary */].modeDeviceDebug:
                DeviceParameter.enableDeviceDebugMode();
                break;
            case __WEBPACK_IMPORTED_MODULE_0_app_core_libs_mode_library__["a" /* ModeLibrary */].modeUserDebug:
                DeviceParameter.enableUserDebugMode();
                break;
            case __WEBPACK_IMPORTED_MODULE_0_app_core_libs_mode_library__["a" /* ModeLibrary */].modeShellWebDebug:
                DeviceParameter.enableShellWebDebugMode();
                break;
            default:
                DeviceParameter.enableWebDebugMode();
                break;
        }
    };
    DeviceParameter.toString = function () {
        return 'DeviceParameter... isShellDebugMode:['
            + DeviceParameter._isShellDebugMode
            + '], isReadCardDebugMode:['
            + DeviceParameter._isReadCardDebugMode
            + '], isUserDebugMode:['
            + DeviceParameter._isUserDebugMode
            + '], isInfoDebugMode:['
            + DeviceParameter._isInfoDebugMode
            + ']';
    };
    /**
     * 设置为web浏览器调试模式（开发时在浏览器中运行使用，全模拟）
     */
    DeviceParameter.enableWebDebugMode = function () {
        console.log('run with Web Debug mode');
        DeviceParameter._isShellDebugMode = true;
        DeviceParameter._isReadCardDebugMode = true;
        DeviceParameter._isUserDebugMode = true;
        DeviceParameter._isInfoDebugMode = true;
        DeviceParameter.changeIsInfoDebugMode(DeviceParameter._isInfoDebugMode);
    };
    /**
     * 设置为壳运行web浏览器调试模式（开发时在壳中运行，调用webview浏览器，脱离读卡器使用）
     */
    DeviceParameter.enableShellWebDebugMode = function () {
        console.log('run with Web Debug mode');
        DeviceParameter._isShellDebugMode = false;
        DeviceParameter._isReadCardDebugMode = true;
        DeviceParameter._isUserDebugMode = true;
        DeviceParameter._isInfoDebugMode = true;
        DeviceParameter.changeIsInfoDebugMode(DeviceParameter._isInfoDebugMode);
    };
    /**
     * 设置为产品模式（设备上运行，真实数据使用）
     */
    DeviceParameter.enableProductionMode = function () {
        DeviceParameter._isShellDebugMode = false;
        DeviceParameter._isReadCardDebugMode = false;
        DeviceParameter._isUserDebugMode = false;
        DeviceParameter._isInfoDebugMode = false;
        DeviceParameter.changeIsInfoDebugMode(DeviceParameter._isInfoDebugMode);
    };
    /**
     * 设置为模拟用户调试模式（设备上运行，模拟读卡数据）
     */
    DeviceParameter.enableUserDebugMode = function () {
        DeviceParameter._isShellDebugMode = false;
        DeviceParameter._isReadCardDebugMode = false;
        DeviceParameter._isUserDebugMode = true;
        DeviceParameter._isInfoDebugMode = true;
        DeviceParameter.changeIsInfoDebugMode(DeviceParameter._isInfoDebugMode);
    };
    /**
     * 设置为设备调试模式（在壳中运行使用，全真实，但现实调试信息）
     */
    DeviceParameter.enableDeviceDebugMode = function () {
        DeviceParameter._isShellDebugMode = false;
        DeviceParameter._isReadCardDebugMode = false;
        DeviceParameter._isUserDebugMode = false;
        DeviceParameter._isInfoDebugMode = true;
        DeviceParameter.changeIsInfoDebugMode(DeviceParameter._isInfoDebugMode);
    };
    /**
     * 获取Observable
     * @returns {Observable<boolean>}
     */
    DeviceParameter.getInfoDebugMode = function () {
        return DeviceParameter.isInfoDebugModeObservable;
    };
    DeviceParameter.init = function () {
        console.log('------------- 0');
        DeviceParameter.isInfoDebugModeObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            DeviceParameter.isInfoDebugModeSubscriber = subscriber;
        });
    };
    /**
     * 更新debugMode状态
     * @param {boolean} isInfoDebugMode
     */
    DeviceParameter.changeIsInfoDebugMode = function (isInfoDebugMode) {
        console.log('------------- 1');
        if (DeviceParameter.isInfoDebugModeSubscriber) {
            console.log('------------- 2');
            DeviceParameter.isInfoDebugModeSubscriber.next(isInfoDebugMode);
        }
    };
    return DeviceParameter;
}());

DeviceParameter._isShellDebugMode = false; // 是否调用模拟shell数据，此项可使app脱离外壳运行，true 为不与壳进行交互
DeviceParameter._isUserDebugMode = false; // 是否调用模拟用户数据，此项可脱离真实用户
DeviceParameter._isReadCardDebugMode = false; // 是否模拟读卡操作数据，此项可脱离读卡器运行
DeviceParameter._isInfoDebugMode = false; // 是否显示调试信息
//# sourceMappingURL=device-parameter.js.map

/***/ }),

/***/ "../../../../../src/app/core/public/http.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HttpService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/@angular/common/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
// 对服务器 http 请求处理的封装，未来会提取到公共模块中
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var HttpService = (function () {
    function HttpService(http) {
        this.http = http;
        this.timeoutMax = 18000; // 网络连接超时，毫秒
    }
    HttpService.prototype.requestServer = function (api, parameter) {
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('send http request. URL: ['
            + api
            + '], parameter:['
            + JSON.stringify(parameter)
            + ']');
        return this.http
            .post(api, parameter)
            .timeout(this.timeoutMax)
            .map(this.extractData)
            .catch(this.handleError);
    };
    // 提取数据做额外处理
    HttpService.prototype.extractData = function (responseData) {
        console.log('----- 99 ------', responseData);
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('get http response: ['
            + JSON.stringify(responseData)
            + ']');
        return responseData;
    };
    // HttpErrorResponse
    HttpService.prototype.handleError = function (error) {
        console.log('----- 100 ------', error);
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].error('get ERROR with http response: ['
            + JSON.stringify(error)
            + ']');
        var errMsg;
        if (error.message) {
            errMsg = error.message;
        }
        else {
            errMsg = error.toString();
        }
        return __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__["a" /* Observable */].throw(errMsg);
    };
    // -------------------------------------
    // 暂时封存不用，待下一版本再优化
    HttpService.prototype.requestRemoteJson = function (url) {
        console.log('----- 10 ------');
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('send http request. URL: ['
            + url
            + ']');
        // return this.http
        //   .jsonp(url, 'john')
        //   .timeout(this.timeoutMax)
        //   .map(this.extractData)
        //   .do(console.log)
        //   .catch(this.handleError);
        return this.http
            .get(url)
            .timeout(this.timeoutMax)
            .map(this.extractData)
            .catch(this.handleError);
    };
    return HttpService;
}());
HttpService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]) === "function" && _a || Object])
], HttpService);

var _a;
//# sourceMappingURL=http.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/public/shell-utility.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ShellUtility; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_app_protocol_shell_shell_api_library__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-api-library.ts");
// 与外壳交互时的工具集

var ShellUtility = (function () {
    function ShellUtility() {
    }
    /**
     * 生成符合通讯规则的字符串，提供给外壳使用
     * @param {string} action 与外壳约定的协议号
     * @param {Object} data 与外壳约定的协议数据类型，无则可为 null
     * @returns {string} 拼接完后的字符串，形如 '5100^*^{"name":"abc,"id:"a1"}'
     */
    ShellUtility.generateString2Shell = function (action, data) {
        var data2String;
        if (data) {
            data2String = JSON.stringify(data);
        }
        else {
            data2String = ShellUtility.nullObject;
        }
        data2String = action + ShellUtility.protocolSeparator + data2String;
        return data2String;
    };
    /**
     * 生成符合通讯规则的字符串，专为日志记录的格式，提供给外壳使用
     * 将 '^*^' 一律替换为 '@@'
     * @param {string} action 与外壳约定的协议号
     * @param {Object} data 与外壳约定的协议数据类型，无则可为 null
     * @returns {string}
     */
    ShellUtility.generateString2Shell4Log = function (action, data) {
        var data2String = ShellUtility.generateString2Shell(action, data);
        data2String = data2String.replace(__WEBPACK_IMPORTED_MODULE_0_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].log + ShellUtility.protocolSeparator, '###'); // 保护正常字符
        data2String = data2String.replace(ShellUtility.protocolSeparator, ShellUtility.logProtocolSeparator); // 替换掉可能出错的字符
        data2String = data2String.replace('###', __WEBPACK_IMPORTED_MODULE_0_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].log + ShellUtility.protocolSeparator); // 将受保护的字符替换回来
        return data2String;
    };
    /**
     * 生成符合通讯规则的字符串，提供给web使用，用于模拟shell传来的数据
     * @param {string} status 与外壳约定的协议号
     * @param {Object} data 与外壳约定的协议数据类型，无则可为 null
     * @returns {string} 拼接完后的字符串，形如 '0000^*^{"name":"abc,"id:"a1"}'
     */
    ShellUtility.generateString2web = function (status, data) {
        var data2String;
        if (data) {
            data2String = JSON.stringify(data);
        }
        else {
            data2String = ShellUtility.nullObject;
        }
        data2String = status + ShellUtility.protocolSeparator + data2String;
        return data2String;
    };
    return ShellUtility;
}());

ShellUtility.errorDataFromShell = 'data error from shell';
ShellUtility.protocolSeparator = '^*^'; // 协议中使用
ShellUtility.logProtocolSeparator = '@@'; // 写入设备日志中使用
ShellUtility.nullObject = '{}';
//# sourceMappingURL=shell-utility.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/auth.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_protocol_shell_shell_request_service__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_protocol_server_server_request_service__ = __webpack_require__("../../../../../src/app/protocol/server/server-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__ = __webpack_require__("../../../../../src/app/protocol/server/server-api-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_protocol_server_input_device_login__ = __webpack_require__("../../../../../src/app/protocol/server/input-device-login.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_public_device_parameter__ = __webpack_require__("../../../../../src/app/core/public/device-parameter.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_public_http_service__ = __webpack_require__("../../../../../src/app/core/public/http.service.ts");
// 设备身份认证
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};











var AuthService = (function (_super) {
    __extends(AuthService, _super);
    function AuthService(shellRequestService, serverRequestService, routerService, httpService) {
        var _this = _super.call(this) || this;
        _this.shellRequestService = shellRequestService;
        _this.serverRequestService = serverRequestService;
        _this.routerService = routerService;
        _this.httpService = httpService;
        return _this;
    }
    AuthService.prototype.init = function () {
    };
    // 开始设备身份认证
    AuthService.prototype.startAuth = function () {
        var _this = this;
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('get config info start ...');
        this.web2ShellGetConfigSubscription = this.shellRequestService.web2ShellGetConfig().subscribe(function (data) { return _this.getConfigSuc(data); }, function (error) { return _this.getConfigErr(error); });
    };
    // 获取设备配置，成功
    AuthService.prototype.getConfigSuc = function (outputGetConfig) {
        this.web2ShellGetConfigSubscription.unsubscribe();
        if (outputGetConfig.hospitalID && outputGetConfig.server && outputGetConfig.mode) {
            // save config
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL = outputGetConfig.server;
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId = outputGetConfig.hospitalID;
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.deviceID = outputGetConfig.deviceID;
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.macAddress = outputGetConfig.macAddress;
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.download = outputGetConfig.download;
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.webVersion = outputGetConfig.webVersion;
            __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.mode = outputGetConfig.mode;
            console.log('----- 001 ------', outputGetConfig.download);
            console.log('----- 002 ------', __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.download);
            __WEBPACK_IMPORTED_MODULE_9_app_core_public_device_parameter__["a" /* DeviceParameter */].setMode(outputGetConfig.mode);
            this.getTokenFromServer(outputGetConfig);
        }
        else {
            // 无法获取相关信息时，跳转错误页面
            // log
            __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].error('get config fail: [parameters invalid]. Show Error Page.');
            // go to error page
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_8_app_core_libs_path_library__["a" /* PathLibrary */].errorPage]);
        }
    };
    // 获取设备配置，失败
    AuthService.prototype.getConfigErr = function (errorMsg) {
        // log
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].error('get config fail: [' + errorMsg + ']. Show Error Page.');
        // go to error page
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_8_app_core_libs_path_library__["a" /* PathLibrary */].errorPage]);
    };
    // 去服务器进行设备身份认证
    AuthService.prototype.getTokenFromServer = function (outputGetConfig) {
        var _this = this;
        var inputDeviceLogin = new __WEBPACK_IMPORTED_MODULE_7_app_protocol_server_input_device_login__["a" /* InputDeviceLogin */]();
        inputDeviceLogin.hospital_id = outputGetConfig.hospitalID;
        inputDeviceLogin.device_id = outputGetConfig.deviceID;
        inputDeviceLogin.mac_address = outputGetConfig.macAddress;
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('get token start ...');
        this.serverRequestService.deviceLogin(inputDeviceLogin)
            .subscribe(function (data) { return _this.getTokenFromServerSuc(data, true); }, function (error) { return _this.getTokenFromServerErr(error); });
    };
    /**
     * 获取 token 成功的处理
     * @param {ResponseDataWrap} responseDataWrap 服务器返回的数据
     * @param {boolean} goToUI 是否自动跳转到指定UI（主菜单）
     */
    AuthService.prototype.getTokenFromServerSuc = function (responseDataWrap, goToUI) {
        // update token
        __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token = responseDataWrap.responseToken;
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('get token suc, value:' + responseDataWrap.responseToken);
        if (goToUI) {
            // 跳转UI，进入指定UI
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_8_app_core_libs_path_library__["a" /* PathLibrary */].mainMenu]);
        }
    };
    // 获取 token 失败的处理
    AuthService.prototype.getTokenFromServerErr = function (errorMsg) {
        // log
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].error('get token fail: [' + errorMsg + ']');
        // 跳转UI，进入出错等待界面
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_8_app_core_libs_path_library__["a" /* PathLibrary */].errorWait]);
    };
    // ---------------------------------
    // 去服务器进行设备身份认证
    AuthService.prototype.renewTokenFromServer = function () {
        var _this = this;
        var inputDeviceLogin = new __WEBPACK_IMPORTED_MODULE_7_app_protocol_server_input_device_login__["a" /* InputDeviceLogin */]();
        inputDeviceLogin.hospital_id = __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputDeviceLogin.device_id = __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.deviceID;
        inputDeviceLogin.mac_address = __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.macAddress;
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('get token start ...');
        this.serverRequestService.deviceLogin(inputDeviceLogin)
            .subscribe(function (data) { return _this.getTokenFromServerSuc(data, false); }, function (error) { return _this.getTokenFromServerErr(error); });
    };
    // ---------------------------------
    // 去服务器进行web版本校验
    AuthService.prototype.checkVersionFromServer = function () {
        var _this = this;
        this.httpService
            .requestRemoteJson(__WEBPACK_IMPORTED_MODULE_6_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.download)
            .subscribe(function (data) { return _this.checkVersionFromServerSuc(data, false); }, function (error) { return _this.checkVersionFromServerErr(error); });
    };
    AuthService.prototype.checkVersionFromServerSuc = function (responseDataWrap, goToWelcomeUI) {
        console.log('----- 555 ------');
        // // update token
        // ServerApiLibrary.authInfo.token = responseDataWrap.responseToken;
        //
        // LogService.debug('get token suc, value:' + responseDataWrap.responseToken);
        //
        // if (goToWelcomeUI) {
        //   // 跳转UI，进入欢迎UI
        //   this.routerService.goTo([PathLibrary.banner]);
        // }
    };
    AuthService.prototype.checkVersionFromServerErr = function (errorMsg) {
        console.log('----- 666 ------', errorMsg);
        // // log
        // LogService.error('get token fail: [' + errorMsg + ']');
        //
        // // 跳转UI，进入出错等待界面
        // this.routerService.goTo([PathLibrary.errorWait]);
    };
    return AuthService;
}(__WEBPACK_IMPORTED_MODULE_1_app_core_bases_base_service__["a" /* BaseService */]));
AuthService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5_app_protocol_server_server_request_service__["a" /* ServerRequestService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_protocol_server_server_request_service__["a" /* ServerRequestService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_router_service__["a" /* RouterService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_10_app_core_public_http_service__["a" /* HttpService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_10_app_core_public_http_service__["a" /* HttpService */]) === "function" && _d || Object])
], AuthService);

var _a, _b, _c, _d;
//# sourceMappingURL=auth.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/back.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BackService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__ = __webpack_require__("../../../../rxjs/_esm5/observable/TimerObservable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_auth_service__ = __webpack_require__("../../../../../src/app/core/services/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_protocol_shell_shell_request_service__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-request.service.ts");
// 后台服务，进行版本检查，token续期等的任务
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var BackService = (function (_super) {
    __extends(BackService, _super);
    function BackService(authService, shellRequestService) {
        var _this = _super.call(this) || this;
        _this.authService = authService;
        _this.shellRequestService = shellRequestService;
        _this.timerInitialDelay = 600000; // 初次开始生效的延迟时间，10分钟
        _this.timerPeriodCheckVersion = 600000; // 检查版本的时间间隔，10分钟
        _this.timerPeriodRenewToken = 43200000; // token续期的时间间隔，12小时
        return _this;
    }
    BackService.prototype.init = function () {
    };
    // 订阅，内部开始读秒机制
    BackService.prototype.start = function () {
        var _this = this;
        this.checkVersionTimerObservable = __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__["a" /* TimerObservable */].create(this.timerInitialDelay, this.timerPeriodCheckVersion);
        this.checkVersionTimerSubscription = this.checkVersionTimerObservable.subscribe(function (t) {
            __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('try to check version ...');
            _this.checkVersion();
        });
        this.renewTokenTimerObservable = __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__["a" /* TimerObservable */].create(this.timerPeriodRenewToken, this.timerPeriodRenewToken);
        this.renewTokenTimerSubscription = this.renewTokenTimerObservable.subscribe(function (t) {
            __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('try to renew token ...');
            _this.renewToken();
        });
    };
    // 停止读秒机制
    BackService.prototype.stop = function () {
        if (this.checkVersionTimerSubscription) {
            this.checkVersionTimerSubscription.unsubscribe();
        }
        if (this.renewTokenTimerSubscription) {
            this.renewTokenTimerSubscription.unsubscribe();
        }
    };
    // 检查版本
    BackService.prototype.checkVersion = function () {
        // this.authService.checkVersionFromServer();
        // 本版本直接调用壳，由壳判断是否需要更新
        this.shellRequestService.web2ShellUpdateVersion();
    };
    // token续期
    BackService.prototype.renewToken = function () {
        this.authService.renewTokenFromServer();
    };
    return BackService;
}(__WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__["a" /* BaseService */]));
BackService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_auth_service__["a" /* AuthService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */]) === "function" && _b || Object])
], BackService);

var _a, _b;
//# sourceMappingURL=back.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/error.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ErrorService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
// 封装了出错的处理，应显示在界面上
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ErrorService = (function (_super) {
    __extends(ErrorService, _super);
    function ErrorService() {
        return _super.call(this) || this;
    }
    /**
     * 获取Observable，UI组件专用
     * @returns {Observable<InfoMessage>}
     */
    ErrorService.prototype.getError = function () {
        return this.errorObservable;
    };
    /**
     * 显示错误信息
     * @param {InfoMessage} infoMessage
     */
    ErrorService.prototype.showError = function (infoMessage) {
        this.errorSubscriber.next(infoMessage);
    };
    ErrorService.prototype.init = function () {
        var _this = this;
        this.errorObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.errorSubscriber = subscriber;
        });
    };
    return ErrorService;
}(__WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__["a" /* BaseService */]));
ErrorService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [])
], ErrorService);

//# sourceMappingURL=error.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/info.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InfoService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
// 封装了系统提示信息，主要用于调试时使用
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var InfoService = InfoService_1 = (function (_super) {
    __extends(InfoService, _super);
    function InfoService() {
        return _super.call(this) || this;
    }
    /**
     * 显示调试信息，供内部逻辑程序调用，一般只供 LogService 调用
     * @param {string} newInfo
     */
    InfoService.showDebugInfo = function (newInfo, isError) {
        if (isError === void 0) { isError = false; }
        if (InfoService_1.instance) {
            InfoService_1.instance.updateDebugInfo(newInfo, isError);
        }
    };
    /**
     * 获取Observable，UI组件专用
     * @returns {Observable<InfoMessage>}
     */
    InfoService.getInfo = function () {
        return InfoService_1.instance.infoObservable;
    };
    InfoService.prototype.init = function () {
        var _this = this;
        if (!InfoService_1.instance) {
            InfoService_1.instance = this;
            this.infoObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
                _this.infoSubscriber = subscriber;
            });
        }
    };
    // 更新调试信息
    InfoService.prototype.updateDebugInfo = function (newInfo, isError) {
        if (isError === void 0) { isError = false; }
        if (this.infoSubscriber != null) {
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_3_app_core_objects_info_message__["a" /* InfoMessage */](newInfo);
            if (isError) {
                infoMessage.status = __WEBPACK_IMPORTED_MODULE_3_app_core_objects_info_message__["a" /* InfoMessage */].statusError;
            }
            this.infoSubscriber.next(infoMessage);
        }
    };
    return InfoService;
}(__WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__["a" /* BaseService */]));
InfoService = InfoService_1 = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [])
], InfoService);

var InfoService_1;
//# sourceMappingURL=info.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/loading.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoadingService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
// loading处理，应显示在界面上
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var LoadingService = (function (_super) {
    __extends(LoadingService, _super);
    function LoadingService() {
        return _super.call(this) || this;
    }
    /**
     * 获取Observable，UI组件专用
     * @returns {Observable<boolean>}
     */
    LoadingService.prototype.getLoading = function () {
        return this.loadingObservable;
    };
    /**
     * 显示loading
     */
    LoadingService.prototype.showLoading = function () {
        this.loadingSubscriber.next(true);
    };
    /**
     * 显示loading
     */
    LoadingService.prototype.hiddenLoading = function () {
        this.loadingSubscriber.next(false);
    };
    LoadingService.prototype.init = function () {
        var _this = this;
        this.loadingObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.loadingSubscriber = subscriber;
        });
    };
    return LoadingService;
}(__WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__["a" /* BaseService */]));
LoadingService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [])
], LoadingService);

//# sourceMappingURL=loading.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/log.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LogService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__ = __webpack_require__("../../../../../src/app/core/services/info.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__ = __webpack_require__("../../../../../src/app/core/public/device-parameter.ts");
// 日志记录，需要记录在日志文件或日志服务器中
// 日志级别：error, warn, info, debug
// 暂不实现具体逻辑
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var LogService = (function (_super) {
    __extends(LogService, _super);
    function LogService() {
        return _super.call(this) || this;
    }
    /**
     * 错误级别的日志, 该级别将被永久记录下来
     * @param {string} data 日志内容
     */
    LogService.error = function (data) {
        console.error('log ERROR', data); // 显示在 console 中
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__["a" /* InfoService */].showDebugInfo(data, true); // 显示在调试面板中
        // 写入日志文件
        if (!__WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode) {
            __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */].logToShell('ERROR: ' + data); // 写入日志文件
        }
    };
    /**
     * 警告级别的日志
     * @param {string} data 日志内容
     */
    LogService.warn = function (data) {
        console.error('log WARN', data); // 显示在 console 中
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__["a" /* InfoService */].showDebugInfo(data); // 显示在调试面板中
        // 写入日志文件
        if (!__WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode) {
            __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */].logToShell('WARN: ' + data); // 写入日志文件
        }
    };
    /**
     * 信息级别的日志
     * @param {string} data 日志内容
     */
    LogService.info = function (data) {
        console.log('log INFO', data); // 显示在 console 中
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__["a" /* InfoService */].showDebugInfo(data); // 显示在调试面板中
        // 写入日志文件
        if (!__WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode) {
            __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */].logToShell('INFO: ' + data);
        }
    };
    /**
     * 调试级别的日志，一般都是用此级别，正式上线前应确保无使用此级别信息
     * @param {string} data 日志内容
     */
    LogService.debug = function (data) {
        console.log('log DEBUG', data); // 显示在 console 中
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__["a" /* InfoService */].showDebugInfo(data); // 显示在调试面板中
        // 写入日志文件
        if (!__WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode) {
            __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */].logToShell('DEBUG: ' + data);
        }
    };
    LogService.prototype.init = function () {
    };
    return LogService;
}(__WEBPACK_IMPORTED_MODULE_1_app_core_bases_base_service__["a" /* BaseService */]));
LogService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [])
], LogService);

//# sourceMappingURL=log.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/router.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RouterService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_objects_router_info__ = __webpack_require__("../../../../../src/app/core/objects/router-info.ts");
// 路由的封装，含有用户权限认证的功能
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var RouterService = (function (_super) {
    __extends(RouterService, _super);
    function RouterService(router, userService) {
        var _this = _super.call(this) || this;
        _this.router = router;
        _this.userService = userService;
        _this.whiteList = []; // 白名单，不需要身份认证的页面
        _this.routerTrace = []; // 路由足迹
        return _this;
    }
    RouterService.prototype.init = function () {
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].loading);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].errorPage);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].errorWait);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].cover);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].ad);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].mainMenu);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].inpatientLogin);
        this.whiteList.push(__WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].banner);
    };
    /**
     * 跳转 UI。如无访问权限，则会自动跳转到设定UI
     */
    RouterService.prototype.goTo = function (commands, extras) {
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('router want goto: [' + String(commands) + ']');
        if (commands.length >= 1) {
            // 如果不在白名单中，则一律跳转到读卡界面，读卡完成后重新跳转到想去的界面
            if (this.whiteList.indexOf(commands[0]) < 0) {
                // 不在白名单中
                if (!this.userService.hasPatientInfo()) {
                    // 身份认证未通过，跳到读卡界面，并保留想访问的地址
                    commands[0] = __WEBPACK_IMPORTED_MODULE_3_app_core_libs_path_library__["a" /* PathLibrary */].readCardPath + commands[0];
                }
            }
            // save trace
            var currentRouterInfo = new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_router_info__["a" /* RouterInfo */](commands, extras);
            this.routerTrace.push(currentRouterInfo);
            // navigate
            if (typeof extras === 'object' && extras !== null) {
                this.router.navigate(currentRouterInfo.commands, currentRouterInfo.extras);
                __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('router goto: [' + String(currentRouterInfo.commands) + ', extras:' + String(currentRouterInfo.extras) + ']');
            }
            else {
                this.router.navigate(currentRouterInfo.commands);
                __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('router goto: [' + String(currentRouterInfo.commands) + ']');
            }
        }
        else {
            __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].error('Router parameter error because the commands value is wrong');
        }
    };
    /**
     * 后退
     */
    RouterService.prototype.back = function () {
        this.routerTrace.pop(); // 去掉当前路由信息
        var currentRouterInfo = this.routerTrace.pop(); // 取出上次路由信息
        this.goTo(currentRouterInfo.commands, currentRouterInfo.extras); // 重做上次路由跳转
        __WEBPACK_IMPORTED_MODULE_4_app_core_services_log_service__["a" /* LogService */].debug('router goto: [' + String(currentRouterInfo.commands) + ']');
    };
    /**
     * 清除路由痕迹
     */
    RouterService.prototype.clearTrace = function () {
        this.routerTrace = [];
    };
    return RouterService;
}(__WEBPACK_IMPORTED_MODULE_2_app_core_bases_base_service__["a" /* BaseService */]));
RouterService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object])
], RouterService);

var _a, _b;
//# sourceMappingURL=router.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/system.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SystemService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_protocol_shell_shell_request_service__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_auth_service__ = __webpack_require__("../../../../../src/app/core/services/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_back_service__ = __webpack_require__("../../../../../src/app/core/services/back.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_info_service__ = __webpack_require__("../../../../../src/app/core/services/info.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_services_timer_service__ = __webpack_require__("../../../../../src/app/core/services/timer.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_app_core_public_device_parameter__ = __webpack_require__("../../../../../src/app/core/public/device-parameter.ts");
// 系统相关
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};















var SystemService = (function () {
    function SystemService(shellRequestService, authService, backService, errorService, loadingService, routerService, infoService, logService, timerService, userService, dialog) {
        this.shellRequestService = shellRequestService;
        this.authService = authService;
        this.backService = backService;
        this.errorService = errorService;
        this.loadingService = loadingService;
        this.routerService = routerService;
        this.infoService = infoService;
        this.logService = logService;
        this.timerService = timerService;
        this.userService = userService;
        this.dialog = dialog;
    }
    SystemService.prototype.init = function () {
        var _this = this;
        // set device parameter
        __WEBPACK_IMPORTED_MODULE_14_app_core_public_device_parameter__["a" /* DeviceParameter */].init();
        // 版本发布前应改为设备运行模式
        // DeviceParameter.enableWebDebugMode(); // 脱壳运行，全模拟
        // init all service
        this.shellRequestService.init();
        this.logService.init();
        this.infoService.init();
        this.routerService.init();
        this.authService.init();
        this.errorService.init();
        this.loadingService.init();
        this.timerService.init();
        this.backService.init();
        // 建立获取从 ShellRequestService 发出的记录日志请求的机制
        this.shellRequestService.initLog()
            .subscribe(function (data) { return _this.getLogFromShellRequestServiceSuc(data); }, function (error) { return _this.getLogFromShellRequestServiceErr(error); });
    };
    // UI 初始化完成后调用，便于控制后续所有操作能在UI中显示信息
    SystemService.prototype.initUICompleted = function () {
        __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__["a" /* LogService */].info('UI init completed! ');
        // 开始设备身份认证过程
        this.authService.startAuth();
    };
    // 退回主菜单界面，并清理相关痕迹
    SystemService.prototype.backMainMenu = function () {
        // 关闭所有已打开的界面
        this.dialog.closeAll();
        // 清除用户信息
        this.userService.clearUserInfo();
        // 清除路由信息
        this.routerService.clearTrace();
        // UI跳转
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_12_app_core_libs_path_library__["a" /* PathLibrary */].mainMenu]);
    };
    // 跳转到错误等待界面，并清理相关痕迹
    SystemService.prototype.gotoErrorWait = function () {
        // 关闭所有已打开的界面
        this.dialog.closeAll();
        // 清除用户信息
        this.userService.clearUserInfo();
        // 清除路由信息
        this.routerService.clearTrace();
        // UI跳转
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_12_app_core_libs_path_library__["a" /* PathLibrary */].errorWait]);
    };
    // 获取从 ShellRequestService 发出的记录日志请求
    SystemService.prototype.getLogFromShellRequestServiceSuc = function (infoMessage) {
        if (infoMessage.status === __WEBPACK_IMPORTED_MODULE_13_app_core_objects_info_message__["a" /* InfoMessage */].statusNormal) {
            __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__["a" /* LogService */].debug(infoMessage.data);
        }
        else {
            __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__["a" /* LogService */].error(infoMessage.data);
        }
    };
    // 获取从 ShellRequestService 发出的记录日志请求，异常
    SystemService.prototype.getLogFromShellRequestServiceErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__["a" /* LogService */].error('getLogFromShellRequestService fail: [' + errorMsg + ']');
    };
    return SystemService;
}());
SystemService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_auth_service__["a" /* AuthService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_back_service__["a" /* BackService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_back_service__["a" /* BackService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_10_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_10_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_11_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_11_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_8_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8_app_core_services_router_service__["a" /* RouterService */]) === "function" && _f || Object, typeof (_g = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_info_service__["a" /* InfoService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_info_service__["a" /* InfoService */]) === "function" && _g || Object, typeof (_h = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__["a" /* LogService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_log_service__["a" /* LogService */]) === "function" && _h || Object, typeof (_j = typeof __WEBPACK_IMPORTED_MODULE_7_app_core_services_timer_service__["a" /* TimerService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7_app_core_services_timer_service__["a" /* TimerService */]) === "function" && _j || Object, typeof (_k = typeof __WEBPACK_IMPORTED_MODULE_9_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_9_app_core_services_user_service__["a" /* UserService */]) === "function" && _k || Object, typeof (_l = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */]) === "function" && _l || Object])
], SystemService);

var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k, _l;
//# sourceMappingURL=system.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/timer.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TimerService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_observable_TimerObservable__ = __webpack_require__("../../../../rxjs/_esm5/observable/TimerObservable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_bases_base_service__ = __webpack_require__("../../../../../src/app/core/bases/base-service.ts");
// 读秒计数服务
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var TimerService = (function (_super) {
    __extends(TimerService, _super);
    function TimerService() {
        var _this = _super.call(this) || this;
        _this.ONE_SECOND = 1000; // 1秒间隔
        _this.MAX_SECOND = 10; // X秒倒计时，默认预设值
        return _this;
    }
    TimerService.prototype.init = function () {
        var _this = this;
        this.maxTime = this.MAX_SECOND;
        this.timerOutsideObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.timerOutsideSubscriber = subscriber;
        });
        this.timerInsideObservable = __WEBPACK_IMPORTED_MODULE_2_rxjs_observable_TimerObservable__["a" /* TimerObservable */].create(0, this.ONE_SECOND);
    };
    // 订阅，内部开始读秒机制
    TimerService.prototype.start = function (maxTime) {
        var _this = this;
        if (maxTime === void 0) { maxTime = this.maxTime; }
        // reset
        this.maxTime = maxTime;
        this.currentTime = this.maxTime;
        if (this.timerInsideSubscription) {
            this.timerInsideSubscription.unsubscribe();
        }
        this.timerInsideSubscription = this.timerInsideObservable.subscribe(function (t) {
            // console.log('t: ', t);
            if (t <= _this.maxTime) {
                _this.change(_this.timerOutsideSubscriber);
            }
            else {
                // stop it
                _this.timerInsideSubscription.unsubscribe();
            }
        });
        return this.timerOutsideObservable;
    };
    // 只停止对内的读秒机制，对外的 Observable 应由调用方在外部进行 unsubscribe 处理
    TimerService.prototype.stop = function () {
        this.timerInsideSubscription.unsubscribe();
    };
    // 每秒改变读秒值，递减。第一次是立即从上限开始。
    TimerService.prototype.change = function (observer) {
        observer.next(this.currentTime);
        this.currentTime--;
        if (this.currentTime <= -1) {
            observer.complete();
        }
    };
    return TimerService;
}(__WEBPACK_IMPORTED_MODULE_3_app_core_bases_base_service__["a" /* BaseService */]));
TimerService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [])
], TimerService);

//# sourceMappingURL=timer.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/services/user.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_objects_user_info__ = __webpack_require__("../../../../../src/app/core/objects/user-info.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_input_query_in_hospital_patient__ = __webpack_require__("../../../../../src/app/protocol/server/input-query-in-hospital-patient.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_protocol_server_input_query_out_patient_info__ = __webpack_require__("../../../../../src/app/protocol/server/input-query-out-patient-info.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_protocol_server_input_query_outpatient_pay_records__ = __webpack_require__("../../../../../src/app/protocol/server/input-query-outpatient-pay-records.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_protocol_server_input_create_order__ = __webpack_require__("../../../../../src/app/protocol/server/input-create-order.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_protocol_server_input_unified_order__ = __webpack_require__("../../../../../src/app/protocol/server/input-unified-order.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_app_protocol_server_input_query_order_status__ = __webpack_require__("../../../../../src/app/protocol/server/input-query-order-status.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_app_protocol_server_server_request_service__ = __webpack_require__("../../../../../src/app/protocol/server/server-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__ = __webpack_require__("../../../../../src/app/protocol/server/server-api-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_app_protocol_server_output_query_in_hospital_patient__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-in-hospital-patient.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_app_protocol_server_output_query_out_patient_info__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-out-patient-info.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16_app_protocol_server_output_query_outpatient_pay_records__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-outpatient-pay-records.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17_app_protocol_server_output_create_order__ = __webpack_require__("../../../../../src/app/protocol/server/output-create-order.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18_app_protocol_server_output_unified_order__ = __webpack_require__("../../../../../src/app/protocol/server/output-unified-order.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19_app_protocol_server_output_query_order_status__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-order-status.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__ = __webpack_require__("../../../../../src/app/core/libs/public-library.ts");
// 封装了用户的行为
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};























var UserService = (function () {
    function UserService(serverRequestService, errorService, loadingService) {
        this.serverRequestService = serverRequestService;
        this.errorService = errorService;
        this.loadingService = loadingService;
        this.userInfo = new __WEBPACK_IMPORTED_MODULE_5_app_core_objects_user_info__["a" /* UserInfo */](); // 赋除值，因此不会为空对象
    }
    /**
     * 是否用户信息已获取
     * @returns {boolean}
     */
    UserService.prototype.hasPatientInfo = function () {
        return this.userInfo.hasPatientInfo();
    };
    /**
     * 清除用户信息
     */
    UserService.prototype.clearUserInfo = function () {
        this.userInfo.clearPatientInfo();
    };
    /**
     * 获取就诊人信息
     * @returns {OutputQueryOutPatientInfo}
     */
    UserService.prototype.getOutPatientInfo = function () {
        return this.userInfo.outputQueryOutPatientInfo;
    };
    /**
     * 获取住院病人信息
     * @returns {OutputQueryInHospitalPatient}
     */
    UserService.prototype.getInPatientInfo = function () {
        return this.userInfo.outputQueryInHospitalPatient;
    };
    // -----------------------------------------------------------------------------
    /**
     * 从服务器获取门诊病人的信息
     * @param {string} patientNum 就诊卡号
     * @returns {Observable<OutputQueryOutPatientInfo>}
     */
    UserService.prototype.getOutPatientInfoFromServer = function (patientNum) {
        var _this = this;
        this.getOutPatientInfoFromServerObservable = new __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.getOutPatientInfoFromServerSubscriber = subscriber;
        });
        var inputQueryOutPatientInfo = new __WEBPACK_IMPORTED_MODULE_7_app_protocol_server_input_query_out_patient_info__["a" /* InputQueryOutPatientInfo */]();
        inputQueryOutPatientInfo.hospital_id = __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputQueryOutPatientInfo.patient_card = patientNum;
        setTimeout(function () {
            _this.serverRequestService.queryOutPatientInfo(inputQueryOutPatientInfo)
                .subscribe(function (data) { return _this.getOutPatientInfoFromServerSuc(data); }, function (error) { return _this.getOutPatientInfoFromServerErr(error); });
        }, 200);
        return this.getOutPatientInfoFromServerObservable;
    };
    // 从服务器获取门诊病人的信息，成功
    UserService.prototype.getOutPatientInfoFromServerSuc = function (responseDataWrap) {
        if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseSuccess) {
            // save
            this.userInfo.outputQueryOutPatientInfo =
                Object.assign(new __WEBPACK_IMPORTED_MODULE_15_app_protocol_server_output_query_out_patient_info__["a" /* OutputQueryOutPatientInfo */](), responseDataWrap.responseData);
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('getOutPatientInfoFromServerSuc: ' + this.userInfo.outputQueryOutPatientInfo.real_name);
            this.getOutPatientInfoFromServerSubscriber.next(this.userInfo.outputQueryOutPatientInfo);
        }
        else if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseTokenTimeout) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getOutPatientInfoFromServerErr: [token time out]');
            this.getOutPatientInfoFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
        }
        else {
            this.getOutPatientInfoFromServerSubscriber.error(responseDataWrap.responseMessage);
        }
    };
    UserService.prototype.getOutPatientInfoFromServerErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getOutPatientInfoFromServerErr: [' + errorMsg + ']');
        this.getOutPatientInfoFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
    };
    // -----------------------------------------------------------------------------
    /**
     * 获取门诊缴费记录（未缴费用）
     * @returns {Observable<OutputQueryOutpatientPayRecords>}
     */
    UserService.prototype.getOutpatientPayRecordsFromServer = function () {
        var _this = this;
        this.getOutpatientPayRecordsFromServerObservable = new __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.getOutpatientPayRecordsFromServerSubscriber = subscriber;
        });
        var inputQueryOutpatientPayRecords = new __WEBPACK_IMPORTED_MODULE_8_app_protocol_server_input_query_outpatient_pay_records__["a" /* InputQueryOutpatientPayRecords */]();
        inputQueryOutpatientPayRecords.hospital_id = __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputQueryOutpatientPayRecords.patient_card = this.userInfo.outputQueryOutPatientInfo.patient_card;
        setTimeout(function () {
            _this.serverRequestService.queryOutpatientPayRecords(inputQueryOutpatientPayRecords)
                .subscribe(function (data) { return _this.getOutpatientPayRecordsFromServerSuc(data); }, function (error) { return _this.getOutpatientPayRecordsFromServerErr(error); });
        }, 200);
        return this.getOutpatientPayRecordsFromServerObservable;
    };
    UserService.prototype.getOutpatientPayRecordsFromServerSuc = function (responseDataWrap) {
        if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseSuccess) {
            var outpatientPayRecords = Object.assign(new __WEBPACK_IMPORTED_MODULE_16_app_protocol_server_output_query_outpatient_pay_records__["a" /* OutputQueryOutpatientPayRecords */](), responseDataWrap.responseData);
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('getOutpatientPayRecordsFromServerSuc: ' + responseDataWrap.toString());
            this.getOutpatientPayRecordsFromServerSubscriber.next(outpatientPayRecords);
        }
        else if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseTokenTimeout) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getOutpatientPayRecordsFromServerErr: [token time out]');
            this.getOutpatientPayRecordsFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
        }
        else {
            this.getOutpatientPayRecordsFromServerSubscriber.error(responseDataWrap.responseMessage);
        }
    };
    UserService.prototype.getOutpatientPayRecordsFromServerErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getOutpatientPayRecordsFromServerErr: [' + errorMsg + ']');
        this.getOutpatientPayRecordsFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
    };
    // -----------------------------------------------------------------------------
    /**
     * 缴费下单（生成预订单）
     * @param {string} totalAmt
     * @param {string} dataSrc
     * @param {string} payType
     * @returns {Observable<OutputCreateOrder>}
     */
    UserService.prototype.createOrderByServer = function (totalAmt, dataSrc, payType) {
        var _this = this;
        this.createOrderByServerObservable = new __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.createOrderByServerSubscriber = subscriber;
        });
        var inputCreateOrder = new __WEBPACK_IMPORTED_MODULE_9_app_protocol_server_input_create_order__["a" /* InputCreateOrder */]();
        if (dataSrc === __WEBPACK_IMPORTED_MODULE_20_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcInpatient) {
            inputCreateOrder.patient_id = this.userInfo.outputQueryInHospitalPatient.serial_number;
        }
        else {
            inputCreateOrder.patient_id = this.userInfo.outputQueryOutPatientInfo.patient_card;
        }
        inputCreateOrder.hospital_id = __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputCreateOrder.totle_amt = totalAmt;
        inputCreateOrder.data_src = dataSrc;
        inputCreateOrder.pay_type = payType;
        setTimeout(function () {
            _this.serverRequestService.createOrder(inputCreateOrder)
                .subscribe(function (data) { return _this.createOrderByServerSuc(data); }, function (error) { return _this.createOrderByServerErr(error); });
        }, 200);
        return this.createOrderByServerObservable;
    };
    UserService.prototype.createOrderByServerSuc = function (responseDataWrap) {
        if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseSuccess) {
            var orderInfo = Object.assign(new __WEBPACK_IMPORTED_MODULE_17_app_protocol_server_output_create_order__["a" /* OutputCreateOrder */](), responseDataWrap.responseData);
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('createOrderByServerSuc: ' + responseDataWrap.toString());
            this.createOrderByServerSubscriber.next(orderInfo);
        }
        else if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseTokenTimeout) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('createOrderByServerErr: [token time out]');
            this.createOrderByServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
        }
        else {
            this.createOrderByServerSubscriber.error(responseDataWrap.responseMessage);
        }
    };
    UserService.prototype.createOrderByServerErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('createOrderByServerErr: [' + errorMsg + ']');
        this.createOrderByServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
    };
    // -----------------------------------------------------------------------------
    /**
     * 统一支付渠道下单
     * @param {string} id
     * @param {string} tradeNo
     * @param {string} orderAmount
     * @returns {Observable<OutputUnifiedOrder>}
     */
    UserService.prototype.unifiedOrderByServer = function (id, tradeNo, orderAmount) {
        var _this = this;
        this.unifiedOrderByServerObservable = new __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.unifiedOrderByServerSubscriber = subscriber;
        });
        var inputUnifiedOrder = new __WEBPACK_IMPORTED_MODULE_10_app_protocol_server_input_unified_order__["a" /* InputUnifiedOrder */]();
        inputUnifiedOrder.hospital_id = __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputUnifiedOrder.id = id;
        inputUnifiedOrder.trade_no = tradeNo;
        inputUnifiedOrder.order_amount = orderAmount;
        setTimeout(function () {
            _this.serverRequestService.unifiedOrder(inputUnifiedOrder)
                .subscribe(function (data) { return _this.unifiedOrderByServerSuc(data); }, function (error) { return _this.unifiedOrderByServerErr(error); });
        }, 200);
        return this.unifiedOrderByServerObservable;
    };
    UserService.prototype.unifiedOrderByServerSuc = function (responseDataWrap) {
        if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseSuccess) {
            var orderInfo = Object.assign(new __WEBPACK_IMPORTED_MODULE_18_app_protocol_server_output_unified_order__["a" /* OutputUnifiedOrder */](), responseDataWrap.responseData);
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('unifiedOrderByServerSuc: ' + responseDataWrap.toString());
            this.unifiedOrderByServerSubscriber.next(orderInfo);
        }
        else if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseTokenTimeout) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('unifiedOrderByServerErr: [token time out]');
            this.unifiedOrderByServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
        }
        else {
            this.unifiedOrderByServerSubscriber.error(responseDataWrap.responseMessage);
        }
    };
    UserService.prototype.unifiedOrderByServerErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('unifiedOrderByServerErr: [' + errorMsg + ']');
        this.unifiedOrderByServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
    };
    // -----------------------------------------------------------------------------
    /**
     * 查询订单状态
     * @param {string} tradeNo
     * @returns {Observable<OutputQueryOrderStatus>}
     */
    UserService.prototype.queryOrderStatusFromServer = function (tradeNo) {
        var _this = this;
        this.queryOrderStatusFromServerObservable = new __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.queryOrderStatusFromServerSubscriber = subscriber;
        });
        var inputQueryOrderStatus = new __WEBPACK_IMPORTED_MODULE_11_app_protocol_server_input_query_order_status__["a" /* InputQueryOrderStatus */]();
        inputQueryOrderStatus.hospital_id = __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputQueryOrderStatus.trade_no = tradeNo;
        setTimeout(function () {
            _this.serverRequestService.queryOrderStatus(inputQueryOrderStatus)
                .subscribe(function (data) { return _this.queryOrderStatusFromServerSuc(data); }, function (error) { return _this.queryOrderStatusFromServerErr(error); });
        }, 200);
        return this.queryOrderStatusFromServerObservable;
    };
    UserService.prototype.queryOrderStatusFromServerSuc = function (responseDataWrap) {
        if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseSuccess) {
            var orderInfo = Object.assign(new __WEBPACK_IMPORTED_MODULE_19_app_protocol_server_output_query_order_status__["a" /* OutputQueryOrderStatus */](), responseDataWrap.responseData);
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('queryOrderStatusFromServerSuc: ' + responseDataWrap.toString());
            this.queryOrderStatusFromServerSubscriber.next(orderInfo);
        }
        else if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseTokenTimeout) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('queryOrderStatusFromServerErr: [token time out]');
            this.queryOrderStatusFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
        }
        else {
            this.queryOrderStatusFromServerSubscriber.error(responseDataWrap.responseMessage);
        }
    };
    UserService.prototype.queryOrderStatusFromServerErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('queryOrderStatusFromServerErr: [' + errorMsg + ']');
        this.queryOrderStatusFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
    };
    /**
     * 从服务器获取住院病人的信息
     * @param {string} serialNumber
     * @returns {Observable<OutputQueryInHospitalPatient>}
     */
    UserService.prototype.getInpatientInfoFromServer = function (serialNumber) {
        var _this = this;
        this.getInpatientInfoFromServerObservable = new __WEBPACK_IMPORTED_MODULE_0_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.getInpatientInfoFromServerSubscriber = subscriber;
        });
        var inputQueryInHospitalPatient = new __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_input_query_in_hospital_patient__["a" /* InputQueryInHospitalPatient */]();
        inputQueryInHospitalPatient.hospital_id = __WEBPACK_IMPORTED_MODULE_13_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.hospitalId;
        inputQueryInHospitalPatient.serial_number = serialNumber;
        setTimeout(function () {
            _this.serverRequestService.queryInHospitalPatient(inputQueryInHospitalPatient)
                .subscribe(function (data) { return _this.getInpatientInfoFromServerSuc(data); }, function (error) { return _this.getInpatientInfoFromServerErr(error); });
        }, 200);
        return this.getInpatientInfoFromServerObservable;
    };
    // 从服务器获取住院病人的信息，成功
    UserService.prototype.getInpatientInfoFromServerSuc = function (responseDataWrap) {
        if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseSuccess) {
            // save
            this.userInfo.outputQueryInHospitalPatient =
                Object.assign(new __WEBPACK_IMPORTED_MODULE_14_app_protocol_server_output_query_in_hospital_patient__["a" /* OutputQueryInHospitalPatient */](), responseDataWrap.responseData);
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('getInpatientInfoFromServerSuc: ' + this.userInfo.outputQueryInHospitalPatient.real_name);
            this.getInpatientInfoFromServerSubscriber.next(this.userInfo.outputQueryInHospitalPatient);
        }
        else if (responseDataWrap.responseCode === __WEBPACK_IMPORTED_MODULE_22_app_core_libs_public_library__["a" /* PublicLibrary */].httpResponseTokenTimeout) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getInpatientInfoFromServerErr: [token time out]');
            this.getInpatientInfoFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
        }
        else {
            this.getInpatientInfoFromServerSubscriber.error(responseDataWrap.responseMessage);
        }
    };
    UserService.prototype.getInpatientInfoFromServerErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getInpatientInfoFromServerErr: [' + errorMsg + ']');
        this.getInpatientInfoFromServerSubscriber.error(__WEBPACK_IMPORTED_MODULE_21_app_core_libs_info_library__["a" /* InfoLibrary */].networkError);
    };
    return UserService;
}());
UserService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_12_app_protocol_server_server_request_service__["a" /* ServerRequestService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_12_app_protocol_server_server_request_service__["a" /* ServerRequestService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _c || Object])
], UserService);

var _a, _b, _c;
//# sourceMappingURL=user.service.js.map

/***/ }),

/***/ "../../../../../src/app/core/utility/app-error-handler.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppErrorHandler; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
// 全局错误处理

var AppErrorHandler = (function () {
    function AppErrorHandler() {
    }
    AppErrorHandler.prototype.handleError = function (error) {
        console.error('AppErrorHandler:', error);
        console.error(error.stack);
        __WEBPACK_IMPORTED_MODULE_0_app_core_services_log_service__["a" /* LogService */].error(error.toString() + '. Detail:');
        __WEBPACK_IMPORTED_MODULE_0_app_core_services_log_service__["a" /* LogService */].error(error.stack);
    };
    return AppErrorHandler;
}());

//# sourceMappingURL=app-error-handler.js.map

/***/ }),

/***/ "../../../../../src/app/home/banner/banner.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*轮播图*/\r\n.content {\r\n  width: 100%;\r\n  height: 100%;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/home/banner/banner.component.html":
/***/ (function(module, exports) {

module.exports = "<!--轮播图-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo></app-main-header-logo>\r\n\r\n  <div class=\"content\" (click)=\"goToMainMenu()\">\r\n    <swiper [(config)]=\"config\">\r\n      <img src=\"assets/images/banner1.png\">\r\n      <img src=\"assets/images/banner2.png\">\r\n      <img src=\"assets/images/banner3.png\">\r\n    </swiper>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/home/banner/banner.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BannerComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 轮播图


var BannerComponent = (function () {
    function BannerComponent(systemService) {
        this.systemService = systemService;
        this.config = {
            direction: 'horizontal',
            slidesPerView: 'auto',
            autoplay: 3000,
            loop: true
        };
    }
    BannerComponent.prototype.ngOnInit = function () {
    };
    BannerComponent.prototype.goToMainMenu = function () {
        this.systemService.backMainMenu();
    };
    return BannerComponent;
}());
BannerComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-banner',
        template: __webpack_require__("../../../../../src/app/home/banner/banner.component.html"),
        styles: [__webpack_require__("../../../../../src/app/home/banner/banner.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_system_service__["a" /* SystemService */]) === "function" && _a || Object])
], BannerComponent);

var _a;
//# sourceMappingURL=banner.component.js.map

/***/ }),

/***/ "../../../../../src/app/home/home.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HomeModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ngx_swiper_wrapper__ = __webpack_require__("../../../../ngx-swiper-wrapper/dist/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ngx_swiper_wrapper___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_ngx_swiper_wrapper__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__loading_loading_component__ = __webpack_require__("../../../../../src/app/home/loading/loading.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__read_card_read_card_component__ = __webpack_require__("../../../../../src/app/home/read-card/read-card.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__main_menu_main_menu_component__ = __webpack_require__("../../../../../src/app/home/main-menu/main-menu.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__main_menu_item_main_menu_item_component__ = __webpack_require__("../../../../../src/app/home/main-menu-item/main-menu-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__outpatient_info_outpatient_info_component__ = __webpack_require__("../../../../../src/app/home/outpatient-info/outpatient-info.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__banner_banner_component__ = __webpack_require__("../../../../../src/app/home/banner/banner.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};












var HomeModule = (function () {
    function HomeModule() {
    }
    return HomeModule;
}());
HomeModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["h" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_4__angular_material__["b" /* MatButtonModule */],
            __WEBPACK_IMPORTED_MODULE_4__angular_material__["c" /* MatCheckboxModule */],
            __WEBPACK_IMPORTED_MODULE_4__angular_material__["f" /* MatDialogModule */],
            __WEBPACK_IMPORTED_MODULE_4__angular_material__["i" /* MatRadioModule */],
            __WEBPACK_IMPORTED_MODULE_5_ngx_swiper_wrapper__["SwiperModule"]
        ],
        declarations: [
            __WEBPACK_IMPORTED_MODULE_6__loading_loading_component__["a" /* LoadingComponent */],
            __WEBPACK_IMPORTED_MODULE_7__read_card_read_card_component__["a" /* ReadCardComponent */],
            __WEBPACK_IMPORTED_MODULE_8__main_menu_main_menu_component__["a" /* MainMenuComponent */],
            __WEBPACK_IMPORTED_MODULE_9__main_menu_item_main_menu_item_component__["a" /* MainMenuItemComponent */],
            __WEBPACK_IMPORTED_MODULE_10__outpatient_info_outpatient_info_component__["a" /* OutpatientInfoComponent */],
            __WEBPACK_IMPORTED_MODULE_11__banner_banner_component__["a" /* BannerComponent */]
        ],
        exports: []
    })
], HomeModule);

//# sourceMappingURL=home.module.js.map

/***/ }),

/***/ "../../../../../src/app/home/loading/loading.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*程序初始化页面*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n}\r\n\r\n.content > div:first-child {\r\n  margin-top: -0.7812rem;\r\n}\r\n\r\n.loading {\r\n  height: 0.3906rem;\r\n  margin: 0.3906rem auto 0;\r\n}\r\n\r\n.loading span {\r\n  display: inline-block;\r\n  width: 0.3906rem;\r\n  height: 100%;\r\n  margin-right: 0.0781rem;\r\n  border-radius: 50%;\r\n  background: #01A9F2;\r\n  -webkit-animation: load 1.2s ease infinite;\r\n}\r\n\r\n.loading span:last-child {\r\n  margin-right: 0;\r\n}\r\n\r\n@-webkit-keyframes load {\r\n  0% {\r\n    opacity: 1;\r\n  }\r\n  100% {\r\n    opacity: 0;\r\n  }\r\n}\r\n\r\n.loading span:nth-child(1) {\r\n  -webkit-animation-delay: 0.15s;\r\n}\r\n\r\n.loading span:nth-child(2) {\r\n  -webkit-animation-delay: 0.3s;\r\n}\r\n\r\n.loading span:nth-child(3) {\r\n  -webkit-animation-delay: 0.45s;\r\n}\r\n\r\n.loading span:nth-child(4) {\r\n  -webkit-animation-delay: 0.6s;\r\n}\r\n\r\n.loading span:nth-child(5) {\r\n  -webkit-animation-delay: 0.75s;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/home/loading/loading.component.html":
/***/ (function(module, exports) {

module.exports = "<!--程序初始化页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <div>正在初始化，请稍后…</div>\r\n    <div class=\"loading\">\r\n      <span></span>\r\n      <span></span>\r\n      <span></span>\r\n      <span></span>\r\n      <span></span>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/home/loading/loading.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoadingComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 程序初始化页面

var LoadingComponent = (function () {
    function LoadingComponent() {
    }
    LoadingComponent.prototype.ngOnInit = function () {
    };
    return LoadingComponent;
}());
LoadingComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-loading',
        template: __webpack_require__("../../../../../src/app/home/loading/loading.component.html"),
        styles: [__webpack_require__("../../../../../src/app/home/loading/loading.component.css")]
    }),
    __metadata("design:paramtypes", [])
], LoadingComponent);

//# sourceMappingURL=loading.component.js.map

/***/ }),

/***/ "../../../../../src/app/home/main-menu-item/main-menu-item.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*主菜单按钮*/\r\n.main-menu-item {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: row;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  width: 2.8rem;\r\n  height: 2.8rem;\r\n  color: #fff;\r\n  font-size: 0.2812rem;\r\n}\r\n\r\n.main-menu-item .icon {\r\n  width: 1.4062rem;\r\n  height: 1.4062rem;\r\n  margin-bottom: 0.3047rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/home/main-menu-item/main-menu-item.component.html":
/***/ (function(module, exports) {

module.exports = "<!--主菜单按钮-->\r\n<button mat-button class=\"main-menu-item\" (click)=\"goToDetail()\">\r\n  <img src=\"{{currentItemData.imageURL}}\" class=\"icon\">\r\n  <div>{{currentItemData.title}}</div>\r\n</button>\r\n"

/***/ }),

/***/ "../../../../../src/app/home/main-menu-item/main-menu-item.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainMenuItemComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_cover_item_library__ = __webpack_require__("../../../../../src/app/core/libs/cover-item-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 主菜单按钮



var MainMenuItemComponent = (function () {
    function MainMenuItemComponent(routerService) {
        this.routerService = routerService;
    }
    MainMenuItemComponent.prototype.ngOnInit = function () {
        // get item data
        this.currentItemData = __WEBPACK_IMPORTED_MODULE_1_app_core_libs_cover_item_library__["a" /* CoverItemLibrary */].itemData()[(this.itemType - 1)];
    };
    MainMenuItemComponent.prototype.goToDetail = function () {
        this.routerService.goTo([this.currentItemData.path]);
    };
    return MainMenuItemComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Number)
], MainMenuItemComponent.prototype, "itemType", void 0);
MainMenuItemComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-main-menu-item',
        template: __webpack_require__("../../../../../src/app/home/main-menu-item/main-menu-item.component.html"),
        styles: [__webpack_require__("../../../../../src/app/home/main-menu-item/main-menu-item.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object])
], MainMenuItemComponent);

var _a;
//# sourceMappingURL=main-menu-item.component.js.map

/***/ }),

/***/ "../../../../../src/app/home/main-menu/main-menu.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*主菜单，功能选择页面*/\r\n.content {\r\n  width: 100%;\r\n  height: 100%;\r\n  background: url(" + __webpack_require__("../../../../../src/assets/images/bg.png") + ") no-repeat;\r\n  background-size: 100% 100%;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: row;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/home/main-menu/main-menu.component.html":
/***/ (function(module, exports) {

module.exports = "<!--主菜单，功能选择页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo [returnBtnShow]=\"false\"></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <app-main-menu-item [itemType]=\"1\"></app-main-menu-item>\r\n    <app-main-menu-item [itemType]=\"2\"></app-main-menu-item>\r\n    <app-main-menu-item [itemType]=\"3\"></app-main-menu-item>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/home/main-menu/main-menu.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainMenuComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_back_service__ = __webpack_require__("../../../../../src/app/core/services/back.service.ts");
// 主菜单，功能选择页面
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MainMenuComponent = (function () {
    function MainMenuComponent(backService) {
        this.backService = backService;
    }
    MainMenuComponent.prototype.ngOnInit = function () {
        // 开启后台服务
        this.backService.start();
    };
    MainMenuComponent.prototype.ngOnDestroy = function () {
        // 关闭后台服务
        this.backService.stop();
    };
    return MainMenuComponent;
}());
MainMenuComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-main-menu',
        template: __webpack_require__("../../../../../src/app/home/main-menu/main-menu.component.html"),
        styles: [__webpack_require__("../../../../../src/app/home/main-menu/main-menu.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_back_service__["a" /* BackService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_back_service__["a" /* BackService */]) === "function" && _a || Object])
], MainMenuComponent);

var _a;
//# sourceMappingURL=main-menu.component.js.map

/***/ }),

/***/ "../../../../../src/app/home/outpatient-info/outpatient-info.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*门诊病人信息展示页*/\r\n.outer-box {\r\n  position: absolute;\r\n  background-color: rgba(0, 0, 0, 0.4);\r\n  width: 100%;\r\n  height: 100%;\r\n  bottom: 0;\r\n}\r\n\r\n.info-box {\r\n  box-sizing: border-box;\r\n  width: 100%;\r\n  height: 5.7812rem;\r\n  padding: 1.0156rem 2.3438rem;\r\n  position: absolute;\r\n  bottom: 0;\r\n  background-color: #fff;\r\n}\r\n\r\n.tips {\r\n  font-size: 0.3906rem;\r\n  color: #333;\r\n  margin-bottom: 0.2344rem;\r\n}\r\n\r\n.info {\r\n  line-height: 0.4688rem;\r\n  font-size: 0.2812rem;\r\n  color: #666;\r\n  margin-bottom: 0.3906rem;\r\n}\r\n\r\n.button-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.button-box button {\r\n  width: 2.3438rem;\r\n  height: 0.625rem;\r\n  font-size: 0.3438rem;\r\n  border-radius: 0.0625rem;\r\n  box-shadow: none;\r\n}\r\n\r\n.confirm-btn {\r\n  color: #fff;\r\n  background-color: #01a9f2;\r\n}\r\n\r\n.cancel-btn {\r\n  color: #00aaf2;\r\n  border: 1px solid #00aaf2;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/home/outpatient-info/outpatient-info.component.html":
/***/ (function(module, exports) {

module.exports = "<!--门诊病人信息展示页-->\r\n<div class=\"outer-box\">\r\n  <div class=\"info-box\">\r\n    <div class=\"tips\">请确认就诊人信息</div>\r\n    <div class=\"info\">\r\n      <div>就诊人：&emsp;&ensp;{{outpatientInfo.real_name}}</div>\r\n      <div>就诊卡号：&ensp;{{outpatientInfo.patient_card}}</div>\r\n    </div>\r\n    <div class=\"button-box\">\r\n      <button mat-raised-button class=\"cancel-btn\" (click)=\"cancelTap()\">取消</button>\r\n      <button mat-raised-button class=\"confirm-btn\" (click)=\"confirmTap()\">确认</button>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/home/outpatient-info/outpatient-info.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutpatientInfoComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_protocol_server_output_query_out_patient_info__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-out-patient-info.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 门诊病人信息展示页





var OutpatientInfoComponent = (function () {
    function OutpatientInfoComponent(routerService, systemService, userService) {
        this.routerService = routerService;
        this.systemService = systemService;
        this.userService = userService;
        this.outpatientInfoShowChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        this.outpatientInfo = new __WEBPACK_IMPORTED_MODULE_4_app_protocol_server_output_query_out_patient_info__["a" /* OutputQueryOutPatientInfo */]();
    }
    OutpatientInfoComponent.prototype.ngOnInit = function () {
        this.outpatientInfo = this.userService.getOutPatientInfo();
    };
    // 确认按钮点击
    OutpatientInfoComponent.prototype.confirmTap = function () {
        this.routerService.goTo([this.nextPath]);
    };
    // 虚拟键盘取消按钮点击
    OutpatientInfoComponent.prototype.cancelTap = function () {
        this.outpatientInfoShow = false;
        this.systemService.backMainMenu();
        this.outpatientInfoShowChange.emit(this.outpatientInfoShow);
    };
    return OutpatientInfoComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], OutpatientInfoComponent.prototype, "outpatientInfoShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", String)
], OutpatientInfoComponent.prototype, "nextPath", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], OutpatientInfoComponent.prototype, "outpatientInfoShowChange", void 0);
OutpatientInfoComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-outpatient-info',
        template: __webpack_require__("../../../../../src/app/home/outpatient-info/outpatient-info.component.html"),
        styles: [__webpack_require__("../../../../../src/app/home/outpatient-info/outpatient-info.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__["a" /* SystemService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_user_service__["a" /* UserService */]) === "function" && _c || Object])
], OutpatientInfoComponent);

var _a, _b, _c;
//# sourceMappingURL=outpatient-info.component.js.map

/***/ }),

/***/ "../../../../../src/app/home/read-card/read-card.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*刷卡页面*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n  text-align: center;\r\n  /*display: flex;*/\r\n  /*align-items: center;*/\r\n  /*justify-content: center;*/\r\n}\r\n\r\n.content .tips {\r\n\t/*text-align: center;*/\r\n\tcolor: #333;\r\n\tfont-size: 0.3594rem;\r\n\tmargin: 0.2734rem auto;\r\n}\r\n\r\n.content img {\r\n\twidth: 7.0312rem;\r\n\theight: 3.9062rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/home/read-card/read-card.component.html":
/***/ (function(module, exports) {

module.exports = "<!--刷卡页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo [returnBtnShow]=\"true\" [timerShow]=\"true\" [timerMax]=\"60\"></app-main-header-logo>\r\n  <div class=\"content\">\r\n    <div class=\"tips\">磁条朝内，由上而下刷卡</div>\r\n    <img src=\"assets/images/pic_minipay.png\">\r\n    <button mat-raised-button *ngIf=\"showReadCardButton\" (click)=\"testReadCard()\">测试读卡</button>\r\n  </div>\r\n</div>\r\n\r\n<app-outpatient-info *ngIf=\"outpatientInfoShow\" [(outpatientInfoShow)]=\"outpatientInfoShow\" [nextPath]=\"nextPath\"></app-outpatient-info>\r\n"

/***/ }),

/***/ "../../../../../src/app/home/read-card/read-card.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReadCardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_protocol_shell_shell_request_service__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-request.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_protocol_shell_shell_api_library__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-api-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_public_device_parameter__ = __webpack_require__("../../../../../src/app/core/public/device-parameter.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 刷卡页面













var ReadCardComponent = (function () {
    function ReadCardComponent(activatedRoute, shellRequestService, userService, errorService, systemService, loadingService) {
        this.activatedRoute = activatedRoute;
        this.shellRequestService = shellRequestService;
        this.userService = userService;
        this.errorService = errorService;
        this.systemService = systemService;
        this.loadingService = loadingService;
        this.showReadCardButton = __WEBPACK_IMPORTED_MODULE_10_app_core_public_device_parameter__["a" /* DeviceParameter */].isReadCardDebugMode; // 模拟读卡器时使用
        this.outpatientInfoShow = false; // 用户信息提示，为了解决读卡器问题
    }
    ReadCardComponent.prototype.ngOnInit = function () {
        this.getParameterFromRoute();
        this.waitReadCardResult();
    };
    ReadCardComponent.prototype.ngOnDestroy = function () {
        if (this.subscriptionGetParameter) {
            this.subscriptionGetParameter.unsubscribe();
        }
        if (this.web2ShellGetReadCardResultSubscription) {
            this.web2ShellGetReadCardResultSubscription.unsubscribe();
        }
    };
    // 触发模拟读卡器的功能
    ReadCardComponent.prototype.testReadCard = function () {
        this.shellRequestService.shell2WebTest(__WEBPACK_IMPORTED_MODULE_8_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readMagneticCard);
    };
    // 解析路由参数，获取下一步要去的位置
    ReadCardComponent.prototype.getParameterFromRoute = function () {
        var _this = this;
        this.subscriptionGetParameter = this.activatedRoute.params.subscribe(function (params) {
            _this.nextPath = params[__WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].readCardParameter];
            __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('nextPath: ' + _this.nextPath);
        });
    };
    // 等待读卡结果
    ReadCardComponent.prototype.waitReadCardResult = function () {
        var _this = this;
        this.web2ShellGetReadCardResultSubscription = this.shellRequestService.web2ShellReadMagneticCard()
            .subscribe(function (data) { return _this.getReadCardResultSuc(data); }, function (error) { return _this.getReadCardResultErr(error); });
    };
    // 等待读卡结果，成功
    ReadCardComponent.prototype.getReadCardResultSuc = function (outputReadMagneticCard) {
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('get read card result suc: [' + outputReadMagneticCard.cardID + ']');
        this.tempCardID = outputReadMagneticCard.cardID;
        this.getPatientInfo(this.tempCardID);
        this.web2ShellGetReadCardResultSubscription.unsubscribe();
    };
    // 等待读卡结果，失败
    ReadCardComponent.prototype.getReadCardResultErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].error('get read card result fail: [' + errorMsg + ']');
        this.systemService.backMainMenu();
    };
    // 从服务器获取用户信息
    ReadCardComponent.prototype.getPatientInfo = function (inpatientNum) {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getOutPatientInfoFromServer(inpatientNum).subscribe(function (data) { return _this.getPatientInfoSuc(data); }, function (error) { return _this.getPatientInfoErr(error); });
    };
    // 从服务器获取用户信息，成功
    ReadCardComponent.prototype.getPatientInfoSuc = function (outputQueryOutPatientInfo) {
        this.loadingService.hiddenLoading();
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('getPatientInfoSuc: ' + outputQueryOutPatientInfo);
        this.outpatientInfoShow = true;
    };
    // 从服务器获取用户信息，失败
    ReadCardComponent.prototype.getPatientInfoErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].error('getPatientInfoErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_12_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_11_app_core_objects_info_message__["a" /* InfoMessage */](__WEBPACK_IMPORTED_MODULE_12_app_core_libs_info_library__["a" /* InfoLibrary */].outpatientNumError);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    return ReadCardComponent;
}());
ReadCardComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-read-card',
        template: __webpack_require__("../../../../../src/app/home/read-card/read-card.component.html"),
        styles: [__webpack_require__("../../../../../src/app/home/read-card/read-card.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_7_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7_app_protocol_shell_shell_request_service__["a" /* ShellRequestService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_9_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_9_app_core_services_user_service__["a" /* UserService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_system_service__["a" /* SystemService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _f || Object])
], ReadCardComponent);

var _a, _b, _c, _d, _e, _f;
//# sourceMappingURL=read-card.component.js.map

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient-info/inpatient-info.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*住院病人信息展示页*/\r\n.outer-box {\r\n  position: absolute;\r\n  background-color: rgba(0, 0, 0, 0.4);\r\n  width: 100%;\r\n  height: 100%;\r\n  bottom: 0;\r\n}\r\n\r\n.info-box {\r\n  box-sizing: border-box;\r\n  width: 100%;\r\n  height: 5.7812rem;\r\n  padding: 1.0156rem 2.3438rem;\r\n  position: absolute;\r\n  bottom: 0;\r\n  background-color: #fff;\r\n}\r\n\r\n.tips {\r\n  font-size: 0.3906rem;\r\n  color: #333;\r\n  margin-bottom: 0.2344rem;\r\n}\r\n\r\n.info {\r\n  line-height: 0.4688rem;\r\n  font-size: 0.2812rem;\r\n  color: #666;\r\n  margin-bottom: 0.3906rem;\r\n}\r\n\r\n.button-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.button-box button {\r\n  width: 2.3438rem;\r\n  height: 0.625rem;\r\n  font-size: 0.3438rem;\r\n  border-radius: 0.0625rem;\r\n  box-shadow: none;\r\n}\r\n\r\n.confirm-btn {\r\n  color: #fff;\r\n  background-color: #01a9f2;\r\n}\r\n\r\n.cancel-btn {\r\n  color: #00aaf2;\r\n  border: 1px solid #00aaf2;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient-info/inpatient-info.component.html":
/***/ (function(module, exports) {

module.exports = "<!--住院病人信息展示页-->\r\n<div class=\"outer-box\">\r\n  <div class=\"info-box\">\r\n    <div class=\"tips\">请确认就诊人信息</div>\r\n    <div class=\"info\">\r\n      <div>就诊人：&ensp;{{inpatientInfo.real_name}}</div>\r\n      <div>住院号：&ensp;{{inpatientInfo.serial_number}}</div>\r\n    </div>\r\n    <div class=\"button-box\">\r\n      <button mat-raised-button class=\"cancel-btn\" (click)=\"cancelTap()\">取消</button>\r\n      <button mat-raised-button class=\"confirm-btn\" (click)=\"confirmTap()\">确认</button>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient-info/inpatient-info.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InpatientInfoComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_protocol_server_output_query_in_hospital_patient__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-in-hospital-patient.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 住院病人信息展示页






var InpatientInfoComponent = (function () {
    function InpatientInfoComponent(routerService, userService) {
        this.routerService = routerService;
        this.userService = userService;
        this.inpatientInfoShowChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        this.inpatientInfo = new __WEBPACK_IMPORTED_MODULE_5_app_protocol_server_output_query_in_hospital_patient__["a" /* OutputQueryInHospitalPatient */]();
    }
    InpatientInfoComponent.prototype.ngOnInit = function () {
        this.inpatientInfo = this.userService.getInPatientInfo();
    };
    // 确认按钮点击
    InpatientInfoComponent.prototype.confirmTap = function () {
        var dataSrcObj = { dataSrc: __WEBPACK_IMPORTED_MODULE_3_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcInpatient };
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentAmountSelect, dataSrcObj]);
    };
    // 虚拟键盘取消按钮点击
    InpatientInfoComponent.prototype.cancelTap = function () {
        this.inpatientInfoShow = false;
        this.inpatientInfoShowChange.emit(this.inpatientInfoShow);
    };
    return InpatientInfoComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], InpatientInfoComponent.prototype, "inpatientInfoShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], InpatientInfoComponent.prototype, "inpatientInfoShowChange", void 0);
InpatientInfoComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-inpatient-info',
        template: __webpack_require__("../../../../../src/app/inpatient/inpatient-info/inpatient-info.component.html"),
        styles: [__webpack_require__("../../../../../src/app/inpatient/inpatient-info/inpatient-info.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object])
], InpatientInfoComponent);

var _a, _b;
//# sourceMappingURL=inpatient-info.component.js.map

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*住院登录*/\r\n.content {\r\n  padding: 0.1953rem;\r\n  text-align: center;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.tips {\r\n  color: #333;\r\n  font-size: 0.3594rem;\r\n  margin: -0.3906rem auto 0.3906rem;\r\n}\r\n\r\n.serial-number {\r\n  width: 7.0312rem;\r\n  height: 0.9375rem;\r\n  padding: 0 0.3906rem;\r\n  color: #999;\r\n  font-size: 0.2812rem;\r\n  border-radius: 0.0781rem;\r\n  text-align: left;\r\n  box-shadow: 0 0 15px rgba(0,170,242,0.8);\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"common-page\">\r\n  <app-main-header [infoHidden]=\"true\" [timerShow]=\"true\" [timerMax]=\"60\"></app-main-header>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"tips\">请输入住院号</div>\r\n    <button mat-raised-button class=\"serial-number\" (click)=\"openVirtualKeyboard()\">请输入住院号</button>\r\n  </div>\r\n</div>\r\n\r\n<app-virtual-keyboard *ngIf=\"virtualKeyboardShow\" [(virtualKeyboardShow)]=\"virtualKeyboardShow\" (getInpatientInfo)=\"getInpatientInfo($event)\"></app-virtual-keyboard>\r\n<app-inpatient-info *ngIf=\"inpatientInfoShow\" [(inpatientInfoShow)]=\"inpatientInfoShow\"></app-inpatient-info>\r\n"

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InpatientLoginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_objects_router_info__ = __webpack_require__("../../../../../src/app/core/objects/router-info.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 住院登录










var InpatientLoginComponent = (function () {
    function InpatientLoginComponent(userService, errorService, systemService, loadingService) {
        this.userService = userService;
        this.errorService = errorService;
        this.systemService = systemService;
        this.loadingService = loadingService;
        // 虚拟键盘显示状态
        this.virtualKeyboardShow = false;
        // 住院信息显示状态
        this.inpatientInfoShow = false;
    }
    InpatientLoginComponent.prototype.ngOnInit = function () {
    };
    // 打开虚拟键盘
    InpatientLoginComponent.prototype.openVirtualKeyboard = function () {
        this.virtualKeyboardShow = true;
    };
    // 展示住院信息
    InpatientLoginComponent.prototype.openInpatientInfo = function () {
        this.inpatientInfoShow = true;
    };
    // 从服务器获取住院病人信息
    InpatientLoginComponent.prototype.getInpatientInfo = function (serialNumber) {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getInpatientInfoFromServer(serialNumber).subscribe(function (data) { return _this.getInpatientInfoSuc(data); }, function (error) { return _this.getInpatientInfoErr(error); });
    };
    // 从服务器获取住院病人信息，成功
    InpatientLoginComponent.prototype.getInpatientInfoSuc = function (outputQueryInHospitalPatient) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('getInpatientInfoSuc: ' + outputQueryInHospitalPatient);
        this.openInpatientInfo();
        this.loadingService.hiddenLoading();
    };
    // 从服务器获取住院病人信息，失败
    InpatientLoginComponent.prototype.getInpatientInfoErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getInpatientInfoErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */](__WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__["a" /* InfoLibrary */].inpatientNumError);
            infoMessage.routerInfo = new __WEBPACK_IMPORTED_MODULE_7_app_core_objects_router_info__["a" /* RouterInfo */]([__WEBPACK_IMPORTED_MODULE_5_app_core_libs_path_library__["a" /* PathLibrary */].inpatientLogin], null);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    return InpatientLoginComponent;
}());
InpatientLoginComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-inpatient-login',
        template: __webpack_require__("../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.html"),
        styles: [__webpack_require__("../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_user_service__["a" /* UserService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__["a" /* SystemService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _d || Object])
], InpatientLoginComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=inpatient-login.component.js.map

/***/ }),

/***/ "../../../../../src/app/inpatient/inpatient.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InpatientModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__techiediaries_ngx_qrcode__ = __webpack_require__("../../../../@techiediaries/ngx-qrcode/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ngx_barcode__ = __webpack_require__("../../../../ngx-barcode/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__inpatient_login_inpatient_login_component__ = __webpack_require__("../../../../../src/app/inpatient/inpatient-login/inpatient-login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__inpatient_info_inpatient_info_component__ = __webpack_require__("../../../../../src/app/inpatient/inpatient-info/inpatient-info.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
// 住院模块








var InpatientModule = (function () {
    function InpatientModule() {
    }
    return InpatientModule;
}());
InpatientModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"],
            __WEBPACK_IMPORTED_MODULE_2__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["b" /* MatButtonModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["c" /* MatCheckboxModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["i" /* MatRadioModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["f" /* MatDialogModule */],
            __WEBPACK_IMPORTED_MODULE_4__techiediaries_ngx_qrcode__["a" /* NgxQRCodeModule */],
            __WEBPACK_IMPORTED_MODULE_5_ngx_barcode__["a" /* NgxBarcodeModule */]
        ],
        declarations: [
            __WEBPACK_IMPORTED_MODULE_6__inpatient_login_inpatient_login_component__["a" /* InpatientLoginComponent */],
            __WEBPACK_IMPORTED_MODULE_7__inpatient_info_inpatient_info_component__["a" /* InpatientInfoComponent */]
        ],
        entryComponents: []
    })
], InpatientModule);

//# sourceMappingURL=inpatient.module.js.map

/***/ }),

/***/ "../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*门诊缴费记录*/\r\n.content {\r\n  padding: 0.1953rem;\r\n}\r\n\r\n.total-amount {\r\n  font-size: 0.2812rem;\r\n  color: #333;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  /*justify-content: center;*/\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.total-amount > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  width: 4.75rem;\r\n}\r\n\r\n.total-amount img {\r\n  width: 0.3438rem;\r\n  height: 0.3438rem;\r\n  margin-right: 0.1953rem;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n  margin-left: 0.2344rem;\r\n  width: 1.85rem;\r\n  display: inline-block;\r\n  text-align: left;\r\n}\r\n\r\n.tips {\r\n  width: 1.8516rem;\r\n  height: 0.3125rem;\r\n  line-height: 0.3125rem;\r\n  font-size: 0.2188rem;\r\n  color: #ca9951;\r\n  background-color: #fdf8e0;\r\n  margin: 0.0781rem 0 0 0.4297rem;\r\n  border-radius: 0.0312rem;\r\n  text-align: center;\r\n}\r\n\r\n/*账单*/\r\n.bill-list {\r\n  margin-top: 0.1953rem;\r\n  overflow-y: auto;\r\n  height: 3.0156rem;\r\n}\r\n\r\n.bill-item {\r\n  margin-bottom: 0.1562rem;\r\n  background-color: #fff;\r\n  padding: 0.1562rem 0.24rem;\r\n}\r\n\r\n.bill-info {\r\n  font-size: 0.2812rem;\r\n  padding: 0 0.16rem;\r\n  color: #333;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.bill-date {\r\n  line-height: 0.2812rem;\r\n  font-size: 0.2344rem;\r\n  color: #999;\r\n  margin-top: 0.0781rem;\r\n}\r\n\r\n.bill-info img {\r\n  width: 0.2344rem;\r\n  height: 0.2344rem;\r\n}\r\n\r\n.bill-detail {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n  padding: 0 0.1562rem;\r\n}\r\n\r\n.bill-detail > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.bill-detail > div:not(:last-child) {\r\n  margin-bottom: 0.0781rem;\r\n}\r\n\r\n.bill-detail .number {\r\n  color: #999;\r\n}\r\n\r\n.bill-detail span:not(:last-child) {\r\n  margin-right: 0.3906rem;\r\n}\r\n\r\n.bill-amount {\r\n  width: 2.14rem;\r\n  text-align: left;\r\n}\r\n\r\n.empty-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n}\r\n\r\n.empty-box img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.3906rem auto;\r\n}\r\n\r\n/*展开时追加样式*/\r\n.bill-item.active {\r\n  box-shadow: 0 0.0469rem 0.1562rem #ddd;\r\n}\r\n\r\n.bill-item.active .bill-info {\r\n  padding-bottom: 0.1562rem;\r\n}\r\n\r\n.bill-item.active .bill-detail {\r\n  padding-top: 20px;\r\n  border-top: 1px dashed #ddd;\r\n}\r\n\r\n.footer {\r\n  padding: 0 0.1562rem;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: end;\r\n      -ms-flex-pack: end;\r\n          justify-content: flex-end;\r\n}\r\n\r\n.footer button {\r\n  width: 1.4688rem;\r\n  height: 0.625rem;\r\n  border-radius: 0.0625rem;\r\n  font-size: 0.3438rem;\r\n  color: #fff;\r\n  background-color: #01a9f2;\r\n  box-shadow: none;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.html":
/***/ (function(module, exports) {

module.exports = "<!--门诊缴费记录-->\r\n<div class=\"common-page\">\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"120\"></app-main-header>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"total-amount\">\r\n      <div>\r\n        <img src=\"assets/images/icon_balance.png\">\r\n        <div>\r\n          <span>一卡通余额</span>\r\n          <span class=\"money\">¥ {{outpatientInfo.balance}}</span>\r\n        </div>\r\n      </div>\r\n      <div *ngIf=\"!billListEmpty\">\r\n        <img src=\"assets/images/icon_money.png\">\r\n        <div>\r\n          <span>门诊费用合计</span>\r\n          <span class=\"money\">¥ {{billAmount}}</span>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"tips\" *ngIf=\"!billListEmpty&&tipsShow\">余额不足，请充值</div>\r\n    <div class=\"bill-list\" *ngIf=\"!billListEmpty\">\r\n      <div class=\"bill-item\" *ngFor=\"let item of outputQueryOutpatientPayRecords.list\">\r\n        <div class=\"bill-info\">\r\n          <div>\r\n            <div>{{item.fee_type}}</div>\r\n            <div class=\"bill-date\">{{item.fee_date}}</div>\r\n          </div>\r\n          <div>\r\n            <span>金额</span>\r\n            <span class=\"money\">¥ {{item.order_amount}}</span>\r\n            <!--<img src=\"assets/images/arrow_down.png\">-->\r\n          </div>\r\n        </div>\r\n      </div>\r\n      <!--<div class=\"bill-item active\">-->\r\n        <!--<div class=\"bill-info\">-->\r\n          <!--<div>-->\r\n            <!--<div>药费</div>-->\r\n            <!--<div class=\"bill-date\">2017-10-10 08:10:26</div>-->\r\n          <!--</div>-->\r\n          <!--<div>-->\r\n            <!--<span>金额</span>-->\r\n            <!--<span class=\"money\">¥ 100.00</span>-->\r\n            <!--&lt;!&ndash;<img src=\"assets/images/arrow_up.png\">&ndash;&gt;-->\r\n          <!--</div>-->\r\n        <!--</div>-->\r\n        <!--<div class=\"bill-detail\">-->\r\n          <!--<div>-->\r\n            <!--<div>-->\r\n              <!--<span>阿莫西林胶囊</span>-->\r\n              <!--<span>单价 ¥ 10</span>-->\r\n              <!--<span class=\"number\">X 2</span>-->\r\n            <!--</div>-->\r\n            <!--<span class=\"bill-amount\">¥ 20.00</span>-->\r\n          <!--</div>-->\r\n          <!--<div>-->\r\n            <!--<div>-->\r\n              <!--<span>阿莫西林胶囊</span>-->\r\n              <!--<span>单价 ¥ 10</span>-->\r\n              <!--<span class=\"number\">X 2</span>-->\r\n            <!--</div>-->\r\n            <!--<span class=\"bill-amount\">¥ 20.00</span>-->\r\n          <!--</div>-->\r\n        <!--</div>-->\r\n      <!--</div>-->\r\n    </div>\r\n    <div *ngIf=\"billListEmpty\" class=\"empty-box\">\r\n      <img src=\"assets/images/icon_blank.png\">\r\n      暂无您的门诊费用记录\r\n    </div>\r\n  </div>\r\n\r\n  <div class=\"footer\" *ngIf=\"tipsShow\">\r\n    <button mat-raised-button (click)=\"goToPaymentAmountSelect()\">去充值</button>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutpatientPaymentRecordComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_output_query_outpatient_pay_records__ = __webpack_require__("../../../../../src/app/protocol/server/output-query-outpatient-pay-records.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 门诊缴费记录











var OutpatientPaymentRecordComponent = (function () {
    function OutpatientPaymentRecordComponent(routerService, userService, systemService, errorService, loadingService) {
        this.routerService = routerService;
        this.userService = userService;
        this.systemService = systemService;
        this.errorService = errorService;
        this.loadingService = loadingService;
        this.outputQueryOutpatientPayRecords = new __WEBPACK_IMPORTED_MODULE_6_app_protocol_server_output_query_outpatient_pay_records__["a" /* OutputQueryOutpatientPayRecords */](); // 门诊缴费记录（未缴费用）
        this.billAmount = ''; // 费用账单总金额
        this.debt = ''; // 需要补缴的金额
        this.tipsShow = false;
        this.billListEmpty = false;
    }
    OutpatientPaymentRecordComponent.prototype.ngOnInit = function () {
        this.outpatientInfo = this.userService.getOutPatientInfo();
        this.getOutpatientPayRecords();
    };
    OutpatientPaymentRecordComponent.prototype.goToPaymentAmountSelect = function () {
        var debtObj = { debt: this.debt };
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentAmountSelect, debtObj]);
    };
    // 从服务器获取门诊缴费记录（未缴费用）
    OutpatientPaymentRecordComponent.prototype.getOutpatientPayRecords = function () {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getOutpatientPayRecordsFromServer().subscribe(function (data) { return _this.getOutpatientPayRecordsSuc(data); }, function (error) { return _this.getOutpatientPayRecordsErr(error); });
    };
    // 从服务器获取门诊缴费记录（未缴费用），成功
    OutpatientPaymentRecordComponent.prototype.getOutpatientPayRecordsSuc = function (outputQueryOutpatientPayRecords) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('getOutpatientPayRecordsSuc: ' + outputQueryOutpatientPayRecords);
        this.outputQueryOutpatientPayRecords = outputQueryOutpatientPayRecords;
        if (this.outputQueryOutpatientPayRecords.list) {
            this.countBillAmount();
        }
        else {
            this.billListEmpty = true;
        }
        console.dir(this.outputQueryOutpatientPayRecords);
        this.loadingService.hiddenLoading();
    };
    // 从服务器获取门诊缴费记录（未缴费用），失败
    OutpatientPaymentRecordComponent.prototype.getOutpatientPayRecordsErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getOutpatientPayRecordsErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_8_app_core_objects_info_message__["a" /* InfoMessage */](errorMsg);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    // 计算总金额
    OutpatientPaymentRecordComponent.prototype.countBillAmount = function () {
        var amount = 0;
        var balance_amt = parseFloat(this.outputQueryOutpatientPayRecords.balance_amt);
        // const balance_amt = 0;
        for (var i = 0; i < this.outputQueryOutpatientPayRecords.list.length; i++) {
            amount += parseFloat(this.outputQueryOutpatientPayRecords.list[i].order_amount);
        }
        amount = Math.round(amount * 100) / 100;
        this.billAmount = amount.toString();
        if (balance_amt < amount) {
            this.tipsShow = true;
            var debt = amount - balance_amt;
            this.debt = (Math.round(debt * 100) / 100).toString();
        }
    };
    return OutpatientPaymentRecordComponent;
}());
OutpatientPaymentRecordComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-outpatient-payment-record',
        template: __webpack_require__("../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.html"),
        styles: [__webpack_require__("../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_10_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_10_app_core_services_system_service__["a" /* SystemService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object])
], OutpatientPaymentRecordComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=outpatient-payment-record.component.js.map

/***/ }),

/***/ "../../../../../src/app/outpatient/outpatient.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutpatientModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__outpatient_payment_record_outpatient_payment_record_component__ = __webpack_require__("../../../../../src/app/outpatient/outpatient-payment-record/outpatient-payment-record.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
// 门诊模块





var OutpatientModule = (function () {
    function OutpatientModule() {
    }
    return OutpatientModule;
}());
OutpatientModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"],
            __WEBPACK_IMPORTED_MODULE_2__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["b" /* MatButtonModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["c" /* MatCheckboxModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["f" /* MatDialogModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_material__["i" /* MatRadioModule */],
        ],
        declarations: [
            __WEBPACK_IMPORTED_MODULE_4__outpatient_payment_record_outpatient_payment_record_component__["a" /* OutpatientPaymentRecordComponent */],
        ],
        exports: []
    })
], OutpatientModule);

//# sourceMappingURL=outpatient.module.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-create-order.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputCreateOrder; });
// @see: ServerApiLibrary.createOrder
var InputCreateOrder = (function () {
    function InputCreateOrder() {
    }
    return InputCreateOrder;
}());

//# sourceMappingURL=input-create-order.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-device-login.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputDeviceLogin; });
// @see: ServerApiLibrary.deviceLogin
var InputDeviceLogin = (function () {
    function InputDeviceLogin() {
    }
    return InputDeviceLogin;
}());

//# sourceMappingURL=input-device-login.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-query-in-hospital-patient.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputQueryInHospitalPatient; });
// @see: ServerApiLibrary.queryInHospitalPatient
var InputQueryInHospitalPatient = (function () {
    function InputQueryInHospitalPatient() {
    }
    return InputQueryInHospitalPatient;
}());

//# sourceMappingURL=input-query-in-hospital-patient.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-query-order-status.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputQueryOrderStatus; });
// @see: ServerApiLibrary.queryOrderStatus
var InputQueryOrderStatus = (function () {
    function InputQueryOrderStatus() {
    }
    return InputQueryOrderStatus;
}());

//# sourceMappingURL=input-query-order-status.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-query-out-patient-info.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputQueryOutPatientInfo; });
// @see: ServerApiLibrary.queryOutPatientInfo
var InputQueryOutPatientInfo = (function () {
    function InputQueryOutPatientInfo() {
    }
    return InputQueryOutPatientInfo;
}());

//# sourceMappingURL=input-query-out-patient-info.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-query-outpatient-pay-records.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputQueryOutpatientPayRecords; });
// @see: ServerApiLibrary.queryOutpatientPayRecords
var InputQueryOutpatientPayRecords = (function () {
    function InputQueryOutpatientPayRecords() {
    }
    return InputQueryOutpatientPayRecords;
}());

//# sourceMappingURL=input-query-outpatient-pay-records.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/input-unified-order.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputUnifiedOrder; });
// @see: ServerApiLibrary.unifiedOrder
var InputUnifiedOrder = (function () {
    function InputUnifiedOrder() {
    }
    return InputUnifiedOrder;
}());

//# sourceMappingURL=input-unified-order.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/output-create-order.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputCreateOrder; });
// @see: ServerApiLibrary.createOrder
var OutputCreateOrder = (function () {
    function OutputCreateOrder() {
    }
    return OutputCreateOrder;
}());

//# sourceMappingURL=output-create-order.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/output-query-in-hospital-patient.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputQueryInHospitalPatient; });
// @see: ServerApiLibrary.queryInHospitalPatient
var OutputQueryInHospitalPatient = (function () {
    function OutputQueryInHospitalPatient() {
    }
    return OutputQueryInHospitalPatient;
}());

//# sourceMappingURL=output-query-in-hospital-patient.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/output-query-order-status.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputQueryOrderStatus; });
// @see: ServerApiLibrary.queryOrderStatus
var OutputQueryOrderStatus = (function () {
    function OutputQueryOrderStatus() {
    }
    return OutputQueryOrderStatus;
}());

//# sourceMappingURL=output-query-order-status.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/output-query-out-patient-info.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputQueryOutPatientInfo; });
// @see: ServerApiLibrary.queryOutPatientInfo
var OutputQueryOutPatientInfo = (function () {
    function OutputQueryOutPatientInfo() {
    }
    return OutputQueryOutPatientInfo;
}());

//# sourceMappingURL=output-query-out-patient-info.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/output-query-outpatient-pay-records.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputQueryOutpatientPayRecords; });
// @see: ServerApiLibrary.queryOutpatientPayRecords
var OutputQueryOutpatientPayRecords = (function () {
    function OutputQueryOutpatientPayRecords() {
    }
    return OutputQueryOutpatientPayRecords;
}());

//# sourceMappingURL=output-query-outpatient-pay-records.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/output-unified-order.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputUnifiedOrder; });
// @see: ServerApiLibrary.unifiedOrder
var OutputUnifiedOrder = (function () {
    function OutputUnifiedOrder() {
    }
    return OutputUnifiedOrder;
}());

//# sourceMappingURL=output-unified-order.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/server-api-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ServerApiLibrary; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_app_core_objects_auth_info__ = __webpack_require__("../../../../../src/app/core/objects/auth-info.ts");
// 服务器接口定义，由工具生成

var ServerApiLibrary = (function () {
    function ServerApiLibrary() {
    }
    Object.defineProperty(ServerApiLibrary, "authInfo", {
        get: function () {
            return this._authInfo;
        },
        set: function (value) {
            this._authInfo = value;
        },
        enumerable: true,
        configurable: true
    });
    return ServerApiLibrary;
}());

ServerApiLibrary._authInfo = new __WEBPACK_IMPORTED_MODULE_0_app_core_objects_auth_info__["a" /* AuthInfo */]();
// -----------
ServerApiLibrary.deviceLogin = 'minipay/auth/login'; // 设备登陆
ServerApiLibrary.queryPayChannelList = 'minipay/channel/qryPayChannelList'; // 商户支付渠道查询
ServerApiLibrary.queryOutPatientInfo = 'minipay/patient/qryOutPatientInfo'; // 查询门诊病人信息
ServerApiLibrary.queryInHospitalPatient = 'minipay/patient/qryInhospitalPatient'; // 查询住院病人信息
ServerApiLibrary.createOrder = 'minipay/order/createOrder'; // 缴费下单（生成预订单）
ServerApiLibrary.unifiedOrder = 'minipay/order/UnifiedOrder'; // 统一支付渠道下单
ServerApiLibrary.queryOrderStatus = 'minipay/order/qryOrderStatus'; // 查询订单状态
ServerApiLibrary.queryPaidRecord = 'minipay/order/qryPaidRecord'; // 查询缴费记录
ServerApiLibrary.queryOutpatientPayRecords = 'minipay/outpatient/qryOutpatientPayRecords'; // 门诊缴费记录（未缴费用）
//# sourceMappingURL=server-api-library.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/server/server-request.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ServerRequestService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_public_http_service__ = __webpack_require__("../../../../../src/app/core/public/http.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__ = __webpack_require__("../../../../../src/app/protocol/server/server-api-library.ts");
// 服务器接口访问，由工具生成
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ServerRequestService = (function () {
    function ServerRequestService(httpService) {
        this.httpService = httpService;
    }
    // 设备登陆
    ServerRequestService.prototype.deviceLogin = function (inputDeviceLogin) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].deviceLogin, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputDeviceLogin
        });
    };
    // 商户支付渠道查询
    ServerRequestService.prototype.queryPayChannelList = function (inputQueryPayChannelList) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].queryPayChannelList, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputQueryPayChannelList
        });
    };
    // 查询门诊病人信息
    ServerRequestService.prototype.queryOutPatientInfo = function (inputQueryOutPatientInfo) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].queryOutPatientInfo, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputQueryOutPatientInfo
        });
    };
    // 查询住院病人信息
    ServerRequestService.prototype.queryInHospitalPatient = function (inputQueryInHospitalPatient) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].queryInHospitalPatient, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputQueryInHospitalPatient
        });
    };
    // 缴费下单（生成预订单）
    ServerRequestService.prototype.createOrder = function (inputCreateOrder) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].createOrder, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputCreateOrder
        });
    };
    // 统一支付渠道下单
    ServerRequestService.prototype.unifiedOrder = function (inputUnifiedOrder) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].unifiedOrder, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputUnifiedOrder
        });
    };
    // 查询订单状态
    ServerRequestService.prototype.queryOrderStatus = function (inputQueryOrderStatus) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].queryOrderStatus, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputQueryOrderStatus
        });
    };
    // 查询缴费记录
    ServerRequestService.prototype.queryPaidRecord = function (inputQueryPaidRecord) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].queryPaidRecord, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputQueryPaidRecord
        });
    };
    // 门诊缴费记录（未缴费用）
    ServerRequestService.prototype.queryOutpatientPayRecords = function (inputQueryOutpatientPayRecords) {
        return this.httpService
            .requestServer(__WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.serverURL + __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].queryOutpatientPayRecords, {
            'requestToken': __WEBPACK_IMPORTED_MODULE_2_app_protocol_server_server_api_library__["a" /* ServerApiLibrary */].authInfo.token,
            'requestData': inputQueryOutpatientPayRecords
        });
    };
    return ServerRequestService;
}());
ServerRequestService = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_public_http_service__["a" /* HttpService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_public_http_service__["a" /* HttpService */]) === "function" && _a || Object])
], ServerRequestService);

var _a;
//# sourceMappingURL=server-request.service.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/shell/input-log.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InputLog; });
// @see: ShellApiLibrary.log
var InputLog = (function () {
    function InputLog() {
    }
    return InputLog;
}());

//# sourceMappingURL=input-log.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/shell/output-get-config.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputGetConfig; });
// @see: ShellApiLibrary.getConfig
var OutputGetConfig = (function () {
    function OutputGetConfig() {
    }
    return OutputGetConfig;
}());

//# sourceMappingURL=output-get-config.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/shell/output-read-magnetic-card.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutputReadMagneticCard; });
// @see: ShellApiLibrary.readMagneticCard
var OutputReadMagneticCard = (function () {
    function OutputReadMagneticCard() {
    }
    return OutputReadMagneticCard;
}());

//# sourceMappingURL=output-read-magnetic-card.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/shell/shell-api-library.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ShellApiLibrary; });
// 外壳接口定义，由工具生成
var ShellApiLibrary = (function () {
    function ShellApiLibrary() {
    }
    return ShellApiLibrary;
}());

ShellApiLibrary.statusSuc = '0000'; // 状态码，成功
ShellApiLibrary.statusErrCode = '4001'; // 状态码，指令编码错误
ShellApiLibrary.statusErrReadCard = '4002'; // 状态码，读卡错误
ShellApiLibrary.getConfig = '6001'; // 获取配置数据
ShellApiLibrary.log = '5010'; // 日志记录
ShellApiLibrary.readMagneticCard = '1001'; // 读磁条卡
ShellApiLibrary.readICCard = '1002'; // 读IC卡
ShellApiLibrary.readIDCard = '1001'; // 读身份证
ShellApiLibrary.readHealthCard = '1004'; // 读医保卡
ShellApiLibrary.shutDown = '5001'; // 关闭软件
ShellApiLibrary.updateVersion = '5002'; // 更新程序
ShellApiLibrary.setTimeForPowerOn = '1012'; // 设置自动开机时间
ShellApiLibrary.setTimeForPowerOff = '1014'; // 设置自动关机时间
ShellApiLibrary.test = 'test'; // 内容调试
//# sourceMappingURL=shell-api-library.js.map

/***/ }),

/***/ "../../../../../src/app/protocol/shell/shell-request.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ShellRequestService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__ = __webpack_require__("../../../../../src/app/core/public/shell-utility.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__ = __webpack_require__("../../../../../src/app/protocol/shell/shell-api-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_mock_mock_shell__ = __webpack_require__("../../../../../src/app/core/mock/mock-shell.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__ = __webpack_require__("../../../../../src/app/core/public/device-parameter.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_protocol_shell_input_log__ = __webpack_require__("../../../../../src/app/protocol/shell/input-log.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_assets_external_external_js__ = __webpack_require__("../../../../../src/assets/external/external.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_assets_external_external_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_8_assets_external_external_js__);
// shell 接口访问，由工具生成
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var ShellRequestService = ShellRequestService_1 = (function () {
    function ShellRequestService() {
    }
    // 将日志写入外壳，提供给其他类使用
    ShellRequestService.logToShell = function (data) {
        if (ShellRequestService_1.instance) {
            var inputLog = new __WEBPACK_IMPORTED_MODULE_7_app_protocol_shell_input_log__["a" /* InputLog */]();
            inputLog.param = data;
            ShellRequestService_1.instance.web2ShellLog(inputLog);
        }
    };
    // 初始化日志，此类日志可被其他类捕获后进一步处理
    ShellRequestService.prototype.initLog = function () {
        var _this = this;
        this.logObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.logSubscriber = subscriber;
        });
        return this.logObservable;
    };
    ShellRequestService.prototype.init = function () {
        ShellRequestService_1.instance = this;
        // 注册对外的接口
        window['angularComponentRef'] = {
            runThisFunctionFromOutside: this.runThisFunctionFromOutside.bind(this),
        };
    };
    // 被从外部调用的函数
    ShellRequestService.prototype.runThisFunctionFromOutside = function (type, data) {
        this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('get message from shell, type:[' + type + '], data:[' + JSON.stringify(data) + ']'));
        switch (type) {
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].getConfig:
                this.shell2WebGetConfig(data);
                break;
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readMagneticCard:
                this.shell2WebReadMagneticCard(data);
                break;
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readICCard:
                this.shell2WebReadICCard(data);
                break;
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readIDCard:
                this.shell2WebReadIDCard(data);
                break;
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].readHealthCard:
                this.shell2WebReadHealthCard(data);
                break;
            case __WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].test:
                this.shell2WebTest(data);
                break;
            default:
                this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('unknown data from shell with code: ' + type + ', and data: ' + data, __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
        }
    };
    // ------
    // 通知外壳，获取配置数据
    ShellRequestService.prototype.web2ShellGetConfig = function () {
        var _this = this;
        this.getConfigObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.getConfigSubscriber = subscriber;
        });
        var dataContent = __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2Shell(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].getConfig, null);
        setTimeout(function () {
            _this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('send message to shell:[' +
                dataContent +
                '], isShellDebugMode:[' +
                __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode +
                ']'));
            requestServiceInJS(dataContent, __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode);
        }, 200);
        return this.getConfigObservable;
    };
    // 外壳回调，获取配置数据
    ShellRequestService.prototype.shell2WebGetConfig = function (data) {
        var valueArray = data.split(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].protocolSeparator);
        if ((valueArray.length === 2) && (__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc === valueArray[0])) {
            var dataObject = JSON.parse(valueArray[1]);
            if (this.getConfigSubscriber) {
                try {
                    this.getConfigSubscriber.next(dataObject);
                }
                catch (err) {
                    this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber.next() get error ...' + err.stack, __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
                    this.getConfigSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
                }
            }
            else {
                this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber is null or undefined!', __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
            }
        }
        else {
            this.getConfigSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
        }
    };
    // 通知外壳，日志记录
    ShellRequestService.prototype.web2ShellLog = function (data) {
        var dataContent = __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2Shell4Log(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].log, data);
        requestServiceInJS(dataContent, __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode);
    };
    // 通知外壳，读磁条卡
    ShellRequestService.prototype.web2ShellReadMagneticCard = function () {
        var _this = this;
        this.readMagneticCardObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.readMagneticCardSubscriber = subscriber;
        });
        return this.readMagneticCardObservable;
    };
    // 外壳回调，读磁条卡
    ShellRequestService.prototype.shell2WebReadMagneticCard = function (data) {
        this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('get card from shell: ' + data));
        if (__WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isUserDebugMode) {
            // 模拟用户数据
            data = __WEBPACK_IMPORTED_MODULE_4_app_core_mock_mock_shell__["a" /* MockShell */].mockReadCardData();
            this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('get card from mock: ' + data));
        }
        var valueArray = data.split(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].protocolSeparator);
        if ((valueArray.length === 2) && (__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc === valueArray[0])) {
            var dataObject = JSON.parse(valueArray[1]);
            if (this.readMagneticCardSubscriber) {
                try {
                    this.readMagneticCardSubscriber.next(dataObject);
                }
                catch (err) {
                    this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber.next() get error ...' + err.stack, __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
                    this.readMagneticCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
                }
            }
            else {
                this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber is null or undefined!', __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
            }
        }
        else {
            this.readMagneticCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
        }
    };
    // 通知外壳，读IC卡
    ShellRequestService.prototype.web2ShellReadICCard = function () {
        var _this = this;
        this.readICCardObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.readICCardSubscriber = subscriber;
        });
        return this.readICCardObservable;
    };
    // 外壳回调，读IC卡
    ShellRequestService.prototype.shell2WebReadICCard = function (data) {
        var valueArray = data.split(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].protocolSeparator);
        if ((valueArray.length === 2) && (__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc === valueArray[0])) {
            var dataObject = JSON.parse(valueArray[1]);
            if (this.readICCardSubscriber) {
                try {
                    this.readICCardSubscriber.next(dataObject);
                }
                catch (err) {
                    this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber.next() get error ...' + err.stack, __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
                    this.readICCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
                }
            }
            else {
                this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber is null or undefined!', __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
            }
        }
        else {
            this.readICCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
        }
    };
    // 通知外壳，读身份证
    ShellRequestService.prototype.web2ShellReadIDCard = function () {
        var _this = this;
        this.readIDCardObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.readIDCardSubscriber = subscriber;
        });
        return this.readIDCardObservable;
    };
    // 外壳回调，读身份证
    ShellRequestService.prototype.shell2WebReadIDCard = function (data) {
        var valueArray = data.split(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].protocolSeparator);
        if ((valueArray.length === 2) && (__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc === valueArray[0])) {
            var dataObject = JSON.parse(valueArray[1]);
            if (this.readIDCardSubscriber) {
                try {
                    this.readIDCardSubscriber.next(dataObject);
                }
                catch (err) {
                    this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber.next() get error ...' + err.stack, __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
                    this.readIDCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
                }
            }
            else {
                this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber is null or undefined!', __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
            }
        }
        else {
            this.readIDCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
        }
    };
    // 通知外壳，读医保卡
    ShellRequestService.prototype.web2ShellReadHealthCard = function () {
        var _this = this;
        this.readHealthCardObservable = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__["a" /* Observable */](function (subscriber) {
            _this.readHealthCardSubscriber = subscriber;
        });
        return this.readHealthCardObservable;
    };
    // 外壳回调，读医保卡
    ShellRequestService.prototype.shell2WebReadHealthCard = function (data) {
        var valueArray = data.split(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].protocolSeparator);
        if ((valueArray.length === 2) && (__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].statusSuc === valueArray[0])) {
            var dataObject = JSON.parse(valueArray[1]);
            if (this.readHealthCardSubscriber) {
                try {
                    this.readHealthCardSubscriber.next(dataObject);
                }
                catch (err) {
                    this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber.next() get error ...' + err.stack, __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
                    this.readHealthCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
                }
            }
            else {
                this.log(new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */]('the subscriber is null or undefined!', __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */].statusError));
            }
        }
        else {
            this.readHealthCardSubscriber.error(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].errorDataFromShell);
        }
    };
    // 通知外壳，关闭软件
    ShellRequestService.prototype.web2ShellShutDown = function () {
        var dataContent = __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2Shell(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].shutDown, null);
        requestServiceInJS(dataContent, __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode);
    };
    // 通知外壳，更新程序
    ShellRequestService.prototype.web2ShellUpdateVersion = function () {
        var dataContent = __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2Shell(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].updateVersion, null);
        requestServiceInJS(dataContent, __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode);
    };
    // 通知外壳，设置自动开机时间
    ShellRequestService.prototype.web2ShellSetTimeForPowerOn = function (data) {
        var dataContent = __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2Shell(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].setTimeForPowerOn, data);
        requestServiceInJS(dataContent, __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode);
    };
    // 通知外壳，设置自动关机时间
    ShellRequestService.prototype.web2ShellSetTimeForPowerOff = function (data) {
        var dataContent = __WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].generateString2Shell(__WEBPACK_IMPORTED_MODULE_3_app_protocol_shell_shell_api_library__["a" /* ShellApiLibrary */].setTimeForPowerOff, data);
        requestServiceInJS(dataContent, __WEBPACK_IMPORTED_MODULE_5_app_core_public_device_parameter__["a" /* DeviceParameter */].isShellDebugMode);
    };
    // 外壳回调，测试
    ShellRequestService.prototype.shell2WebTest = function (data) {
        var valueArray = data.split(__WEBPACK_IMPORTED_MODULE_2_app_core_public_shell_utility__["a" /* ShellUtility */].protocolSeparator);
        if (valueArray.length === 2) {
            __WEBPACK_IMPORTED_MODULE_4_app_core_mock_mock_shell__["a" /* MockShell */].mock(valueArray[0], valueArray[1]);
        }
        else {
            __WEBPACK_IMPORTED_MODULE_4_app_core_mock_mock_shell__["a" /* MockShell */].mock(valueArray[0], null);
        }
    };
    // 记录日志，本类内部使用
    ShellRequestService.prototype.log = function (data) {
        this.logSubscriber.next(data);
    };
    return ShellRequestService;
}());
ShellRequestService = ShellRequestService_1 = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [])
], ShellRequestService);

var ShellRequestService_1;
//# sourceMappingURL=shell-request.service.js.map

/***/ }),

/***/ "../../../../../src/app/shared/error-dialog/error-dialog.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*错误信息弹框*/\r\n.error-dialog {\r\n  padding: 0.2344rem 0.2344rem;\r\n  text-align: center;\r\n}\r\n\r\n.tips {\r\n  font-family: 'PingFang-SC-Bold', Verdana, Geneva, sans-serif;\r\n  font-size: 0.3438rem;\r\n  color: #666;\r\n  margin-bottom: 0.35rem;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n}\r\n\r\n.tips .error-info {\r\n  width: 6.25rem;\r\n  word-break: break-all;\r\n  word-wrap: break-word;\r\n  text-align: left;\r\n}\r\n\r\n.tips img {\r\n  width: 0.625rem;\r\n  height: 0.625rem;\r\n  margin-right: 0.2344rem;\r\n}\r\n\r\n.btn-group button {\r\n  font-family: 'Microsoft YaHei', Verdana, Geneva, sans-serif;\r\n  font-size: 0.3438rem;\r\n  width: 2.3438rem;\r\n  height: 0.625rem;\r\n  border-radius: 0.0625rem;\r\n  box-shadow: none;\r\n}\r\n\r\n.btn-group button:not(:last-child) {\r\n  margin-right: 0.4688rem;\r\n}\r\n\r\n.left-btn {\r\n  color: #01a9f2;\r\n  background-color: #fff;\r\n  border: 1px solid #01a9f2;\r\n  box-shadow: none;\r\n}\r\n\r\n.right-btn {\r\n  color: #fff;\r\n  background-color: #00a9f2;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/error-dialog/error-dialog.component.html":
/***/ (function(module, exports) {

module.exports = "<!--错误信息弹框-->\r\n<div class=\"error-dialog\">\r\n  <div class=\"tips\"><img src=\"assets/images/icon_error.png\"><div class=\"error-info\">{{data.errorInfo}}</div></div>\r\n  <div class=\"btn-group\">\r\n    <button mat-raised-button class=\"left-btn\" (click)=\"onNoClick()\">关闭</button>\r\n    <!--<button mat-raised-button class=\"left-btn\">{{leftBtn}}</button>-->\r\n    <!--<button mat-raised-button class=\"right-btn\">{{rightBtn}}</button>-->\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/error-dialog/error-dialog.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ErrorDialogComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__ = __webpack_require__("../../../../rxjs/_esm5/observable/TimerObservable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
// 错误信息弹框



var ErrorDialogComponent = (function () {
    function ErrorDialogComponent(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.leftBtn = '取消';
        this.rightBtn = '确定';
    }
    ErrorDialogComponent.prototype.ngOnInit = function () {
        if (this.data.leftBtn) {
            this.leftBtn = this.data.leftBtn;
        }
        if (this.data.rightBtn) {
            this.rightBtn = this.data.rightBtn;
        }
        this.doAutoClose(this.data.autoCloseTime);
    };
    ErrorDialogComponent.prototype.ngOnDestroy = function () {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    };
    ErrorDialogComponent.prototype.onNoClick = function () {
        this.dialogRef.close();
    };
    // 处理自动关闭
    ErrorDialogComponent.prototype.doAutoClose = function (autoCloseTime) {
        var _this = this;
        if (autoCloseTime > 0) {
            this.timerObservable = __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__["a" /* TimerObservable */].create(autoCloseTime, autoCloseTime);
            this.timerSubscription = this.timerObservable.subscribe(function (t) {
                console.log('t: ', t);
                _this.dialogRef.close();
            });
        }
    };
    return ErrorDialogComponent;
}());
ErrorDialogComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-error-dialog',
        template: __webpack_require__("../../../../../src/app/shared/error-dialog/error-dialog.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/error-dialog/error-dialog.component.css")]
    }),
    __param(1, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Inject"])(__WEBPACK_IMPORTED_MODULE_2__angular_material__["a" /* MAT_DIALOG_DATA */])),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__angular_material__["g" /* MatDialogRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_material__["g" /* MatDialogRef */]) === "function" && _a || Object, Object])
], ErrorDialogComponent);

var _a;
//# sourceMappingURL=error-dialog.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/error-page/error-page.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*错误页面*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.content img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.7812rem auto 0.3906rem;\r\n}\r\n\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/error-page/error-page.component.html":
/***/ (function(module, exports) {

module.exports = "<!--错误页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <img src=\"assets/images/icon_error_big.png\">\r\n    <div>系统错误，请联系导诊台</div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/error-page/error-page.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ErrorPageComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 错误页面，用于设备无法正常提供服务，也无法继续进行其他操作的场合

var ErrorPageComponent = (function () {
    function ErrorPageComponent() {
    }
    ErrorPageComponent.prototype.ngOnInit = function () {
    };
    return ErrorPageComponent;
}());
ErrorPageComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-error-page',
        template: __webpack_require__("../../../../../src/app/shared/error-page/error-page.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/error-page/error-page.component.css")]
    }),
    __metadata("design:paramtypes", [])
], ErrorPageComponent);

//# sourceMappingURL=error-page.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/error-wait/error-wait.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*服务器连接错误页面*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n}\r\n\r\n.content > div {\r\n  height: 100%;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n}\r\n\r\n.content img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0 auto 0.3906rem;\r\n}\r\n\r\nbutton {\r\n  width: 2.3438rem;\r\n  height: 0.625rem;\r\n  color: #fff;\r\n  font-size: 0.3438rem;\r\n  margin: 0.3125rem auto 0.1172rem;\r\n}\r\n\r\nbutton.blue-btn {\r\n  background-color: #01a9f2;\r\n  box-shadow: none;\r\n}\r\n\r\nbutton.gray-btn[disabled] {\r\n  background-color: #ccc;\r\n  color: #fff;\r\n  box-shadow: none;\r\n}\r\n\r\n.tips {\r\n  font-size: 0.1562rem;\r\n  color: #01a9f2;\r\n}\r\n\r\n.tips > span {\r\n  color: #999;\r\n}\r\n\r\n/*加载中样式*/\r\n.loadEffect {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  position: relative;\r\n  margin: 0 auto 0.3906rem;\r\n}\r\n\r\n.loadEffect span {\r\n  display: inline-block;\r\n  width: 0.25rem;\r\n  height: 0.25rem;\r\n  border-radius: 50%;\r\n  background: #01a9f2;\r\n  position: absolute;\r\n  -webkit-animation: load 1.04s ease infinite;\r\n  animation: load 1.04s ease infinite;\r\n  -webkit-animation-fill-mode: both;\r\n  animation-fill-mode: both;\r\n}\r\n\r\n@-webkit-keyframes load {\r\n  0% {\r\n    opacity: 1;\r\n  }\r\n  100% {\r\n    opacity: 0.2;\r\n  }\r\n}\r\n\r\n@keyframes load {\r\n  0% {\r\n    opacity: 1;\r\n  }\r\n  100% {\r\n    opacity: 0.2;\r\n  }\r\n}\r\n\r\n.loadEffect span:nth-child(1) {\r\n  left: 0;\r\n  top: 50%;\r\n  margin-top: -0.125rem;\r\n  -webkit-animation-delay: 0.13s;\r\n  animation-delay: 0.13s;\r\n}\r\n\r\n.loadEffect span:nth-child(2) {\r\n  left: 0.2188rem;\r\n  top: 0.2188rem;\r\n  -webkit-animation-delay: 0.26s;\r\n  animation-delay: 0.26s;\r\n}\r\n\r\n.loadEffect span:nth-child(3) {\r\n  left: 50%;\r\n  top: 0;\r\n  margin-left: -0.125rem;\r\n  -webkit-animation-delay: 0.39s;\r\n  animation-delay: 0.39s;\r\n}\r\n\r\n.loadEffect span:nth-child(4) {\r\n  top: 0.2188rem;\r\n  right: 0.2188rem;\r\n  -webkit-animation-delay: 0.52s;\r\n  animation-delay: 0.52s;\r\n}\r\n\r\n.loadEffect span:nth-child(5) {\r\n  right: 0;\r\n  top: 50%;\r\n  margin-top: -0.125rem;\r\n  -webkit-animation-delay: 0.65s;\r\n  animation-delay: 0.65s;\r\n}\r\n\r\n.loadEffect span:nth-child(6) {\r\n  right: 0.2188rem;\r\n  bottom: 0.2188rem;\r\n  -webkit-animation-delay: 0.78s;\r\n  animation-delay: 0.78s;\r\n}\r\n\r\n.loadEffect span:nth-child(7) {\r\n  bottom: 0;\r\n  left: 50%;\r\n  margin-left: -0.125rem;\r\n  -webkit-animation-delay: 0.91s;\r\n  animation-delay: 0.91s;\r\n}\r\n\r\n.loadEffect span:nth-child(8) {\r\n  bottom: 0.2188rem;\r\n  left: 0.2188rem;\r\n  -webkit-animation-delay: 1.04s;\r\n  animation-delay: 1.04s;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/error-wait/error-wait.component.html":
/***/ (function(module, exports) {

module.exports = "<!--服务器连接错误页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <div *ngIf=\"!btnTryDisabled\">\r\n      <img src=\"assets/images/icon_fail.png\">\r\n      <div>服务器连接失败，请检查网络或尝试重连</div>\r\n      <button mat-raised-button class=\"blue-btn\" (click)=\"tryAgain()\" [disabled]=\"btnTryDisabled\">重连</button>\r\n    </div>\r\n    <div *ngIf=\"btnTryDisabled\">\r\n      <div class=\"loadEffect\">\r\n        <span></span>\r\n        <span></span>\r\n        <span></span>\r\n        <span></span>\r\n        <span></span>\r\n        <span></span>\r\n        <span></span>\r\n        <span></span>\r\n      </div>\r\n      <div>正在重连中，请稍等...</div>\r\n      <button mat-raised-button class=\"gray-btn\" disabled>重连</button>\r\n      <span class=\"tips\">\r\n        <app-timer [timerMax]=\"60\" [start]=\"btnTryDisabled\" (completed)=\"timerCompleted($event)\"></app-timer>\r\n        <span>后可再次重试</span>\r\n      </span>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/error-wait/error-wait.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ErrorWaitComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__ = __webpack_require__("../../../../rxjs/_esm5/observable/TimerObservable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_auth_service__ = __webpack_require__("../../../../../src/app/core/services/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
// 出错后等待重新尝试的UI
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ErrorWaitComponent = (function () {
    function ErrorWaitComponent(authService) {
        this.authService = authService;
        this.timerInitialDelay = 1500; // 初次自动重连的时间，1500ms
        this.timerPeriod = 600000; // 自动重连的时间间隔，10分钟
    }
    ErrorWaitComponent.prototype.ngOnInit = function () {
        this.initTimeout();
    };
    ErrorWaitComponent.prototype.ngOnDestroy = function () {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    };
    /**
     * 重新试着获取 token
     */
    ErrorWaitComponent.prototype.tryAgain = function () {
        this.btnTryDisabled = true;
        this.authService.startAuth();
    };
    // 定时器时间到
    ErrorWaitComponent.prototype.timerCompleted = function () {
        this.btnTryDisabled = false;
    };
    ErrorWaitComponent.prototype.initTimeout = function () {
        var _this = this;
        this.timerObservable = __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_TimerObservable__["a" /* TimerObservable */].create(this.timerInitialDelay, this.timerPeriod);
        this.timerSubscription = this.timerObservable.subscribe(function (t) {
            __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('try link server auto ...');
            _this.authService.startAuth();
        });
    };
    return ErrorWaitComponent;
}());
ErrorWaitComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-error-wait',
        template: __webpack_require__("../../../../../src/app/shared/error-wait/error-wait.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/error-wait/error-wait.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_auth_service__["a" /* AuthService */]) === "function" && _a || Object])
], ErrorWaitComponent);

var _a;
//# sourceMappingURL=error-wait.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/loading/loading.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*加载状态显示*/\r\n.outer-box {\r\n  position: absolute;\r\n  background-color: rgba(0, 0, 0, 0.4);\r\n  width: 100%;\r\n  height: 100%;\r\n  bottom: 0;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n}\r\n\r\n.spinner {\r\n  width: 0.7rem;\r\n  height: 0.7rem;\r\n  position: relative;\r\n}\r\n\r\n.container1 > div, .container2 > div, .container3 > div {\r\n  width: 0.18rem;\r\n  height: 0.18rem;\r\n  background-color: #fff;\r\n\r\n  border-radius: 100%;\r\n  position: absolute;\r\n  -webkit-animation: bouncedelay 1.2s infinite ease-in-out;\r\n  animation: bouncedelay 1.2s infinite ease-in-out;\r\n  -webkit-animation-fill-mode: both;\r\n  animation-fill-mode: both;\r\n}\r\n\r\n.spinner .spinner-container {\r\n  position: absolute;\r\n  width: 100%;\r\n  height: 100%;\r\n}\r\n\r\n.container2 {\r\n  -webkit-transform: rotateZ(45deg);\r\n  transform: rotateZ(45deg);\r\n}\r\n\r\n.container3 {\r\n  -webkit-transform: rotateZ(90deg);\r\n  transform: rotateZ(90deg);\r\n}\r\n\r\n.circle1 { top: 0; left: 0; }\r\n.circle2 { top: 0; right: 0; }\r\n.circle3 { right: 0; bottom: 0; }\r\n.circle4 { left: 0; bottom: 0; }\r\n\r\n.container2 .circle1 {\r\n  -webkit-animation-delay: -1.1s;\r\n  animation-delay: -1.1s;\r\n}\r\n\r\n.container3 .circle1 {\r\n  -webkit-animation-delay: -1.0s;\r\n  animation-delay: -1.0s;\r\n}\r\n\r\n.container1 .circle2 {\r\n  -webkit-animation-delay: -0.9s;\r\n  animation-delay: -0.9s;\r\n}\r\n\r\n.container2 .circle2 {\r\n  -webkit-animation-delay: -0.8s;\r\n  animation-delay: -0.8s;\r\n}\r\n\r\n.container3 .circle2 {\r\n  -webkit-animation-delay: -0.7s;\r\n  animation-delay: -0.7s;\r\n}\r\n\r\n.container1 .circle3 {\r\n  -webkit-animation-delay: -0.6s;\r\n  animation-delay: -0.6s;\r\n}\r\n\r\n.container2 .circle3 {\r\n  -webkit-animation-delay: -0.5s;\r\n  animation-delay: -0.5s;\r\n}\r\n\r\n.container3 .circle3 {\r\n  -webkit-animation-delay: -0.4s;\r\n  animation-delay: -0.4s;\r\n}\r\n\r\n.container1 .circle4 {\r\n  -webkit-animation-delay: -0.3s;\r\n  animation-delay: -0.3s;\r\n}\r\n\r\n.container2 .circle4 {\r\n  -webkit-animation-delay: -0.2s;\r\n  animation-delay: -0.2s;\r\n}\r\n\r\n.container3 .circle4 {\r\n  -webkit-animation-delay: -0.1s;\r\n  animation-delay: -0.1s;\r\n}\r\n\r\n@-webkit-keyframes bouncedelay {\r\n  0%, 80%, 100% { -webkit-transform: scale(0.0) }\r\n  40% { -webkit-transform: scale(1.0) }\r\n}\r\n\r\n@keyframes bouncedelay {\r\n  0%, 80%, 100% {\r\n    transform: scale(0.0);\r\n    -webkit-transform: scale(0.0);\r\n  } 40% {\r\n      transform: scale(1.0);\r\n      -webkit-transform: scale(1.0);\r\n    }\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/loading/loading.component.html":
/***/ (function(module, exports) {

module.exports = "<!--加载状态显示-->\r\n<div class=\"outer-box\">\r\n  <div class=\"spinner\">\r\n    <div class=\"spinner-container container1\">\r\n      <div class=\"circle1\"></div>\r\n      <div class=\"circle2\"></div>\r\n      <div class=\"circle3\"></div>\r\n      <div class=\"circle4\"></div>\r\n    </div>\r\n    <div class=\"spinner-container container2\">\r\n      <div class=\"circle1\"></div>\r\n      <div class=\"circle2\"></div>\r\n      <div class=\"circle3\"></div>\r\n      <div class=\"circle4\"></div>\r\n    </div>\r\n    <div class=\"spinner-container container3\">\r\n      <div class=\"circle1\"></div>\r\n      <div class=\"circle2\"></div>\r\n      <div class=\"circle3\"></div>\r\n      <div class=\"circle4\"></div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/loading/loading.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoadingComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 加载状态显示

var LoadingComponent = (function () {
    function LoadingComponent() {
    }
    LoadingComponent.prototype.ngOnInit = function () {
    };
    return LoadingComponent;
}());
LoadingComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-loading',
        template: __webpack_require__("../../../../../src/app/shared/loading/loading.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/loading/loading.component.css")]
    }),
    __metadata("design:paramtypes", [])
], LoadingComponent);

//# sourceMappingURL=loading.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/main-header-logo/main-header-logo.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*header, 显示相关 logo*/\r\n.header {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: row;\r\n          flex-direction: row;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.header .logo {\r\n  height: 0.7812rem;\r\n  /*width: 2.4297rem;*/\r\n  margin-left: 0.1484rem;\r\n}\r\n\r\n.header button {\r\n  width: 1.1562rem;\r\n  height: 0.625rem;\r\n  margin-right: 0.2031rem;\r\n  font-size: 0.3438rem;\r\n  color: #00aaf2;\r\n  border: 1px solid #00aaf2;\r\n  border-radius: 0.0625rem;\r\n  box-shadow: none;\r\n}\r\n\r\n.header button.time-button {\r\n  width: 1.7969rem;\r\n}\r\n\r\n.double-btn {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/main-header-logo/main-header-logo.component.html":
/***/ (function(module, exports) {

module.exports = "<!--header, 显示相关 logo-->\r\n<div class=\"header\">\r\n  <img src=\"assets/images/logo.png\" class=\"logo\">\r\n  <button mat-raised-button [class.time-button]=\"timerShow\" *ngIf=\"returnBtnShow\" (click)=\"goToReturn()\">关闭\r\n    <app-timer *ngIf=\"timerShow\" [timerMax]=\"timerMax\" [start]=\"true\" (completed)=\"timerCompleted($event)\"></app-timer>\r\n  </button>\r\n  <div class=\"double-btn\" *ngIf=\"doubleBtnShow\">\r\n    <button mat-raised-button (click)=\"goToPrev()\">返回</button>\r\n    <button mat-raised-button [class.time-button]=\"timerShow\" (click)=\"goToReturn()\">关闭\r\n      <app-timer *ngIf=\"timerShow\" [timerMax]=\"timerMax\"  [start]=\"true\" (completed)=\"timerCompleted($event)\"></app-timer>\r\n    </button>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/main-header-logo/main-header-logo.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainHeaderLogoComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// header, 显示相关 logo



var MainHeaderLogoComponent = (function () {
    function MainHeaderLogoComponent(routerService, systemService) {
        this.routerService = routerService;
        this.systemService = systemService;
    }
    MainHeaderLogoComponent.prototype.ngOnInit = function () {
    };
    MainHeaderLogoComponent.prototype.goToReturn = function () {
        this.systemService.backMainMenu();
    };
    MainHeaderLogoComponent.prototype.goToPrev = function () {
        this.routerService.back();
    };
    // 定时器时间到
    MainHeaderLogoComponent.prototype.timerCompleted = function (event) {
        this.systemService.backMainMenu();
    };
    return MainHeaderLogoComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], MainHeaderLogoComponent.prototype, "returnBtnShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], MainHeaderLogoComponent.prototype, "doubleBtnShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], MainHeaderLogoComponent.prototype, "timerShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Number)
], MainHeaderLogoComponent.prototype, "timerMax", void 0);
MainHeaderLogoComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-main-header-logo',
        template: __webpack_require__("../../../../../src/app/shared/main-header-logo/main-header-logo.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/main-header-logo/main-header-logo.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__["a" /* SystemService */]) === "function" && _b || Object])
], MainHeaderLogoComponent);

var _a, _b;
//# sourceMappingURL=main-header-logo.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/main-header/main-header.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*header, 显示 用户信息、返回按钮*/\r\n.header {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: reverse;\r\n      -ms-flex-direction: row-reverse;\r\n          flex-direction: row-reverse;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.patient-info {\r\n  font-size: 0.2812rem;\r\n  color: #333;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: row;\r\n          flex-direction: row;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.patient-info img {\r\n  height: 0.625rem;\r\n  width: 0.625rem;\r\n  margin-left: 0.1875rem;\r\n}\r\n\r\n.patient-info span {\r\n  padding: 0 0.1562rem;\r\n}\r\n\r\n.patient-info .patient-name {\r\n  border-right: 2px solid #ccc;\r\n}\r\n\r\n.header > button {\r\n  width: 1.1562rem;\r\n  height: 0.625rem;\r\n  margin-right: 0.2031rem;\r\n  font-size: 0.3438rem;\r\n  color: #00aaf2;\r\n  border: 1px solid #00aaf2;\r\n  border-radius: 0.0625rem;\r\n  box-shadow: none;\r\n}\r\n\r\n.header > button.time-button {\r\n  width: 1.9531rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/main-header/main-header.component.html":
/***/ (function(module, exports) {

module.exports = "<!--header, 显示 用户信息、返回按钮-->\r\n<div class=\"header\">\r\n  <button mat-raised-button (click)=\"goToReturn()\" [class.time-button]=\"timerShow\">关闭\r\n    <app-timer *ngIf=\"timerShow\" [timerMax]=\"timerMax\" [start]=\"true\" (completed)=\"timerCompleted($event)\"></app-timer>\r\n  </button>\r\n  <div class=\"patient-info\" *ngIf=\"!infoHidden\">\r\n    <img src=\"assets/images/head.png\">\r\n    <div *ngIf=\"dataSrc!=='prepaySrc'\">\r\n      <span class=\"patient-name\">{{outpatientInfo.real_name}}</span>\r\n      <span>就诊号：{{outpatientInfo.patient_card}}</span>\r\n    </div>\r\n    <div *ngIf=\"dataSrc==='prepaySrc'\">\r\n      <span class=\"patient-name\">{{inpatientInfo.real_name}}</span>\r\n      <span>住院号：{{inpatientInfo.serial_number}}</span>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/main-header/main-header.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainHeaderComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// header, 显示 用户信息、返回按钮




var MainHeaderComponent = (function () {
    function MainHeaderComponent(userService, systemService) {
        this.userService = userService;
        this.systemService = systemService;
    }
    MainHeaderComponent.prototype.ngOnInit = function () {
        if (this.dataSrc === __WEBPACK_IMPORTED_MODULE_3_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcInpatient) {
            this.inpatientInfo = this.userService.getInPatientInfo();
        }
        else {
            this.outpatientInfo = this.userService.getOutPatientInfo();
        }
    };
    MainHeaderComponent.prototype.goToReturn = function () {
        this.systemService.backMainMenu();
    };
    // 定时器时间到
    MainHeaderComponent.prototype.timerCompleted = function (event) {
        this.systemService.backMainMenu();
    };
    return MainHeaderComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], MainHeaderComponent.prototype, "infoHidden", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], MainHeaderComponent.prototype, "timerShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Number)
], MainHeaderComponent.prototype, "timerMax", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", String)
], MainHeaderComponent.prototype, "dataSrc", void 0);
MainHeaderComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-main-header',
        template: __webpack_require__("../../../../../src/app/shared/main-header/main-header.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/main-header/main-header.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_user_service__["a" /* UserService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_system_service__["a" /* SystemService */]) === "function" && _b || Object])
], MainHeaderComponent);

var _a, _b;
//# sourceMappingURL=main-header.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*缴费金额选择页面*/\r\n.content {\r\n  padding: 0.1953rem;\r\n}\r\n\r\n.total-amount {\r\n  font-size: 0.2812rem;\r\n  color: #333;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  /*justify-content: center;*/\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.total-amount > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  width: 4.6rem;\r\n}\r\n\r\n.total-amount img {\r\n  width: 0.3438rem;\r\n  height: 0.3438rem;\r\n  margin-right: 0.1953rem;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n  margin-left: 0.2344rem;\r\n}\r\n\r\n.choose-amount {\r\n  height: 3.125rem;\r\n  box-sizing: border-box;\r\n  padding: 0.2344rem;\r\n  margin-top: 0.2344rem;\r\n  color: #333;\r\n  background-color: #fff;\r\n  text-align: center;\r\n}\r\n\r\n.choose-amount > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.choose-amount > div:not(:first-child) {\r\n  padding: 0.2344rem 0;\r\n}\r\n\r\n.choose-amount .tips{\r\n  font-size: 0.25rem;\r\n  line-height: 0.2812rem;\r\n}\r\n\r\n.amount {\r\n  box-sizing: border-box;\r\n  width: 1.9531rem;\r\n  height: 0.7031rem;\r\n  line-height: 0.6875rem;\r\n  font-size: 0.3125rem;\r\n  border: 1px solid #ddd;\r\n  border-radius: 0.0391rem;\r\n}\r\n\r\n.amount.active {\r\n  color: #feb229;\r\n  border-color: #feb229;\r\n  position:relative;\r\n}\r\n\r\n.amount.active img {\r\n  width: 0.3516rem;\r\n  height: 0.3516rem;\r\n  position: absolute;\r\n  right: 0;\r\n  bottom: 0;\r\n}\r\n\r\n.input-amount {\r\n  box-sizing: border-box;\r\n  width: 4.3594rem;\r\n  height: 0.7031rem;\r\n  color: #999;\r\n  font-size: 0.2812rem;\r\n  text-align: left;\r\n  padding-left: 30px;\r\n  border: 1px solid #ddd;\r\n  border-radius: 0.0391rem;\r\n}\r\n\r\n.input-amount.active {\r\n  color: #333;\r\n}\r\n\r\n.input-amount span {\r\n  vertical-align: baseline;\r\n}\r\n\r\n.footer {\r\n  padding: 0 0.1562rem;\r\n  font-size: 0.2812rem;\r\n  color: #333;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.footer button {\r\n  width: 1.4688rem;\r\n  height: 0.625rem;\r\n  border-radius: 0.0625rem;\r\n  font-size: 0.3438rem;\r\n  color: #fff;\r\n  background-color: #01a9f2;\r\n  box-shadow: none;\r\n}\r\n\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.html":
/***/ (function(module, exports) {

module.exports = "<!--缴费金额选择页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"60\" [dataSrc]=\"dataSrc\"></app-main-header>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"total-amount\">\r\n      <div>\r\n        <img src=\"assets/images/icon_balance.png\">\r\n        <div *ngIf=\"debt\">\r\n          <span>应缴金额</span>\r\n          <span class=\"money\">¥ {{debt}}</span>\r\n        </div>\r\n        <div *ngIf=\"!debt\">\r\n          <span>当前余额</span>\r\n          <span class=\"money\">¥ {{balanceAmount}}</span>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"choose-amount\">\r\n      <div class=\"tips\">请选择缴费金额</div>\r\n      <div>\r\n        <div class=\"amount\" (click)=\"shortcutKeyTap('100')\" [class.active]=\"shortcutKey==='100'\">¥100<img src=\"assets/images/ticker.png\" *ngIf=\"shortcutKey==='100'\"></div>\r\n        <div class=\"amount\" (click)=\"shortcutKeyTap('200')\" [class.active]=\"shortcutKey==='200'\">¥200<img src=\"assets/images/ticker.png\" *ngIf=\"shortcutKey==='200'\"></div>\r\n        <div class=\"amount\" (click)=\"shortcutKeyTap('500')\" [class.active]=\"shortcutKey==='500'\">¥500<img src=\"assets/images/ticker.png\" *ngIf=\"shortcutKey==='500'\"></div>\r\n        <div class=\"amount\" (click)=\"shortcutKeyTap('1000')\" [class.active]=\"shortcutKey==='1000'\">¥1000<img src=\"assets/images/ticker.png\" *ngIf=\"shortcutKey==='1000'\"></div>\r\n      </div>\r\n      <div>\r\n        <div class=\"amount\" (click)=\"shortcutKeyTap('2000')\" [class.active]=\"shortcutKey==='2000'\">¥2000<img src=\"assets/images/ticker.png\" *ngIf=\"shortcutKey==='2000'\"></div>\r\n        <div class=\"amount\" (click)=\"shortcutKeyTap('5000')\" [class.active]=\"shortcutKey==='5000'\">¥5000<img src=\"assets/images/ticker.png\" *ngIf=\"shortcutKey==='5000'\"></div>\r\n        <button mat-button class=\"input-amount\" [class.active]=\"amountSting!=='请输入缴费金额'\" (click)=\"openVirtualKeyboard()\"><span *ngIf=\"amountSting!=='请输入缴费金额'\">¥</span>{{amountSting}}</button>\r\n        <!--<div class=\"input-amount\">请输入缴费金额</div>-->\r\n      </div>\r\n    </div>\r\n  </div>\r\n\r\n  <div class=\"footer\">\r\n    <div>缴费金额<span class=\"money\">¥ <span>{{totalAmount}}</span></span></div>\r\n    <button mat-raised-button (click)=\"goToPaymentTypeSelect()\">去支付</button>\r\n  </div>\r\n\r\n</div>\r\n\r\n<app-virtual-keyboard-float *ngIf=\"virtualKeyboardShow\" [(amountSting)]=\"amountSting\" [(totalAmount)]=\"totalAmount\" [(shortcutKey)]=\"shortcutKey\" [(virtualKeyboardShow)]=\"virtualKeyboardShow\"></app-virtual-keyboard-float>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PaymentAmountSelectComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 缴费金额选择页面









var PaymentAmountSelectComponent = (function () {
    function PaymentAmountSelectComponent(routerService, errorService, route, userService) {
        this.routerService = routerService;
        this.errorService = errorService;
        this.route = route;
        this.userService = userService;
        this.amountSting = '请输入缴费金额';
        this.totalAmount = '100';
        this.shortcutKey = '100';
        this.debt = ''; // 应缴金额
        this.balanceAmount = ''; // 当前余额
        this.dataSrc = __WEBPACK_IMPORTED_MODULE_7_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcOneCardPay; // 缴费类型:默认为一卡通充值
        // 虚拟键盘显示状态
        this.virtualKeyboardShow = false;
    }
    PaymentAmountSelectComponent.prototype.ngOnInit = function () {
        if (this.route.snapshot.paramMap.get('debt')) {
            this.debt = this.route.snapshot.paramMap.get('debt');
            this.totalAmount = this.debt;
            this.amountSting = this.debt;
            this.shortcutKey = '';
        }
        else {
            // 如果路由中有缴费类型的字段，则证明是住院缴费
            if (this.route.snapshot.paramMap.get('dataSrc')) {
                this.dataSrc = this.route.snapshot.paramMap.get('dataSrc');
                this.balanceAmount = this.userService.getInPatientInfo().remain_amt;
            }
            else {
                this.balanceAmount = this.userService.getOutPatientInfo().balance;
            }
            if (parseFloat(this.balanceAmount) < 0) {
                this.totalAmount = (-parseFloat(this.balanceAmount)).toString();
                this.shortcutKey = '';
            }
        }
    };
    // 快捷键输入金额
    PaymentAmountSelectComponent.prototype.shortcutKeyTap = function (keyNumber) {
        this.totalAmount = keyNumber;
        this.shortcutKey = keyNumber;
        this.amountSting = '请输入缴费金额';
    };
    // 打开虚拟键盘
    PaymentAmountSelectComponent.prototype.openVirtualKeyboard = function () {
        this.virtualKeyboardShow = true;
    };
    PaymentAmountSelectComponent.prototype.goToPaymentTypeSelect = function () {
        if (parseFloat(this.totalAmount) === 0) {
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_6_app_core_objects_info_message__["a" /* InfoMessage */](__WEBPACK_IMPORTED_MODULE_8__core_libs_info_library__["a" /* InfoLibrary */].paymentAmountError);
            infoMessage.noRouter = true; // 关闭时无需跳转
            infoMessage.autoCloseTime = 3000; // 自动关闭倒计时毫秒
            this.errorService.showError(infoMessage);
        }
        else {
            var orderInfo = { totalAmount: this.totalAmount, dataSrc: this.dataSrc };
            // const orderInfo = {totalAmount: '0.01', dataSrc: this.dataSrc};
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentTypeSelect, orderInfo]);
        }
    };
    return PaymentAmountSelectComponent;
}());
PaymentAmountSelectComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-payment-amount-select',
        template: __webpack_require__("../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__angular_router__["a" /* ActivatedRoute */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */]) === "function" && _d || Object])
], PaymentAmountSelectComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=payment-amount-select.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/payment-result/payment-result.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*支付结果*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n}\r\n\r\n.success {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  text-align: center;\r\n  height: 100%;\r\n}\r\n\r\n.result-box {\r\n  font-size: 0.2812rem;\r\n  line-height: 0.4297rem;\r\n  color: #666;\r\n  width: 3.5938rem;\r\n}\r\n\r\n.result-box .success-tips {\r\n  font-size: 0.4375rem;\r\n  color: #333;\r\n  margin: 0.3125rem auto 0.2344rem;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n}\r\n\r\n.result-box img {\r\n  width: 1.7969rem;\r\n  height: 1.7969rem;\r\n}\r\n\r\n.gray-line {\r\n  width: 1px;\r\n  height: 2.5469rem;\r\n  border-left: 1px solid #ddd;\r\n  margin: 0 0.3906rem;\r\n}\r\n\r\n.phone {\r\n  width: 1.8594rem;\r\n  height: 3.2656rem;\r\n}\r\n\r\n.tips {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.wechat-box .tips > span {\r\n  color: #09bb07;\r\n}\r\n\r\n.alipay-box .tips > span {\r\n  color: #00aaf2;\r\n}\r\n\r\n.fail {\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.fail img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.7812rem auto 0.3906rem;\r\n}\r\n\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/payment-result/payment-result.component.html":
/***/ (function(module, exports) {

module.exports = "<!--支付结果-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo [returnBtnShow]=\"true\" [timerShow]=\"true\" [timerMax]=\"60\"></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"success\" *ngIf=\"paymentResult==='success'\">\r\n      <div class=\"result-box\">\r\n        <img src=\"assets/images/icon_succeed.png\">\r\n        <div class=\"success-tips\">充值成功</div>\r\n        <div *ngIf=\"dataSrc!=='prepaySrc'\">可用金额：<span class=\"money\">¥{{outpatientInfo.balance}}</span></div>\r\n        <div *ngIf=\"dataSrc==='prepaySrc'\">可用金额：<span class=\"money\">¥{{inpatientInfo.remain_amt}}</span></div>\r\n        <div>充值金额：¥{{orderAmount}}</div>\r\n      </div>\r\n      <div class=\"gray-line\"></div>\r\n      <div class=\"wechat-box\" *ngIf=\"paymentType==='WeChat'\">\r\n        <img src=\"assets/images/iPhone_weixin.png\" class=\"phone\">\r\n        <div class=\"tips\">打开<span>微信</span>-微信支付查看支付详情</div>\r\n      </div>\r\n      <div class=\"alipay-box\" *ngIf=\"paymentType==='AliPay'\">\r\n        <img src=\"assets/images/iPhone_zhifubao.png\" class=\"phone\">\r\n        <div class=\"tips\">打开<span>支付宝</span>-支付助手查看支付详情</div>\r\n      </div>\r\n    </div>\r\n    <div *ngIf=\"paymentResult==='fail'||paymentResult==='HISFail'\" class=\"fail\">\r\n      <img src=\"assets/images/icon_fail.png\">\r\n      <div *ngIf=\"paymentResult=='fail'\">很抱歉，当前缴费支付异常，请您重新缴费。</div>\r\n      <div *ngIf=\"paymentResult=='HISFail'\">很抱歉，当前缴费支付异常，请到医院窗口办理退费。</div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/payment-result/payment-result.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PaymentResultComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 支付结果










var PaymentResultComponent = (function () {
    function PaymentResultComponent(route, userService, errorService, systemService, loadingService) {
        this.route = route;
        this.userService = userService;
        this.errorService = errorService;
        this.systemService = systemService;
        this.loadingService = loadingService;
    }
    PaymentResultComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            _this.paymentType = params['paymentType'];
            _this.orderStatus = params['orderStatus'];
            _this.orderAmount = params['orderAmount'];
            _this.dataSrc = params['dataSrc'];
            if (_this.orderStatus === __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].orderStatusSucToHIS) {
                _this.paymentResult = __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].PaySuccess;
                if (_this.dataSrc === __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcInpatient) {
                    _this.inpatientInfo = _this.userService.getInPatientInfo();
                    _this.getInpatientInfo(_this.inpatientInfo.serial_number);
                }
                else {
                    _this.outpatientInfo = _this.userService.getOutPatientInfo();
                    _this.getPatientInfo(_this.outpatientInfo.patient_card);
                }
            }
            else if (_this.orderStatus === __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].orderStatusFailedToHIS) {
                _this.paymentResult = __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].FailedToHIS;
            }
            else {
                _this.paymentResult = __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].PayFail;
            }
        });
    };
    // 从服务器获取用户信息
    PaymentResultComponent.prototype.getPatientInfo = function (inpatientNum) {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getOutPatientInfoFromServer(inpatientNum).subscribe(function (data) { return _this.getPatientInfoSuc(data); }, function (error) { return _this.getPatientInfoErr(error); });
    };
    // 从服务器获取用户信息，成功
    PaymentResultComponent.prototype.getPatientInfoSuc = function (outputQueryOutPatientInfo) {
        __WEBPACK_IMPORTED_MODULE_5_app_core_services_log_service__["a" /* LogService */].debug('getPatientInfoSuc: ' + outputQueryOutPatientInfo);
        this.outpatientInfo = outputQueryOutPatientInfo;
        this.loadingService.hiddenLoading();
    };
    // 从服务器获取用户信息，失败
    PaymentResultComponent.prototype.getPatientInfoErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_5_app_core_services_log_service__["a" /* LogService */].error('getPatientInfoErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_7_app_core_objects_info_message__["a" /* InfoMessage */](__WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__["a" /* InfoLibrary */].outpatientNumError);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    // 从服务器获取住院病人信息
    PaymentResultComponent.prototype.getInpatientInfo = function (serialNumber) {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getInpatientInfoFromServer(serialNumber).subscribe(function (data) { return _this.getInpatientInfoSuc(data); }, function (error) { return _this.getInpatientInfoErr(error); });
    };
    // 从服务器获取住院病人信息，成功
    PaymentResultComponent.prototype.getInpatientInfoSuc = function (outputQueryInHospitalPatient) {
        __WEBPACK_IMPORTED_MODULE_5_app_core_services_log_service__["a" /* LogService */].debug('getInpatientInfoSuc: ' + outputQueryInHospitalPatient);
        this.inpatientInfo = outputQueryInHospitalPatient;
        this.loadingService.hiddenLoading();
    };
    // 从服务器获取住院病人信息，失败
    PaymentResultComponent.prototype.getInpatientInfoErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_5_app_core_services_log_service__["a" /* LogService */].error('getInpatientInfoErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_7_app_core_objects_info_message__["a" /* InfoMessage */](__WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__["a" /* InfoLibrary */].inpatientNumError);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    return PaymentResultComponent;
}());
PaymentResultComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-payment-result',
        template: __webpack_require__("../../../../../src/app/shared/payment-result/payment-result.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/payment-result/payment-result.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__["a" /* SystemService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object])
], PaymentResultComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=payment-result.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/payment-type-select/payment-type-select.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*支付类型选择页面*/\r\n.content {\r\n  /*display: flex;\r\n  align-items: center;\r\n  justify-content: space-between;*/\r\n  text-align: center;\r\n  height: 100%;\r\n}\r\n\r\n.tips {\r\n  color: #333;\r\n  font-size: 0.3125rem;\r\n  margin: 0.7031rem auto 0.1562rem;\r\n}\r\n\r\n.payment-type-btn {\r\n  width: 3.125rem;\r\n  height: 3.125rem;\r\n  border-radius: 0.3906rem;\r\n}\r\n\r\n.payment-type-btn:not(:last-child) {\r\n  margin-right: 0.9375rem;\r\n}\r\n\r\n.payment-type-btn img {\r\n  width: 2.3438rem;\r\n  height: 2.6562rem;\r\n  margin-top: 0.1562rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/payment-type-select/payment-type-select.component.html":
/***/ (function(module, exports) {

module.exports = "<!--支付类型选择页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo [doubleBtnShow]=\"true\" [timerShow]=\"true\" [timerMax]=\"60\"></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"tips\">请选择支付方式</div>\r\n    <button mat-button (click)=\"paymentTypeSelect('WeChat')\" class=\"payment-type-btn\">\r\n      <img src=\"assets/images/pay_weixin.png\">\r\n    </button>\r\n    <button mat-button (click)=\"paymentTypeSelect('AliPay')\" class=\"payment-type-btn\">\r\n      <img src=\"assets/images/pay_zhifubao.png\">\r\n    </button>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/payment-type-select/payment-type-select.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PaymentTypeSelectComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 支付类型选择页面












var PaymentTypeSelectComponent = (function () {
    function PaymentTypeSelectComponent(routerService, route, userService, systemService, errorService, loadingService) {
        this.routerService = routerService;
        this.route = route;
        this.userService = userService;
        this.systemService = systemService;
        this.errorService = errorService;
        this.loadingService = loadingService;
    }
    PaymentTypeSelectComponent.prototype.ngOnInit = function () {
    };
    PaymentTypeSelectComponent.prototype.paymentTypeSelect = function (paymentType) {
        var _this = this;
        this.paymentType = paymentType;
        this.route.params.subscribe(function (params) {
            _this.orderInfo = params;
            _this.totalAmount = params['totalAmount'];
            _this.dataSrc = params['dataSrc'];
            var payType;
            if (paymentType === 'WeChat') {
                payType = __WEBPACK_IMPORTED_MODULE_8_app_core_libs_pay_library__["a" /* PayLibrary */].payTypeWeChatQRCode;
            }
            else if (paymentType === 'AliPay') {
                payType = __WEBPACK_IMPORTED_MODULE_8_app_core_libs_pay_library__["a" /* PayLibrary */].payTypeALiPayQRCode;
            }
            _this.createOrder(_this.totalAmount, _this.dataSrc, payType);
        });
    };
    // 缴费下单（生成预订单）
    PaymentTypeSelectComponent.prototype.createOrder = function (totalAmt, dataSrc, payType) {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.createOrderByServer(totalAmt, dataSrc, payType).subscribe(function (data) { return _this.createOrderSuc(data); }, function (error) { return _this.createOrderErr(error); });
    };
    // 缴费下单（生成预订单），成功
    PaymentTypeSelectComponent.prototype.createOrderSuc = function (outputCreateOrder) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('createOrderSuc: ' + outputCreateOrder);
        this.orderInfo = outputCreateOrder;
        console.dir(this.orderInfo);
        var id = outputCreateOrder.id;
        var trade_no = outputCreateOrder.trade_no;
        var order_amount = outputCreateOrder.order_amount;
        this.unifiedOrder(id, trade_no, order_amount);
    };
    // 缴费下单（生成预订单），失败
    PaymentTypeSelectComponent.prototype.createOrderErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('createOrderErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_10_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_9_app_core_objects_info_message__["a" /* InfoMessage */](errorMsg);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    // 统一支付渠道下单
    PaymentTypeSelectComponent.prototype.unifiedOrder = function (id, tradeNo, orderAmount) {
        var _this = this;
        this.userService.unifiedOrderByServer(id, tradeNo, orderAmount).subscribe(function (data) { return _this.unifiedOrderSuc(data); }, function (error) { return _this.unifiedOrderErr(error); });
    };
    // 统一支付渠道下单，成功
    PaymentTypeSelectComponent.prototype.unifiedOrderSuc = function (outputUnifiedOrder) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('unifiedOrderSuc: ' + outputUnifiedOrder);
        var paymentObj = {
            payUrl: outputUnifiedOrder.pay_url,
            tradeNo: outputUnifiedOrder.trade_no,
            paymentType: this.paymentType,
            totalAmount: this.totalAmount,
            dataSrc: this.dataSrc
        };
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].payment, paymentObj]);
        this.loadingService.hiddenLoading();
    };
    // 统一支付渠道下单，失败
    PaymentTypeSelectComponent.prototype.unifiedOrderErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('unifiedOrderErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_10_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_9_app_core_objects_info_message__["a" /* InfoMessage */](errorMsg);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    return PaymentTypeSelectComponent;
}());
PaymentTypeSelectComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-payment-type-select',
        template: __webpack_require__("../../../../../src/app/shared/payment-type-select/payment-type-select.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/payment-type-select/payment-type-select.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_6__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__angular_router__["a" /* ActivatedRoute */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__["a" /* UserService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_11_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_11_app_core_services_system_service__["a" /* SystemService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _f || Object])
], PaymentTypeSelectComponent);

var _a, _b, _c, _d, _e, _f;
//# sourceMappingURL=payment-type-select.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/payment/payment.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*扫描二维码支付页面*/\r\n.content {\r\n  padding: 0.1562rem 0.1953rem;\r\n  height: 100%;\r\n}\r\n\r\n.balance {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n}\r\n\r\n.code-box {\r\n  background-color: #fff;\r\n  margin-top: 0.1562rem;\r\n  padding: 0.1562rem 1.4062rem;\r\n  font-size: 0.3594rem;\r\n  color: #333;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n  margin-left: 0.35rem;\r\n}\r\n\r\n.payment-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n  text-align: center;\r\n}\r\n\r\n.phone {\r\n  width: 1.8594rem;\r\n  height: 3.2656rem;\r\n}\r\n\r\n.wechat-tips, .alipay-tips {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.wechat-tips > span {\r\n  color: #09bb07;\r\n}\r\n\r\n.alipay-tips > span {\r\n  color: #00aaf2;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/payment/payment.component.html":
/***/ (function(module, exports) {

module.exports = "<!--扫描二维码支付页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"120\" [dataSrc]=\"dataSrc\"></app-main-header>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"balance\" *ngIf=\"dataSrc!=='prepaySrc'\">当前余额 ¥{{outpatientInfo.balance}}</div>\r\n    <div class=\"balance\" *ngIf=\"dataSrc==='prepaySrc'\">当前余额 ¥{{inpatientInfo.remain_amt}}</div>\r\n    <div class=\"code-box\">\r\n      <div>缴费金额<span class=\"money\">¥ {{totalAmount}}</span></div>\r\n      <div class=\"payment-box\">\r\n        <ngx-qrcode\r\n          [qrc-element-type]=\"'img'\"\r\n          [qrc-value]=\"payUrl\"\r\n          qrc-class=\"QRCode\"\r\n          qrc-errorCorrectionLevel=\"L\">\r\n        </ngx-qrcode>\r\n        <div *ngIf=\"paymentType==='WeChat'\"><img src=\"assets/images/iPhone_weixin.png\" class=\"phone\">\r\n          <div class=\"wechat-tips\">打开<span>微信</span>扫一扫支付</div>\r\n        </div>\r\n        <div *ngIf=\"paymentType==='AliPay'\"><img src=\"assets/images/iPhone_zhifubao.png\" class=\"phone\">\r\n          <div class=\"alipay-tips\">打开<span>支付宝</span>扫一扫支付</div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/payment/payment.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PaymentComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_libs_pay_library__ = __webpack_require__("../../../../../src/app/core/libs/pay-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 扫描二维码支付页面









var PaymentComponent = (function () {
    function PaymentComponent(route, routerService, systemService, userService) {
        this.route = route;
        this.routerService = routerService;
        this.systemService = systemService;
        this.userService = userService;
        this.queryCount = 16;
    }
    PaymentComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            _this.paymentType = params['paymentType'];
            _this.totalAmount = params['totalAmount'];
            _this.payUrl = params['payUrl'];
            _this.tradeNo = params['tradeNo'];
            _this.dataSrc = params['dataSrc'];
            if (_this.dataSrc === __WEBPACK_IMPORTED_MODULE_6_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcInpatient) {
                _this.inpatientInfo = _this.userService.getInPatientInfo();
            }
            else {
                _this.outpatientInfo = _this.userService.getOutPatientInfo();
            }
        });
        setTimeout(function () {
            _this.getOrderStatus(_this.tradeNo);
        }, 9000);
    };
    // 查询订单状态
    PaymentComponent.prototype.getOrderStatus = function (tradeNo) {
        var _this = this;
        this.userService.queryOrderStatusFromServer(tradeNo).subscribe(function (data) { return _this.getOrderStatusSuc(data); }, function (error) { return _this.getOrderStatusErr(error); });
    };
    // 查询订单状态，成功
    PaymentComponent.prototype.getOrderStatusSuc = function (outputQueryOrderStatus) {
        var _this = this;
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('getOrderStatusSuc: ' + outputQueryOrderStatus);
        this.queryCount--;
        var paymentObj = {
            orderAmount: outputQueryOrderStatus.order_amount,
            orderStatus: outputQueryOrderStatus.order_status,
            paymentType: this.paymentType,
            dataSrc: this.dataSrc
        };
        if (outputQueryOrderStatus.order_status === __WEBPACK_IMPORTED_MODULE_6_app_core_libs_pay_library__["a" /* PayLibrary */].orderStatusWaitPay
            || outputQueryOrderStatus.order_status === __WEBPACK_IMPORTED_MODULE_6_app_core_libs_pay_library__["a" /* PayLibrary */].orderStatusNotStartToHIS
            || outputQueryOrderStatus.order_status === __WEBPACK_IMPORTED_MODULE_6_app_core_libs_pay_library__["a" /* PayLibrary */].orderStatusPaying) {
            console.log('timeOut go');
            if (this.queryCount > 0) {
                setTimeout(function () {
                    _this.getOrderStatus(_this.tradeNo);
                }, 3000);
            }
        }
        else {
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].paymentResult, paymentObj]);
        }
        console.dir(outputQueryOrderStatus);
    };
    // 查询订单状态，失败
    PaymentComponent.prototype.getOrderStatusErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].error('getOrderStatusErr: ' + errorMsg);
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_7_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            var paymentObj = {
                orderStatus: 'error',
                paymentType: this.paymentType
            };
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].paymentResult, paymentObj]);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    return PaymentComponent;
}());
PaymentComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-payment',
        template: __webpack_require__("../../../../../src/app/shared/payment/payment.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/payment/payment.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__["a" /* RouterService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__["a" /* SystemService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */]) === "function" && _d || Object])
], PaymentComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=payment.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/shared.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SharedModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__techiediaries_ngx_qrcode__ = __webpack_require__("../../../../@techiediaries/ngx-qrcode/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__status_info_status_info_component__ = __webpack_require__("../../../../../src/app/shared/status-info/status-info.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__timer_timer_component__ = __webpack_require__("../../../../../src/app/shared/timer/timer.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__system_alert_system_alert_component__ = __webpack_require__("../../../../../src/app/shared/system-alert/system-alert.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__main_header_main_header_component__ = __webpack_require__("../../../../../src/app/shared/main-header/main-header.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__main_header_logo_main_header_logo_component__ = __webpack_require__("../../../../../src/app/shared/main-header-logo/main-header-logo.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__payment_amount_select_payment_amount_select_component__ = __webpack_require__("../../../../../src/app/shared/payment-amount-select/payment-amount-select.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__virtual_keyboard_virtual_keyboard_component__ = __webpack_require__("../../../../../src/app/shared/virtual-keyboard/virtual-keyboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__payment_type_select_payment_type_select_component__ = __webpack_require__("../../../../../src/app/shared/payment-type-select/payment-type-select.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__payment_payment_component__ = __webpack_require__("../../../../../src/app/shared/payment/payment.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__payment_result_payment_result_component__ = __webpack_require__("../../../../../src/app/shared/payment-result/payment-result.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__virtual_keyboard_float_virtual_keyboard_float_component__ = __webpack_require__("../../../../../src/app/shared/virtual-keyboard-float/virtual-keyboard-float.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__error_dialog_error_dialog_component__ = __webpack_require__("../../../../../src/app/shared/error-dialog/error-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__loading_loading_component__ = __webpack_require__("../../../../../src/app/shared/loading/loading.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__error_page_error_page_component__ = __webpack_require__("../../../../../src/app/shared/error-page/error-page.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__error_wait_error_wait_component__ = __webpack_require__("../../../../../src/app/shared/error-wait/error-wait.component.ts");
// Shared feature module
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



















var SharedModule = (function () {
    function SharedModule() {
    }
    return SharedModule;
}());
SharedModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["b" /* MatButtonModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["c" /* MatCheckboxModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["f" /* MatDialogModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["i" /* MatRadioModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["h" /* MatExpansionModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["d" /* MatChipsModule */],
            __WEBPACK_IMPORTED_MODULE_3__techiediaries_ngx_qrcode__["a" /* NgxQRCodeModule */]
        ],
        declarations: [
            __WEBPACK_IMPORTED_MODULE_4__status_info_status_info_component__["a" /* StatusInfoComponent */],
            __WEBPACK_IMPORTED_MODULE_5__timer_timer_component__["a" /* TimerComponent */],
            __WEBPACK_IMPORTED_MODULE_6__system_alert_system_alert_component__["a" /* SystemAlertComponent */],
            __WEBPACK_IMPORTED_MODULE_7__main_header_main_header_component__["a" /* MainHeaderComponent */],
            __WEBPACK_IMPORTED_MODULE_8__main_header_logo_main_header_logo_component__["a" /* MainHeaderLogoComponent */],
            __WEBPACK_IMPORTED_MODULE_9__payment_amount_select_payment_amount_select_component__["a" /* PaymentAmountSelectComponent */],
            __WEBPACK_IMPORTED_MODULE_10__virtual_keyboard_virtual_keyboard_component__["a" /* VirtualKeyboardComponent */],
            __WEBPACK_IMPORTED_MODULE_11__payment_type_select_payment_type_select_component__["a" /* PaymentTypeSelectComponent */],
            __WEBPACK_IMPORTED_MODULE_12__payment_payment_component__["a" /* PaymentComponent */],
            __WEBPACK_IMPORTED_MODULE_13__payment_result_payment_result_component__["a" /* PaymentResultComponent */],
            __WEBPACK_IMPORTED_MODULE_14__virtual_keyboard_float_virtual_keyboard_float_component__["a" /* VirtualKeyboardFloatComponent */],
            __WEBPACK_IMPORTED_MODULE_15__error_dialog_error_dialog_component__["a" /* ErrorDialogComponent */],
            __WEBPACK_IMPORTED_MODULE_16__loading_loading_component__["a" /* LoadingComponent */],
            __WEBPACK_IMPORTED_MODULE_17__error_page_error_page_component__["a" /* ErrorPageComponent */],
            __WEBPACK_IMPORTED_MODULE_18__error_wait_error_wait_component__["a" /* ErrorWaitComponent */],
        ],
        exports: [
            __WEBPACK_IMPORTED_MODULE_4__status_info_status_info_component__["a" /* StatusInfoComponent */],
            __WEBPACK_IMPORTED_MODULE_5__timer_timer_component__["a" /* TimerComponent */],
            __WEBPACK_IMPORTED_MODULE_6__system_alert_system_alert_component__["a" /* SystemAlertComponent */],
            __WEBPACK_IMPORTED_MODULE_7__main_header_main_header_component__["a" /* MainHeaderComponent */],
            __WEBPACK_IMPORTED_MODULE_8__main_header_logo_main_header_logo_component__["a" /* MainHeaderLogoComponent */],
            __WEBPACK_IMPORTED_MODULE_10__virtual_keyboard_virtual_keyboard_component__["a" /* VirtualKeyboardComponent */],
            __WEBPACK_IMPORTED_MODULE_15__error_dialog_error_dialog_component__["a" /* ErrorDialogComponent */],
            __WEBPACK_IMPORTED_MODULE_16__loading_loading_component__["a" /* LoadingComponent */],
            __WEBPACK_IMPORTED_MODULE_17__error_page_error_page_component__["a" /* ErrorPageComponent */],
        ],
        entryComponents: [
            __WEBPACK_IMPORTED_MODULE_6__system_alert_system_alert_component__["a" /* SystemAlertComponent */],
            __WEBPACK_IMPORTED_MODULE_15__error_dialog_error_dialog_component__["a" /* ErrorDialogComponent */]
        ]
    })
], SharedModule);

//# sourceMappingURL=shared.module.js.map

/***/ }),

/***/ "../../../../../src/app/shared/status-info/status-info.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*系统状态显示*/\r\n.status-info {\r\n  width: 6rem;\r\n  text-align: left;\r\n  position: fixed;\r\n  bottom: 0;\r\n  left: 2rem;\r\n  opacity: 0.8;\r\n}\r\n\r\n.status-info-content {\r\n  max-height: 2rem;\r\n  overflow-y: scroll;\r\n}\r\n\r\n.last-info {\r\n  width: 100%;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.last-info button {\r\n  margin-right: 0.3rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/status-info/status-info.component.html":
/***/ (function(module, exports) {

module.exports = "<!--系统状态显示-->\r\n<div class=\"status-info\" *ngIf=\"showInfoContent\">\r\n  <mat-expansion-panel>\r\n    <mat-expansion-panel-header>\r\n      <mat-panel-title>\r\n        Last info:\r\n      </mat-panel-title>\r\n      <mat-panel-description>\r\n        <div class=\"last-info\">\r\n          <span>{{infoArray[0].data}}</span><button mat-raised-button color=\"primary\" (click)=\"clearLog()\">清除记录</button>\r\n        </div>\r\n      </mat-panel-description>\r\n    </mat-expansion-panel-header>\r\n\r\n    <div class=\"status-info-content\">\r\n      <div *ngFor=\"let infoMessage of infoArray;let index = index\">\r\n        <mat-chip-list>\r\n          <mat-chip selected=\"true\" color=\"warn\" *ngIf=\"infoMessage.status===2\">Error:</mat-chip>\r\n          <mat-chip *ngIf=\"infoMessage.status===1\">Log:</mat-chip>\r\n          <mat-basic-chip>&emsp;{{ infoMessage.data }}</mat-basic-chip>\r\n        </mat-chip-list>\r\n      </div>\r\n    </div>\r\n  </mat-expansion-panel>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/status-info/status-info.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StatusInfoComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__ = __webpack_require__("../../../../../src/app/core/services/info.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__ = __webpack_require__("../../../../../src/app/core/public/device-parameter.ts");
// 系统状态显示
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var StatusInfoComponent = (function () {
    function StatusInfoComponent(systemService) {
        this.systemService = systemService;
        this.showInfoContent = __WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__["a" /* DeviceParameter */].isInfoDebugMode;
        this.infoArray = []; // 状态信息
        this.maxInfoNum = 15; // 状态信息最大显示条数
    }
    StatusInfoComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showInfoContentObservable = __WEBPACK_IMPORTED_MODULE_4_app_core_public_device_parameter__["a" /* DeviceParameter */].getInfoDebugMode();
        this.showInfoContentSubscription = this.showInfoContentObservable.subscribe(function (isInfoDebugMode) {
            _this.showInfoContent = isInfoDebugMode;
        });
        this.infoArray.push(new __WEBPACK_IMPORTED_MODULE_3_app_core_objects_info_message__["a" /* InfoMessage */]('--'));
        this.infoStart();
    };
    StatusInfoComponent.prototype.ngOnDestroy = function () {
        this.infoStop();
    };
    StatusInfoComponent.prototype.infoStart = function () {
        var _this = this;
        this.infoObservable = __WEBPACK_IMPORTED_MODULE_2_app_core_services_info_service__["a" /* InfoService */].getInfo();
        this.infoSubscription = this.infoObservable.subscribe(function (infoMessage) {
            if (_this.infoArray.length >= _this.maxInfoNum) {
                _this.infoArray.pop();
            }
            _this.infoArray.unshift(infoMessage);
            console.log('-- update! --', infoMessage.data);
        }, function (error) {
            console.log('-- error! --');
        }, function () {
            console.log('-- end! --');
        });
        this.systemService.initUICompleted();
    };
    StatusInfoComponent.prototype.infoStop = function () {
        if (this.infoSubscription) {
            this.infoSubscription.unsubscribe();
        }
    };
    StatusInfoComponent.prototype.clearLog = function () {
        this.infoArray.length = 1;
    };
    return StatusInfoComponent;
}());
StatusInfoComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-status-info',
        template: __webpack_require__("../../../../../src/app/shared/status-info/status-info.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/status-info/status-info.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_system_service__["a" /* SystemService */]) === "function" && _a || Object])
], StatusInfoComponent);

var _a;
//# sourceMappingURL=status-info.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/system-alert/system-alert.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*系统警告框*/\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/system-alert/system-alert.component.html":
/***/ (function(module, exports) {

module.exports = "<!--系统警告框-->\r\n<div>{{ info }}</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/system-alert/system-alert.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SystemAlertComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
// 系统警告框
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};


var SystemAlertComponent = (function () {
    function SystemAlertComponent(data) {
        this.data = data;
    }
    SystemAlertComponent.prototype.ngOnInit = function () {
        this.info = this.data['info'];
    };
    return SystemAlertComponent;
}());
SystemAlertComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-system-alert',
        template: __webpack_require__("../../../../../src/app/shared/system-alert/system-alert.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/system-alert/system-alert.component.css")]
    }),
    __param(0, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Inject"])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["a" /* MAT_DIALOG_DATA */])),
    __metadata("design:paramtypes", [Object])
], SystemAlertComponent);

//# sourceMappingURL=system-alert.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/timer/timer.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*倒计时读秒*/\r\n/*.timer {*/\r\n  /*font-size: 0.4rem;*/\r\n  /*margin: 0 .1771rem 0 0;*/\r\n  /*width: 120px;*/\r\n  /*height: .4167rem;*/\r\n\r\n  /*font-family: ArialMT;*/\r\n  /*font-size: 40px;*/\r\n  /*color: #be2828;*/\r\n  /*text-align: right;*/\r\n  /*line-height: .4167rem;*/\r\n\r\n  /*background-color: #00a9f2;*/\r\n/*}*/\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/timer/timer.component.html":
/***/ (function(module, exports) {

module.exports = "<!--倒计时读秒-->\r\n<span class=\"timer\">{{ timerValue }}</span>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/timer/timer.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TimerComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_services_timer_service__ = __webpack_require__("../../../../../src/app/core/services/timer.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
// 倒计时读秒
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var TimerComponent = (function () {
    function TimerComponent(timerService) {
        this.timerService = timerService;
        this.completed = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"](); // 完成事件
        this.isDoing = false; // 是否正在倒计时中
    }
    TimerComponent.prototype.ngOnInit = function () {
    };
    TimerComponent.prototype.ngOnChanges = function (changes) {
        var changeStart = changes['start'];
        if ((changeStart.currentValue) && (!this.isDoing)) {
            this.timerStart();
        }
    };
    TimerComponent.prototype.ngOnDestroy = function () {
        this.timerStop();
    };
    TimerComponent.prototype.timerStart = function () {
        var _this = this;
        this.isDoing = true;
        this.timerObservable = this.timerService.start(this.timerMax);
        this.timerSubscription = this.timerObservable.subscribe(function (value) {
            _this.timerValue = value + 's';
        }, function (error) {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('timer is error!');
        }, function () {
            __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('timer is end!');
            _this.isDoing = false;
            _this.completed.emit(_this.timerMax);
        });
    };
    TimerComponent.prototype.timerStop = function () {
        this.timerService.stop();
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    };
    return TimerComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Number)
], TimerComponent.prototype, "timerMax", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], TimerComponent.prototype, "start", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]) === "function" && _a || Object)
], TimerComponent.prototype, "completed", void 0);
TimerComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-timer',
        template: __webpack_require__("../../../../../src/app/shared/timer/timer.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/timer/timer.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1_app_core_services_timer_service__["a" /* TimerService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_app_core_services_timer_service__["a" /* TimerService */]) === "function" && _b || Object])
], TimerComponent);

var _a, _b;
//# sourceMappingURL=timer.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/virtual-keyboard-float/virtual-keyboard-float.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*虚拟键盘，缴费金额选择页面使用（带小数点的虚拟键盘）*/\r\n.outer-box {\r\n  position: absolute;\r\n  background-color: rgba(0, 0, 0, 0.4);\r\n  width: 100%;\r\n  height: 100%;\r\n  bottom: 0;\r\n}\r\n\r\n.keyboard-box {\r\n  position: absolute;\r\n  bottom: 0;\r\n  width: 100%;\r\n}\r\n\r\n.amount-box {\r\n  height: 0.9375rem;\r\n  line-height: 0.9375rem;\r\n  padding: 0 0.4688rem;\r\n  font-size: 0.4062rem;\r\n  color: #999;\r\n  background-color: #fff;\r\n}\r\n\r\n.amount-box.active {\r\n  color: #333;\r\n}\r\n\r\n.virtual-keyboard {\r\n  width: 100%;\r\n  box-sizing: border-box;\r\n  height: 4.8438rem;\r\n  background-color: #f5f5f5;\r\n  padding: 0 0.4688rem;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.virtual-keyboard button {\r\n  font-size: 0.4531rem;\r\n  border-radius: 0.0781rem;\r\n}\r\n\r\n.left-btn-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n  width: 6.7188rem;\r\n  height: 4.0625rem;\r\n}\r\n\r\n.left-btn-box > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.left-btn-box button {\r\n  width: 2.0312rem;\r\n  height: 0.7812rem;\r\n  color: #01a9f2;\r\n  background-color: #fff;\r\n}\r\n\r\n.right-btn-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n  height: 4.0625rem;\r\n  width: 2.0312rem;\r\n}\r\n\r\n.confirm-btn {\r\n  width: 2.0312rem;\r\n  height: 2.4219rem;\r\n  color: #fff;\r\n  background-color: #01a9f2;\r\n}\r\n\r\n.cancel-btn {\r\n  width: 2.0312rem;\r\n  height: 1.3281rem;\r\n  color: #999;\r\n  border-color: #fff;\r\n}\r\n\r\n.delete-btn > img {\r\n  width: 0.7812rem;\r\n  height: 0.4375rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/virtual-keyboard-float/virtual-keyboard-float.component.html":
/***/ (function(module, exports) {

module.exports = "<!--虚拟键盘，缴费金额选择页面使用（带小数点的虚拟键盘）-->\r\n<div class=\"outer-box\">\r\n  <div class=\"keyboard-box\">\r\n    <div class=\"amount-box\" [class.active]=\"amountSting!=='请输入缴费金额'\">{{amountSting}}</div>\r\n    <div class=\"virtual-keyboard\">\r\n      <div class=\"left-btn-box\">\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('1')\">1</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('2')\">2</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('3')\">3</button>\r\n        </div>\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('4')\">4</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('5')\">5</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('6')\">6</button>\r\n        </div>\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('7')\">7</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('8')\">8</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('9')\">9</button>\r\n        </div>\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('.')\">.</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('0')\">0</button>\r\n          <button mat-raised-button (click)=\"deleteTap()\" class=\"delete-btn\"><img src=\"assets/images/delete.png\"></button>\r\n        </div>\r\n      </div>\r\n      <div class=\"right-btn-box\">\r\n        <button mat-raised-button class=\"confirm-btn\" (click)=\"confirmTap()\">确定</button>\r\n        <button mat-raised-button class=\"cancel-btn\" (click)=\"cancelTap()\">取消</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/virtual-keyboard-float/virtual-keyboard-float.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VirtualKeyboardFloatComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 虚拟键盘，缴费金额选择页面使用（带小数点的虚拟键盘）

var VirtualKeyboardFloatComponent = (function () {
    function VirtualKeyboardFloatComponent() {
        this.amountStingChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        this.totalAmountChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        this.shortcutKeyChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        this.virtualKeyboardShowChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
    }
    VirtualKeyboardFloatComponent.prototype.ngOnInit = function () {
    };
    // 虚拟键盘输入金额
    VirtualKeyboardFloatComponent.prototype.virtualKeyboardTap = function (keyNumber) {
        if (this.amountSting === '请输入缴费金额' || this.amountSting === '0') {
            if (keyNumber !== '.') {
                this.amountSting = keyNumber;
            }
            else {
                this.amountSting = '0.';
                this.amountStingChange.emit(this.amountSting);
            }
        }
        else if (this.amountSting.indexOf('.') === -1 || (keyNumber !== '.' && this.amountSting.split('.')[1].length < 2)) {
            this.amountSting += keyNumber;
        }
    };
    // 虚拟键盘删除按钮点击
    VirtualKeyboardFloatComponent.prototype.deleteTap = function () {
        if (this.amountSting.length === 1) {
            this.amountSting = '请输入缴费金额';
        }
        else if (this.amountSting !== '请输入缴费金额') {
            this.amountSting = this.amountSting.substring(0, this.amountSting.length - 1);
        }
    };
    // 虚拟键盘确认按钮点击
    VirtualKeyboardFloatComponent.prototype.confirmTap = function () {
        if (this.amountSting !== '请输入缴费金额') {
            if (this.amountSting.substr(-1, 1) === '.') {
                this.amountSting = this.amountSting.substring(0, this.amountSting.length - 1);
            }
            this.totalAmount = this.amountSting;
            this.shortcutKey = '';
        }
        this.amountStingChange.emit(this.amountSting);
        this.totalAmountChange.emit(this.totalAmount);
        this.shortcutKeyChange.emit(this.shortcutKey);
        this.closeVirtualKeyboard();
    };
    // 虚拟键盘取消按钮点击
    VirtualKeyboardFloatComponent.prototype.cancelTap = function () {
        if (this.shortcutKey !== '') {
            this.amountSting = '请输入缴费金额';
        }
        else {
            this.amountSting = this.totalAmount;
        }
        this.closeVirtualKeyboard();
    };
    // 关闭虚拟键盘
    VirtualKeyboardFloatComponent.prototype.closeVirtualKeyboard = function () {
        this.virtualKeyboardShow = false;
        this.virtualKeyboardShowChange.emit(this.virtualKeyboardShow);
    };
    return VirtualKeyboardFloatComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", String)
], VirtualKeyboardFloatComponent.prototype, "amountSting", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", String)
], VirtualKeyboardFloatComponent.prototype, "totalAmount", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", String)
], VirtualKeyboardFloatComponent.prototype, "shortcutKey", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], VirtualKeyboardFloatComponent.prototype, "virtualKeyboardShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], VirtualKeyboardFloatComponent.prototype, "amountStingChange", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], VirtualKeyboardFloatComponent.prototype, "totalAmountChange", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], VirtualKeyboardFloatComponent.prototype, "shortcutKeyChange", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], VirtualKeyboardFloatComponent.prototype, "virtualKeyboardShowChange", void 0);
VirtualKeyboardFloatComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-virtual-keyboard-float',
        template: __webpack_require__("../../../../../src/app/shared/virtual-keyboard-float/virtual-keyboard-float.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/virtual-keyboard-float/virtual-keyboard-float.component.css")]
    }),
    __metadata("design:paramtypes", [])
], VirtualKeyboardFloatComponent);

//# sourceMappingURL=virtual-keyboard-float.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/virtual-keyboard/virtual-keyboard.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*虚拟键盘，住院登录使用*/\r\n.outer-box {\r\n  position: absolute;\r\n  background-color: rgba(0, 0, 0, 0.4);\r\n  width: 100%;\r\n  height: 100%;\r\n  bottom: 0;\r\n}\r\n\r\n.keyboard-box {\r\n  position: absolute;\r\n  bottom: 0;\r\n  width: 100%;\r\n}\r\n\r\n.amount-box {\r\n  height: 0.9375rem;\r\n  line-height: 0.9375rem;\r\n  padding: 0 0.4688rem;\r\n  font-size: 0.4062rem;\r\n  color: #999;\r\n  background-color: #fff;\r\n}\r\n\r\n.amount-box.active {\r\n  color: #333;\r\n}\r\n\r\n.virtual-keyboard {\r\n  width: 100%;\r\n  box-sizing: border-box;\r\n  height: 4.8438rem;\r\n  background-color: #f5f5f5;\r\n  padding: 0 0.4688rem;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.virtual-keyboard button {\r\n  font-size: 0.4531rem;\r\n  border-radius: 0.0781rem;\r\n}\r\n\r\n.left-btn-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n  width: 6.7188rem;\r\n  height: 4.0625rem;\r\n}\r\n\r\n.left-btn-box > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.left-btn-box button {\r\n  width: 2.0312rem;\r\n  height: 0.7812rem;\r\n  color: #01a9f2;\r\n  background-color: #fff;\r\n}\r\n\r\n.right-btn-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n  height: 4.0625rem;\r\n  width: 2.0312rem;\r\n}\r\n\r\n.confirm-btn {\r\n  width: 2.0312rem;\r\n  height: 2.4219rem;\r\n  color: #fff;\r\n  background-color: #01a9f2;\r\n}\r\n\r\n.cancel-btn {\r\n  width: 2.0312rem;\r\n  height: 1.3281rem;\r\n  color: #999;\r\n  border-color: #fff;\r\n}\r\n\r\n.delete-btn > img {\r\n  width: 0.7812rem;\r\n  height: 0.4375rem;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/virtual-keyboard/virtual-keyboard.component.html":
/***/ (function(module, exports) {

module.exports = "<!--虚拟键盘，住院登录使用-->\r\n<div class=\"outer-box\">\r\n  <div class=\"keyboard-box\">\r\n    <div class=\"amount-box\" [class.active]=\"serialNumber!=='请输入住院号'\">{{serialNumber}}</div>\r\n    <div class=\"virtual-keyboard\">\r\n      <div class=\"left-btn-box\">\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('1')\">1</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('2')\">2</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('3')\">3</button>\r\n        </div>\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('4')\">4</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('5')\">5</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('6')\">6</button>\r\n        </div>\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('7')\">7</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('8')\">8</button>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('9')\">9</button>\r\n        </div>\r\n        <div>\r\n          <button mat-raised-button (click)=\"virtualKeyboardTap('0')\">0</button>\r\n          <button mat-raised-button (click)=\"deleteTap()\" class=\"delete-btn\"><img src=\"assets/images/delete.png\"></button>\r\n          <button mat-raised-button (click)=\"clearTap()\">清空</button>\r\n        </div>\r\n      </div>\r\n      <div class=\"right-btn-box\">\r\n        <button mat-raised-button class=\"confirm-btn\" (click)=\"confirmTap()\">确定</button>\r\n        <button mat-raised-button class=\"cancel-btn\" (click)=\"cancelTap()\">取消</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/virtual-keyboard/virtual-keyboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VirtualKeyboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// 虚拟键盘，住院登录使用

var VirtualKeyboardComponent = (function () {
    function VirtualKeyboardComponent() {
        this.serialNumber = '请输入住院号';
        this.virtualKeyboardShowChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        this.getInpatientInfo = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
    }
    VirtualKeyboardComponent.prototype.ngOnInit = function () {
    };
    // 虚拟键盘输入金额
    VirtualKeyboardComponent.prototype.virtualKeyboardTap = function (keyNumber) {
        if (this.serialNumber === '请输入住院号') {
            this.serialNumber = keyNumber;
        }
        else {
            this.serialNumber += keyNumber;
        }
    };
    // 虚拟键盘删除按钮点击
    VirtualKeyboardComponent.prototype.deleteTap = function () {
        if (this.serialNumber.length === 1) {
            this.serialNumber = '请输入住院号';
        }
        else if (this.serialNumber !== '请输入住院号') {
            this.serialNumber = this.serialNumber.substring(0, this.serialNumber.length - 1);
        }
    };
    // 虚拟键盘确认按钮点击
    VirtualKeyboardComponent.prototype.confirmTap = function () {
        if (this.serialNumber !== '请输入住院号') {
            this.getInpatientInfo.emit(this.serialNumber);
        }
        this.closeVirtualKeyboard();
    };
    // 虚拟键盘取消按钮点击
    VirtualKeyboardComponent.prototype.cancelTap = function () {
        this.closeVirtualKeyboard();
    };
    // 虚拟键盘清空按钮点击
    VirtualKeyboardComponent.prototype.clearTap = function () {
        this.serialNumber = '请输入住院号';
    };
    // 关闭虚拟键盘
    VirtualKeyboardComponent.prototype.closeVirtualKeyboard = function () {
        this.virtualKeyboardShow = false;
        this.virtualKeyboardShowChange.emit(this.virtualKeyboardShow);
    };
    return VirtualKeyboardComponent;
}());
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], VirtualKeyboardComponent.prototype, "virtualKeyboardShow", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], VirtualKeyboardComponent.prototype, "virtualKeyboardShowChange", void 0);
__decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", Object)
], VirtualKeyboardComponent.prototype, "getInpatientInfo", void 0);
VirtualKeyboardComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-virtual-keyboard',
        template: __webpack_require__("../../../../../src/app/shared/virtual-keyboard/virtual-keyboard.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/virtual-keyboard/virtual-keyboard.component.css")]
    }),
    __metadata("design:paramtypes", [])
], VirtualKeyboardComponent);

//# sourceMappingURL=virtual-keyboard.component.js.map

/***/ }),

/***/ "../../../../../src/assets/external/external.js":
/***/ (function(module, exports) {

/**
 * 外部程序的转换器
 */

// 访问外壳
function requestServiceInJS(data, isDebugMode)
{
  // to shell: 3001^*^{"A":"a","B":"b"}
  // from shell: 3001, 0000^*^{"A":"a","B":"b"}

  // alert('send to shell: [' + data + ']');
  if (!isDebugMode) {
    // 正式环境，调用外壳
    // alert('send to shell: 【' + data + '】');
    window.external.RequestService(data);

  } else {
    // 内部测试环境，调用模拟程序
    console.log('---- call shell in debug mode -----');
    responseService('test', data);
  }

}

// 外壳的回调
function responseService(type, data)
{
  // alert('shell data: 【' + type + '】，【' + data + '】');
  window['angularComponentRef'].runThisFunctionFromOutside(type, data);
}


/***/ }),

/***/ "../../../../../src/assets/images/bg.png":
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "bg.2f7217ff615c8ddef72a.png";

/***/ }),

/***/ "../../../../../src/assets/lib/amfe-flexible-2.2.1/index.min.js":
/***/ (function(module, exports) {

!function(e,t){function n(){t.body?t.body.style.fontSize=12*o+"px":t.addEventListener("DOMContentLoaded",n)}function d(){var e=i.clientWidth/10;i.style.fontSize=e+"px"}var i=t.documentElement,o=e.devicePixelRatio||1;if(n(),d(),e.addEventListener("resize",d),e.addEventListener("pageshow",function(e){e.persisted&&d()}),o>=2){var a=t.createElement("body"),s=t.createElement("div");s.style.border=".5px solid transparent",a.appendChild(s),i.appendChild(a),1===s.offsetHeight&&i.classList.add("hairlines"),i.removeChild(a)}}(window,document);

/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
// The file contents for the current environment will overwrite these during build.
var environment = {
    production: true
};
//# sourceMappingURL=environment.js.map

/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/@angular/platform-browser-dynamic.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["enableProdMode"])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map