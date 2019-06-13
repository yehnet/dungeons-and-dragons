package BusinessLayer;

public class Location {
    private int _x,_y;

    Location(int x , int y){
        _x = x;
        _y = y;
    }

    Location(Location newLoc){
        _x = newLoc.getX();
        _y = newLoc.getY();
    }

    public Location getUp(){
        return new Location(_x,_y-1);
    }

    public Location getRight(){
        return new Location(_x+1,_y);
    }

    public Location getDown(){
        return new Location(_x,_y+1);
    }

    public Location getLeft(){
        return new Location(_x-1,_y);
    }

    public int getX() {
        return _x;
    }

    public void setX(int _x) {
        this._x = _x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int _y) {
        this._y = _y;
    }

    public boolean equals(Location l){
        return (_x == l.getX() & _y == l.getY());
    }
}
