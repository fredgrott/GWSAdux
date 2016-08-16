package com.github.shareme.gwsadux.core.baseviews;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.shareme.gwsadux.core.viewstate.AluxView;
import com.github.shareme.gwsadux.core.viewstate.AluxViewAdapter;
import com.github.shareme.gwsadux.core.viewstate.Scope;
import com.github.shareme.gwsadux.core.viewstate.ViewState;

/**
 * Created by fgrott on 8/16/2016.
 */

public abstract class MyFragment<S extends ViewState> extends Fragment implements AluxView<S> {

  private static final String ALUX_SCOPE = "alux_scope";

  private final AluxViewAdapter<S> adapter = new AluxViewAdapter<>(this);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null)
      adapter.onRestore(savedInstanceState.getBundle(ALUX_SCOPE));
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
  }

  @Override
  public void onResume() {
    super.onResume();
    adapter.onResume();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    adapter.onDestroy();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (!getActivity().isChangingConfigurations())
      adapter.scope().remove();
  }
}
