package BusinessLayer.Units;

import BusinessLayer.NextNumber;
import BusinessLayer.Observer;

import java.util.List;

public class Rouge extends Player {
    //-------------------------------fields-----------------------------------------------------------------------------
    Integer _cost, _currentEnergy;
    //-------------------------------constructors---------------------------------------------------------------------------
    public Rouge(String name, Integer healthPool, Integer attackPoints, Integer defensivePoints, Integer cost, Observer o) {
        super(name, healthPool, attackPoints, defensivePoints, o);
        _currentEnergy = 100;
        _cost = cost;
    }
    //--------------------------------methods-------------------------------------------------------------------------------
    public void levelUp(){
        super.levelUp();
        _currentEnergy = 100;
        setAttackPoints(getAttackPoints() + ( 3* getPlayerLevel()));
    }

    public void newRound(){
        _currentEnergy = Math.min(_currentEnergy +10, 100);
    }

    public void castSpell(List<Enemy> enemies){
        if (_currentEnergy < _cost){
            notifyObserver("cant cast Fan of Knives, dont have enough energy");
            return;
        }
        _currentEnergy = _currentEnergy - _cost;
        for (Enemy e : enemies){
            if (isInRange(e)){
                spellCombat(e);
            }
        }
    }

    private boolean isInRange(Enemy e){
        int x = e.getPosition().getX() - getPosition().getX();
        int y = e.getPosition().getY() - getPosition().getY();
        int distance = (int)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        if (distance < 2)
            return true;
        return false;
    }

    private void spellCombat(Enemy e){
        notifyObserver(getName() + " cast Fan of Knives");
        NextNumber rnd = NextNumber.getInstance();
        int defense = rnd.nextInt(e.getDefensivePoints());
        if(getAttackPoints() > defense)
            e.reduceHealth(getAttackPoints() - defense);
    }

    public Integer getCurrentEnergy() {
        return _currentEnergy;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\tEnergy: "+getCurrentEnergy()+"/100";
    }
}
