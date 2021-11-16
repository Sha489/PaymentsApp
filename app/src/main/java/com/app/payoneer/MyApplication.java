package com.app.payoneer;

import android.app.AppComponentFactory;
import android.app.Application;
import android.content.Context;

import com.app.payoneer.di.AppComponent;
import com.app.payoneer.di.AppModule;
import com.app.payoneer.di.DaggerAppComponent;
import com.app.payoneer.di.UtilsModule;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MyApplication extends Application {

    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Montserrat-Medium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
