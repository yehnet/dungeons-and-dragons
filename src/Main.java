import PresentationLayer.CLI;

public class Main {
    public static void main(String[] args){
        boolean deterministic = false;
        if(args.length == 2 && args[1].equals("-D"))
            deterministic = true;
        String path = args[0];
        //For Testing
        //path = "C:\\Users\\Netanel\\Dropbox\\Degree\\Freshment year\\Principles of object oriented programming\\Assignments\\3\\DND\\levels";
        CLI view = new CLI(path, deterministic);
        view.run();
    }
}
