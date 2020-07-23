package pieces;

import javafx.util.Pair;

import java.util.List;

public interface Piece {
    void move(Pair<Integer, Integer> newCoords);

    List<Pair<Integer, Integer>> getMoveList();

    //the pawn captures in a diferent direction than it moves
    List<Pair<Integer, Integer>> getCaptureList();

    List<Pair<Integer, Integer>> getIntermediateList(Pair<Integer, Integer> move);


    Pair<Integer, Integer> getCoords();

    Colour getColour();

    boolean canJump();

    Type getType();
}
