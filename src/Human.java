public class Human extends Calliance{
    public Human(int HP, int[] location, String id, int ap) {

        super(HP,location, id, ap);

    }
    public int getAP() {

        return Constants.humanAP;

    }

    public int getMaxMove(){

        return Constants.humanMaxMove;

    }
}
