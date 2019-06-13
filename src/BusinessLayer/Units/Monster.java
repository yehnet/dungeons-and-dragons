package BusinessLayer.Units;

import BusinessLayer.Location;
import BusinessLayer.Observer;

public class Monster extends Enemy {
    //-------------------------------fields-----------------------------------------------------------------------------
    private Integer _range;
    //-------------------------------constructors-----------------------------------------------------------------------
    public Monster(String name, char tile, Integer healthPool, Integer attackPoints, Integer defensivePoints, Integer range, Integer experience, Observer o) {
        super(name, tile,healthPool, attackPoints, defensivePoints, experience, o);
        _range = range;
    }
    //--------------------------------methods---------------------------------------------------------------------------

    @Override
    public void nextMove(Player p) {
        //moves: 0 - up , 1 - right, 2- down , 3- left
        boolean[] moves = movesAvailable();
        if (isInRange(p.getPosition())){
            if(getPosition().equals(p.getPosition().getLeft()) | getPosition().equals(p.getPosition().getDown())
                    | getPosition().equals(p.getPosition().getRight()) | getPosition().equals(p.getPosition().getUp() )) {
                combat(p);
            } else {
                int dx = getPosition().getX() - p.getPosition().getX();
                int dy = getPosition().getY() - p.getPosition().getY();
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0 & moves[3])
                        setPosition(getPosition().getLeft());
                    else if (moves[1])
                        setPosition(getPosition().getRight());
                } else {
                    if (dy > 0 & moves[0])
                        setPosition(getPosition().getUp());
                    else if (moves[2])
                        setPosition(getPosition().getDown());
                }
            }
        } else {
            boolean notFound  = true;
            int moveTo= -1;
            if (!moves[0] & !moves[1] & !moves[2] & !moves[3]) {
                notFound = false;
                moveTo = 4;
            }
            while (notFound){
                moveTo = nextNumber.nextInt(4);
                if (moveTo == 4)
                    notFound = false;
                else if (moves[moveTo])
                        notFound = false;
            }
            switch (moveTo){
                case(0):
                    setPosition(getPosition().getUp());
                    break;
                case(1):
                    setPosition(getPosition().getRight());
                    break;
                case(2):
                    setPosition(getPosition().getDown());
                    break;
                case(3):
                    setPosition(getPosition().getLeft());
                    break;
                case(4):
                    //stay
                    break;
            }
        }


    }

    public boolean isInRange(Location l){
        int x = l.getX() - getPosition().getX();
        int y = l.getY() - getPosition().getY();
        int distance = (int)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return distance <= _range;
    }

    private boolean[] movesAvailable(){
        boolean[] moves = {false,false,false,false};
        if (isEmptyTile(getPosition().getUp()))
            moves[0] = true;
        if (isEmptyTile(getPosition().getRight()))
            moves[1] = true;
        if(isEmptyTile(getPosition().getDown()))
            moves[2] = true;
        if(isEmptyTile(getPosition().getLeft()))
            moves[3] = true;
        return moves;
    }
}
