package GamePackage;

import Interfaces.IMessageHandler;
import PrinterPackage.CmdPrinter;
import PrinterPackage.PrintVisitor;
import SpecialAbility.AbilityRedirect;
import GameObjects.Tiles.Empty;
import GameObjects.Tile;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Mage;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Units.Players.Rogue;
import GameObjects.Tiles.Units.Players.Warrior;
import GameObjects.Tiles.Wall;
import UnitsMovement.EnemyVisitor;
import UnitsMovement.PlayerVisitor;
import Utilities.Health;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GameManager {


    private List<String> LevelFiles;
    private Level currLevel;

    private Integer levelIndex=0;

    private boolean gameStatus = true;


    private IMessageHandler messageHandler;

    public GameManager() {
        this("", -1);
    }

    public GameManager(String path, int PlayerPick) {
        this.messageHandler = new CmdPrinter();
        try
        {
            this.LevelFiles = Files.list(Paths.get(path)).sorted().map(Path::toString).collect(Collectors.toList());
            messageHandler.sendMessage("You have selected: "+ Factory.getPlayer(PlayerPick,0,0).getName());
            Start(PlayerPick);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void Start(int playerPick)
    {
        while(levelIndex < LevelFiles.size())
        {
            try {
                List<String> currLevelData = Files.readAllLines(Paths.get(LevelFiles.get(levelIndex++)));
                currLevel = new Level(messageHandler);
                currLevel.InitializeLevel(currLevelData, playerPick);

            } catch (IOException e) {
                e.printStackTrace();
            }
            while (currLevel.getGameStatus() == true)
            {
                currLevel.Show();
                PrintVisitor printVisitor = new PrintVisitor(messageHandler);
                printVisitor.Start(currLevel.getActivePlayer());
                char move = messageHandler.getMessage().toCharArray()[0];
                currLevel.Move(move);
            }
            if(!currLevel.isPlayerAlive())
                break;
        }
        if(currLevel.isPlayerAlive())
        {
            messageHandler.sendMessage("You Won!");
        }
    }


    static class Factory {
        public static Tile getEnemy(char ch, int i, int j,int playerPick) {
            switch (ch) {
                case '@':
                    return getPlayer(playerPick,i,j);
                case '.':
                    return new Empty(i, j);
                case '#':
                    return new Wall(i, j);
                case 's':
                    return new Monster("Lannister Solider", new Health(80, 80), 8, 3, 25, 's', i, j, 3);
                case 'k':
                    return new Monster("Lannister Knight", new Health(200, 200), 14, 8, 50, 'k', i, j, 4);
                case 'q':
                    return new Monster("Queen’s Guard", new Health(400, 400), 20, 15, 100, 'q', i, j, 5);
                case 'z':
                    return new Monster("Wright", new Health(600, 600), 30, 15, 100, 'z', i, j, 3);
                case 'b':
                    return new Monster("Bear-Wright", new Health(1000, 1000), 75, 30, 250, 'b', i, j, 4);
                case 'g':
                    return new Monster("Giant-Wright", new Health(1500, 1500), 100, 40, 500, 'g', i, j, 5);
                case 'w':
                    return new Monster("White Walker", new Health(2000, 2000), 150, 50, 1000, 'w', i, j, 5);
                case 'M':
                    return new Monster("The Mountain", new Health(1000, 1000), 60, 25, 500, 'M', i, j, 6);
                case 'C':
                    return new Monster("Queen Cersei", new Health(100, 100), 10, 10, 1000, 'C', i, j, 1);
                case 'K':
                    return new Monster("Night’s King", new Health(5000, 5000), 300, 150, 5000, 'K', i, j, 8);
                case 'B':
                    return new Trap("Bonus Trap", new Health(1, 1), 1, 1, 250, 'B', i, j, 1, 5);
                case 'Q':
                    return new Trap("Queen’s Trap", new Health(250, 250), 50, 10, 100, 'Q', i, j, 3, 7);
                default:
                    return new Trap("Death Trap", new Health(500, 500), 100, 20, 250, 'D', i, j, 1, 10);
            }
        }
        public static Player getPlayer(int playerPick, int i, int j) {
            switch (playerPick) {
                case 1:
                    return new Warrior("Jon Snow", new Health(300, 300), 30, 4, 3, i, j, new CmdPrinter());
                case 2:
                    return new Warrior("The Hound", new Health(400, 400), 20, 6, 5, i, j, new CmdPrinter());
                case 3:
                    return new Mage("Melisandre", new Health(100, 100), 5, 1, 300, 30, 15, 5, 6, i, j, new CmdPrinter());
                case 4:
                    return new Mage("Thoros of Myr", new Health(250, 250), 25, 4, 150, 20, 20, 3, 4, i, j, new CmdPrinter());
                case 5:
                    return new Rogue("Arya Stark", new Health(150, 150), 40, 2, 20, i, j, new CmdPrinter());
                default:
                    return new Rogue("Bronn", new Health(250, 250), 35, 3, 50, i, j, new CmdPrinter());
            }
        }
    }
}


