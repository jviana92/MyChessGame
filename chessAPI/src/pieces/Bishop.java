package pieces;

import javafx.util.Pair;

import java.util.List;

public class Bishop extends Movements implements Piece {

    Pair<Integer, Integer> coords;
    Type type = Type.BISHOP;
    Colour colour;

    public Bishop(Pair<Integer, Integer> coords, Colour colour) {
        this.coords = coords;
        this.colour = colour;
    }

    @Override
    public void move(Pair<Integer, Integer> newCoords) {
        coords = newCoords;
    }

    @Override
    public List<Pair<Integer, Integer>> getMoveList() {
        return diagonalMoves();
    }

    @Override
    public List<Pair<Integer, Integer>> getCaptureList() {
        return getMoveList();
    }

    @Override
    public List<Pair<Integer, Integer>> getIntermediateList(Pair<Integer, Integer> move) {
        return diagonalIntermediateMoves(move);
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
        return "B";
    }
}
