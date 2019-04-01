package com.cyz.android.newmovie;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈志 on 2018/4/2.
 */

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private JsonAdapter jsonAdapter;
    private Songs songs;
    private List<Songs> msongs=new ArrayList<>();
    private Handler mHandler;
    private MyService.MyBinder myBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.DataDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                songs=(Songs) msg.obj;
                msongs.add(songs);
                jsonAdapter=new JsonAdapter(msongs);
                recyclerView.setAdapter(jsonAdapter);
            }
        };
        Intent intent1=new Intent(MainActivity.this,MyService.class);
        intent1.putExtra("str3",new Messenger(mHandler));
        startService(intent1);
        bindService(intent1,connection,BIND_AUTO_CREATE);
    }

//    class GetJsonData extends AsyncTask<Void,Void,Void>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//             @Override
//            protected Void doInBackground(Void... params) {
//
//            try{
//                OkHttpClient client=new OkHttpClient();
//                Request request=new Request.Builder().url("http://api.meituan.com/mmdb/movie/v2/list/rt/order/coming.json?ci=1&limit=12&token=&__vhos" +
//                        "t=api.maoyan.com&utm_campaign=AmovieBmovieCD-1&movieBundleVersion=6801&utm_source=xiaomi&utm_me" +
//                        "dium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76" +
//                        "F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a37" +
//                        "5bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__sk" +
//                        "no=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D").build();
//                Response response=client.newCall(request).execute();
//                String json=response.body().string();

//                JSONObject jsonObject=new JSONObject(json);
//                JSONObject data=jsonObject.getJSONObject("data");
//                JSONArray coming=data.getJSONArray("coming");
//                List<Songs> msongs=new ArrayList<>();
//                for (int i=0;i<coming.length();i++){
//                    JSONObject jsonObject1=coming.getJSONObject(i);
//                    Songs songs=new Songs();
//                    try{
//                        songs.setSongtitle(jsonObject1.getString("nm"));
//                    }catch (Exception e4){
//                        songs.setSongtitle("信息跑丢了!-_-");
//                    }
//                    try{
//                        songs.setSinger(jsonObject1.getString("cat"));
//                    }catch (Exception e5){
//                        songs.setSinger("信息跑丢了!-_-");
//                    }
//                    try{
//                        songs.setImgpath(jsonObject1.getString("img"));
//                    }catch (Exception e6){
//                        songs.setImgpath("信息跑丢了!-_-");
//                    }
//                    try{
//                        songs.setBoxInfo(jsonObject1.getString("boxInfo"));
//                    }catch (Exception e7){
//                        songs.setBoxInfo("信息跑丢了!-_-");
//                    }
//                    try{
//                        songs.setComingTitle(jsonObject1.getString("comingTitle"));
//                    }catch (Exception e8){
//                        songs.setComingTitle("信息跑丢了!-_-");
//                    }
//                    try{
//                        songs.setDesc(jsonObject1.getString("desc"));
//                    }catch (Exception e9){
//                        songs.setDesc("信息跑丢了!-_-");
//                    }
//                    try {
//                        songs.setFra(jsonObject1.getString("fra"));
//                    }catch (Exception e1){
//                        songs.setFra("中国");
//                    }
//                    try{
//                        songs.setVideoName(jsonObject1.getString("videoName"));
//                    }catch (Exception e2){
//                        songs.setVideoName("终极预告片");
//                    }
//                    try{
//                        songs.setVideopath(jsonObject1.getString("videourl"));
//                    }catch(Exception e3){
//                        songs.setVideopath("1");
//                    }
//                    msongs.add(songs);
//                }
//                jsonAdapter=new JsonAdapter(msongs);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            recyclerView.setAdapter(jsonAdapter);
//        }
//    }

    class JsonAdapter extends RecyclerView.Adapter<JsonAdapter.JsonViewHoler>{
        private List<Songs> song;

        public JsonAdapter(List<Songs> msongs){
            song=msongs;
        }

        class JsonViewHoler extends RecyclerView.ViewHolder{
            private TextView tv1;
            private TextView tv2;
//            private TextView tv3;
//            private VideoView vv;
            private ImageView iv;

            public JsonViewHoler(View view) {
                super(view);
                tv1=(TextView) view.findViewById(R.id.information1);
                tv2=(TextView) view.findViewById(R.id.information2);
//              tv3=(TextView) view.findViewById(R.id.videoname);
                iv=(ImageView) view.findViewById(R.id.img);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MainActivity.this,VideoPaly.class);
                        songs=song.get(getAdapterPosition());
                        i.putExtra("str1",songs.getVideopath());
                        startActivity(i);
                    }
                });
//                vv=(VideoView) view.findViewById(R.id.video);
            }
        }

        @Override
        public JsonViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view=layoutInflater.from(MainActivity.this).inflate(R.layout.json_layout,parent,false);
            return new JsonViewHoler(view);
        }

        @Override
        public void onBindViewHolder(JsonViewHoler holder, int position) {
            songs=song.get(position);
            holder.tv1.setText(songs.getSongtitle()+"\n"+songs.getSinger()+"\n"+songs.getBoxInfo()+songs.getComingTitle());
            holder.tv2.setText(songs.getFra()+"\n"+songs.getDesc());
//            holder.tv3.setText(songs.getVideoName());
//            try {
//                if (songs.getVideopath() != "1") {
//                    holder.vv.setVideoURI(Uri.parse(songs.getVideopath()));
//                    holder.vv.setMediaController(new MediaController(JsonActivity.this));
//                    holder.vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            Toast.makeText(JsonActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
            String str=songs.getImgpath().replaceAll("w.h","134.163");
            Glide.with(MainActivity.this).load(str).into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return song.size();
        }
    }
}
