import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PoliteActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class PoliteActor extends Actor
{
    public PoliteActor()
    {
        super();
    }
    
    /**
     * Act - do whatever the PoliteActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    public void addedToWorld(World world)
    {
        GreenfootImage img = getImage();
        int halfW = img.getWidth()/2;
        int halfH = img.getHeight()/2;
        int h = world.getHeight();
        int w = world.getWidth();
        
        Actor a = getOneIntersectingObject(null);
        while(a != null)
        {
            int x = (int)(Math.random()*(w - halfW) + halfW);
            int y = (int)(Math.random()*(h - halfH) + halfH);
            super.setLocation(x,y);//move out of way
            a = getOneIntersectingObject(null);
        }   
    }
    
    public final void setImage(GreenfootImage img)
    {
        //ignore attempts to change image
        if(getImage() == null)
            super.setImage(img);
    }
    
    public final void setImage(String path)
    {
        //ignore attempts to change image
        if(getImage() == null)
            super.setImage(path);
    }
    
    public final void setLocation(int x, int y)
    {
        if(acting())
        {
            super.setLocation(x,y);              
        }
    }
    
    public final void move(int distance)
    {
        if(acting())
            super.move(distance);
    }
    
    public final void removeTouching(java.lang.Class c)
    {
        //ignore
    }
    
    public final void setRotation(int rotation)
    {
        if(acting())
            super.setRotation(rotation);
    }
    
    public final void turn(int amount)
    {
        if(acting())
            super.turn(amount);
    }
    
    public final void turnTowards(int x, int y)
    {
        if(acting())
            super.turnTowards(x,y);
    }
    
    public abstract boolean acting();
}
