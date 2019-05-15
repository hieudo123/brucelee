package com.example.lifestyle.hope.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson


class RetrofitClient private constructor(){

    companion object {
        var gson = GsonBuilder()
                .setLenient()
                .create()
        private var retrofit: Retrofit? = null
        fun getClient(baseUrl: String): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
    }

}