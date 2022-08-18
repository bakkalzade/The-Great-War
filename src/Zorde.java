public class Zorde extends Creatures{
    public Zorde(int HP, int[] location, String id, int ap) {

        super(HP, location, id, ap);

    }
    public void getHeal(int healPoint){

        int maxHP = this.getFirstHP();

        if (maxHP<this.getHP()+healPoint){

            this.setHP(maxHP);

        }else {

            this.setHP(this.getHP()+healPoint);
        }
    }
}
