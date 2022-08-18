public class Ork extends Zorde{

    public Ork(int HP, int[] location, String id, int ap) {

        super(HP,location, id, ap);

    }

    public int getAP() {

        return Constants.orkAP;

    }
    public int getMaxMove(){

        return Constants.orkMaxMove;

    }
    public int getHealPoint(){

        return Constants.orkHealPoints;

    }
}
