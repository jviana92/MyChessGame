package pieces;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Movements implements Piece {


    public Queen(Pair<Integer, Integer> coords, Colour colour) {
        this.coords = coords;
        this.colour = colour;
        type = Type.QUEEN;
    }

    @Override
    public void move(Pair<Integer, Integer> newCoords) {
        coords = newCoords;
    }

    @Override
    public List<Pair<Integer, Integer>> getMoveList() {
        ArrayList<Pair <Integer,Integer> > moves = new ArrayList<>();
        horizontalMoves().forEach(e -> moves.add(e));
        diagonalMoves().forEach(e -> moves.add(e));
        return moves;
    }

    @Override
    public List<Pair<Integer, Integer>> getCaptureList() {
        return getMoveList();
    }

    @Override
    public List<Pair<Integer, Integer>> getIntermediateList(Pair<Integer, Integer> move) {
        if(horizontalMoves().contains(move)){
            return horizontalIntermediateMoves(move);
        }
        else {
            return diagonalIntermediateMoves(move);
        }
    }
    @Override
    public Pair<Integer, Integer> getCoords() {
        return this.coords;
    }
    @Override
    public boolean canJump() {
        return false;
    }
    @Override
    public Type getType() {
        return type;
    }
    @Override
    public Colour getColour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
