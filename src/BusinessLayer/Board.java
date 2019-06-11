package BusinessLayer;

import BusinessLayer.Units.Observable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Board extends Observable {
    private static Board INSTANCE = null;
    char[][] map;//FIXME: change to map of objects instead of char (because of invisible traps)
    NextNumber nextNumber;

    private Board(){
        nextNumber = NextNumber.getInstance();
    }

    public static Board getInstance()
    {
        if (INSTANCE == null )
            INSTANCE = new Board();
        return INSTANCE;
    }

    public void init(Observer o ,File f){
        register(o);
        String text = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            while ( (line = br.readLine()) != null){
                text = text + "\n" + line;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        String[] lines = text.split("\n");
        map = new char[lines.length -1][lines[1].length()];

        for (int i = 0; i < lines.length -1 ; i++) {
            map[i] = lines[i+1].toCharArray();
        }
    }


    public void removeUnit(Location loc){
        map[ loc.getY()][loc.getX()] = '.';
    }

    public void killPlayer(Location loc){
        //assuming correct player location
        map[ loc.getY()][loc.getX()] = 'X';
    }

    public char getFromLocation(Location target){
        return map[target.getY()][target.getX()];
    }

    public char[][] getMap() {
        return map;
    }

    public boolean isEmptyTile(int x, int y){
        return (map[y][x] == '.');
    }

    public boolean isEmptyTile(Location loc){
        try{
            if (map[loc.getY()][loc.getX()] == '.')
                return true;
        } catch (ArrayIndexOutOfBoundsException e){
        }
        return false;
    }

    public boolean isAWall(Location loc){
        try{
            if (map[loc.getY()][loc.getX()] == '#')
                return true;
        } catch (ArrayIndexOutOfBoundsException e){
        }
        return false;
    }

    public void setNewTile(char c , int x , int y){
        map[y][x] = c;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public Location getRandomEmpty(Location loc, Integer range){
        List<Location> emptyTiles = new ArrayList<>();
        Location temp;
        for (int i = -range; i <= range ; i++) {
            for (int j =-range ; j <= range ; j++) {
                temp = new Location(loc);
                temp.setX(temp.getX()-i);
                temp.setY(temp.getY()-j);
                if(isInRange(loc,temp,range) && isEmptyTile(temp))
                    emptyTiles.add(temp);
            }

        }
        int rnd = nextNumber.nextInt(emptyTiles.size()-1);
        return emptyTiles.get(rnd);
    }

    private boolean isInRange(Location loc , Location target, int range){
        double distance =
                Math.sqrt((Math.pow(loc.getX() - target.getX(),2)) + Math.pow(loc.getY() - target.getY(),2));
        if (distance < range)
            return true;
        return false;
    }
    @Override
    public String toString(){
        String output= "";
        for (int i = 0; i < map.length -1 ; i++) {
            output =output+ new String(map[i]) + "\n";
        }
        output = output +  new String(map[map.length-1]);
        return output;
    }
}
