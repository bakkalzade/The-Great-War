public class Goblin extends Zorde{
    public Goblin(int HP, int[] location, String id, int ap) {

        super(HP,location, id, ap);

    }
    public int getAP() {

        return Constants.goblinAP;

    }
    public int getMaxMove(){

        return Constants.goblinMaxMove;

    }
}
