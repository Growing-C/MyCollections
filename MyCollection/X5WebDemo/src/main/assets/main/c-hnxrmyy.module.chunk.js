webpackJsonp(["c-hnxrmyy.module"],{

/***/ "../../../../../src/app/c-hnxrmyy/c-hnxrmyy.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CHnxrmyyModule", function() { return CHnxrmyyModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__techiediaries_ngx_qrcode__ = __webpack_require__("../../../../@techiediaries/ngx-qrcode/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_c_hnxrmyy_hnxrmyy_router__ = __webpack_require__("../../../../../src/app/c-hnxrmyy/hnxrmyy-router.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_c_hnxrmyy_outpatient_billing_outpatient_billing_component__ = __webpack_require__("../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_c_hnxrmyy_payment_result_payment_result_component__ = __webpack_require__("../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.ts");
// 怀宁县人民医院 定制模块
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var CHnxrmyyModule = (function () {
    function CHnxrmyyModule() {
    }
    return CHnxrmyyModule;
}());
CHnxrmyyModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"],
            __WEBPACK_IMPORTED_MODULE_3_app_shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_2__techiediaries_ngx_qrcode__["a" /* NgxQRCodeModule */],
            __WEBPACK_IMPORTED_MODULE_4_app_c_hnxrmyy_hnxrmyy_router__["a" /* HnxrmyyRouter */]
        ],
        declarations: [
            __WEBPACK_IMPORTED_MODULE_5_app_c_hnxrmyy_outpatient_billing_outpatient_billing_component__["a" /* OutpatientBillingComponent */],
            __WEBPACK_IMPORTED_MODULE_6_app_c_hnxrmyy_payment_result_payment_result_component__["a" /* PaymentResultComponent */]
        ]
    }),
    __metadata("design:paramtypes", [])
], CHnxrmyyModule);

//# sourceMappingURL=c-hnxrmyy.module.js.map

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/hnxrmyy-router.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HnxrmyyRouter; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_c_hnxrmyy_outpatient_billing_outpatient_billing_component__ = __webpack_require__("../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_c_hnxrmyy_payment_result_payment_result_component__ = __webpack_require__("../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.ts");
// 模块内的路由设置




var routes = [
    { path: __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].outpatientBilling, component: __WEBPACK_IMPORTED_MODULE_2_app_c_hnxrmyy_outpatient_billing_outpatient_billing_component__["a" /* OutpatientBillingComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentResult, component: __WEBPACK_IMPORTED_MODULE_3_app_c_hnxrmyy_payment_result_payment_result_component__["a" /* PaymentResultComponent */] }
];
var HnxrmyyRouter = __WEBPACK_IMPORTED_MODULE_0__angular_router__["c" /* RouterModule */].forChild(routes);
//# sourceMappingURL=hnxrmyy-router.js.map

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*门诊缴费记录*/\r\n.content {\r\n  padding: 0.1953rem;\r\n}\r\n\r\n/*空记录*/\r\n.empty-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n}\r\n\r\n.empty-box img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.3906rem auto;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"common-page\">\r\n  \r\n  <!--头部-->\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"120\"></app-main-header>\r\n  \r\n  <!--账单列表-->\r\n  <div class=\"content\">\r\n    \r\n    <!--item of list-->\r\n    <app-billing-item\r\n      *ngFor=\"let item of recordList\"\r\n      [showCheck]=\"true\"\r\n      [showExpand]=\"true\"\r\n      [showTimeDetail]=\"showTimeDetailInOutpatientBillingItem\"\r\n      [itemValue]=\"item\"\r\n      (changed)=\"itemSelectChangeHandle($event)\"\r\n    ></app-billing-item>\r\n    \r\n    <!--无记录-->\r\n    <div *ngIf=\"billListEmpty\" class=\"empty-box\">\r\n      <img src=\"assets/images/icon_blank.png\">\r\n      暂无您的门诊费用记录\r\n    </div>\r\n  \r\n  </div>\r\n  \r\n  <!--底部-->\r\n  <app-billing-bar\r\n    [showCheck]=\"false\"\r\n    [showMoney]=\"true\"\r\n    [showBtnPay]=\"true\"\r\n    [btnTitle]=\"btnTitle\"\r\n    [total]=\"billAmount\"\r\n    [isSelectAll]=\"true\"\r\n    (changed)=\"selectAllHandle($event)\"\r\n    (clicked)=\"btnPayClickHandle()\"\r\n  ></app-billing-bar>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutpatientBillingComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_objects_pay_info__ = __webpack_require__("../../../../../src/app/core/objects/pay-info.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_base_component_outpatient_billing_base_outpatient_billing_base_component__ = __webpack_require__("../../../../../src/app/base-component/outpatient-billing-base/outpatient-billing-base.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_libs_proxy_library__ = __webpack_require__("../../../../../src/app/core/libs/proxy-library.ts");
// 门诊缴费记录
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





var OutpatientBillingComponent = (function (_super) {
    __extends(OutpatientBillingComponent, _super);
    function OutpatientBillingComponent() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    // 跳转支付类型选择UI
    OutpatientBillingComponent.prototype.goToPaymentUI = function () {
        var payInfo = new __WEBPACK_IMPORTED_MODULE_2_app_core_objects_pay_info__["a" /* PayInfo */]();
        payInfo.dataSrc = __WEBPACK_IMPORTED_MODULE_4_app_core_libs_proxy_library__["a" /* ProxyLibrary */].getOutpatientPayDataSrc();
        payInfo.totalAmount = this.billAmount;
        payInfo.visitID = this.selectedRecord.outpatientPayRecordsListItem.his_id;
        // 跳向 支付类型选择UI
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentTypeSelect, payInfo]);
    };
    return OutpatientBillingComponent;
}(__WEBPACK_IMPORTED_MODULE_3_app_base_component_outpatient_billing_base_outpatient_billing_base_component__["a" /* OutpatientBillingBaseComponent */]));
OutpatientBillingComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-outpatient-billing',
        template: __webpack_require__("../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.html"),
        styles: [__webpack_require__("../../../../../src/app/c-hnxrmyy/outpatient-billing/outpatient-billing.component.css")]
    })
], OutpatientBillingComponent);

