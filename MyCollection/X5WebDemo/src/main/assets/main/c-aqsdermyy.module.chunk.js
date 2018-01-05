webpackJsonp(["c-aqsdermyy.module"],{

/***/ "../../../../../src/app/c-aqsdermyy/aqsdermyy-router.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AqsdermyyRouter; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_c_aqsdermyy_outpatient_billing_outpatient_billing_component__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_c_aqsdermyy_payment_payment_component__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/payment/payment.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_c_aqsdermyy_payment_result_payment_result_component__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.ts");
// 模块内的路由设置





var routes = [
    { path: __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].outpatientBilling, component: __WEBPACK_IMPORTED_MODULE_2_app_c_aqsdermyy_outpatient_billing_outpatient_billing_component__["a" /* OutpatientBillingComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].payment, component: __WEBPACK_IMPORTED_MODULE_3_app_c_aqsdermyy_payment_payment_component__["a" /* PaymentComponent */] },
    { path: __WEBPACK_IMPORTED_MODULE_1_app_core_libs_path_library__["a" /* PathLibrary */].paymentResult, component: __WEBPACK_IMPORTED_MODULE_4_app_c_aqsdermyy_payment_result_payment_result_component__["a" /* PaymentResultComponent */] }
];
var AqsdermyyRouter = __WEBPACK_IMPORTED_MODULE_0__angular_router__["c" /* RouterModule */].forChild(routes);
//# sourceMappingURL=aqsdermyy-router.js.map

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/c-aqsdermyy.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CAqsdermyyModule", function() { return CAqsdermyyModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__techiediaries_ngx_qrcode__ = __webpack_require__("../../../../@techiediaries/ngx-qrcode/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_c_aqsdermyy_aqsdermyy_router__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/aqsdermyy-router.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_c_aqsdermyy_outpatient_billing_outpatient_billing_component__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_c_aqsdermyy_payment_payment_component__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/payment/payment.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_c_aqsdermyy_payment_result_payment_result_component__ = __webpack_require__("../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.ts");
// 安庆市第二人民医院 定制模块
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};








var CAqsdermyyModule = (function () {
    function CAqsdermyyModule() {
    }
    return CAqsdermyyModule;
}());
CAqsdermyyModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"],
            __WEBPACK_IMPORTED_MODULE_3_app_shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_2__techiediaries_ngx_qrcode__["a" /* NgxQRCodeModule */],
            __WEBPACK_IMPORTED_MODULE_4_app_c_aqsdermyy_aqsdermyy_router__["a" /* AqsdermyyRouter */]
        ],
        declarations: [
            __WEBPACK_IMPORTED_MODULE_5_app_c_aqsdermyy_outpatient_billing_outpatient_billing_component__["a" /* OutpatientBillingComponent */],
            __WEBPACK_IMPORTED_MODULE_6_app_c_aqsdermyy_payment_payment_component__["a" /* PaymentComponent */],
            __WEBPACK_IMPORTED_MODULE_7_app_c_aqsdermyy_payment_result_payment_result_component__["a" /* PaymentResultComponent */]
        ]
    }),
    __metadata("design:paramtypes", [])
], CAqsdermyyModule);

