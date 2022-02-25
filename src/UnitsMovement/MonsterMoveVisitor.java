package UnitsMovement;

import Interfaces.IMessageHandler;
import Interfaces.Visited;
import Interfaces.Visitor;
import GameObjects.Tiles.Empty;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Wall;

import java.awt.*;

public class MonsterMoveVisitor implements Visitor{
    private Tile[][] board;
    private IMessageHandler messageHandler;
    private Monster monster;
    public MonsterMoveVisitor(Tile[][] board, IMessageHandler messageHandler)
    {
        this.board = board;
        this.messageHandler = messageHandler;
    }
    public void Start(Visited from, Visited to)
    {
        from.execute(this);
        if(monster == null)
            return;
        to.execute(this);
    }

    @Override
    public void engage(Trap t) {

    }

    @Override
    public void engage(Monster m) {
        this.monster = m;
    }

    @Override
    public void engage(Player p) {
        if(monster != null)
        {
            messageHandler.sendMessage(monster.getName() + " engaged in combat with " + p.getName() + " ");
            messageHandler.sendMessage(monster.describe());
            messageHandler.sendMessage(p.describe());
            int AttackRoll = monster.Attack();
            messageHandler.sendMessage(monster.getName() + " rolled " + AttackRoll + " attack points ");

            int defenceRoll = p.Defend();
            messageHandler.sendMessage(p.getName() + " rolled " + defenceRoll + " defence points ");

            if ((AttackRoll - defenceRoll) > 0) {
                messageHandler.sendMessage(monster.getName() + " dealt " +(AttackRoll-defenceRoll)+ " damage to "+ p.getName());

                p.takeDmg(AttackRoll - defenceRoll);
            }
            else
            {
                messageHandler.sendMessage(monster.getName() + " dealt 0 damage to "+ p.getName());
            }
            if (!p.isAlive()) {
                messageHandler.sendMessage(p.getName() + " was killed by " + monster.getName());
                p.setCharacter('X');
            }
        }
    }

    @Override
    public void engage(Wall w)
    {

    }

    @Override
    public void engage(Empty e)
    {
        if(monster!=null)
        {
            Point emptyLocation = e.getPosition();
            Point unitLocation = this.monster.getPosition();
            this.monster.setPosition(emptyLocation);
            e.setPosition(unitLocation);
            board[emptyLocation.x][emptyLocation.y] = this.monster;
            board[unitLocation.x][unitLocation.y] = e;
        }
    }
}
