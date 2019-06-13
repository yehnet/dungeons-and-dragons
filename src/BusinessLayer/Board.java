package BusinessLayer;

import BusinessLayer.Units.Observable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Board extends Observable {
    private static Board INSTANCE = null;
    private char[][] map;
    private boolean[][] traps;
    private NextNumber nextNumber;

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
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ( (line = br.readLine()) != null){
                text.append("\n").append(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        String[] lines = text.toString().split("\n");
        map = new char[lines.length -1][lines[1].length()];
        traps = new boolean[lines.length-1][lines[1].length()];

        for (int i = 0; i < lines.length -1 ; i++) {
            map[i] = lines[i+1].toCharArray();
        }

        for (int i = 0; i < lines.length-1 ; i++) {
            for (int j = 0; j < lines[1].length() ; j++) {
                traps[i][j] = false;
            }
        }


    }


    public void removeUnit(Location loc){
        map[ loc.getY()][loc.getX()] = '.';
    }

    public void removeTrap(Location loc){
        traps[loc.getY()][loc.getX()] = false;
    }
    
    public void killPlayer(Location loc){
        //assuming correct player location
        map[ loc.getY()][loc.getX()] = 'X';
    }

    public char[][] getMap() {
        return map;
    }

    public boolean isEmptyTile(Location loc){
        try{
            if (map[loc.getY()][loc.getX()] == '.' && !traps[loc.getY()][loc.getX()])
                return true;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        return false;
    }

    public boolean isAWall(Location loc){
        try{
            if (map[loc.getY()][loc.getX()] == '#')
                return true;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        return false;
    }

    public void setNewTile(char c , int x , int y){
        map[y][x] = c;
        if ( c == 'B' | c == 'Q' | c == 'D')
            traps[y][x] = true;
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
        return distance < range;
    }
    @Override
    public String toString(){
        StringBuilder output= new StringBuilder();
        for (int i = 0; i < map.length -1 ; i++) {
            output.append(new String(map[i])).append("\n");
        }
        output.append(new String(map[map.length - 1]));
        return output.toString();
    }
}
