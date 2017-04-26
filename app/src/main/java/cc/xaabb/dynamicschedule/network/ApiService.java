package cc.xaabb.dynamicschedule.network;

import org.json.JSONObject;

import java.util.List;

import cc.xaabb.dynamicschedule.config.Constants;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.UserModel;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by root on 16-2-28.
 */
public interface ApiService {
    String defaultParam = Constants.Key.ROLE + "=" + Constants.Value.ROLE + "&"
            + Constants.Key.HASH + "=" + Constants.Value.HASH;

    //获取课表
    @GET(Constants.Api.ECARD + "?" + defaultParam)
    Observable<Result<List<Course>>> getBalance(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);

    //用户注册
    @POST(Constants.Api.USER_REGISTER)
    Observable<Result<UserModel>> postUserRegister(@Query("username") String username, @Query("password") String password);

    //用户登录
    @POST(Constants.Api.USER_LOGIN)
    Observable<Result<UserModel>> postUserLogin(@Query("username") String username, @Query("password") String password);

    //分享课表
    @Headers({"contentType : 'application/json'"})
    @POST(Constants.Api.SCHEDULE_UPLOAD)
    Observable<Result<String>> postScheduleUpload(@Body JSONObject course);

    //获取用户
    @GET(Constants.Api.USER+"/28")
    Observable<Result<UserModel>> getUser(@Query("id") int id);

//
//    String secondhandDefaultParm = Constants.Api.SECOND_HAND_URL_PARAM + "?" + Constants.Key.S_TYPE + "=&" + Constants.Key.S_TITLE + "=&" + Constants.Key.S_LIMIT_ID + "=0";
//    //获取校园卡余额
//    @GET(Constants.Api.ECARD + "?" + defaultParam)
//    Observable<EcardModel> getBalance(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);
//
//    //获取个人信息
//    @GET(Constants.Api.STU_INFO + "?" + defaultParam)
//    Observable<StudentInfoModel> getStudentInfo(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);
//
//    //获取校园网余额
//    @GET(Constants.Api.CAMPUS_NET_BALANCE + "?" + defaultParam)
//    Observable<CampusNetwork> getCampusNetwork(@Query(Constants.Key.SID) String sid);
//
//    //获取图书馆读者信息
//    @GET(Constants.Api.LIBRARY_READER_INFO + "?" + defaultParam)
//    Observable<LibraryReaderInfoModel> getLibraryReaderInfo(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);
//
//    //获取图书馆借书列表
//    @GET(Constants.Api.LIBRARY_RENT_LIST + "?" + defaultParam)
//    Observable<LibraryRentListModel> getLibraryRentList(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);
//
//    //获取校友信息
//    @GET(Constants.Api.SEARCH_MATE + "?" + defaultParam + "&server=remote")
//    Observable<MateInfoModel> getMateInfo(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.NAME) String name, @Query(Constants.Key.CARD) String card, @Query(Constants.Key.TYPE) String type);
//
//
//    @GET(Constants.Api.HOLIDAY + "?" + defaultParam + "&" + Constants.Key.HOLIDAY_ACTION + "=" + Constants.Key.HOLIDAY_ACTION_ALL)
//    Observable<HolidayAllModel> getHolidayAll();
//
//
//
//    //课表请求。
//    @GET(Constants.Api.COURSE+"?"+defaultParam)
//    Observable<CourseListModel> getCourse(@QueryMap Map<String, String> map);
//
//    //查询当前第几周。
//    @GET(Constants.Api.CURRENT_WEEK + "?" + defaultParam + "&type=static")
//    Observable<CurrentWeekModel> getCurrentWeek();
//
//    //成绩报表
//    @GET(Constants.Api.GRADE_REPORT + "?" + defaultParam)
//    Observable<GradeReportModel> getGradeReport(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);
//
//
//    //成绩祥单
//    @GET(Constants.Api.GRADE_DETAILS + "?" + defaultParam)
//    Observable<GradeDetailsModel> getGradeDetails(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);
//
//
//    //二手街
//    @GET(secondhandDefaultParm)
//    Observable<JsonArray> getScondModelList();
//
//    //获取学期代码
//    @GET(Constants.Api.TERM_CODE + "?" + defaultParam)
//    Observable<TermCodeModel> getTermCodes(@Query(Constants.Key.SID) String sid, @Query(Constants.Key.PASSWORD) String password);


}
