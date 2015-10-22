package project1.csc296.project1;

import android.util.Log;

import java.util.Random;

/**
 * Created by zoetiet on 10/9/15.
 */
public class Hangman {

    private String gameWord, hiddenWord;
    private int randomNumber, guessCount = 0, totalCount = 0, playerTurn = 1, numGuessesP1, numGuessesP2;
    private char[] charsUsed = new char[26];
    private char guess;

    private boolean charInWord = false;

    String[] wordBank = {"chair",
            "money", "orange", "stove",
            "leopard", "mouse", "earbud",
            "water", "light", "table"};

    public Hangman(){
        Random random = new Random();
        randomNumber = random.nextInt(10);
        setUpGameWord(randomNumber);
    }

    public String setUpGameWord(int arrayIndex){
        gameWord = wordBank[randomNumber];
        StringBuilder s = new StringBuilder(gameWord.length());

        for (int i = 0; i < gameWord.length(); i++) {
            s.append("*");
        }
        hiddenWord = s.toString();
        Log.d("hangman", "word is  : " + gameWord);
        return hiddenWord;
    }

    public void playerSetUp(){
        guessCount = 0;
        playerTurn = 1;
        setUpGameWord(randomNumber);
        charsUsed = new char[26];
    }

    public void nextPlayerSetUp(){
        guessCount = 0;
        playerTurn = 2;
        setUpGameWord(randomNumber);
        charsUsed = new char[26];
    }

    public void setGuess(char c){
        guess = c;
    }

    public boolean isValidGuess(String s){
        if(s.length() > 1){
            return false;
        } else {
            return true;
        }
    }

    public boolean hasBeenGuessed(char c){
        if(String.valueOf(charsUsed).contains(String.valueOf(c))){
            return true;
        } else {
            return false;
        }
    }

    public String getHiddenWord(){
        return hiddenWord;
    }

    public char[] getCharsUsed(){
        return charsUsed;
    }

    public boolean isInWord(){
        StringBuilder s = new StringBuilder(hiddenWord);

        charInWord = false;
        for (int i = 0; i < gameWord.length(); i++) {
            //if the character is in the word, update textview, add to charsUsed
            if (gameWord.charAt(i) == guess) {
                s.setCharAt(i, guess);
                hiddenWord = s.toString();
                charsUsed[totalCount] = guess;
                totalCount++;
                charInWord = true;
            }
        }

        return charInWord;
    }

    public int isAllGuessed(int player){
        if(!hiddenWord.contains("*")){
            if (player == 1) {
                numGuessesP1 = guessCount;
            }
            if (player == 2){
                numGuessesP2 = guessCount;
            }
            return player;
        } else{
            return 0;
        }
    }

    public int getNumGuessesP1(){
        return numGuessesP1;
    }

    public int getNumGuessesP2(){
        return numGuessesP2;
    }

    public void wrongGuess(){
        guessCount++;
        totalCount++;
        charsUsed[totalCount] = guess;
    }

    public int getGuessCount(){
        return guessCount;
    }

    public int getPlayerTurn(){
        return playerTurn;
    }
}
