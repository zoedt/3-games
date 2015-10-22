package project1.csc296.project1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TicTacToeActivity extends Activity implements View.OnClickListener{

    TextView mResult;
    Fragment mScoreboard;
    String mPlayer1, mPlayer2;
    GridLayout mTicTacToeLayout;
    Button mQuitButton;
    Boolean quitClick = false;
    TicTacToe ttt;

    int score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        Intent intent = getIntent();
        mPlayer1 = intent.getStringExtra(MainActivity.KEY_PLAYER_1);
        mPlayer2 = intent.getStringExtra(MainActivity.KEY_PLAYER_2);
        score1 = intent.getIntExtra(MainActivity.KEY_SCORE_1, -1);
        score2 = intent.getIntExtra(MainActivity.KEY_SCORE_2, -1);

        updateScoreboard();

        ttt = new TicTacToe();

        mResult = (TextView) findViewById(R.id.text_view_tic_tac_toe_winner);
        mTicTacToeLayout = (GridLayout) findViewById(R.id.grid_layout_tictactoe);

        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                GridLayout.Spec rowSpec = GridLayout.spec(row, 1, 1.0f);
                GridLayout.Spec colSpec = GridLayout.spec(col, 1, 1.0f);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                params.setGravity(Gravity.FILL_HORIZONTAL + Gravity.FILL_VERTICAL);
                params.width = 10;
                params.height = 10;
                Button b = new Button(this);
                b.setId(col + (row * 3));
                b.setLayoutParams(params);
                mTicTacToeLayout.addView(b);
                b.setOnClickListener(this);
            }
        }

        mQuitButton = (Button) findViewById(R.id.button_quit_tictactoe);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quitClick == false) {
                    Toast.makeText(TicTacToeActivity.this, "Press quit again if you want to quit", Toast.LENGTH_SHORT).show();
                    quitClick = true;
                } else if (quitClick == true) {
                    Intent i = new Intent(TicTacToeActivity.this, MainActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tic_tac_toe, menu);
        return true;
    }



    public void onClick(View view){
        if (ttt.getPlayerTurn() == 1) {
            Button b = (Button) view;
            b.setBackgroundResource(R.mipmap.tictactoe_x);
            int buttonId = b.getId();
            ttt.getRowColumn(buttonId);
            int row = ttt.getRow();
            int col = ttt.getCol();
            ttt.makeMove(ttt.getPlayerTurn(), row, col);
            b.setEnabled(false);
            int hasWon = ttt.checkWin(ttt.getPlayerTurn(), row, col);
            Log.d("TICTACTOE", hasWon + "");
            if (hasWon == ttt.getPlayerTurn()) {
                score1++;
                Toast.makeText(this, "Player 1 has won. Current score: " + score1, Toast.LENGTH_LONG).show();
                updateScoreboard();
                mResult.setText("Player 1 wins!");
                mResult.setBackgroundColor(Color.parseColor("#9dd1bb"));
            } else if (hasWon == 3){
                draw();
            }
            ttt.nextTurn(ttt.getPlayerTurn());
        } else if (ttt.getPlayerTurn() == 2) {
            Button b = (Button) view;
            b.setBackgroundResource(R.mipmap.tictactoe_o);
            int buttonId = b.getId();
            ttt.getRowColumn(buttonId);
            int row = ttt.getRow();
            int col = ttt.getCol();
            ttt.makeMove(ttt.getPlayerTurn(), row, col);
            b.setEnabled(false);
            int hasWon = ttt.checkWin(ttt.getPlayerTurn(), row, col);
            if (hasWon == ttt.getPlayerTurn()) {
                score2++;
                Toast.makeText(this, "Player 2 has won. Current score: " + score2, Toast.LENGTH_LONG).show();
                updateScoreboard();
                mResult.setText("Player 2 wins!");
                mResult.setBackgroundColor(Color.parseColor("#9dd1bb"));
            } else if (hasWon == 3){
                draw();
            }
            ttt.nextTurn(ttt.getPlayerTurn());
        }

    }

    public void draw(){
            mResult.setText("Draw!");
            mResult.setBackgroundColor(Color.parseColor("#c7c7c7"));
    }

    public void updateScoreboard(){
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY_PLAYER_1, mPlayer1);
        bundle.putString(MainActivity.KEY_PLAYER_2, mPlayer2);
        Log.d("UPDATETICTACTOE", score1 + " score 1");
        Log.d("UPDATETICTACTOE", score2+" score 2");
        bundle.putInt(MainActivity.KEY_SCORE_1, score1);
        bundle.putInt(MainActivity.KEY_SCORE_1, score2);

        mScoreboard = new ScoreboardFragment();
        mScoreboard.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_scoreboard_fragment_tic_tac_toe, mScoreboard)
                .commit();
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
