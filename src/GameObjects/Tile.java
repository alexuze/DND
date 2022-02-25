package GameObjects;

import Interfaces.SpecialAbilityVisited;
import Interfaces.SpecialAbilityVisitor;
import Interfaces.Visited;
import GameObjects.Tiles.Units.Unit;

import java.awt.*;
public abstract class Tile implements Visited,SpecialAbilityVisited {
    /*
    type(get,set) , point , Interfaces.IUnit (null if wall or empty) ,
     */
    private char character;
    private Point Position;
    protected Unit unit = null;

    public Tile (char c, Integer x, Integer y){
        character = c;
        if( x!= null && y != null)
            Position = new Point(x,y);
        else
            Position = new Point();
    }
    public Tile()
    {

    }
    public char getCharacter() {
        return character;
    }

    public void setCharacter (char c){
        character = c;
    }
    public Point getPosition() {
        return Position;
    }
    public void setPosition(Point newPosition)
    {
        this.Position = newPosition;
    }

    @Override
    public String toString()
    {
        return String.valueOf(character);
    }
    public abstract boolean isEmpty();
    public abstract boolean isWall();
    public abstract boolean isPlayer();
    public abstract boolean isEnemy();
    public abstract String describe();
    public void execute(SpecialAbilityVisitor V)
    {}


}

