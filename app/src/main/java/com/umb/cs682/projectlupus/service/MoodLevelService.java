package com.umb.cs682.projectlupus.service;

import android.content.Context;
import android.util.Log;

import com.umb.cs682.projectlupus.db.dao.MoodLevelDao;
import com.umb.cs682.projectlupus.domain.MoodLevelBO;
import com.umb.cs682.projectlupus.util.DateTimeUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import de.greenrobot.dao.query.CountQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MoodLevelService {
    public static final String TAG = "projectlupus.service";
    private static final String SQL_DISTINCT_DATES= "SELECT DISTINCT "+MoodLevelDao.Properties.Date.columnName+" FROM "+MoodLevelDao.TABLENAME;

    private Context context;
    private MoodLevelDao moodLevelDao;

    private MoodLevelBO bo;
    private QueryBuilder queryBuilder;

    public MoodLevelService(Context context, MoodLevelDao moodLevelDao){
        this.context = context;
        this.moodLevelDao = moodLevelDao;
        queryBuilder = moodLevelDao.queryBuilder();
    }

    public void loadDummyData(){
        if(getCount() == 0) {
            Calendar cal = Calendar.getInstance();
            Random random = new Random();
            for (int i = 1; i <= 31; i++) {
                cal.set(2015, 3, i, 7, 30);
                bo = new MoodLevelBO(null, 1,DateTimeUtil.toDate(new Date(cal.getTimeInMillis())), random.nextInt(6)); //DateTimeUtil.toDate(new Date(cal.getTimeInMillis()))
                moodLevelDao.insert(bo);
                cal.set(2015, 3, i, 18, 30);
                bo = new MoodLevelBO(null, 1,DateTimeUtil.toDate(new Date(cal.getTimeInMillis())), random.nextInt(6)); //DateTimeUtil.toDate(new Date(cal.getTimeInMillis()))
                moodLevelDao.insert(bo);
            }
            for (int i = 1; i <= 11; i++) {
                cal.set(2015, 4, i, 7, 30);
                bo = new MoodLevelBO(null, 1,DateTimeUtil.toDate(new Date(cal.getTimeInMillis())), random.nextInt(6)); //DateTimeUtil.toDate(new Date(cal.getTimeInMillis()))
                moodLevelDao.insert(bo);
                cal.set(2015, 4, i, 18, 30);
                bo = new MoodLevelBO(null, 1,DateTimeUtil.toDate(new Date(cal.getTimeInMillis())), random.nextInt(6)); //DateTimeUtil.toDate(new Date(cal.getTimeInMillis()))
                moodLevelDao.insert(bo);
            }
        }
    }

    public void addMoodLevel(int reminderID, int moodLevel){
        bo = new MoodLevelBO(null, reminderID, DateTimeUtil.toDateTime(new Date()), moodLevel);
        moodLevelDao.insert(bo);
        Log.i(TAG, "Added mood level for reminder ID: "+bo.getReminderId()+" mood level :"+bo.getMoodLevel()+" date :"+DateTimeUtil.toDateTime(bo.getDate()));
    }

    public List<MoodLevelBO> getAllData(){
        Query query = queryBuilder.build();
        return query.list();
    }

    public TreeMap<Date, Float> getTimeVsMoodLevel(){
        TreeMap<Date, Float> timeVsMoodLevelMap = new TreeMap<>();
        Float avg;

        List<MoodLevelBO> allData = getAllData();
        for (int i = 0;i <= allData.size()-1;i ++) {
            Date tempForComparison = DateTimeUtil.toDate(allData.get(i).getDate());
            if(allData.size() > 1 && i+1 < allData.size() && DateTimeUtil.toDate(allData.get(i).getDate()).equals(DateTimeUtil.toDate(allData.get(i + 1).getDate()))) {
                int j = i + 1;
                avg = (float) allData.get(i).getMoodLevel();
                do {
                    avg += allData.get(j).getMoodLevel();
                    j ++;
                } while (j < allData.size() && tempForComparison.equals(DateTimeUtil.toDate(allData.get(j).getDate())));
                avg /= (j - i);
                i = j - 1;
            }
            else {
                avg = (float) allData.get(i).getMoodLevel();
            }
            if(i == allData.size() && !timeVsMoodLevelMap.containsKey(tempForComparison)){
                avg = (float) allData.get(i).getMoodLevel();
            }
            timeVsMoodLevelMap.put(allData.get(i).getDate(),avg);
        }

        return timeVsMoodLevelMap;
    }


    private long getCount(){
        CountQuery query = moodLevelDao.queryBuilder().buildCount();
        return query.count();
    }
}
