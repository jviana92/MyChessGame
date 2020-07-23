package player;

import pieces.Colour;

public class Player {
    String name;
    Colour colour;


    public Player(String name,Colour colour) {
        this.name = name;
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }
}
