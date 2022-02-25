package GameObjects.Tiles;

import GameObjects.Tile;
import Interfaces.SpecialAbilityVisitor;
import Interfaces.Visitor;
import GameObjects.Tiles.Units.Unit;

import java.awt.*;

public class Empty extends Tile {

    public Empty(Integer x,Integer y)
    {
        super('.',x,y);
    }
    public Empty(Point p)
    {
        this(p.x,p.y);
    }
    public Unit getUnit()
    {
        return null;
    }
    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }
    public boolean isEmpty()
    {
        return true;
    }
    public boolean isWall()
    {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isEnemy() {
        return false;
    }
    @Override
    public String describe()
    {
        return "";
    }



    @Override
    public void execute(Visitor V) {
        V.engage(this);
    }

    @Override
    public void execute(SpecialAbilityVisitor V) {

    }
}
