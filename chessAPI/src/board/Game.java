package board;

import javafx.util.Pair;
import pieces.*;
import player.Player;

import java.util.*;

public class Game {

    Board board;
    List<Piece> whitePieces = new ArrayList<>();
    List<Piece> blackPieces = new ArrayList<>();

    List<Player> players = new ArrayList<>();
    Player currentPlayer;

    public Game(){

        //1. Create the board
        this.board = new Board();

        //2. Create the pieces and add to the corresponding list
        createPieces();

        //3. Add the pieces in the board
        addPiecesToBoard();

        //4. Create 2 players
        createPlayer();
    }



    public void startGame(){

        while(!hasNoLegalMoves()){
            System.out.println(this);
            requestMove();
        }
        if(isMated()){
            winner();
        }
        else {
            System.out.println("It is a draw by stale-mate");
        }
        winner();
    }

    public void winner(){
        changePlayer();

        System.out.println("Game over.");
        System.out.println("The player with the colour " + currentPlayer.getColour() + " WINS");
    }

    public boolean isMated(){
        Piece king =getKing();


        Map<Piece , List<Pair<Integer,Integer>>> pieceMovements;

        if(currentPlayer.getColour() == Colour.WHITE){
            pieceMovements = allMoves(Colour.BLACK,false);
        }
        else{
            pieceMovements = allMoves(Colour.WHITE,false);
        }
        for (Map.Entry<Piece, List<Pair<Integer, Integer>>> entry : pieceMovements.entrySet()){
            Piece piece = entry.getKey();
            Pair<Integer,Integer> moveToGetKing = new Pair<>(king.getCoords().getKey()-piece.getCoords().getKey(),king.getCoords().getValue()-piece.getCoords().getValue());
            if(entry.getValue().contains(moveToGetKing)){
                return true;
            }
        }
        return false;
    }

    //Can be mate or stale-mate
    public boolean hasNoLegalMoves(){
        Map<Piece , List<Pair<Integer,Integer>>> pieceMovements;
        pieceMovements = allMoves(currentPlayer.getColour(),true);
        for (Map.Entry<Piece, List<Pair<Integer, Integer>>> entry : pieceMovements.entrySet()){
            if(entry.getValue().size()>0){
                return false;
            }
        }
        return true;
    }


    public void requestMove(){
        Pair <Integer, Integer> initCoords, finalCoords;

        //TO DO - Verify if you are not in mate situation
        System.out.println("Type the Coords of the piece you want to move, for example E3");
        initCoords = askInput();

        //Making sure that a piece is selected
            //Making sure each player can only move its own colour
        System.out.println("Type the Coords of the location you want your piece to move, for example E3");

        finalCoords = askInput();
        //1. Check if chosen position has a piece
        if(board.hasPiece(initCoords)){

            //2. Check if you are not trying to move an opponent's piece
            if(board.getPieceByCoords(initCoords).getColour() == currentPlayer.getColour()) {
                if (checkMoveLegality(initCoords, finalCoords,true) ) {
                    makeMove(initCoords, finalCoords);
                    changePlayer();


                } else {
                    System.out.println("Sorry, move not accepted");
                }
            }else{
                System.out.println("You are trying to move your opponents colour");
            }
        }else{
            System.out.println("Sorry no piece in that position");
        }
    }


    //This function will run at the end of the player's turn to check if he is not in check
    public boolean kingInCheckAfterMove(Pair <Integer,Integer> initCoords, Pair <Integer,Integer> finalCoords){
        Piece king = getKing();
        Map<Piece , List<Pair<Integer,Integer>>> pieceMovements;
        Piece capturedPiece = makeMove(initCoords,finalCoords);

        if(currentPlayer.getColour() == Colour.WHITE){
            pieceMovements = allMoves(Colour.BLACK,false); //Here the variable should consider check is set to false. That is because we are verifying the opponents moves to see if he can capture the king (= he is attacking the king)
//            System.out.println(pieceMovements);
        }
        else{
            pieceMovements = allMoves(Colour.WHITE,false); //same as previous
//            System.out.println(pieceMovements);
        }
        for (Map.Entry<Piece, List<Pair<Integer, Integer>>> entry : pieceMovements.entrySet()){
            Piece piece = entry.getKey();
            Pair<Integer,Integer> moveToGetKing = new Pair<>(king.getCoords().getKey()-piece.getCoords().getKey(),king.getCoords().getValue()-piece.getCoords().getValue());
//            System.out.println(piece + " at " + piece.getCoords() +  " : " + moveToGetKing);
            if(entry.getValue().contains(moveToGetKing)){
                undoMove(initCoords,finalCoords,capturedPiece);
                return true;
            }

        }
        undoMove(initCoords,finalCoords,capturedPiece);

        return false;
    }






