import PresentationLayer.CLI;

public class Main {
    public static void main(String[] args){
        boolean deterministic = false;
        if(args.length == 2 && args[1].equals("-D"))
            deterministic = true;
        String path = args[0];
        CLI view = new CLI(path, deterministic);
        view.run();
    }
}
