package in.xnnyygn.android.tetris;

public class Rectangle {

	public int left;
	public int top;
	public int right;
	public int bottom;

	public Rectangle(int left, int top, int right, int bottom) {
		super();
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	@Override
	public String toString() {
		return "Rectangle [left=" + left + ", top=" + top + ", right=" + right
				+ ", bottom=" + bottom + "]";
	}

}
