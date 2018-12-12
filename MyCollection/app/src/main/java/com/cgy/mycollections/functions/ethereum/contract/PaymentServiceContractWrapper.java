package com.cgy.mycollections.functions.ethereum.contract;

import com.cgy.mycollections.functions.ethereum.contract.ContractWrapper;
import com.cgy.mycollections.utils.L;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;


public class PaymentServiceContractWrapper extends ContractWrapper {


    @SuppressWarnings("rawtypes")
    public String createBillPayment(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String customeraddress, BigInteger orderValue, byte[] data) {
        String txHash = null;
        try {
            Function function = new Function("merchantRequestPreApproval",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(customeraddress),
                            new Uint256(orderValue),
                            new DynamicBytes(data)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);

            // get the next available nonce
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            // create our transaction
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    encodedFunction);
            // sign and encode
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            // send
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            // waitForTransactionReceipt(transactionHash);
            L.e("CreateBillPayment Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CreateBillPayment Failed!");
        }
        return txHash;
    }

    @SuppressWarnings("rawtypes")
    public String settleBillPayment(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String caddr, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("merchantPreApprovalSettle",
                    Arrays.<Type>asList(
                            new Address(caddr),
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            // get the next available nonce
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            // create our transaction
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    encodedFunction);
            // sign and encode
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            // send
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            // waitForTransactionReceipt(transactionHash);
            L.e("CreateBillPayment Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CreateBillPayment Failed!");
        }
        return txHash;
    }


    /***
     * @author lvqiu
     * @description 商家注册
     * @param credentials
     * @param nonce
     * @param merchantAddress
     * @param merchantName
     * @param appId
     * @param appName
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String merchantRegister(Web3j web3j, Credentials credentials, BigInteger nonce, String merchantAddress, String merchantName, int appId, String appName) {
        String txHash = null;
        try {
            Function function = new Function("merchantRegister",
                    Arrays.<Type>asList(
                            new Address(merchantAddress),
                            new Utf8String(merchantName),
                            new Uint32(appId),
                            new Utf8String(appName)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            // get the next available nonce
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            // create our transaction
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    encodedFunction);
            // sign and encode
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            // send
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            // waitForTransactionReceipt(transactionHash);
            L.e("CreateBillPayment Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CreateBillPayment Failed!");
        }
        return txHash;
    }


    /***
     * @author lvqiu
     * @description 商户审核
     * @param credentials
     * @param nonce
     * @param merchantAddress
     * @param appId
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String merchantAudit(Web3j web3j, Credentials credentials, BigInteger nonce, String merchantAddress, int appId, int merchantStatus) {
        String txHash = null;
        try {
            Function function = new Function("merchantAudit",
                    Arrays.<Type>asList(
                            new Address(merchantAddress),
                            new Uint32(appId),
                            new Uint8(merchantStatus)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            // get the next available nonce
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            // create our transaction
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    encodedFunction);
            // sign and encode
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            // send
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            // waitForTransactionReceipt(transactionHash);
            L.e("CreateBillPayment Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CreateBillPayment Failed!");
        }
        return txHash;
    }

    /***
     * @author lvqiu
     * @description 商家请求支付 2.0
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param customeraddress
     * @param orderValue
     * @param data
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String merchantPayRequest(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String customeraddress, BigInteger orderValue, byte[] data) {
        String txHash = null;
        try {
            Function function = new Function("merchantPayRequest",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(customeraddress),
                            new Uint256(orderValue),
                            new DynamicBytes(data)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("CreateBillPayment Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CreateBillPayment Failed!");
        }
        return txHash;
    }


    /***
     * @author lvqiu
     * @description 顾客支付 2.0
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param merchantAddress
     * @param orderValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String customerPayRequest(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String merchantAddress, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("customerPayRequest",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(merchantAddress),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    orderValue, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("CreateBillPayment Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CreateBillPayment Failed!");
        }
        return txHash;
    }

    /***
     *   顾客取消（未支付）  条件：原状态=0 即未支付
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param merchantAddress
     * @param orderValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String customerCancelUnpaid(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String merchantAddress, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("customerCancelUnpaid",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(merchantAddress),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    orderValue, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("CustomerCancelUnpaid Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CustomerCancelUnpaid Failed!");
        }
        return txHash;
    }

    /***
     *   顾客取消（已支付）条件：原状态=20 即支付完成  此时不会完成退款动作
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param merchantAddress
     * @param orderValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String customerCancelPaid(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String merchantAddress, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("customerCancelPaid",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(merchantAddress),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    orderValue, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("CustomerCancelPaid Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("CustomerCancelPaid Failed!");
        }
        return txHash;
    }


    /***
     *   商家取消（已支付）  条件：原状态=0 即未支付
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param orderValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String merchantCancelUnpaid(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String customerAddress, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("merchantCancelUnpaid",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(customerAddress),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    orderValue, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("MerchantCancelUnpaid Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("MerchantCancelUnpaid Failed!");
        }
        return txHash;
    }

    /***
     *   商家取消（已支付） 条件：原状态=20 即支付完成  此时完成退款
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param orderValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String merchantCancelPaid(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String customerAddress, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("merchantCancelPaid",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(customerAddress),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    orderValue, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("MerchantCancelPaid Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("MerchantCancelPaid Failed!");
        }
        return txHash;
    }


    /***
     *   商家取消承认（已支付）即同意取消并退款  条件：原状态=23 即商家取消(退款完成)
     * @param credentials
     * @param nonce
     * @param appId
     * @param paymentId
     * @param orderValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String merchantCancelAccept(Web3j web3j, Credentials credentials, BigInteger nonce, int appId, long paymentId, String customerAddress, BigInteger orderValue) {
        String txHash = null;
        try {
            Function function = new Function("merchantCancelAccept",
                    Arrays.<Type>asList(
                            new Uint32(appId),
                            new Uint256(paymentId),
                            new Address(customerAddress),
                            new Uint256(orderValue)),
                    Collections.<TypeReference<?>>emptyList());
            String encodedFunction = FunctionEncoder.encode(function);
            if (nonce == null)
                nonce = getNonce(web3j,credentials);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
                    orderValue, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            assertTransaction(ethSendTransaction);
            txHash = ethSendTransaction.getTransactionHash();
            L.e("MerchantCancelAccept Successed! tx=" + txHash);
        } catch (Exception ex) {
            L.e("MerchantCancelAccept Failed!");
        }
        return txHash;
    }


}
