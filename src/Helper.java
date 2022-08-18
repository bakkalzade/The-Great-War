import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;


public class Helper {

    // E is Zorde or Calliance
    // K is the specific type of the creature
    public static <E extends Creatures, K extends E> void boardDataDesign(ArrayList<Creatures> allCreatures, Map<Integer, Creatures[]> boardMap, K creature){

        int[] location=creature.getLocation();

        //add the creature to all the arrays that used in program
        allCreatures.add(creature);

        //board map with creature objects
        Creatures[] oldArray=boardMap.get(location[1]);
        oldArray[location[0]]=creature;
        boardMap.put(location[1],oldArray);


    }

    public static Creatures creatureFromId(BoardData boardData, String id){

        ArrayList<Creatures> allCreatures = boardData.getAllCreatures();

        for (Creatures creature: allCreatures){

            if (creature.getID().equals(id)){

             return creature;

            }
        }
        //else
        return null;


    }

    public static int getMaxMove(String id){
        String type = id.substring(0,1);

        switch (type){


            case "O":

                return Constants.orkMaxMove;

            case "T":

                return Constants.trollMaxMove;

            case "G":

                return Constants.goblinMaxMove;

            case "H":

                return Constants.humanMaxMove;

            case "E":

                return Constants.elfMaxMove;

            case "D":

                return Constants.dwarfMaxMove;


        }
        //else
        return 0;
    }

    public static ArrayList<Creatures> getAllNeighbors(BoardData boardData, Creatures creatures, int ranger){

        ArrayList<int[]> neighbors= new ArrayList<>();
        ArrayList<Creatures> allNeighbors = new ArrayList<>();
        ArrayList<Integer> range = new ArrayList<>();

        int size = boardData.getBoardMap().size();


        int currentX = creatures.getLocationX();
        int currentY = creatures.getLocationY();

        //define the range of attack
        for (int i = -ranger; i <= ranger; i++) {

            range.add(i);
        }


        for (int i: range){

            for (int j: range){

                int possibleNeighborX = currentX+i;
                int possibleNeighborY = currentY+j;

                //don't add creatures' itself
                if (i == 0 && j == 0){

                    continue;
                }

                //boundary check
                if (possibleNeighborX>=0 && possibleNeighborY>=0 && possibleNeighborY< size && possibleNeighborX< size){

                    int[] position= {possibleNeighborX,possibleNeighborY};

                    neighbors.add(position);
                }
            }
        }

        for (int[] position: neighbors){

            int xPosition= position[0];
            int yPosition= position[1];

            Creatures neighbor;
            neighbor = boardData.getBoardMap().get(yPosition)[xPosition];

            //check the neighbor's existence
            if (neighbor!= null){

                allNeighbors.add(neighbor);

            }
        }
        return allNeighbors;

    }

    public static ArrayList<Creatures> getEnemyNeighbors(BoardData boardData, Creatures creatures, int ranger){

        ArrayList<Creatures> enemyNeighbors = new ArrayList<>();

        ArrayList<Creatures> allNeighbors = getAllNeighbors(boardData,creatures,ranger);

        for (Creatures neighbor: allNeighbors){

            //if they are not instances of same class add the neighbor as an enemy
            if (creatures instanceof Zorde && neighbor instanceof Calliance){

                enemyNeighbors.add(neighbor);

            }else if (creatures instanceof  Calliance && neighbor instanceof Zorde){

                enemyNeighbors.add(neighbor);

            }

        }

        return enemyNeighbors;

    }

    public static ArrayList<Creatures> getFriendNeighbors(BoardData boardData, Creatures creatures, int ranger){

        ArrayList<Creatures> friendNeighbors = new ArrayList<>();

        ArrayList<Creatures> allNeighbors = getAllNeighbors(boardData,creatures,ranger);

        for (Creatures neighbor: allNeighbors){

            //if they are instances of same class add the neighbor as friend
            if (creatures instanceof Zorde && neighbor instanceof Zorde){

                friendNeighbors.add(neighbor);

            }else if (creatures instanceof  Calliance && neighbor instanceof Calliance){

                friendNeighbors.add(neighbor);

            }

        }

        return friendNeighbors;
    }

    // it can be null check it
    public static Creatures whoIsThere(BoardData boardData, Creatures creatures, int xToMove, int yToMove){

        int xPosition = creatures.getLocationX();
        int yPosition = creatures.getLocationY();

        int xTarget = xToMove+xPosition;
        int yTarget = yToMove+yPosition;

        Creatures someone = boardData.getBoardMap().get(yTarget)[xTarget];

        return someone;//it can be null check it!!!
    }

