public class Dwarf extends Calliance{
    public Dwarf(int HP, int[] location, String id, int ap) {

        super(HP,location, id, ap);

    }
    public int getAP() {

        return Constants.dwarfAP;

    }
    public int getMaxMove(){

        return Constants.dwarfMaxMove;

    }
}
