package GameObjects.Tiles.Units.Enemies;


import GameObjects.Tiles.Units.Unit;
import Utilities.Health;

public abstract class Enemy extends Unit {
    private Integer ExpVal;

    public Enemy (String name, Health health /* need to check this */, Integer ATK, Integer DEF, Integer ExpVal,char c,Integer x,Integer y){
        super(name, health, ATK, DEF,c,x,y);
        this.ExpVal = ExpVal;
    }
    public Integer getExpVal() {
        return ExpVal;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
    @Override
    public boolean isEnemy() {
        return true;
    }
}
