package GameObjects.Tiles.Units.Players;


import Interfaces.IMessageHandler;
import Interfaces.SpecialAbilityVisited;
import Interfaces.Visited;
import GameObjects.Tiles.Units.Enemies.Enemy;
import GameObjects.Tiles.Units.Unit;
import Utilities.Health;

public abstract class Player extends Unit implements Visited, SpecialAbilityVisited {
    protected Integer experience;
    protected Integer Level;
    protected IMessageHandler messageHandler;

    public Player(String name, Health health /* need to check this */, Integer ATK, Integer DEF,Integer x,Integer y, IMessageHandler m) {
        super(name, health, ATK, DEF,'@',x,y);
        experience = 0;
        Level = 1;
        this.messageHandler = m;
    }
    public Player()
    {}
    private void CheckLVLUP() {
        if(experience >= (Level * 50)) {
            LVLUP();
        }
    }
    public void resetEXP(){
        experience = experience - Level * 50;
    }
    public void LVLUP(){
        resetEXP();
        Level = Level + 1;
        this.health.setPool(health.getPool() + 10 * Level);
        this.health.resourceChange(health.getPool());
        this.attackPoints = attackPoints + 4* Level;
        this.defensePoints = defensePoints + 1* Level;
    }

    public void LootTheBody(Enemy x){
        experience = experience + x.getExpVal();
        CheckLVLUP();
    }
    public abstract boolean CastAbillity();
    public abstract void onGameTick();

    @Override
    public boolean isPlayer()
    {
        return true;
    }




    @Override
    public boolean isEnemy() {
        return false;
    }


}
