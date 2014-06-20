package in.xnnyygn.android.tetris;

import java.util.Random;

public class DefaultShapeFactory implements ShapeFactory {

  private static final Shape SHAPE00 = new Shape(new Block(0, 0), new Block(0,
      1), new Block(1, 0), new Block(1, 1));

  private static final Shape SHAPE10 = new Shape(new Block(0, 0), new Block(0,
      1), new Block(0, 2), new Block(0, 3));
  private static final Shape SHAPE11 = new Shape(new Block(0, 1), new Block(1,
      1), new Block(2, 1), new Block(3, 1));

  private static final Shape SHAPE20 = new Shape(new Block(1, 0), new Block(0,
      1), new Block(1, 1), new Block(0, 2));
  private static final Shape SHAPE21 = new Shape(new Block(0, 1), new Block(1,
      1), new Block(1, 2), new Block(2, 2));

  private static final Shape SHAPE30 = new Shape(new Block(1, 0), new Block(0,
      1), new Block(1, 1), new Block(2, 1));
  private static final Shape SHAPE31 = new Shape(new Block(1, 0), new Block(1,
      1), new Block(2, 1), new Block(1, 2));
  private static final Shape SHAPE32 = new Shape(new Block(1, 0), new Block(1,
      1), new Block(0, 1), new Block(1, 2));
  private static final Shape SHAPE33 = new Shape(new Block(0, 1), new Block(1,
      1), new Block(2, 1), new Block(1, 2));

  private static final Shape SHAPE40 = new Shape(new Block(0, 0), new Block(1,
      0), new Block(1, 1), new Block(1, 2));
  private static final Shape SHAPE41 = new Shape(new Block(0, 1), new Block(1,
      1), new Block(2, 0), new Block(2, 1));
  private static final Shape SHAPE42 = new Shape(new Block(0, 0), new Block(0,
      1), new Block(0, 2), new Block(1, 2));
  private static final Shape SHAPE43 = new Shape(new Block(0, 0), new Block(1,
      0), new Block(2, 0), new Block(0, 1));

  private static final Shape SHAPE50 = new Shape(new Block(0, 0), new Block(1,
      0), new Block(0, 1), new Block(0, 2));
  private static final Shape SHAPE51 = new Shape(new Block(0, 0), new Block(0,
      1), new Block(1, 1), new Block(2, 1));
  private static final Shape SHAPE52 = new Shape(new Block(1, 0), new Block(1,
      1), new Block(0, 2), new Block(1, 2));
  private static final Shape SHAPE53 = new Shape(new Block(0, 0), new Block(1,
      0), new Block(2, 0), new Block(2, 1));

  private Random random = new Random(System.currentTimeMillis());
  private Shape[] shapes = {SHAPE00, SHAPE10, SHAPE11, SHAPE50, SHAPE51,
      SHAPE52, SHAPE53, SHAPE30, SHAPE31, SHAPE32, SHAPE33, SHAPE20, SHAPE21,
      SHAPE40, SHAPE41, SHAPE42, SHAPE43};

  public DefaultShapeFactory() {
    super();

    bindShapeRotateTargets(SHAPE10, SHAPE11);
    bindShapeRotateTargets(SHAPE20, SHAPE21);
    bindShapeRotateTargets(SHAPE30, SHAPE31, SHAPE32, SHAPE33);
    bindShapeRotateTargets(SHAPE40, SHAPE41, SHAPE42, SHAPE43);
    bindShapeRotateTargets(SHAPE50, SHAPE51, SHAPE52, SHAPE53);
  }

  public static void bindShapeRotateTargets(Shape... shapes) {
    if (shapes.length == 0) return;
    int lastIndex = shapes.length - 1;
    for (int i = 0; i < lastIndex; i++) {
      shapes[i].setRotateTarget(shapes[i + 1]);
    }
    shapes[lastIndex].setRotateTarget(shapes[0]);
  }

  public Shape randomShape() {
    return shapes[random.nextInt(shapes.length)];
  }

}
