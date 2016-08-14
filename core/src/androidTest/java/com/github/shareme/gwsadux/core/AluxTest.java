package com.github.shareme.gwsadux.core;

import android.os.Handler;
import android.os.Looper;

import com.github.shareme.gwsadux.core.viewstate.Alux;
import com.github.shareme.gwsadux.core.viewstate.StateCallback;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static android.util.Log.i;
import static com.github.shareme.gwsadux.core.racer.RacerThread.race;
import static com.github.shareme.gwsadux.core.racer.Util.run;
import static com.github.shareme.gwsadux.core.racer.Util.waitFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by fgrott on 8/14/2016.
 */

public class AluxTest {

  public static final int TEST_RETRIES = 10000;
  public static final int TIME_LIMIT = 10000;
  public static final int THREADS_NUMBER = 10;

  @Test
  public void testLifecycle() throws Exception {

    AtomicReference<TestState> calledBack = new AtomicReference<>();
    StateCallback callback = state -> {
      assertEquals(Looper.myLooper(), Looper.getMainLooper());
      assertTrue(calledBack.compareAndSet(null, (TestState) state));
    };

    String key = UUID.randomUUID().toString();
    Alux.INSTANCE.create(key, new TestState());
    assertNotNull(Alux.INSTANCE.state(key));
    Alux.INSTANCE.register(key, callback);
    Alux.INSTANCE.apply(key, state -> new TestState());
    assertTrue(waitFor(100, () -> calledBack.get() != null));

    calledBack.set(null);
    AtomicBoolean mainThreadTestDone = new AtomicBoolean();
    new Handler(Looper.getMainLooper()).post(() -> {
      Alux.INSTANCE.apply(key, state -> new TestState());
      assertNotNull(calledBack.get());
      mainThreadTestDone.set(true);
    });
    assertTrue(waitFor(100, mainThreadTestDone::get));

    Alux.INSTANCE.unregister(key, callback);
    Alux.INSTANCE.remove(key);
    assertNull(Alux.INSTANCE.state(key));
  }

  @Test
  public void testRace() throws Exception {

    AtomicLong calls = new AtomicLong();
    AtomicLong funcs = new AtomicLong();

    run(TEST_RETRIES, TIME_LIMIT, iteration -> {

      List<List<Runnable>> threads = new ArrayList<>();

      for (int i = 0; i < THREADS_NUMBER; i++) {
        String key = UUID.randomUUID().toString();
        threads.add(Arrays.asList(
                () -> Alux.INSTANCE.create(key, new TestState()),
                () -> assertNotNull(Alux.INSTANCE.state(key)),
                () -> {
                  calls.incrementAndGet();
                  Alux.INSTANCE.apply(key, state -> {
                    funcs.incrementAndGet();
                    return new TestState();
                  });
                },
                () -> Alux.INSTANCE.remove(key),
                () -> assertNull(Alux.INSTANCE.state(key))));
      }

      race(threads);
    });

    i(getClass().getSimpleName(), "calls: " + calls.get() + " funcs: " + funcs.get() +
            " percent: " + String.format("%.2f", (double) (funcs.get() - calls.get()) / calls.get())); // 4% are normally retries due to STM
    assertNotEquals(calls.get(), funcs.get());
  }

}
