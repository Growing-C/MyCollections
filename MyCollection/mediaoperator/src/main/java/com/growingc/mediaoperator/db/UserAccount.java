package com.growingc.mediaoperator.db;

/**
 * Description :用户帐号信息
 * Author :cgy
 * Date :2018/10/16
 */
public class UserAccount {

    private String act;//帐号
    private String pwd;//密码
    private String phone;//手机号
    private String email;//邮箱
    private String name;//名字
    private String ethaddr;//以太坊钱包地址
    private String sex;//性别
    private String uid; //用户id
    private String publicKey; //用户公钥
    private String privateKey; //用户私钥

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEthaddr() {
        return ethaddr;
    }

    public void setEthaddr(String ethaddr) {
        this.ethaddr = ethaddr;
    }

    @Override
    public String toString() {
        return "uid:"+uid+"-帐号:" + act + "-密码:" + pwd + "-手机号:" + phone + "-邮箱:" + email + "-名字:" +
                name + "-性别:" + sex + "-publickey:" + publicKey + "-privateKey:" + privateKey;
    }
}
