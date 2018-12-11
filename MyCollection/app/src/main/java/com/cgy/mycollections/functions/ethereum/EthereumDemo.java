package com.cgy.mycollections.functions.ethereum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.SharePreUtil;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.ens.contracts.generated.ENS;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import appframe.utils.IOUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * web3j的使用调用以太坊 智能合约
 */
public class EthereumDemo extends AppCompatActivity {


    @BindView(R.id.show_content)
    EditText mContentV;
    @BindView(R.id.wallet_name)
    TextView mWalletNameV;

    /********测试用变量*********/
    String contractAddress = "0x968d96bb812f1eab0e39de53a58e3be47b7eacbe";//智能合约地址
    String currentAddress = "";
    String currentWalletFilePath = "/ATest/z_wallet_temp/";
    String bCLNodeUrl = "https://eco.blockchainlock.io/node";//BCL 链节点主网
    String net = "https://mainnet.infura.io/v3/4b299feeb9f34fdebe9fa7ad439932de";//BCLWallet eth链主网
//    String net = "https://rinkeby.infura.io/v3/503f328f3e104b87aa3cbb77c0d1205a";//infura测试网络
//    String  net="https://mainnet.infura.io/v3/503f328f3e104b87aa3cbb77c0d1205a";//infura主网
    /********测试用变量*********/

    Web3j[] web3js = new Web3j[2];
    Web3j web3Eth;
    Web3j web3BCL;

    int currentWeb3 = 1;//0 是以太坊  1是bcl

