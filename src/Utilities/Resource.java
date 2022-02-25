package Utilities;

public class Resource {
    protected Integer pool;
    protected Integer amount;
    public Resource (int pool, int amount){
        this.pool = pool;
        if (amount > pool){//in case of amount being higher than Utilities.Health capacity
            this.amount = pool;
        }
        else
        {
            this.amount = amount;
        }
    }

    public void  setPool(int newPool){pool = newPool;}
    public Integer getPool(){return pool;}
    public void resourceChange(int change){
        Integer temp = amount + change;
        if (temp < 0){//in case of amount being lower than Utilities.Health capacity
            amount = 0;
        }
        else if (temp > pool){//in case of amount being higher than Utilities.Health capacity
            amount = pool;
        }
        else {
            amount = temp;
        }
    }
    public void setAmount(int newAmount)
    {
        if(newAmount <= pool)
        {
            if(newAmount >= 0)
                this.amount = newAmount;
            else
                this.amount = 0;
        }
        else
        {
            this.amount = pool;
        }

    }
    public Integer getAmount()
    {
        return this.amount;
    }
}
