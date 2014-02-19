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
 * �̿ϼ� �κ� : favicon
 * 
 * ������
 * 
 * Text() �Է� ���� ���� String Tag�� ���� ��������, �� ������ �̿��Ͽ� HttpGet�� ���� website�� Title�� ��� ActionBar Tab
 * Name�� �Է� �� �� �ֵ��� ���� �Ͽ����ϴ�.
 */

public class StudyActivity extends Activity {

	private String addressName;// HttpGet�� ���� �Է� ���� URL Name

	private static String Tag; // Diaglog 'input'���� ���� �Է� ���� ���� ����
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
		// TODO �ڵ� ������ �޼ҵ� ����

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
			tv.setText(saveTag); // tag URL ������ ����� �Ѿ� ������ Ȯ���� ���� TextView

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
		builder.setTitle("����Ʈ �ּҸ� �Է��� �ּ���");

		final EditText input = new EditText(this);

		// input.setText("");
		input.setHint("�ּҸ� �Է��ϼ���");
		builder.setView(input);

		builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
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
			// TODO �ڵ� ������ �޼ҵ� ����
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
							// TODO �ڵ� ������ �޼ҵ� ����

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
									// ���� tag �� �Է� ����
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