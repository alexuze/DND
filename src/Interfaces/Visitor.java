package Interfaces;

import GameObjects.Tiles.Empty;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Wall;

public interface Visitor {
    void engage(Trap t);
    void engage(Monster m);
    void engage(Player p);
    void engage(Wall w);
    void engage(Empty e);
}
