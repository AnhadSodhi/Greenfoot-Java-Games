import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class SafeNestAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SafeNestAnt extends Ant
{
    public String getOwner()
    {
        return "SafeNest";
    }
    
    public boolean wantsToMove()
    {
        return true;
    }
    
    public void move()
    {        
        ArrayList<Nest> nests = (ArrayList<Nest>)getWorld().getObjects(Nest.class);
        ArrayList<Spider> spiders = (ArrayList<Spider>)getWorld().getObjects(Spider.class);
        int leastSpiders = Integer.MAX_VALUE;
        Nest safest = null;
        for(Nest n : nests)
        {
            int spiderCount = 0;
            for(Spider s : spiders)
            {
                double distance = Math.abs(s.getX() - n.getX()) + Math.abs(s.getY() - n.getY());
                if(distance < 100)
                {
                    spiderCount++;
                }
            }
            
            if(spiderCount < leastSpiders)
            {
                leastSpiders = spiderCount;
                safest = n;
            }
        }
        
        if(safest != null)
        {
            turnTowards(safest.getX(), safest.getY());
            move(getSpeed());
        }
    }
   
    public boolean wantsToPickUpFood()
    {
        return true;
    }
    
    public boolean wantsToBump()
    {
        return true;
    }
    
    public boolean wantsToDropFood()
    {
        return false;
    }  
}
