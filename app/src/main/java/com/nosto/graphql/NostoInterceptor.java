package com.nosto.graphql;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NostoInterceptor implements Interceptor {

    private static final String TOKEN = "5VsztSfLhrGDXOsbs1ZqMHWgBOwICfmhIWCocVRICaSDeUMF9kQ3mz4oSxl76jUE";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .method(original.method(), original.body())
                .addHeader("Authorization", "Basic " + getAuth(TOKEN));
        return chain.proceed(builder.build());
    }

    private static String getAuth(String password) {
        return Base64.encodeToString((":" + password).getBytes(), Base64.NO_WRAP);
    }
}
