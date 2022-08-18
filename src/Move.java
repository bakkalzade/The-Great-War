import java.util.ArrayList;

public class Move {

    public static <E extends Creatures,K extends E> boolean move(BoardData boardData,K creature, int xToMove, int yToMove,boolean attack){

        boolean MayIGoFurther;
        boolean isItFriend;

        //find exact location of the creature
        int currentLocationX = creature.getLocationX();
        int currentLocationY = creature.getLocationY();

        // define target location
        int xTarget = currentLocationX+xToMove;
        int yTarget = currentLocationY+yToMove;

        //who is on the location that we want to move
        Creatures ownerOfPlace = Helper.whoIsThere(boardData,creature,xToMove,yToMove);

        //if no one is there
        if (ownerOfPlace== null){

            MayIGoFurther = true;//a further move is allowed

            Helper.justMove(boardData,creature,xTarget,yTarget);

            if (attack){

                ArrayList<Creatures> enemyNeighbors = Helper.getEnemyNeighbors(boardData,creature,1);

                attackTheNeighbors(boardData,enemyNeighbors,creature.getAP());

            }



        }else {//if someone is there

            MayIGoFurther=false;//no further moves are allowed

            //determine whether it is an enemy or friend
            isItFriend = Helper.isItFriend(creature, ownerOfPlace);

            if (!isItFriend){//if the owner of place is enemy fight to death

                Helper.fightToDeath(boardData,creature,ownerOfPlace);

            }
            //if the owner of place is friend don't move any further.

            return MayIGoFurther;

        }


        return MayIGoFurther;

    }

    public static void healOrk(BoardData boardData,Ork ork){

        int healPoint= ork.getHealPoint();
        ArrayList<Creatures> friendNeighbors= Helper.getFriendNeighbors(boardData,ork,1);

        ork.getHeal(healPoint);

        for (Creatures friend: friendNeighbors){

            if(friend instanceof Zorde){

                ((Zorde) friend).getHeal(healPoint);//add the healPoint to friend's HP

            }
        }
    }

    public static void rangeAttackElf(BoardData boardData,Elf elf){

        int range = elf.getRange();
        int rangeAP = elf.getRangeAP();

        ArrayList<Creatures> enemyNeighbors= Helper.getEnemyNeighbors(boardData,elf,range);
        attackTheNeighbors(boardData,enemyNeighbors, rangeAP);

    }

    public static void attackTheNeighbors(BoardData boardData, ArrayList<Creatures> enemyNeighbors, int AP){

        for (Creatures enemy: enemyNeighbors){

            enemy.getDamage(AP);//attack the neighbor

            if (enemy.getHP()==0){//if he is dead make it feel:)

                Helper.youAreDead(boardData,enemy);

            }
        }
    }

}
