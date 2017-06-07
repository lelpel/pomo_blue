package ua.lelpel.pomobluelw;

import android.content.Context;
import android.content.res.Resources;

import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

/**
 * Created by bruce on 05.06.2017.
 */

public class PomoBlueApplication extends SugarApp {
    private Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(getApplicationContext());

        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
    }

    public Resources getAppResources() {
        return ctx.getResources();
    }
}
