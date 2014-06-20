package in.xnnyygn.android.tetris;

public interface TetrisCallback {

  void onGameOver();
  
  void onPlaceShape();
  
  void onFixDown(int rows);

}
