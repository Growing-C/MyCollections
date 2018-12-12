package com.cgy.mycollections.functions.ethereum;

import android.support.annotation.NonNull;

import com.cgy.mycollections.functions.ethereum.contract.ContractDto;
import com.cgy.mycollections.utils.L;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import appframe.utils.IOUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 * Author :cgy
 * Date :2018/12/12
 */
public class Web3jManager {

    //<editor-fold desc="参数 ">
    public static final int INDEX_ETH = 0;
    public static final int INDEX_BCL = 1;

    Web3j[] web3js = new Web3j[2];
    Web3j web3Eth;
    Web3j web3BCL;

    //</editor-fold>
    public Web3jManager(String ethUrl, String bclUrl) {
        web3Eth = Web3jFactory.build(new HttpService(ethUrl));
        web3BCL = Web3jFactory.build(new HttpService(bclUrl));
        web3js[INDEX_ETH] = web3Eth;
        web3js[INDEX_BCL] = web3BCL;
    }


    //<editor-fold desc="钱包和账户创建和加载 ">

    /**
     * 创建bip39钱包，包含助记词
     *
     * @param walletFilePath 示例路径：/storage/emulated/0/Android/data/com.xxx.xxx/files/ATest/z_wallet_temp
     * @see {@link org.web3j.crypto.MnemonicUtils#generateSeed(String, String)} 由于生成时第二个参数用的password，导致别的钱包可能不兼容（很多钱包用的空字符）
     */
    public Bip39Wallet createBip39Account(@NonNull String walletFilePath, @NonNull String password) {
        requireNonNull(walletFilePath, "walletFilePath is null");
        requireNonNull(password, "password is null");

        Bip39Wallet bip39Wallet = null;
        try {
//        String walletFileName0 = "";//文件名
//            String walletFilePath0 = getExternalFilesDir(null) + "/ATest/z_wallet_temp";
            //钱包文件保持路径，请替换位自己的某文件夹路径
            L.e("createBip39Account walletFilePath0:" + walletFilePath);
            File walletFile = new File(walletFilePath);

            IOUtils.createFolder(walletFile);
            //walletName: UTC--2018-12-10T16-48-07.024--5f914405dc399d77d1a573658f3a193926fe25e6.json
//            walletFileName0 = WalletUtils.generateNewWalletFile("123456", walletFile, false);//useFullScrypt为false说明是轻量级钱包

            MemoryUtil.injectWordList();//Bip39Wallet中的MnemonicUtils中的WORD_LIST为空，未读取到数据，此处反射读取赋值
            bip39Wallet = WalletUtils.generateBip39Wallet(password, walletFile);//含有助记词的wallet
            L.e("getMnemonic:" + bip39Wallet.getMnemonic());
//            walletFileName0 = bip39Wallet.getFilename();
            //WalletUtils.generateFullNewWalletFile("password1",new File(walleFilePath1));
            //WalletUtils.generateLightNewWalletFile("password2",new File(walleFilePath2));
//            L.e("walletName: " + walletFileName0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bip39Wallet;
    }

    /**
     * 获取钱包中的所有账户
     *
     * @param chainIndex
     * @return
     */
    public Observable<EthAccounts> getAccounts(final int chainIndex) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<EthAccounts>() {
            @Override
            public void subscribe(ObservableEmitter<EthAccounts> e) throws Exception {
//                Web3ClientVersion web3ClientVersion = null;
//                String clientVersion = "";
//                String ethAccounts = "";
                try {
//                    web3ClientVersion = web3js[currentWeb3].web3ClientVersion().send();
//                    clientVersion = web3ClientVersion.getWeb3ClientVersion();
//                    //Geth/v1.8.15-omnibus-255989da/linux-amd64/go1.10.1
//                    e.onNext(clientVersion);
                    EthAccounts accounts = web3js[chainIndex].ethAccounts().send();
//                    ethAccounts = "accounts size:" + accounts.getAccounts().size();
//                    if (accounts.getAccounts().size() > 0) {
//                        for (String ac : accounts.getAccounts()) {
//                            L.e("getAccounts -->account  :" + ac);
//                        }
//                    }
                    if (accounts != null)
                        e.onNext(accounts);
                    else
                        e.onError(new NullPointerException("account is null"));
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }));
    }

