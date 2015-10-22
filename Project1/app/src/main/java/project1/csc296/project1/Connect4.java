package project1.csc296.project1;

/**
 * Created by zoetiet on 10/9/15.
 */
public class Connect4 {

    private int playerTurn, turns;
    private int [][] board = new int [5][5];
    int column, row;

    public Connect4(){
        playerTurn = 1;
    }

    public int getPlayerTurn(){
        return playerTurn;
    }

    public void setColumn(int c){
        column = c;
    }
    public void setRow(int r){
        row = r;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int putPiece(int player, int c){
        //c is column
        int row = 0;
        for (int i = 4; i >= 0; i--) {
            if (board[i][c] == 0) {
                board[i][c] = player;
                row = i;
                break;
            }
        }
        return row;
    }

    public void printBoard(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void switchPlayer(int player){
        if(player == 1){
            playerTurn = 2;
        }
        if (player == 2){
            playerTurn = 1;
        }
    }

    public boolean winCheck(int player){
        int vCheck = 0, hCheck = 0, dCheck = 0, adCheck = 0;

        turns++;
        //check vertical
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[j][i] == player){
                    vCheck++;
                    if(vCheck == 4){
                        return true;
                    }
                } else {
                    vCheck = 0;
                }
            }
        }

        //checks horizontal
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i][j] == player){
                    hCheck++;
                    if(hCheck == 4){
                        return true;

                    }
                } else {
                    hCheck = 0;
                }
            }
        }

        //check diagonal downwards
        for(int row = 0; row < 2; row++){
            for(int col = 0; col < 2; col++){
                if(board[row][col] == player
                        && board[row+1][col+1] == player
                        && board[row+2][col+2] == player
                        && board[row+3][col+3] == player){
                    //dCheck++;
                    return true;
                }
            }
        }

        //check diagonal upwards
        for(int row = 0; row < 2; row++){
            for(int col = 3; col < 5; col++){
                if(board[row][col] == player
                        && board[row+1][col-1] == player
                        && board[row+2][col-2] == player
                        && board[row+3][col-3] == player){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isDraw(){
        if((winCheck(1)==false) && (winCheck(2)==false) && turns == 25 ){
            return true;
        } else {
            return false;
        }
    }

}
