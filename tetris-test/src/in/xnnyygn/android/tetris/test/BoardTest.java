package in.xnnyygn.android.tetris.test;

import in.xnnyygn.android.tetris.Block;
import in.xnnyygn.android.tetris.Board;
import in.xnnyygn.android.tetris.ListTetrisCallback;
import in.xnnyygn.android.tetris.Row;
import in.xnnyygn.android.tetris.Shape;
import junit.framework.TestCase;

public class BoardTest extends TestCase {

  public void testFixBlocks() {
    Board board =
        new Board(4, 5, new Row[] {new Row(4), new Row(4),
            new Row(true, false, true, false),
            new Row(true, true, true, false), new Row(true, true, true, false)});
    Shape shape =
        new Shape(new Block(0, 0), new Block(1, 0), new Block(1, 1), new Block(
            1, 2));
    board.fixBlocks(shape, 2, 1, ListTetrisCallback.NIL);
    assertEquals(false, board.hasBlock(0, 2));
    assertEquals(false, board.hasBlock(1, 2));
    assertEquals(true, board.hasBlock(2, 2));
    assertEquals(true, board.hasBlock(3, 2));
    assertEquals(true, board.hasBlock(0, 3));
    assertEquals(false, board.hasBlock(1, 3));
    assertEquals(true, board.hasBlock(2, 3));
    assertEquals(true, board.hasBlock(3, 3));
  }
}
