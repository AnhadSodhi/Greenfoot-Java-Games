import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Spider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spider extends PoliteActor
{
    private int speed_;
    private int venomStrength_;
    private int biteCounter_;
    public final static int BITE_DELAY = 50;
    private boolean acting_;
    
    public Spider()
    {
        super();
        speed_ = Ant.WALK;
        venomStrength_ = (int)(Math.random()*3)+1;//venom strength between 1 and 3
        biteCounter_ = 0;
        acting_ = false;
    }
    
    /**
     * Determines how fast a Spider can move. 
     * It is set to the Ant's walking speed.
     * @param none
     * @return The speed of this Spider
     */
    public int getSpeed()
    {
        return speed_;
    }
    
    /**
     * Determines the amount of damage a Spider can do to an Ant. 
     * It is set in the constructor as a random value between 1 and 3.
     * Some Spiders are more dangerous than others.
     * @param none
     * @return The venomStrength of this Spider (damage it will do when it bites an Ant)
     */
    public int getVenomStrength()
    {
        return venomStrength_;
    }
    
    /**
     * Determines how long before a Spider can bite an Ant. 
     * After biting an Ant the Spider must recover. 
     * It cannot bite the Ant again the timeToStrike() is <= 0.
     * @param none
     * @return number of turns this Spider must wait before it can bite an Ant
     */public int timeToStrike()
    {
        if(biteCounter_ >= BITE_DELAY)
            return 0;
        else
            return BITE_DELAY - biteCounter_;
    }
    
    /**
     * Not for use, helper method to enfore rules of game
     */
    public boolean acting()
    {
        return acting_;
    }
    
    /**
     * The Spider's behaviour in the game.
     */
    public void act() 
    {
        acting_ = true;
        biteCounter_++;
        spiderWalk();        
        
        acting_ = false;
    }    
    
    /**
     * Spiders have a pre-determined behaviour.
     * They can "see" Ants within a 100px radius. 
     * When a Spider finds an Ant it will chase the Ant in hopes of biting it.
     * It does not attack any particular Ant, it picks a random one on each turn if its within the 100px.
     * If it is successful in biting the Ant, the Ant will lose points equal to venomStrength and be forced to drop its food (if holding a crumb).
     * When the Spider does not see an Ant or it cannot bite an Ant (BITE_DELAY has not expired) it wanders around randomly.
     */
    private void spiderWalk()
    {
        //randomly select one of the Ants within 100px
        //this may make the spider behave irradically when more than one ant is in range for a period of time
        List<Ant> ants = getObjectsInRange(100, Ant.class);
        if(ants.size() > 0 && timeToStrike() <= 0)
        {
            int target = (int)(Math.random()*ants.size());
            Ant a = ants.get(target);
            if(a != null)
            {
                turnTowards(a.getX(), a.getY());
            }
        }
        
        else
        {
            int amount = (int)(Math.random()*41) - 20;
            turn(amount);
        }
        
        move(speed_);
        
        if(isTouching(Ant.class) && timeToStrike() <= 0)
        {
            Ant a = (Ant)getOneIntersectingObject(Ant.class);
            a.injure(venomStrength_);
            biteCounter_ = 0;
        }
   }
}
