package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/9/27.
 */

public class PayMentBean implements Serializable{

    /**
     * datas : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017091508737302&biz_content=%7B%22out_trade_no%22%3A%222017092713471960748%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%85%89%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.94.140.92%3A8080%2FJDLot%2Fali%2Foauth%2Fchecksign&sign=dSJOISud5EJZotquAPO32p8HG5ZMKvljYff7jc8wGRGJHJx3KwmCvMhwZOkE8ANwe%2FkMOoJfvs3oT%2Fkaz8SkTltSaTkEizNTjj3%2FUQqX1PDwA%2FS6ptNJvnR6ypKGweHVBeXHlX0Y%2FfTOyaNd4H3kqCdGpZ%2BQDznHSuJRLuKDF%2BdceNjKzh6WuMISVBPHL2k%2BdO%2FsNX13amF3uH7wgABnDt2KlSvHNBibd2wLpg6lfFkLVcooB2CJAJtUz4gxQKmuPRnrOslbkbHmkJvT8pFQ6trV%2FtteMu13avl39YfpHzCnO4wJTfgnS9szZW74BIsJWU11pMMn%2BttYRwcIekPBxQ%3D%3D&sign_type=RSA2&timestamp=2017-09-27+13%3A47%3A19&version=1.0
     * msg : success
     * msgContext : success
     */

    public String datas;
    public String msg;
    public String msgContext;
    public String out_trade_no;
}
