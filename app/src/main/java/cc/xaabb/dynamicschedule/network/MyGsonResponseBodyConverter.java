package cc.xaabb.dynamicschedule.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.Result2;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by caspar on 16-10-25.
 */

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    public MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            Result re = mGson.fromJson(response, Result.class);
            //关注的重点，自定义响应码中非0的情况，一律抛出ApiException异常。
            //这样，我们就成功的将该异常交给onError()去处理了。
            if (!re.isOk()) {
                value.close();
                throw new ApiException(re.getCode(), re.getMsg());
            }
        } catch (Exception e) {
            try {
                Result2 re = mGson.fromJson(response, Result2.class);
                //关注的重点，自定义响应码中非0的情况，一律抛出ApiException异常。
                //这样，我们就成功的将该异常交给onError()去处理了。
                if (!re.isOk()) {
                    value.close();
                    throw new ApiException(Integer.parseInt(re.getCode()), re.getMsg());
                }
            } catch (Exception e2)  {

                MediaType mediaType = value.contentType();
                Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
                ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
                InputStreamReader reader = new InputStreamReader(bis,charset);
                JsonReader jsonReader = mGson.newJsonReader(reader);
                try {
                    return adapter.read(jsonReader);
                } finally {
                    value.close();
                }
            }
        }

        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(bis,charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}