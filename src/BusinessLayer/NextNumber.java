package BusinessLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NextNumber implements RandomGenerator{

    static NextNumber INSTANCE = null;
    boolean deterministic = false;
    Queue<Integer> fromFile;

    private NextNumber(){
    }

    public static NextNumber getInstance(){
        if ( INSTANCE == null){
            INSTANCE = new NextNumber();
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
            List <String> temp = Files.readAllLines(Paths.get("random_numbers.txt"));
            for (int i = 0; i < temp.size(); i++) {
                fromFile.add(Integer.parseInt(temp.get(i)));
            }
        } catch (Exception e){
            System.out.println("File \"random_numbers.txt\" not found");
        }
        }
    @Override
    public int nextInt(int n) {
        if(deterministic)
            return fromFile.poll();
        else
            return (int)(Math.random()*n);
    }
}