    /**
     * 根据助记词加载bip39钱包
     * 如果助记词 mnemonic 错误的话经测试，会返回一个钱包地址，但是和创建的地址不同
     *
     * @param mnemonic
     * @param password
     * @return
     */
    public Credentials loadBip39Credentials(String mnemonic, String password) {
        requireNonNull(password, "password is null");
        requireNonNull(mnemonic, "mnemonic is null");

        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadBip39Credentials(password, mnemonic);
            String address = credentials.getAddress();
            BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

//            mContentV.append("public key=\n" + publicKey + "\n");
//            mContentV.append("private key=\n" + privateKey + "\n");
            L.e("address=" + address);
            L.e("public key=" + publicKey);
            L.e("private key=" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return credentials;
    }

    /********加载钱包文件**********/
    public Credentials loadWallet(String walletFilePath, String password) {
        requireNonNull(walletFilePath, "walletFilePath is null");
        requireNonNull(password, "password is null");

        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(password, walletFilePath);
            String address = credentials.getAddress();
            BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

//            mContentV.append("public key=\n" + publicKey + "\n");
//            mContentV.append("private key=\n" + privateKey + "\n");
            L.e("address=" + address);
            L.e("public key=" + publicKey);
            L.e("private key=" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return credentials;
    }


    //</editor-fold>


    //<editor-fold desc="交易相关 ">

    /**
     * 获取gas price
     *
     * @param chainIndex
     * @return
     */
    public Observable<String> getGasPrice(final int chainIndex) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                try {
                    //第二个参数：区块的参数，建议选最新区块
                    EthGasPrice gas = web3js[chainIndex].ethGasPrice().send();
                    L.e("gas price:" + gas.getGasPrice());

                    e.onNext("" + gas.getGasPrice());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }));
    }

    /**
     * 查询指定地址的余额
     *
     * @param chainIndex 链脚标，表示是哪个链
     * @param address    等待查询余额的地址
     **/
    public Observable<String> getBalanceOf(final int chainIndex, @NonNull final String address) {
        requireNonNull(address, "address is null");

        return applySchedulers(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    //第二个参数：区块的参数，建议选最新区块
                    EthGetBalance balance = web3js[chainIndex].ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
//                    String "earliest" 为最早/起源块
//                    String "latest" - 为最新的采矿块
//                    String "pending" - 待处理状态/交易
                    //格式转化 wei-ether
                    String balanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString().concat(" ether");
                    L.e("balanceETH:" + balanceETH);

                    e.onNext(balanceETH);
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }));
    }

    /**
     * 交易
     *
     * @param chainIndex     目标链
     * @param credentials    转出账户
     * @param addressTransTo 转入地址
     * @param amount         数量
     * @return
     */
    public Observable<Boolean> transTo(final int chainIndex, @NonNull final Credentials credentials, @NonNull final String addressTransTo, @NonNull final BigDecimal amount) {
        requireNonNull(addressTransTo, "addressTransTo is null");
        ObjectHelper.requireNonNull(credentials, "credentials is null");
        ObjectHelper.requireNonNull(amount, "amount is null");

        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) {
                try {
                    //开始发送0.01 =eth到指定地址
//                    String address_to = "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87";
                    TransactionReceipt send = Transfer.sendFunds(web3js[chainIndex], credentials, addressTransTo, amount, Convert.Unit.FINNEY).send();
                    L.e("Transaction complete:");
                    L.e("trans hash=" + send.getTransactionHash());
                    L.e("from :" + send.getFrom());
                    L.e("to:" + send.getTo());
                    L.e("gas used=" + send.getGasUsed());
                    L.e("status: " + send.getStatus());

                    e.onNext(true);
//                    e.onNext("Transaction complete:");
//                    e.onNext("trans hash=" + send.getTransactionHash());
//                    e.onNext("from :" + send.getFrom());
//                    e.onNext("to:" + send.getTo());
//                    e.onNext("gas used=" + send.getGasUsed());
//                    e.onNext("status: " + send.getStatus());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }));
    }

    //</editor-fold>

    //<editor-fold desc="合约相关 ">

    /**
     * 根据管理合约的地址 获取目标合约地址
     *
     * @param contractAddress    管理合约的地址
     * @param targetContractName 目标合约名        "Payment"-支付合约
     * @return
     */
    public Observable<ContractDto> getContract(final int chainIndex, @NonNull final String contractAddress, @NonNull final String targetContractName) {
        requireNonNull(contractAddress, "contractAddress is null");
        requireNonNull(targetContractName, "targetContractName is null");

        return applySchedulers(Observable.create(new ObservableOnSubscribe<ContractDto>() {
            @Override
            public void subscribe(ObservableEmitter<ContractDto> e) {
                ContractDto contractDto = new ContractDto();
                try {
                    L.e("GetContractAddress Start..." + contractAddress);
                    Function function = new Function("getContractAddress", Arrays.<Type>asList(new Utf8String(targetContractName)),
                            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                            }, new TypeReference<Utf8String>() {
                            }));

                    String encodedFunction = FunctionEncoder.encode(function);

                    Transaction transaction = Transaction.createEthCallTransaction(null, contractAddress, encodedFunction);
                    EthCall response = web3js[chainIndex].ethCall(transaction, DefaultBlockParameterName.LATEST).send();
                    List<Type> someTypes = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
                    if ((someTypes != null) && (someTypes.size() > 0)) {
                        Address val = (Address) someTypes.get(0);
                        Utf8String vers = (Utf8String) someTypes.get(1);
                        contractDto.setAddress(val.getValue());
                        contractDto.setVersion(vers.getValue());
                        L.e("GetContractAddress Successed! address=" + contractDto.getAddress() + " | version=" + contractDto.getVersion());
                    } else {
                        L.e("GetContractAddress Failed! address=empty");
                        throw new NullPointerException("GetContractAddress Failed! address=empty");
                    }

                    if ("0x0000000000000000000000000000000000000000".equals(contractDto.getAddress()) || isEmpty(contractDto.getAddress())) {
                        L.e("GetContractAddress Failed! The Name [ " + targetContractName + " ] Of Address Is Not Exist !");
                        throw new IllegalArgumentException("GetContractAddress Failed! The Name [ " + targetContractName + " ] Of Address Is Not Exist !");
                    }

                    e.onNext(contractDto);
                } catch (Exception ex) {
                    L.e("GetContractAddress Failed! message" + ex.getLocalizedMessage());
                    e.onError(ex);
                }
                e.onComplete();
            }
        }));
    }

    //</editor-fold>

    //<editor-fold desc="私有处理内容">

    private boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * string类型的参数是否为空检验
     *
     * @param str
     * @param message
     * @return
     */
    private String requireNonNull(String str, String message) {
        if (str == null || str.trim().length() == 0) {
            throw new NullPointerException(message);
        }
        return str;
    }

    /**
     * Observable<T> 做统一的处理，处理了线程调度、分割返回结果等操作组合了起
     *
     * @param responseObservable
     * @param <T>
     * @return
     */
    private <T> io.reactivex.Observable<T> applySchedulers(io.reactivex.Observable<T> responseObservable) {
        return responseObservable.subscribeOn(Schedulers.io())//在线程池中执
                .observeOn(AndroidSchedulers.mainThread());//在主线程中获得观察结
    }
    //</editor-fold>
}
