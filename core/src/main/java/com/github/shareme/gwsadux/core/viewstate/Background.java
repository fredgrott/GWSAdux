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

import android.os.Parcelable;

/**
 * Represents a background task.
 *
 * The typical use case is to extend this class to keep arguments in it and to implement
 * {@link Background#execute(BackgroundCallback)} to execute the background task itself.
 *
 * All subclasses of {@link Background} *must* be immutable, ie autovalue tools
 *
 * Created by fgrott on 8/14/2016.
 */
@SuppressWarnings("unused")
public interface Background<S extends ViewState> extends Parcelable {
  /**
   * Executes the background task.
   *
   * @param callback a method that should be called on the background task completion.
   *                 It must provide a function that will be applied to state.
   *                 The current implementation implies that the callback must be called
   *                 on the main thread.
   */
  Cancellable execute(BackgroundCallback<S> callback);
}
