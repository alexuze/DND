package GameObjects.Tiles.Units.Players;

import Interfaces.IMessageHandler;
import Interfaces.SpecialAbilityVisitor;
import Interfaces.Visitor;
import Utilities.Health;
import Utilities.Mana;

public class Mage extends Player  {
    private final String AbillityName = "Blizzard";
    private Mana mana;
    private Integer spellPower;
    private Integer hitsCount;
    private Integer abillityRange;

    public Mage(String name, Health health /* need to check this */, Integer ATK, Integer DEF, Integer manaPool,Integer manaCost, Integer spellPower,Integer hitsCount,Integer range,Integer x,Integer y, IMessageHandler m) {
        super(name, health, ATK, DEF,x,y,m);
        mana = new Mana(manaPool,manaCost);
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abillityRange = range;
    }

    public void LVLUP(){
        super.LVLUP();
        int pool = mana.getPool();
        mana.LVLRefresh( pool + 25 * Level, pool/4);
        spellPower = spellPower + (10 * Level);
        messageHandler.sendMessage(getName() + " reached level "+Level+ ": +"+10*Level+" Health, +"+4*Level+" Attack, +"+1*Level+" Defence, +"+25*Level+" Maximum mana, +"+10*Level +" Spell Power" );

    }

    @Override
    public boolean CastAbillity()
    {
        if(mana.getAmount() < mana.getCost())
        {
            messageHandler.sendMessage(getName() + " tried to cast "+ AbillityName + ", but there was not enough mana: "+ mana.getAmount()+"/"+mana.getCost());
            return false;
        }
        else {
            messageHandler.sendMessage(getName() + " used " + AbillityName);
            mana.setAmount(mana.getAmount() - mana.getCost());
            return true;
        }
    }
    public void execute(Visitor V) {
        V.engage(this);
    }
    @Override
    public void onGameTick()
    {
        mana.setAmount(Math.min(mana.getPool(),mana.getAmount()+Level));
    }
    @Override
    public String describe()
    {
        return super.describe() +" Level: " + Level + " Experience: "+ experience +"/" + 50*Level+ " Mana: "+ mana.getAmount()+"/"+mana.getPool() + " Spell power: "+ spellPower;
    }

    @Override
    public void execute(SpecialAbilityVisitor V) {
        V.engage(this);
    }
    public Integer getSpellPower()
    {
        return this.spellPower;
    }
    public Integer getAbillityRange()
    {
        return this.abillityRange;
    }
    public Integer getHitsCount()
    {
        return this.hitsCount;
    }

}

