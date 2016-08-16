package com.github.shareme.gwsadux.core.baseviews;

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
 * Created by fgrott on 8/16/2016.
 */

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
