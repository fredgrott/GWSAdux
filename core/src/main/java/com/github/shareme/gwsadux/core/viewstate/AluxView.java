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
import android.view.View;

/**
 * This is the recommended interface for alux enabled views.
 * Created by fgrott on 8/14/2016.
 */
@SuppressWarnings("unused")
public interface AluxView<S extends ViewState> {

  interface FieldUpdater<T> {
    void call(T value);
  }

  S state();
  Scope<S> scope();

  /**
   * Incorporates updating logic for a visual part of an activity,
   * allowing to update only parts of the activity that have updated state.
   * <p>
   * Given we have a deal with immutable state, we only compare
   * references to values instead of calling equals() to detect
   * changed data.
   */
  <T> void part(String name, T newValue, FieldUpdater<T> updater);

  /**
   * Resets "updated" flag for all parts.
   * First successive {@link #part(String, Object, FieldUpdater)} calls for each part
   * will cause updater to be fired.
   * <p>
   * Call this method when view has been recreated, for example during {@link android.app.Fragment#onViewCreated(View, Bundle)}.
   */
  void resetParts();

  /**
   * onScopeCreated is called when scope and state was created for the first time.
   * This is a good place to initialize background tasks.
   */
  void onScopeCreated(Scope<S> scope);

  /**
   * Creates an initial state.
   * Must be implemented by a specific View implementation.
   */
  S initial();

  /**
   * Updated view from a given state.
   * Must be implemented by a specific View implementation.
   */
  void update(S state);
}
