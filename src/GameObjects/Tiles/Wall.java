package GameObjects.Tiles;

import GameObjects.Tile;
import Interfaces.Visitor;

public class Wall extends Tile {



    public Wall(Integer x,Integer y)
    {
        super('#',x,y);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isWall() {
        return true;
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
    public void execute(Visitor V) {
        V.engage(this);
    }
    @Override
    public String describe()
    {
        return "";
    }
}
