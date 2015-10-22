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
import android.widget.TextView;
import android.widget.Toast;


public class HangmanActivity extends Activity implements View.OnClickListener {

    Fragment mScoreboard;
    String mPlayer1, mPlayer2, enteredStringGuess;
    int score1, score2;
    TextView mGameWordTextView, mLettersGuessed, mP1GuessTextView, mP2GuessTextView;
    EditText mEditTextCharGuess;
    Button mGuessButton, mNextPlayerButton, mQuitButton;
    Hangman h;

    boolean quitClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        h = new Hangman();

        Intent intent = getIntent();
        mPlayer1 = intent.getStringExtra(MainActivity.KEY_PLAYER_1);
        mPlayer2 = intent.getStringExtra(MainActivity.KEY_PLAYER_2);
        score1 = intent.getIntExtra(MainActivity.KEY_SCORE_1, -1);
        score2 = intent.getIntExtra(MainActivity.KEY_SCORE_2, -1);

        updateScoreboard();

        mEditTextCharGuess = (EditText) findViewById(R.id.edit_text_char_guess);
        mLettersGuessed = (TextView) findViewById(R.id.text_view_letters_guessed);
        mP1GuessTextView = (TextView) findViewById(R.id.text_view_p1_guesses);
        mP2GuessTextView = (TextView) findViewById(R.id.text_view_p2_guesses);
        mGameWordTextView = (TextView) findViewById(R.id.text_view_game_word);
        h.playerSetUp();

        mGuessButton = (Button) findViewById(R.id.button_guess);
        mNextPlayerButton = (Button) findViewById(R.id.button_next_player);
        mNextPlayerButton.setEnabled(false);
        mNextPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset board
                h.nextPlayerSetUp();
                mLettersGuessed.setText("");
                mGameWordTextView.setText(h.getHiddenWord());
                mGuessButton.setEnabled(true);
                mNextPlayerButton.setEnabled(false);
            }
        });

        mGuessButton.setOnClickListener(this);
        mQuitButton = (Button) findViewById(R.id.button_quit_hangman);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quitClick == false) {
                    Toast.makeText(HangmanActivity.this, "Press quit again if you want to quit", Toast.LENGTH_SHORT).show();
                    quitClick = true;
                } else if (quitClick == true) {
                    Intent i = new Intent(HangmanActivity.this, MainActivity.class);
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

    public void setUpGameWordView(int arrayIndex) {
        String hidden = h.setUpGameWord(arrayIndex);
        mGameWordTextView.setText(hidden);
    }

    public void onClick(View view) {
        if (h.getPlayerTurn() == 1) {
            enteredStringGuess = mEditTextCharGuess.getText().toString();
            boolean validMove = h.isValidGuess(enteredStringGuess);
            if (validMove == false) {
                Toast.makeText(this, "You did not guess a character", Toast.LENGTH_SHORT).show();
            } else if( h.hasBeenGuessed(enteredStringGuess.charAt(0))){
                Toast.makeText(this, "This letter was already guessed!", Toast.LENGTH_SHORT).show();
            } else {
                h.setGuess(enteredStringGuess.charAt(0));
                if(h.isInWord()){
                    String hw = h.getHiddenWord();
                    Log.d("hangman", hw);
                    mGameWordTextView.setText(h.getHiddenWord());
                    mLettersGuessed.setText(h.getCharsUsed(), 0, h.getCharsUsed().length);
                    if(h.isAllGuessed(h.getPlayerTurn()) == h.getPlayerTurn()){
                        Toast.makeText(this, "You guessed the word", Toast.LENGTH_SHORT).show();
                        mGuessButton.setEnabled(false);
                        mNextPlayerButton.setEnabled(true);
                    }
                } else {
                    if(h.getGuessCount() == 8){
                        mGuessButton.setEnabled(false);
                    } else {
                        h.wrongGuess();
                        mLettersGuessed.setText(h.getCharsUsed(), 0, h.getCharsUsed().length);
                        mP1GuessTextView.setText("Player 1 has guessed wrong " + h.getGuessCount() + " time(s)");
                    }
                }
            }
            mEditTextCharGuess.getText().clear();

        }
        if (h.getPlayerTurn() == 2){
            enteredStringGuess = mEditTextCharGuess.getText().toString();
            boolean validMove = h.isValidGuess(enteredStringGuess);
            if (validMove == false) {
                Toast.makeText(this, "You did not guess a character", Toast.LENGTH_SHORT).show();
            } else if( h.hasBeenGuessed(enteredStringGuess.charAt(0))){
                Toast.makeText(this, "This letter was already guessed!", Toast.LENGTH_SHORT).show();
            } else {
                h.setGuess(enteredStringGuess.charAt(0));
                if(h.isInWord()){
                    mGameWordTextView.setText(h.getHiddenWord());
                    mLettersGuessed.setText(h.getCharsUsed(), 0, h.getCharsUsed().length);
                    if(h.isAllGuessed(h.getPlayerTurn()) == h.getPlayerTurn()){
                        Toast.makeText(this, "You guessed the word", Toast.LENGTH_SHORT).show();
                        mGuessButton.setEnabled(false);
                        mNextPlayerButton.setEnabled(false);
                        if(h.getNumGuessesP1() > h.getNumGuessesP2()){
                            score2++;
                            Toast.makeText(this, "Player 2 wins! Current score: " + score2, Toast.LENGTH_LONG).show();
                            updateScoreboard();
                        } else if (h.getNumGuessesP1() < h.getNumGuessesP2()){
                            score1++;
                            Toast.makeText(this, "Player 1 wins! Current score: " + score1, Toast.LENGTH_LONG).show();
                            updateScoreboard();
                        } else if (h.getNumGuessesP1() == h.getNumGuessesP2()){
                            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Log.d("hangman", "entering first else statement");
                    if(h.getGuessCount() == 8){
                        mGuessButton.setEnabled(false);
                    } else {
                        Log.d("hangman", "i am a wrong guess, please update");
                        h.wrongGuess();
                        mLettersGuessed.setText(h.getCharsUsed(), 0, h.getCharsUsed().length);
                        mP2GuessTextView.setText("Player 2 has guessed wrong " + h.getGuessCount() + " time(s)");
                    }
                }
            }
            mEditTextCharGuess.getText().clear();
        }

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
                .replace(R.id.frame_layout_scoreboard_fragment_hangman, mScoreboard)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hangman, menu);
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
