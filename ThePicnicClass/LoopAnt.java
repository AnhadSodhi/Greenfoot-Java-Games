import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class SimpleAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LoopAnt extends Ant
{
    public String getOwner()
    {
        return "ExampleFinder";
    }
    
    public boolean wantsToMove()
    {
        return true;
    }
    
    public void move()
    {
        ArrayList<Ant> allAnts = (ArrayList<Ant>)getWorld().getObjects(Ant.class);
        for(int n = 0; n < allAnts.size(); n++)
        {
        	Ant next = allAnts.get(n);
        	if(next.getOwner().equals("Example"))
        	{
        		turnTowards( next.getX(), next.getY() );
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
        return false;
    }   
}
