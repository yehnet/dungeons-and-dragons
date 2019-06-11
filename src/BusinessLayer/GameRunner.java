package BusinessLayer;

import BusinessLayer.Units.*;
import PresentationLayer.CLI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameRunner extends BusinessLayer.Units.Observable {
    private Player _player;
    private List<Enemy> _enemies = new ArrayList<>();
    private Board _board;
    private NextAction _nextAction;
    private boolean _run;
    private CLI _ui;
    private File[] _levels;
    private int _levelnum;


    public GameRunner(CLI o,String path, boolean deterministic){
        _ui = o;
        register(o);
        NextNumber nextNumber = NextNumber.getInstance();
        _nextAction = NextAction.getInstance();
        if (deterministic){
            _nextAction.setDeterministic();
            nextNumber.setDeterministic();
        }
        loadFiles(path);
        _levelnum = 0;
        _board = Board.getInstance();


    }

    public void run(){
        init();
        char act;
        _run = true;
        notifyObserver(_board.toString());
        while (_run){
            notifyObserver("------------------------------------------------for testing purposes-----------------------------------------------------------");
            String action  = _nextAction.nextAction();
            Location moveTo;
            if (checkInput(action)) {
                act = action.charAt(0);
                switch (act) {
                    case ('w'):
                        moveTo = _player.getPosition().moveUp();
                        playerTurn(moveTo);
                        break;
                    case ('a'):
                        moveTo = _player.getPosition().moveLeft();
                        playerTurn(moveTo);
                        break;
                    case ('s'):
                        moveTo = _player.getPosition().moveDown();
                        playerTurn(moveTo);
                        break;
                    case ('d'):
                        moveTo = _player.getPosition().moveRight();
                        playerTurn(moveTo);
                        break;
                    case ('q'):
                        _player.castSpell(_enemies);
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
            if (_player.getCurrentHealth() > 0)
                if (!_enemies.isEmpty())
                    nextRound();
                else
                    nextLevel();
            else lose();
            notifyObserver(_board.toString());
            notifyObserver(_player.toString());
        }
    }

    private boolean checkInput(String input){
        return input != null && (input.length() == 1 &&
                input.charAt(0) == 'w' | input.charAt(0) == 'a' | input.charAt(0) == 's' | input.charAt(0) == 'd'
                        | input.charAt(0) == 'q' | input.charAt(0) == 'e');
    }

    private void choosePlayer(){
        int playerNum;
        String input = _nextAction.nextAction();
        if(input.length()==1 && input.charAt(0) == '1' | input.charAt(0) == '2' | input.charAt(0) == '3' | input.charAt(0) == '4'
                | input.charAt(0) == '5' | input.charAt(0) == '6' | input.charAt(0) == '7' ){
            playerNum = Integer.parseInt(input);
            switch (playerNum){
                case(1):
                    _player = new Warrior("Jon Snow", 300, 30,4, 6, _ui);
                    break;
                case(2):
                    _player = new Warrior("The Hound" , 400,20,6 , 4, _ui);
                    break;
                case(3):
                    _player = new Mage("Melisandre" , 160,10, 1,40,300,30,5,6, _ui);
                    break;
                case(4):
                    _player = new Mage("Thoros of Myr", 250 , 25,3,15,150, 50,3,3, _ui);
                    break;
                case(5):
                    _player = new Rouge("Arya Stark", 150, 40,2,20, _ui);
                    break;
                case(6):
                    _player = new Rouge("Bronn", 250,35,3,20, _ui);
                    break;
            }
            notifyObserver("You have selected:\n"
                    + _player.toString()
                    + "\nUse w/s/a/d to move.\n" +
                    "Use e for special ability or q to pass." );
        }
    }

    private void playerTurn(Location moveTo){
        if (!_board.isAWall(moveTo)){
            if (_board.isEmptyTile(moveTo)) {
                //FIXME: in case of invisible trap, the path is blocked
                _player.setPosition(moveTo);
            } else
                for ( Enemy e : _enemies){
                    if ( e.getPosition().equals(moveTo)){
                        notifyObserver(_player.getName() + " engaged in battle with " + e.getName() + ":\n" + _player.toString()+ "\n" + e.toString());
                        _player.combat(e);
                        if (e.getCurrentHealth() == 0) {
                            _player.addExperience(e.getExperience());
                            _board.removeUnit(e.getPosition());
                            _enemies.remove(e);
                        }
                    }
                }
        }
    }

    private void enemiesTurn(){
        for ( Enemy e : _enemies){
            e.nextMove(_player);
            if( _player.getCurrentHealth() == 0) { // _player lost all of his life.
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
                        _player.setPosition(new Location(j,i));
                        break;
                    case('s'):
                        e = new Monster("Lannister Soldier",'s',80,8,3,3,25, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('k'):
                        e = new Monster("Lannister Knight",'k',200,14,8,4,50, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('q'):
                        e = new Monster("Queen's Guard",'q',400,20,15,5,100, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('z'):
                        e = new Monster("Wright",'z',600,30,15,3,100, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('b'):
                        e=new Monster("Bear-Wright", 'b',1000,75,30,4,250, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('g'):
                        e=new Monster("Giant-Wright",'g',1500,100,40,5,500, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('w'):
                        e=new Monster("White Walker",'w',2000,150,50,6,1000, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('M'):
                        e=new Monster("The Mountain",'M',1000,60,25,6,500, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('C'):
                        e=new Monster("Queen Cersei", 'C',100,10,10,1,1000, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('K'):
                        e=new Monster("Night's King",'K',5000,300,150,8,5000, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('B'):
                        e=new Trap("Bonus \"Trap\"" ,'B', 1,1,1,250,5,6,2, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('Q'):
                        e=new Trap("Queen's Trap",'Q',250,50,10,100,4,10,4, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                    case('D'):
                        e=new Trap("Death Trap",'D',500,100,20,250,6,10,3, _ui);
                        e.setPosition(new Location(j,i));
                        _enemies.add(e);
                        break;
                }
            }
        }
    }

    private void nextRound(){
        _player.newRound();
    }

    private void nextLevel(){
        _board.init(_ui, _levels[_levelnum]);
        loadUnits(_board.getMap());
        _levelnum++;
    }

    private void lose(){
        notifyObserver(_player.getName() + " died.\n" + "You Lost.");
        Location playerLocation = _player.getPosition();
        char[][] map = _board.getMap();
        map[playerLocation.getY()][playerLocation.getX()] = 'X';
        _board.setMap(map);
        _run = false;
    }

    private void win(){
        notifyObserver(_player.getName() + " win.\n" + _player.toString());
        notifyObserver( "You win.\n" + "*********Game Over*********");
        _run = false;
    }

    private void init(){
        if (_levelnum < _levels.length) {
            _board.init(_ui, _levels[_levelnum]);
            choosePlayer();
            loadUnits(_board.getMap());
            _levelnum++;
        } else
            win();
    }

    private void loadFiles(String path){
        _levels = new File(path).listFiles();
    }

}
