package BusinessLayer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class NextAction implements ActionReader {
    private static NextAction INSTANCE = null;
    private boolean deterministic = false;
    private Queue<String> fromFile;

    private NextAction(){}

    public static NextAction getInstance(){
        if ( INSTANCE == null){
            INSTANCE = new NextAction();
        }
        return INSTANCE;
    }

    public void setDeterministic(){
        deterministic = true;
        fromFile = new LinkedList<>();
        loadFile();
    }

    private void loadFile(){
        try {
            List<String> temp = Files.readAllLines(Paths.get("user_input.txt"));
            fromFile.addAll(temp);
        } catch (Exception e){
            System.out.println("File \"user_input.txt\" not found");
        }
    }
    @Override
    public String nextAction() {
        if (deterministic)
            return fromFile.poll();
        else{
            String action = "";
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine())
                action = scanner.nextLine();
            return action;
        }
    }
}
