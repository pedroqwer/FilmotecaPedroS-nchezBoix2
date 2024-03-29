
package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MoeActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    MediaPlayer mediaPlayer;
    MediaController mediaController;
    VideoView videoView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moe);

        button=(Button)findViewById(R.id.idVolver);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                finish();

            }
        });


        mediaController=new MediaController(this);
        mediaController.setMediaPlayer(this);

        videoView=(VideoView) findViewById(R.id.VideoFilm);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"));

        Handler h=new Handler();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaController.show();
                    }
                });

            }
        });
    }

    @Override
    public void start() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }

    }

    @Override
    public void pause() {

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {

        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (!mediaController.isShowing()){
                mediaController.show();
            }
            else {
                mediaController.hide();
            }
        }
        return  false;

    }
}
