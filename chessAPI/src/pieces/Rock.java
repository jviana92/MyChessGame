package pieces;

import javafx.util.Pair;

import java.util.List;

public class Rock extends Movements implements Piece {


    public Rock(Pair<Integer, Integer> coords, Colour colour) {
        this.coords = coords;
        this.colour = colour;
        type = Type.ROCK;
    }

    @Override
    public void move(Pair<Integer, Integer> newCoords) {
        coords = newCoords;
    }

    @Override
    public List<Pair<Integer, Integer>> getMoveList() {
        return horizontalMoves();
    }

    @Override
    public List<Pair<Integer, Integer>> getCaptureList() {
        return getMoveList();
    }

    @Override
    public List<Pair<Integer, Integer>> getIntermediateList(Pair<Integer, Integer> move) {
        return horizontalIntermediateMoves(move);

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
        return "R";
    }
}

