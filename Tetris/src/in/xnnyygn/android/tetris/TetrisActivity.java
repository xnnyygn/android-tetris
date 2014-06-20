package in.xnnyygn.android.tetris;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public class TetrisActivity extends Activity implements TetrisCallback,
    DialogInterface.OnClickListener {

  private static final int SCORE_ONE_LINE = 100;
  private TetrisView tetris;
  private TextView tvScore;
  private int score = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tetris);

    tetris = (TetrisView) findViewById(R.id.tetris);
    tetris.setTetrisCallback(this);

    tvScore = (TextView) findViewById(R.id.tvScore);
  }

  @Override
  protected void onPause() {
    super.onPause();
    tetris.pause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    tetris.resume();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case DialogInterface.BUTTON_POSITIVE:
        tetris.restart();
        updateScore(0);
        break;
      default:
        finish();
        break;
    }
  }

  @Override
  public void onGameOver() {
    new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
        .setMessage("Game Over, Restart?").setPositiveButton("Yes", this)
        .setNegativeButton("No", this).show();
  }

  @Override
  public void onPlaceShape() {
  }

  @Override
  public void onFixDown(int rows) {
    updateScore(score + SCORE_ONE_LINE * rows);
  }

  private void updateScore(int newScore) {
    score = newScore;
    tvScore.setText(getString(R.string.score_prefix) + score);
  }

}
