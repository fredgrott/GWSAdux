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
package com.github.shareme.gwsadux.nongdb.mvvm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.shareme.gwsadux.core.viewstate.AluxView;
import com.github.shareme.gwsadux.core.viewstate.AluxViewAdapter;
import com.github.shareme.gwsadux.core.viewstate.Scope;
import com.github.shareme.gwsadux.core.viewstate.ViewState;

/**
 * Sample Activity implementing the MVVM App Architecture pattern
 * Created by fgrott on 8/17/2016.
 */
@SuppressWarnings("unused")
public abstract class MyActivity<S extends ViewState> extends AppCompatActivity implements AluxView<S> {

  private static final String ALUX_SCOPE = "alux_scope";

  private final AluxViewAdapter<S> adapter = new AluxViewAdapter<>(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null)
      adapter.onRestore(savedInstanceState.getBundle(ALUX_SCOPE));
  }

  public void post(Runnable runnable) {
    getWindow()
            .getDecorView()
            .post(runnable);
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
  public <T> void part(String name, T newValue, FieldUpdater<T> updater) {
    adapter.part(name, newValue, updater);
  }

  @Override
  public void resetParts() {
    adapter.resetParts();
  }

  @Override
  public void onScopeCreated(Scope<S> scope) {
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(ALUX_SCOPE, adapter.scope().save());
  }

  @Override
  protected void onResume() {
    super.onResume();
    adapter.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    adapter.onDestroy();
    if (!isChangingConfigurations())
      adapter.scope().remove();
  }
}
