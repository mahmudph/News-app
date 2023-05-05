/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.di

import androidx.room.Room
import id.myone.paging_3_example.BuildConfig
import id.myone.paging_3_example.common.Constraint
import id.myone.paging_3_example.data.AppRepository
import id.myone.paging_3_example.data.AppRepositoryContract
import id.myone.paging_3_example.data.local.PagingExampleDatabase
import id.myone.paging_3_example.data.remote.PagingServiceApi
import id.myone.paging_3_example.ui.screens.news.NewsViewModel
import id.myone.paging_3_example.ui.screens.search.SearchNewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appNetworkModule = module {
    single {
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.API_KEY)
                    .build()

                chain.proceed(request.newBuilder().url(url).build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constraint.HOST_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PagingServiceApi::class.java)
    }

    single<AppRepositoryContract> { AppRepository(get(), get()) }
}


val appDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PagingExampleDatabase::class.java,
            Constraint.PAGING_EXAMPLE_DB_NAME
        ).build()
    }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { SearchNewsViewModel(get()) }
}