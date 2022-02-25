package GameObjects.Tiles.Units.Players;

import Interfaces.IMessageHandler;
import Interfaces.SpecialAbilityVisitor;
import Interfaces.Visitor;
import Utilities.Health;

public class Rogue extends Player {
    private final String AbillityName = "Fan of Knives";

    private Integer cost;
    private Integer currentEnergy;
    private final Integer MAXMIMAL_ENERGY = 100;

    public Rogue(String name, Health health, int ATK, int DEF, Integer cost,Integer x, Integer y, IMessageHandler m) {
        super(name, health, ATK, DEF, x, y, m);
        this.cost = cost;

        this.currentEnergy = MAXMIMAL_ENERGY;
    }

    public void LVLUP() {
        super.LVLUP();
        currentEnergy = 100;
        attackPoints = attackPoints + 3 * Level;
        messageHandler.sendMessage(getName() + " reached level "+Level+ ": +"+10*Level+" Health, +"+7*Level+" Attack, +"+1*Level+" Defence");
    }

    @Override
    public boolean CastAbillity()
    {
        if(currentEnergy < cost)
        {
            messageHandler.sendMessage(getName() + " tried to cast "+ AbillityName + ", but there was not enough energy: "+ currentEnergy+"/"+cost);
            return false;
        }
        else
        {
            messageHandler.sendMessage(getName()+" used "+ AbillityName);
            currentEnergy -= cost;
            return true;
        }
    }
    public void execute(Visitor V) {
        V.engage(this);
    }
    @Override
    public void onGameTick() {
        currentEnergy = Math.min(currentEnergy + 10, 100);
    }

    @Override
    public String describe() {
        return super.describe() + " Level: " + Level + " Experience: " + experience + "/" + 50 * Level + " Energy: " + currentEnergy + "/100";

    }
    public Integer getMaximumAttack()
    {
        return this.attackPoints;
    }

    @Override
    public void execute(SpecialAbilityVisitor V) {
        V.engage(this);
    }
}