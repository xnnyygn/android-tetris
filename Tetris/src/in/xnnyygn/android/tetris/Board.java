package in.xnnyygn.android.tetris;

public class Board {

  private final int columns;
  private final int rows;

  private Row[] blockRows;

  public Board(int columns, int rows) {
    this(columns, rows, newBlockRows(columns, rows));
  }

  private static Row[] newBlockRows(int columns, int rows) {
    Row[] blockRows = new Row[rows];
    for (int y = 0; y < rows; y++) {
      blockRows[y] = new Row(columns);
    }
    return blockRows;
  }

  public Board(int columns, int rows, Row[] blockRows) {
    this.columns = columns;
    this.rows = rows;
    this.blockRows = blockRows;
  }

  public boolean inBound(Block block) {
    return inBound(block.x, block.y);
  }

  public boolean inBound(int x, int y) {
    return x >= 0 && x < columns && y >= 0 && y < rows;
  }

  public int getColumns() {
    return columns;
  }

  public int getRows() {
    return rows;
  }

  public void fixBlocks(Shape shape, int offsetX, int offsetY,
      TetrisCallback tetrisCallback) {
    for (Block block : shape) {
      int x = block.x + offsetX;
      int y = block.y + offsetY;
      blockRows[y].set(x);
    }
    Rectangle rect = shape.getRect();
    int top = rect.top + offsetY;
    int bottom = rect.bottom + offsetY;
    int[] notFullRows = new int[rect.bottom - rect.top + 1];
    int notFullRowCnt = 0;
    for (int y = bottom; y >= top; y--) {
      if (blockRows[y].isNotFull()) {
        notFullRows[notFullRowCnt++] = y;
      }
    }
    if (notFullRowCnt == notFullRows.length) // no full row
      return;
    for (int y = bottom, i = 0; y > bottom - notFullRowCnt; y--) {
      blockRows[y] = blockRows[notFullRows[i++]];
    }
    int downOffsetY = notFullRows.length - notFullRowCnt;
    for (int y = top - 1; y >= 0; y--) {
      blockRows[y + downOffsetY] = blockRows[y];
    }
    for (int y = 0; y < downOffsetY; y++) {
      blockRows[y] = new Row(columns);
    }
    tetrisCallback.onFixDown(downOffsetY);
  }

  public boolean collision(Shape shape, int offsetX, int offsetY) {
    for (Block block : shape) {
      int x = block.x + offsetX;
      int y = block.y + offsetY;
      if (x < 0 || x >= columns || y >= rows || (y >= 0 && hasBlock(x, y))) {
        return true;
      }
    }
    return false;
  }

  public boolean hasBlock(int x, int y) {
    return blockRows[y].get(x);
  }

  @Override
  public String toString() {
    return "Board [columns=" + columns + ", rows=" + rows + "]";
  }

}
