package com.vidplayer.plus.barcode;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.content.*;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import arabware.credits.*;
import com.bumptech.glide.Glide;
import com.github.rubensousa.previewseekbar.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	private HashMap<String, Object> mapvar = new HashMap<>();
	private double index = 0;
	private String path = "";
	private String directory = "";
	private double index1 = 0;
	private double folderExist = 0;
	private double size = 0;
	private String humanReadableSize = "";
	private double folderView = 0;
	private double duration = 0;
	private String videoDuration = "";
	private String selectedPath = "";
	private String videoPath = "";
	private String videoSize = "";
	private String videoHeight = "";
	private String videoWidth = "";
	private String videoOrientation = "";
	private String videoDate = "";
	private double createdTimeMillisec = 0;
	private String formattedDate = "";
	private String errorMessage = "";
	private String result = "";
	private String str1 = "";
	private String str2 = "";
	private String str = "";
	private String file1 = "";
	private String file2 = "";
	private double milli_seconds_value = 0;
	private double n = 0;
	
	private ArrayList<HashMap<String, Object>> folders = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> videos = new ArrayList<>();
	private ArrayList<String> list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allVideosList = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout toolbar;
	private ListView listview1;
	private ListView listview2;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear3;
	private LinearLayout _drawer_linear6;
	private LinearLayout _drawer_linear7;
	private LinearLayout _drawer_linear8;
	private ImageView _drawer_imageview1;
	private TextView _drawer_textview1;
	private ImageView _drawer_imageview2;
	private TextView _drawer_textview4;
	private ImageView _drawer_imageview5;
	private TextView _drawer_textview3;
	private ImageView _drawer_imageview6;
	private TextView _drawer_textview6;
	private TextView _drawer_textview7;
	
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar calendar = Calendar.getInstance();
	private Intent i = new Intent();
	private Intent i1 = new Intent();
	private TimerTask t1;
	private ObjectAnimator obj1 = new ObjectAnimator();
	private RequestNetwork req1_dwnld;
	private RequestNetwork.RequestListener _req1_dwnld_request_listener;
	private Intent i2 = new Intent();
	private Intent i3 = new Intent();
	private Intent i4 = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_fab = findViewById(R.id._fab);
		
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		linear1 = findViewById(R.id.linear1);
		toolbar = findViewById(R.id.toolbar);
		listview1 = findViewById(R.id.listview1);
		listview2 = findViewById(R.id.listview2);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_linear2 = _nav_view.findViewById(R.id.linear2);
		_drawer_linear3 = _nav_view.findViewById(R.id.linear3);
		_drawer_linear6 = _nav_view.findViewById(R.id.linear6);
		_drawer_linear7 = _nav_view.findViewById(R.id.linear7);
		_drawer_linear8 = _nav_view.findViewById(R.id.linear8);
		_drawer_imageview1 = _nav_view.findViewById(R.id.imageview1);
		_drawer_textview1 = _nav_view.findViewById(R.id.textview1);
		_drawer_imageview2 = _nav_view.findViewById(R.id.imageview2);
		_drawer_textview4 = _nav_view.findViewById(R.id.textview4);
		_drawer_imageview5 = _nav_view.findViewById(R.id.imageview5);
		_drawer_textview3 = _nav_view.findViewById(R.id.textview3);
		_drawer_imageview6 = _nav_view.findViewById(R.id.imageview6);
		_drawer_textview6 = _nav_view.findViewById(R.id.textview6);
		_drawer_textview7 = _nav_view.findViewById(R.id.textview7);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		req1_dwnld = new RequestNetwork(this);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				folderView = 0;
				_getVideos(folders.get((int)_position).get("directory").toString());
			}
		});
		
		listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				i.setClass(getApplicationContext(), VideoplayerActivity.class);
				mapvar = videos.get((int)_position);
				i.putExtra("data", new Gson().toJson(mapvar));
				startActivity(i);
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
				
				View bottomSheetView; bottomSheetView = getLayoutInflater().inflate(R.layout.dwnld_dialog,null );
				bottomSheetDialog.setContentView(bottomSheetView);
				
				bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				LinearLayout dwnld_bg = (LinearLayout) bottomSheetView.findViewById(R.id.dwnld_bg);
				LinearLayout dwnld_edtlin1 = (LinearLayout) bottomSheetView.findViewById(R.id.dwnld_edtlin1);
				LinearLayout dwnld_btn1 = (LinearLayout) bottomSheetView.findViewById(R.id.dwnld_btn1);
				LinearLayout dwnld_btn2 = (LinearLayout) bottomSheetView.findViewById(R.id.dwnld_btn2);
				ImageView dwnld_img1 = (ImageView) bottomSheetView.findViewById(R.id.dwnld_img1);
				final EditText dwnld_edt1 = (EditText) bottomSheetView.findViewById(R.id.dwnld_edt1);
				dwnld_edt1.setSingleLine(true);
				dwnld_img1.setColorFilter(0xFF9E9E9E, PorterDuff.Mode.MULTIPLY);
				_Card_View(dwnld_bg, 30, "#FFFFFF", 0, 0, "");
				_Card_View(dwnld_btn1, 10, "#FFFFFF", 0, 1, "#D32F2F");
				_Card_View(dwnld_btn2, 10, "#D32F2F", 5, 0, "");
				_Card_View(dwnld_edtlin1, 10, "#FFFFFF", 0, 1, "#9E9E9E");
				dwnld_btn1.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
								
								bottomSheetDialog.dismiss();
								
						}
				});
				dwnld_btn2.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
						_Custom_Loading(true);
						req1_dwnld.startRequestNetwork(RequestNetworkController.GET, "https://maadhav-ytdl.herokuapp.com/video_info.php?url=".concat(dwnld_edt1.getText().toString()), "A", _req1_dwnld_request_listener);
						bottomSheetDialog.dismiss();
										
								}
						});
				bottomSheetDialog.setCancelable(false);
				bottomSheetDialog.show();
			}
		});
		
		_req1_dwnld_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				_Custom_Loading(false);
				str1 = _response.replace("{", "").replace("}", "").replace("\n", "").replace(" ", "").replace(":", "").replace("links", "").replace("[", "").replace("]", "").replace("\"", "");
				str2 = str1.trim().replace("\\", "").replace("https", "https:");
				i2.setAction(Intent.ACTION_VIEW);
				i2.setData(Uri.parse(str2));
				startActivity(i2);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				_Custom_Loading(false);
				SketchwareUtil.showMessage(getApplicationContext(), "Something Went Wrong");
			}
		};
		
		_drawer_linear1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_drawer_linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_drawer_linear3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i3.setAction(Intent.ACTION_VIEW);
				i3.setData(Uri.parse("https://web.sketchub.in/u/BARCODE_Developers"));
				startActivity(i3);
			}
		});
		
		_drawer_linear6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intentDynLink = new Intent();
				intentDynLink.setAction(Intent.ACTION_SEND);
				intentDynLink.putExtra(Intent.EXTRA_TEXT,  "https://web.sketchub.in/p/4523");
				intentDynLink.setType("text/plain");
				startActivity(intentDynLink);
			}
		});
		
		_drawer_linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i4.setAction(Intent.ACTION_VIEW);
				i4.setData(Uri.parse("https://t.me/ansil_shan"));
				startActivity(i4);
			}
		});
		
		_drawer_linear8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
	}
	
	private void initializeLogic() {
		
		_Card_View(toolbar, 0, "#FFFFFF", 15, 0, "");
		_drawerWithoutActionBar(false);
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);  androidx.drawerlayout.widget.DrawerLayout .LayoutParams params = (androidx.drawerlayout.widget.DrawerLayout .LayoutParams)_nav_view.getLayoutParams();  params.width = (int)getDip((int)250);  params.height = androidx.drawerlayout.widget.DrawerLayout .LayoutParams.MATCH_PARENT;  _nav_view.setLayoutParams(params);
		 _nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		_getAllVideos();
		folderView = 1;
		_getFolders();
		_SortMap(folders, "lowerCaseDirectoryName", false, true);
		listview2.setVisibility(View.GONE);
		listview1.setAdapter(new Listview1Adapter(folders));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		_fab.setBackgroundTintList(ColorStateList.valueOf(0xFFC62828));
		t1 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						obj1.setTarget(_fab);
						obj1.setPropertyName("translationY");
						obj1.setFloatValues((float)(0), (float)(-40));
						obj1.setDuration((int)(175));
						obj1.setInterpolator(new LinearInterpolator());
						obj1.setRepeatMode(ValueAnimator.REVERSE);
						obj1.setRepeatCount((int)(7));
						obj1.start();
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t1, (int)(0), (int)(3000));
	}
	
	@Override
	public void onBackPressed() {
		if (folderView == 0) {
			listview1.setVisibility(View.VISIBLE);
			listview2.setVisibility(View.GONE);
			folderView = 1;
		}
		else {
			finish();
		}
	}
	public void _getAllVideos() {
		Uri uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String[] projection = {
			android.provider.MediaStore.Video.VideoColumns.DATA,
			 android.provider.MediaStore.Video.VideoColumns.SIZE,
			 android.provider.MediaStore.Video.VideoColumns.HEIGHT,
			 android.provider.MediaStore.Video.VideoColumns.WIDTH,
			 android.provider.MediaStore.Video.VideoColumns.DURATION,
			 android.provider.MediaStore.Video.VideoColumns.DATE_MODIFIED
		};
		android.database.Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				videoPath = cursor.getString(0);
				videoSize = cursor.getString(1);
				videoHeight = cursor.getString(2);
				videoWidth = cursor.getString(3);
				videoDuration = cursor.getString(4);
				videoDate = cursor.getString(5);
				mapvar = new HashMap<>();
				mapvar.put("videoPath", videoPath);
				mapvar.put("videoSize", videoSize);
				mapvar.put("videoHeight", videoHeight);
				mapvar.put("videoWidth", videoWidth);
				mapvar.put("videoDate", videoDate);
				mapvar.put("videoDuration", videoDuration);
				createdTimeMillisec = new java.io.File(videoPath).lastModified();
				calendar.setTimeInMillis((long)(createdTimeMillisec));
				formattedDate = new SimpleDateFormat("dd MMM").format(calendar.getTime());
				mapvar.put("formattedDate", formattedDate.toUpperCase());
				allVideosList.add(mapvar);
			}
			cursor.close();
		}
	}
	
	
	public void _getFolders() {
		index = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(allVideosList.size()); _repeat12++) {
			path = allVideosList.get((int)index).get("videoPath").toString();
			java.io.File file = new java.io.File(path);
			directory = file.getParent();
			index1 = 0;
			folderExist = 0;
			for(int _repeat36 = 0; _repeat36 < (int)(folders.size()); _repeat36++) {
				if (directory.equals(folders.get((int)index1).get("directory").toString())) {
					folderExist = 1;
					folders.get((int)index1).put("items", String.valueOf((long)(Double.parseDouble(folders.get((int)index1).get("items").toString()) + 1)));
					folders.get((int)index1).put("size", String.valueOf((long)(Double.parseDouble(folders.get((int)index1).get("size").toString()) + FileUtil.getFileLength(path))));
				}
				index1++;
			}
			if (folderExist == 0) {
				mapvar = new HashMap<>();
				mapvar.put("directory", directory);
				mapvar.put("directoryName", Uri.parse(directory).getLastPathSegment());
				mapvar.put("lowerCaseDirectoryName", Uri.parse(directory).getLastPathSegment().toLowerCase());
				mapvar.put("items", "1");
				mapvar.put("size", String.valueOf((long)(FileUtil.getFileLength(path))));
				folders.add(mapvar);
			}
			index++;
		}
	}
	
	
	public void _SortMap(final ArrayList<HashMap<String, Object>> _listMap, final String _key, final boolean _isNumber, final boolean _Ascending) {
		Collections.sort(_listMap, new Comparator<HashMap<String,Object>>(){
			public int compare(HashMap<String,Object> _compareMap1, HashMap<String,Object> _compareMap2){
				if (_isNumber) {
					int _count1 = Integer.valueOf(_compareMap1.get(_key).toString());
					int _count2 = Integer.valueOf(_compareMap2.get(_key).toString());
					if (_Ascending) {
						return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;
					}
					else {
						return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;
					}
				}
				else {
					if (_Ascending) {
						return (_compareMap1.get(_key).toString()).compareTo(_compareMap2.get(_key).toString());
					}
					else {
						return (_compareMap2.get(_key).toString()).compareTo(_compareMap1.get(_key).toString());
					}
				}
			}});
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
	
	
	public void _shapeRadius(final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
	}
	
	
	public void _getVideos(final String _directory) {
		videos.clear();
		index = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allVideosList.size()); _repeat11++) {
			path = allVideosList.get((int)index).get("videoPath").toString();
			java.io.File file = new java.io.File(path);
			directory = file.getParent();
			if (directory.equals(_directory)) {
				mapvar = allVideosList.get((int)index);
				videos.add(mapvar);
			}
			index++;
		}
		listview1.setVisibility(View.GONE);
		listview2.setAdapter(new Listview2Adapter(videos));
		((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
		listview2.setVisibility(View.VISIBLE);
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
	
	
	public void _drawerWithoutActionBar(final boolean _abshown) {
		if(_abshown==true){
			getSupportActionBar().show();
		}else{
			if(_abshown==false){
				getSupportActionBar().hide();
			}else{
			}
		}
	}
	
	
	public void _Custom_Loading(final boolean _ifShow) {
		if (_ifShow) {
			if (coreprog == null){
				coreprog = new ProgressDialog(this);
				coreprog.setCancelable(false);
				coreprog.setCanceledOnTouchOutside(false);
				
				coreprog.requestWindowFeature(Window.FEATURE_NO_TITLE);  coreprog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
				
			}
			coreprog.setMessage(null);
			coreprog.show();
			View _view = getLayoutInflater().inflate(R.layout.custom1, null);
			LinearLayout linear_base = (LinearLayout) _view.findViewById(R.id.linear_base);
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor("#FFFFFF"));
			gd.setCornerRadius(25);
			linear_base.setBackground(gd);
			coreprog.setContentView(_view);
		}
		else {
			if (coreprog != null){
				coreprog.dismiss();
			}
		}
	}
	private ProgressDialog coreprog;
	{
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.folder_item, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			
			_shapeRadius(textview3, "#425B7C", 5);
			textview1.setText(folders.get((int)_position).get("directoryName").toString());
			textview2.setText(folders.get((int)_position).get("items").toString().concat(" videos"));
			size = Double.parseDouble(folders.get((int)_position).get("size").toString());
			humanReadableSize = bytesIntoHumanReadable((long)size);
			textview3.setText(humanReadableSize);
			
			return _view;
		}
	}
	
	public class Listview2Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.video_item, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			
			_shapeRadius(textview1, "#000000", 5);
			_shapeRadius(textview3, "#425B7C", 5);
			_shapeRadius(textview4, "#425B7C", 5);
			duration = Double.parseDouble(videos.get((int)_position).get("videoDuration").toString());
			videoDuration = stringForTime((int)duration);
			textview1.setText(videoDuration);
			path = videos.get((int)_position).get("videoPath").toString();
			
			androidx.cardview.widget.CardView cardview1 = new androidx.cardview.widget.CardView(MainActivity.this);
			cardview1.setCardElevation(0);
			cardview1.setRadius(10);
			ViewGroup imageParent = ((ViewGroup)imageview1.getParent()); imageParent.removeView(imageview1);
			cardview1.addView(imageview1);
			imageParent.addView(cardview1);
			if (Uri.parse(path).getLastPathSegment().length() > 34) {
				textview2.setText(Uri.parse(path).getLastPathSegment().substring((int)(0), (int)(34)).concat("..........."));
			}
			else {
				textview2.setText(Uri.parse(path).getLastPathSegment());
			}
			size = Double.parseDouble(videos.get((int)_position).get("videoSize").toString());
			humanReadableSize = bytesIntoHumanReadable((long)size);
			textview3.setText(humanReadableSize);
			com.bumptech.glide.Glide.with(getApplicationContext())
			.load(path)
			.into(imageview1);
			textview4.setText(videos.get((int)_position).get("formattedDate").toString());
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setClass(getApplicationContext(), VideoplayerActivity.class);
					mapvar = videos.get((int)_position);
					i.putExtra("data", new Gson().toJson(mapvar));
					startActivity(i);
				}
			});
			
			return _view;
		}
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