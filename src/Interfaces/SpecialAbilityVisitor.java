package Interfaces;

import GameObjects.Tiles.Units.Players.Mage;
import GameObjects.Tiles.Units.Players.Rogue;
import GameObjects.Tiles.Units.Players.Warrior;

// this interface helps determine which character used it's special ability
public interface SpecialAbilityVisitor
{
    void engage(Mage m);
    void engage(Warrior w);
    void engage(Rogue r);
}
