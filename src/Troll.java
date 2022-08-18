public class Troll extends Zorde{

    public Troll(int HP, int[] location, String id, int ap) {

        super(HP,location, id, ap);

    }
    public int getAP() {

        return Constants.trollAP;

    }
    public int getMaxMove(){

        return Constants.trollMaxMove;

    }
}
