package pieces;

import javafx.util.Pair;

import java.util.List;

public class Pawn extends Movements implements Piece  {



    public Pawn(Pair<Integer, Integer> coords, Colour colour) {
        this.coords = coords;
        this.colour = colour;
        type =  Type.PAWN;
    }

    @Override
    public void move(Pair<Integer, Integer> newCoords) {
        coords = newCoords;
        hasMoved = true;
    }

    @Override
    public List<Pair<Integer, Integer>> getMoveList() {
        return  pawnMoves(hasMoved);
    }

    @Override
    public List<Pair<Integer, Integer>> getCaptureList() {
        return pawnCapturing();
    }

    @Override
    public List<Pair<Integer, Integer>> getIntermediateList(Pair<Integer, Integer> move) {
        return pawnIntermediateMoves(move);
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
        return "P";
    }
}
