package board;

import pieces.Piece;

public class Position {

    Piece piece;


    public Position() {

    }

    public Piece getPiece(){
        return this.piece;
    }

    public void addPiece( Piece piece){
        this.piece = piece;
    }

    public boolean hasPiece(){
        return piece != null;
    }



    public void removePiece(){
        piece = null;
    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        if(piece != null){
            return piece.toString();
        }
        else{
            return " ";
        }
    }
}
