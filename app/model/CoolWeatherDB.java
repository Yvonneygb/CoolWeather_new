package com.coolweather.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.db.CoolWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LX-PC on 2017/5/23.
 */

//把一些常见的数据库操作封装起来，方便后面使用
public class CoolWeatherDB {

    //数据库名
    public static final String DB_NAME = "cool_weather";

    //数据库版本
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    //将构造方法私有化
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper =  new CoolWeatherOpenHelper(context , DB_NAME , null , VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取CoolWeatherDB的实例
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB == null){
            //单例模式
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    //将Province例存储到数据库
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name" , province.getProvinceName());
            values.put("province_code" , province.getProvinceCode());
            db.insert("Province" , null , values);
        }
    }

    //从数据库读取全国省份信息
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<>();
        Cursor cursor = db.query("Province" , null , null , null , null , null ,null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }

    //将City例存储到数据库
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name" , city.getCityName());
            values.put("city_code" , city.getCityCode());
            values.put("province_id" , city.getProvinceId());
            db.insert("City" , null , values);
        }
    }

    //从数据库读取全国省份信息
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<>();
        //问题是 no such table: City
        //no such table: City (code 1): , while compiling: SELECT * FROM City WHERE province_id = ?应该这样
        // Cursor cursor = db.query("City" , null , "province_id = ?" , new String[]{String.valueOf(provinceId)} , null , null , null);这样不对



        //正确的函数   query应该这样写
//        public Cursor query(String table, String[] columns, String selection,String[] selectionArgs, String groupBy, String having,String orderBy) {
//
//             return query(false, table, columns, selection, selectionArgs, groupBy,having, orderBy, null /* limit */);
//        }

          //大概写了一下
         Cursor cursor = db.query("City" , new String[]{"city_name","city_code"} , "province_id=?" ,new  String[]{String.valueOf(provinceId)}, null , null , null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }

    //将City例存储到数据库
    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name" , county.getCountyName());
            values.put("county_code" , county.getCountyCode());
            values.put("city_id" , county.getCityId());
            db.insert("County" , null , values);
        }
    }

    //从数据库读取全国省份信息
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<>();
        //query 语句不对
        Cursor cursor = db.query("County" , null , "city_id = ?" , new String[]{String.valueOf(cityId)} , null , null , null);


        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }


        return list;
    }
}
