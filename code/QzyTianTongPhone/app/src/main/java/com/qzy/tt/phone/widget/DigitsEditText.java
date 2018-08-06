/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.qzy.tt.phone.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * EditText which suppresses IME show up.
 */
@SuppressLint("AppCompatCustomView")
public class DigitsEditText extends EditText {
	public DigitsEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		try {
			// 反射setShowSoftInputOnFocus(false);
			Method setShowSoftInputOnFocus;
			setShowSoftInputOnFocus = DigitsEditText.class.getMethod(
					"setShowSoftInputOnFocus", boolean.class);
			setShowSoftInputOnFocus.setAccessible(true);
			setShowSoftInputOnFocus.invoke(this, false);
		} catch (Exception e) {
			
		}
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		final InputMethodManager imm = ((InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		if (imm != null && imm.isActive(this)) {
			imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		final boolean ret = super.onTouchEvent(event);
		// Must be done after super.onTouchEvent()
		final InputMethodManager imm = ((InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		if (imm != null && imm.isActive(this)) {
			imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
		}
		return ret;
	}
}
