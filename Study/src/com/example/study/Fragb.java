package com.example.study;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Fragb extends Fragment {
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		StudyActivity studyActivity = new StudyActivity();
		
		String nameUrl = studyActivity.saveTag;
		
		// TODO Auto-generated method stub
		View myFragmentView = inflater.inflate(R.layout.fragb, container, false);

		TextView tv = (TextView) myFragmentView.findViewById(R.id.textView1);
		tv.setText(nameUrl); // tag URL 정보가 제대로 넘어 온지를 확인을 위한 TextView

		WebView webView;
		webView = (WebView) myFragmentView.findViewById(R.id.webView1);
		WebSettings webSettings = webView.getSettings();
		webView.setScrollContainer(false);
		webSettings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new CustomWebClient());

		webView.loadUrl("http://" + nameUrl);

		return myFragmentView;
		//265868
	}// end of onCreateView

} // end of Fragb class