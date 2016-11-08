package com.menusystem.bean;

/**
 * Created by ${Kikis} on 2016-08-01.
 */

public class Verify {

    public static final String VERIFY_KEY="G*Pf8UrW$rdP4oeA0ObzKoQhN3Gn1fS7";
    //public static final String HTTP="http://120.76.144.49:8080/MenuSystem";
    public static final String MHTTP="http://192.168.1.88:8080/MenuSystem";
    public static final String FOOD_TYPE_HHTP="/MenuType";
    public static final String FOOD_MENU_HHTP="/Menu";
    public static final String FOOD_STATE_HHTP="/FoodState";
    public static final String FOOD_PLACEORDER_HTTP="/PlaceOrder";
    public static final String ORDER_FOOD_STATUS_HTTP="/OrderStatus";
    public static final String QUERY_TABLE_NUMBER="/QueryTableNumber";
    public static final String ORDERQUERY="/FalseOrderQuery";
    public static final String PAYTHEBILL="/PayTheBill";
    public static final String MESSAGE="/Message";

    public static final String PAY_QUERY_THREAD="/QueryPayTheBill";

    //版本号查询;
    public static final String VERSION_QUERY="/VersionQuery";
    //Apk下载;
    public static final String APK_DOWNLOAD="http://192.168.1.88:8080/version/";
    public static final String APK = ".apk";

    //二维码付款URL;
    public static final String WECHAT_PAY = "http://192.168.1.88:8080/QrCode/Wechat_pay.png";
    public static final String Ali_PAY = "http://192.168.1.88:8080/QrCode/Ali_pay.png";

    public static final String MY_LOGCAT_TAG="Cristiano == ";
    public static final int RESULET_CODE = 1;
    public static final int PAY_THE_ALL_AC_RESULET_CODE = 2;
    public static final String TABLE_NUMBER ="Table_Number";
    /**
     * 菜品信息状态码: 1.已下单,0.未下单（无效菜品）,9.已上菜.
     */
    public static final int PLACE_AN_ORDER = 1;

    public static final int NO_PLACE_AN_ORDER = 0;

    public static final int OFF_THE_STOCKS = 9;


    /**
     * 菜单消息状态码:  0.结账，1.加水，2.催单;
     */
    public static final int SETTLE = 0;
    public static final int ADD_WATER = 1;
    public static final int REMINDER = 2;


    public static final int  MAXSIZE = 23040000;
    /**
     * MainActivity中的flag;
     */
    //成功下单标识符
    public static boolean ORDERSUCCEED = false;
    //查询线程标识符
    public static boolean THREADCONTROL = false;
    //ActivityResult标识符
    public static Boolean ORDERFLAG = false;
    //买单标识
    public static boolean PAY_THE_BILL = false;

}