//# sourceMappingURL=outpatient-billing.component.js.map

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*支付结果*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n}\r\n\r\n.success {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  text-align: center;\r\n  height: 100%;\r\n}\r\n\r\n.result-box {\r\n  font-size: 0.2812rem;\r\n  line-height: 0.4297rem;\r\n  color: #666;\r\n  width: 3.5938rem;\r\n}\r\n\r\n.result-box .success-tips {\r\n  font-size: 0.4375rem;\r\n  color: #333;\r\n  margin: 0.3125rem auto 0.2344rem;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n}\r\n\r\n.result-box img {\r\n  width: 1.7969rem;\r\n  height: 1.7969rem;\r\n}\r\n\r\n.gray-line {\r\n  width: 1px;\r\n  height: 2.5469rem;\r\n  border-left: 1px solid #ddd;\r\n  margin: 0 0.3906rem;\r\n}\r\n\r\n.phone {\r\n  width: 1.8594rem;\r\n  height: 3.2656rem;\r\n}\r\n\r\n.tips {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.wechat-box .tips > span {\r\n  color: #09bb07;\r\n}\r\n\r\n.alipay-box .tips > span {\r\n  color: #00aaf2;\r\n}\r\n\r\n.fail {\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.fail img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.7812rem auto 0.3906rem;\r\n}\r\n\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.html":
/***/ (function(module, exports) {

module.exports = "<!--支付结果-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo [returnBtnShow]=\"true\" [timerShow]=\"true\" [timerMax]=\"60\"></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    \r\n    <!--支付成功-->\r\n    <div class=\"success\" *ngIf=\"paymentResult==='success'\">\r\n      <div class=\"result-box\">\r\n        <img src=\"assets/images/icon_succeed.png\">\r\n        <div class=\"success-tips\">{{tips}}</div>\r\n        <div *ngIf=\"dataSrc==='prepaySrc'\">可用金额：<span class=\"money\">¥{{inpatientInfo.remain_amt}}</span></div>\r\n        <div *ngIf=\"dataSrc==='prepaySrc'\">充值金额：¥{{orderAmount}}</div>\r\n        <div *ngIf=\"dataSrc!=='prepaySrc'\">门诊缴费金额：¥{{orderAmount}}</div>\r\n      </div>\r\n      <div class=\"gray-line\"></div>\r\n      <div class=\"wechat-box\" *ngIf=\"paymentType==='WeChat'\">\r\n        <img src=\"assets/images/iPhone_weixin.png\" class=\"phone\">\r\n        <div class=\"tips\">打开<span>微信</span>-微信支付查看支付详情</div>\r\n      </div>\r\n      <div class=\"alipay-box\" *ngIf=\"paymentType==='AliPay'\">\r\n        <img src=\"assets/images/iPhone_zhifubao.png\" class=\"phone\">\r\n        <div class=\"tips\">打开<span>支付宝</span>-支付助手查看支付详情</div>\r\n      </div>\r\n    </div>\r\n    \r\n    <!--支付失败-->\r\n    <div *ngIf=\"paymentResult==='fail'||paymentResult==='HISFail'\" class=\"fail\">\r\n      <img src=\"assets/images/icon_fail.png\">\r\n      <div *ngIf=\"paymentResult=='fail'\">很抱歉，当前缴费支付异常，请您重新缴费。</div>\r\n      <div *ngIf=\"paymentResult=='HISFail'\">很抱歉，当前缴费支付异常，请到医院窗口办理退费。</div>\r\n    </div>\r\n    \r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.ts":
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
            // 显示文字
            if (_this.dataSrc === __WEBPACK_IMPORTED_MODULE_2_app_core_libs_pay_library__["a" /* PayLibrary */].dataSrcHIS) {
                _this.tips = __WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__["a" /* InfoLibrary */].tipsPaySuc; // 支付成功
            }
            else {
                _this.tips = __WEBPACK_IMPORTED_MODULE_8_app_core_libs_info_library__["a" /* InfoLibrary */].tipsDepositSuc; // 充值成功
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
        template: __webpack_require__("../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.html"),
        styles: [__webpack_require__("../../../../../src/app/c-hnxrmyy/payment-result/payment-result.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__["a" /* SystemService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object])
], PaymentResultComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=payment-result.component.js.map

/***/ })

});
//# sourceMappingURL=c-hnxrmyy.module.chunk.js.map