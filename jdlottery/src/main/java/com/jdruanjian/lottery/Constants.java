/**
 * Author: S.J.H
 * 
 * Date: 2016/7/1
 */
package com.jdruanjian.lottery;

/**
 * 常数
 */
public class Constants {

	//IP地址
	public static final String API_SERVER = "https://jindi163.com:8443/JDLot";
	/**
	 * 注册
	 */
	public final static String REGISTER = API_SERVER+"/user/register";

	/*
	验证码
	* */
	public final static String GER_CODE = API_SERVER+"/user/send";

	/**
	 * 登录
	 */
	public final static String LOGIN =API_SERVER+ "/user/login";


	/**
	 * 验证手机是否可用
	 */
	public final static String CHECK =API_SERVER+ "/user/check";

	//重置密码
	public final static String RESET_PWD= API_SERVER+"/user/reset";


	//头像上传
	public static final String IMG_URL = API_SERVER+"/user/avatar/add";

	//头像下载显示
	public static final String IMG_URL_LOAD =API_SERVER+ "/user/avatar/show";

	//首页彩票列表
	public final static String HOME_PAGE_URL = API_SERVER+"/lottype/pagelist";

	//所有彩票最新一期
	public final static String NEW_KSLOT = API_SERVER+"/type/new/lot";

	//新增绑定银行账号
	public final static String BIND_BANKCARD= API_SERVER+"/user/bank/addbank";

	//查询账户最新绑定银行账号
	public final static String QUERY_BINDCARD= API_SERVER+"/user/bank/show";

	//新增绑定支付宝账号
	public final static String BIND_ALIPLAY= API_SERVER+"/user/ali/add";

	//查询最新绑定支付宝账号
	public final static String QUERY_ALIPLAY= API_SERVER+"/user/ali/show";

	//查询是否绑定支付宝或者银行卡
	public final static String QUERY_BANK_AliPLAY= API_SERVER+"/user/ali/exist";

	//用户关注彩种新增
	public final static String USER_NEWADDLOTT= API_SERVER+"/user/lotlink/add";

	//用户删除关注彩种
	public final static String USER_DELETELOTT= API_SERVER+"/user/lotlink/del";

	//用户已添加彩种列表
	public final static String USER_LINKLOT= API_SERVER+"/user/lotlink/list";

	//用户可添加彩种列表
	public final static String USER_ISNOLINKLOT= API_SERVER+"/user/lotlink/alllist";

    //所有已开彩票列表
    public final static String ALREADY_LOTT= API_SERVER+"/lot/type/pagelist";

	//签到积分
	public final static String SIGN_COUNT= API_SERVER+"/user/integral/add";

	//积分列表
	public final static String INTEGER_LIST= API_SERVER+"/user/integral/list";


	//积分列表
	public final static String MY_TEAMLIST= API_SERVER+"/user/team/pagelist";


	//总积分总金额
	public final static String TOTAL_MOIN= API_SERVER+"/user/order/money";


	//收费列表
	public final static String CHARGE_LIST= API_SERVER+"/price/plan/list";

	//支付宝支付信息
	public final static String ORDER_PAY= API_SERVER+"/ali/oauth/sign";

	//微信支付信息
	public final static String ORDER_WECHAT= API_SERVER+"/wechat/oauth/sign";


	//订单信息
	public final static String ORDER_INFO= API_SERVER+"/user/order/list";


    //积分支付
	public final static String INT_PAY= API_SERVER+"/user/order/update";

	//指定某期计划的包天包周包月价格
	public final static String ASSIGN_PLAN= API_SERVER+"/price/plan/lot";

	//指定彩票最新一期
//	public final static String NEW_NUMBER=  "http://jindi163.com:8800/JDLot/type/new/number/";

	//指定彩票最新一期
	public final static String NEW_NUMBER=  API_SERVER+"/type/new/number";

	//意见反馈 微信，QQ交流群信息
	public final static String WEICHA_MESS= API_SERVER+"/dict/group/list";

	//意见反馈 微信，QQ交流群信息
	public final static String FEED_BACK= API_SERVER+"/user/advise/add";

	//计算器
	public final static String CALCULATOR= API_SERVER+"/lot/count/list";


	//提现
	public final static String WITHDRAW= API_SERVER+"/user/commision/add";

	//提现记录
	public final static String WITHDRAW_RECORD= API_SERVER+"/user/commision/list";

	//取消订单
	public final static String CANCEL_ORDER= API_SERVER+"/user/order/cancel";

	//版本更新
	public final static String UPDATE_VERSION= API_SERVER+"/user/number/name";

	//轮播banner
	public final static String BANNER_IMG= API_SERVER+"/index/advertising/img";
	//appid 微信分配的公众账号ID
	public static final String APP_ID = "wxcc915e0f91f89504";

	//商户号 微信分配的公众账号ID
	public static final String MCH_ID = "1490202172";

	//  API密钥，在商户平台设置
//	public static final  String API_KEY= "ganzhoualpha112114115WXZHOUALPHA";


	//判断
	/**
	 * 日志
	 */
	public static final String TAG = "hhdebug";

	/**
	 * 开发者模式
	 */
	public static final boolean DEVELOPER_MODE = true;
	/**
	 * app cache key
	 */
	public static final String CURRENT_VERSION = "";// 当前版本

	/**
	 * action
	 */
	public static final String NOTIFICATION_REMAIN_CHANGE = "";
	/**
	 * image cache
	 */
	public static final String IMAGE_CACHE_SDCARD_PATH = "";
	/**
	 * error log
	 */
	public static final String LOG_CACHE_SDCARD_PATH = "";
	public static final String CURRENT_USER = "";// 当前登录用户
	public static final String CURRENT_USER_INFO = "";// 当前登录用户详细信息
	public static final String CURRENT_SHOPS = "";
	public static final String CURRENT_USER_LIST = "";// 用户列表
	public static final String CURRENT_CLASSLIST = "";

}