//# sourceMappingURL=c-aqsdermyy.module.js.map

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*门诊缴费记录*/\r\n.content {\r\n  padding: 0.1953rem;\r\n}\r\n\r\n/*余额*/\r\n.total-amount {\r\n  font-size: 0.2812rem;\r\n  color: #333;\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  /*justify-content: center;*/\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.total-amount > div {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  width: 4.75rem;\r\n}\r\n\r\n.total-amount img {\r\n  width: 0.3438rem;\r\n  height: 0.3438rem;\r\n  margin-right: 0.1953rem;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n  margin-left: 0.2344rem;\r\n  width: 1.85rem;\r\n  display: inline-block;\r\n  text-align: left;\r\n}\r\n\r\n/*提示信息*/\r\n.tips {\r\n  width: 1.8516rem;\r\n  height: 0.3125rem;\r\n  line-height: 0.3125rem;\r\n  font-size: 0.2188rem;\r\n  color: #ca9951;\r\n  background-color: #fdf8e0;\r\n  margin: 0.0781rem 0 0 0.4297rem;\r\n  border-radius: 0.0312rem;\r\n  text-align: center;\r\n}\r\n\r\n/*空记录*/\r\n.empty-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n}\r\n\r\n.empty-box img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.3906rem auto;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"common-page\">\r\n  \r\n  <!--头部-->\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"120\"></app-main-header>\r\n  \r\n  <!--账单列表-->\r\n  <div class=\"content\">\r\n    \r\n    <!--账户余额及支付费用合计-->\r\n    <div class=\"total-amount\">\r\n      <div>\r\n        <img src=\"assets/images/icon_balance.png\">\r\n        <div>\r\n          <span>一卡通余额</span>\r\n          <span class=\"money\">¥ {{outpatientInfo.balance}}</span>\r\n        </div>\r\n      </div>\r\n      <div *ngIf=\"!billListEmpty\">\r\n        <img src=\"assets/images/icon_money.png\">\r\n        <div>\r\n          <span>门诊费用合计</span>\r\n          <span class=\"money\">¥ {{billAmount}}</span>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    \r\n    <!--余额不足的提示-->\r\n    <div class=\"tips\" *ngIf=\"!billListEmpty&&tipsShow\">余额不足，请充值</div>\r\n    \r\n    <!--item of list-->\r\n    <app-billing-item\r\n      *ngFor=\"let item of recordList\"\r\n      [showCheck]=\"false\"\r\n      [showExpand]=\"false\"\r\n      [showTimeDetail]=\"showTimeDetailInOutpatientBillingItem\"\r\n      [itemValue]=\"item\"\r\n    ></app-billing-item>\r\n    \r\n    <!--无记录-->\r\n    <div *ngIf=\"billListEmpty\" class=\"empty-box\">\r\n      <img src=\"assets/images/icon_blank.png\">\r\n      暂无您的门诊费用记录\r\n    </div>\r\n  \r\n  </div>\r\n  \r\n  <!--底部-->\r\n  <app-billing-bar\r\n    *ngIf=\"tipsShow\"\r\n    [showCheck]=\"false\"\r\n    [showMoney]=\"false\"\r\n    [showBtnPay]=\"true\"\r\n    [btnTitle]=\"btnTitle\"\r\n    [total]=\"billAmount\"\r\n    [isSelectAll]=\"true\"\r\n    (clicked)=\"btnPayClickHandle()\"\r\n  ></app-billing-bar>\r\n\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutpatientBillingComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_proxy_library__ = __webpack_require__("../../../../../src/app/core/libs/proxy-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__ = __webpack_require__("../../../../../src/app/core/libs/path-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_objects_outpatient_pay_record_vo__ = __webpack_require__("../../../../../src/app/core/objects/outpatient-pay-record-vo.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
// 门诊缴费记录
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};












