package BusinessLayer.Units;

import BusinessLayer.Location;
import BusinessLayer.Observer;

public abstract class Enemy extends GameUnit {
    //-------------------------------fields-----------------------------------------------------------------------------
    private Integer _experience;
    //-------------------------------constructors---------------------------------------------------------------------------
    Enemy(String name, char tile , Integer healthPool, Integer attackPoints, Integer defensivePoints, Integer experience, Observer o) {
        super(name,tile, healthPool, attackPoints, defensivePoints,o);
        _experience = experience;
    }
    //--------------------------------methods-------------------------------------------------------------------------------
    public Integer getExperience() {
        return _experience;
    }

    public abstract void nextMove(Player p);

    public abstract boolean isInRange(Location l);

    void combat(Player p){
        notifyObserver(getName() + " engaged in battle with " + p.getName() +":");
        notifyObserver(toString());
        notifyObserver(p.toString());
        int attack = nextNumber.nextInt(getAttackPoints());
        notifyObserver(getName() + " rolled " + attack + " attack points.");
        int defense = nextNumber.nextInt(p.getDefensivePoints());
        notifyObserver(p.getName() + " rolled " + defense + " defense points.");
        if (attack > defense){
            p.reduceHealth(attack - defense);
            notifyObserver(getName() +" hit " + p.getName() + " for " + (attack - defense) + " damage.");
            if (p.getCurrentHealth() == 0){
                board.killPlayer(p.getPosition());
            }
        }
    }

    @Override
    public String toString() {
        return (getName()+"\t\t"+"Health: "+getCurrentHealth()+"\t\tAttack damage: "+getAttackPoints()+"\t\tDefense: "+getDefensivePoints());
    }
}
