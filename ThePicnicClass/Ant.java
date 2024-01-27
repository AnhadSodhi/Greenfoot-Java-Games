import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * <pre>
 * WELCOME TO THE PICNIC!
 * 
 * This class describes the functionality of an Ant
 * The only methods you can override are (see documentation below):
 *  -wantsToMove()
 *  -move()
 *  -wantsToBump()
 *  -wantsToPickUpFood()
 *  -wantsToDropFood()
 * 
 * You can add any other methods you wish to help you complete the methods above.
 * The rules of the picnic are enforced on each turn. 
 * If you break a rule all of your changes are undone and you lose your turn.
 * 
 * The act sequence is as follows:
 *  if(foundFood() && wantsToPickUpFood())
 *      pickupFood();
 *      
 *  updateSpeed();
 *  if(wantsToMove())
 *      move();
 *      
 *  bumpCounter_++;
 *  if(canBump() && wantsToBump())
 *      bump();
 *      
 *  if(holdingFood())
 *  {
 *      if(wantsToDropFood())
 *          dropFood();
 *      if(foundNest())
 *          depositFood();
 *  }    
 *  </pre>   
 */
public abstract class Ant extends PoliteActor
{
    public static final int OBSTACLE = 1;
    public static final int WALK = 3;
    public static final int RUN = 5;
    public static final int BUMP_DELAY = 50;
    
    private int speed_;
    private int points_;
    private Crumb holdingFood_;
    private int bumpCounter_;
    private boolean acting_;
    private boolean depositing_;
    private boolean dropping_;
    private boolean movingFood_;
    
    public Ant()
    {
        super();
        speed_ = RUN;
        points_ = 0;
        holdingFood_ = null;
        bumpCounter_ = 0;
        acting_ = false;
        depositing_ = false;
        dropping_ = false;
        movingFood_ = false;
    }
    
    /**
     * Use this method to determine how many points your Ant or an opponent has.
     * For exmaple, you may decide to only bump ants with a high number of points.
     * @param none
     * @return the number of points the Ant has
     */
    public final int getPoints()
    {
        return points_;
    }
    
    /**
     * Use this method to determine the speed of your Ant or an opponent.
     * You cannot manually change your speed, only examine it to determine where you could move
     * @param none
     * @return the speed (in pixels) of this Ant     * 
     */
    public final int getSpeed()
    {
        return speed_;
    }
    
    /**
     * Use this method to get the name of an Ant.
     * You MUST implement this method in your own Ant class (for the scoreboard)
     * @param none
     * @return the name of this Ant
     */
    public abstract String getOwner();
    
    /**
     * This method determines the whether or not your Ant wants to move. 
     * If it returns true, then your move() method will be called on its turn
     * It it returns false, your Ant will not be moved during its turn
     * DEFAULT is to return true, always move during your turn.
     * @param none
     * @return true or false, whether or not your Ant is going to move during its turn.
     */
    public boolean wantsToMove()
    {
        return true;
    }
    
    /**
     * Defines your Ant's movement.
     * Off all the possible locations it can legally move to, which would you choose?
     * This is where you will likely turn and move your Ant using the usual methods of an Actor.
     * Look for a Crumb/Nest, avoid Spiders, etc.
     * If you move farther than your current speed you will lose a turn.
     * You may find it useful to think of your speed as a bounding box of possible locations:
     * <pre>
     * Ex.  speed_ = 2, * = legal move, X = out of bounds, A = the Ant
     * XXXXXXX
     * X*****X
     * X*****X
     * X**A**X
     * X*****X
     * X*****X
     * XXXXXXX
     * </pre>
     * @param none
     * @return nothing
     */
    public void move()
    {
        
    }
   
    /**
     * Your Ant can determine whether or not it should pickup a Crumb that it finds.
     * If you are near a Crumb of higher value or a Spider with timeToStrike <= 0 you may choose to avoid the Crumb.
     * DEFAULT is to return true, always pickup food you find.
     * This question is asked during your turn if you have found a Crumb (are touching a Crumb)
     * @param none
     * @return nothing
     */
    public boolean wantsToPickUpFood()
    {
        return true;
    }
    
    /**
     * If you canBump() another Ant this method will be called to let you decide if you should actually bump the other Ant.
     * Any Ant can be bumped but it has no effect unless the Ant is holdingFood().
     * Ants holding food that are bumped are forced to drop their food somewhere randomly 50px away.
     * After bumping you will havev a 30 act delay before you can bump again.
     * You may be strategic and only bump when an Ant has a higher number of points than you, there is no other crumb nearby, etc.
     * DEFAULT is to return true, always bump an Ant when you are able to.
     * @param none
     * @return nothing
     */
    public boolean wantsToBump()
    {
        return true;
    }
    
