package BusinessLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class NextAction implements ActionReader {
    static NextAction INSTANCE = null;
    boolean deterministic = false;
    Queue<String> fromFile;

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
            //FIXME: where the file will sit?
            BufferedReader br = new BufferedReader(
                    new FileReader("C:\\Users\\Netanel\\Dropbox\\Degree\\Freshment year\\Principles of object oriented programming\\Assignments\\3\\DND\\deterministic\\user_input.txt"));
            String line ="";
            while((line = br.readLine()) != null){
                fromFile.add(line);
            }
        } catch (Exception e){
            System.out.println("File not found");
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
