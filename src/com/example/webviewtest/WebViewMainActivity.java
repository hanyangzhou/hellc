package com.example.webviewtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewMainActivity extends Activity {

	private WebView webView;
	private static String homeUrl = "http://m.365omo.com/";
	private ProgressDialog progressBar;
	private Button btn_back, btn_refresh, btn_home, btn_quit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_main);
		webView = (WebView) findViewById(R.id.web_view);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		
		btn_back.setOnClickListener(onClickListener);
		btn_refresh.setOnClickListener(onClickListener);
		btn_home.setOnClickListener(onClickListener);
		btn_quit.setOnClickListener(onClickListener);
		
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.loadUrl(homeUrl);
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		// ����Web��ͼ
		webView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			// ��ʼ������ҳʱҪ���Ĺ���
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (progressBar == null || (progressBar != null && !progressBar.isShowing())) {					
					progressBar = ProgressDialog.show(WebViewMainActivity.this,
							 null, "���ڼ���...");
					progressBar.setProgressStyle(R.style.dialog);
					progressBar.setCanceledOnTouchOutside(true);
				}else{
					
				}
				
			}

			// �������ʱҪ���Ĺ���
			@Override
			public void onPageFinished(WebView view, String url) {
				if (progressBar.isShowing()) {
					progressBar.dismiss();
				}
			}

			@Override
			// ת�����ʱ�Ĵ���
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				alertDialog.setTitle("����");
				alertDialog.setMessage("����������������ã�");
				alertDialog.setButton("�õ�", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(Build.VERSION.SDK_INT > 14){
							startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
							}else {
							startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							}
					}
				});
				alertDialog.show();
			}
//
//			// ������ҳ���صĽ�����
//			public void onProgressChanged(WebView view, int newProgress) {
//				WebViewMainActivity.this.getWindow().setFeatureInt(
//						Window.FEATURE_PROGRESS, newProgress * 100);
//				super.onProgressChanged(view, newProgress);
//			}
		});	
	}

	View.OnClickListener onClickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
				case R.id.btn_back:
					webView.goBack();
					if (progressBar.isShowing()) {
						progressBar.dismiss();
					}
					break;
				case R.id.btn_refresh:
					webView.loadUrl(webView.getUrl());
					break;
				case R.id.btn_home:
					webView.loadUrl(homeUrl);
					break;
				case R.id.btn_quit:
					WebViewMainActivity.this.finish();
					break;
			}
		}
	};
	@Override
	// ���û���
	// ����Activity���onKeyDown(int keyCoder,KeyEvent event)����
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack(); // goBack()��ʾ����WebView����һҳ��
			progressBar.dismiss();
			return true;
		}
		return super.onKeyDown(keyCode, event);  
	}
}
