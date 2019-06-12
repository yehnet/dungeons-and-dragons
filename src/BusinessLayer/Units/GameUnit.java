package BusinessLayer.Units;

import BusinessLayer.Board;
import BusinessLayer.Location;
import BusinessLayer.NextNumber;
import BusinessLayer.Observer;


public abstract class GameUnit extends Observable{
//-------------------------------fields-----------------------------------------------------------------------------
    private String _name;
    private Location _position;//x,y coordinates
    private Integer _attackPoints, _defensivePoints, _healthPool , _currentHealth;
    private char _tile;
    NextNumber nextNumber;
    Board board;

//-------------------------------constructors---------------------------------------------------------------------------
    GameUnit(String name, char tile, Integer healthPool, Integer attackPoints, Integer defensivePoints, Observer o) {
        _name = name;
        _tile = tile;
        _healthPool = healthPool;
        _currentHealth = healthPool;
        _attackPoints = attackPoints;
        _defensivePoints = defensivePoints;
        nextNumber = NextNumber.getInstance();
        board = Board.getInstance();
        register(o);
    }

//--------------------------------methods-------------------------------------------------------------------------------
    public String getName() {
        return _name;
    }

    public Location getPosition() {
        return _position;
    }

    Integer getAttackPoints() {
        return _attackPoints;
    }

    public Integer getDefensivePoints() {
        return _defensivePoints;
    }

    void setAttackPoints(Integer _attackPoints) {
        this._attackPoints = _attackPoints;
    }

    void setDefensivePoints(Integer _defensivePoints) {
        this._defensivePoints = _defensivePoints;
    }

    Integer getHealthPool() {
        return _healthPool;
    }

    public Integer getCurrentHealth() {
        return _currentHealth;
    }

    void setCurrentHealth(Integer currentHealth) {
        _currentHealth = currentHealth;
        if ( _currentHealth < 0)
            _currentHealth = 0;
        if (_currentHealth > _healthPool)
            setMaxHealth();
    }

    public void reduceHealth(Integer amount){
        _currentHealth = _currentHealth - amount;
        if(getCurrentHealth() < 0)
            setHealthPool(0);
    }

    void setHealthPool(Integer _healthPool) {
        this._healthPool = _healthPool;
    }

    void setMaxHealth(){
        this._currentHealth = _healthPool;
    }

    public void setPosition(Location position) {
        if (_position != null) {
            board.removeUnit(getPosition());
            _position = position;
            board.setNewTile(_tile, getPosition().getX(), getPosition().getY());
        } else
            _position = position;

    }

    boolean isEmptyTile(Location l){
        return board.isEmptyTile(l);
    }

    char getTile(){
        return _tile;
    }
}
