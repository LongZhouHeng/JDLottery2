package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.PersonIAliplayBean;
import com.jdruanjian.lottery.model.entity.PersonInfoBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.PersonInfoAPI;
import com.jdruanjian.lottery.model.net.PersonlAliplayAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Longlong on 2017/9/28.
 */

public class PersonInfo_Activity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_banknum)
    EditText etBanknum;
    @BindView(R.id.et_bankname)
    EditText etBankname;
    @BindView(R.id.et_phonenum)
    EditText etPhonenum;
    @BindView(R.id.et_aliplay)
    EditText etAliplay;
    @BindView(R.id.btn_refer_1)
    Button btnRefer1;
    @BindView(R.id.tv_bankinfo)
    TextView tvBankinfo;
    @BindView(R.id.btn_refer)
    Button btnRefer;
    @BindView(R.id.ll_bankinfo)
    LinearLayout llBankinfo;
    @BindView(R.id.tv_alipalyinfo)
    TextView tvAlipalyinfo;
    @BindView(R.id.ll_alipalyinfo)
    LinearLayout llAlipalyinfo;
    private PersonInfoBean personInfoBean;
    private PersonIAliplayBean personIAliplayBean;
    private boolean visibility_Flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);
        setTitlebar("个人身份信息");
        showBackBtn();
        setBar();

    }
    @OnClick({R.id.tv_bankinfo, R.id.btn_refer, R.id.tv_alipalyinfo, R.id.btn_refer_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bankinfo:
                if (visibility_Flag) {
                    getPersonInfo();
                    llBankinfo.setVisibility(View.VISIBLE);
                    visibility_Flag = false;
                } else {
                    llBankinfo.setVisibility(View.GONE);
                    visibility_Flag = true;
                }
                break;
            case R.id.btn_refer:
                Intent intent = new Intent(this,BindBankNum_Acitivity.class);
                startActivity(intent);
                break;
            case R.id.tv_alipalyinfo:
                 if (visibility_Flag) {
                     getAlipalyNum();
                     llAlipalyinfo.setVisibility(View.VISIBLE);
                     visibility_Flag = false;
                 } else {
                     llAlipalyinfo.setVisibility(View.GONE);
                     visibility_Flag = true;
                 }
                break;
            case R.id.btn_refer_1:
                Intent intent1 = new Intent(this,BindAliPlayNum_Acitivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void getPersonInfo() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(PersonInfo_Activity.this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        PersonInfoAPI api = new PersonInfoAPI(PersonInfo_Activity.this, PrefUtils.getString(getApplication(), "uid", null), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    personInfoBean = (PersonInfoBean) response.model;
                    if (personInfoBean.msgContext.equals("haven'tBankNum")){

                        Intent intent2 = new Intent(PersonInfo_Activity.this,BindBankNum_Acitivity.class);
                        startActivity(intent2);
                    }else if (personInfoBean.msgContext.equals("datasSuc")&&personInfoBean.datas !=null ) {

                        etName.setText(personInfoBean.datas.uname);

                        String banknum = personInfoBean.datas.ubanknum;
                        StringBuilder bkn = new StringBuilder();
                        for (int i = 0; i < banknum.length(); i++) {
                            char c = banknum.charAt(i);
                            if (i >= 0 && i <= 14) {
                                bkn.append('*');
                            } else {
                                bkn.append(c);
                            }
                        }
                        etBanknum.setText(bkn.toString());

                        etBankname.setText(personInfoBean.datas.ubankname);
                        String phnum = PrefUtils.getString(getApplication(), "phono", null);
                        StringBuilder num = new StringBuilder();
                        for (int j = 0; j < phnum.length(); j++) {
                            char d = phnum.charAt(j);
                            if (j > 2 && j <= 7) {
                                num.append('*');
                            } else {
                                num.append(d);
                            }
                        }
                        etPhonenum.setText(num.toString());
                    }

                } else {
                    personInfoBean = (PersonInfoBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    public void getAlipalyNum() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(PersonInfo_Activity.this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        PersonlAliplayAPI api = new PersonlAliplayAPI(PersonInfo_Activity.this, PrefUtils.getString(getApplication(), "uid", null), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    personIAliplayBean = (PersonIAliplayBean) response.model;
                    if (PrefUtils.getString(getApplication(), "uid", null) != null&&
                            personIAliplayBean.msgContext.equals("datasSuc")) {
                        //      etAliplay.setText(personIAliplayBean.datas);
                        String phnum = personIAliplayBean.datas;
                        StringBuilder num = new StringBuilder();
                        for (int j = 0; j < phnum.length(); j++) {
                            char d = phnum.charAt(j);
                            if (j > 2 && j <= 7) {
                                num.append('*');
                            } else {
                                num.append(d);
                            }
                        }
                        etAliplay.setText(num.toString());
                    } else {
             //           Intent intent1 = new Intent(PersonInfo_Activity.this,BindAliPlayNum_Acitivity.class);
          //              startActivity(intent1);
                    }
                } else {
                    personIAliplayBean = (PersonIAliplayBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }


}
