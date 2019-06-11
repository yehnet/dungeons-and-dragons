package BusinessLayer;

import BusinessLayer.Units.*;
import PresentationLayer.CLI;
import javafx.beans.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameRunner extends BusinessLayer.Units.Observable {
    Player player;
    List<Enemy> enemies = new ArrayList<>();
    Board board;
    NextNumber nextNumber;
    NextAction nextAction;
    boolean run;
    CLI ui;
    File[] levels;
    int levelnum;


    public GameRunner(CLI o,String path, boolean deterministic){
        ui = o;
        register(o);
        nextNumber = NextNumber.getInstance();
        nextAction = NextAction.getInstance();
        if (deterministic){
            nextAction.setDeterministic();
            nextNumber.setDeterministic();
        }
        loadFiles(path);
        levelnum = 0;
        board = Board.getInstance();


    }

    public void run(){
        init();
        char act;
        run = true;
        notifyObserver(board.toString());
        while (run){
            notifyObserver("------------------------------------------------for testing purposes-----------------------------------------------------------");
            String action  = nextAction.nextAction();
            Location moveTo;
            if (checkInput(action)) {
                act = action.charAt(0);
                switch (act) {
                    case ('w'):
                        moveTo = player.getPosition().moveUp();
                        playerTurn(moveTo);
                        break;
                    case ('a'):
                        moveTo = player.getPosition().moveLeft();
                        playerTurn(moveTo);
                        break;
                    case ('s'):
                        moveTo = player.getPosition().moveDown();
                        playerTurn(moveTo);
                        break;
                    case ('d'):
                        moveTo = player.getPosition().moveRight();
                        playerTurn(moveTo);
                        break;
                    case ('q'):
                        player.castSpell(enemies);
                        break;
                    case ('e'):
                        //do nothing
                        break;
                }
            } else
            {
                break;
            }
            enemiesTurn();
            if (player.getCurrentHealth() > 0)
                if (!enemies.isEmpty())
                    nextRound();
                else
                    nextLevel();
            else lose();
            notifyObserver(board.toString());
            notifyObserver(player.toString());
        }
    }

    private boolean checkInput(String input){
        if (input == null ||  input.length() != 1 &&
                !(input.charAt(0) == 'w'  | input.charAt(0) == 'a' | input.charAt(0) == 's' | input.charAt(0) == 'd'
                | input.charAt(0) == 'q' | input.charAt(0) == 'e'))
            return false;
        return true;
    }

    private void choosePlayer(){
        int playerNum;
        String input = nextAction.nextAction();
        if(input.length()==1 && input.charAt(0) == '1' | input.charAt(0) == '2' | input.charAt(0) == '3' | input.charAt(0) == '4'
                | input.charAt(0) == '5' | input.charAt(0) == '6' | input.charAt(0) == '7' ){
            playerNum = Integer.parseInt(input);
            switch (playerNum){
                case(1):
                    player = new Warrior("Jon Snow", 300, 30,4, 6,ui);
                    break;
                case(2):
                    player = new Warrior("The Hound" , 400,20,6 , 4,ui);
                    break;
                case(3):
                    player = new Mage("Melisandre" , 160,10, 1,40,300,30,5,6,ui);
                    break;
                case(4):
                    player = new Mage("Thoros of Myr", 250 , 25,3,15,150, 50,3,3,ui);
                    break;
                case(5):
                    player = new Rouge("Arya Stark", 150, 40,2,20,ui);
                    break;
                case(6):
                    player = new Rouge("Bronn", 250,35,3,20,ui);
                    break;
            }
            notifyObserver("You have selected:\n"
                    + player.toString()
                    + "\nUse w/s/a/d to move.\n" +
                    "Use e for special ability or q to pass." );
        }
    }

    private void playerTurn(Location moveTo){
        if (!board.isAWall(moveTo)){
            if (board.isEmptyTile(moveTo)) {
                //FIXME: in case of invisible trap, the path is blocked
                player.setPosition(moveTo);
            } else
                for ( Enemy e : enemies){
                    if ( e.getPosition().equals(moveTo)){
                        notifyObserver(player.getName() + " engaged in battle with " + e.getName() + ":\n" + player.toString()+ "\n" + e.toString());
                        player.combat(e);
                        if (e.getCurrentHealth() == 0) {
                            player.addExperience(e.getExperience());
                            board.removeUnit(e.getPosition());
                            enemies.remove(e);
                        }
                    }
                }
        }
    }

    private void enemiesTurn(){
        for ( Enemy e : enemies){
            e.nextMove(player);
            if( player.getCurrentHealth() == 0) { // player lost all of his life.
                lose();
            }
        }

    }

    private void loadUnits(char[][] map){
        Enemy e;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch(map[i][j]){
                    case('@'):
                        player.setPosition(new Location(j,i));
                        break;
                    case('s'):
                        e = new Monster("Lannister Soldier",'s',80,8,3,3,25,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('k'):
                        e = new Monster("Lannister Knight",'k',200,14,8,4,50,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('q'):
                        e = new Monster("Queen's Guard",'q',400,20,15,5,100,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('z'):
                        e = new Monster("Wright",'z',600,30,15,3,100,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('b'):
                        e=new Monster("Bear-Wright", 'b',1000,75,30,4,250,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('g'):
                        e=new Monster("Giant-Wright",'g',1500,100,40,5,500,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('w'):
                        e=new Monster("White Walker",'w',2000,150,50,6,1000,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('M'):
                        e=new Monster("The Mountain",'M',1000,60,25,6,500,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('C'):
                        e=new Monster("Queen Cersei", 'C',100,10,10,1,1000,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('K'):
                        e=new Monster("Night's King",'K',5000,300,150,8,5000,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('B'):
                        e=new Trap("Bonus \"Trap\"" ,'B', 1,1,1,250,5,6,2,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('Q'):
                        e=new Trap("Queen's Trap",'Q',250,50,10,100,4,10,4,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                    case('D'):
                        e=new Trap("Death Trap",'D',500,100,20,250,6,10,3,ui);
                        e.setPosition(new Location(j,i));
                        enemies.add(e);
                        break;
                }
            }
        }
    }

    private void nextRound(){
        player.newRound();
    }

    private void nextLevel(){
        board.init(ui,levels[levelnum]);
        loadUnits(board.getMap());
        levelnum++;
    }

    private void lose(){
        notifyObserver(player.getName() + " died.\n" + "You Lost.");
        Location playerLocation = player.getPosition();
        char[][] map = board.getMap();
        map[playerLocation.getY()][playerLocation.getX()] = 'X';
        board.setMap(map);
        run = false;
    }

    private void win(){
        notifyObserver(player.getName() + " win.\n" + player.toString());
        notifyObserver( "You win.\n" + "*********Game Over*********");
        run = false;
    }

    private void init(){
        if (levelnum < levels.length) {
            board.init(ui, levels[levelnum]);
            choosePlayer();
            loadUnits(board.getMap());
            levelnum++;
        } else
            win();
    }

    private void loadFiles(String path){
        levels = new File(path).listFiles();
    }

}
