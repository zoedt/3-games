package project1.csc296.project1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    static final String KEY_PLAYER_1 = "project1.csc296.PLAYER1";
    static final String KEY_PLAYER_2 = "project1.csc296.PLAYER2";
    static final String KEY_SCORE_1 = "project1.csc296.SCORE1";
    static final String KEY_SCORE_2 = "project1.csc296.SCORE2";

    private static final int RC_TICTACTOE = 1;
    private static final int RC_HANGMAN = 2;
    private static final int RC_CONNECT4 = 3;


    String mPlayer1, mPlayer2;
    int mPlayer1Score = 0, mPlayer2Score = 0;
    Fragment mScoreboard;
    Button mTicTacToe, mConnect4, mHangman;
    EditText mEditPlayer1, mEditPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreboard = new ScoreboardFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.frame_layout_scoreboard_fragment, mScoreboard)
                .commit();

        mTicTacToe = (Button) findViewById(R.id.button_tic_tac_toe);
        mTicTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPlayer1 = (EditText) findViewById(R.id.edit_text_player_1);
                mEditPlayer2 = (EditText) findViewById(R.id.edit_text_player_2);
                mPlayer1 = mEditPlayer1.getText().toString();
                mPlayer2 = mEditPlayer2.getText().toString();

                Intent i = new Intent(MainActivity.this, TicTacToeActivity.class);
                i.putExtra(KEY_PLAYER_1, mPlayer1);
                i.putExtra(KEY_PLAYER_2, mPlayer2);
                Log.d("MAINACTIVITY", mPlayer1Score+"");
                i.putExtra(KEY_SCORE_1, mPlayer1Score);
                i.putExtra(KEY_SCORE_2, mPlayer2Score);
                startActivityForResult(i, RC_TICTACTOE);
            }
        });

        mConnect4 = (Button) findViewById(R.id.button_connect_4);
        mConnect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPlayer1 = (EditText) findViewById(R.id.edit_text_player_1);
                mEditPlayer2 = (EditText) findViewById(R.id.edit_text_player_2);
                mPlayer1 = mEditPlayer1.getText().toString();
                mPlayer2 = mEditPlayer2.getText().toString();

                Intent i = new Intent(MainActivity.this, Connect4Activity.class);
                i.putExtra(KEY_PLAYER_1, mPlayer1);
                i.putExtra(KEY_PLAYER_2, mPlayer2);
                startActivity(i);
            }
        });

        mHangman = (Button) findViewById(R.id.button_hangman);
        mHangman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPlayer1 = (EditText) findViewById(R.id.edit_text_player_1);
                mEditPlayer2 = (EditText) findViewById(R.id.edit_text_player_2);
                mPlayer1 = mEditPlayer1.getText().toString();
                mPlayer2 = mEditPlayer2.getText().toString();

                Intent i = new Intent(MainActivity.this, HangmanActivity.class);
                i.putExtra(KEY_PLAYER_1, mPlayer1);
                i.putExtra(KEY_PLAYER_2, mPlayer2);
                Log.d("MAINACTIVITY", mPlayer1Score+"");
                i.putExtra(KEY_SCORE_1, mPlayer1Score);
                i.putExtra(KEY_SCORE_2, mPlayer2Score);
                startActivityForResult(i, RC_HANGMAN);
            }
        });

    }

    public void updateScoreboard(){
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY_PLAYER_1, mPlayer1);
        bundle.putString(MainActivity.KEY_PLAYER_2, mPlayer2);
        bundle.putInt(MainActivity.KEY_SCORE_1, mPlayer1Score);
        bundle.putInt(MainActivity.KEY_SCORE_1, mPlayer2Score);

        mScoreboard = new ScoreboardFragment();
        mScoreboard.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_scoreboard_fragment, mScoreboard)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == RC_TICTACTOE){
                mPlayer1 = data.getStringExtra(MainActivity.KEY_PLAYER_1);
                mPlayer2 = data.getStringExtra(MainActivity.KEY_PLAYER_2);
                mPlayer1Score = data.getIntExtra(MainActivity.KEY_SCORE_1, -1);
                mPlayer2Score = data.getIntExtra(MainActivity.KEY_SCORE_2, -1);
                mEditPlayer1.setText(mPlayer1);
                mEditPlayer2.setText(mPlayer2);
                updateScoreboard();
            }
            if(requestCode == RC_CONNECT4){
                mPlayer1 = data.getStringExtra(MainActivity.KEY_PLAYER_1);
                mPlayer2 = data.getStringExtra(MainActivity.KEY_PLAYER_2);
                mPlayer1Score = data.getIntExtra(MainActivity.KEY_SCORE_1, -1);
                mPlayer2Score = data.getIntExtra(MainActivity.KEY_SCORE_2, -1);
                mEditPlayer1.setText(mPlayer1);
                mEditPlayer2.setText(mPlayer2);
                updateScoreboard();
            }
            if(requestCode == RC_HANGMAN){
                mPlayer1 = data.getStringExtra(MainActivity.KEY_PLAYER_1);
                mPlayer2 = data.getStringExtra(MainActivity.KEY_PLAYER_2);
                mPlayer1Score = data.getIntExtra(MainActivity.KEY_SCORE_1, -1);
                mPlayer2Score = data.getIntExtra(MainActivity.KEY_SCORE_2, -1);
                mEditPlayer1.setText(mPlayer1);
                mEditPlayer2.setText(mPlayer2);
                updateScoreboard();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
