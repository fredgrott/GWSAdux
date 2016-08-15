/*
  Copyright 2014 Inloop, s.r.o.
  Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package com.github.shareme.gwsadux.core.viewmodel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

/**
 * Create and keep this class inside your Activity. Store it
 * in {@link android.support.v7.app.AppCompatActivity#onRetainCustomNonConfigurationInstance()
 * and restore in {@link android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)} before
 * calling the super implemenentation.

 * Created by fgrott on 8/14/2016.
 */

public class ViewModelProviderImpl {

  private final HashMap<String, AbstractViewModel<? extends IView>> mViewModelCache;

  public static ViewModelProviderImpl newInstance(@NonNull final AppCompatActivity activity) {
    if (activity.getLastCustomNonConfigurationInstance() == null) {
      return new ViewModelProviderImpl();
    } else {
      return  (ViewModelProviderImpl) activity.getLastCustomNonConfigurationInstance();
    }
  }



  private ViewModelProviderImpl() {
    mViewModelCache = new HashMap<>();
  }

  public synchronized void remove(String modeIdentifier) {
    mViewModelCache.remove(modeIdentifier);
  }

  public synchronized void removeAllViewModels() {
    mViewModelCache.clear();
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public synchronized <T extends IView> ViewModelWrapper<T> getViewModel(final String modelIdentifier,
                                                                         final @NonNull Class<? extends AbstractViewModel<T>> viewModelClass) {
    AbstractViewModel<T> instance = (AbstractViewModel<T>) mViewModelCache.get(modelIdentifier);
    if (instance != null) {
      return new ViewModelWrapper<>(instance, false);
    }

    try {
      instance = viewModelClass.newInstance();
      instance.setUniqueIdentifier(modelIdentifier);
      mViewModelCache.put(modelIdentifier, instance);
      return new ViewModelWrapper<>(instance, true);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static class ViewModelWrapper<T extends IView> {
    @NonNull
    public final AbstractViewModel<T> viewModel;
    public final boolean wasCreated;

    private ViewModelWrapper(@NonNull AbstractViewModel<T> mViewModel, boolean mWasCreated) {
      this.viewModel = mViewModel;
      this.wasCreated = mWasCreated;
    }
  }

}
