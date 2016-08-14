package com.github.shareme.gwsadux.core.viewstate;

import android.os.Parcelable;

/**
 * This is a base class for activity/fragment state.
 *
 * An activity/fragment normally should have one {@link ViewState} which contains
 * all data that is used to display the activity/fragment.
 *
 * All {@link ViewState} subclasses *must* be immutable, ie autovalue tools.
 *
 * Created by fgrott on 8/14/2016.
 */

public interface ViewState extends Parcelable {
}
