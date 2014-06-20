package in.xnnyygn.android.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TetrisView extends View implements Runnable, DrawCallback,
    TetrisCallback {

  private static final long INTERVAL = 1000;
  private static final int BORDER_WIDTH = 1;
  private static final int BLOCK_WIDTH = 40 + BORDER_WIDTH * 2;
  private static final int BLOCK_HEIGHT = BLOCK_WIDTH;
  private Paint borderPaint;
  private Paint blockPaint;
  private Rect borderRect;

  private Handler handler;
  private boolean running = false;

  private Tetris tetris;
  private boolean gameOver = false;
  private TetrisCallback tetrisCallback = ListTetrisCallback.NIL;

  private boolean translating = false;
  private boolean downOrRotate = false;
  private int lastVirtualShapeX;
  private float startY;

  public TetrisView(Context context) {
    this(context, null);
  }

  public TetrisView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TetrisView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    borderPaint = new Paint();
    borderPaint.setColor(Color.WHITE);
    borderPaint.setStyle(Style.STROKE);
    borderPaint.setStrokeWidth(BORDER_WIDTH);

    blockPaint = new Paint();
    blockPaint.setColor(Color.WHITE);
    blockPaint.setStyle(Style.FILL);

    handler = new Handler();

    resume();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    int left = (w % BLOCK_WIDTH) >> 1;
    int top = (h % BLOCK_HEIGHT) >> 1;
    borderRect = new Rect(left, top, w - left, h - top);

    tetris = new Tetris(w / BLOCK_WIDTH, h / BLOCK_HEIGHT);
    tetris.setTetrisCallback(new ListTetrisCallback(this, tetrisCallback));
    tetris.placeShape();
  }

  public void setTetrisCallback(TetrisCallback tetrisCallback) {
    this.tetrisCallback = tetrisCallback;
  }

  @Override
  protected void onDraw(final Canvas canvas) {
    canvas.drawColor(Color.BLACK); // background
    canvas.drawRect(borderRect, borderPaint); // border
    tetris.draw(this, canvas); // board and shape
  }

  @Override
  public void drawBlock(Canvas canvas, int x, int y) {
    int left = borderRect.left + BORDER_WIDTH + BLOCK_WIDTH * x;
    int top = borderRect.top + BORDER_WIDTH + BLOCK_WIDTH * y;
    canvas.drawRect(left + BORDER_WIDTH, top + BORDER_WIDTH, left + BLOCK_WIDTH
        - BORDER_WIDTH, top + BLOCK_WIDTH - BORDER_WIDTH, blockPaint);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    onTouch(event);
    return true;
  }

  private void onTouch(MotionEvent event) {
    if (gameOver) return;
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        float x = event.getX();
        int w = getWidth();
        if (x > w * 0.75f && x < w) {
          startY = event.getY();
          downOrRotate = true;
          break;
        }
        lastVirtualShapeX = calculateBlockX(event);
        translating = true;
        break;
      case MotionEvent.ACTION_MOVE:
        if (downOrRotate && event.getY() - startY > 100) {
          down();
          break;
        }
        translateShape(event);
        break;
      case MotionEvent.ACTION_UP:
        if (downOrRotate && startY - event.getY() > 50) {
          rotate();
        }
        translateShape(event);
        translating = false;
        downOrRotate = false;
    }
  }

  private void translateShape(MotionEvent event) {
    if (!translating) return;
    int currentVirtualShapeX = calculateBlockX(event);
    if (tetris.translate(currentVirtualShapeX - lastVirtualShapeX)) {
      invalidate();
      lastVirtualShapeX = currentVirtualShapeX;
    }
  }

  private int calculateBlockX(MotionEvent event) {
    return (int) event.getX() / BLOCK_WIDTH;
  }

  private void rotate() {
    if (tetris.rotate()) {
      invalidate();
    }
  }

  private void down() {
    if (tetris.down()) {
      invalidate();
    }
  }

  public void restart() {
    gameOver = false;
    tetris.clearBoard();
    tetris.placeShape();

    resume();
  }

  public void pause() {
    running = false;
  }

  public void resume() {
    if (!gameOver && !running) {
      running = true;
      handler.postDelayed(this, INTERVAL);
    }
  }

  @Override
  public void onGameOver() {
    gameOver = true;
    running = false;
  }

  @Override
  public void onPlaceShape() {
    translating = false;
    downOrRotate = false;
  }

  @Override
  public void onFixDown(int rows) {
  }

  @Override
  public void run() {
    if (gameOver) return;
    down();
    if (running) handler.postDelayed(this, INTERVAL);
  }

}
