package SpecialAbility;

import Interfaces.*;
import GameObjects.Tiles.Empty;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Enemies.Enemy;

import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Units.Players.Warrior;
import GameObjects.Tiles.Wall;

import java.awt.*;
import java.util.List;

public class AvengersShield implements Visitor
{
    private Warrior warrior;
    private List<Tile> enemies;
    private Tile[][] board;
    private IMessageHandler messageHandler;
    public AvengersShield(Warrior warrior, List<Tile> enemies, Tile[][]board, IMessageHandler messageHandler)
    {
        this.warrior = warrior;
        this.enemies = enemies;
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

        int AttackRoll = warrior.getHealth().getPool() / 10;
        int defenceRoll = e.Defend();
        messageHandler.sendMessage(e.getName() + " rolled " + defenceRoll + " defence points ");
        if((AttackRoll-defenceRoll) > 0) {
            messageHandler.sendMessage(warrior.getName() + " dealt " +(AttackRoll-defenceRoll)+ " damage to "+ e.getName());
            e.takeDmg(AttackRoll - defenceRoll);
        }
        else
        {
            messageHandler.sendMessage(warrior.getName() + " dealt 0 damage to "+ e.getName());
        }
        if(!e.isAlive()) {
            messageHandler.sendMessage(e.getName() + " died. "+ warrior.getName()+ " gained " + e.getExpVal() + " experience");
            warrior.LootTheBody(e);
            Point eLocation = e.getPosition();
            Tile newTile = new Empty(eLocation);
            board[eLocation.x][eLocation.y] = newTile;
            enemies.remove(e);
        }
    }
}
