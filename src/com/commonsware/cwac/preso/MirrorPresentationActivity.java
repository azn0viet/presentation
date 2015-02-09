/***
Copyright (c) 2013 CommonsWare, LLC
Licensed under the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License. You may obtain
a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.commonsware.cwac.preso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MirrorPresentationActivity extends Activity implements PresentationHelper.Listener {
	PresentationFragment preso = null;
	PresentationHelper helper = null;

	MirroringWebViewFragment source = null;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mirror_presentation);

		MirroringWebViewFragment frag = new MirroringWebViewFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.source_frame, frag);
		transaction.commit();
		
		helper = new PresentationHelper(this, this);
		source = frag;
		
		TextView tv_disable = (TextView)findViewById(R.id.tv_disable);
		tv_disable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (helper.isEnabled()) {
					helper.disable();
				}
			}
		});
		
		TextView tv_enable = (TextView)findViewById(R.id.tv_enable);
		tv_enable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!helper.isEnabled()) {
					helper.enable();
				}
			}
		});
		
		TextView tv_switch = (TextView)findViewById(R.id.tv_switch);
		tv_switch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!helper.isEnabled()) {
					helper.enable();
				} else {
					helper.disable();
				}
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (helper != null) {
			helper.onResume();
		}
	}

	@Override
	public void onPause() {
		if (helper != null) {
			helper.onPause();
		}
		super.onPause();
	}

	@Override
	public void clearPreso(boolean switchToInline) {
		if (preso != null) {
			preso.dismiss();
			preso = null;
		}
	}

	@Override
	public void showPreso(Display display) {
		preso = buildPreso(display);
		preso.show(getFragmentManager(), "preso");
	}

	private PresentationFragment buildPreso(Display display) {
		MirrorPresentationFragment result = MirrorPresentationFragment.newInstance(this, display);
		if (source != null) {
			source.setMirror(result);
		}
		return (result);
	}
}