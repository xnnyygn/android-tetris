package in.xnnyygn.android.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IndexActivity extends Activity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_index);

    // bind listeners
    findViewById(R.id.btnStart).setOnClickListener(this);
    findViewById(R.id.btnExit).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnStart:
        Intent intent = new Intent(this, TetrisActivity.class);
        startActivity(intent);
        break;
      case R.id.btnExit:
        finish();
        break;
    }
  }

}
