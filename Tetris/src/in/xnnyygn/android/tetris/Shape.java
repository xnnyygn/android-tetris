package in.xnnyygn.android.tetris;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Shape implements Iterable<Block> {

  private final List<Block> blocks;
  private Shape rotateTarget = this;

  public Shape(Block... blocks) {
    this(Arrays.asList(blocks));
  }

  public Shape(List<Block> blocks) {
    super();
    this.blocks = blocks;
  }

  @Override
  public Iterator<Block> iterator() {
    return blocks.iterator();
  }

  public Rectangle getRect() {
    if (blocks.isEmpty()) {
      throw new IllegalStateException("no block");
    }
    Iterator<Block> iterator = blocks.iterator();
    Block firstBlock = iterator.next();
    int left = firstBlock.x, top = firstBlock.y;
    int right = left, bottom = top;
    while (iterator.hasNext()) {
      Block block = iterator.next();
      int x = block.x;
      if (x < left) {
        left = x;
      }
      if (x > right) {
        right = x;
      }

      int y = block.y;
      if (y < top) {
        top = y;
      }
      if (y > bottom) {
        bottom = y;
      }
    }
    return new Rectangle(left, top, right, bottom);
  }

  public Shape getRotateTarget() {
    return rotateTarget;
  }

  public void setRotateTarget(Shape shape) {
    this.rotateTarget = shape;
  }

  @Override
  public String toString() {
    return "Shape [blocks=" + blocks + "]";
  }

}
