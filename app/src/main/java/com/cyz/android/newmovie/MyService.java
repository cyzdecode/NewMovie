package com.cyz.android.newmovie;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 陈志 on 2018/4/28.
 */

public class MyService extends Service {
    public static final String TAG = "MyService";
    private MyBinder myBinder=new MyBinder();
    private Messenger messenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        messenger= (Messenger) intent.getExtras().get("str3");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
    class MyBinder extends Binder{
        public Void DataDownload(){
           new GetJsonData2().execute();
            return null;
        }

    }
    class GetJsonData2 extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json="";
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://api.meituan.com/mmdb/movie/v2/list/rt/order/coming.json?ci=1&limit=12&token=&__vhos" +
                        "t=api.maoyan.com&utm_campaign=AmovieBmovieCD-1&movieBundleVersion=6801&utm_source=xiaomi&utm_me" +
                        "dium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76" +
                        "F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a37" +
                        "5bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__sk" +
                        "no=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D").build();
                Response response = client.newCall(request).execute();
                json = response.body().string();
                JSONObject jsonObject=new JSONObject(json);
                JSONObject data=jsonObject.getJSONObject("data");
                JSONArray coming=data.getJSONArray("coming");
                for (int i=0;i<coming.length();i++){
                    JSONObject jsonObject1=coming.getJSONObject(i);
                    Songs songs=new Songs();
                    try{
                        songs.setSongtitle(jsonObject1.getString("nm"));
                    }catch (Exception e4){
                        songs.setSongtitle("信息跑丢了!-_-");
                    }
                    try{
                        songs.setSinger(jsonObject1.getString("cat"));
                    }catch (Exception e5){
                        songs.setSinger("信息跑丢了!-_-");
                    }
                    try{
                        songs.setImgpath(jsonObject1.getString("img"));
                    }catch (Exception e6){
                        songs.setImgpath("信息跑丢了!-_-");
                    }
                    try{
                        songs.setBoxInfo(jsonObject1.getString("boxInfo"));
                    }catch (Exception e7){
                        songs.setBoxInfo("信息跑丢了!-_-");
                    }
                    try{
                        songs.setComingTitle(jsonObject1.getString("comingTitle"));
                    }catch (Exception e8){
                        songs.setComingTitle("信息跑丢了!-_-");
                    }
                    try{
                        songs.setDesc(jsonObject1.getString("desc"));
                    }catch (Exception e9){
                        songs.setDesc("信息跑丢了!-_-");
                    }
                    try {
                        songs.setFra(jsonObject1.getString("fra"));
                    }catch (Exception e1){
                        songs.setFra("中国");
                    }
                    try{
                        songs.setVideoName(jsonObject1.getString("videoName"));
                    }catch (Exception e2){
                        songs.setVideoName("终极预告片");
                    }
                    try{
                        songs.setVideopath(jsonObject1.getString("videourl"));
                    }catch(Exception e3){
                        songs.setVideopath("1");
                    }
                    Message message=new Message();
                    message.obj=songs;
                    messenger.send(message);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
