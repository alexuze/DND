package GameObjects.Tiles.Units.Players;

import Interfaces.IMessageHandler;
import Interfaces.SpecialAbilityVisitor;
import Interfaces.Visitor;
import Utilities.Health;

public class Warrior extends Player {
    private final String AbillityName = "Avenger’s Shield";

    private Integer abilityCooldown;
    private Integer remainingCooldown;
    public Warrior(String name, Health health, int ATK, int DEF, Integer abilityCooldown,Integer x,Integer y, IMessageHandler m)
    {
        super(name,health,ATK,DEF,x,y,m);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }
    public void LVLUP()
    {
        super.LVLUP();
        remainingCooldown = 0;
        health.setPool(health.getPool()+5*Level);
        health.setAmount(health.getPool());
        attackPoints = attackPoints + 2*Level;
        defensePoints = defensePoints+ Level;
        messageHandler.sendMessage(getName() + " reached level "+Level+ ": +"+15*Level+" Health, +"+6*Level+" Attack, +"+2*Level+" Defence");

    }
    @Override
    public boolean CastAbillity() {
        if(remainingCooldown > 0) {
            messageHandler.sendMessage(getName() + " tried to cast "+AbillityName+ ", but there is a cooldown: "+remainingCooldown );
            return false;
        }
        remainingCooldown = abilityCooldown;
        int healedFor = AbilityHealing();
        messageHandler.sendMessage(getName()+" used "+ AbillityName + " healing for "+ healedFor);
        return true;
    }
    @Override
    public void onGameTick()
    {
        if(remainingCooldown > 0)
            remainingCooldown--;
    }
    public void execute(Visitor V) {
        V.engage(this);
    }
    @Override
    public String describe()
    {
        return super.describe() +" Level: " + Level + " Experience: "+ experience +"/" + 50*Level+ " Cooldown: "+remainingCooldown+"/"+abilityCooldown;
    }

    @Override
    public void execute(SpecialAbilityVisitor V) {
        V.engage(this);
    }
    public String getAbillityName()
    {
        return this.AbillityName;
    }
    public int AbilityHealing()
    {
        int AfterhealingAmount = health.getAmount()+10*defensePoints;
        health.setAmount(AfterhealingAmount);
        return 10*defensePoints;
    }

}
