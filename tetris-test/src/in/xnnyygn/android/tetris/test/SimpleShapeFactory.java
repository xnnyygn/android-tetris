package in.xnnyygn.android.tetris.test;

import in.xnnyygn.android.tetris.Shape;
import in.xnnyygn.android.tetris.ShapeFactory;

public class SimpleShapeFactory implements ShapeFactory {

  private final Shape shape;

  public SimpleShapeFactory(Shape shape) {
    super();
    this.shape = shape;
  }

  @Override
  public Shape randomShape() {
    return shape;
  }

}
