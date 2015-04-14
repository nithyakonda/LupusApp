package com.umb.cs682.projectlupus.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.umb.cs682.projectlupus.db.dao.ActivitySenseDao;
import com.umb.cs682.projectlupus.db.dao.MedicineDao;
import com.umb.cs682.projectlupus.db.dao.MoodLevelDao;
import com.umb.cs682.projectlupus.db.dao.ProfileDao;
import com.umb.cs682.projectlupus.db.dao.ReminderDao;
import com.umb.cs682.projectlupus.db.helpers.DaoMaster;
import com.umb.cs682.projectlupus.db.helpers.DaoSession;
import com.umb.cs682.projectlupus.service.ActivitySenseService;
import com.umb.cs682.projectlupus.service.HomeService;
import com.umb.cs682.projectlupus.service.MedicineService;
import com.umb.cs682.projectlupus.service.MoodLevelService;
import com.umb.cs682.projectlupus.service.ReminderService;

/**
 * Created by Nithya Kiran on 3/16/2015.
 */
public class AppConfig {
    //DB
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    //Service Objects
    private static ActivitySenseService activitySenseService;
    private static HomeService homeService;
    private static MedicineService medicineService;
    private static MoodLevelService moodLevelService;
    private static ReminderService reminderService;

    private static boolean isBound = false;

    //DAO Objects
    private static ActivitySenseDao activitySenseDao;
    private static MedicineDao medicineDao;
    private static MoodLevelDao moodLevelDao;
    private static ProfileDao profileDao;
    private static ReminderDao reminderDao;

    //Application Context
    private static Context appContext;

 /*   static{
        try{
            Log.i("Config", "Configuring Services");
            configureServices();
        }catch (Exception e){
            Log.e("Config",e.getMessage());
        }
    }*/

    public static void setAppContext(Context context){
        appContext = context;
    }

    public static void configureServices() throws Exception{
        try {
            //Initialize DAOs
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            activitySenseDao = daoSession.getActivitySenseDao();
            medicineDao = daoSession.getMedicineDao();
            moodLevelDao = daoSession.getMoodLevelDao();
            profileDao = daoSession.getProfileDao();
            reminderDao = daoSession.getReminderDao();

            //Initialize services
            activitySenseService = new ActivitySenseService(activitySenseDao, appContext);
            if(activitySenseService != null) {
                Log.d("Config", "Initialized Activity Sensing Service");
            }
        }catch (Exception e){
            Log.e("Config", e.getMessage());
        }
    }

    public static Context getAppContext(){
        return appContext;
    }
    public static ActivitySenseService getActivitySenseService(){
        return activitySenseService;
    }


}
