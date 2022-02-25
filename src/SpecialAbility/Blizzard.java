package SpecialAbility;

import Interfaces.IMessageHandler;
import Interfaces.Visited;
import Interfaces.Visitor;
import GameObjects.Tiles.Empty;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Enemies.Enemy;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Mage;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Wall;

import java.awt.*;
import java.util.List;

public class Blizzard implements Visitor {

    private Mage mage;
    private List<Tile> enemies;
    private List<Tile> inRangeEnemies;
    private Tile[][] board;
    private IMessageHandler messageHandler;
    public Blizzard(Mage mage, List<Tile> enemies,List<Tile> inRangeEnemies, Tile[][]board, IMessageHandler messageHandler)
    {
        this.mage = mage;
        this.enemies = enemies;
        this.inRangeEnemies = inRangeEnemies;
        this.board = board;
        this.messageHandler = messageHandler;
    }
    public void Start(Visited V)
    {
        V.execute(this);
    }


    @Override
    public void engage(Trap t) {
        Cast(t);
    }

    @Override
    public void engage(Monster m) {
        Cast(m);
    }

    @Override
    public void engage(Player p) {

    }

    @Override
    public void engage(Wall w) {

    }

    @Override
    public void engage(Empty e) {

    }
    private void Cast(Enemy e)
    {
        int AttackRoll = mage.getSpellPower();
        int defenceRoll = e.Defend();
        messageHandler.sendMessage(e.getName() + " rolled " + defenceRoll + " defence points ");
        if((AttackRoll-defenceRoll) > 0) {
            messageHandler.sendMessage(mage.getName() + " dealt " +(AttackRoll-defenceRoll)+ " damage to "+ e.getName());
            e.takeDmg(AttackRoll - defenceRoll);
        }
        else
        {
            messageHandler.sendMessage(mage.getName() + " dealt 0 damage to "+ e.getName());
        }
        if(!e.isAlive()) {
            messageHandler.sendMessage(e.getName() + " died. "+ mage.getName()+ " gained " + e.getExpVal() + " experience");
            mage.LootTheBody(e);
            Point eLocation = e.getPosition();
            Tile newTile = new Empty(eLocation);
            board[eLocation.x][eLocation.y] = newTile;
            enemies.remove(e);
            inRangeEnemies.remove(e);
        }
    }
}
