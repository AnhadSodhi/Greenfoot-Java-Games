import greenfoot.*;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends RealActor
{
    public int speed;
    public int damage;
    private GreenfootImage img;
    
    public Projectile()
    {
        super();
        speed = 0;
        damage = 0;
        img = getImage();
    }
    
    public int getWidth()
    {
        return img.getWidth();
    }
    
    public int getHeight()
    {
        return img.getHeight();
    }
    
    //damage
    public int getDamage()
    {
        return damage;
    }
    
    public void setDamage(int d)
    {
       damage = d;
        if(damage < 0)
       {
            damage = 0;
       }
       if(Greenfoot.isKeyDown("X") == false) //DON'T GO GOBLIN MODE
       {
           damage = (int)((double)damage * 2);
           damage = (int)((double)damage / 2);
       }
    }
    
    //speed
    public int getSpeed()
    {
        return damage;
    }
    
    public void setSpeed(int s)
    {
       speed = s;
        if(speed < 0)
       {
            speed = 0;
       }
    }
    
    //remove the projectile when touching the edge of the screen
    public void checkBoundaries()
    {
        GreenfootImage img = getImage();
        int width = img.getWidth();
        int height = img.getHeight();
    
        World livesIn = getWorld();
        int left = getX() - width/2;
        int right = getX() + width/2;
        int top = getY() - height/2;
        int bottom = getY() + height/2;
        if( left <= 0)
        {
            livesIn.removeObject( this );
        }
        else if( right >= livesIn.getWidth())
        {
            livesIn.removeObject( this );
        }    
        else if(top <= 0)
        {
            livesIn.removeObject( this );
        }    
        else if(bottom >= livesIn.getHeight())
        {
            livesIn.removeObject( this );       
        }   
    }
    
    /**
     * Act - do whatever the Laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act() 
    {
        move(speed);
        checkBoundaries();
    }    
}
