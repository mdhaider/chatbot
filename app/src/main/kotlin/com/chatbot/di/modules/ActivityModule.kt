package com.chatbot.di.modules

import ai.api.AIDataService
import ai.api.android.AIService
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.chatbot.BuildConfig
import com.chatbot.data.io.SchedulerProvider
import com.chatbot.data.io.SchedulerProviderImpl
import com.chatbot.di.qualifier.ActivityCtxQualifier
import com.chatbot.di.qualifier.DatabaseRefQualifier
import com.chatbot.di.scopes.ActivityScope
import com.chatbot.ui.auth.login.LoginPresenter
import com.chatbot.ui.auth.login.LoginPresenterImpl
import com.chatbot.ui.auth.login.LoginView
import com.chatbot.ui.auth.recovery.RecoverPassPresenter
import com.chatbot.ui.auth.recovery.RecoverPassPresenterImpl
import com.chatbot.ui.auth.recovery.RecoverPassView
import com.chatbot.ui.auth.register.RegisterPresenter
import com.chatbot.ui.auth.register.RegisterPresenterImpl
import com.chatbot.ui.auth.register.RegisterView
import com.chatbot.ui.main.MainAdapter
import com.chatbot.ui.main.MainPresenter
import com.chatbot.ui.main.MainPresenterImpl
import com.chatbot.ui.main.MainView
import com.chatbot.ui.splash.SplashPresenter
import com.chatbot.ui.splash.SplashPresenterImpl
import com.chatbot.ui.splash.SplashView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

/**
 * @author lusinabrian on 20/09/17.
 * @Notes module for activities
 */
@Module
class ActivityModule(val mActivity: AppCompatActivity) {

    @Provides
    @ActivityCtxQualifier
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    fun provideSchedulers(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @ActivityScope
    fun provideMainPresenter(mainPresenter: MainPresenterImpl<MainView>): MainPresenter<MainView> {
        return mainPresenter
    }

    @Provides
    @ActivityScope
    fun provideLoginPresenter(loginPresenter: LoginPresenterImpl<LoginView>): LoginPresenter<LoginView> {
        return loginPresenter
    }

    @Provides
    @ActivityScope
    fun provideRegisterPresenter(registerPresenter: RegisterPresenterImpl<RegisterView>): RegisterPresenter<RegisterView> {
        return registerPresenter
    }

    @Provides
    @ActivityScope
    fun provideSplashPresenter(splashPresenter: SplashPresenterImpl<SplashView>) : SplashPresenter<SplashView> = splashPresenter

    @Provides
    @ActivityScope
    fun provideRecoverPresenter(recoverPassPresenter: RecoverPassPresenterImpl<RecoverPassView>): RecoverPassPresenter<RecoverPassView> {
        return recoverPassPresenter
    }

    @Provides
    @DatabaseRefQualifier
    fun provideFirebaseDatabase(): DatabaseReference {
        val dbRef = FirebaseDatabase.getInstance().reference
        dbRef.keepSynced(true)
        return dbRef
    }

    @Provides
    fun provideMainAdapter(@DatabaseRefQualifier databaseRef: DatabaseReference): MainAdapter {
        return MainAdapter(databaseRef)
    }

    @Provides
    fun provideAiService(@Named("AiConfig") aiConfig: ai.api.android.AIConfiguration): AIService {
        return AIService.getService(mActivity, aiConfig)
    }

    @Provides
    @Named("AiConfig")
    fun provideAiConfiguration(): ai.api.android.AIConfiguration {
        return ai.api.android.AIConfiguration(BuildConfig.API_AI_CLIENT_ACCESS_TOKEN,
                ai.api.AIConfiguration.SupportedLanguages.English,
                ai.api.android.AIConfiguration.RecognitionEngine.System)
    }
}