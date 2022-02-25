package GameObjects.Tiles.Units;

import GameObjects.Tile;
import Utilities.Health;

import java.awt.*;

public abstract class Unit extends Tile{
    protected String name;
    protected Health health;
    protected Integer attackPoints;
    protected Integer defensePoints;

    public Unit(String name, Health health /* need to check this */, Integer ATK, Integer DEF,char c,Integer x,Integer y) {
        super(c,x,y);
        this.name = name;
        this.health = health;
        attackPoints = ATK;
        defensePoints = DEF;
    }
    public Unit()
    {
        this.name = "";
        this.health = new Health(0,0);
        attackPoints = 0;
        defensePoints = 0;
    }
    @Override
    public String describe()
    {
        return name +" Health: " + health.getAmount() + "/" + health.getPool() + " Attack: "+ attackPoints + " Defence: " + defensePoints;
    }
    public int Attack() {
        return (int) (Math.random() * this.attackPoints);
    }

    public int Defend() {
        return (int) (Math.random() * this.defensePoints);
    }
    public void takeDmg(int dmg)
    {
        this.health.setAmount(health.getAmount()-dmg);
    }
    public boolean isAlive()
    {
        return health.getAmount() > 0;
    }
    public void setPosition(Point newPos)
    {
        super.setPosition(newPos);
    }
    public Health getHealth()
    {
        return this.health;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isWall() {
        return false;
    }

    public abstract boolean isPlayer();

    public Point getPosition()
    {
        return super.getPosition();
    }
    public String getName()
    {
        return this.name;
    }


}
enum Action
{
    Stay,Fight,Move
}
