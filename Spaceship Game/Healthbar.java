import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

/**
 * Write a description of class Healthbar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Healthbar extends Actor
{
    private Player player;
    private int timer;
    
    public Healthbar(Player p)
    {
        super();
        player = p;
        timer = 0;
    }
    
    //attempt to display player's health under player (unsuccessful, ran out of time before due date)
    public void draw()
    {
        //String healthString = new String("Congratulations! You have not died yet! Your health is: " + player.getHealth());
        //System.out.println(healthString);
    }
   
    /**
     * Act - do whatever the Healthbar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        timer++;
        draw();
    }    
}
