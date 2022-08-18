public class Elf extends Calliance{
    public Elf(int HP, int[] location, String id, int ap) {

        super(HP,location, id, ap);

    }
    public int getAP() {

        return Constants.elfAP;

    }
    public int getMaxMove(){

        return Constants.elfMaxMove;

    }
    public int getRangeAP(){

        return Constants.elfRangedAP;

    }
    public int getRange(){

        return Constants.elfRange;
    }
}
