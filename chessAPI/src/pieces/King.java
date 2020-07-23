package pieces;

import javafx.util.Pair;

import java.util.List;

public class King extends Movements implements Piece{


    public King(Pair<Integer, Integer> coords, Colour colour) {
        this.coords = coords;
        this.colour = colour;
        type = Type.KING;
    }

    @Override
    public void move(Pair<Integer, Integer> newCoords) {
        coords = newCoords;
    }

    @Override
    public List<Pair<Integer, Integer>> getMoveList() {
        return kingMoves();
    }

    @Override
    public List<Pair<Integer, Integer>> getCaptureList() {
        return getMoveList();
    }

    @Override
    public List<Pair<Integer, Integer>> getIntermediateList(Pair<Integer, Integer> move) {
        return null;
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
        return "K";
    }
}
