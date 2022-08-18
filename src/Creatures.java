public abstract class Creatures {
    private int HP;
    private int[] location;
    private String ID;
    private int AP;
    private final int firstHP;

    public Creatures(int HP, int[] location, String id, int ap) {
        this.HP = HP;
        this.location = location;
        ID = id;
        AP = ap;
        firstHP=HP;
    }

    public int getHP() {

        return HP;

    }

    public void setHP(int HP) {

        this.HP = HP;

    }
    public void getDamage(int damage){

        HP = HP-damage;

        if (HP<=0){
            HP=0;
        }
    }
    public int getMaxMove(){
        return 0;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public String getID() {
        return ID;
    }

    public int getLocationX(){
        return this.location[0];
    }

    public void setLocation(int x, int y){
        this.location[0]=x;
        this.location[1]=y;

    }


    public int getLocationY(){
        return this.location[1];
    }

    public int getAP() {

        return AP;
    }

    public int getFirstHP() {
        return firstHP;
    }
}
