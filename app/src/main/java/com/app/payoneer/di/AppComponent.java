package com.app.payoneer.di;

import com.app.payoneer.ui.payments.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
    void doInjection(MainActivity mainActivity);
}
