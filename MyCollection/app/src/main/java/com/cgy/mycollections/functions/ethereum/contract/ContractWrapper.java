package com.cgy.mycollections.functions.ethereum.contract;

import android.text.TextUtils;

import appframe.utils.L;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class ContractWrapper {

//    protected static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private static long HOLDER_TIME = 60000L;

    private static Map<String, NonceHolder> nonceHolder = new ConcurrentHashMap<>();

    private static class NonceHolder {
        long lastAcessTime;
        BigInteger nonce;
    }

    protected String contractAddress;
    protected BigInteger gasPrice = new BigInteger("90");
    protected BigInteger gasLimit = new BigInteger("1000000");

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public static BigInteger getNonce(Web3j web3j, Credentials credentials) throws IOException {
        long lNow = System.currentTimeMillis();
        String address = credentials.getAddress();
        NonceHolder holder = nonceHolder.get(address);

        if ((holder != null) && (lNow - holder.lastAcessTime < HOLDER_TIME)) {
            holder.nonce = holder.nonce.add(BigInteger.ONE);
            holder.lastAcessTime = lNow;
            L.e("cached nonce of {" + address + "} is {" + holder.nonce.toString() + "}");
        } else {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            if (holder == null) {
                holder = new NonceHolder();
                nonceHolder.put(address, holder);
            }
            holder.nonce = nonce;
            holder.lastAcessTime = lNow;
            L.e("lastest nonce of {" + address + "} is {" + holder.nonce.toString() + "}");
        }
        return holder.nonce;
    }

    public static void assertTransaction(EthSendTransaction ethSendTransaction) {
        if (ethSendTransaction.hasError()) {
            throw new RuntimeException(ethSendTransaction.getError().getMessage());
        }
    }

    public static TransactionReceipt waitForTransactionReceipt(Web3j web3j, String transactionHash) {
        L.e("waitForConfirm Start, transactionHash=" + transactionHash);
        TransactionReceipt receipt = null;
        try {
            EthGetTransactionReceipt transactionReceipt = null;
            while (true) {
                transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
                if (transactionReceipt.getTransactionReceipt() != null) {//TODO:此处和java不一致，待验证
                    receipt = transactionReceipt.getTransactionReceipt();//TODO:此处和java不一致，待验证
                    L.e("waitForConfirm End, transactionHash=" + transactionHash + ", receipt=" + receipt);
                    break;
                }

                try {
                    Thread.sleep(5000);
                } catch (Exception ex) {
                    ;
                }
            }
        } catch (Exception ex) {
            L.e("waitForConfirm Failed, transactionHash=" + transactionHash + ", message="
                    + ex.getLocalizedMessage());
        }

        return receipt;
    }

    public static BigInteger getBalance(Web3j web3j, String address) {
        try {
            EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            if (balance.hasError()) {
                throw new RuntimeException(balance.getError().getMessage());
            }
            return balance.getBalance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * h获取合约地址
     *
     * @param contractName
     * @return
     */
    public static ContractDto getContractAddress(Web3j web3j, String contractAddressManage, String contractName) {
        ContractDto contractDto = new ContractDto();
        try {
            L.e("GetContractAddress Start...");
            Function function = new Function("getContractAddress", Arrays.<Type>asList(new Utf8String(contractName)),
                    Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                    }, new TypeReference<Utf8String>() {
                    }));

            String encodedFunction = FunctionEncoder.encode(function);

            Transaction transaction = Transaction.createEthCallTransaction(null, contractAddressManage, encodedFunction);
            EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> someTypes = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
            if ((someTypes != null) && (someTypes.size() > 0)) {
                Address val = (Address) someTypes.get(0);
                Utf8String vers = (Utf8String) someTypes.get(1);
                contractDto.setAddress(val.getValue());
                contractDto.setVersion(vers.getValue());
                L.e("GetContractAddress Successed! address=" + contractDto.getAddress() + " | version=" + contractDto.getVersion());
            } else {
                L.e("GetContractAddress Failed! address=empty");
            }

        } catch (Exception ex) {
            L.e("GetContractAddress Failed! message" + ex.getLocalizedMessage());
        }
        if ("0x0000000000000000000000000000000000000000".equals(contractDto.getAddress()) || TextUtils.isEmpty(contractDto.getAddress())) {
            L.e("GetContractAddress Failed! The Name [ " + contractName + " ] Of Address Is Not Exist !");
            throw new IllegalArgumentException("GetContractAddress Failed! The Name [ " + contractName + " ] Of Address Is Not Exist !");
        }
        return contractDto;
    }

    public static void main(String[] args) {
//        System.err.println(getContractAddress("0x56F30Afaa988b7084C6865e84D349283921a2f32", "Payment").getAddress());
    }
}
