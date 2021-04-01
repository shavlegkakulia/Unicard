package ge.unicard.pos;

import android.app.Application;
import android.content.Context;

import com.orm.SugarContext;

import ge.unicard.pos.di.component.ApplicationComponent;
import ge.unicard.pos.di.component.DaggerApplicationComponent;
import ge.unicard.pos.di.module.ApplicationModule;
import io.realm.Realm;

/**
 * Created by Akaki on 10/23/18.
 */

public class App extends Application{


    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((App) context.getApplicationContext()).mApplicationComponent;
    }

    private ApplicationComponent mApplicationComponent;
    private static final String DATABASE_NAME = "print_again";


    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        //Realm.init(getApplicationContext());


        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
