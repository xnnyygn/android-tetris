package in.xnnyygn.android.tetris;

import java.util.Arrays;

public class Row {

  private boolean[] cells;

  public Row(int columns) {
    cells = new boolean[columns];
  }

  public Row(boolean... cells) {
    this.cells = cells;
  }

  public void set(int x) {
    // 0 <= x < columns
    cells[x] = true;
  }

  public boolean get(int x) {
    return cells[x];
  }

  public boolean isNotFull() {
    for (boolean cell : cells) {
      if (!cell) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "Row [" + Arrays.toString(cells) + "]";
  }

}