    public boolean checkMoveLegality(Pair <Integer,Integer> initCoords, Pair <Integer,Integer> finalCoords, boolean shouldConsiderMate){
        Piece movingPiece = board.getPieceByCoords(initCoords);

        Pair <Integer,Integer> attemptedMove = new Pair<>(finalCoords.getKey() - initCoords.getKey(), finalCoords.getValue() - initCoords.getValue());
        List<Pair<Integer,Integer>> allowedMoves;



        //3. Check if you are actually moving the piece
        if(initCoords.equals(finalCoords)) {
//            System.out.println("Initial position equals Final Position");
            return false;
        }

        //4. verify if the move is in bounds
        if((finalCoords.getKey()>7) || (finalCoords.getKey()<0) || (finalCoords.getValue()>7) || (finalCoords.getValue()<0)){
//            System.out.println("Location out of bounds");
            return false;
        }
        Piece capturingPiece = board.getPieceByCoords(finalCoords);


        //5. Determine if the final position has piece
        if(capturingPiece == null){
            allowedMoves = movingPiece.getMoveList();
        }else{
            allowedMoves = movingPiece.getCaptureList();
            //6. check if it is not trying to capture a same colour piece
            if(movingPiece.getColour() == capturingPiece.getColour()){
//                System.out.println("You are trying to capture your own pieces");
                return false;
            }
        }

        //7. check if the move is in list of allowed moves
        if(!allowedMoves.contains(attemptedMove)){
//            System.out.println("That move is not allowed according to piece rules");
            return false;
        }

        //8. check if the the path is not blocked (except for horse)
        if(!movingPiece.canJump()){
            List<Pair<Integer,Integer>> intermediateMoves = movingPiece.getIntermediateList(attemptedMove);
            if(intermediateMoves != null){
                for (Pair<Integer, Integer> intermediateMove : intermediateMoves) {
                    Pair<Integer, Integer> Coords = new Pair<>(initCoords.getKey() + intermediateMove.getKey(), initCoords.getValue() + intermediateMove.getValue());
                    if (board.hasPiece(Coords)) {
//                        System.out.println("There is a piece in between");
                        return false;
                    }
                }
            }

        }

        if(shouldConsiderMate){
            return !kingInCheckAfterMove(initCoords, finalCoords);
        }

        return true;
    }



    public Map<Piece , List<Pair<Integer,Integer>>> allMoves(Colour colour,boolean shouldConsiderCheck){

        Map<Piece , List<Pair<Integer,Integer>>> allMoves = new HashMap<>();
        Map<Piece , List<Pair<Integer,Integer>>> filtered = new HashMap<>();

        if(colour == Colour.WHITE){
            whitePieces.forEach(e -> allMoves.put(e, e.getMoveList()));

            allMoves.forEach((k,v)-> filtered.put(k,filteredMoves(k,v,shouldConsiderCheck)));
        }
        else {
            blackPieces.forEach(e -> allMoves.put(e, e.getMoveList()));

            allMoves.forEach((k,v)-> filtered.put(k,filteredMoves(k,v,shouldConsiderCheck)));
        }

        return filtered;
    }



    public List<Pair<Integer,Integer>> filteredMoves(Piece piece, List<Pair<Integer,Integer>> uncheckedMoves ,boolean shouldConsiderCheck){
//        System.out.println("Checking piece " + piece + " at position " + piece.getCoords());
        List<Pair<Integer,Integer>> validMoves = new ArrayList<>();
        Pair<Integer,Integer> initCoords = piece.getCoords();
        uncheckedMoves.forEach(e -> {
            Pair<Integer,Integer> finalCoords = new Pair<>(initCoords.getKey() + e.getKey(), initCoords.getValue() + e.getValue());
//            System.out.println("Checking the move " + initCoords + " to " + finalCoords);
            if(checkMoveLegality(initCoords,finalCoords,shouldConsiderCheck) ){
//                System.out.println("that was valid");
                validMoves.add(e);
            }
        });
        return validMoves;
    }



    //Returns the captured so it can be used later to undo a move
    public Piece makeMove(Pair <Integer,Integer> initCoords, Pair <Integer,Integer> finalCoords){
        Piece capturedPiece = board.getPieceByCoords(finalCoords);
        Piece movingPiece = board.getPieceByCoords(initCoords);
        //System.out.println(capturedPiece);

        //Moves the piece on the board
        board.movePiece(initCoords, finalCoords);

        //Alters the internal coords of the piece
        movingPiece.move(finalCoords);

        //If a piece was captured it removes it
        if(capturedPiece != null){
            if(capturedPiece.getColour() == Colour.BLACK){
                blackPieces.remove(capturedPiece);
            }
            else {
                whitePieces.remove(capturedPiece);
            }
        }
        return capturedPiece;
    }

