/*
 * Copyright (C) 2010 The Android Open Source Project
 * Portions Copyright (c) 2013 CommonsWare, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commonsware.cwac.preso;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class MirroringWebViewFragment extends MirroringFragment {
	private WebView mWebView;
	private boolean mIsWebViewAvailable;

	@SuppressLint("SetJavaScriptEnabled") 
	@Override
	protected View onCreateMirroredContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mWebView != null) {
			mWebView.destroy();
		}

		mWebView = new WebView(getActivity());
		mIsWebViewAvailable = true;
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(getString(R.string.default_url));
		
		Log.d("dazeaz", "mIsWebViewAvailable : " + mIsWebViewAvailable);
		return mWebView;
	}

	/**
	 * Called when the fragment is visible to the user and actively running. Resumes the WebView.
	 */
	@Override
	public void onPause() {
		super.onPause();

		mWebView.onPause();
	}

	/**
	 * Called when the fragment is no longer resumed. Pauses the WebView.
	 */
	@Override
	public void onResume() {
		mWebView.onResume();

		super.onResume();
	}

	/**
	 * Called when the WebView has been detached from the fragment. The WebView is no longer available after this time.
	 */
	@Override
	public void onDestroyView() {
		mIsWebViewAvailable = false;
		super.onDestroyView();
	}

	/**
	 * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
	 */
	@Override
	public void onDestroy() {
		if (mWebView != null) {
			mWebView.destroy();
			mWebView = null;
		}
		super.onDestroy();
	}

	/**
	 * Gets the WebView.
	 */
	public WebView getWebView() {
		return mIsWebViewAvailable ? mWebView : null;
	}
}
