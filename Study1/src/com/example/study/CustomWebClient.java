package com.example.study;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO �ڵ� ������ �޼ҵ� ����
		
		view.loadUrl(url);
		return true;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO �ڵ� ������ �޼ҵ� ����
		super.onPageFinished(view, url);
	}

}
