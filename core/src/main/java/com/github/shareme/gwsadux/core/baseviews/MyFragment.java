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

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.shareme.gwsadux.core.viewmodel.AbstractViewModel;
import com.github.shareme.gwsadux.core.viewmodel.IView;
import com.github.shareme.gwsadux.core.viewmodel.ViewModelHelper;
import com.github.shareme.gwsadux.core.viewstate.AluxView;
import com.github.shareme.gwsadux.core.viewstate.AluxViewAdapter;
import com.github.shareme.gwsadux.core.viewstate.Scope;
import com.github.shareme.gwsadux.core.viewstate.ViewState;

/**
 * This is how to implement a Fragment to support GWSAdux, and of course you can
 * extends this class.
 * Created by fgrott on 8/15/2016.
 */

public abstract class MyFragment<S extends ViewState,T extends IView, R extends AbstractViewModel<T>> extends Fragment implements AluxView<S>, IView {

  private static final String ALUX_SCOPE = "alux_scope";

  private final AluxViewAdapter<S> adapter = new AluxViewAdapter<>(this);

  private final ViewModelHelper<T, R> mViewModeHelper = new ViewModelHelper<>();


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null)
      adapter.onRestore(savedInstanceState.getBundle(ALUX_SCOPE));
    mViewModeHelper.onCreate(getActivity(), savedInstanceState, getViewModelClass(), getArguments());

  }

  @Nullable
  public abstract Class<R> getViewModelClass();

  /**
   * Call this after your view is ready - usually on the end of {@link Fragment#onViewCreated(View, Bundle)}
   * @param view view
   */
  protected void setModelView(@NonNull final T view) {
    mViewModeHelper.setView(view);
  }



  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter.resetParts();
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
  public void onScopeCreated(Scope<S> scope) {
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
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(ALUX_SCOPE, adapter.scope().save());
    mViewModeHelper.onSaveInstanceState(outState);

  }

  @Override
  public void onStart() {
    super.onStart();
    mViewModeHelper.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    mViewModeHelper.onStop();
  }


  @Override
  public void onResume() {
    super.onResume();
    adapter.onResume();
  }

  @Override
  public void onDestroyView() {
    mViewModeHelper.onDestroyView(this);

    super.onDestroyView();
    adapter.onDestroy();
  }

  @Override
  public void onDestroy() {
    mViewModeHelper.onDestroy(this);

    super.onDestroy();
    if (!getActivity().isChangingConfigurations())
      adapter.scope().remove();
  }

  /**
   * @see ViewModelHelper#getViewModel()
   */
  @NonNull
  @SuppressWarnings("unused")
  public R getViewModel() {
    return mViewModeHelper.getViewModel();
  }


}
