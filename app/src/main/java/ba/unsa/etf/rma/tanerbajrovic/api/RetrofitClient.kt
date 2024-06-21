package ba.unsa.etf.rma.tanerbajrovic.api

import ba.unsa.etf.rma.tanerbajrovic.utils.Constants.Companion.TREFLE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Interceptor and okHttpClient for logging
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = getUnsafeOkHttpClient(logging)

    // Retrofit API instance
    val trefleApiService: TrefleAPI by lazy {
        Retrofit.Builder()
            .baseUrl(TREFLE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TrefleAPI::class.java)
    }

}