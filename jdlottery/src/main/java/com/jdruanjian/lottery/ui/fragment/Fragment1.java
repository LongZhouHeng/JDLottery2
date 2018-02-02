package com.jdruanjian.lottery.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.LotteryListAdapter;
import com.jdruanjian.lottery.model.LotteryListModel;
import com.jdruanjian.lottery.model.NewBannermodel;
import com.jdruanjian.lottery.model.entity.LotteryList;
import com.jdruanjian.lottery.model.entity.NewPeriodBean;
import com.jdruanjian.lottery.model.entity.NewPeriodList;
import com.jdruanjian.lottery.model.entity.SignCountBean;
import com.jdruanjian.lottery.model.entity.UpDateVerSion;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.LotteryTypeAPI;
import com.jdruanjian.lottery.model.net.NewBannerAPI;
import com.jdruanjian.lottery.model.net.NewPeroidAPI;
import com.jdruanjian.lottery.model.net.SignCountAPI;
import com.jdruanjian.lottery.model.net.VerSionUpDateAPI;
import com.jdruanjian.lottery.ui.activity.AddLott_Activity;
import com.jdruanjian.lottery.ui.activity.Banner_Activity;
import com.jdruanjian.lottery.ui.activity.Calculator_Activity;
import com.jdruanjian.lottery.ui.activity.ChargeList_Activity;
import com.jdruanjian.lottery.ui.activity.ExtendShare_Activity;
import com.jdruanjian.lottery.ui.activity.LoginActivity;
import com.jdruanjian.lottery.ui.activity.Personnal_Activity;
import com.jdruanjian.lottery.ui.activity.UseGuide_Activity;
import com.jdruanjian.lottery.util.GlideImageLoader;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by 龙龙 on 2017/8/21.
 */

public class Fragment1 extends BaseFragment implements OnClickListener {


