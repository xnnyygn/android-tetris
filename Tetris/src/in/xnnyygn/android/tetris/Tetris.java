package in.xnnyygn.android.tetris;

import android.graphics.Canvas;

public class Tetris {

  private Board board;

  private ShapeFactory shapeFactory = new DefaultShapeFactory();
  private Shape shape;

  private int shapeX;
  private int shapeY;

  private TetrisCallback tetrisCallback = ListTetrisCallback.NIL;

  public Tetris(int columns, int rows) {
    board = new Board(columns, rows);
  }

  public void clearBoard() {
    board = new Board(board.getColumns(), board.getRows());
  }

  public boolean placeShape() {
    tetrisCallback.onPlaceShape();
    shape = shapeFactory.randomShape();
    Rectangle rect = shape.getRect();
    shapeX = (board.getColumns() - (rect.right - rect.left + 1)) >> 1;
    shapeY = -rect.bottom;
    return !board.collision(shape, shapeX, shapeY);
  }

  public void draw(DrawCallback callback, Canvas canvas) {

    // draw board
    for (int x = 0; x < board.getColumns(); x++) {
      for (int y = 0; y < board.getRows(); y++) {
        if (board.hasBlock(x, y)) {
          callback.drawBlock(canvas, x, y);
        }
      }
    }

    // draw shape
    for (Block block : shape) {
      int x = block.x + shapeX;
      int y = block.y + shapeY;
      if (board.inBound(x, y)) {
        callback.drawBlock(canvas, x, y);
      }
    }
  }

  public boolean translate(int offset) {
    if (offset == 0) return false;
    int step = offset > 0 ? 1 : -1;
    int i = 0;
    int x = shapeX + step;
    while (i != offset && !board.collision(shape, x, shapeY)) {
      x += step;
      i += step;
    }
    int newX = x - step;
    if (newX == shapeX) return false;
    shapeX = newX;
    return true;
  }

  public boolean down() {
    if (!board.collision(shape, shapeX, shapeY + 1)) {
      shapeY++;
      return true;
    }
    // shape still out of bound
    if (shape.getRect().top + shapeY < 0) {
      tetrisCallback.onGameOver();
      return false;
    }
    board.fixBlocks(shape, shapeX, shapeY, tetrisCallback);
    // failed to generate
    if (!placeShape()) {
      tetrisCallback.onGameOver();
      return false;
    }
    return true;
  }

  public boolean rotate() {
    Rectangle originalShapeRect = shape.getRect();
    Shape newShape = shape.getRotateTarget();
    if (!board.collision(newShape, shapeX, shapeY)) {
      // rotate OK
      shape = newShape;
      return true;
    }
    Rectangle newShapeRect = newShape.getRect();
    // try left
    int leftDelta = newShapeRect.left - originalShapeRect.left;
    int rightDelta = newShapeRect.right - originalShapeRect.right;
    // try left and right
    return ((leftDelta < 0 && tryTranslateToFirstNotCollisionX(-leftDelta, 1,
        newShape)) || (rightDelta > 0 && tryTranslateToFirstNotCollisionX(
        rightDelta, -1, newShape)));
  }

  private boolean tryTranslateToFirstNotCollisionX(int maxOffset, int step,
      Shape shape) {
    for (int x = shapeX + step, i = 0; i < maxOffset; i++, x += step) {
      if (!board.collision(shape, x, shapeY)) {
        this.shape = shape;
        shapeX = x;
        return true;
      }
    }
    return false;
  }

  public int getShapeX() {
    return shapeX;
  }

  public void setShapeX(int shapeX) {
    this.shapeX = shapeX;
  }

  public int getShapeY() {
    return shapeY;
  }

  public void setShapeY(int shapeY) {
    this.shapeY = shapeY;
  }

  public void setShapeFactory(ShapeFactory shapeFactory) {
    this.shapeFactory = shapeFactory;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Shape getShape() {
    return shape;
  }

  public void setTetrisCallback(TetrisCallback tetrisCallback) {
    this.tetrisCallback = tetrisCallback;
  }

}
