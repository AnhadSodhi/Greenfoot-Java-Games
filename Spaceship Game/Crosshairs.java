import greenfoot.*;

/**
 * Write a description of class Crosshairs here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Crosshairs extends RealActor
{
    private int crossXPos;
    private int crossYPos;
    private int crossXModifier;
    private int crossYModifier;
    private int timer;
    private Player player;
    
    public Crosshairs(Player p)
    {
        super();
        player = p;
        crossXModifier = 0;
        crossYModifier = 0;
        timer = 0;
        
    }
    
    public void checkForDamage()
    {
        if(isTouching(Player.class))
        {
            removeTouching(Player.class);
            int count = 1;
            int printTimes = 50;
            while(count <= printTimes)
            {
                System.out.println("Haha, you died. Visit this link: https://imgur.com/a/3h2qI5X");
                count++;
            }
            if(count > printTimes)
            {
                Greenfoot.stop();
            }
        }
    }
    
    public void checkIfFreeTrialOnLifeHasExpired(double percentChance)
    {
        if(timer % 30 == 0)
        {
            double dieNum = (Math.random()*100);
            int count = 1;
            int printTimes = 50;
            while(dieNum + percentChance >= 100 && count <= printTimes)
            {
                player.setHealth(0);
                System.out.println("Oh no! Your free trial on life has expired! :(");
                count++;
            }
            if(count > printTimes)
            {
                Greenfoot.stop();
            }
        }
    }
    
    //movement
    public void processCrossMovement()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(!Greenfoot.isKeyDown("X")) //DON'T GO GOBLIN MODE
        {
            crossXModifier = (int)((Math.random()*99)-50);
            crossYModifier = (int)((Math.random()*99)-50);
        }
        else
        {
           crossXModifier = 0;
           crossYModifier = 0;
        }
        if(mi != null)
        {
            crossXPos = mi.getX() + crossXModifier;
            crossYPos = mi.getY() + crossYModifier;
            setLocation(crossXPos, crossYPos);
        }
        else
        {
            crossXPos = 0;
            crossYPos = 0;
        }
    }
    
    //get coordinates so the player can reference them
    public int getX()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null)
        {
            return crossXPos;
        }
        else
        {
            return 0;
        }
    }
    
    public int getY()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null)
        {
            return crossYPos;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Act - do whatever the Crosshairs wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkForDamage();
        processCrossMovement();
        checkIfFreeTrialOnLifeHasExpired(0);
        timer++;
    } 
    
}