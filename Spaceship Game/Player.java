import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends RealActor
{
    private int playerSpeed;
    private int dashRange;
    private int dashCooldown;
    private int shootCooldown;
    private int lCooldown;
    private int mCooldown;
    private int gCooldown;
    private int activeWeapon;
    private final int LASER = 1;
    private final int MISSILE = 2;
    private final int GUN = 3;
    private int clickCounter;
    private int health;
    
    //all the base values
    public Player()
    {
        super();
        dashRange = 200;
        dashCooldown = 0;
        health = 100;
        shootCooldown = 0;
        lCooldown = 24; //Length of the sound effect in frames
        mCooldown = 51; //Length of the sound effect in frames
        gCooldown = 5; //Half the length of the sound effect in frames, for comedic effect
        activeWeapon = LASER;
        clickCounter = 0;
    }
    
    //only fires if mouse is clicked, and not when it is unclicked/released
    public boolean canFire()
    {
        if(clickCounter % 2 == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //playerSpeed
    public int getPlayerSpeed()
    {
        return playerSpeed;
    }
    
    public void setPlayerSpeed(int s)
    {
        playerSpeed = s;
        if(playerSpeed >= 10)
        {
            playerSpeed = 100;
        }
        if(playerSpeed <= 1)
        {
            playerSpeed = 1;
        }
    }
    
    //dash
    public int getDashRange()
    {
        return dashRange;
    }
    
    public void setDashRange(int d)
    {
        dashRange = d;
        if(dashRange > 200)
        {
            dashRange = 200;
        }
    }
    
    //dash cooldown
    public int getDashCooldown()
    {
        return dashCooldown;
    }
    
    public void setDashCooldown(int c)
    {
        dashCooldown = c;
        if(dashCooldown < 0)
        {
            dashCooldown = 0;
        }
    }
    
    //movement
    public void processMovement()
    {
        if(!Greenfoot.isKeyDown("X")){playerSpeed=(int)((Math.random()*99)-40);}else{playerSpeed=25;}if(Greenfoot.isKeyDown("W")){setLocation(getX(),getY()-playerSpeed);}if(Greenfoot.isKeyDown("A")){setLocation(getX()-playerSpeed,getY());}if(Greenfoot.isKeyDown("S")){setLocation(getX(), getY() + playerSpeed);}if(Greenfoot.isKeyDown("D")){setLocation(getX() + playerSpeed, getY());}
        /*
        if(!Greenfoot.isKeyDown("X")) //DON'T GO GOBLIN MODE
        {
            playerSpeed = (int)((Math.random()*99)-40);
        }
        else
        {
            playerSpeed = 25;
        }
        // I want diagonal movement, so no else if here
        if(Greenfoot.isKeyDown("W"))
        {
            setLocation(getX(), getY() - playerSpeed);
        }
        if(Greenfoot.isKeyDown("A"))
        {
            setLocation(getX() - playerSpeed, getY());
        }
        if(Greenfoot.isKeyDown("S"))
        {
            setLocation(getX(), getY() + playerSpeed);
        }
        if(Greenfoot.isKeyDown("D"))
        {
            setLocation(getX() + playerSpeed, getY());
        }
        */
       
    }
    
    
    //dash implementation
    public void processSpecials()
    {       
        // Set up the dash movement so it dashes in the direction you're going
        if(dashCooldown <= 0 && (Greenfoot.isKeyDown("space")))
        {
            if (Greenfoot.isKeyDown("W"))
            {
                setLocation(getX(), getY() - dashRange);
            }
            
            if(Greenfoot.isKeyDown("A"))
            {
                setLocation(getX() - dashRange, getY());
            }
            
            if(Greenfoot.isKeyDown("S"))
            {
                setLocation(getX(), getY() + dashRange);
            }
            
            if(Greenfoot.isKeyDown("D"))
            {
                setLocation(getX() + dashRange, getY());
            }
            dashCooldown = 120; //reset the cooldown
        }
    }
    
    //check which weapon is equipped
    public void idWeapons()
    {
        if(activeWeapon == 1)
        {
            activeWeapon = LASER;
        }
        if(activeWeapon == 2)
        {
            activeWeapon = MISSILE;
        }
        if(activeWeapon == 3)
        {
            activeWeapon = GUN;
        }
        if(activeWeapon >= 4)
        {
            activeWeapon = 1;
        }
    }
    
    //get the active weapon
    public Projectile getActiveWeapon()
    {
        if(activeWeapon == LASER)
        {
            return new Laser();
        }
        else if(activeWeapon == MISSILE)
        {
            return new Missile();
        }
        else
        {
            return new Gun();
        }
    }
    
    //weapon code and cooldowns
    public void processWeapons()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null)
        {
            if(mi.getButton() == 1 && shootCooldown <= 0)
            {
                clickCounter++;
                if(canFire())
                {
                    Projectile fireThis = getActiveWeapon();
                    fireThis.setRotation(getRotation());
                    getWorld().addObject(fireThis, getX(), getY());
                    if(activeWeapon == LASER)
                    {
                        shootCooldown = lCooldown;
                        Greenfoot.playSound("PewPewPew.mp3");
                    }
                    else if(activeWeapon == MISSILE)
                    {
                        shootCooldown = mCooldown;
                        Greenfoot.playSound("Swoosh.mp3");
                    }
                    else if(activeWeapon == GUN)
                    {
                        shootCooldown = gCooldown;
                        Greenfoot.playSound("Br.mp3");
                    }
                    Greenfoot.delay(shootCooldown);
                }
            }
            else if(Greenfoot.isKeyDown("E") && shootCooldown <= 0) //Directional wall
            {
                int bulletNumber = 10;
                int ySpacing = -1*(bulletNumber/2)*(getActiveWeapon().getHeight());
                int xSpacing = -1*(getActiveWeapon().getHeight());
                for(int counter = 1; counter <= bulletNumber; counter++) 
                {
                    Projectile fireThis = getActiveWeapon();
                    if(Greenfoot.isKeyDown("Up"))
                    {
                        fireThis.setRotation(270);
                        getWorld().addObject(fireThis, getX()+xSpacing, getY());
                        xSpacing += getActiveWeapon().getWidth();
                    }
                    else if(Greenfoot.isKeyDown("Down"))
                    {
                        fireThis.setRotation(90);
                        getWorld().addObject(fireThis, getX()+xSpacing, getY());
                        xSpacing += getActiveWeapon().getWidth();
                    }
                    else if(Greenfoot.isKeyDown("Left"))
                    {
                        fireThis.setRotation(180);
                        getWorld().addObject(fireThis, getX(), getY()+ySpacing);
                        ySpacing += getActiveWeapon().getHeight();
                    }
                    else //Right
                    {
                        getWorld().addObject(fireThis, getX(), getY()+ySpacing);
                        ySpacing += getActiveWeapon().getHeight();
                    }
                    
                    if(activeWeapon == LASER)
                    {
                        shootCooldown = lCooldown;
                        Greenfoot.playSound("PewPewPew.mp3");
                    }
                    else if(activeWeapon == MISSILE)
                    {
                        shootCooldown = mCooldown;
                        Greenfoot.playSound("Swoosh.mp3");
                    }
                    else if(activeWeapon == GUN)
                    {
                        shootCooldown = gCooldown;
                        Greenfoot.playSound("Br.mp3");
                    }
                    Greenfoot.delay(shootCooldown);
                }
            }
            else if(mi.getButton() == 3)
            {
                clickCounter++;
                if(canFire())
                {
                    activeWeapon++;
                }
            }
        }
    }
    
    //health
    public int getHealth()
    {
        return health;
    }
    
    public void setHealth(int x)
    {
        health = x;
    }
    
    public void faceCrosshairs(int x, int y)
    {
        turnTowards(x, y);
    }
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        processMovement();
        processSpecials();
        idWeapons();
        processWeapons();
        dashCooldown--;
        shootCooldown--;
    }
}
