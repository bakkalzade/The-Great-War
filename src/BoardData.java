import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardData {
    private ArrayList<Creatures> allCreatures;
    private Map<Integer, Creatures[]> boardMap;


    public BoardData(String fileName) {

        ArrayList<Creatures> allCreatures=new ArrayList<>();// list keeps all the alive creatures
        Map<Integer, Creatures[]> boardMap = new HashMap<>();//map keeps all creatures as a board

        File file = new File(fileName);
        int size;

        try {

            String currentLine;

            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((currentLine=br.readLine())!= null){

                if (currentLine.equals("BOARD")){

                    size = Integer.parseInt(br.readLine().split("x")[0]);

                    for (int i = 0; i < size; i++) {

                        Creatures[] rowArrayCre= new Creatures[size];

                        boardMap.put(i,rowArrayCre);

                    }

                }
                if (currentLine.equals("CALLIANCE")){

                    String callianceLine;

                    while ( !(callianceLine= br.readLine()).equals("")){

                        String type = callianceLine.split(" ")[0];

                        String id = callianceLine.split(" ")[1];

                        int[] location= new int[2];

                        location[0] = Integer.parseInt(callianceLine.split(" ")[2]);

                        location[1] = Integer.parseInt(callianceLine.split(" ")[3]);

                        Calliance calliance = new Calliance(0,location,"", 0);

                        //create objects according to their name.
                        switch (type){

                            case "HUMAN":

                                calliance = new Human(Constants.humanHP,location,id,Constants.humanAP);

                                break;

                            case "ELF":

                                calliance = new Elf(Constants.elfHP,location,id,Constants.elfAP);

                                break;

                            case "DWARF":

                                calliance = new Dwarf(Constants.dwarfHP,location,id,Constants.dwarfAP);

                                break;

                        }

                        Helper.boardDataDesign(allCreatures,boardMap,calliance);
                    }

                }
                if (currentLine.equals("ZORDE")){

                    String zordeLine;

                    while ((zordeLine= br.readLine())!= null && !zordeLine.equals("")){


                        String type = zordeLine.split(" ")[0];

                        String id = zordeLine.split(" ")[1];

                        int[] location= new int[2];

                        location[0] = Integer.parseInt(zordeLine.split(" ")[2]);

                        location[1] = Integer.parseInt(zordeLine.split(" ")[3]);

                        Zorde zorde = new Zorde(0,location,"", 0);

                        switch (type){

                            case "ORK":

                                zorde = new Ork(Constants.orkHP,location,id,Constants.orkAP);

                                break;

                            case "TROLL":

                                zorde = new Troll(Constants.trollHP,location,id,Constants.trollAP);

                                break;

                            case "GOBLIN":

                                zorde = new Goblin(Constants.goblinHP,location,id,Constants.goblinAP);

                                break;

                        }

                        Helper.boardDataDesign(allCreatures,boardMap,zorde);

                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.allCreatures = allCreatures;
        this.boardMap = boardMap;

    }

    public ArrayList<Creatures> getAllCreatures() {
        return allCreatures;
    }

    public void setAllCreatures(ArrayList<Creatures> allCreatures) {
        this.allCreatures = allCreatures;
    }

    public Map<Integer, Creatures[]> getBoardMap() {
        return boardMap;
    }

    public void setBoardMap(Map<Integer, Creatures[]> boardMap) {
        this.boardMap = boardMap;
    }


}

