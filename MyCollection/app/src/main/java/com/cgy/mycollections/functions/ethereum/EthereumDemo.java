package com.cgy.mycollections.functions.ethereum;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ethereum.contract.ContractDto;
import appframe.utils.L;
import com.cgy.mycollections.utils.SharePreUtil;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthAccounts;

import java.math.BigDecimal;
import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

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
    String bCLNodeUrl = "https://eco.blockchainlock.io/node";//BCL 链节点主网
    String net = "https://mainnet.infura.io/v3/4b299feeb9f34fdebe9fa7ad439932de";//BCLWallet eth链主网
//    String net = "https://rinkeby.infura.io/v3/503f328f3e104b87aa3cbb77c0d1205a";//infura测试网络
//    String  net="https://mainnet.infura.io/v3/503f328f3e104b87aa3cbb77c0d1205a";//infura主网
    /********测试用变量*********/

    String currentAddress = "";
    Credentials credentials;

    Web3jManager mWeb3jManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethereum_demo);
        ButterKnife.bind(this);

//        currentWalletFilePath = getExternalFilesDir(null) + currentWalletFilePath + getWalletName();

        showWallet(null);
//        web3Eth = Web3jFactory.build(new HttpService(net));

//        web3BCL = Web3jFactory.build(new HttpService(bCLNodeUrl));

//        web3js[0] = web3Eth;
//        web3js[1] = web3BCL;

        mWeb3jManager = new Web3jManager(net, bCLNodeUrl);
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
                mWeb3jManager.getAccounts(Web3jManager.INDEX_BCL).subscribe(new DisposableObserver<EthAccounts>() {
                    @Override
                    public void onNext(EthAccounts s) {
                        L.e("EthAccounts onNext:" + s.getAccounts().toString());
//                        mContentV.append(s + "\n");
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
                loadWallet();
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
        Bip39Wallet bip39Wallet = mWeb3jManager.createBip39Account(WalletFileHelper.getExternalWalletFileDir(this), "123456");
        if (bip39Wallet == null) {
            Toast.makeText(this, "创建失败", Toast.LENGTH_SHORT).show();
            return;
        }
//        L.e("getMnemonic:" + bip39Wallet.getMnemonic());
        String walletFileName0 = bip39Wallet.getFilename();
        L.e("walletName: " + walletFileName0);
        saveWalletName(walletFileName0);

        loadWallet();
    }

    /********加载钱包文件**********/
    private void loadWallet() {
//        logic crop gym memory tattoo caught address wood whale destroy badge acquire
//        credentials = mWeb3jManager.loadBip39Credentials("logic crop gym memory tattoo caught address wood whale destroy badge acquire"
//                , "123456");
        credentials = mWeb3jManager.loadWallet(WalletFileHelper.getExternalWalletFile(this, getWalletName())
                , "123456");
        if (credentials == null) {
            Toast.makeText(this, "加载钱包失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        currentAddress = address;

        showWallet(currentAddress);
        mContentV.setText("");
        mContentV.append("address=" + address + "\n");
//            mContentV.append("public key=\n" + publicKey + "\n");
//            mContentV.append("private key=\n" + privateKey + "\n");
        L.e("loadWallet address=" + address);
        L.e("public key=" + publicKey);
        L.e("private key=" + privateKey);
    }

    /***********获取gas***********/
    private void getGasPrice() {
        mWeb3jManager.getGasPrice(Web3jManager.INDEX_BCL).subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                L.e("onNext:" + s);
                mWalletNameV.append("\nGas price:" + s);
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
        mWeb3jManager.getContract(Web3jManager.INDEX_BCL, contractAddress, "Payment")
                .subscribe(new DisposableObserver<ContractDto>() {
                    @Override
                    public void onNext(ContractDto dto) {
                        L.e("onNext:" + dto.getAddress());
                        mWalletNameV.append("\n" + "Payment contract address:" + dto.getAddress());
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

    /***********查询指定地址的余额***********/
    private void getBalanceOf() {
        mWeb3jManager.getBalanceOf(Web3jManager.INDEX_BCL, currentAddress)
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
        if (credentials == null) {
            Toast.makeText(this, "先加载钱包", Toast.LENGTH_SHORT).show();
            return;
        }

        mWeb3jManager.transTo(Web3jManager.INDEX_BCL, credentials, "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87", BigDecimal.ONE)
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean s) {
                        L.e("onNext:" + s);
//                        mContentV.append(s + "\n");
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
