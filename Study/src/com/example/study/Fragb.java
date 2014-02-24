package com.example.study;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Fragb extends Fragment {
	
	static String  TAG = "Fragb";
	public static String URLNAME_ID ="url_name";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		
		
		
		/*StudyActivity studyActivity = new StudyActivity();
		
		String nameUrl = studyActivity.saveTag;*/
		
		// TODO Auto-generated method stub
		View myFragmentView = inflater.inflate(R.layout.fragb, container, false);

		if (getArguments() != null) {
			String nameUrl = getArguments().getString(URLNAME_ID);
			//Log.i(TAG,"nameUrl:     "+test);
			TextView tv = (TextView) myFragmentView.findViewById(R.id.textView1);
			tv.setText(nameUrl); // tag URL 정보가 제대로 넘어 온지를 확인을 위한 TextView
	
			WebView webView;
			webView = (WebView) myFragmentView.findViewById(R.id.webView1);
			WebSettings webSettings = webView.getSettings();
			webView.setScrollContainer(false);
			webSettings.setJavaScriptEnabled(true);
	
			webView.setWebViewClient(new CustomWebClient());
	
			//webView.loadUrl("http://www.naver.com");
			webView.loadUrl("http://" + nameUrl);
		}

		return myFragmentView;
		//265868
	}// end of onCreateView

	private void debug(String string, String string2) {
		// TODO 자동 생성된 메소드 스텁
		
	}

} // end of Fragb class