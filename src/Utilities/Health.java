package Utilities;

public class Health extends Resource {

    public Health (int pool, int amount){
        super(pool,amount);
    }
    public void takeDMG(int dmg){
        resourceChange(-dmg);
    }
}
