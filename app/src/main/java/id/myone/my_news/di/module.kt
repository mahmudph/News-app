/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.di

import androidx.room.Room
import androidx.work.WorkManager
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import id.myone.my_news.BuildConfig
import id.myone.my_news.common.Constraint
import id.myone.my_news.data.AppRepository
import id.myone.my_news.data.AppRepositoryContract
import id.myone.my_news.data.local.PagingExampleDatabase
import id.myone.my_news.data.remote.PagingServiceApi
import id.myone.my_news.ui.screens.bookmark.BookmarkViewModel
import id.myone.my_news.ui.screens.detail.NewsDetailViewModel
import id.myone.my_news.ui.screens.forgot_password.ForgotPasswordViewModel
import id.myone.my_news.ui.screens.home.HomeViewModel
import id.myone.my_news.ui.screens.login.LoginViewModel
import id.myone.my_news.ui.screens.news.NewsViewModel
import id.myone.my_news.ui.screens.onboarding.OnBoardingViewModel
import id.myone.my_news.ui.screens.profile.ProfileViewModel
import id.myone.my_news.ui.screens.profile.update.UpdateProfileViewModel
import id.myone.my_news.ui.screens.register.RegisterViewModel
import id.myone.my_news.ui.screens.search.SearchNewsViewModel
import id.myone.my_news.ui.screens.settings.SettingsViewModel
import id.myone.my_news.ui.utils.SnackBarDelegate
import id.myone.my_news.utils.AppDataStore
import id.myone.my_news.utils.analytic.Analytic
import id.myone.my_news.utils.auth.FirebaseEmailPasswordOAuthContract
import id.myone.my_news.utils.auth.FirebaseEmailPasswordOAuthImpl
import id.myone.my_news.utils.auth.FirebaseGoogleAuthContract
import id.myone.my_news.utils.auth.FirebaseGoogleOAuthImpl
import id.myone.my_news.utils.config.RemoteConfigApp
import id.myone.my_news.utils.notification.NotificationHandler
import id.myone.my_news.utils.works.SynchronizeNewsWorker
import id.myone.my_news.utils.works.WorkManagerHandler
import id.myone.my_news.viewmodels.SplashViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
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
                    .addQueryParameter("apiKey",BuildConfig.API_KEY)
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

    single<AppRepositoryContract> { AppRepository(get(), get(), get()) }
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

val appUtilityModule = module {
    single { SnackBarDelegate(null, null) }
    single { AppDataStore(androidContext(), get()) }
    single { FirebaseAuth.getInstance() }

    single { Firebase.remoteConfig }
    single { Firebase.analytics }
    single { RemoteConfigApp(get()) }
    single { Analytic(get()) }

    single<FirebaseGoogleAuthContract> { FirebaseGoogleOAuthImpl(androidContext(), get()) }
    single<FirebaseEmailPasswordOAuthContract> { FirebaseEmailPasswordOAuthImpl(get()) }

    single { NotificationHandler(get(), get()) }
}

val workerModule = module {
    single { WorkManager.getInstance(androidContext()) }
    single { WorkManagerHandler(get()) }
    worker { SynchronizeNewsWorker(get(), get(), get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get(), get()) }
    viewModel { SearchNewsViewModel(get()) }

    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get(), get()) }
    viewModel { BookmarkViewModel(get()) }

    viewModel { ProfileViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }

    viewModel { UpdateProfileViewModel(get()) }

    viewModel { HomeViewModel(get(), get()) }

    viewModel { SplashViewModel(get(), get()) }

    viewModel { OnBoardingViewModel(get()) }

    viewModel { NewsDetailViewModel(get()) }
}