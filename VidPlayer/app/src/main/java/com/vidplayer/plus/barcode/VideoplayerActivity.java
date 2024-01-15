package com.vidplayer.plus.barcode;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import arabware.credits.*;
import com.github.rubensousa.previewseekbar.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import arabware.credits.SketchwareFrameExtractor;

public class VideoplayerActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> data = new HashMap<>();
	private double videoHeight = 0;
	private double videoWidth = 0;
	private String path = "";
	private boolean isPlaying = false;
	private double duration = 0;
	private String videoDuration = "";
	private double cduration = 0;
	private String currentDuration = "";
	private double time = 0;
	
	private LinearLayout relativelayout_1;
	private LinearLayout relativelayout_2;
	private VideoView vid_content;
	private LinearLayout relativelayout_3;
	private LinearLayout linear15;
	private LinearLayout linear14;
	private LinearLayout linear13;
	private LinearLayout linear20;
	private ImageView imageview9;
	private TextView textview19;
	private LinearLayout linear18;
	private ImageView backward;
	private LinearLayout linear16;
	private ImageView play_pause;
	private LinearLayout linear17;
	private ImageView forward;
	private LinearLayout linear19;
	private FrameLayout previewFrameLayout;
	private PreviewSeekBar seekbar1;
	private ImageView imageView;
	private TextView current_progress;
	private LinearLayout linear21;
	private TextView max_progress;
	
	private TimerTask t;
	private TimerTask t1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.videoplayer);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		relativelayout_1 = findViewById(R.id.relativelayout_1);
		relativelayout_2 = findViewById(R.id.relativelayout_2);
		vid_content = findViewById(R.id.vid_content);
		MediaController vid_content_controller = new MediaController(this);
		vid_content.setMediaController(vid_content_controller);
		relativelayout_3 = findViewById(R.id.relativelayout_3);
		linear15 = findViewById(R.id.linear15);
		linear14 = findViewById(R.id.linear14);
		linear13 = findViewById(R.id.linear13);
		linear20 = findViewById(R.id.linear20);
		imageview9 = findViewById(R.id.imageview9);
		textview19 = findViewById(R.id.textview19);
		linear18 = findViewById(R.id.linear18);
		backward = findViewById(R.id.backward);
		linear16 = findViewById(R.id.linear16);
		play_pause = findViewById(R.id.play_pause);
		linear17 = findViewById(R.id.linear17);
		forward = findViewById(R.id.forward);
		linear19 = findViewById(R.id.linear19);
		previewFrameLayout = findViewById(R.id.previewFrameLayout);
		seekbar1 = findViewById(R.id.seekbar1);
		imageView = findViewById(R.id.imageView);
		current_progress = findViewById(R.id.current_progress);
		linear21 = findViewById(R.id.linear21);
		max_progress = findViewById(R.id.max_progress);
		
		relativelayout_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				relativelayout_2.setVisibility(View.VISIBLE);
				_ViewFadeIn(relativelayout_2, 200);
			}
		});
		
		vid_content.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer _mediaPlayer) {
				isPlaying = true;
				duration = vid_content.getDuration();
				videoDuration = stringForTime((int)duration);
				max_progress.setText(videoDuration);
				seekbar1.setMax((int)vid_content.getDuration());
				t = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (isPlaying) {
									cduration = vid_content.getCurrentPosition();
									currentDuration = stringForTime((int)cduration);
									current_progress.setText(currentDuration);
									seekbar1.setProgress((int)vid_content.getCurrentPosition());
									if ((vid_content.getCurrentPosition() == vid_content.getDuration()) || (vid_content.getDuration() < vid_content.getCurrentPosition())) {
										finish();
									}
								}
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(t, (int)(0), (int)(1000));
			}
		});
		
		relativelayout_3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				relativelayout_2.setVisibility(View.GONE);
			}
		});
		
		imageview9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		backward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_seekVideo(vid_content.getCurrentPosition() - 10000);
			}
		});
		
		play_pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isPlaying) {
					vid_content.pause();
					play_pause.setImageResource(R.drawable.playarrow);
					isPlaying = false;
				}
				else {
					vid_content.start();
					play_pause.setImageResource(R.drawable.pause);
					isPlaying = true;
				}
			}
		});
		
		forward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_seekVideo(vid_content.getCurrentPosition() + 10000);
			}
		});
	}
	
	private void initializeLogic() {
		
		((ViewGroup) vid_content.getParent()).removeView(vid_content);
		((ViewGroup) relativelayout_2.getParent()).removeView(relativelayout_2);
		android.widget.RelativeLayout rl = new android.widget.RelativeLayout(VideoplayerActivity.this);
		
		rl.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
		
		rl.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		
		relativelayout_1.removeAllViews();
		
		relativelayout_1.addView(rl);
		
		rl.addView(vid_content);
		
		rl.addView(relativelayout_2);
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		vid_content.setMediaController(null);
		seekbar1.getProgressDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
		seekbar1.getThumb().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int colors [] = { 0xD8000000, 0x00000000, 0xD8000000 };
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colors);
			relativelayout_3.setElevation(getDip(5));
			android.graphics.drawable.RippleDrawable SketchUi_RD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
			relativelayout_3.setBackground(SketchUi_RD);
		}
		data = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<HashMap<String, Object>>(){}.getType());
		videoHeight = Double.parseDouble(data.get("videoHeight").toString());
		videoWidth = Double.parseDouble(data.get("videoWidth").toString());
		if (videoWidth > videoHeight) {
			setRequestedOrientation(Build.VERSION.SDK_INT < 9 ?
			
			android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
			                  android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
			
		}
		path = data.get("videoPath").toString();
		vid_content.setVideoURI(Uri.parse(path));
		vid_content.start();
		_Card_View(previewFrameLayout, 10, "#FFFFFF", 8, 0, "");
		t1 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
					}
				});
			}
		};
		_timer.schedule(t1, (int)(50));
		if (Uri.parse(path).getLastPathSegment().length() < 40) {
			textview19.setText(Uri.parse(path).getLastPathSegment());
		}
		else {
			textview19.setText(Uri.parse(path).getLastPathSegment().substring((int)(0), (int)(30)).concat("......."));
		}
		seekbar1.addOnScrubListener(new PreviewBar.OnScrubListener() {
			    @Override
			    public void onScrubStart(PreviewBar previewBar) {
				        
				vid_content.pause();
				play_pause.setImageResource(R.drawable.playarrow);
				isPlaying = false;
			}
			
			    @Override
			    public void onScrubMove(PreviewBar previewBar, int progress, boolean fromUser) {
				        
				time = progress;
				t1.cancel();
				t1 = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								imageView.setImageBitmap(SketchwareFrameExtractor.getFrameFromVideoPath(path,(int)time));
							}
						});
					}
				};
				_timer.schedule(t1, (int)(250));
				  }
			
			    @Override
			    public void onScrubStop(PreviewBar previewBar) {
				        
				_seekVideo(time);
				vid_content.start();
				play_pause.setImageResource(R.drawable.pause);
				isPlaying = true;
			}
		});
	}
	
	public void _Card_View(final View _view, final double _cornerradius, final String _bgcolor, final double _elevation, final double _stroke, final String _strokecolor) {
		if (_stroke == 0) {
			//𝐁𝐥𝐨𝐜𝐤 𝐜𝐫𝐞𝐚𝐭𝐞𝐝 𝐛𝐲 𝐇-6𝐢𝐱
			android.graphics.drawable.GradientDrawable cv = new android.graphics.drawable.GradientDrawable(); 
			float cornerradius = (float) _cornerradius;
			cv.setCornerRadius(cornerradius);
			cv.setColor(Color.parseColor("#" + _bgcolor.replace("#", "")));
			_view.setBackground(cv);
			float elevation = (float) _elevation;
			_view.setElevation(elevation);
		}
		else {
			android.graphics.drawable.GradientDrawable cv = new android.graphics.drawable.GradientDrawable(); 
			float cornerradius = (float) _cornerradius;
			cv.setStroke((int)_stroke, Color.parseColor("#" + _strokecolor.replace("#", "")));
			cv.setCornerRadius(cornerradius);
			cv.setColor(Color.parseColor("#" + _bgcolor.replace("#", "")));
			_view.setBackground(cv);
			float elevation = (float) _elevation;
			_view.setElevation(elevation);
		}
	}
	
	
	public void _seekVideo(final double _position) {
		vid_content.seekTo((int)_position);
	}
	
	
	public void _ViewFadeIn(final View _viewFadeIn, final double _viewFadeInSetTime) {
		long x = (long)_viewFadeInSetTime;
		
		Animation fadeIn = new AlphaAnimation(0, 1); 
		fadeIn.setDuration(x);
		AnimationSet animation = new AnimationSet(true); animation.addAnimation(fadeIn);
		_viewFadeIn.startAnimation(animation);
	}
	
	
	public void _library() {
	}
	private String bytesIntoHumanReadable(long bytes) {
		    long kilobyte = 1024;
		    long megabyte = kilobyte * 1024;
		    long gigabyte = megabyte * 1024;
		    long terabyte = gigabyte * 1024;
		
		    if ((bytes >= 0) && (bytes < kilobyte)) {
			        return bytes + " B";
			
			    } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
			        return (bytes / kilobyte) + " KB";
			
			    } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
			        return (bytes / megabyte) + " MB";
			
			    } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
			        return (bytes / gigabyte) + " GB";
			
			    } else if (bytes >= terabyte) {
			        return (bytes / terabyte) + " TB";
			
			    } else {
			        return bytes + " Bytes";
			    }
	}
	{
	}
	private String stringForTime(int timeMs) {
		StringBuilder mFormatBuilder = null;
		Formatter mFormatter = null;
		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
		        int totalSeconds = timeMs / 1000;
		
		        int seconds = totalSeconds % 60;
		        int minutes = (totalSeconds / 60) % 60;
		        int hours = totalSeconds / 3600;
		
		        mFormatBuilder.setLength(0);
		        if (hours > 0) {
			            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
			        } else {
			            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
			        }
		    }
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}