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
package com.github.shareme.gwsadux.core.viewstate;

import android.os.Bundle;

import java.util.HashMap;

/**
 * AluxViewAdapter incorporates common view logic.
 * This should be a single place where view state is stored.
 *
 * Created by fgrott on 8/14/2016.
 */
@SuppressWarnings("unuseed")
public class AluxViewAdapter<S extends ViewState> {

  private final AluxView<S> view;
  private final StateCallback<S> callback = new StateCallback<S>() {
    @Override
    public void call(S state) {
      view.update(state);
    }
  };
  private Scope<S> scope;

  private HashMap<String, Object> updated = new HashMap<>();

  /**
   *
   * @param view the view
   */
  public AluxViewAdapter(AluxView<S> view) {
    this.view = view;
  }

  /**
   * Restores from a bundle
   * @param bundle the bundle
   */
  public void onRestore(Bundle bundle) {
    if (scope != null)
      throw new IllegalStateException("onRestore() must be called before scope() and before onResume()");

    scope = new Scope<>(bundle);
    scope.register(callback);
  }

  /**
   * Returns the scope and if null initializes the view
   * @return scope
   */
  public Scope<S> scope() {
    if (scope == null) {
      scope = new Scope<>(view.initial());
      scope.register(callback);
      view.onScopeCreated(scope);
    }
    return scope;
  }

  /**
   * Updates fields of Alux enabled views
   * @param name the name
   * @param newValue the newValue
   * @param updater the updater
   * @param <T> T type
   */
  public <T> void part(String name, T newValue, AluxView.FieldUpdater<T> updater) {
    if (!updated.containsKey(name) || updated.get(name) != newValue) {
      updater.call(newValue);
      updated.put(name, newValue);
    }
  }

  /**
   * Clear parts
   */
  public void resetParts() {
    updated.clear();
  }

  /**
   * Update view with scope state
   */
  public void onResume() {
    view.update(scope().state());
  }

  /**
   * Unregister the scope callback upon onDestroy lifecycle events
   */
  public void onDestroy() {
    scope.unregister(callback);
  }
}
