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
import java.util.Random;

public class EnemyVisitor implements Visitor {
    private Monster monster = null;
    private Trap trap = null;
    private Player player = null;
    private Tile[][] board;
    private IMessageHandler messageHandler;
    private Point playerP;
    public EnemyVisitor(Tile[][] board, IMessageHandler messageHandler,Point playerP)
    {
        this.board = board;
        this.messageHandler = messageHandler;
        this.playerP = playerP;
    }
    public void Start(Visited from)
    {
        board[playerP.x][playerP.y].execute(this);
        from.execute(this);
    }
    @Override
    public void engage(Trap t) {
        this.trap = t;
        if(trap != null)
        {
            if(player.getPosition().distance(trap.getPosition()) <= 2)
            {
                messageHandler.sendMessage(trap.getName() + " engaged in combat with " + player.getName() + " ");
                messageHandler.sendMessage(trap.describe());
                messageHandler.sendMessage(player.describe());
                int AttackRoll = trap.Attack();
                messageHandler.sendMessage(trap.getName() + " rolled " + AttackRoll + " attack points");

                int defenceRoll = player.Defend();
                messageHandler.sendMessage(player.getName() + " rolled " + defenceRoll + " defence points");

                if ((AttackRoll - defenceRoll) > 0) {
                    messageHandler.sendMessage(trap.getName() + " dealt " +(AttackRoll-defenceRoll)+ " damage to "+ player.getName());
                    player.takeDmg(AttackRoll - defenceRoll);
                }
                else
                {
                    messageHandler.sendMessage(trap.getName() + " dealt 0 damage to "+ player.getName());
                }
                if (!player.isAlive()) {
                    messageHandler.sendMessage(player.getName() + " was killed by "+ trap.getName());

                    player.setCharacter('X');
                }
            }
        }
    }
    public void engage(Monster m)
    {
        this.monster = m;
        if(monster != null)
        {
            Point monsterP = monster.getPosition();
            Point playerP = player.getPosition();
            MonsterMoveVisitor mv = new MonsterMoveVisitor(board,messageHandler);
            if(monsterP.distance(playerP) < monster.getVisionRange())
            {
                Integer dy = monsterP.x - playerP.x;
                Integer dx = monsterP.y - playerP.y;
                if(Math.abs(dx) > Math.abs(dy))
                {
                    if(dx > 0)
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x][monsterP.y-1]);
                    else
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x][monsterP.y+1]);
                }
                else
                {
                    if(dy > 0)
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x-1][monsterP.y]);
                    else
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x+1][monsterP.y]);
                }
            }
            else
            {
                Random r = new Random();
                Integer pick = r.nextInt(5);
                switch (pick)
                {
                    case 0:
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x-1][monsterP.y]);
                        break;
                    case 1:
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x+1][monsterP.y]);
                        break;
                    case 2:
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x][monsterP.y-1]);
                        break;
                    case 3:
                        mv.Start(board[monsterP.x][monsterP.y],board[monsterP.x][monsterP.y+1]);
                        break;
                    default:
                        break;
                }
            }
        }

    }
    @Override
    public void engage(Player p)
    {
        this.player = p;

    }

    @Override
    public void engage(Wall w) {

    }

    @Override
    public void engage(Empty e)
    {

    }
}
