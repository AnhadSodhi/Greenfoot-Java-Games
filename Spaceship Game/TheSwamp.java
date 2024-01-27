import greenfoot.*;
import java.awt.Color;

/**
 * Write a description of class TheSwamp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TheSwamp extends World
{
    private Player mainCharacter;
    private Crosshairs crosshairs;
    private Healthbar healthbar;
    private MouseInfo mi;
    /**
     * Constructor for objects of class TheSwamp.
     * 
     */
    public TheSwamp()
    {
        // Create a new world with 1440x810 cells with a cell size of 1x1 pixels.
        super(1440, 810, 1); 
        
        //Make the player spawn in the middle of the screen
        mainCharacter = new Player();
        addObject(mainCharacter, getWidth()/4, getHeight()/2);
        
        
        //Make the crosshairs appear to the right of the player
        crosshairs = new Crosshairs(mainCharacter);
        addObject(crosshairs, getWidth()/2 + 400, getHeight()/2);
        
        healthbar = new Healthbar(mainCharacter);
        addObject(healthbar, mainCharacter.getX(), mainCharacter.getY());
    }
    
    public void act()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null)
        {
            mainCharacter.faceCrosshairs(crosshairs.getX(), crosshairs.getY());
        }
    }
}