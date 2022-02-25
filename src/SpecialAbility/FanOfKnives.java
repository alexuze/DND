package SpecialAbility;

import Interfaces.IMessageHandler;
import Interfaces.Visited;
import Interfaces.Visitor;
import GameObjects.Tiles.Empty;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Enemies.Enemy;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Units.Players.Rogue;
import GameObjects.Tiles.Wall;

import java.awt.*;
import java.util.List;

public class FanOfKnives implements Visitor
{
    private Rogue rogue;
    private List<Tile> enemies;
    private List<Tile> inRangeEnemies;
    private Tile[][] board;
    private IMessageHandler messageHandler;
    public FanOfKnives(Rogue rogue,List<Tile> enemies,List<Tile>inRangeEnemies,Tile[][]board,IMessageHandler messageHandler)
    {
        this.rogue = rogue;
        this.enemies = enemies;
        this.board = board;
        this.messageHandler = messageHandler;
        this.inRangeEnemies = inRangeEnemies;
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
        int AttackRoll = rogue.getMaximumAttack();
        int defenceRoll = e.Defend();
        messageHandler.sendMessage(e.getName() + " rolled " + defenceRoll + " defence points ");
        if((AttackRoll-defenceRoll) > 0) {
            messageHandler.sendMessage(rogue.getName() + " dealt " +(AttackRoll-defenceRoll)+ " damage to "+ e.getName());
            e.takeDmg(AttackRoll - defenceRoll);
        }
        else
        {
            messageHandler.sendMessage(rogue.getName() + " dealt 0 damage to "+ e.getName());
        }
        if(!e.isAlive()) {
            messageHandler.sendMessage(e.getName() + " died. "+ rogue.getName()+ " gained " + e.getExpVal() + " experience");
            rogue.LootTheBody(e);
            Point eLocation = e.getPosition();
            Tile newTile = new Empty(eLocation);
            board[eLocation.x][eLocation.y] = newTile;
            enemies.remove(e);
            inRangeEnemies.remove(e);
        }
    }
}
