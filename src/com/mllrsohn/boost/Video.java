package com.mllrsohn.boost;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Video extends Activity implements
    OnCompletionListener, OnErrorListener, OnInfoListener,
    OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener,
    SurfaceHolder.Callback {
  Display currentDisplay;

  SurfaceView surfaceView;
  SurfaceHolder surfaceHolder;

  MediaPlayer mediaPlayer;

  int videoWidth = 0,videoHeight = 0;

  boolean readyToPlay = false;

  public final static String LOGTAG = "CUSTOM_VIDEO_PLAYER";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.video);

    surfaceView = (SurfaceView) this.findViewById(R.id.SurfaceView);
    surfaceHolder = surfaceView.getHolder();
    surfaceHolder.addCallback(this);
    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    mediaPlayer = new MediaPlayer();
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.setOnErrorListener(this);
    mediaPlayer.setOnInfoListener(this);
    mediaPlayer.setOnPreparedListener(this);
    mediaPlayer.setOnSeekCompleteListener(this);
    mediaPlayer.setOnVideoSizeChangedListener(this);
    String filePath = Environment.getExternalStorageDirectory().getPath()
        + "/Test.m4v";

    try {
      mediaPlayer.setDataSource(filePath);
    } catch (Exception e) {
      Log.v(LOGTAG, e.getMessage());
      finish();
    }
    currentDisplay = getWindowManager().getDefaultDisplay();
  }

  public void surfaceCreated(SurfaceHolder holder) {
    mediaPlayer.setDisplay(holder);

    try {
      mediaPlayer.prepare();
    } catch (Exception e) {
      finish();
    }
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int width,
      int height) {
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
  }

  public void onCompletion(MediaPlayer mp) {
    finish();
  }

  public boolean onError(MediaPlayer mp, int whatError, int extra) {
    Log.v(LOGTAG, "onError Called");

    if (whatError == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
      Log.v(LOGTAG, "Media Error, Server Died " + extra);
    } else if (whatError == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
      Log.v(LOGTAG, "Media Error, Error Unknown " + extra);
    }

    return false;
  }

  public boolean onInfo(MediaPlayer mp, int whatInfo, int extra) {
    if (whatInfo == MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING) {
      Log.v(LOGTAG, "Media Info, Media Info Bad Interleaving " + extra);
    } else if (whatInfo == MediaPlayer.MEDIA_INFO_NOT_SEEKABLE) {
      Log.v(LOGTAG, "Media Info, Media Info Not Seekable " + extra);
    } else if (whatInfo == MediaPlayer.MEDIA_INFO_UNKNOWN) {
      Log.v(LOGTAG, "Media Info, Media Info Unknown " + extra);
    } else if (whatInfo == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
      Log.v(LOGTAG, "MediaInfo, Media Info Video Track Lagging " + extra);
    }else if (whatInfo == MediaPlayer.MEDIA_INFO_METADATA_UPDATE) {
        Log.v(LOGTAG,"MediaInfo, Media Info Metadata Update " + extra);
    }
    return false;
  }

  public void onPrepared(MediaPlayer mp) {
    videoWidth = mp.getVideoWidth();
    videoHeight = mp.getVideoHeight();
    surfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth,
        videoHeight));
    mp.start();
  }

  public void onSeekComplete(MediaPlayer mp) {
  }

  public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
  }
}