    /**
     * This method determines if your Ant should drop food that it is holding.
     * Spiders have the same speed as Ants that are holding food so you may want to drop your food to get away quickly.
     * You may be on your way to a Nest and bump into another Crumb (or see one close by), worth more points. 
     * You can drop this Crumb and pickup the better one.
     * DEFAULT is to return false, never drop food you are carrying.
     * @param none
     * @return nothing
     */
    public boolean wantsToDropFood()
    {
        return false;
    }      
    
    /**
     * You can ask other Ants to see their Crumb. 
     * If you use the getPoints() method for a Crumb you can make a more accurate judgement about whether or not bump the Ant.
     * You would also know that they can't RUN while holding a Crumb.
     * @param none
     * @return a Crumb being carried by this Ant (null if no Crumb is being held)
     */
    public final Crumb getCrumb()
    {
        return holdingFood_;
    }
    
    /**
     * Let's the Ant know that they have found a Crumb which can be picked up.
     * @param none
     * @return true if the Ant is not currently holding a Crumb and has found one, false otherwise.
     */
    public final boolean foundFood()
    {
        //touching a crumb that no other Ant is holding and not holding one already
        if(holdingFood_ != null)
            return false;
        
        Crumb c = (Crumb)getOneIntersectingObject(Crumb.class);
        if(c == null)
            return false;
            
        ArrayList<Ant> ants = (ArrayList<Ant>)getWorld().getObjects(Ant.class);
        for(Ant a : ants)
        {
            if(a.getCrumb() == c)//already taken
                return false;
        }
        return true;
    }
    
    /**
     * Let's the Ant know that they have found a Nest.
     * @param none
     * @return true if the Ant is not touching a Nest, false otherwise.
     */
    public final boolean foundNest()
    {
        return getOneIntersectingObject(Nest.class) != null;
    }
    
    /**
     * Let's the Ant know that they are able to bump another Ant.
     * Ant's have a delay and can't bump on successive turns. 
     * They must wait BUMP_DELAY before they can bump another Ant.
     * @param none
     * @return true if the Ant is not touching another Ant and they haven't bumped anyone for the previous BUMP_DELAY turns, false otherwise.
     */
    public final boolean canBump()
    {
        return bumpCounter_ >= BUMP_DELAY && getOneIntersectingObject(Ant.class) != null;
    }
    
    /**
     * Determine if the Ant is holding a Crumb
     * @param none
     * @return true if the Ant is holding a Crumb, false otherwise
     */
    public final boolean holdingFood()
    {
        return holdingFood_ != null;
    }
    
    /**
     * A method to help you determine if your Ant has reached the edge of the screen.
     * Movements past the edge of the screen are ignored, you must turn away from the edge.
     * Touching the edge is ok if you are moving away from it.
     * @param none
     * @return true if the Ant it touching the edge of the screen, false otherwise.
     */
    public final boolean isAtEdge()
    {
        int x = getX();
        int y = getY();
        GreenfootImage img = getImage();
        int halfW = img.getWidth()/2;
        int halfH = img.getHeight()/2;
        int buffer = 2;
        
        World world = getWorld();
        int w = world.getWidth();
        int h = world.getWidth();
        
        return x + halfW + buffer >= w || x - halfW - buffer <= 0 || y + halfH + buffer >= h || y - halfH - buffer <= 0;
    }
    
    private boolean distanceViolation(int savedX, int savedY, int savedRotation, int max)
    {
        int dx = savedX - getX();
        int dy = savedY - getY();
        if( dx > max || dy > max )//cheating! - lose turn
        {
            setLocation(savedX, savedY);
            setRotation(savedRotation);
            return true;
        }
        
        return false;
    }
    
