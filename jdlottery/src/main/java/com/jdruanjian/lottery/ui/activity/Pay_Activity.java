package com.jdruanjian.lottery.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.MainActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.AssignPlanPriceBean;
import com.jdruanjian.lottery.model.entity.CancelOrderBean;
import com.jdruanjian.lottery.model.entity.IntPayBean;
import com.jdruanjian.lottery.model.entity.PayMentBean;
import com.jdruanjian.lottery.model.entity.WeChatPayBean;
import com.jdruanjian.lottery.model.entity.Weichat_QQ_mess;
import com.jdruanjian.lottery.model.net.AliplayPayAPI;
import com.jdruanjian.lottery.model.net.AssignPlanPriceAPI;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.CancelOrderAPI;
import com.jdruanjian.lottery.model.net.IntPayAPI;
import com.jdruanjian.lottery.model.net.WeChatPayAPI;
import com.jdruanjian.lottery.model.net.Weichat_QQAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PayResult;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Longlong on 2017/9/27.
 */

public class Pay_Activity extends BaseActivity {
    @BindView(R.id.tv_title_jieguo)
    TextView tvTitleJieguo;
    @BindView(R.id.tv_lottery_name)
    TextView tvLotteryName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_payplan)
    TextView tvPayplan;
    @BindView(R.id.wechat_pay)
    RelativeLayout wechatPay;
    @BindView(R.id.aplay_pay)
    RelativeLayout aplayPay;
    @BindView(R.id.int_pay)
    RelativeLayout intPay;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.tv_paymoney)
    TextView tvPaymoney;
    @BindView(R.id.im_check_1)
    ImageButton imCheck1;
    @BindView(R.id.im_check_2)
    ImageButton imCheck2;
    @BindView(R.id.im_check_3)
    ImageButton imCheck3;
    public int flag = 0;
    @BindView(R.id.tv_payint)
    TextView tvPayint;
    @BindView(R.id.tv_moneypay)
    TextView tvMoneypay;
    @BindView(R.id.rl_day)
    RelativeLayout rlDay;
    @BindView(R.id.rl_week)
    RelativeLayout rlWeek;
    @BindView(R.id.rl_month)
    RelativeLayout rlMonth;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_daymoney)
    TextView tvDaymoney;
    @BindView(R.id.tv_daymoney_1)
    TextView tvDaymoney1;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_weekmoney)
    TextView tvWeekmoney;
    @BindView(R.id.tv_weekmoney_1)
    TextView tvWeekmoney1;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_monthmoney)
    TextView tvMonthmoney;
    @BindView(R.id.tv_monthmoney_1)
    TextView tvMonthmoney1;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title_result)
    TextView tvTitleResult;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_type1)
    TextView tvType1;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.tv_wei)
    TextView tvWei;
    @BindView(R.id.im_3)
    ImageView im3;
    @BindView(R.id.tv_inte)
    TextView tvInte;
    @BindView(R.id.QQ_number)
    TextView QQNumber;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_yaermoney)
    TextView tvYaermoney;
    @BindView(R.id.tv_yaermoney_1)
    TextView tvYaermoney1;
    @BindView(R.id.rl_year)
    RelativeLayout rlYear;
    private String order_des;//订单描述
    private String order_title;//订单标题
    private String order_payamount;
    private String allow_latertime = "30";
    private static final int SDK_PAY_FLAG = 1;
    private WeChatPayBean weChatPayBean;
    private PayMentBean payMentBean;
    private IntPayBean intPayBean;
    private String authInfo;
    private Dialog dialog;
    private String appIds, partnerIds, nonceStrs, timeStamps, signs, prepayIds;
    private String packageValues;
    // IWXAPI 是第三方app和微信通信的openapi接口
    public IWXAPI api;
    private int CHECK_TAG_1 = 1;
    private AssignPlanPriceBean planPriceBean;
    private String lottery_name;
    private String plan_name;
    private int integer;
    private int tag = 1;
    private int index = 1;
    private int orderint;
    private CancelOrderBean cancelOrderBean;
    private String trde_no;
    private String uid;
    private String pay_money;
    private Weichat_QQ_mess weichatQqMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        tvTitleJieguo.setVisibility(View.GONE);
        setBar();
        setTitleResult("支付订单");
        getQQ_WeiChat();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("intent").equals("all")) {
            tvLotteryName.setText(bundle.getString("alllots"));
            tvPayplan.setText(bundle.getString("allplans"));
            rlYear.setVisibility(View.VISIBLE);
            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");
            rlDay.setEnabled(false);
            rlWeek.setEnabled(true);
            rlMonth.setEnabled(true);
            rlYear.setEnabled(true);
            tvDay.setTextColor(getResources().getColor(R.color.white));
            tvDaymoney.setTextColor(getResources().getColor(R.color.white));
            tvDaymoney1.setTextColor(getResources().getColor(R.color.white));
            tvWeek.setTextColor(getResources().getColor(R.color.praise));
            tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
            tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
            tvMonth.setTextColor(getResources().getColor(R.color.praise));
            tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
            tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
            tvType.setText("包天");
            lottery_name = bundle.getString("alllots");
            plan_name = bundle.getString("allplans");
            tag = 3;
            AssignPlanPrice();
        } else if (bundle.getString("intent").equals("all_1")) {
            tvLotteryName.setText(bundle.getString("alllots_1"));
            tvPayplan.setText(bundle.getString("allplans_1"));
            rlYear.setVisibility(View.VISIBLE);
            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("allplans_1");
            rlDay.setEnabled(false);
            rlWeek.setEnabled(true);
            rlMonth.setEnabled(true);
            rlYear.setEnabled(true);
            tvDay.setTextColor(getResources().getColor(R.color.white));
            tvDaymoney.setTextColor(getResources().getColor(R.color.white));
            tvDaymoney1.setTextColor(getResources().getColor(R.color.white));
            tvWeek.setTextColor(getResources().getColor(R.color.praise));
            tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
            tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
            tvMonth.setTextColor(getResources().getColor(R.color.praise));
            tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
            tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
            tvType.setText("包天");
            lottery_name = bundle.getString("alllots_1");
            plan_name = bundle.getString("allplans_1");
            tag = 4;
            AssignPlanPrice();
        }
        if (bundle.getString("intent").equals("intent1")) {
            tvLotteryName.setText(bundle.getString("title"));
            tvPayplan.setText(bundle.getString("title1"));
            rlYear.setVisibility(View.GONE);
            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
            rlDay.setEnabled(false);
            rlWeek.setEnabled(true);
            rlMonth.setEnabled(true);
            tvDay.setTextColor(getResources().getColor(R.color.white));
            tvDaymoney.setTextColor(getResources().getColor(R.color.white));
            tvDaymoney1.setTextColor(getResources().getColor(R.color.white));
            tvWeek.setTextColor(getResources().getColor(R.color.praise));
            tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
            tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
            tvMonth.setTextColor(getResources().getColor(R.color.praise));
            tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
            tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
            tvType.setText("包天");
            lottery_name = bundle.getString("name");
            plan_name = bundle.getString("title1");
            tag = 2;
            AssignPlanPrice();
        } else if (bundle.getString("intent").equals("intent3")) {
            rlYear.setVisibility(View.GONE);
            tag = 1;
            if (bundle.getString("body").equals("包天")) {
                CHECK_TAG_1 = 1;
                order_des = bundle.getString("body");//包天，包周，包月；
                order_payamount = bundle.getString("money");//支付金额
                order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                tvLotteryName.setText(bundle.getString("lottname"));
                tvPayplan.setText(bundle.getString("payplan"));
                tvType.setText(bundle.getString("body"));
                lottery_name = bundle.getString("lottname");
                plan_name = bundle.getString("payplan");
                orderint = Integer.parseInt(bundle.getString("money"));
                rlDay.setEnabled(false);
                rlWeek.setEnabled(true);
                rlMonth.setEnabled(true);
                tvDay.setTextColor(getResources().getColor(R.color.white));
                tvDaymoney.setTextColor(getResources().getColor(R.color.white));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.white));
                tvWeek.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvMonth.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
            } else if (bundle.getString("body").equals("包周")) {
                CHECK_TAG_1 = 2;
                order_des = bundle.getString("body");//包天，包周，包月；
                order_payamount = bundle.getString("money");//支付金额
                order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                tvLotteryName.setText(bundle.getString("lottname"));
                tvPayplan.setText(bundle.getString("payplan"));
                tvType.setText(bundle.getString("body"));
                lottery_name = bundle.getString("lottname");
                plan_name = bundle.getString("payplan");
                orderint = Integer.parseInt(bundle.getString("money"));
                rlDay.setEnabled(true);
                rlWeek.setEnabled(false);
                rlMonth.setEnabled(true);
                tvDay.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.praise));
                tvWeek.setTextColor(getResources().getColor(R.color.white));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.white));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.white));
                tvMonth.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
            } else if (bundle.getString("body").equals("包月")) {
                CHECK_TAG_1 = 3;
                order_des = bundle.getString("body");//包天，包周，包月；
                order_payamount = bundle.getString("money");//支付金额
                order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                tvLotteryName.setText(bundle.getString("lottname"));
                tvPayplan.setText(bundle.getString("payplan"));
                tvType.setText(bundle.getString("body"));
                lottery_name = bundle.getString("lottname");
                plan_name = bundle.getString("payplan");
                orderint = Integer.parseInt(bundle.getString("money"));
                rlDay.setEnabled(true);
                rlWeek.setEnabled(true);
                rlMonth.setEnabled(false);
                tvDay.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.praise));
                tvWeek.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvMonth.setTextColor(getResources().getColor(R.color.white));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.white));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.white));
            }
            AssignPlanPrice();

        }
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(Pay_Activity.this, Constants.APP_ID, true);
        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        boolean isWXAppInstalledAndSupported = api.isWXAppInstalled();

    }


    @OnClick({R.id.wechat_pay, R.id.aplay_pay, R.id.int_pay, R.id.btn_pay, R.id.rl_day, R.id.rl_week, R.id.rl_month, R.id.btn_back, R.id.rl_year})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent3 = new Intent(this, MainActivity.class);
                setResult(4, intent3);
                finish();
            case R.id.wechat_pay:
                imCheck1.setEnabled(false);
                imCheck2.setEnabled(true);
                imCheck3.setEnabled(true);
                flag = 1;
                AssignPlanPrice();
                break;
            case R.id.aplay_pay:
                imCheck2.setEnabled(false);
                imCheck1.setEnabled(true);
                imCheck3.setEnabled(true);
                flag = 2;
                AssignPlanPrice();
                break;
            case R.id.int_pay:
                imCheck3.setEnabled(false);
                imCheck2.setEnabled(true);
                imCheck1.setEnabled(true);
                flag = 3;
                AssignPlanPrice();
                break;
            case R.id.rl_day:
                rlDay.setEnabled(false);
                rlWeek.setEnabled(true);
                rlMonth.setEnabled(true);
                rlYear.setEnabled(true);
                tvDay.setTextColor(getResources().getColor(R.color.white));
                tvDaymoney.setTextColor(getResources().getColor(R.color.white));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.white));
                tvWeek.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvMonth.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvYear.setTextColor(getResources().getColor(R.color.praise));
                tvYaermoney.setTextColor(getResources().getColor(R.color.praise));
                tvYaermoney1.setTextColor(getResources().getColor(R.color.praise));
                tvType.setText("包天");
                CHECK_TAG_1 = 1;
                AssignPlanPrice();
                break;
            case R.id.rl_week:
                rlDay.setEnabled(true);
                rlWeek.setEnabled(false);
                rlMonth.setEnabled(true);
                rlYear.setEnabled(true);
                tvDay.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.praise));
                tvWeek.setTextColor(getResources().getColor(R.color.white));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.white));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.white));
                tvMonth.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvYear.setTextColor(getResources().getColor(R.color.praise));
                tvYaermoney.setTextColor(getResources().getColor(R.color.praise));
                tvYaermoney1.setTextColor(getResources().getColor(R.color.praise));
                tvType.setText("包周");
                CHECK_TAG_1 = 2;
                AssignPlanPrice();
                break;
            case R.id.rl_month:
                rlDay.setEnabled(true);
                rlWeek.setEnabled(true);
                rlMonth.setEnabled(false);
                rlYear.setEnabled(true);
                tvDay.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.praise));
                tvWeek.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvMonth.setTextColor(getResources().getColor(R.color.white));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.white));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.white));
                tvYear.setTextColor(getResources().getColor(R.color.praise));
                tvYaermoney.setTextColor(getResources().getColor(R.color.praise));
                tvYaermoney1.setTextColor(getResources().getColor(R.color.praise));
                tvType.setText("包月");
                CHECK_TAG_1 = 3;
                AssignPlanPrice();
                break;
            case R.id.rl_year:
                rlDay.setEnabled(true);
                rlWeek.setEnabled(true);
                rlMonth.setEnabled(true);
                rlYear.setEnabled(false);
                tvDay.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney.setTextColor(getResources().getColor(R.color.praise));
                tvDaymoney1.setTextColor(getResources().getColor(R.color.praise));
                tvWeek.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney.setTextColor(getResources().getColor(R.color.praise));
                tvWeekmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvMonth.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney.setTextColor(getResources().getColor(R.color.praise));
                tvMonthmoney1.setTextColor(getResources().getColor(R.color.praise));
                tvYear.setTextColor(getResources().getColor(R.color.white));
                tvYaermoney.setTextColor(getResources().getColor(R.color.white));
                tvYaermoney1.setTextColor(getResources().getColor(R.color.white));
                tvType.setText("包年");
                CHECK_TAG_1 = 4;
                AssignPlanPrice();
                break;
            case R.id.btn_pay:
                if (flag == 1) {
                    if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                        if (tag == 1) {
                            order_des = tvType.getText().toString();
                            order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                            order_payamount = tvPaymoney.getText().toString();
                            WeChatRequest();
                        } else if (tag == 2) {
                            order_des = tvType.getText().toString();
                            order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                            order_payamount = tvPaymoney.getText().toString();
                            WeChatRequest();
                        } else {
                            order_des = tvType.getText().toString();
                            order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                            order_payamount = tvPaymoney.getText().toString();
                            WeChatRequest();
                        }

                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                if (flag == 2) {
                    if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                        Bundle bundle = getIntent().getExtras();
                        if (tag == 1) {
                            order_des = tvType.getText().toString();
                            order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                            order_payamount = tvPaymoney.getText().toString();
                            AlipalyRequest();
                        } else if (tag == 2) {
                            order_des = tvType.getText().toString();
                            order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                            order_payamount = tvPaymoney.getText().toString();
                            AlipalyRequest();
                        } else {
                            order_des = tvType.getText().toString();
                            order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                            order_payamount = tvPaymoney.getText().toString();
                            AlipalyRequest();
                        }


                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                if (flag == 3) {
                    if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                        order_des = tvType.getText().toString();
                        order_title = tvLotteryName.getText().toString() + "·" + tvPayplan.getText().toString();
                        order_payamount = pay_money;
                        System.out.println("SSSSSSS+++++=====" + order_payamount);
                        if (integer > 0) {
                            showdialog();
                        } else {
                            //Toast.makeText(getApplicationContext(),"订单有误，请确认无误后再购买",Toast.LENGTH_SHORT).show();
                            showdialogorder();
                        }
                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    public void AssignPlanPrice() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }
        AssignPlanPriceAPI api = new AssignPlanPriceAPI(this, lottery_name, plan_name,
                new BasicResponse.RequestListener() {

                    @Override
                    public void onComplete(BasicResponse response) {
                        if (response.error == BasicResponse.SUCCESS) {
                            planPriceBean = (AssignPlanPriceBean) response.model;
                            Bundle bundle = getIntent().getExtras();
                            if (planPriceBean.msgContext.equals("success")) {

                                tvDaymoney.setText(planPriceBean.day);
                                tvWeekmoney.setText(planPriceBean.week);
                                tvMonthmoney.setText(planPriceBean.month);
                                tvYaermoney.setText(planPriceBean.year);
                                if (tag == 1) {

                                } else if (tag == 2) {

                                    //          tvPaymoney.setText(order_payamount);
                                    integer = (orderint * 100);
                                }
                                if (CHECK_TAG_1 == 1) {
                                    tvPaymoney.setText(planPriceBean.day);
                                    integer = (Integer.parseInt(planPriceBean.day) * 100);

                                } else {
                                  /*  if (index==1){
                                        tvPaymoney.setText(planPriceBean.day);
                                        integer = (Integer.parseInt(planPriceBean.day) * 100);
                                    }*/
                                }

                                if (CHECK_TAG_1 == 2) {
                                    tvPaymoney.setText(planPriceBean.week);
                                    integer = (Integer.parseInt(planPriceBean.week) * 100);
                                } else {
                                  /*  if (index==2){
                                        tvPaymoney.setText(planPriceBean.week);
                                        integer = (Integer.parseInt(planPriceBean.week) * 100);

                                    }*/
                                }
                                if (CHECK_TAG_1 == 3) {
                                    tvPaymoney.setText(planPriceBean.month);
                                    integer = (Integer.parseInt(planPriceBean.month) * 100);

                                } else {
                                   /* if (index==3){
                                        tvPaymoney.setText(planPriceBean.month);
                                        integer = (Integer.parseInt(planPriceBean.month) * 100);

                                    }*/
                                }
                                if (CHECK_TAG_1 == 4) {
                                    tvPaymoney.setText(planPriceBean.year);
                                    integer = (Integer.parseInt(planPriceBean.year) * 100);

                                } else {
                                   /* if (index==3){
                                        tvPaymoney.setText(planPriceBean.month);
                                        integer = (Integer.parseInt(planPriceBean.month) * 100);

                                    }*/
                                }
                                if (flag == 1) {
                                    //        Bundle bundle = new Bundle();
                                    if (CHECK_TAG_1 == 1) {
                                        tvPaymoney.setText(planPriceBean.day);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包天";
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }
                                        order_payamount = planPriceBean.day;

                                    } else if (CHECK_TAG_1 == 2) {
                                        tvPaymoney.setText(planPriceBean.week);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }
                                        order_des = "包周";
                                        order_payamount = planPriceBean.week;

                                    } else if (CHECK_TAG_1 == 3) {
                                        tvPaymoney.setText(planPriceBean.month);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包月";
                                        order_payamount = planPriceBean.month;
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }

                                    }else if (CHECK_TAG_1 == 4) {
                                        tvPaymoney.setText(planPriceBean.year);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包年";
                                        order_payamount = planPriceBean.year;
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }

                                    }
                                }
                                if (flag == 2) {
                                    //          Bundle bundle = new Bundle();
                                    if (CHECK_TAG_1 == 1) {
                                        tvPaymoney.setText(planPriceBean.day);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包天";
                                        order_payamount = planPriceBean.day;
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }

                                    } else if (CHECK_TAG_1 == 2) {
                                        tvPaymoney.setText(planPriceBean.week);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包周";
                                        order_payamount = planPriceBean.week;
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }

                                    } else if (CHECK_TAG_1 == 3) {
                                        tvPaymoney.setText(planPriceBean.month);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包月";
                                        order_payamount = planPriceBean.month;
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }
                                    }else if (CHECK_TAG_1 == 4) {
                                        tvPaymoney.setText(planPriceBean.year);
                                        tvMoneypay.setVisibility(View.VISIBLE);
                                        tvPayint.setVisibility(View.GONE);
                                        order_des = "包年";
                                        order_payamount = planPriceBean.year;
                                        if (tag == 1) {
                                            order_title = bundle.getString("title") + "·" + bundle.getString("title1");
                                        } else if (tag == 2) {
                                            order_title = bundle.getString("lottname") + "·" + bundle.getString("payplan");//订单标题
                                        }else if (tag == 3) {
                                            order_title = bundle.getString("alllots") + "·" + bundle.getString("allplans");//订单标题
                                        }else if (tag == 4) {
                                            order_title = bundle.getString("alllots_1") + "·" + bundle.getString("all_plans_1");//订单标题
                                        }

                                    }
                                }
                                if (flag == 3) {
                                    if (CHECK_TAG_1 == 1) {
                                        pay_money = planPriceBean.day;
                                        tvPaymoney.setText(String.valueOf((Integer.parseInt(planPriceBean.day) * 100)));
                                        tvMoneypay.setVisibility(View.GONE);
                                        tvPayint.setVisibility(View.VISIBLE);
                                    } else {

                                    }

                                    if (CHECK_TAG_1 == 2) {
                                        pay_money = planPriceBean.week;
                                        tvPaymoney.setText(String.valueOf((Integer.parseInt(planPriceBean.week) * 100)));
                                        tvMoneypay.setVisibility(View.GONE);
                                        tvPayint.setVisibility(View.VISIBLE);
                                    } else {

                                    }

                                    if (CHECK_TAG_1 == 3) {
                                        pay_money = planPriceBean.month;
                                        tvPaymoney.setText(String.valueOf((Integer.parseInt(planPriceBean.month) * 100)));
                                        tvMoneypay.setVisibility(View.GONE);
                                        tvPayint.setVisibility(View.VISIBLE);
                                    } else {

                                    }
                                    if (CHECK_TAG_1 == 4) {
                                        pay_money = planPriceBean.year;
                                        tvPaymoney.setText(String.valueOf((Integer.parseInt(planPriceBean.year) * 100)));
                                        tvMoneypay.setVisibility(View.GONE);
                                        tvPayint.setVisibility(View.VISIBLE);
                                    } else {

                                    }
                                }
                            }
                        } else {
                            planPriceBean = (AssignPlanPriceBean) response.model;
                        }
                    }
                });

        api.executeRequest(0);
    }

    public void IntPay() {

        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        IntPayAPI api = new IntPayAPI(this, PrefUtils.getString(getApplication(), "uid", null), order_des, order_title,
                order_payamount, allow_latertime, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    intPayBean = (IntPayBean) response.model;
                    if (intPayBean.msgContext.equals("success")) {
                        Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Pay_Activity.this, SpendRecord_Activity.class);
                        startActivity(intent);
                    } else if (intPayBean.msgContext.equals("notEnough")) {

                        shownotEnough();
                    }
                } else {
                    intPayBean = (IntPayBean) response.model;
                }
            }
        });

        api.executeRequest(0);
    }

    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_intpay, null);
        TextView order_money = (TextView) localView.findViewById(R.id.order_money);
        TextView tv_orderinfo = (TextView) localView.findViewById(R.id.tv_orderinfo);
        Button btn_ensure = (Button) localView.findViewById(R.id.btn_ensure);
        TextView btn_cancel = (TextView) localView.findViewById(R.id.btn_cancel);
        AssignPlanPrice();
        order_money.setText(String.valueOf(integer));
        tv_orderinfo.setText(order_title);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //           Toast.makeText(Pay_Activity.this, "支付失败", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btn_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        IntPay();
                        dialog.dismiss();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 0);//3秒后执行TimeTask的run方法
            }
        });

    }

    public void WeChatRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        WeChatPayAPI Api = new WeChatPayAPI(this, PrefUtils.getString(getApplication(), "uid", null), order_des, order_title,
                order_payamount, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    weChatPayBean = (WeChatPayBean) response.model;
                    if (weChatPayBean.msgContext.equals("success")) {
                        PrefUtils.putString(getApplication(), "trade_num", weChatPayBean.out_trade_no);
                        appIds = Constants.APP_ID;
                        partnerIds = Constants.MCH_ID;
                        nonceStrs = weChatPayBean.nonce_str;
                        timeStamps = weChatPayBean.timestamp;
                        signs = weChatPayBean.sign;
                        prepayIds = weChatPayBean.prepay_id;
                        packageValues = "Sign=WXPay";
                        try {
                            PayReq req = new PayReq();

                            req.appId = appIds;
                            req.partnerId = partnerIds;
                            req.prepayId = prepayIds;
                            req.nonceStr = nonceStrs;
                            req.timeStamp = timeStamps;
                            req.packageValue = packageValues;
                            req.sign = signs;
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            api.sendReq(req);
                            Log.e("orion", "req.checkArgs() = " + req.checkArgs());
                        } catch (Exception e) {
                            Log.e("PAY_GET", "异常：" + e.getMessage());
                            Toast.makeText(Pay_Activity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    weChatPayBean = (WeChatPayBean) response.model;
                }
            }
        });
        Api.executeRequest(0);
    }

    /*  private long genTimeStamp() {
          return System.currentTimeMillis() / 1000;
      }*/
    public void AlipalyRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        AliplayPayAPI api = new AliplayPayAPI(this, PrefUtils.getString(getApplication(), "uid", null), order_des, order_title,
                order_payamount, allow_latertime, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    payMentBean = (PayMentBean) response.model;
                    if (payMentBean.msgContext.equals("success")) {
                        authInfo = payMentBean.datas;
                        PrefUtils.putString(getApplication(), "trade_no", payMentBean.out_trade_no);
                        getaplaypay();
                    }
                } else {
                    payMentBean = (PayMentBean) response.model;
                }
            }
        });

        api.executeRequest(0);
    }

    /*  // 将用户信息保存到配置文件
      private void saveUserMsg() {
          PrefUtils.putString(getApplication(), "phono", phone);
          PrefUtils.putString(getApplication(), "pwd", password);
          PrefUtils.putString(getApplication(), "uid", uid);
          PrefUtils.putString(getApplication(),"inviteNum",inviteNum);

      }*/
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(Pay_Activity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Pay_Activity.this, SpendRecord_Activity.class);
                        startActivity(intent);
                    } else {
                        //          type =1;
                        //            uid= PrefUtils.getString(getApplication(),"uid",null);
                        //          System.out.println("DDDDDDDD======"+uid);
                        //         trde_no = order_no.getText().toString();
                        doRequest();
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //            Toast.makeText(Pay_Activity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

        ;
    };

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }

        CancelOrderAPI api = new CancelOrderAPI(this, PrefUtils.getString(getApplication(), "uid", null), PrefUtils.getString(getApplication(), "trade_no", null), 1, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    cancelOrderBean = (CancelOrderBean) response.model;
                    if (cancelOrderBean.msgContext.equals("success")) {
                        Toast.makeText(getApplicationContext(), "支付已取消", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    cancelOrderBean = (CancelOrderBean) response.model;
                    //           meBagImageView.setImageResource(R.drawable.im_tou);
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    public void getaplaypay() {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Pay_Activity.this);
                Map<String, String> result = alipay.payV2(authInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void showdialogorder() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_order, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void shownotEnough() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_intenough, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    /*
     * 调起微信支付
	 */
   /* private void sendPayReq() {

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        api.registerApp(Constants.APP_ID);
        api.sendReq(req);
        Log.i(">>>>>", req.partnerId);
 /*   }*//*
    private void genPayReq() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayReq req = new PayReq();
                    req.appId = appIds;
                    req.partnerId = partnerIds;
                    req.nonceStr = nonceStrs;
                    req.timeStamp = timeStamps;
                    req.sign = signs;
                    req.prepayId = prepayIds;
                    req.packageValue = packageValues;
                    Toast.makeText(Pay_Activity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    api.sendReq(req);
                } catch (Exception e) {
                    Log.e("PAY_GET", "异常：" + e.getMessage());
//                    Toast.makeText(Pay_Activity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).start();

    }*/

    public void getQQ_WeiChat() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        Weichat_QQAPI api = new Weichat_QQAPI(this, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    weichatQqMess = (Weichat_QQ_mess) response.model;
                    if (weichatQqMess.msgContext.equals("datasSuc")) {
                        QQNumber.setText(weichatQqMess.datas.QQ);
                    } else {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    weichatQqMess = (Weichat_QQ_mess) response.model;

                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }
}