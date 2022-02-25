package GameObjects.Tiles.Units.Enemies;

import Interfaces.Visitor;
import Utilities.Health;

public class Monster extends Enemy {
    private Integer visionRange;
    public Monster(String name, Health health /* need to check this */, Integer ATK, Integer DEF, Integer ExpVal, char c, Integer x, Integer y,Integer visionRange)
    {
        super(name, health, ATK, DEF, ExpVal, c, x, y);
        this.visionRange = visionRange;
    }
    @Override
    public void execute(Visitor V) {
        V.engage(this);
    }

    public Integer getVisionRange()
    {
        return this.visionRange;
    }
}
