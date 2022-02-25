import GamePackage.GameManager;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        System.out.println("Please choose a player:");
        System.out.println("1. Jon Snow             Health: 300/300         Attack: 30              Defense: 4              Level: 1                Experience: 0/50                Cooldown: 0/3");
        System.out.println("2. The Hound            Health: 400/400         Attack: 20              Defense: 6              Level: 1                Experience: 0/50                Cooldown: 0/5");
        System.out.println("3. Melisandre           Health: 100/100         Attack: 5               Defense: 1              Level: 1                Experience: 0/50                Mana: 75/300            Spell Power: 15\n");
        System.out.println("4. Thoros of Myr        Health: 250/250         Attack: 25              Defense: 4              Level: 1                Experience: 0/50                Mana: 37/150            Spell Power: 20\n");
        System.out.println("5. Arya Stark           Health: 150/150         Attack: 40              Defense: 2              Level: 1                Experience: 0/50                Energy: 100/100\n");
        System.out.println("6. Bronn                Health: 250/250         Attack: 35              Defense: 3              Level: 1                Experience: 0/50                Energy: 100/100\n");
        Scanner in = new Scanner(System.in);
        Integer pick = Integer.parseInt(in.nextLine());
        if(pick>0 && pick < 6)
        {
            GameManager DND = new GameManager(args[0],pick);
        }
    }
}