    @BindView(R.id.iv_title)
    LinearLayout ivTitle;
    @BindView(R.id.iv_qiandao)
    RelativeLayout ivQiandao;
    @BindView(R.id.tv_lotname)
    TextView tvLotname;
    @BindView(R.id.tv_lotperiod)
    TextView tvLotperiod;
    @BindView(R.id.counter_result)
    LinearLayout counterResult;
    @BindView(R.id.tv_three_1)
    TextView tvThree1;
    @BindView(R.id.tv_three_2)
    TextView tvThree2;
    @BindView(R.id.tv_three_3)
    TextView tvThree3;
    @BindView(R.id.three_num)
    LinearLayout threeNum;
    @BindView(R.id.tv_four_1)
    TextView tvFour1;
    @BindView(R.id.tv_four_2)
    TextView tvFour2;
    @BindView(R.id.tv_four_3)
    TextView tvFour3;
    @BindView(R.id.tv_four_4)
    TextView tvFour4;
    @BindView(R.id.tv_four_5)
    TextView tvFour5;
    @BindView(R.id.four_num)
    LinearLayout fourNum;
    @BindView(R.id.tv_five_1)
    TextView tvFive1;
    @BindView(R.id.tv_five_2)
    TextView tvFive2;
    @BindView(R.id.tv_five_3)
    TextView tvFive3;
    @BindView(R.id.tv_five_4)
    TextView tvFive4;
    @BindView(R.id.tv_five_5)
    TextView tvFive5;
    @BindView(R.id.tv_five_6)
    TextView tvFive6;
    @BindView(R.id.tv_five_7)
    TextView tvFive7;
    @BindView(R.id.tv_five_8)
    TextView tvFive8;
    @BindView(R.id.tv_five_9)
    TextView tvFive9;
    @BindView(R.id.tv_five_10)
    TextView tvFive10;
    @BindView(R.id.five_num)
    LinearLayout fiveNum;
    @BindView(R.id.tv_two_1)
    TextView tvTwo1;
    @BindView(R.id.tv_two_2)
    TextView tvTwo2;
    @BindView(R.id.tv_two_3)
    TextView tvTwo3;
    @BindView(R.id.tv_two_4)
    TextView tvTwo4;
    @BindView(R.id.tv_two_5)
    TextView tvTwo5;
    @BindView(R.id.two_num)
    LinearLayout twoNum;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_login_1)
    TextView tvLogin1;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_sign_1)
    TextView tvSign1;
    private MaterialRefreshLayout materialRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.add_lottery)
    LinearLayout addLottery;
    @BindView(R.id.charge_list)
    LinearLayout chargeList;
    @BindView(R.id.user_guide)
    LinearLayout userGuide;
    @BindView(R.id.spread_share)
    LinearLayout spreadShare;
    private ListView mListView;
    private LotteryListModel model;
    private NewPeriodBean newPeriodBean;
    private SignCountBean signCountBean;
    private ArrayList<NewPeriodList> newPeriodList;
    private ArrayList<LotteryList> mLotteryList;
    private LotteryListAdapter mAdapter;
    private int PageNum = 1;
    private String PageSize = "40";
    private String lottery_name;
    private String lottery_full_name;
    private String uid;
    public String NewNumber;
    private Dialog dialog;
    private int TIME = 30000;
    private Timer timer;
    private int flag = 1;
    private UpDateVerSion upDateVerSion;
    private String version;
    private String name = "android";
    //   private NewBannerBean bannerBean;
    private NewBannermodel bannermodel;
    private Banner banner;
    List<String> list = new ArrayList<>();
    private String url ,url_0,url_1,url_2,url_3;

    private int tag =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment1, container, false);
        unbinder = ButterKnife.bind(this, layout);
        setBar(layout);
        VerSionUpDate();
        banner = (Banner) layout.findViewById(R.id.banner);
        getBanner();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannermodel.datas.get(position).url !=null){
                    Uri uri = Uri.parse(bannermodel.datas.get(position).url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else {

                }

            }
        });
        mListView = (ListView) layout.findViewById(R.id.lv);
        mLotteryList = new ArrayList<LotteryList>();
        mAdapter = new LotteryListAdapter(getActivity(), mLotteryList);
        mListView.setAdapter(mAdapter);
        newPeriodList = new ArrayList<NewPeriodList>();
        if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {

            uid = PrefUtils.getString(getActivity().getApplication(), "uid", null);

        } else {
            uid = "";
        }
        materialRefreshLayout = (MaterialRefreshLayout) layout.findViewById(R.id.refresh);
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //   Toast.makeText(getActivity(), "pull refresh", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    /*    if (!NetworkUtils.isNetworkAvaliable(getActivity())) {

                        } else {*/
                        PageNum = 1;
                        doRequest();
                        //                 }

                    }
                }, 1000);

            }

            @Override
            public void onfinish() {
                //  Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                //     Toast.makeText(getActivity(), "load more", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        /*if (!NetworkUtils.isNetworkAvaliable(getActivity())) {

                        } else {*/
                        PageNum++;
                        doRequest();
                        //            }


                    }
                }, 1000);
            }
        });
        materialRefreshLayout.autoRefresh();
        /*if (!NetworkUtils.isNetworkAvaliable(getActivity())) {

        } else {*/
        timer = new Timer();
        timer.schedule(task, TIME, TIME);
        //    }

        return layout;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (lottery_name != null && lottery_name != "") {
                    updatenumber();
                } else {
                    getupdate();
                }

            }
            super.handleMessage(msg);
        }

        ;
    };

    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    private void getnewnum() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (mLotteryList.get(position).getLottery_name() != null) {
                    if (isLoading) {
                        return;
                    }

                    if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
                        toastIfActive(R.string.errcode_network_unavailable);

                        return;
                    }
                    NewPeroidAPI api = new NewPeroidAPI(getActivity(), mLotteryList.get(position).getLottery_name(), new BasicResponse.RequestListener() {
                        @Override
                        public void onComplete(BasicResponse response) {
                            if (response.error == BasicResponse.SUCCESS) {
                                newPeriodBean = (NewPeriodBean) response.model;
                                if (newPeriodBean.msgContext.equals("datasSuc") && !tvLotperiod.getText().toString().equals("")) {
                                    tvLotperiod.setText(newPeriodBean.datas.get(0).getPeriod());
                                    //            System.out.println("RRRRRR=======" + tvLotperiod);
                                    tvLotname.setText(mLotteryList.get(position).getLottery_full_name());
                                    lottery_name = mLotteryList.get(position).getLottery_name();
                                    lottery_full_name = mLotteryList.get(position).getLottery_full_name();
                                    //               Toast.makeText(getActivity().getApplication(),mLotteryList.get(position).getLottery_name(),Toast.LENGTH_SHORT).show();
                                    //                Toast.makeText(getActivity().getApplication(),mLotteryList.get(position).getLottery_name(),Toast.LENGTH_SHORT).show();
                                    NewNumber = newPeriodBean.datas.get(0).getNumber();
                                    //            System.out.println("RRRRRR+++++++" + NewNumber);
                                    if (NewNumber.length() < 4 && NewNumber.length() > 0 && newPeriodBean.msgContext.equals("datasSuc")) {
                                        threeNum.setVisibility(View.VISIBLE);
                                        fourNum.setVisibility(View.GONE);
                                        fiveNum.setVisibility(View.GONE);
                                        twoNum.setVisibility(View.GONE);
                                        String one = NewNumber.substring(0, 1);
                                        String two = NewNumber.substring(1, 2);
                                        String three = NewNumber.substring(2);
                                        tvThree1.setText(one);
                                        tvThree2.setText(two);
                                        tvThree3.setText(three);

                                    } else if (NewNumber.length() < 6 && NewNumber.length() > 4 && newPeriodBean.msgContext.equals("datasSuc")) {
                                        threeNum.setVisibility(View.GONE);
                                        fourNum.setVisibility(View.VISIBLE);
                                        fiveNum.setVisibility(View.GONE);
                                        twoNum.setVisibility(View.GONE);
                                        String one = NewNumber.substring(0, 1);
                                        String two = NewNumber.substring(1, 2);
                                        String three = NewNumber.substring(2, 3);
                                        String four = NewNumber.substring(3, 4);
                                        String five = NewNumber.substring(4);
                                        tvFour1.setText(one);
                                        tvFour2.setText(two);
                                        tvFour3.setText(three);
                                        tvFour4.setText(four);
                                        tvFour5.setText(five);

                                    } else if (NewNumber.length() < 30 && NewNumber.length() > 19 && newPeriodBean.msgContext.equals("datasSuc")) {
                                        threeNum.setVisibility(View.GONE);
                                        fourNum.setVisibility(View.GONE);
                                        fiveNum.setVisibility(View.VISIBLE);
                                        twoNum.setVisibility(View.GONE);
                                        String[] num = NewNumber.split(",");
                                        tvFive1.setText(num[0]);
                                        tvFive2.setText(num[1]);
                                        tvFive3.setText(num[2]);
                                        tvFive4.setText(num[3]);
                                        tvFive5.setText(num[4]);
                                        tvFive6.setText(num[5]);
                                        tvFive7.setText(num[6]);
                                        tvFive8.setText(num[7]);
                                        tvFive9.setText(num[8]);
                                        tvFive10.setText(num[9]);
                                    } else if (NewNumber.length() < 15 && NewNumber.length() > 8 && newPeriodBean.msgContext.equals("datasSuc")) {
                                        threeNum.setVisibility(View.GONE);
                                        fourNum.setVisibility(View.GONE);
                                        fiveNum.setVisibility(View.GONE);
                                        twoNum.setVisibility(View.VISIBLE);
                                        String[] num = NewNumber.split(",");
                                        tvTwo1.setText(num[0]);
                                        tvTwo2.setText(num[1]);
                                        tvTwo3.setText(num[2]);
                                        tvTwo4.setText(num[3]);
                                        tvTwo5.setText(num[4]);
                                    }
                                }

                            } else {
                                newPeriodBean = (NewPeriodBean) response.model;
//                              newPeriodList.addAll((ArrayList<NewPeriodList>) newPeriodBean.datas);
//                              tvLotperiod.setText(newPeriodList.get(position).getPeriod());

                            }
                        }
                    });
                    //         api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
                    api.executeRequest(0);
                } else {
                    Toast.makeText(getActivity().getApplication(), "数据显示错误", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @OnClick({R.id.add_lottery, R.id.charge_list, R.id.user_guide, R.id.spread_share, R.id.iv_title, R.id.iv_qiandao, R.id.counter_result, R.id.banner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_lottery:
                Intent intent = new Intent(getActivity(), AddLott_Activity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.charge_list:
                Intent intent1 = new Intent(getActivity(), ChargeList_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("intent", "intent");
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 2);
                break;
            case R.id.counter_result:
                Intent intent_ca = new Intent(getActivity(), Calculator_Activity.class);
                startActivity(intent_ca);
                break;
            case R.id.user_guide:
                Intent intent_guide = new Intent(getActivity(), UseGuide_Activity.class);
                startActivity(intent_guide);
                break;
            case R.id.spread_share:
                Intent intent_share = new Intent(getActivity(), ExtendShare_Activity.class);
                startActivity(intent_share);
                break;
            case R.id.iv_title:

                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {

                    //          System.out.println("UFO------" + PrefUtils.getString(getActivity().getApplication(), "uid", null));
                    Intent intent_person = new Intent(getActivity(), Personnal_Activity.class);
                    startActivity(intent_person);

                } else {
                    Intent intent_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.iv_qiandao:
                if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                    doRequestint();
                } else {
                    Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent2);
                }
                break;

        }
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }

        LotteryTypeAPI api = new LotteryTypeAPI(getActivity(), uid, PageNum, PageSize, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (LotteryListModel) response.model;
                    if (PageNum == 1) {
                        mLotteryList.clear();
                        mLotteryList.addAll((ArrayList<LotteryList>) model.datas);
                        getnewnum();
                        if (lottery_name != null && lottery_name != "") {
                            updatenumber();
                        } else {
                            getupdate();
                        }

                        Log.e("heihei", mLotteryList.size() + "");
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    //       model = (LotteryListModel) response.model;
                    //           mLotteryList.clear();
                    //             mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity().getApplication(), "数据错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    public void getupdate() {

        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        } else {

        }

        NewPeroidAPI api = new NewPeroidAPI(getActivity(), mLotteryList.get(0).getLottery_name(), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    newPeriodBean = (NewPeriodBean) response.model;
                    tvLotperiod.setText(newPeriodBean.datas.get(0).getPeriod());
                    //         Toast.makeText(getActivity().getApplication(),newPeriodBean.datas.get(0).getPeriod(),Toast.LENGTH_SHORT).show();
                    tvLotname.setText(mLotteryList.get(0).getLottery_full_name());
                    //          Toast.makeText(getActivity().getApplication(),mLotteryList.get(0).getLottery_full_name(),Toast.LENGTH_SHORT).show();
                    NewNumber = newPeriodBean.datas.get(0).getNumber();
                    if (NewNumber.length() < 4 && NewNumber.length() > 0) {
                        threeNum.setVisibility(View.VISIBLE);
                        fourNum.setVisibility(View.GONE);
                        fiveNum.setVisibility(View.GONE);
                        twoNum.setVisibility(View.GONE);
                        String one = NewNumber.substring(0, 1);
                        String two = NewNumber.substring(1, 2);
                        String three = NewNumber.substring(2);
                        tvThree1.setText(one);
                        tvThree2.setText(two);
                        tvThree3.setText(three);
                    } else if (NewNumber.length() < 6 && NewNumber.length() > 4) {
                        threeNum.setVisibility(View.GONE);
                        fourNum.setVisibility(View.VISIBLE);
                        fiveNum.setVisibility(View.GONE);
                        twoNum.setVisibility(View.GONE);
                        String one = NewNumber.substring(0, 1);
                        String two = NewNumber.substring(1, 2);
                        String three = NewNumber.substring(2, 3);
                        String four = NewNumber.substring(3, 4);
                        String five = NewNumber.substring(4);
                        tvFour1.setText(one);
                        tvFour2.setText(two);
                        tvFour3.setText(three);
                        tvFour4.setText(four);
                        tvFour5.setText(five);

                    } else if (NewNumber.length() < 30 && NewNumber.length() > 19) {
                        threeNum.setVisibility(View.GONE);
                        fourNum.setVisibility(View.GONE);
                        fiveNum.setVisibility(View.VISIBLE);
                        twoNum.setVisibility(View.GONE);
                        String[] num = NewNumber.split(",");
                        tvFive1.setText(num[0]);
                        tvFive2.setText(num[1]);
                        tvFive3.setText(num[2]);
                        tvFive4.setText(num[3]);
                        tvFive5.setText(num[4]);
                        tvFive6.setText(num[5]);
                        tvFive7.setText(num[6]);
                        tvFive8.setText(num[7]);
                        tvFive9.setText(num[8]);
                        tvFive10.setText(num[9]);
                    } else if (NewNumber.length() < 15 && NewNumber.length() > 8) {
                        threeNum.setVisibility(View.GONE);
                        fourNum.setVisibility(View.GONE);
                        fiveNum.setVisibility(View.GONE);
                        twoNum.setVisibility(View.VISIBLE);
                        String[] num = NewNumber.split(",");
                        tvTwo1.setText(num[0]);
                        tvTwo2.setText(num[1]);
                        tvTwo3.setText(num[2]);
                        tvTwo4.setText(num[3]);
                        tvTwo5.setText(num[4]);
                    }
                } else {
                    //      newPeriodBean = (NewPeriodBean) response.model;
                    //          newPeriodList.addAll((ArrayList<NewPeriodList>) newPeriodBean.datas);
                    //          tvLotperiod.setText(newPeriodList.get(0).getPeriod());
                    //           tvLotname.setText(mLotteryList.get(0).getLottery_full_name());
                    //            NewNumber = newPeriodList.get(0).getNumber();
                }
            }
        });
        api.executeRequest(0);
    }

    private void updatenumber() {
        if (lottery_name != null) {
            if (isLoading) {
                return;
            }

            if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
                toastIfActive(R.string.errcode_network_unavailable);

                return;
            }
            NewPeroidAPI api = new NewPeroidAPI(getActivity(), lottery_name, new BasicResponse.RequestListener() {
                @Override
                public void onComplete(BasicResponse response) {
                    if (response.error == BasicResponse.SUCCESS) {
                        newPeriodBean = (NewPeriodBean) response.model;
                        if (newPeriodBean.msgContext.equals("datasSuc") && !tvLotperiod.getText().toString().equals("")) {
                            tvLotperiod.setText(newPeriodBean.datas.get(0).getPeriod());
                            //          System.out.println("RRRRRR=======" + tvLotperiod);
                            tvLotname.setText(lottery_full_name);
                            NewNumber = newPeriodBean.datas.get(0).getNumber();
                            //             System.out.println("RRRRRR+++++++" + NewNumber);
                            if (NewNumber.length() < 4 && NewNumber.length() > 0 && newPeriodBean.msgContext.equals("datasSuc")) {
                                threeNum.setVisibility(View.VISIBLE);
                                fourNum.setVisibility(View.GONE);
                                fiveNum.setVisibility(View.GONE);
                                twoNum.setVisibility(View.GONE);
                                String one = NewNumber.substring(0, 1);
                                String two = NewNumber.substring(1, 2);
                                String three = NewNumber.substring(2);
                                tvThree1.setText(one);
                                tvThree2.setText(two);
                                tvThree3.setText(three);

                            } else if (NewNumber.length() < 6 && NewNumber.length() > 4 && newPeriodBean.msgContext.equals("datasSuc")) {
                                threeNum.setVisibility(View.GONE);
                                fourNum.setVisibility(View.VISIBLE);
                                fiveNum.setVisibility(View.GONE);
                                twoNum.setVisibility(View.GONE);
                                String one = NewNumber.substring(0, 1);
                                String two = NewNumber.substring(1, 2);
                                String three = NewNumber.substring(2, 3);
                                String four = NewNumber.substring(3, 4);
                                String five = NewNumber.substring(4);
                                tvFour1.setText(one);
                                tvFour2.setText(two);
                                tvFour3.setText(three);
                                tvFour4.setText(four);
                                tvFour5.setText(five);

                            } else if (NewNumber.length() < 30 && NewNumber.length() > 19 && newPeriodBean.msgContext.equals("datasSuc")) {
                                threeNum.setVisibility(View.GONE);
                                fourNum.setVisibility(View.GONE);
                                fiveNum.setVisibility(View.VISIBLE);
                                twoNum.setVisibility(View.GONE);
                                String[] num = NewNumber.split(",");
                                tvFive1.setText(num[0]);
                                tvFive2.setText(num[1]);
                                tvFive3.setText(num[2]);
                                tvFive4.setText(num[3]);
                                tvFive5.setText(num[4]);
                                tvFive6.setText(num[5]);
                                tvFive7.setText(num[6]);
                                tvFive8.setText(num[7]);
                                tvFive9.setText(num[8]);
                                tvFive10.setText(num[9]);
                            } else if (NewNumber.length() < 15 && NewNumber.length() > 8 && newPeriodBean.msgContext.equals("datasSuc")) {
                                threeNum.setVisibility(View.GONE);
                                fourNum.setVisibility(View.GONE);
                                fiveNum.setVisibility(View.GONE);
                                twoNum.setVisibility(View.VISIBLE);
                                String[] num = NewNumber.split(",");
                                tvTwo1.setText(num[0]);
                                tvTwo2.setText(num[1]);
                                tvTwo3.setText(num[2]);
                                tvTwo4.setText(num[3]);
                                tvTwo5.setText(num[4]);
                            }
                        }

                    } else {
                        //              newPeriodBean = (NewPeriodBean) response.model;
//                              newPeriodList.addAll((ArrayList<NewPeriodList>) newPeriodBean.datas);
//                              tvLotperiod.setText(newPeriodList.get(position).getPeriod());

                    }
                }
            });
            //         api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
            api.executeRequest(0);
        } else {
            //   Toast.makeText(getActivity().getApplication(), "数据显示错误", Toast.LENGTH_SHORT).show();
            getupdate();
        }
    }

    public void showdialog() {
        String integer = signCountBean.signIntegral;
        String signday = signCountBean.signCount;
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sgin, null);
        TextView tvinteger = (TextView) localView.findViewById(R.id.tv_integer);
        TextView tvsginday = (TextView) localView.findViewById(R.id.tv_sginday);
        Button btnshut = (Button) localView.findViewById(R.id.btn_shut);
        tvinteger.setText(integer);
        tvsginday.setText(signday);
        dialog = new Dialog(getActivity(), R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.TOP);
        // 设置全屏
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        btnshut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void doRequestint() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        SignCountAPI api = new SignCountAPI(getActivity(), PrefUtils.getString(getActivity().getApplication(), "uid", null), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    signCountBean = (SignCountBean) response.model;
                    if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null && signCountBean.msgContext.equals("success")) {
                        showdialog();
                    } else if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null && signCountBean.msgContext.equals("signed")) {

                        Toast.makeText(getActivity().getApplication(), "你已签过到了", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    signCountBean = (SignCountBean) response.model;

                }
            }
        });

        api.executeRequest(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            if (lottery_name != null && lottery_name != "") {
                updatenumber();
            } else {
                getupdate();
            }

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            doRequest();
            if (lottery_name != null && lottery_name != "") {
                updatenumber();
            } else {
                getupdate();
            }
        }
        if (resultCode == 2) {
            doRequest();
            if (lottery_name != null) {
                updatenumber();
            } else {
                getupdate();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        getActivity().moveTaskToBack(true);
        super.onDestroyView();
//        unbinder.unbind();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        //    timer.cancel();
        super.onPause();

    }

    @Override
    public void onStop() {
        //    timer.cancel();
        //     unbinder.unbind();
        super.onStop();
    }

    @Override
    public void onDetach() {
        //       timer.cancel();
        //     unbinder.unbind();
        super.onDetach();

    }

    /**
     * 从onStop回到Activity的时候会执行
     * 按HOME键的时候会执行onStop,重新回到程序会执行这个方法
     */
    @Override
    public void onStart() {

        super.onStart();
    }

    //检测本程序的版本，这里假设从服务器中获取到最新的版本号为3
    public void checkVersion() {
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
        if (getVersionName() < Float.parseFloat(version)) {
            showdialogupdate();
        } else {
            //否则吐司，说现在是最新的版本
            //         Toast.makeText(getActivity(),"当前已经是最新的版本",Toast.LENGTH_SHORT).show();

        }
    }

    private void showdialogupdate() {

        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_checkupdate, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(getActivity(), R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        tv_ensure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //    dialog.dismiss();
                loadNewVersionProgress();
            }
        });

    }

    /**
     * 下载新版本程序
     */
    private void loadNewVersionProgress() {
        final String uri = "http://120.77.84.221:19303/PK10SSCZS.APK";
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载...");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                    Toast.makeText(getActivity(), "更新完成", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    //下载apk失败
                    Toast.makeText(getActivity(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finishActivity();
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
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

        return 1;


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

        return 2;
    }

    public void VerSionUpDate() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        VerSionUpDateAPI api = new VerSionUpDateAPI(getActivity(), name, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    upDateVerSion = (UpDateVerSion) response.model;
                    if (upDateVerSion.msgContext.equals("versionUpdate")) {
                        version = upDateVerSion.num;
                        System.out.println("111111+++++==="+version);
                        checkVersion();
                    }
                } else {
                    upDateVerSion = (UpDateVerSion) response.model;
                    //           meBagImageView.setImageResource(R.drawable.im_tou);
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    private void getBanner() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        NewBannerAPI api = new NewBannerAPI(getActivity(), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    bannermodel = (NewBannermodel) response.model;
                    //   bannerBean = (NewBannerBean) response.model;
                    if (bannermodel.msg.equals("success")) {
                        //把图片放到集合中

                        for (int i = 0; i < bannermodel.datas.size(); i++) {

                            list.add(bannermodel.datas.get(i).img);

                        }
                        //设置轮播时间
                        //设置内置样式，共有六种可以点入方法内逐一体验使用。
                  //      banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                        banner.setDelayTime(3000);
                        //调用ImageApp()方法实现图片的加载
                        banner.setImageLoader(new GlideImageLoader());
                        banner.setScrollX(WindowManager.LayoutParams.MATCH_PARENT);
                        banner.setScrollY(WindowManager.LayoutParams.MATCH_PARENT);
                        banner.setImages(list);
                        banner.start();

                    }
                } else {
                    bannermodel = (NewBannermodel) response.model;
                    //           meBagImageView.setImageResource(R.drawable.im_tou);
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }


}
