package in.xnnyygn.android.tetris.test;

import in.xnnyygn.android.tetris.Block;
import in.xnnyygn.android.tetris.Rectangle;
import in.xnnyygn.android.tetris.Shape;
import junit.framework.TestCase;

public class ShapeTest extends TestCase {

	public void testGetRect0() {
		Shape shape = new Shape(new Block(0, 0), new Block(0, 1), new Block(0,
				2), new Block(0, 3));
		Rectangle rect = shape.getRect();
		assertEquals("top must be 0", 0, rect.top);
		assertEquals("left must be 0", 0, rect.left);
		assertEquals("right must be 0", 0, rect.right);
		assertEquals("bottom must be 3", 3, rect.bottom);
	}

	public void testGetRect1() {
		Shape shape = new Shape(new Block(0, 0), new Block(1, 0), new Block(2,
				0), new Block(3, 0));
		Rectangle rect = shape.getRect();
		assertEquals("top must be 0", 0, rect.top);
		assertEquals("left must be 0", 0, rect.left);
		assertEquals("right must be 3", 3, rect.right);
		assertEquals("bottom must be 0", 0, rect.bottom);
	}

	public void testGetRect2() {
		Shape shape = new Shape(new Block(0, 0), new Block(1, 0), new Block(1,
				1), new Block(2, 1));
		Rectangle rect = shape.getRect();
		assertEquals("top must be 0", 0, rect.top);
		assertEquals("left must be 0", 0, rect.left);
		assertEquals("right must be 2", 2, rect.right);
		assertEquals("bottom must be 1", 1, rect.bottom);
	}
}
