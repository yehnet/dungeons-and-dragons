package BusinessLayer;

public class Location {
    int _x,_y;

    public Location(int x , int y){
        _x = x;
        _y = y;
    }

    public Location(Location newLoc){
        _x = newLoc.getX();
        _y = newLoc.getY();
    }

    public Location moveUp(){
        return new Location(_x,_y-1);
    }

    public Location moveRight(){
        return new Location(_x+1,_y);
    }

    public Location moveDown(){
        return new Location(_x,_y+1);
    }

    public Location moveLeft(){
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
