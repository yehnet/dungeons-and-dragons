package BusinessLayer.Units;


import BusinessLayer.NextNumber;
import BusinessLayer.Observer;

import java.util.ArrayList;
import java.util.List;


public class Mage extends Player{
    //-------------------------------fields-----------------------------------------------------------------------------
    private Integer _spellPower,_manaPool,_currentMana, _cost, _hitTimes, _range;

    //-------------------------------constructors---------------------------------------------------------------------------

    public Mage(String name, Integer healthPool, Integer attackPoints, Integer defensivePoints, Integer spellPower, Integer manaPool, Integer manaCost, Integer hitTimes, Integer range, Observer o) {
        super(name, healthPool, attackPoints, defensivePoints,o);
        _spellPower = spellPower;
        _manaPool = manaPool;
        _currentMana = _manaPool / 4;
        _cost = manaCost;
        _hitTimes = hitTimes;
        _range = range;
    }

    //--------------------------------methods-------------------------------------------------------------------------------

    public void levelUp(){
        super.levelUp();
        _manaPool = _manaPool + (25 * getPlayerLevel());
        _currentMana = Math.min(_currentMana+(_manaPool/4) , _manaPool);
        _spellPower = _spellPower + ( 10 * getPlayerLevel());
    }

    public void newRound(){
        _currentMana = Math.min(_manaPool, _currentMana + 1);
    }

    public void castSpell(List<Enemy> enemies){
        if (_currentMana < _cost) {
            notifyObserver("cant cast blizzard, dont have enough mana");
            return;
        }
        _currentMana = _currentMana - _cost;
        int hits = 0;
        Enemy e = getEnemyInRange(enemies);
        //TODO: print cast spelled
        while (hits < _hitTimes & e != null) {
            NextNumber rnd = NextNumber.getInstance();
            int defense = rnd.nextInt(e.getDefensivePoints());
            if(getSpellPower() > defense)
                e.reduceHealth(getSpellPower() - defense);
            hits++;
        }
    }

    private Enemy getEnemyInRange(List<Enemy> enemies ){
        NextNumber random = NextNumber.getInstance();
        List<Enemy> closeEnemies = new ArrayList<>();
        closeEnemies.addAll(enemies);
        while (closeEnemies != null){
            int rnd = random.nextInt(enemies.size());
            if (isInRange(enemies.get(rnd)))
                return enemies.get(rnd);
            else
                enemies.remove(rnd);
        }
        return null;
    }

    private boolean isInRange(Enemy e){
        int x = e.getPosition().getX() - getPosition().getX();
        int y = e.getPosition().getY() - getPosition().getY();
        int distance = (int)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        if (distance <= _range)
            return true;
        return false;
    }

    public Integer getSpellPower() {
        return _spellPower;
    }

    public Integer getCurrentMana() {
        return _currentMana;
    }

    public Integer getManaPool() {
        return _manaPool;
    }

    @Override
    public String toString() {
       return super.toString() +"\t\tSpellPower: " + getSpellPower() +"\t\tMana: " +getCurrentMana() + "/" + getManaPool();
    }
}