    // the creatures shouldn't be equal to null !!!
    public static boolean isItFriend(Creatures creatures, Creatures creatures1){

        boolean isIt=false;

        if (creatures instanceof Zorde && creatures1 instanceof Zorde){

            isIt = true;

        }else if (creatures instanceof  Calliance && creatures1 instanceof Calliance){

            isIt = true;

        }

        return isIt;
    }

    public static void fightToDeath(BoardData boardData,Creatures attacker, Creatures target){

        int xTarget = target.getLocationX();
        int yTarget = target.getLocationY();

        //determine HP's and AP's
        int attackerHP = attacker.getHP();
        int attackerAP = attacker.getAP();

        //first attack of attacker char
        target.getDamage(attackerAP);
        int targetHP = target.getHP();

        //remove the one with lowest HP and define the new HP
        if (attackerHP> targetHP){

            youAreDead(boardData,target);
            attacker.setHP(attackerHP-targetHP);
            justMove(boardData,attacker,xTarget,yTarget);


        }else if (attackerHP< targetHP){

            youAreDead(boardData,attacker);
            target.setHP(targetHP-attackerHP);


        }else {//remove the both one

            youAreDead(boardData,target);
            youAreDead(boardData,attacker);

        }



    }

    public static void youAreDead(BoardData boardData, Creatures deadOne){

        //remove from allCreature arrayList
        ArrayList<Creatures> allCreatures = boardData.getAllCreatures();
        allCreatures.remove(deadOne);


        //remove from the boardMap
        Map<Integer,Creatures[]> boardMap = boardData.getBoardMap();
        Creatures[] rowArray = boardMap.get(deadOne.getLocationY());
        rowArray[deadOne.getLocationX()]=null;
        boardMap.put(deadOne.getLocationY(),rowArray);

        //set the new boardMap and allCreatures
        boardData.setBoardMap(boardMap);
        boardData.setAllCreatures(allCreatures);


    }

    public static void justMove(BoardData boardData, Creatures creatures, int xTarget ,int yTarget){

        int xOldLocation = creatures.getLocationX();
        int yOldLocation = creatures.getLocationY();

        //remove the old location from map
        Map<Integer, Creatures[]> boardMap = boardData.getBoardMap();
        Creatures[] oldRowArray = boardMap.get(yOldLocation);
        oldRowArray[xOldLocation] = null;
        boardMap.put(yOldLocation,oldRowArray);

        //design the new location in map
        Creatures[] rowArray = boardMap.get(yTarget);
        rowArray[xTarget]=creatures;
        boardMap.put(yTarget,rowArray);

        //set the new boardMap
        boardData.setBoardMap(boardMap);

        //set the creatures attributes
        creatures.setLocation(xTarget,yTarget);


    }

    public static void outputMake(BoardData boardData, String fileName){

        int size = boardData.getBoardMap().size();

        StringBuilder starStr= new StringBuilder();
        for (int i = 0; i < size*2+2 ; i++) {
            starStr.append("*");
        }

        StringBuilder boardStr= new StringBuilder();
        StringBuilder aliveStr= new StringBuilder();

        String allOutputStr;

        boardStr.append(starStr).append("\n");
        for (int i = 0; i < boardData.getBoardMap().size(); i++) {

            Creatures[] boardArray = boardData.getBoardMap().get(i);
            boardStr.append("*");

            for (Creatures creature:boardArray) {

                String id="  ";

                if (creature!= null){

                    id=creature.getID();

                }

                boardStr.append(id);

            }
            boardStr.append("*\n");
        }
        boardStr.append(starStr).append("\n").append("\n");


        ArrayList<Creatures> allCreatures = boardData.getAllCreatures();
        allCreatures.sort(new idComparator());//sort the list according to their id

        for (Creatures creature: allCreatures){

            aliveStr.append(creature.getID()).append("\t").append(creature.getHP()).append("\t").append("(").append(creature.getFirstHP()).append(")\n");

        }

        allOutputStr= boardStr.toString()+aliveStr.toString()+"\n";

        write(fileName,allOutputStr,true);



    }

    public static void write(String outputFile,String message,boolean append){

        BufferedWriter br;

        try {

            br = new BufferedWriter(new FileWriter(outputFile,append));
            br.write(message);
            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class idComparator implements Comparator<Creatures> {

    public idComparator() {
    }

    @Override
    public int compare(Creatures o1, Creatures o2) {

        return o1.getID().compareTo(o2.getID());


    }
}
