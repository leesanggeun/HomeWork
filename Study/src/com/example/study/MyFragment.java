package com.example.study;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MyFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View myFragmentView = inflater.inflate(R.layout.frag_layout, container,
				false);
		WebView webView;
		webView = (WebView) myFragmentView.findViewById(R.id.webView1);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new CustomWebClient());

        
		
	//	dummyTextView.setText(Integer.toString(getArguments().getInt(
		//		ARG_SECTION_NUMBER)));
		
		webView.loadUrl("http://www.naver.com");

		// return inflater.inflate(R.layout.frag_layout, container, false);

		return myFragmentView;

	}

}
