package UnitsMovement;

import Interfaces.IMessageHandler;
import Interfaces.Visited;
import Interfaces.Visitor;
import GameObjects.Tiles.Empty;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Enemies.Enemy;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Wall;

import java.awt.*;
import java.util.List;

public class PlayerVisitor implements Visitor {
    private Player player=null;

    private Tile[][] board;
    private List<Tile> currEnemies;
    private IMessageHandler messageHandler;
    private boolean isSpecialAttack;
    public PlayerVisitor(Tile[][] board, List<Tile> currEnemies, IMessageHandler messageHandler)
    {
        this.board = board;
        this.currEnemies = currEnemies;
        this.messageHandler = messageHandler;

    }
    public void Start(Visited from, Visited V)
    {
        from.execute(this);
        if(player==null)
            return;
        V.execute(this);
    }

    public void engage(Trap e)
    {
        Fight(e);
    }
    public void engage(Monster e)
    {
        Fight(e);
    }
    @Override
    public void engage(Player p) {
        this.player = p;
    }

    @Override
    public void engage(Wall w) {

    }

    @Override
    public void engage(Empty e)
    {
        Point emptyLocation = e.getPosition();
        Point unitLocation = this.player.getPosition();
        this.player.setPosition(emptyLocation);
        e.setPosition(unitLocation);
        board[emptyLocation.x][emptyLocation.y] = this.player;
        board[unitLocation.x][unitLocation.y] = e;
    }
    private void Fight(Enemy e)
    {
        messageHandler.sendMessage(player.getName() + " engaged in combat with " + e.getName() + " ");
        messageHandler.sendMessage(player.describe());
        messageHandler.sendMessage(e.describe());
        int AttackRoll = player.Attack();
        messageHandler.sendMessage(player.getName() + "rolled " + AttackRoll + " attack points");

        int defenceRoll = e.Defend();
        messageHandler.sendMessage(e.getName() + "rolled " + defenceRoll + " defence points");

        if((AttackRoll-defenceRoll) > 0) {
            messageHandler.sendMessage(player.getName() + " dealt " +(AttackRoll-defenceRoll)+ " damage to "+ e.getName());

            e.takeDmg(AttackRoll - defenceRoll);
        }
        else
        {
            messageHandler.sendMessage(player.getName() + " dealt 0 damage to "+ e.getName());
        }
        if(!e.isAlive()) {
            messageHandler.sendMessage(e.getName() + " died. "+ player.getName()+ " gained " + e.getExpVal() + " experience");
            player.LootTheBody(e);
            currEnemies.remove(e);
            Point eLocation = e.getPosition();
            Tile newTile = new Empty(eLocation);

            Point unitLocation = this.player.getPosition();
            this.player.setPosition(eLocation);
            newTile.setPosition(unitLocation);
            board[eLocation.x][eLocation.y] = this.player;
            board[unitLocation.x][unitLocation.y] = newTile;
        }
    }
}
