/*
  The MIT License (MIT)

Copyright (c) 2015 Konstantin Mikheev sirstripy-at-gmail-com
Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
package com.github.shareme.gwsadux.nongdb.mvc;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.shareme.gwsadux.core.viewstate.AluxView;
import com.github.shareme.gwsadux.core.viewstate.AluxViewAdapter;
import com.github.shareme.gwsadux.core.viewstate.Scope;
import com.github.shareme.gwsadux.core.viewstate.ViewState;

/**
 * Sample implementing GWSAdux with a MVC App architecture pattern.
 * Every view you want updated must implement GWSAdux this way.
 * Created by fgrott on 8/17/2016.
 */
@SuppressWarnings("unused")
public abstract class MyView<S extends ViewState> extends FrameLayout implements AluxView<S> {

  private static final String ALUX_SCOPE = "alux_scope";
  private static final String SUPER = "super";

  private final AluxViewAdapter<S> adapter = new AluxViewAdapter<>(this);

  public MyView(Context context) {
    super(context);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    if (isInEditMode())
      return;

    adapter.onResume();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (!getActivity().isChangingConfigurations())
      adapter.onDestroy();
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    Bundle bundle = (Bundle) state;
    super.onRestoreInstanceState(bundle.getParcelable(SUPER));
    adapter.onRestore(bundle.getBundle(ALUX_SCOPE));
  }

  @Override
  protected Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();
    bundle.putParcelable(ALUX_SCOPE, adapter.scope().save());
    bundle.putParcelable(SUPER, super.onSaveInstanceState());
    return bundle;
  }

  public Activity getActivity() {
    Context context = getContext();
    while (!(context instanceof Activity) && context instanceof ContextWrapper)
      context = ((ContextWrapper) context).getBaseContext();
    if (!(context instanceof Activity))
      throw new IllegalStateException("Expected an activity context, got " + context.getClass().getSimpleName());
    return (Activity) context;
  }

  @Override
  public Scope<S> scope() {
    return adapter.scope();
  }

  @Override
  public S state() {
    return adapter.scope().state();
  }

  @Override
  public <T> void part(String name, T newValue, AluxView.FieldUpdater<T> updater) {
    adapter.part(name, newValue, updater);
  }

  @Override
  public void resetParts() {
    adapter.resetParts();
  }

  @Override
  public void onScopeCreated(Scope<S> scope) {
  }
}
