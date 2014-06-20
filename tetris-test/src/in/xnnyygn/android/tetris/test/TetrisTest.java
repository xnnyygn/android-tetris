package in.xnnyygn.android.tetris.test;

import in.xnnyygn.android.tetris.Block;
import in.xnnyygn.android.tetris.Board;
import in.xnnyygn.android.tetris.DefaultShapeFactory;
import in.xnnyygn.android.tetris.Row;
import in.xnnyygn.android.tetris.Shape;
import in.xnnyygn.android.tetris.Tetris;
import junit.framework.TestCase;

public class TetrisTest extends TestCase {

  private static final int COLUMNS = 4;
  private static final int ROWS = 5;
  private static final Shape SHAPE0 = new Shape(new Block(0, 0),
      new Block(0, 1), new Block(0, 2), new Block(0, 3));
  private static final Shape SHAPE00 = new Shape(new Block(1, 0), new Block(1,
      1), new Block(1, 2), new Block(1, 3));
  private static final Shape SHAPE01 = new Shape(new Block(0, 1), new Block(1,
      1), new Block(2, 1), new Block(3, 1));
  private static final Shape SHAPE10 = new Shape(new Block(0, 0), new Block(1,
      0), new Block(2, 0), new Block(2, 1));

  @Override
  public void setUp() throws Exception {
    super.setUp();

    DefaultShapeFactory.bindShapeRotateTargets(SHAPE00, SHAPE01);
  }

  public void testTranslateToLeftWithoutObstacle() {
    testTranslateWithoutObstacle(0, 0, 0, false);
    testTranslateWithoutObstacle(0, -1, 0, false);
    testTranslateWithoutObstacle(1, -1, 0, true);
    testTranslateWithoutObstacle(1, -2, 0, true);
    testTranslateWithoutObstacle(2, -2, 0, true);
    testTranslateWithoutObstacle(3, -2, 1, true);
  }

  private void testTranslateWithoutObstacle(int startShapeX, int offset,
      int expectedEndShapeX, boolean expectedTranslated) {
    testTranslate(null, startShapeX, offset, expectedEndShapeX,
        expectedTranslated);
  }

  private void testTranslate(Board board, int startShapeX, int offset,
      int expectedEndShapeX, boolean expectedTranslated) {
    Tetris tetris = new Tetris(COLUMNS, ROWS);
    if (board != null) tetris.setBoard(board);
    tetris.setShapeFactory(new SimpleShapeFactory(SHAPE0));
    tetris.placeShape();
    tetris.setShapeX(startShapeX);
    tetris.setShapeY(0);

    assertEquals(expectedTranslated, tetris.translate(offset));
    assertEquals(expectedEndShapeX, tetris.getShapeX());
  }

  public void testTranslateToRightWithoutObstacle() {
    testTranslateWithoutObstacle(0, 0, 0, false);
    testTranslateWithoutObstacle(0, 1, 1, true);
    testTranslateWithoutObstacle(1, COLUMNS, COLUMNS - 1, true);
    testTranslateWithoutObstacle(COLUMNS - 1, 1, COLUMNS - 1, false);
    testTranslateWithoutObstacle(COLUMNS - 2, 1, COLUMNS - 1, true);
  }

  public void testTranslateToLeftWithObstacle() {
    testTranslateWithObstacle(0, -1, 0, false);
    testTranslateWithObstacle(1, -1, 0, true);
    testTranslateWithObstacle(3, -1, 3, false);
    testTranslateWithObstacle(3, -2, 3, false);
  }

  private void testTranslateWithObstacle(int startShapeX, int offset,
      int expectedEndShapeX, boolean expectedTranslated) {
    Row row = new Row(false, false, true, false);
    Board board =
        new Board(COLUMNS, ROWS, new Row[] {new Row(COLUMNS), row, row, row,
            row});
    testTranslate(board, startShapeX, offset, expectedEndShapeX,
        expectedTranslated);
  }

  public void testTranslateToRightWithObstacle() {
    testTranslateWithObstacle(0, 1, 1, true);
    testTranslateWithObstacle(1, 1, 1, false);
    testTranslateWithObstacle(1, 2, 1, false);
    testTranslateWithObstacle(3, 1, 3, false);
  }

  public void testPlaceShape() {
    testPlaceShape(SHAPE0, 1, -3);
    testPlaceShape(SHAPE10, 0, -1);
  }

  private void testPlaceShape(Shape shape, int expectedShapeX,
      int expectedShapeY) {
    Tetris tetris = new Tetris(COLUMNS, ROWS);
    tetris.setShapeFactory(new SimpleShapeFactory(shape));
    tetris.placeShape();
    assertEquals(expectedShapeX, tetris.getShapeX());
    assertEquals(expectedShapeY, tetris.getShapeY());
  }

  public void testRotateDirectly() {
    Tetris tetris = new Tetris(COLUMNS, ROWS);
    tetris.setShapeFactory(new SimpleShapeFactory(SHAPE00));
    tetris.placeShape();
    tetris.setShapeX(0);
    tetris.rotate();
    assertEquals(SHAPE00.getRotateTarget(), tetris.getShape());
    assertEquals(0, tetris.getShapeX());
  }

  public void testRotateTranslateToRight() {
    Tetris tetris = new Tetris(COLUMNS, ROWS);
    tetris.setShapeFactory(new SimpleShapeFactory(SHAPE00));
    tetris.placeShape();
    tetris.translate(-COLUMNS); // translate to left most
    assertEquals(-1, tetris.getShapeX());
    tetris.rotate();
    assertEquals(SHAPE00.getRotateTarget(), tetris.getShape());
    assertEquals(0, tetris.getShapeX());
  }

  public void testRotateTranslateToLeft() {
    Tetris tetris = new Tetris(COLUMNS, ROWS);
    tetris.setShapeFactory(new SimpleShapeFactory(SHAPE00));
    tetris.placeShape();
    tetris.translate(COLUMNS); // translate to right most
    assertEquals(2, tetris.getShapeX());
    tetris.rotate();
    assertEquals(SHAPE00.getRotateTarget(), tetris.getShape());
    assertEquals(0, tetris.getShapeX());
  }

}
