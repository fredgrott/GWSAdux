package com.github.shareme.gwsadux.core.baseviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.shareme.gwsadux.core.viewstate.AluxView;
import com.github.shareme.gwsadux.core.viewstate.AluxViewAdapter;
import com.github.shareme.gwsadux.core.viewstate.Scope;
import com.github.shareme.gwsadux.core.viewstate.ViewState;

/**
 * Created by fgrott on 8/16/2016.
 */

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
