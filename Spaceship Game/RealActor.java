
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This actor tries to mimic everything about the Actor class but add the feature of real valued (double)
 * movement and coordinates. In this sense it will allow more accurate movement than the standard Actor and
 * allows for decimal movement (ie. speed = 1.5, gravity = 9.8) and rotations.
 * 
 * @author Cam Joyce 
 * @version 1.0 April 2nd, 2014
 */
public class RealActor extends Actor
{
    protected double xLoc_;
    protected double yLoc_;
    protected double theta_;
    
    /**
     * Creates an Actor that will retain real valued coordinates and rotations
     */
    public RealActor()
    {
        super();
        theta_ = 0;
    }
    
    /**
     * This method is called when an actor is added to a world so it allows us to initialize the
     * coordinates at that time.  
     * @param world The world this Actor was added to
     */
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        xLoc_ = super.getX();
        yLoc_ = super.getY();
    }
    
    /**
     * Sets the on screen position of this Actor
     * @param x the x coordinate of its pixel location
     * @param y the y coordinate of its pixel location
     */
    public void setLocation(int x, int y)
    {
        xLoc_ = x;
        yLoc_ = y;
        super.setLocation(x,y);
    }
    
    /**
     * Sets the on screen position of this Actor using decimal values 
     * @param x the x coordinate of its pixel location
     * @param y the y coordinate of its pixel location
     */
    public void setLocation(double x, double y)
    {
        xLoc_ = x;
        yLoc_ = y;
        super.setLocation(this.getX(),this.getY());
    }
    
    /**
     * Turns that actor a given amount by changing its rotation by a given amount
     * @param amount how much to turn the Actor from its current orientation. Positive values are clockwise while negative are counter clockwise
     */
    public void turn(int amount)
    {
        theta_ += amount;
        super.setRotation(this.getRotation());
    }
    
    /**
     * Turns that actor a given amount by changing its rotation by a given amount as a decimal
     * @param amount how much to turn the Actor from its current orientation. Positive values are clockwise while negative are counter clockwise
     */
    public void turn(double amount)
    {
        theta_ += amount;
        super.setRotation(this.getRotation());
    }
    
    /**
     * Turns that actor a toward a location on the screen
     * @param x the x coordinate of the pixel location to turn towards
     * @param y the y coordinate of the pixel location to turn towards
     */
    public void turnTowards(int x, int y)
    {
        double dx = xLoc_ - x;
        double dy = yLoc_ - y;
        theta_ = Math.toDegrees(Math.atan2(dy, dx) + Math.PI);
        super.setRotation( this.getRotation() );
    }
    
    /**
     * Turns that actor a toward a location on the screen if the location uses decimal values
     * @param x the x coordinate of the pixel location to turn towards
     * @param y the y coordinate of the pixel location to turn towards
     */
    public void turnTowards(double x, double y)
    {
        double dx = xLoc_ - x;
        double dy = yLoc_ - y;
        theta_ = Math.toDegrees(Math.atan2(dy, dx) + Math.PI);
        super.setRotation( this.getRotation() );
    }
    
    /**
     * Changes the rotation of this Actor to one given
     * @param theta The angle that the actor will now rotate to
     */
    public void setRotation(double theta)
    {
        theta_ = theta;
        super.setRotation( this.getRotation() );
    }
    
    /**
     * Changes the rotation of this Actor to one given as a decimal
     * @param theta The angle that the actor will now rotate to
     */
    public void setRotation(int theta)
    {
        theta_ = theta;
        super.setRotation( theta );
    }
    
    /**
     * Moves this Actor by a given distance
     * @param distance The amount this actor will be moved (using its current rotation)
     */
    public void move(int distance)
    {
        double radians = Math.toRadians(theta_);
        xLoc_ += distance*Math.cos(radians);
        yLoc_ += distance*Math.sin(radians);
        super.setLocation( this.getX(), this.getY() );        
    }
    
    /**
     * Moves this Actor by a given distance as a decimal
     * @param distance The amount this actor will be moved (using its current rotation)
     */
    public void move(double distance)
    {
        double radians = Math.toRadians(theta_);
        xLoc_ += distance*Math.cos(radians);
        yLoc_ += distance*Math.sin(radians);
        super.setLocation( this.getX(), this.getY() ); 
    }
    
    /**
     * Gets the x coordinate of this Actor
     * @return the x pixel location of this Actor
     */    
    public int getX()
    {
        return (int)Math.round(xLoc_);
    }
    
    /**
     * Gets the y coordinate of this Actor
     * @return the y pixel location of this Actor
     */    
    public int getY()
    {
        return (int)Math.round(yLoc_);
    }
    
    /**
     * Gets the rotation/orientation of this Actor
     * @return the direction this Actor is facing
     */    
    public int getRotation()
    {
        return (int)Math.round(theta_);
    }
    
    /**
     * Gets the real x coordinate of this Actor, not the rounded value where the Actor is located on screen
     * @return the real x location of this Actor
     */    
    public double getRealX()
    {
        return xLoc_;
    }
    
    /**
     * Gets the real y coordinate of this Actor, not the rounded value where the Actor is located on screen
     * @return the real y location of this Actor
     */    
    public double getRealY()
    {
        return yLoc_;
    }
    
    /**
     * Gets the rotation/orientation of this Actor, not the rounded value the Actor is using for display purposes
     * @return the direction this Actor is facing
     */    
    public double getRealRotation()
    {
        return theta_;
    }
}