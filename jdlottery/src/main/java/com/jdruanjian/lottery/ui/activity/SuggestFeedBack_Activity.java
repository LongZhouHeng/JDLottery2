package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.FeedBackmess;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.model.entity.Weichat_QQ_mess;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.FeedBackAPI;
import com.jdruanjian.lottery.model.net.UsersAPI;
import com.jdruanjian.lottery.model.net.Weichat_QQAPI;
import com.jdruanjian.lottery.util.ControlNumEditText;
import com.jdruanjian.lottery.util.ControlNumEditText.onTextEditListener;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/31.
 */

public class SuggestFeedBack_Activity extends BaseActivity {
    @BindView(R.id.et)
    ControlNumEditText et;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.btn_refer_message)
    Button btnReferMessage;
    @BindView(R.id.QQ_number)
    TextView QQNumber;
    private int MAX_NUM = 200;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    private Weichat_QQ_mess weichatQqMess;
    private FeedBackmess feedBackmess;
    private String message;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * 编辑框监听器
     */
    private TextWatcher mTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        setTitlebar("意见反馈");
        showBackBtn();
        setBar();
        getQQ_WeiChat();
        et = (ControlNumEditText) findViewById(R.id.et);
        tvShow = (TextView) findViewById(R.id.tv_show);
        //下面这两个方法你只需要设置一个就行，因为这两个方法都会知道一个最大的输入值。
        //You just need to write one of these two method below.Because you need to know the max number.
        et.setMaxNum(MAX_NUM);
        MAX_NUM = et.getMaxNum();

        et.setOnTextEditListener(new onTextEditListener() {

            @Override
            public void textChanged(int cur_num) {
                tvShow.setText(String.valueOf((MAX_NUM - cur_num)));
            }
        });
        message = et.getText().toString().trim();
    }

    @OnClick(R.id.btn_refer_message)
    public void onViewClicked() {
        if (validate()){
            doRequest();
        }
    }

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


    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        FeedBackAPI api = new FeedBackAPI(this, PrefUtils.getString(getApplication(), "uid", null), message, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    feedBackmess = (FeedBackmess) response.model;
                    System.out.println("WWWWW+++++--" + message);
                    if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                        if (!TextUtils.isEmpty(message) && feedBackmess.msgContext.equals("addSuc")) {
                            Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(SuggestFeedBack_Activity.this, LoginActivity.class);
                        startActivity(intent);
                    }


                } else {
                    feedBackmess = (FeedBackmess) response.model;
                    //           meBagImageView.setImageResource(R.drawable.im_tou);
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    // 验证方法
    private boolean validate() {
        // 获得手机号码
        message = et.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(SuggestFeedBack_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}