    Credentials credentials;
    BigInteger mGasPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethereum_demo);
        ButterKnife.bind(this);

        currentWalletFilePath = getExternalFilesDir(null) + currentWalletFilePath + getWalletName();

        showWallet(null);
        web3Eth = Web3jFactory.build(new HttpService(net));

        web3BCL = Web3jFactory.build(new HttpService(bCLNodeUrl));

        web3js[0] = web3Eth;
        web3js[1] = web3BCL;
    }

    public void saveWalletName(String walletName) {
        SharePreUtil.putString("Ethereum", this, "wallet", walletName);
    }

    public String getWalletName() {
        return SharePreUtil.getString("Ethereum", this, "wallet");
    }

    public void showWallet(String address) {
        mWalletNameV.setText("钱包名：" + getWalletName() + "\n钱包地址：" + address);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test://查询客户端版本

                /*******连接以太坊客户端**************/
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        Web3ClientVersion web3ClientVersion = null;
                        String clientVersion = "";
                        String ethAccounts = "";
                        try {
                            web3ClientVersion = web3js[currentWeb3].web3ClientVersion().send();
                            clientVersion = web3ClientVersion.getWeb3ClientVersion();
                            //Geth/v1.8.15-omnibus-255989da/linux-amd64/go1.10.1
                            e.onNext(clientVersion);

                            EthAccounts accounts = web3js[currentWeb3].ethAccounts().send();
                            ethAccounts = "accounts size:" + accounts.getAccounts().size();
                            if (accounts.getAccounts().size() > 0) {
                                L.e("account 1:" + accounts.getAccounts().get(0));
                            }
                            e.onNext(ethAccounts);
                        } catch (Exception ex) {
                            e.onError(ex);
                        }
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io())//在线程池中执行
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableObserver<String>() {
                            @Override
                            public void onNext(String s) {
                                L.e("onNext:" + s);
                                mContentV.append(s + "\n");
                            }

                            @Override
                            public void onError(Throwable e) {
                                L.e("onError:" + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                L.e("onComplete:");
                            }
                        });
                break;
            case R.id.create_wallet://创建钱包
                createAccount();
                break;
            case R.id.load_wallet://读取钱包
                String walletFilePath0 = currentWalletFilePath;

                File file = new File(walletFilePath0);
                if (file.exists()) {
                    try {
                        InputStream in = new FileInputStream(file);
                        String content = IOUtils.toString(in);
                        in.close();
                        L.e(content);
//                        mContentV.setText(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    loadWallet();
                } else
                    Toast.makeText(this, "钱包不存在", Toast.LENGTH_SHORT).show();
                break;
            case R.id.get_gas://获取gas price
                getGasPrice();
                break;
            case R.id.get_balance://获取余额
                getBalanceOf();
                break;
            case R.id.transaction://交易
                transTo();
                break;
            case R.id.get_contract://获取合约地址
                getContract();
                break;
            case R.id.clear:
                mContentV.setText("");
                break;
            default:
                break;
        }
    }

    /*************创建一个钱包文件**************/
    private void createAccount() {
        try {

            String walletFileName0 = "";//文件名
            String walletFilePath0 = getExternalFilesDir(null) + "/ATest/z_wallet_temp";
//            /storage/emulated/0/Android/data/com.cgy.mycollections/files/ATest/z_wallet_temp
            //钱包文件保持路径，请替换位自己的某文件夹路径
            L.e("walletFilePath0:" + walletFilePath0);

            File walletFile = new File(walletFilePath0);

            IOUtils.createNewFile(walletFile);

            //walletName: UTC--2018-12-10T16-48-07.024--5f914405dc399d77d1a573658f3a193926fe25e6.json
            walletFileName0 = WalletUtils.generateNewWalletFile("123456", walletFile, false);//useFullScrypt为false说明是轻量级钱包
            //WalletUtils.generateFullNewWalletFile("password1",new File(walleFilePath1));
            //WalletUtils.generateLightNewWalletFile("password2",new File(walleFilePath2));
            L.e("walletName: " + walletFileName0);
            saveWalletName(walletFileName0);

            loadWallet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /********加载钱包文件**********/
    private void loadWallet() {
        try {
            String walletFilePath = currentWalletFilePath;

            String passWord = "123456";
            credentials = WalletUtils.loadCredentials(passWord, walletFilePath);
            String address = credentials.getAddress();
            BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

            currentAddress = address;

            showWallet(currentAddress);
            mContentV.setText("");
            mContentV.append("address=" + address + "\n");
//            mContentV.append("public key=\n" + publicKey + "\n");
//            mContentV.append("private key=\n" + privateKey + "\n");
            L.e("address=" + address);
            L.e("public key=" + publicKey);
            L.e("private key=" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***********获取gas***********/
    private void getGasPrice() {
        if (web3js[currentWeb3] == null) return;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    //第二个参数：区块的参数，建议选最新区块
                    EthGasPrice gas = web3js[currentWeb3].ethGasPrice().send();
                    L.e("gas price:" + gas.getGasPrice());
                    mGasPrice = gas.getGasPrice();

                    e.onNext("gas price:" + gas.getGasPrice());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())//在线程池中执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        L.e("onNext:" + s);
                        mWalletNameV.append("\n" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        L.e("onComplete:");
                    }
                });

    }

    /***********获取合约***********/
    private void getContract() {
        if (web3js[currentWeb3] == null) return;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    ContractDto dto = getContractAddress(contractAddress, "Payment");
                    e.onNext("Payment contract address:" + dto.getAddress());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())//在线程池中执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        L.e("onNext:" + s);
                        mWalletNameV.append("\n" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        L.e("onComplete:");
                    }
                });

    }

    /**
     * 根据管理合约的地址 获取目标合约地址
     *
     * @param contractAddressManage
     * @param contractName          "Payment"-支付合约
     * @return
     */
    public ContractDto getContractAddress(String contractAddressManage, String contractName) {
        ContractDto contractDto = new ContractDto();
        try {
            L.e("GetContractAddress Start..." + contractAddressManage);
            Function function = new Function("getContractAddress", Arrays.<Type>asList(new Utf8String(contractName)),
                    Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                    }, new TypeReference<Utf8String>() {
                    }));

            String encodedFunction = FunctionEncoder.encode(function);

            Transaction transaction = Transaction.createEthCallTransaction(null, contractAddressManage, encodedFunction);
            EthCall response = web3js[currentWeb3].ethCall(transaction, DefaultBlockParameterName.LATEST).send();
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


    /***********查询指定地址的余额***********/
    private void getBalanceOf() {
        if (web3js[currentWeb3] == null) return;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    String address = currentAddress;//等待查询余额的地址
                    //第二个参数：区块的参数，建议选最新区块
                    EthGetBalance balance = web3js[currentWeb3].ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
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
        }).subscribeOn(Schedulers.io())//在线程池中执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        L.e("onNext:" + s);
                        mContentV.append(s + "\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        L.e("onComplete:");
                    }
                });

    }

    /****************交易*****************/
    private void transTo() {
        if (web3js[currentWeb3] == null) return;
        if (credentials == null) {
            Toast.makeText(this, "先加载钱包", Toast.LENGTH_SHORT).show();
            return;
        }

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    //开始发送0.01 =eth到指定地址
                    String address_to = "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87";
                    TransactionReceipt send = Transfer.sendFunds(web3js[currentWeb3], credentials, address_to, BigDecimal.ONE, Convert.Unit.FINNEY).send();

//                    L.e("Transaction complete:");
//                    L.e("trans hash=" + send.getTransactionHash());
//                    L.e("from :" + send.getFrom());
//                    L.e("to:" + send.getTo());
//                    L.e("gas used=" + send.getGasUsed());
//                    L.e("status: " + send.getStatus());

                    e.onNext("Transaction complete:");
                    e.onNext("trans hash=" + send.getTransactionHash());
                    e.onNext("from :" + send.getFrom());
                    e.onNext("to:" + send.getTo());
                    e.onNext("gas used=" + send.getGasUsed());
                    e.onNext("status: " + send.getStatus());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())//在线程池中执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        L.e("onNext:" + s);
                        mContentV.append(s + "\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        L.e("onComplete:");
                    }
                });
    }

}
