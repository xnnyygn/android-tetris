package in.xnnyygn.android.tetris;

import java.util.Arrays;
import java.util.List;

public class ListTetrisCallback implements TetrisCallback {

  public static TetrisCallback NIL = new ListTetrisCallback();
  private final List<TetrisCallback> listeners;

  public ListTetrisCallback(TetrisCallback... listeners) {
    this(Arrays.asList(listeners));
  }

  public ListTetrisCallback(List<TetrisCallback> listeners) {
    super();
    this.listeners = listeners;
  }

  @Override
  public void onGameOver() {
    for (TetrisCallback listener : listeners) {
      listener.onGameOver();
    }
  }

  @Override
  public void onPlaceShape() {
    for (TetrisCallback listener : listeners) {
      listener.onPlaceShape();
    }
  }

  @Override
  public void onFixDown(int rows) {
    for (TetrisCallback listener : listeners) {
      listener.onFixDown(rows);
    }
  }

}
