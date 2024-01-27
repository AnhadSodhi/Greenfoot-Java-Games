import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Crumbs here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Crumb extends PoliteActor
{
    public Crumb()
    {
        super();
    }
    /**
     * Act - do whatever the Crumbs wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }   
    
    public boolean acting()
    {
        //can only move when another Ant, that is acting, is holding this crumb
        ArrayList<Ant> ants = (ArrayList<Ant>)getIntersectingObjects(Ant.class);
        for(Ant a : ants)
        {
            if(a.getCrumb() == this)//Ant is holding this crumb
            {
                if(a.acting() && a.movingFood())//Ant's turn and in control of this crumb
                    return true;
                if(a.dropping())
                    return true;//bitten by a spider    
            }
        } 
        
        return false;
    }
    
    public abstract int points();
}
