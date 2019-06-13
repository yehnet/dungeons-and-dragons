package BusinessLayer.Units;

import BusinessLayer.Observer;

import java.util.List;

public abstract class Player extends GameUnit {
    //-------------------------------fields-----------------------------------------------------------------------------
    private Integer _experience,_playerLevel;

    //private Ability ability;

    //-------------------------------constructors---------------------------------------------------------------------------


    Player(String name, Integer healthPool, Integer attackPoints, Integer defensivePoints, Observer o) {
        super(name,'@', healthPool, attackPoints, defensivePoints,o);
        _experience = 0;
        _playerLevel = 1;

    }

    //--------------------------------methods-------------------------------------------------------------------------------
    public void levelUp(){
        notifyObserver(getName() + " leveled up");
        _experience = _experience - (_playerLevel * 50);
        _playerLevel = _playerLevel + 1;
        setHealthPool( getHealthPool() + (10 * _playerLevel));
        setMaxHealth();
        setAttackPoints(getAttackPoints() + (5 * _playerLevel));
        setDefensivePoints(getDefensivePoints() + ( 2 * _playerLevel));
    }

    Integer getPlayerLevel() {
        return _playerLevel;
    }

    public void addExperience(int exp){
        _experience =+ exp;
        if(_experience > (_playerLevel*50) ) {
            levelUp();
        }
    }

    public abstract void castSpell(List<Enemy> enemies);

    public void combat(Enemy e){
        int attack = nextNumber.nextInt(getAttackPoints());
        notifyObserver(getName() + " rolled " + attack + " attack points.");
        int defense = nextNumber.nextInt(e.getDefensivePoints());
        notifyObserver(e.getName() + " rolled " + defense + " defense points.");
        if (attack > defense){
            e.reduceHealth(attack - defense);
            notifyObserver(getName() +" hit " + e.getName() + " for " + (attack - defense) + " damage.");
        }
    }

    public abstract void newRound();

    private Integer getExperience() {
        return _experience;
    }

    @Override
    public String toString() {
        return (getName()+"\t\t"+"Health: "+getCurrentHealth()+"\t\tAttack damage: "+getAttackPoints()+"\t\tDefense: "+getDefensivePoints() +
        "\n\t\tLevel: "+getPlayerLevel()+"\t\tExperience: "+ getExperience()+"/"+_playerLevel*50 );
    }
}
