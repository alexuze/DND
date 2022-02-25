package Utilities;

public class Mana extends Resource {
    private Integer cost;
    public Mana (int pool,int cost)
    {
        super(pool,pool/4);
        this.cost = cost;
    }
    public Integer getCost()
    {
        return this.cost;
    }
    private void setCost(Integer cost)
    {
        this.cost =cost;
    }
    public void consumeMana(int i){
        resourceChange(-i);
    }
    public void LVLRefresh(int increment, int currChange){
        pool = pool + increment;
        resourceChange(currChange/4);
    }

}
