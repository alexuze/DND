package SpecialAbility;

import Interfaces.IMessageHandler;
import Interfaces.SpecialAbilityVisited;
import Interfaces.SpecialAbilityVisitor;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Players.Mage;
import GameObjects.Tiles.Units.Players.Rogue;
import GameObjects.Tiles.Units.Players.Warrior;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// this class helps determine which hero used his special ability
public class AbilityRedirect implements SpecialAbilityVisitor {
    List<Tile> Potentialenemies;
    List<Tile> inRangeEnemies;
    private IMessageHandler messageHandler;
    private Tile[][] board;
    public AbilityRedirect(Tile[][] board, List<Tile> enemies, IMessageHandler messageHandler)
    {
        this.board = board;
        this.Potentialenemies = enemies;
        this.messageHandler = messageHandler;
    }
    public void Start(SpecialAbilityVisited from)
    {
        from.execute(this);
    }

    @Override
    public void engage(Mage m)
    {
        boolean b = m.CastAbillity();
        if(b)
        {
            inRangeEnemies = new ArrayList<>();
            for (Tile t : Potentialenemies) {
                if (t.getPosition().distance(m.getPosition()) < m.getAbillityRange()) {
                    inRangeEnemies.add(t);
                }
            }
            Blizzard mageAbility = new Blizzard(m,Potentialenemies,inRangeEnemies,board,messageHandler);
            Random r = new Random();
            Tile toAttack;
            Integer pick;
            Integer hitsCount = m.getHitsCount();
            while(!inRangeEnemies.isEmpty() && hitsCount > 0)
            {
                pick = r.nextInt(inRangeEnemies.size());
                toAttack = inRangeEnemies.get(pick);
                mageAbility.Start(toAttack);
                hitsCount--;
            }
        }
    }

    @Override
    public void engage(Warrior w)
    {
        boolean b = w.CastAbillity();
        if(b)
        {
            inRangeEnemies = new ArrayList<>();
            for (Tile t : Potentialenemies) {
                if (t.getPosition().distance(w.getPosition()) < 3) {
                    inRangeEnemies.add(t);
                }
            }
            if(!inRangeEnemies.isEmpty()) {
                Random r = new Random();
                Integer pick = r.nextInt(inRangeEnemies.size());
                Tile toAttack = inRangeEnemies.get(pick);
                AvengersShield warriorAbility = new AvengersShield(w, Potentialenemies, board, messageHandler);
                warriorAbility.Start(toAttack);
            }
        }
    }

    @Override
    public void engage(Rogue r) {
        boolean b = r.CastAbillity();
        if(b)
        {
            inRangeEnemies = new ArrayList<>();
            for (Tile t : Potentialenemies) {
                if (t.getPosition().distance(r.getPosition()) <= 2) {
                    inRangeEnemies.add(t);
                }
            }
            if(!inRangeEnemies.isEmpty())
            {
                FanOfKnives rougeAbility = new FanOfKnives(r,Potentialenemies,inRangeEnemies,board,messageHandler);
                for(int i=0;i<inRangeEnemies.size();i++)
                {
                    if(inRangeEnemies.get(i) != null)
                        rougeAbility.Start(inRangeEnemies.get(i));
                }
            }
        }
    }
}