    public Piece getKing(){
        Piece king = null;
        if(currentPlayer.getColour() == Colour.WHITE){
            for(Piece piece: whitePieces){
                if(piece.getType() == Type.KING){
                    king = piece;
                }
            }
        }
        else{
            for(Piece piece: blackPieces){
                if(piece.getType() == Type.KING){
                    king = piece;                }
            }
        }
        return king;
    }

    public void undoMove(Pair <Integer,Integer> initCoords, Pair <Integer,Integer> finalCoords, Piece previouslyCaptured){

        //The piece has been moved to its final location
        Piece movedPiece = board.getPieceByCoords(finalCoords);

        board.movePiece(finalCoords,initCoords);
        movedPiece.move(initCoords);

        if(previouslyCaptured!=null){
            board.createPiece(previouslyCaptured);
            if(previouslyCaptured.getColour() == Colour.WHITE){
                whitePieces.add(previouslyCaptured);
            }
            else{
                blackPieces.add(previouslyCaptured);
            }
        }
    }


    public Pair <Integer,Integer> askInput(){

        //This functions asks for input and converts to a pair to represent the position in the matrix(E1 --> (4,0))
        String input;
        Pair <Integer,Integer> move;
        int x, y;
        //TO DO, Convert E3 , e3, e 3 into 43
        Scanner myScan = new Scanner(System.in);
        input = myScan.nextLine();
        input = input.toLowerCase();
        x = xConverter(input.charAt(0));
        y = input.charAt(1) - 49;
        move = new Pair<>(x, y);

        return move;
    }

    public int xConverter(char c){
        //Converts the x position to int ( A = 0, B = 1...)
        return  c-97;
    }

    public void createPieces(){
//        whitePieces.add(new Rock(new Pair<Integer, Integer> (0,0),Colour.WHITE));
//        whitePieces.add(new Knight(new Pair<Integer, Integer> (1,0),Colour.WHITE));
//        whitePieces.add(new Bishop(new Pair<Integer, Integer> (2,0),Colour.WHITE));
//        whitePieces.add(new Queen(new Pair<Integer, Integer> (3,0),Colour.WHITE));
//        whitePieces.add(new King(new Pair<Integer, Integer> (4,0),Colour.WHITE));
//        whitePieces.add(new Bishop(new Pair<Integer, Integer> (5,0),Colour.WHITE));
//        whitePieces.add(new Knight(new Pair<Integer, Integer> (6,0),Colour.WHITE));
//        whitePieces.add(new Rock(new Pair<Integer, Integer> (7,0),Colour.WHITE));
//
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (0,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (1,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (2,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (3,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (4,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (5,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (6,1),Colour.WHITE));
//        whitePieces.add(new Pawn(new Pair<Integer, Integer> (7,1),Colour.WHITE));
//
//        blackPieces.add(new Rock(new Pair<Integer, Integer> (0,7),Colour.BLACK));
//        blackPieces.add(new Knight(new Pair<Integer, Integer> (1,7),Colour.BLACK));
//        blackPieces.add(new Bishop(new Pair<Integer, Integer> (2,7),Colour.BLACK));
//        blackPieces.add(new Queen(new Pair<Integer, Integer> (3,7),Colour.BLACK));
//        blackPieces.add(new King(new Pair<Integer, Integer> (4,7),Colour.BLACK));
//        blackPieces.add(new Bishop(new Pair<Integer, Integer> (5,7),Colour.BLACK));
//        blackPieces.add(new Knight(new Pair<Integer, Integer> (6,7),Colour.BLACK));
//        blackPieces.add(new Rock(new Pair<Integer, Integer> (7,7),Colour.BLACK));
//
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (0,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (1,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (2,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (3,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (4,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (5,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (6,6),Colour.BLACK));
//        blackPieces.add(new Pawn(new Pair<Integer, Integer> (7,6),Colour.BLACK));

        whitePieces.add(new King(new Pair<>(4, 0),Colour.WHITE));
        blackPieces.add(new King(new Pair<>(7, 0),Colour.BLACK));
        whitePieces.add(new Queen(new Pair<>(6, 5),Colour.WHITE));

    }

    public void createPlayer(){
        players.add(new Player("Joao", Colour.WHITE));
        players.add(new Player("Paula", Colour.BLACK));
        currentPlayer = players.get(0);
    }
    public void changePlayer(){
        currentPlayer = players.get(1-players.indexOf(currentPlayer));
    }
    public void addPiecesToBoard(){
        whitePieces.forEach((e) -> board.createPiece(e));
        blackPieces.forEach((e) -> board.createPiece(e));
    }

    public void showBoard(){
        System.out.println(board);
    }

    @Override
    public String toString() {
        return board.toString();
    }


}


