package PrinterPackage;

import Interfaces.IMessageHandler;

import java.util.Scanner;

public class CmdPrinter implements IMessageHandler {
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        Scanner in = new Scanner(System.in);
        String pick = in.nextLine();
        return pick;
    }
}
