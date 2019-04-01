package com.cyz.android.newmovie;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


/**
 * Created by 陈志 on 2018/4/16.
 */

public class VideoPaly extends AppCompatActivity {
    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String str1=bundle.getString("str1");

        vv=(VideoView)findViewById(R.id.videoView);
        try {
            if(str1 !="1") {
                vv.start();
                vv.setVideoURI(Uri.parse(str1));
                vv.setMediaController(new MediaController(VideoPaly.this));
                vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(VideoPaly.this, "播放完成了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}