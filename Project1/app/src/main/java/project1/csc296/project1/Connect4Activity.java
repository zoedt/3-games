package project1.csc296.project1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class Connect4Activity extends Activity implements View.OnClickListener{

    Fragment mScoreboard;
    String mPlayer1, mPlayer2;
    GridLayout mConnect4BoardLayout, mConnect4ButtonLayout;
    Connect4 c4;
    int score1, score2;
    Button mQuitButton;

    boolean quitClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect4);

        Intent intent = getIntent();
        mPlayer1 = intent.getStringExtra(MainActivity.KEY_PLAYER_1);
        mPlayer2 = intent.getStringExtra(MainActivity.KEY_PLAYER_2);
        score1 = intent.getIntExtra(MainActivity.KEY_SCORE_1, -1);
        score2 = intent.getIntExtra(MainActivity.KEY_SCORE_2, -1);

        updateScoreboard();

        c4 = new Connect4();

        mConnect4BoardLayout = (GridLayout) findViewById(R.id.grid_layout_connect4_board);
        mConnect4ButtonLayout = (GridLayout) findViewById(R.id.grid_layout_connect4_buttons);

        for(int i = 0; i < 5; i++){
            GridLayout.Spec rowSpec = GridLayout.spec(0, 1, 1.0f);
            GridLayout.Spec colSpec = GridLayout.spec(i, 1, 1.0f);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
            params.setGravity(Gravity.FILL_HORIZONTAL + Gravity.FILL_VERTICAL);
            params.width = 10;
            params.height = 10;
            Button b = new Button(this);
            b.setId(i + 25);
            b.setText("PUT");
            b.setLayoutParams(params);
            mConnect4ButtonLayout.addView(b);
            b.setOnClickListener(this);
        }

        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 5; col++) {
                GridLayout.Spec rowSpec = GridLayout.spec(row, 1, 1.0f);
                GridLayout.Spec colSpec = GridLayout.spec(col, 1, 1.0f);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                params.setGravity(Gravity.FILL_HORIZONTAL + Gravity.FILL_VERTICAL);
                params.width = 10;
                params.height = 10;
                ImageView iv = new ImageView(this);
                iv.setImageResource(R.mipmap.connect_4_empty);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setLayoutParams(params);
                iv.setId(col + (row * 5));
                mConnect4BoardLayout.addView(iv);
            }
        }

        mQuitButton = (Button) findViewById(R.id.button_quit_connect4);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quitClick == false) {
                    Toast.makeText(Connect4Activity.this, "Press quit again if you want to quit", Toast.LENGTH_SHORT).show();
                    quitClick = true;
                } else if (quitClick == true) {
                    Intent i = new Intent(Connect4Activity.this, MainActivity.class);
                    i.putExtra(MainActivity.KEY_PLAYER_1, mPlayer1);
                    i.putExtra(MainActivity.KEY_PLAYER_2, mPlayer2);
                    i.putExtra(MainActivity.KEY_SCORE_1, score1);
                    i.putExtra(MainActivity.KEY_SCORE_2, score2);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });

    }

    public void onClick(View view){
        int playerTurn = c4.getPlayerTurn();
        if(playerTurn == 1){
            Button b = (Button) view;
            c4.setColumn(b.getId() - 25);
            c4.setRow(c4.putPiece(playerTurn, c4.getColumn()));
            if(c4.getRow() == 0){
                b.setEnabled(false);
            }
            int imageviewid = c4.getColumn() + (c4.getRow() * 5);
            ImageView iv = (ImageView) findViewById(imageviewid);
            iv.setImageResource(R.mipmap.connect_4_star);
            boolean win = c4.winCheck(playerTurn);
            if (win == true){
                score1++;
                Toast.makeText(this, "Player 1 wins. Current score: " + score1, Toast.LENGTH_LONG).show();
                fadeButtons();
                updateScoreboard();
            }
            if(c4.isDraw()){
                Toast.makeText(this, "It is a draw", Toast.LENGTH_LONG).show();
                fadeButtons();
            }
            c4.switchPlayer(playerTurn);
        } else if(playerTurn == 2){
            Button b = (Button) view;
            c4.setColumn(b.getId() - 25);
            c4.setRow(c4.putPiece(playerTurn, c4.getColumn()));
            if(c4.getRow() == 0){
                b.setEnabled(false);
            }
            int imageviewid = c4.getColumn() + (c4.getRow() * 5);
            ImageView iv = (ImageView) findViewById(imageviewid);
            iv.setImageResource(R.mipmap.connect_4_heart);
            boolean win = c4.winCheck(playerTurn);
            if (win == true){
                score2++;
                Toast.makeText(this, "Player 2 wins. Current score is: " + score2, Toast.LENGTH_LONG).show();
                fadeButtons();
                updateScoreboard();
            }
            if(c4.isDraw()){
                Toast.makeText(this, "It is a draw", Toast.LENGTH_LONG).show();
                fadeButtons();
            }
            c4.switchPlayer(playerTurn);
        }

    }

    public void fadeButtons(){
        Animation fadeOutAnim = AnimationUtils.loadAnimation(Connect4Activity.this, R.anim.fade);
        mConnect4ButtonLayout.startAnimation(fadeOutAnim);
        mConnect4ButtonLayout.setVisibility(View.GONE);
    }

    public void updateScoreboard(){
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY_PLAYER_1, mPlayer1);
        bundle.putString(MainActivity.KEY_PLAYER_2, mPlayer2);
        Log.d("UPDATEHANGMAN", score1 + " score 1");
        Log.d("UPDATEHANGMAN", score2+" score 2");
        bundle.putInt(MainActivity.KEY_SCORE_1, score1);
        bundle.putInt(MainActivity.KEY_SCORE_1, score2);

        mScoreboard = new ScoreboardFragment();
        mScoreboard.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_scoreboard_fragment_connect4, mScoreboard)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
