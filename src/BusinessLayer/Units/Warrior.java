package BusinessLayer.Units;

import BusinessLayer.Observer;

import java.util.List;

public class Warrior extends Player {
    //-------------------------------fields-----------------------------------------------------------------------------
    private Integer _cooldown, _remaining;

    //-------------------------------constructors---------------------------------------------------------------------------


    public Warrior(String name, Integer healthPool, Integer attackPoints, Integer defensivePoints,Integer cooldown, Observer o) {
        super(name, healthPool, attackPoints, defensivePoints,o);
        _cooldown = cooldown;
        _remaining = 0;
    }

    //--------------------------------methods-------------------------------------------------------------------------------
    public void levelUp(){
        super.levelUp();
        _remaining = 0;
        setHealthPool(getHealthPool() + (getPlayerLevel() * 5));
        setDefensivePoints(getDefensivePoints() + (getPlayerLevel()));
    }

    public void castSpell(List<Enemy> enemies){
     if (_remaining > 0 ){
         notifyObserver("can't cast Heal, still in cooldown");
         return;
     }
     _remaining = _cooldown;

     int afterHeal = (getDefensivePoints() * 2) + getCurrentHealth();
     int healthPool = getHealthPool();
     int healPoints = Math.min(healthPool,afterHeal);
     //TODO: print cast spelled
     setCurrentHealth(healPoints);
    }

    public void newRound(){
        if (_remaining > 0 ){
            _remaining = _remaining -1;
        }
    }

    public Integer getCooldown() {
        return _cooldown;
    }

    public Integer getRemaining() {
        return _remaining;
    }

    @Override
    public String toString(){
    return super.toString()+"\t\tAbility cooldown: " + getCooldown()+"\t\tRemaining: "+getRemaining();
    }

}