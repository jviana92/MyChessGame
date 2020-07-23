package board;

import javafx.util.Pair;
import pieces.Piece;


public class Board {



    Position[][] board = new Position[8][8];


    public Board() {
        for (int i=0 ; i<8;i++){

            for (int j=0; j<8; j++){
                board[i][j] = new Position();
            }
        }
    }

    public void createPiece(Piece piece){
        board[piece.getCoords().getKey()][piece.getCoords().getValue()].setPiece(piece);
    }


    public Piece getPieceByCoords(Pair<Integer,Integer> coords){

        return board[coords.getKey()][coords.getValue()].getPiece();
    }



    public boolean hasPiece(Pair <Integer,Integer> coords){
        return board[coords.getKey()][coords.getValue()].hasPiece();
    }



    public Piece movePiece(Pair <Integer,Integer> initCoords, Pair <Integer,Integer> finalCoords){
        Piece capturedPiece, movingPiece ;
        capturedPiece = this.getPieceByCoords(finalCoords);
        movingPiece = this.getPieceByCoords(initCoords);
        board[initCoords.getKey()][initCoords.getValue()].removePiece();
        board[finalCoords.getKey()][finalCoords.getValue()].addPiece(movingPiece);

        if(capturedPiece!= null){
            return capturedPiece;
        }
        else{
            return null;
        }
    }

    @Override
    public String toString() {
        String resume = "";
        for (int j = 7; j>=0;j--){
            resume += "|";
            for (int i = 0; i<8; i++){
                resume += board[i][j] + "|";
            }
            resume += "\n";
        }
        return resume;
    }


}
