package com.github.shareme.gwsadux.core.viewstate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An utility class that represents a reference to a value that should not be parceled.
 *
 * Created by fgrott on 8/14/2016.
 */
@SuppressWarnings("unused")
public class Transient<T> implements Parcelable {

  private static final Transient EMPTY = new Transient();

  public final T value;

  /**
   * Creates new Transient with null value as the type
   */
  public Transient() {
    value = null;
  }

  /**
   * gets the T type
   * @return value
   */
  public T get() {
    return value;
  }

  /**
   * retruns true if value is not null
   * @return true if value is not null
   */
  public boolean isPresent() {
    return value != null;
  }

  /**
   * returns a new empty Transient
   * @param <T> T type
   * @return an empty transient
   */
  @SuppressWarnings("unchecked")
  public static <T> Transient<T> empty() {
    return EMPTY;
  }

  public static <T> Transient<T> of(T value) {
    return value == null ? EMPTY : new Transient<>(value);
  }

  protected Transient(Parcel ignored, boolean fromParcel) {
    this.value = null;
  }

  private Transient(T value) {
    this.value = value;
  }

  /**
   * NOOP by design
   * @param dest the dest
   * @param flags the flags
   */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  /**
   * describe contents as an int
   * @return zero as int
   */
  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Transient> CREATOR = new Creator<Transient>() {
    @Override
    public Transient createFromParcel(Parcel in) {
      return new Transient(in, true);
    }

    @Override
    public Transient[] newArray(int size) {
      return new Transient[size];
    }
  };

  /**
   * equate the objects
   * @param o o
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Transient<?> that = (Transient<?>) o;

    return !(value != null ? !value.equals(that.value) : that.value != null);
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Transient{" +
            "value=" + value +
            '}';
  }
}
