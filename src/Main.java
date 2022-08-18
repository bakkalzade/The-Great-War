
public class Main {

    public static void main(String[] args){

        String initialFile = args[0];
        String commandFile = args[1];
        String outputFile = args[2];

        BoardData boardData = new BoardData(initialFile);
        Commander.commander(commandFile,outputFile,boardData);

    }
}
