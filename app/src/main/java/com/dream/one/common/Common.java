package com.dream.one.common;

import com.dream.one.model.Classify;

/**
 * Created by CNKI-0000 on 2015/11/27.
 */
public class Common {
    public static final String APP_ID = "12973";
    public static final String SECRET = "c85e466b303746beb6c5ba74ebd7cfa2";
    //http://route.showapi.com/582-1?
    // &showapi_appid=12973
    // &showapi_timestamp=20151127102732
    // &showapi_sign=c85e466b303746beb6c5ba74ebd7cfa2
    public static final String CLASSIFY_URL = "http://route.showapi.com/582-1?";
    public static final String ID_SEARCH_URL = "http://route.showapi.com/582-5?";
    //https://route.showapi.com/582-2?
    // key=关键词
    // &page=
    // &showapi_appid=12973
    // &showapi_timestamp=20151127102732
    // &typeId=0 分类码
    // &showapi_sign=c85e466b303746beb6c5ba74ebd7cfa2
    public static final String ARTICLE_CLASSIFY_URL = "http://route.showapi.com/582-2?";

    public static final Classify[] classifies = {
            new Classify("0", "热点"),
            new Classify("1", "推荐"),
            new Classify("19", "体育迷"),
            new Classify("2", "段子手"),
            new Classify("3", "养生堂"),
            new Classify("4", "私房话"),
            new Classify("5", "八卦精"),
            new Classify("6", "爱生活"),
            new Classify("7", "财经迷"),
            new Classify("8", "汽车迷"),
            new Classify("9", "科技咖"),
            new Classify("10", "潮人帮"),
            new Classify("11", "辣妈帮"),
            new Classify("13", "旅行家"),
            new Classify("15", "美食家"),
            new Classify("46", "古今通")
    };
}
