package GamePackage;

import GameObjects.Tile;
import Interfaces.IMessageHandler;
import PrinterPackage.PrintVisitor;
import SpecialAbility.AbilityRedirect;
import UnitsMovement.EnemyVisitor;
import UnitsMovement.PlayerVisitor;

import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Level
{
    private Tile[][] board;
    private List<Tile> activeEnemies;
    private static Tile activePlayer = null;
    private boolean gameStatus = true;
    private boolean playerLost = false;
    private IMessageHandler messageHandler;


    public Level(IMessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
        activeEnemies = new ArrayList<>();
    }
    public List<Tile> InitializeLevel(List<String> lvl, int playerPick)
    {
        int rowCount = lvl.size();
        int colCount = lvl.get(0).toCharArray().length;
        board = new Tile[rowCount][colCount];
        for(int i=0;i<rowCount;i++)
        {
            String row = lvl.get(i);
            for(int j=0;j<colCount;j++)
            {
                char ch = row.charAt(j);
                Tile toAdd = GameManager.Factory.getEnemy(ch,i,j,playerPick);
                board[i][j] = toAdd;
                if(toAdd.isEnemy())
                    activeEnemies.add(toAdd);
                if(toAdd.isPlayer()) {
                    if(activePlayer == null)
                        activePlayer = toAdd;
                    else
                        activePlayer.setPosition(new Point(i,j));
                }
            }
        }
        return activeEnemies;
    }
    public void Show()
    {
        messageHandler.sendMessage(toString());
    }
    public void Move(char move)
    {
        if (!activeEnemies.isEmpty() && gameStatus)
        {
            PlayerVisitor p = new PlayerVisitor(board, activeEnemies, messageHandler);
            AbilityRedirect Ability = new AbilityRedirect(board,activeEnemies,messageHandler);
            Point playerLocation = activePlayer.getPosition();
            switch (move) {
                case 'w':
                    p.Start(activePlayer, board[playerLocation.x - 1][playerLocation.y]);
                    break;
                case 'a':
                    p.Start(activePlayer, board[playerLocation.x][playerLocation.y - 1]);
                    break;
                case 's':
                    p.Start(activePlayer, board[playerLocation.x + 1][playerLocation.y]);
                    break;
                case 'd':
                    p.Start(activePlayer, board[playerLocation.x][playerLocation.y + 1]);
                    break;
                case 'e':
                    Ability.Start(activePlayer);
                    break;
                default:
                    break;
            }
            playerLocation = activePlayer.getPosition();
            EnemyVisitor e = new EnemyVisitor(board, messageHandler, playerLocation);
            for (Tile tile : activeEnemies) {
                e.Start(tile);
                if (board[playerLocation.x][playerLocation.y].getCharacter() == 'X') {
                    messageHandler.sendMessage("You Lost");
                    gameStatus = false;
                    playerLost = true;
                    break;
                }
            }

        }
        if (!gameStatus && playerLost) {
            PrintVisitor printVisitor = new PrintVisitor(messageHandler);
            printVisitor.Start(activePlayer);
            messageHandler.sendMessage("Game Over.");
        }
        else if(activeEnemies.isEmpty())
        {
            gameStatus = false;
        }
    }
    @Override
    public String toString()
    {
        String res = "";
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j < board[i].length;j++)
            {
                res += board[i][j].toString();
            }
            res += "\n";
        }
        return res;
    }
    public boolean isPlayerAlive()
    {
        return playerLost==false;
    }

    public boolean getGameStatus()
    {
        return this.gameStatus;
    }
    public Tile getActivePlayer()
    {
        return activePlayer;
    }

}
