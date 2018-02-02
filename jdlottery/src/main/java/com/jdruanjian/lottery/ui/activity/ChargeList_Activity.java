package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.MainActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.GridViewAdapter;
import com.jdruanjian.lottery.model.ChargeListModel;
import com.jdruanjian.lottery.model.LotteryListModel;
import com.jdruanjian.lottery.model.entity.ChargeListBean;
import com.jdruanjian.lottery.model.entity.LotteryList;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.ChargeListAPI;
import com.jdruanjian.lottery.model.net.LotteryTypeAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/29.
 */

public class ChargeList_Activity extends BaseActivity {

    @BindView(R.id.tv_title_jieguo)
    TextView tvTitleJieguo;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.layout_title)    //点击出现弹窗选择彩种
            LinearLayout layoutTitle;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ExpandableListView mListView;
    private List<ChargeListBean> group_list;
    private List<ChargeListBean.ChargeListArrBean> child_list;
    private ChargeListBean strbean;
    private ChargeListBean.ChargeListArrBean membean;
    private LayoutInflater mInflater;
    private ChargeListModel chargeListModel;
    private Adapter adapter;
    private boolean visibility_Flag = false;
    private int PageNum = 1;
    private String PageSize = "20";
    private LotteryListModel model;
    private ArrayList<LotteryList> mLotteryList;
    public String Lottname;
    public String Lottery_name;
    public String Lottery_name1;
    private int flag = -1;
    private String uid;
    private String interfaceStatus = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargelist);
        ButterKnife.bind(this);
        setBar();
        Bundle bundle = ChargeList_Activity.this.getIntent().getExtras();
        tvTitleJieguo = (TextView) findViewById(R.id.tv_title_jieguo);
        mLotteryList = new ArrayList<LotteryList>();
        gridView = (GridView) findViewById(R.id.gv_changelist);
        gridViewAdapter = new GridViewAdapter(this, mLotteryList);
        gridView.setAdapter(gridViewAdapter);
        layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        layoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibility_Flag) {
                    gridView.setVisibility(View.VISIBLE);
                    visibility_Flag = false;
                    flag = 1;
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            gridViewAdapter.setSeclection(position);
                            gridViewAdapter.notifyDataSetChanged();
                            tvTitleJieguo.setText(mLotteryList.get(position).getLottery_full_name());
                            Lottname = mLotteryList.get(position).getLottery_name();
                            if (flag == 1) {
                                Lottery_name = Lottname;
                                setTitleotther(mLotteryList.get(position).getLottery_full_name());
                                doRequest();
                                gridView.setVisibility(View.GONE);
                                visibility_Flag = true;
                            }

                        }
                    });
                } else {
                    gridView.setVisibility(View.GONE);
                    visibility_Flag = true;

                }
            }
        });
        if (PrefUtils.getString(getApplication(), "uid", null) != null) {
            uid = PrefUtils.getString(getApplication(), "uid", null);
        } else {
            uid = "";
        }
        if (bundle.getString("intent").equals("intent")) {
            getGridRequest();
        } else if (bundle.getString("intent").equals("intent2")) {
            getGridRequest();
        } else if (bundle.getString("intent").equals("intent4")) {
            getGridRequest();
        }
        initView();

    }

    public void getGridRequest() {

        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }
        LotteryTypeAPI api = new LotteryTypeAPI(this, uid, PageNum, PageSize, new BasicResponse.RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (LotteryListModel) response.model;
                    Bundle bundle = ChargeList_Activity.this.getIntent().getExtras();
                    if (flag == -1) {
                        mLotteryList.clear();
                        mLotteryList.addAll((ArrayList<LotteryList>) model.datas);
                        setTitleotther(mLotteryList.get(0).getLottery_full_name());
                        Lottery_name = mLotteryList.get(0).getLottery_name();
                        gridViewAdapter.setSeclection(0);
                        doRequest();
                        Log.e("heihei", mLotteryList.size() + "");
                        gridViewAdapter.notifyDataSetChanged();
                    }

                } else {
                    model = (LotteryListModel) response.model;
                    mLotteryList.clear();
                    gridViewAdapter.notifyDataSetChanged();
                }
            }
        });
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

        ChargeListAPI api = new ChargeListAPI(this, Lottery_name, new BasicResponse.RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {

                if (response.error == BasicResponse.SUCCESS) {
                    chargeListModel = (ChargeListModel) response.model;
                    if (chargeListModel.msgContext.equals("datasSuc")) {
                        group_list.clear();
                        group_list.addAll((ArrayList<ChargeListBean>) chargeListModel.datas);
                        adapter.notifyDataSetChanged();
                    } else {
                        group_list.clear();
                        group_list.addAll((ArrayList<ChargeListBean>) chargeListModel.datas);
                        adapter.notifyDataSetChanged();
                    }


                } else {
                    chargeListModel = (ChargeListModel) response.model;
                    group_list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);
    }

    private void initView() {
        mListView = (ExpandableListView) findViewById(R.id.list);
        mInflater = LayoutInflater.from(ChargeList_Activity.this);
        group_list = new ArrayList<ChargeListBean>();
        for (int i = 0; i < group_list.size(); i++) {
            strbean = new ChargeListBean();
            child_list = new ArrayList<ChargeListBean.ChargeListArrBean>();
            for (int j = 0; j < child_list.size(); j++) {
                membean = new ChargeListBean.ChargeListArrBean();
                child_list.add(membean);
            }
            strbean.setList(child_list);
            group_list.add(strbean);
        }
        adapter = new Adapter();
        mListView.setGroupIndicator(null);

        /**
         * ExpandableListView的组监听事件
         */
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                //         Toast.makeText(SimpleExpandActivity1.this, "第" + groupPosition + "组被点击了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /**
         * ExpandableListView的组展开监听
         */
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //       Toast.makeText(SimpleExpandActivity1.this, "第" + groupPosition + "组展开", Toast.LENGTH_SHORT).show();
                //       control.setText("点击展开");
            }
        });
        /**
         * ExpandableListView的组合拢监听
         */
        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //           Toast.makeText(SimpleExpandActivity1.this, "第" + groupPosition + "组合拢", Toast.LENGTH_SHORT).show();

            }
        });
        /**
         * ExpandableListView的子元素点击监听
         */
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //          Toast.makeText(SimpleExpandActivity1.this, "第" + groupPosition + "组的第" + childPosition + "被点击了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mListView.setAdapter(adapter);
        //设置Group默认收起
        int groupCount = mListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            mListView.collapseGroup(i);
            //     mListView.expandGroup(i);//展开
        }
    }


    @OnClick({R.id.btn_back, R.id.btn_allots})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent = new Intent(this, MainActivity.class);
                setResult(2, intent);
                finish();
                break;
            case R.id.btn_allots:
                Intent intent_all = new Intent(this, Pay_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("alllots","所有彩种");
                bundle.putString("allplans","全部计划");
                bundle.putString("intent","all");
                intent_all.putExtras(bundle);
                startActivity(intent_all);
                break;
        }
    }


    //自定义适配器
    class Adapter extends BaseExpandableListAdapter {
        //获取子元素对象
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        //获取子元素Id
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //加载子元素并显示
        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            View view = null;
            ChildHolder childholder = null;
            if (convertView != null) {
                view = convertView;
                childholder = (ChildHolder) view.getTag();
            } else {
                view = View.inflate(ChargeList_Activity.this, R.layout.listitem_planlist_2, null);
                childholder = new ChildHolder();
                childholder.mImage = (TextView) view.findViewById(R.id.tv_buy);
                childholder.mPrice = (TextView) view.findViewById(R.id.tv_more_info);
                childholder.mStateText = (TextView) view.findViewById(R.id.tv_plan);
                childholder.mSecondPrice = (TextView) view.findViewById(R.id.tv_money);
                view.setTag(childholder);
            }
            childholder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                        Intent intent = new Intent(ChargeList_Activity.this, Pay_Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("lottname", group_list.get(groupPosition).lotTypeName);
                        bundle.putString("money", group_list.get(groupPosition).chargeListArr.get(childPosition).money);
                        //             System.out.println("MMMMMMMMMMMMM------====="+group_list.get(groupPosition).chargeListArr.get(childPosition).money);
                        bundle.putString("payplan", group_list.get(groupPosition).chargeListArr.get(childPosition).planname);
                        bundle.putString("body", group_list.get(groupPosition).chargeListArr.get(childPosition).plandate);
                        bundle.putString("intent", "intent3");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ChargeList_Activity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
            childholder.mPrice.setText(group_list.get(groupPosition)
                    .getList().get(childPosition).planname);
            int len = group_list.get(groupPosition)
                    .getList().size();
            System.out.println(len + "-----------------");
            childholder.mStateText.setText(group_list.get(groupPosition)
                    .getList().get(childPosition).plandate);
            childholder.mSecondPrice.setText(group_list.get(groupPosition)
                    .getList().get(childPosition).money);
            return view;
        }

        //获取子元素数目
        @Override
        public int getChildrenCount(int groupPosition) {
            return group_list.get(groupPosition).chargeListArr.size();
        }

        //获取组元素对象
        @Override
        public Object getGroup(int groupPosition) {
            return group_list.get(groupPosition);
        }

        //获取组元素数目
        @Override
        public int getGroupCount() {
            return group_list.size();
        }

        //获取组元素Id
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //加载并显示组元素
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            View view = null;
            GroupHolder groupholder = null;
            if (convertView != null) {
                view = convertView;
                groupholder = (GroupHolder) view.getTag();
            } else {
                view = View.inflate(ChargeList_Activity.this, R.layout.listitem_planlist_1, null);
                groupholder = new GroupHolder();
                groupholder.mSpaceText = (TextView) view.findViewById(R.id.tv_pro_all);
                groupholder.mLotDate = (TextView) view.findViewById(R.id.tv_all);
                groupholder.LL = (LinearLayout) view.findViewById(R.id.ll_plan);
                groupholder.control = (TextView) view.findViewById(R.id.tv_control);
                groupholder.imexpand = (ImageView) view.findViewById(R.id.iv_more);
                groupholder.impackup = (ImageView) view.findViewById(R.id.iv_up);
                view.setTag(groupholder);
            }
            if (groupPosition == 0) {
                groupholder.mSpaceText.setBackgroundResource(R.drawable.arrow_lott);
                groupholder.LL.setBackgroundResource(R.drawable.lott_next);
            }
            if (groupPosition == 1) {
                groupholder.mSpaceText.setBackgroundResource(R.drawable.arrow_lott1);
                groupholder.LL.setBackgroundResource(R.drawable.lott_next1);
            }
            if (groupPosition == 2) {
                groupholder.mSpaceText.setBackgroundResource(R.drawable.arrow_lott2);
                groupholder.LL.setBackgroundResource(R.drawable.lott_next2);
            }
            if (groupPosition == 3) {
                groupholder.mSpaceText.setBackgroundResource(R.drawable.arrow_lott2);
                groupholder.LL.setBackgroundResource(R.drawable.lott_next2);
            }
            if (isExpanded) {
                groupholder.control.setText("点击收起");
                groupholder.imexpand.setVisibility(View.GONE);
                groupholder.impackup.setVisibility(View.VISIBLE);

            } else {
                groupholder.control.setText("点击展开");
                groupholder.imexpand.setVisibility(View.VISIBLE);
                groupholder.impackup.setVisibility(View.GONE);
            }
            groupholder.mSpaceText.setText(group_list.get(groupPosition).lotTypeName);
            groupholder.mLotDate.setText(group_list.get(groupPosition).lotDate);
            return view;
        }

        @Override
        public boolean hasStableIds() {

            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            return true;
        }

    }

    static class GroupHolder {
        TextView mSpaceText;
        TextView mLotDate;
        LinearLayout LL;
        public TextView control;
        public ImageView imexpand, impackup;
    }

    static class ChildHolder {
        TextView mImage;
        TextView mStateText;
        TextView mPrice;
        TextView mSecondPrice;

    }

}