    /**
     * This method is not overridable in your Ant class. 
     * The information here is only to help you understand how your Ant work.
     * You cannot change this method or use it.
     * First Ants move, then they decide if the should:
     * 1) bump
     * 2) drop their food
     * 3) pick up food
     * If an Ant has reached a Nest it will always deposit their Crumb for points
     * 
     * The act full sequence is as follows:
     * <pre>
     * 
     *  updateSpeed();
     *  if(wantsToMove())
     *      move();
     *      
     *  bumpCounter_++;
     *  if(canBump() && wantsToBump())
     *      bump();
     *  
     *  if(holdingFood() && wantsToDropFood())
     *      dropFood();
     *  if(foundFood() && wantsToPickUpFood())
     *      pickupFood();  
     *      
     *  if(holdingFood())
     *  {
     *      if(foundNest())
     *          depositFood();
     *  }    
     *  </pre> 
     *  @param none
     *  @return nothing
     */
    public final void act() 
    {
        acting_ = true;
        int savedX = getX();
        int savedY = getY();
        int savedRotation = getRotation();
        
        updateSpeed();        
        if(wantsToMove())
            move();
        
        if(distanceViolation(savedX, savedY, savedRotation, speed_))
            return;
        /*int dx = savedX - getX();
        int dy = savedY - getY();
        if( dx > speed_ || dy > speed_ )//cheating! - lose turn
        {
            setLocation(savedX, savedY);
            setRotation(savedRotation);
        }*/
        
        //at this point they've made a legal move but we have to enforce that no other movement occurs
        savedX = getX();
        savedY = getY();
        savedRotation = getRotation();
        
        bumpCounter_++;
        boolean lostTurn = false;
        boolean bump = false;
        boolean drop = false;
        boolean pickup = false;
        if(wantsToBump() && canBump())
        {
            if(distanceViolation(savedX, savedY, savedRotation, 0))//wantsToBump could also move
                lostTurn = true;
            
            bump = true;
        }
        
        if(holdingFood() && wantsToDropFood())
        {
            if(distanceViolation(savedX, savedY, savedRotation, 0))//wantsToDropFood could also move
                lostTurn = true;
                
            drop = true;
        }
        if(foundFood() && wantsToPickUpFood())
        {
            if(distanceViolation(savedX, savedY, savedRotation, 0))//wantsToDropFood could also move
                lostTurn = true;
                
            pickup = true;
        }
        
        if(!lostTurn)
        {
            if(bump)
                bump();
            if(drop)
                dropFood();
            if(pickup)
                pickupFood();
                
            if(holdingFood())
            {
                movingFood_ = true;
                holdingFood_.setLocation(getX(), getY());//crumb follows Ant
                movingFood_ = false;
                
                if(foundNest())
                    depositFood();
            }  
        }
        
        acting_ = false;
    }
    
    /**
     * Called only by an active Spider.
     * This method takes away points equal to the Spider's venomStrength and forces the Ant to drop its food.
     * @param amount The amount of points to take away from the Ant
     * @return nothing
     */
    public final void injure(int amount)
    {
        ArrayList<Spider> spiders = (ArrayList<Spider>)getIntersectingObjects(Spider.class);
        for(Spider s : spiders)
        {
            if(s.acting())//spider is the Actor trying to injure the Ant (not another Ant cheating)
            {
                points_ -= amount;
                dropFood();
            }
        }
    }
    
    /**
     * Not for use in the game. 
     * This method determines the speed of the Ant during its turn.
     * @param nothing
     * @return nothing
     */
    private final void updateSpeed()
    {
        Obstacle blocked = (Obstacle)getOneIntersectingObject(Obstacle.class);
        if(blocked != null)
            speed_ = OBSTACLE;
        else if(holdingFood())
            speed_ = WALK;
        else
            speed_ = RUN;
    }    
    
    /**
     * Not for use in the game. 
     * This method picks up a Crumb for an Ant.
     * Should only be called after foundFood has returned true.
     * @param nothing
     * @return nothing
     */
    private final void pickupFood()
    {
        holdingFood_ = (Crumb)getOneIntersectingObject(Crumb.class);;
        speed_ = WALK;        
    }    
    
    /**
     * Not for use in the game. 
     * This method deposits a Crumb for an Ant and replaces it with a new (random) Crumb.
     * @param nothing
     * @return nothing
     */
    private final void depositFood()
    {
        points_ += holdingFood_.points();
        Yard y = (Yard)getWorld();
        depositing_ = true;
        y.replaceCrumb(holdingFood_);//replace crumb
        depositing_ = false;
        holdingFood_ = null;
        speed_ = RUN;        
    }
    
    /**
     * Not for use in the game. 
     * This method makes an Ant drop its food to a random location 50 px away.
     * @param nothing
     * @return nothing
     */
    private final void dropFood()
    {
        if(holdingFood_ != null)
        {
            //drop on 50 pixel radius
            int radius = 50;
            double angle = Math.random()*2*Math.PI;//[0-360) in radians
            int x = (int)(getX() + radius * Math.cos(angle));
            int y = (int)(getY() + radius * Math.sin(angle));
            dropping_ = true;
            holdingFood_.setLocation(x, y);
            dropping_ = false;
            holdingFood_ = null;
            speed_ = RUN;
        }
    }
    
    /**
     * Not for use in the game. 
     * This method bumps another Ant.
     * canBump() must be called prior to this or a null pointer can be thrown
     * @param nothing
     * @return nothing
     */
    private final void bump()
    {
        Ant ant = (Ant)getOneIntersectingObject(Ant.class);
        ant.dropFood();
        bumpCounter_ = 0;//reset
    }
    
    /**
     * Not for use, helper method to enfore rules of game
     */
    public final boolean acting()
    {
        return acting_;
    }
    
    /**
     * Not for use, helper method to enfore rules of game
     */
    public final boolean depositing()
    {
        return depositing_;
    }
    
    /**
     * Not for use, helper method to enfore rules of game
     */
    public final boolean dropping()
    {
        return dropping_;
    }
    
    /**
     * Not for use, helper method to enfore rules of game
     */
    public final boolean movingFood()
    {
        return movingFood_;
    }
}
