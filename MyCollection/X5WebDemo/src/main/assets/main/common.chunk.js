webpackJsonp(["common"],{

/***/ "../../../../../src/app/base-component/outpatient-billing-base/outpatient-billing-base.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*门诊缴费记录*/\r\n.content {\r\n  padding: 0.1953rem;\r\n}\r\n\r\n/*空记录*/\r\n.empty-box {\r\n  display: -webkit-box;\r\n  display: -ms-flexbox;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n      -ms-flex-align: center;\r\n          align-items: center;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n      -ms-flex-direction: column;\r\n          flex-direction: column;\r\n  font-size: 0.3125rem;\r\n  color: #666;\r\n}\r\n\r\n.empty-box img {\r\n  width: 1.5625rem;\r\n  height: 1.5625rem;\r\n  margin: 0.3906rem auto;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/base-component/outpatient-billing-base/outpatient-billing-base.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"common-page\">\r\n  \r\n  <!--头部-->\r\n  <app-main-header [timerShow]=\"true\" [timerMax]=\"120\"></app-main-header>\r\n  \r\n  <!--账单列表-->\r\n  <div class=\"content\">\r\n    \r\n    <!--item of list-->\r\n    <app-billing-item\r\n      *ngFor=\"let item of recordList\"\r\n      [showCheck]=\"true\"\r\n      [showExpand]=\"true\"\r\n      [showTimeDetail]=\"showTimeDetailInOutpatientBillingItem\"\r\n      [itemValue]=\"item\"\r\n      (changed)=\"itemSelectChangeHandle($event)\"></app-billing-item>\r\n    \r\n    <!--无记录-->\r\n    <div *ngIf=\"billListEmpty\" class=\"empty-box\">\r\n      <img src=\"assets/images/icon_blank.png\">\r\n      暂无您的门诊费用记录\r\n    </div>\r\n    \r\n  </div>\r\n  \r\n  <!--底部-->\r\n  <app-billing-bar\r\n    [showCheck]=\"true\"\r\n    [showMoney]=\"true\"\r\n    [showBtnPay]=\"true\"\r\n    [btnTitle]=\"btnTitle\"\r\n    [total]=\"billAmount\"\r\n    [isSelectAll]=\"true\"\r\n    (changed)=\"selectAllHandle($event)\"\r\n    (clicked)=\"btnPayClickHandle()\"\r\n  ></app-billing-bar>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/base-component/outpatient-billing-base/outpatient-billing-base.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OutpatientBillingBaseComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_app_core_libs_proxy_library__ = __webpack_require__("../../../../../src/app/core/libs/proxy-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__ = __webpack_require__("../../../../../src/app/core/services/log.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__ = __webpack_require__("../../../../../src/app/core/services/error.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__ = __webpack_require__("../../../../../src/app/core/services/loading.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__ = __webpack_require__("../../../../../src/app/core/services/router.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__ = __webpack_require__("../../../../../src/app/core/services/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_app_core_objects_info_message__ = __webpack_require__("../../../../../src/app/core/objects/info-message.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_app_core_objects_outpatient_pay_record_vo__ = __webpack_require__("../../../../../src/app/core/objects/outpatient-pay-record-vo.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__ = __webpack_require__("../../../../../src/app/core/libs/info-library.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_app_core_services_system_service__ = __webpack_require__("../../../../../src/app/core/services/system.service.ts");
// 门诊病人的缴费记录，未缴费
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};











var OutpatientBillingBaseComponent = (function () {
    function OutpatientBillingBaseComponent(routerService, userService, systemService, errorService, loadingService) {
        this.routerService = routerService;
        this.userService = userService;
        this.systemService = systemService;
        this.errorService = errorService;
        this.loadingService = loadingService;
        this.recordList = new Array(); // 账单明细
        this.billAmount = ''; // 费用账单总金额
        this.btnTitle = __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__["a" /* InfoLibrary */].tipsGotoPay; // 按钮上显示的名字
        this.billListEmpty = false; // 账单明细列表是否为空
        this.showTimeDetailInOutpatientBillingItem = __WEBPACK_IMPORTED_MODULE_1_app_core_libs_proxy_library__["a" /* ProxyLibrary */].getShowTimeDetailInOutpatientBillingItem();
    }
    OutpatientBillingBaseComponent.prototype.ngOnInit = function () {
        this.init();
    };
    // 全选 改变时触发
    OutpatientBillingBaseComponent.prototype.selectAllHandle = function (isSelectAll) {
        this.selectAll(isSelectAll);
    };
    // item 改变选中与否时触发
    OutpatientBillingBaseComponent.prototype.itemSelectChangeHandle = function (itemValueChanged) {
        if (itemValueChanged.isSelect === true) {
            // 保证其他 item 不被选中
            this.check4RadioBtn(itemValueChanged, this.recordList);
        }
        // 重新计算支付金额
        this.countAmount();
    };
    // 支付按钮被点击时触发
    OutpatientBillingBaseComponent.prototype.btnPayClickHandle = function () {
        this.goToPaymentAmountSelect();
    };
    OutpatientBillingBaseComponent.prototype.init = function () {
        this.getOutpatientPayRecords();
    };
    // 为保证单选框有效的校验，将其他项进行修改，以保证同时只有一个选中
    // itemValueChanged.isSelect 应保证为 true
    OutpatientBillingBaseComponent.prototype.check4RadioBtn = function (itemValueChanged, list) {
        if (itemValueChanged.isSelect === true) {
            // 保存
            this.selectedRecord = itemValueChanged;
            // 确保其他项不被选中
            list.forEach(function (itemValue) {
                if (itemValue !== itemValueChanged) {
                    itemValue.isSelect = false;
                }
            });
        }
    };
    // 设置为全选或全不选
    OutpatientBillingBaseComponent.prototype.selectAll = function (selectAll) {
        this.recordList.forEach(function (itemValue) {
            itemValue.isSelect = selectAll;
        });
    };
    // 从服务器获取门诊缴费记录（未缴费用）
    OutpatientBillingBaseComponent.prototype.getOutpatientPayRecords = function () {
        var _this = this;
        this.loadingService.showLoading();
        this.userService.getOutpatientPayRecordsFromServer().subscribe(function (data) { return _this.getOutpatientPayRecordsSuc(data); }, function (error) { return _this.getOutpatientPayRecordsErr(error); });
    };
    // 从服务器获取门诊缴费记录（未缴费用），成功
    OutpatientBillingBaseComponent.prototype.getOutpatientPayRecordsSuc = function (outputQueryOutpatientPayRecords) {
        var _this = this;
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].debug('getOutpatientPayRecordsSuc: ' + outputQueryOutpatientPayRecords);
        if (outputQueryOutpatientPayRecords.list) {
            // 构造内部的数据列表
            outputQueryOutpatientPayRecords.list.forEach(function (item) {
                if ((item != null) && (typeof item !== 'undefined')) {
                    var outpatientPayRecordVo = new __WEBPACK_IMPORTED_MODULE_8_app_core_objects_outpatient_pay_record_vo__["a" /* OutpatientPayRecordVo */](item, false);
                    _this.recordList.push(outpatientPayRecordVo);
                }
            });
            // 默认选中第一个
            var firstItem = this.recordList[0];
            firstItem.isSelect = true;
            this.check4RadioBtn(firstItem, this.recordList);
            // 计算需要付费的金额
            this.countAmount();
        }
        else {
            // 设置列表为空的标记
            this.billListEmpty = true;
        }
        this.loadingService.hiddenLoading();
    };
    // 从服务器获取门诊缴费记录（未缴费用），失败
    OutpatientBillingBaseComponent.prototype.getOutpatientPayRecordsErr = function (errorMsg) {
        __WEBPACK_IMPORTED_MODULE_2_app_core_services_log_service__["a" /* LogService */].error('getOutpatientPayRecordsErr: ' + errorMsg);
        this.loadingService.hiddenLoading();
        if (errorMsg !== __WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__["a" /* InfoLibrary */].networkError) {
            // 普通错误
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_7_app_core_objects_info_message__["a" /* InfoMessage */](errorMsg);
            this.errorService.showError(infoMessage);
        }
        else {
            // 网络错误，跳去指定UI
            this.systemService.gotoErrorWait();
        }
    };
    // 计算应付的总金额
    OutpatientBillingBaseComponent.prototype.countAmount = function () {
        var total = 0;
        this.recordList.forEach(function (outpatientPayRecordVo) {
            if (outpatientPayRecordVo.isSelect) {
                total += parseFloat(outpatientPayRecordVo.outpatientPayRecordsListItem.order_amount);
            }
        });
        total = Math.round(total * 100) / 100; // 圆整
        this.billAmount = total.toString();
    };
    // 跳转去支付界面
    OutpatientBillingBaseComponent.prototype.goToPaymentAmountSelect = function () {
        if (parseFloat(this.billAmount) === 0) {
            // 金额为0，只显示提示即可
            var infoMessage = new __WEBPACK_IMPORTED_MODULE_7_app_core_objects_info_message__["a" /* InfoMessage */](__WEBPACK_IMPORTED_MODULE_9_app_core_libs_info_library__["a" /* InfoLibrary */].paymentAmountError);
            infoMessage.noRouter = true; // 关闭时无需跳转
            infoMessage.autoCloseTime = 3000; // 自动关闭倒计时毫秒
            this.errorService.showError(infoMessage);
        }
        else {
            // 金额非0
            this.goToPaymentUI();
        }
    };
    // 跳转支付UI，各子类自行实现
    OutpatientBillingBaseComponent.prototype.goToPaymentUI = function () {
        // const payInfo: PayInfo = this.userService.getPayInfo();
        // payInfo.dataSrc = SqsrmyyLibrary.outpatientPayType;
        // payInfo.totalAmount = this.billAmount;
        // payInfo.visitID = this.selectedRecord.outpatientPayRecordsListItem.his_id;
        //
        // this.routerService.goTo([PathLibrary.paymentTypeSelect]);
    };
    return OutpatientBillingBaseComponent;
}());
OutpatientBillingBaseComponent = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-outpatient-billing-base',
        template: __webpack_require__("../../../../../src/app/base-component/outpatient-billing-base/outpatient-billing-base.component.html"),
        styles: [__webpack_require__("../../../../../src/app/base-component/outpatient-billing-base/outpatient-billing-base.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__["a" /* RouterService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5_app_core_services_router_service__["a" /* RouterService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6_app_core_services_user_service__["a" /* UserService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_10_app_core_services_system_service__["a" /* SystemService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_10_app_core_services_system_service__["a" /* SystemService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_app_core_services_error_service__["a" /* ErrorService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_core_services_loading_service__["a" /* LoadingService */]) === "function" && _e || Object])
], OutpatientBillingBaseComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=outpatient-billing-base.component.js.map

/***/ })

});
//# sourceMappingURL=common.chunk.js.map