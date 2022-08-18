import java.io.*;

public class Commander {

    public static void commander(String commandFile,String outputFile, BoardData boardData){

        File file = new File(commandFile);
        try {

            String currentLine;

            BufferedReader br = new BufferedReader(new FileReader(file));

            Helper.write(outputFile,"",false);
            Helper.outputMake(boardData,outputFile);


            while ((currentLine=br.readLine())!= null){

                if (boardData.getAllCreatures().size()==1){//if just a one creature left on the board announce him as the winner

                    break;

                }

                try{
                    String id = currentLine.split(" ")[0];

                    Creatures creature = Helper.creatureFromId(boardData,id); //define the creature that makes move

                    String moveSeq= currentLine.split(" ")[1];

                    int moveLength = moveSeq.split(";").length;

                    int maxMove = Helper.getMaxMove(id);

                    //MaxMove check
                    if (maxMove != moveLength/2){throw  new MaxMoveException();}

                    //read the moves and perform
                    for (int i = 0; i <= moveLength-2 ; i=i+2) {

                        boolean mayIGo = true;

                        //determine the size of board
                        int size = boardData.getBoardMap().size();

                        //find exact location of the creature
                        assert creature != null;
                        int currentLocationX = creature.getLocationX();
                        int currentLocationY = creature.getLocationY();

                        //define where to go
                        String[] moveSeqArr=moveSeq.split(";");

                        int xToMove = Integer.parseInt(moveSeqArr[i]);
                        int yToMove = Integer.parseInt(moveSeqArr[i+1]);


                        //Boundary check
                        if ((currentLocationX+xToMove)>size || (currentLocationY+yToMove)>size || (currentLocationX+xToMove)<0 || (currentLocationY+yToMove)<0){

                            throw new BoundaryException(i);}


                        if (creature instanceof Ork){

                            //if it is first move of Ork, Heal the neighbors
                            if (i==0){

                                Move.healOrk(boardData, (Ork) creature);

                            }
                            //if it is last move, attack
                            if (i==moveLength-2){

                                mayIGo = Move.move(boardData, (Ork) creature,xToMove,yToMove,true);

                            }else{

                                mayIGo = Move.move(boardData, (Ork) creature,xToMove,yToMove,false);

                            }

                        }
                        else if (creature instanceof Troll){

                            if (i==moveLength-2){//troll attacks at final step

                                mayIGo = Move.move(boardData, (Troll) creature,xToMove,yToMove,true);

                            }
                        }
                        else if (creature instanceof Goblin){//goblin attacks at every step

                            mayIGo = Move.move(boardData, (Goblin) creature,xToMove,yToMove,true);

                        }
                        else if (creature instanceof Human){//human attacks at final step


                            if (i==moveLength-2){


                                mayIGo = Move.move(boardData, (Human) creature,xToMove,yToMove,true);

                            }else{

                                mayIGo = Move.move(boardData, (Human) creature,xToMove,yToMove,false);

                            }

                        }
                        else if (creature instanceof Elf) {//elf attacks at every step


                            if (i==moveLength-2){//if it is the last move of Elf, range attack the neighbors

                                mayIGo = Move.move(boardData, (Elf) creature,xToMove,yToMove,false);

                                //check if elf is alive and he didn't get out of a fightToDeath
                                if (boardData.getAllCreatures().contains(creature) && mayIGo){

                                    Move.rangeAttackElf(boardData, (Elf) creature);

                                }

                            }
                            else {//if it is not final, just attack

                                mayIGo = Move.move(boardData, (Elf) creature,xToMove,yToMove,true);

                            }

                        }
                        else if (creature instanceof Dwarf) {//dwarf attacks at every step

                            mayIGo = Move.move(boardData, (Dwarf) creature, xToMove, yToMove,true);

                        }

                        if (!mayIGo){//if creature tried to move onto a friend or just got out of a fightToDeath

                            throw new NoMovesAllowedException();//no further moves are allowed

                        }

                    }
            //create the appropriate output and write onto the file
                    Helper.outputMake(boardData,outputFile);


                }
                catch (MaxMoveException e) {
                    Helper.write(outputFile,e.message,true);
                }
                catch (BoundaryException e) {
                    if (e.i!=0){
                        Helper.outputMake(boardData,outputFile);
                    }
                    Helper.write(outputFile,e.message,true);

                }
                catch (NoMovesAllowedException e) {
                    Helper.outputMake(boardData,outputFile);

                }

            }
            String winner = boardData.getAllCreatures().get(0).getClass().getSuperclass().getSimpleName();
            Helper.write(outputFile,"\nGame Finished\n"+winner+" Wins",true);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//exceptions
class BoundaryException extends Exception{
    public final String message ="Error : Game board boundaries are exceeded. Input line ignored.\n\n";
    int i;
    public BoundaryException(int i) {
        this.i =i;

    }

}

class MaxMoveException extends  Exception{

    public final String message ="Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n";

    public MaxMoveException() {

    }
}

class NoMovesAllowedException extends Exception{

}