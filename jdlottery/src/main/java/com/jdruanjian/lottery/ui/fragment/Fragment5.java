package com.jdruanjian.lottery.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.OrderTotalBean;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.OrderTotalAPI;
import com.jdruanjian.lottery.model.net.UsersAPI;
import com.jdruanjian.lottery.ui.activity.ChargeList_Activity;
import com.jdruanjian.lottery.ui.activity.ExtendCenter_Activity;
import com.jdruanjian.lottery.ui.activity.LoginActivity;
import com.jdruanjian.lottery.ui.activity.MyInteger_Activity;
import com.jdruanjian.lottery.ui.activity.Personnal_Activity;
import com.jdruanjian.lottery.ui.activity.Setting_Activity;
import com.jdruanjian.lottery.ui.activity.SpendRecord_Activity;
import com.jdruanjian.lottery.ui.activity.WithDraw_Avtivity;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.jdruanjian.lottery.view.ImageViewCanvas;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by 龙龙 on 2017/8/21.
 */

public class Fragment5 extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.my_messge)
    RelativeLayout myMessge;
    @BindView(R.id.my_record)
    RelativeLayout myRecord;
    @BindView(R.id.my_int)
    RelativeLayout myInt;
    @BindView(R.id.my_update)
    RelativeLayout myUpdate;
    @BindView(R.id.my_set)
    RelativeLayout mySet;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.me_bag_imageView)
    ImageViewCanvas meBagImageView;
    @BindView(R.id.ll_sign_al)
    LinearLayout llSignAl;
    @BindView(R.id.my_extend)
    RelativeLayout myExtend;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_int)
    TextView tvInt;
    @BindView(R.id.tv_int1)
    TextView tvInt1;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_name_11)
    TextView tvName11;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.iv_my_messge)
    ImageView ivMyMessge;
    @BindView(R.id.iv_my_redord)
    ImageView ivMyRedord;
    @BindView(R.id.iv_my_pop)
    ImageView ivMyPop;
    @BindView(R.id.tv_tui)
    TextView tvTui;
    @BindView(R.id.tv_jine)
    TextView tvJine;
    @BindView(R.id.iv_my_int)
    ImageView ivMyInt;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.tv_mo)
    TextView tvMo;
    @BindView(R.id.iv_my_chenge)
    ImageView ivMyChenge;
    @BindView(R.id.iv_my_set)
    ImageView ivMySet;
    private Dialog dialog;
    private PhotoURL model;
    private String uid;
    private OrderTotalBean orderTotalBean;
    private String withmoney;
    private String moneyTotal;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment5, container, false);
        unbinder = ButterKnife.bind(this, layout);
        btnBack.setVisibility(View.GONE);
        setTitlebar(layout, "用户中心");
        setBar(layout);
        if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
            uid = PrefUtils.getString(getActivity().getApplication(), "uid", null);
        } else {
            uid = "";
        }
        System.out.println("TTTTTTT+++++-----"+PrefUtils.getString(getActivity().getApplication(), "uid", null));
        doRequest();
        getOrder();
        if (PrefUtils.getString(getActivity().getApplication(), "phono", null) != null) {
            tvName11.setText(PrefUtils.getString(getActivity().getApplication(), "phono", null));
        } else {
            tvName11.setText("精彩助手欢迎您");
        }
        return layout;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            doRequest();
            getOrder();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_exchange, R.id.tv_withdraw, R.id.my_messge, R.id.my_record, R.id.my_extend,
            R.id.my_int, R.id.my_update, R.id.my_set, R.id.me_bag_imageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_bag_imageView:
                Intent intent_my1 = new Intent(getActivity(), Personnal_Activity.class);
                startActivityForResult(intent_my1, 1);
                break;

            case R.id.tv_exchange:   //兑换
                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                    Intent intent_exchange = new Intent(getActivity(), ChargeList_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("intent", "intent2");
                    intent_exchange.putExtras(bundle);
                    startActivity(intent_exchange);
                } else {
                    Intent intentLog = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLog);
   //                 finishActivity();
                }
                break;
            case R.id.tv_withdraw:   //提现
                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                    Intent intent_draw = new Intent(getActivity(), WithDraw_Avtivity.class);
                    Bundle bundle  = new Bundle();
                    bundle.putString("money",withmoney);
                    intent_draw.putExtras(bundle);
                    startActivity(intent_draw);
                } else {
                    Intent intentLog = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLog);
       //             finishActivity();
                }
                break;
            case R.id.my_messge:   //个人信息
                Intent intent_my = new Intent(getActivity(), Personnal_Activity.class);
                startActivity(intent_my);
                break;
            case R.id.my_record:   //消费记录
                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                    Intent intent_redcord = new Intent(getActivity(), SpendRecord_Activity.class);
                    startActivity(intent_redcord);
                } else {
                    Intent intentLog = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLog);
       //             finishActivity();
                }

                break;
            case R.id.my_extend:
                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null){
                    Intent intent_tend = new Intent(getActivity(), ExtendCenter_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("money",moneyTotal);
                    bundle.putString("commision",tvBalance.getText().toString());
                    intent_tend.putExtras(bundle);
                    startActivity(intent_tend);
                }else {
                    Intent intentLog = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLog);
           //         finishActivity();
                }

                break;
            case R.id.my_int:   //我的积分
                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                    Intent intent_int = new Intent(getActivity(), MyInteger_Activity.class);
                    startActivity(intent_int);
                } else {
                    Intent intentLog = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLog);
       //             finishActivity();
                }
                break;
            case R.id.my_update://检查更新
