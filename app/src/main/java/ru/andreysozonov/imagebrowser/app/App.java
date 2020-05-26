package ru.andreysozonov.imagebrowser.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = generateAppComponent();


        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }


    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static void setComponent(AppComponent component) {
        appComponent = component;
    }

    public AppComponent generateAppComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
