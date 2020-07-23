package pieces;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Movements {
    Colour colour;
    Pair<Integer, Integer> coords;
    Type type;

    boolean hasMoved = false;
    Boolean isFirstMove = false;

    public List<Pair<Integer, Integer>> horseyMoves(){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();
        moves.add(new Pair<>(1,2));
        moves.add(new Pair<>(1,-2));
        moves.add(new Pair<>(-1,2));
        moves.add(new Pair<>(-1,-2));
        moves.add(new Pair<>(2,1));
        moves.add(new Pair<>(2,-1));
        moves.add(new Pair<>(-2,1));
        moves.add(new Pair<>(-2,1));
        return moves;
    }

    public List<Pair<Integer, Integer>> diagonalMoves(){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();

        for (int i = 1;i<=7;i++){
            moves.add(new Pair<>(i,i));
            moves.add(new Pair<>(i,-i));
            moves.add(new Pair<>(-i,i));
            moves.add(new Pair<>(-i,-i));
        }
        return moves;
    }

    public List<Pair<Integer, Integer>> diagonalIntermediateMoves(Pair<Integer, Integer> attemptedMove){
        //If the move is (3,3), it should return (1,1) and (2,2)
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();
        int xFactor = attemptedMove.getKey()<0 ? -1:1;
        int yFactor = attemptedMove.getValue()<0 ? -1:1;

        for(int i = 1; i<Math.abs(attemptedMove.getKey()); i++){
            moves.add(new Pair<Integer, Integer> (i*xFactor,i*yFactor));
        }
        return moves;
    }

    public List<Pair<Integer, Integer>> horizontalMoves(){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();

        for (int i = -8;i<=8;i++){
            moves.add(new Pair<>(i,0));
            moves.add(new Pair<>(0,i));
        }
        return moves;
    }

    public List<Pair<Integer, Integer>> horizontalIntermediateMoves(Pair<Integer, Integer> attemptedMove){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();

        if(attemptedMove.getKey() == 0){ //this means the move is (0,X)
            int Factor = attemptedMove.getValue()<0 ? -1:1;
            for(int i = 1; i<Math.abs(attemptedMove.getValue()); i++){
                moves.add(new Pair<Integer, Integer> (0,i*Factor));
            }
        }else{ // for moves (X,0)
            int Factor = attemptedMove.getKey()<0 ? -1:1;
            for(int i = 1; i<Math.abs(attemptedMove.getKey()); i++){
                moves.add(new Pair<Integer, Integer> (i*Factor,0));
            }
        }

        return moves;
    }

    public List<Pair<Integer, Integer>> kingMoves(){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();
        for (int i = -1;i<=1;i++){
            for (int j = -1;j<=1;j++){
                moves.add(new Pair<>(i,j));
            }
        }
        return moves;
    }
    public List<Pair<Integer, Integer>> pawnIntermediateMoves(Pair<Integer, Integer> attemptedMove){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();



        if(colour == Colour.WHITE){
            if(attemptedMove.equals(new Pair<Integer, Integer>(0,2))){
                moves.add(new Pair<Integer, Integer>(0,1));
            }
        }
        else{
            if(attemptedMove.equals(new Pair<Integer, Integer>(0,-2))){
                moves.add(new Pair<Integer, Integer>(0,-1));
            }
        }
        return moves;
    }
    public List<Pair<Integer, Integer>> pawnMoves(boolean hasMoved){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();

        if(colour == Colour.WHITE){
            moves.add(new Pair <Integer, Integer> (0, 1));
            if(!hasMoved){
                moves.add(new Pair <Integer, Integer> (0, 2));
            }
        }
        else{
            moves.add(new Pair <Integer, Integer> (0, -1));
            if(!hasMoved){
                moves.add(new Pair <Integer, Integer> (0, -2));
            }
        }

        return moves;
    }
    public List<Pair<Integer, Integer>> pawnCapturing(){
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();
        if(colour == Colour.WHITE){
            moves.add(new Pair <Integer, Integer> (1, 1));
            moves.add(new Pair <Integer, Integer> (-1, 1));
        }
        else{
            moves.add(new Pair <Integer, Integer> (1, -1));
            moves.add(new Pair <Integer, Integer> (-1, -1));
        }


        return moves;
    }

    public boolean withinBounds(Pair<Integer, Integer> move){
        if(((coords.getKey() + move.getKey())>=0) && ((coords.getKey() + move.getKey())<8) && ((coords.getValue() + move.getValue())>=0) && ((coords.getValue() + move.getValue())<8)){
            return true;
        }
        return false;

    }
}