//               checkVersion();
                break;
            case R.id.my_set:    //设置
                Intent intent_sett = new Intent(getActivity(), Setting_Activity.class);
                startActivity(intent_sett);
                break;


        }
    }

    public void getOrder() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        OrderTotalAPI api = new OrderTotalAPI(getActivity(), uid, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    orderTotalBean = (OrderTotalBean) response.model;
                    if (orderTotalBean.msgContext.equals("datasSuc")) {
                        tvInt.setText(orderTotalBean.integral);
                        tvInt1.setText(orderTotalBean.integral);
                        tvMoney.setText(orderTotalBean.commision);
                        tvBalance.setText(orderTotalBean.commision);
                        moneyTotal = orderTotalBean.money;
                        withmoney =orderTotalBean.commision;
                    }
                } else {
                    orderTotalBean = (OrderTotalBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        UsersAPI api = new UsersAPI(getActivity(), uid, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (PhotoURL) response.model;
                    //      uids = uid;
                    String image = model.datas;
                    ImageLoader.getInstance().displayImage(image, meBagImageView, BaseApplication.getInst().getDisplayImageOptions());
                } else {
                    model = (PhotoURL) response.model;
                    //           meBagImageView.setImageResource(R.drawable.im_tou);
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            doRequest();
            getOrder();
        }


    }

    private void showdialog() {

        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_checkupdate, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(getActivity(), R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    dialog.dismiss();
               loadNewVersionProgress();
            }
        });

    }

    //检测本程序的版本，这里假设从服务器中获取到最新的版本号为3
    public void checkVersion() {
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
        if (getVersionName() <Float.parseFloat("1.3")) {
            showdialog();
        }else{
            //否则吐司，说现在是最新的版本
            Toast.makeText(getActivity(),"当前已经是最新的版本",Toast.LENGTH_SHORT).show();

        }
    }
    /**
     * 下载新版本程序
     */
    private void loadNewVersionProgress() {
        final   String uri="http://120.77.84.221:19303/PK10SSCZS.apk ";
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    Toast.makeText(getActivity(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }}.start();
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time= System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time+"updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }

    /*
       * 获取当前程序的版本名
       */
    private float getVersionName() {
        try {

            //获取packagemanager的实例
            PackageManager packageManager = getActivity().getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            return Float.parseFloat(packInfo.versionName);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return  1;


    }


    /*
     * 获取当前程序的版本号
     */
    private int getVersionCode() {
        try {

            //获取packagemanager的实例
            PackageManager packageManager = getActivity().getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            return packInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return  2;
    }
}