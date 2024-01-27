import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class HunterAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HunterAnt extends Ant
{
    public String getOwner()
    {
        return "Hunter";
    }
    
    public boolean wantsToMove()
    {
        return true;
    }
    
    public void move()
    {
        ArrayList<Ant> allAnts = (ArrayList<Ant>)getWorld().getObjects(Ant.class);
        int mostPoints = Integer.MIN_VALUE;
        Ant best = null;
        for(int n = 0; n < allAnts.size(); n++)
        {
        	Ant next = allAnts.get(n);
        	if(next.getPoints() > mostPoints)
        	{
        		best = next;
        		mostPoints = next.getPoints();
        	}
        }
        
        if(best != null && best != this)
        {
        	turnTowards(best.getX(), best.getY());
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
