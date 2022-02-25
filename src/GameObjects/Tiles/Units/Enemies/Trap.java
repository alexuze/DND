package GameObjects.Tiles.Units.Enemies;

import Interfaces.Visitor;
import Utilities.Health;

public class Trap extends Enemy {
    private Integer visibilityTime;
    private Integer inVisibilityTime;
    private Integer ticksCount;
    private boolean visible;
    public Trap(String name, Health health /* need to check this */, Integer ATK, Integer DEF, Integer ExpVal, char c, Integer x, Integer y,Integer visibilityTime,Integer inVisibilityTime)
    {
        super(name, health, ATK, DEF, ExpVal, c, x, y);
        this.visibilityTime = visibilityTime;
        this.inVisibilityTime = inVisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }
    @Override
    public void execute(Visitor V) {
        V.engage(this);
    }

}
