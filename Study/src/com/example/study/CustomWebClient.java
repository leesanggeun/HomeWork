package com.example.study;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO 자동 생성된 메소드 스텁
		
		view.loadUrl(url);
		return true;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO 자동 생성된 메소드 스텁
		super.onPageFinished(view, url);
	}

}
