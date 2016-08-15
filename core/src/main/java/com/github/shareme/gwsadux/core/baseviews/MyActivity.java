/*
  Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.
 */
package com.github.shareme.gwsadux.core.baseviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.shareme.gwsadux.core.viewmodel.ViewModelProvider;
import com.github.shareme.gwsadux.core.viewmodel.ViewModelProviderImpl;
import com.github.shareme.gwsadux.core.viewstate.AluxView;
import com.github.shareme.gwsadux.core.viewstate.AluxViewAdapter;
import com.github.shareme.gwsadux.core.viewstate.Scope;
import com.github.shareme.gwsadux.core.viewstate.ViewState;

/**
 * This is how you will set up your activity to implement GWSAdux, and of course
 * you can just extend this class.
 * Created by fgrott on 8/15/2016.
 */
@SuppressWarnings("unused")
public abstract class MyActivity<S extends ViewState> extends AppCompatActivity implements AluxView<S>, ViewModelProvider {

  private static final String ALUX_SCOPE = "alux_scope";

  private final AluxViewAdapter<S> adapter = new AluxViewAdapter<>(this);

  private ViewModelProviderImpl mViewModelProvider;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    //This code must be execute prior to super.onCreate()
    mViewModelProvider = ViewModelProviderImpl.newInstance(this);

    super.onCreate(savedInstanceState);
    if (savedInstanceState != null)
      adapter.onRestore(savedInstanceState.getBundle(ALUX_SCOPE));
  }

  @Override
  @Nullable
  public Object onRetainCustomNonConfigurationInstance() {
    return mViewModelProvider;
  }


  @Override
  public void onStop() {
    super.onStop();
    if (isFinishing()) {
      mViewModelProvider.removeAllViewModels();
    }
  }


  @Override
  public ViewModelProviderImpl getViewModelProvider() {
    return mViewModelProvider;
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