var OutpatientBillingComponent = (function () {
    function OutpatientBillingComponent(routerService, userService, systemService, errorService, loadingService) {
        this.routerService = routerService;
        this.userService = userService;
        this.systemService = systemService;
        this.errorService = errorService;
        this.loadingService = loadingService;
        this.recordList = new Array(); // 账单明细
        this.billAmount = ''; // 费用账单总金额
        this.btnTitle = __WEBPACK_IMPORTED_MODULE_10_app_core_libs_info_library__["a" /* InfoLibrary */].tipsGotoDeposit; // 按钮上显示的名字
        this.billListEmpty = false; // 账单明细列表是否为空
        this.debt = ''; // 需要补缴的金额
        this.tipsShow = false;
        this.showTimeDetailInOutpatientBillingItem = __WEBPACK_IMPORTED_MODULE_1_app_core_libs_proxy_library__["a" /* ProxyLibrary */].getShowTimeDetailInOutpatientBillingItem();
    }
    OutpatientBillingComponent.prototype.ngOnInit = function () {
        this.outpatientInfo = this.userService.getOutPatientInfo();
        this.getOutpatientPayRecords();
    };
    // 支付按钮被点击时触发
    OutpatientBillingComponent.prototype.btnPayClickHandle = function () {
        this.goToPaymentAmountSelect();
    };
    // 从服务器获取门诊缴费记录（未缴费用）
    OutpatientBillingComponent.prototype.getOutpatientPayRecords = function () {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getOutpatientPayRecordsFromServer().subscribe(function (data) { return _this.getOutpatientPayRecordsSuc(data); }, function (error) { return _this.getOutpatientPayRecordsErr(error); });
    };
    // 从服务器获取门诊缴费记录（未缴费用），成功
    OutpatientBillingComponent.prototype.getOutpatientPayRecordsSuc = function (outputQueryOutpatientPayRecords) {
        var _this = this;
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].debug('getOutpatientPayRecordsSuc: ' + outputQueryOutpatientPayRecords);
        if (outputQueryOutpatientPayRecords.list) {
            // 构造内部的数据列表
            outputQueryOutpatientPayRecords.list.forEach(function (item) {
                if ((item != null) && (typeof item !== 'undefined')) {
                    var outpatientPayRecordVo = new __WEBPACK_IMPORTED_MODULE_9_app_core_objects_outpatient_pay_record_vo__["a" /* OutpatientPayRecordVo */](item, false);
                    _this.recordList.push(outpatientPayRecordVo);
                }
            });
            // 计算需要付费的金额
            this.countBillAmount(outputQueryOutpatientPayRecords);
        }
        else {
            // 设置列表为空的标记
            this.billListEmpty = true;
        }
        this.loadingService.hiddenLoading();
    };
    // 从服务器获取门诊缴费记录（未缴费用），失败
    OutpatientBillingComponent.prototype.getOutpatientPayRecordsErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_3_app_core_services_log_service__["a" /* LogService */].error('getOutpatientPayRecordsErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_10_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
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
    OutpatientBillingComponent.prototype.countBillAmount = function (outputQueryOutpatientPayRecords) {
        var amount = 0;
        var balance_amt = parseFloat(outputQueryOutpatientPayRecords.balance_amt);
        // const balance_amt = 0;
        for (var i = 0; i < outputQueryOutpatientPayRecords.list.length; i++) {
            amount += parseFloat(outputQueryOutpatientPayRecords.list[i].order_amount);
        }
        amount = Math.round(amount * 100) / 100;
        this.billAmount = amount.toString();
        if (balance_amt < amount) {
            this.tipsShow = true;
            var debt = amount - balance_amt;
            this.debt = (Math.round(debt * 100) / 100).toString();
        }
    };
    OutpatientBillingComponent.prototype.goToPaymentAmountSelect = function () {
        var debtObj = {
            debt: this.debt,
            dataSrc: __WEBPACK_IMPORTED_MODULE_1_app_core_libs_proxy_library__["a" /* ProxyLibrary */].getOutpatientPayDataSrc()
        };
        this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_2_app_core_libs_path_library__["a" /* PathLibrary */].paymentAmountSelect, debtObj]);
    };
    return OutpatientBillingComponent;
}());
OutpatientBillingComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-outpatient-billing',
        template: __webpack_require__("../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.html"),
        styles: [__webpack_require__("../../../../../src/app/c-aqsdermyy/outpatient-billing/outpatient-billing.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_11_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_11_app_core_services_system_service__["a" /* SystemService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object])
], OutpatientBillingComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=outpatient-billing.component.js.map

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*支付结果*/\r\n.content {\r\n  height: 100%;\r\n  background-color: #fff;\r\n}\r\n\r\n.success {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: center;\r\n      -ms-flex-pack: center;\r\n          justify-content: center;\r\n  text-align: center;\r\n  height: 100%;\r\n}\r\n\r\n.result-box {\r\n  font-size: 0.2812rem;\r\n  line-height: 0.4297rem;\r\n  color: #666;\r\n  width: 3.5938rem;\r\n}\r\n\r\n.result-box .success-tips {\r\n  font-size: 0.4375rem;\r\n  color: #333;\r\n  margin: 0.3125rem auto 0.2344rem;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n}\r\n\r\n.result-box img {\r\n  width: 1.7969rem;\r\n  height: 1.7969rem;\r\n}\r\n\r\n.gray-line {\r\n  width: 1px;\r\n  height: 2.5469rem;\r\n  border-left: 1px solid #ddd;\r\n  margin: 0 0.3906rem;\r\n}\r\n\r\n.phone {\r\n  width: 1.8594rem;\r\n  height: 3.2656rem;\r\n}\r\n\r\n.tips {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.wechat-box .tips > span {\r\n  color: #09bb07;\r\n}\r\n\r\n.alipay-box .tips > span {\r\n  color: #00aaf2;\r\n}\r\n\r\n.fail {\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.fail img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.7812rem auto 0.3906rem;\r\n}\r\n\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.html":
/***/ (function(module, exports) {

module.exports = "<!--支付结果-->\r\n<div class=\"common-page\">\r\n  <app-main-header-logo [returnBtnShow]=\"true\" [timerShow]=\"true\" [timerMax]=\"60\"></app-main-header-logo>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"success\" *ngIf=\"paymentResult==='success'\">\r\n      <div class=\"result-box\">\r\n        <img src=\"assets/images/icon_succeed.png\">\r\n        <div class=\"success-tips\">{{tips}}</div>\r\n        <div *ngIf=\"dataSrc!=='prepaySrc'\">可用金额：<span class=\"money\">¥{{outpatientInfo.balance}}</span></div>\r\n        <div *ngIf=\"dataSrc==='prepaySrc'\">可用金额：<span class=\"money\">¥{{inpatientInfo.remain_amt}}</span></div>\r\n        <div>充值金额：¥{{orderAmount}}</div>\r\n      </div>\r\n      <div class=\"gray-line\"></div>\r\n      <div class=\"wechat-box\" *ngIf=\"paymentType==='WeChat'\">\r\n        <img src=\"assets/images/iPhone_weixin.png\" class=\"phone\">\r\n        <div class=\"tips\">打开<span>微信</span>-微信支付查看支付详情</div>\r\n      </div>\r\n      <div class=\"alipay-box\" *ngIf=\"paymentType==='AliPay'\">\r\n        <img src=\"assets/images/iPhone_zhifubao.png\" class=\"phone\">\r\n        <div class=\"tips\">打开<span>支付宝</span>-支付助手查看支付详情</div>\r\n      </div>\r\n    </div>\r\n    <div *ngIf=\"paymentResult==='fail'||paymentResult==='HISFail'\" class=\"fail\">\r\n      <img src=\"assets/images/icon_fail.png\">\r\n      <div *ngIf=\"paymentResult=='fail'\">很抱歉，当前缴费支付异常，请您重新缴费。</div>\r\n      <div *ngIf=\"paymentResult=='HISFail'\">很抱歉，当前缴费支付异常，请到医院窗口办理退费。</div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.ts":
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
        template: __webpack_require__("../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.html"),
        styles: [__webpack_require__("../../../../../src/app/c-aqsdermyy/payment-result/payment-result.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_9_app_core_services_system_service__["a" /* SystemService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object])
], PaymentResultComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=payment-result.component.js.map

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/payment/payment.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*扫描二维码支付页面*/\r\n.content {\r\n  padding: 0.1562rem 0.1953rem;\r\n  height: 100%;\r\n}\r\n\r\n.balance {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n}\r\n\r\n.code-box {\r\n  background-color: #fff;\r\n  margin-top: 0.1562rem;\r\n  padding: 0.1562rem 1.4062rem;\r\n  font-size: 0.3594rem;\r\n  color: #333;\r\n}\r\n\r\n.money {\r\n  color: #ff5a4d;\r\n  margin-left: 0.35rem;\r\n}\r\n\r\n.payment-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-pack: justify;\r\n      -ms-flex-pack: justify;\r\n          justify-content: space-between;\r\n  text-align: center;\r\n}\r\n\r\n.phone {\r\n  width: 1.8594rem;\r\n  height: 3.2656rem;\r\n}\r\n\r\n.wechat-tips, .alipay-tips {\r\n  font-size: 0.25rem;\r\n  color: #666;\r\n  text-align: center;\r\n}\r\n\r\n.wechat-tips > span {\r\n  color: #09bb07;\r\n}\r\n\r\n.alipay-tips > span {\r\n  color: #00aaf2;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/payment/payment.component.html":
/***/ (function(module, exports) {

module.exports = "<!--扫描二维码支付页面-->\r\n<div class=\"common-page\">\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"120\" [dataSrc]=\"dataSrc\"></app-main-header>\r\n\r\n  <div class=\"content\">\r\n    <div class=\"balance\" *ngIf=\"dataSrc!=='prepaySrc'\">当前余额 ¥{{outpatientInfo.balance}}</div>\r\n    <div class=\"balance\" *ngIf=\"dataSrc==='prepaySrc'\">当前余额 ¥{{inpatientInfo.remain_amt}}</div>\r\n    <div class=\"code-box\">\r\n      <div>缴费金额<span class=\"money\">¥ {{totalAmount}}</span></div>\r\n      <div class=\"payment-box\">\r\n        <ngx-qrcode\r\n          [qrc-element-type]=\"'img'\"\r\n          [qrc-value]=\"payUrl\"\r\n          qrc-class=\"QRCode\"\r\n          qrc-errorCorrectionLevel=\"L\">\r\n        </ngx-qrcode>\r\n        <div *ngIf=\"paymentType==='WeChat'\"><img src=\"assets/images/iPhone_weixin.png\" class=\"phone\">\r\n          <div class=\"wechat-tips\">打开<span>微信</span>扫一扫支付</div>\r\n        </div>\r\n        <div *ngIf=\"paymentType==='AliPay'\"><img src=\"assets/images/iPhone_zhifubao.png\" class=\"phone\">\r\n          <div class=\"alipay-tips\">打开<span>支付宝</span>扫一扫支付</div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/c-aqsdermyy/payment/payment.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PaymentComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_libs_proxy_library__ = __webpack_require__("../../../../../src/app/core/libs/proxy-library.ts");
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
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_2_app_core_libs_proxy_library__["a" /* ProxyLibrary */].getPaymentResult(), paymentObj]);
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
            this.routerService.goTo([__WEBPACK_IMPORTED_MODULE_2_app_core_libs_proxy_library__["a" /* ProxyLibrary */].getPaymentResult(), paymentObj]);
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
        template: __webpack_require__("../../../../../src/app/c-aqsdermyy/payment/payment.component.html"),
        styles: [__webpack_require__("../../../../../src/app/c-aqsdermyy/payment/payment.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_router_service__["a" /* RouterService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8_app_core_services_system_service__["a" /* SystemService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_user_service__["a" /* UserService */]) === "function" && _d || Object])
], PaymentComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=payment.component.js.map

/***/ })

});
//# sourceMappingURL=c-aqsdermyy.module.chunk.js.map