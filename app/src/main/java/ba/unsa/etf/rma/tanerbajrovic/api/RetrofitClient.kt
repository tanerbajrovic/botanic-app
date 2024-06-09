package ba.unsa.etf.rma.tanerbajrovic.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Base URL
    private const val BASE_URL: String = "https://trefle.io/api/v1/"

    // Interceptor and okHttpClient for logging
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

    // Retrofit API instance
    val trefleApiService: TrefleAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TrefleAPI::class.java)
    }

}