package com.cgy.mycollections.functions.ethereum.contract;

/**
 * 合约信息
 * @author lvqiu
 * @date 2018年9月14日
 * @time 上午9:24:55
 *
 */
public class ContractDto {
    private String address;
    private String version;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }


}
