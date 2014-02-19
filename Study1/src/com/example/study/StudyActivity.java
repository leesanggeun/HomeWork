package com.example.study;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

/*
 * 미완성 부분 : favicon
 * 
 * 구성도
 * 
 * Text() 입력 받은 값을 String Tag에 저장 시켰으며, 이 정보를 이용하여 HttpGet을 통해 website의 Title을 얻어 ActionBar Tab
 * Name에 입력 될 수 있도록 구성 하였습니다.
 */

public class StudyActivity extends Activity {

	private String addressName;// HttpGet을 통해 입력 받은 URL Name

	private static String Tag; // Diaglog 'input'으로 부터 입력 받은 값을 저장
	private static String saveTag;

	FragmentManager fm;
	ActionBar actionBar;
	TextView check_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study);

		check_URL = (TextView) findViewById(R.id.textView1);

		fm = getFragmentManager();

	}// end of onCreate

	public static class TabListener<T extends Fragment> implements
			ActionBar.TabListener {

		private final Activity myActivity;
		private final String myTag;
		private final Class<T> myClass;

		public TabListener(Activity activity, String tag, Class<T> cls) {
			myActivity = activity;
			myTag = tag;
			myClass = cls;

		}// end of TabListener

		@SuppressLint("NewApi")
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {

			// String a = tab.getTag().toString();
			String tabUrlTag = tab.getTag().toString();
			saveTag = tabUrlTag;

			Fragment myFragment = myActivity.getFragmentManager()
					.findFragmentByTag(myTag);

			// urlNameSend = tab.getText().toString();

			// Check if the fragment is already initialized
			if (myFragment == null) {
				// If not, instantiate and add it to the activity
				myFragment = Fragment
						.instantiate(myActivity, myClass.getName());
				ft.add(android.R.id.content, myFragment, myTag);

				Log.i("onTabSelected", "myTag --->>>>>" + myTag);

			}// end of if
			else {

				ft.attach(myFragment);

			}// end of else

		}// end of onTabSelected

		@SuppressLint("NewApi")
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

			Fragment myFragment = myActivity.getFragmentManager()
					.findFragmentByTag(myTag);

			if (myFragment != null) {

				ft.detach(myFragment);

			}// end of if

		}// end of onTabUnselected

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

			try {
				tab.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}// end of try and catch

		}// end of onTabReaselected

	}// end of TabListener

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.study, menu);

		Log.i("Main", "onCreateOptionMenu");
		return true;
	}// end of onCreateOptionMenu

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 자동 생성된 메소드 스텁

		if (item.getItemId() == R.id.item1) {

			Log.i("Main", "onOptionItemSelected");
			check_URL.setVisibility(View.GONE);

			Text();

		}
		return super.onOptionsItemSelected(item);
	}// end of onOptionsItemSelected

	public static class Fragb extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// TODO Auto-generated method stub
			View myFragmentView = inflater.inflate(R.layout.fragb, container,
					false);

			TextView tv = (TextView) myFragmentView
					.findViewById(R.id.textView1);
			tv.setText(saveTag); // tag URL 정보가 제대로 넘어 온지를 확인을 위한 TextView

			WebView webView;
			webView = (WebView) myFragmentView.findViewById(R.id.webView1);
			WebSettings webSettings = webView.getSettings();
			webView.setScrollContainer(false);
			webSettings.setJavaScriptEnabled(true);

			webView.setWebViewClient(new CustomWebClient());

			webView.loadUrl("http://" + saveTag);

			return myFragmentView;

		}//end of onCreateView

	}//end of Fragb class

	public void Text() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("사이트 주소를 입력해 주세요");

		final EditText input = new EditText(this);

		// input.setText("");
		input.setHint("주소를 입력하세요");
		builder.setView(input);

		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				HttpLoadThread httpLoadThread = new HttpLoadThread(); // new
				httpLoadThread.start();

				Tag = input.getText().toString();

				FragmentTransaction ft2 = fm.beginTransaction();

				ft2.replace(R.id.fragArea, new Fragb());
				ft2.addToBackStack(null);

				ft2.commit();

			}

		});

		builder.create();
		builder.show();
	}// end of Text()

	// -----

	class HttpLoadThread extends Thread {
		@Override
		public void run() {
			// TODO 자동 생성된 메소드 스텁
			HttpGet get = new HttpGet("http://" + Tag);
			Log.i("HttpLoad", "httpGet ---->>>" + get.getURI().toString());
			DefaultHttpClient defaultClient = new DefaultHttpClient();

			try {
				defaultClient.execute(get, responseHandler);

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}

	ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		@Override
		public String handleResponse(HttpResponse response) {
			final StringBuilder sb = new StringBuilder();

			if (response.getStatusLine().getStatusCode() == 200) {

				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent()));
					String line = null;

					while ((line = br.readLine()) != null)

					{
						sb.append(line);

					}

					br.close();
					loadHandler.post(new Runnable() {

						@Override
						public void run() {
							// TODO 자동 생성된 메소드 스텁

							addressName = (sb.substring(
									sb.indexOf("<title>") + 7,
									sb.lastIndexOf("</title>")));

							actionBar = getActionBar();
							actionBar
									.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

							actionBar.addTab(actionBar
									.newTab()
									.setText(addressName)
									.setTag(Tag)
									// 직접 tag 를 입력 받음
									.setTabListener(
											new TabListener<Fragb>(
													StudyActivity.this, Tag,
													Fragb.class)));

						}
					});

				} catch (Exception e) {
					;
				}

			}// end of if

			return sb.toString();
		}

	};

	Handler loadHandler = new Handler();

}