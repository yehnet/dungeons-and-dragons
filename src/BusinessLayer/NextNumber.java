package BusinessLayer;

import java.io.BufferedReader;
import java.io.FileReader;
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
            //FIXME: where the file will sit?
            BufferedReader br = new BufferedReader(
                    new FileReader("C:\\Users\\Netanel\\Dropbox\\Degree\\Freshment year\\Principles of object oriented programming\\Assignments\\3\\DND\\deterministic\\random_numbers.txt"));
            String line ="";
            while((line = br.readLine()) != null){
                fromFile.add(Integer.parseInt(line));
            }
        } catch (Exception e){
            System.out.println("File not found");
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
