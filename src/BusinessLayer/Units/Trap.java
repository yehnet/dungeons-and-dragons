package BusinessLayer.Units;

import BusinessLayer.Location;
import BusinessLayer.Observer;

public class Trap extends Enemy{
    //-------------------------------fields-----------------------------------------------------------------------------
    private Integer _range, _respawn, _visibilityTime, ticksCount;
    private boolean _visible;

    //-------------------------------constructors---------------------------------------------------------------------------
    public Trap(String name,char tile, Integer healthPool, Integer attackPoints, Integer defensivePoints, Integer experience, Integer range, Integer respawn, Integer visibilityTime, Observer o) {
        super(name, tile, healthPool, attackPoints, defensivePoints, experience,o);
        _range = range;
        _respawn = respawn;
        ticksCount = 0;
        _visibilityTime = visibilityTime;
    }

    //--------------------------------methods------------------------------------------------------------------------------

    public Integer getRange() {
        return _range;
    }

    public Integer getRespawn() {
        return _respawn;
    }

    public Integer getVisibilityTime() {
        return _visibilityTime;
    }

    @Override
    public void nextMove(Player p) {
        if (ticksCount == _respawn) {
            ticksCount = 0;
            setPosition(board.getRandomEmpty(getPosition(),getRange()));
            _visible = true;
        } else {
            ticksCount++;
            if (isInRange(p.getPosition())) {
                combat(p);
            }
        }
        if (ticksCount < _visibilityTime)
            _visible = true;
        else
            _visible = false;
        setVisibility();
    }

    @Override
    public boolean isInRange(Location player) {
        double distance =
                Math.sqrt((Math.pow(player.getX() - getPosition().getX(),2)) + Math.pow(player.getY() - getPosition().getY(),2));
        if (distance < 2)
            return true;
        return false;
    }

    private void setVisibility(){
        if (_visible)
            board.setNewTile(getTile() , getPosition().getX(),getPosition().getY());
    }
}
