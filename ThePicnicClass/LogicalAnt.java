import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class SimpleAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LogicalAnt extends Ant
{
    public String getOwner()
    {
        return "Logic";
    }
    
    public boolean wantsToMove()
    {
        return true;
    }
    
    public void move()
    {
        ArrayList<Spider> closeSpiders = (ArrayList<Spider>)getNeighbours(50, true, Spider.class);
        if(closeSpiders.size() > 0)
        {
        	Spider first = closeSpiders.get(0);//index 0 is the first in the list
        	turnTowards( first.getX(), first.getY() );//face the spider
        	turn(180);//turn in the opposite direction
        	move(getSpeed());
        }

        else if(isAtEdge())
        {
            turn(90);
            move(getSpeed());
        }
        
        else if(!holdingFood())//look for Crumbs
        {
            ArrayList<Crumb> closeCrumbs = (ArrayList<Crumb>)getNeighbours(50, true, Crumb.class);
            if(closeCrumbs.size() > 0)
            {
        		Crumb first = closeCrumbs.get(0);//index 0 is the first in the list
        		turnTowards( first.getX(), first.getY() );
        		move(getSpeed());
            }
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
        ArrayList<Spider> closeSpiders = (ArrayList<Spider>)getObjectsInRange(50, Spider.class);
        if(closeSpiders.size() > 0)
        {
        		return true;
        }
        
        return false;
    }   
}
