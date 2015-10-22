package project1.csc296.project1;

/**
 * Created by zoetiet on 10/9/15.
 */
public class TicTacToe {

    int row, col, turns = 0;
    int [][] board;
    int playerTurn = 1;

    public TicTacToe(){
        this.board = new int[3][3];
    }

    public void getRowColumn(int buttonId){
        if(buttonId == 0){
            this.row = 0;
            this.col = 0;
        }
        if(buttonId == 1){
            this.row = 0;
            this.col = 1;
        }
        if(buttonId == 2){
            this.row = 0;
            this.col = 2;
        }
        if(buttonId == 3){
            this.row = 1;
            this.col = 0;
        }
        if(buttonId == 4){
            this.row = 1;
            this.col = 1;
        }
        if (buttonId == 5){
            this.row = 1;
            this.col = 2;
        }
        if(buttonId == 6){
            this.row = 2;
            this.col = 0;
        }
        if(buttonId == 7){
            this.row = 2;
            this.col = 1;
        }
        if (buttonId == 8){
            this.row = 2;
            this.col = 2;
        }
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public int checkWin(int player, int r, int c){
        int hasWon = 0;
        int rowCounter = 0, colCounter = 0;

        turns++;
        //check the row then column player has added to
        for(int i = 0; i < board.length; i++){
            if(board[r][i] == player){
                rowCounter++;
            }
            if(board[i][c] == player){
                colCounter++;
            }
        }

        if(rowCounter == board.length || colCounter == board.length){
            hasWon = player;
        } else {
            hasWon = 0;
        }

        //check diagonals that player has added to
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player){
            hasWon = player;
        }
        if(board[0][2] == player && board[1][1] == player && board[2][0] == player){
            hasWon = player;
        }

        if(hasWon == 0 && turns == 9){
            hasWon = 3; //number to draw
        }

        return hasWon;
    }

    public void makeMove(int player, int row, int col){
        board[row][col] = player;
    }

    public void printBoard(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void nextTurn(int player){
        if(player == 1){
            playerTurn = 2;
        }
        if(player == 2){
            playerTurn = 1;
        }
    }

    public int getPlayerTurn(){
        return playerTurn;
    }

}
