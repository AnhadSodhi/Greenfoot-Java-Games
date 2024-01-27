import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class SimpleAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimpleAnt extends Ant
{
    public String getOwner()
    {
        return "Example";
    }
    
    public boolean wantsToMove()
    {
        return true;
    }
    
    public void move()
    {
        int amount = (int)(Math.random()*41) - 20;//-20 to 20
        turn(amount);
        move(getSpeed());
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
