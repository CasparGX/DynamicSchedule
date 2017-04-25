package cc.xaabb.dynamicschedule.config;

/**
 * Created by root on 16-2-28.
 */
public class Constants {

    public static final String[] WEEK_STRING_ARRAY = new String[]{"一","二","三","四","五","六","日"};
    public final class Api {

        public static final String URL = "http://10.0.3.2:8080";
//        public static final String URL = "http://api.sky31.com";
        public static final String USER = "/user";
        public static final String USER_REGISTER = "/user/register";
        public static final String USER_LOGIN = "/user/login";
        public static final String SCHEDULE_UPLOAD = "/schedule/upload";


        public static final String SECOND_HAND = "http://buy.sky31.com";
        public static final String SECOND_HAND_API = "http://buy.sky31.com/fleaapi";
        public static final String SECOND_HAND_URL_PARAM = "fleaapi/index.php/Admin/Goods/query";
        public static final String STU_INFO = "edu_student_info.php";
        public static final String COURSE = "edu_course.php";
        public static final String GRADE_REPORT = "edu_grade_report.php";
        public static final String GRADE_DETAILS = "edu_grade_details.php";
        public static final String TERM_CODE = "edu_term_code.php";
        public static final String RANK = "edu_rank.php";

        public static final String CAMPUS_NET_BALANCE = "campus_net_balance.php";

        public static final String ECARD = "ecard_balance.php";

        public static final String SEARCH_MATE = "search_mate.php";

        public static final String LIBRARY_READER_INFO = "library_reader_info.php";
        public static final String LIBRARY_RENT_LIST = "library_rent_list.php";
        public static final String LIBRARY_RENEW = "library_renew.php";

        public static final String HOLIDAY = "holiday.php";


        public static final String CMS_LIST = "cms_list.php";   //文章列表

        public static final String SWZL_ACTION = "lost_found.php";
        public static final String CURRENT_WEEK = "current_week.php";
    }

    public final class Key {
        //登录验证
        public static final String ROLE = "role";
        public static final String HASH = "hash";
        public static final String USERNAME = "username";
        public static final String SID = "sid";
        public static final String PASSWORD = "password";
        public static final String LIBRARY_PASSWORD = "library_password";
        public static final String ECARD_PASSWORD = "ecard_password";

        //个人信息
        public static final String NAME = "name";
        public static final String SEX = "sex";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String LOCATION = "location";
        public static final String COLLEGE = "college";
        public static final String MAJOR = "major";
        public static final String CLASSX = "classx";

        //校友信息
        public static final String CARD = "card";
        public static final String TYPE = "type";
        public static final String SERVER = "server";
        public static final String SERVER_LOCAL = "local";
        public static final String SERVER_REMOTE = "remote";

        //校园卡信息
        public static final String ECARD_ID = "ecard_id";
        public static final String BALANCE = "balance";
        public static final String UNCLAIMED = "unclaimed";

        //图书馆信息
        public static final String LIBRARY_VALID_DATE_START = "library_valid_date_start";
        public static final String LIBRARY_VALID_DATE_END = "library_valid_date_end";
        public static final String LIBRARY_DEBT = "library_debt";
        public static final String LIBRARY_RENT_LIST = "library_rent_list";
        public static final String LIBRARY_RENT_NUM = "library_rent_num";

        //校园网信息
        public static final String CAMPUS_NETWORK_STATUS = "campus_network_status";
        public static final String CAMPUS_NETWORK_BALANCE = "campus_network_balance";
        public static final String CAMPUS_NETWORK_PACKAGEX = "campus_network_packageX";
        public static final String CAMPUS_NETWORK_NEXT_STATEMENT_DATE = "campus_network_next_statement_date";

        //节假日
        public static final String HOLIDAY_ACTION= "action";
        public static final String HOLIDAY_ACTION_NEXT = "next";
        public static final String HOLIDAY_ACTION_ALL = "all";
        public static final String HOLIDAY_CACHE_NEXT = "holidayCacheNext";
        public static final String HOLIDAY_CACHE_ALL = "holidayCacheAll";

        //课表
        public static final String COURSE_LIST = "course_list";
        public static final String CURRENT_WEEK = "currentWeek";

        //焦点图缓存
        public static final String HEADER_IMAGE = "header_image_";

        //二手街
        public static final String S_GOODS_LIST = "s_goods_list";
        public static final String S_TYPE = "type";
        public static final String S_PICNAME = "picname";
        public static final String S_PICSRC = "picsrc";
        public static final String S_TITLE = "title";
        public static final String S_LIMIT_ID = "limitID";

    }

    public final class Value {
        public static final String ROLE = "gonggong-android";
        public static final String HASH = "0c95c64db9e48afbdff87a3869386124";


        public static final int RESULT_LOGIN = 1;
        public static final int RESULT_LIBRARY = 2;

        //
        //失物招领
        public static final String SWZL_ASK_METHOD_JSON = "json";
        public static final String SWZL_SUBMIT_LOST = "submit_lost";
        public static final String SWZL_SUBMIT_FOUND = "submit_found";
        public static final String SWZL_SEARCH_LOST = "search_lost";
        public static final String SWZL_SEARCH_FOUND = "search_found";
        public static final String SWZL_SEARCH = "search";

        public static final String SWZL_SEARCH_TAG_LOST = "lost_info";
        public static final String SWZL_SEARCH_TAG_FOUND = "found_info";
    }
}
