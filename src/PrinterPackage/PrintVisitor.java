package PrinterPackage;

import Interfaces.IMessageHandler;
import Interfaces.Visited;
import Interfaces.Visitor;
import GameObjects.Tiles.Empty;
import GameObjects.Tiles.Units.Enemies.Monster;
import GameObjects.Tiles.Units.Enemies.Trap;
import GameObjects.Tiles.Units.Players.Player;
import GameObjects.Tiles.Wall;

public class PrintVisitor implements Visitor {
    private IMessageHandler messageHandler;
    public PrintVisitor(IMessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
    }
    public void Start(Visited V)
    {
        V.execute(this);
    }

    public void engage(Trap e)
    {

    }
    public void engage(Monster e)
    {

    }
    @Override
    public void engage(Player p) {
        messageHandler.sendMessage(p.describe());
    }

    @Override
    public void engage(Wall w) {

    }

    @Override
    public void engage(Empty e)
    {

    }
}
