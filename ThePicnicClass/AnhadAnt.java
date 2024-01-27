import greenfoot.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Write a description of class AnhadAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnhadAnt extends Ant
{
    //These are used in multiple mthods, so I put them out here
    private int spiderChaseDistance = 100;
    private int spiderEmergencyDistance = 75;
    
    public String getOwner()
    {
        return "AnhadAnt";
    }
    
    public boolean wantsToMove()
    {
        return true;
    }
    
    /*
     * Finds the distance of walking in a straight line to the destination
     */
    public int findDistance(int myX, int myY, int otherX, int otherY)
    {
        final int xDiff = Math.abs(otherX-myX);
        final int yDiff = Math.abs(otherY-myY);
        
        return (int)(Math.hypot(xDiff, yDiff));
    }
    
    public Actor getClosestActorInRange(int range, Actor a, Class c)
    {
        ArrayList<Actor> closeActors = (ArrayList<Actor>)getObjectsInRange(range, c);
        if(closeActors.size() > 0)
        {
            int index = 0;
            
            int myX = a.getX();
            int myY = a.getY();
            
            int min = 99999;
            
            for(int n = 0; n < closeActors.size(); n++)
            {
                Actor closest = closeActors.get(n);
                int otherX = closest.getX();
                int otherY = closest.getY();
                
                int distance = findDistance(myX, myY, otherX, otherY);
                if(distance < min) //find the lowest distanced actor, aka the closest actor
                {
                    min = distance;
                    index = n;
                }
            }
            
            return closeActors.get(index);
        }
        else
        {
            return null;
        }
    }
    
    public int getObstaclesInPath(int startX, int startY, int endX, int endY)
    {
        int distance = findDistance(startX, startY, endX, endY);
        int obstaclesNum = 0;
        
        ArrayList<Obstacle> obstacleList = new ArrayList();
        int obstacleRange = 25; //how far away an obstacle can be to count as "in the path"
        
        turnTowards(endX, endY);
        for(int n = 0; n < distance; n++) //every loop, move towards the object 5 pixels, and add the objects in range to obstacleList
        {
            ArrayList<Obstacle> currentObstacles = (ArrayList<Obstacle>)getObjectsInRange(obstacleRange, Obstacle.class);
            if(currentObstacles.size() > 0)
            {
                for(int i = 0; n < currentObstacles.size(); n++)
                {
                    obstacleList.add(currentObstacles.get(i));
                }
            }
            move(5);
        }
        setLocation(startX, startY); //move back to the starting position to avoid moving penalties
        
        //remove duplicates
        Set<Obstacle> obstacleSet = new HashSet(obstacleList);
        obstacleList.clear();
        obstacleList.addAll(obstacleSet);
        
        return obstacleList.size();
    }
    
    public Crumb getBestCrumb(ArrayList<Crumb> crumbs)
    {
        int bestIndex = 0;
        double bestScore = -1;
        
        int myX = getX();
        int myY = getY();
        
        for(int n = 0; n < crumbs.size(); n++)
        {
            //get current crumb X and Y
            Crumb currentCrumb = crumbs.get(n);
            int crumbX = currentCrumb.getX();
            int crumbY = currentCrumb.getY();
            int crumbValue = -1; //start at -1, adjust after finding out which crumb type it is
            String currentClass = ""+currentCrumb.getClass()+"";
            
            Spider closestSpider = (Spider)getClosestActorInRange(50, currentCrumb, Spider.class); //range is spider sight range/2
            
            int distance = findDistance(myX, myY, crumbX, crumbY);
            
            int penalty = 3*(getObstaclesInPath(getX(), getY(), currentCrumb.getX(), currentCrumb.getY())); //penalty is 3*number of obstacles in path
            
            if(closestSpider == null) //if a spider is close to the crumb, ignore it
            {
                //assign a crumb value depending on which crumb it is
                if(currentClass.equals("Crumb1.class"))
                {
                    crumbValue = 1;
                }
                else if(currentClass.equals("Crumb3.class"))
                {
                    crumbValue = 3;
                }
                else
                {
                    crumbValue = 5;
                }
            }
            
            double crumbScore = (double)crumbValue/distance+penalty; //"score" the crumb
            
            if(crumbScore > bestScore) //find out which crumb has the best score
            {
                bestScore = crumbScore;
                bestIndex = n;
            }
        }
        return crumbs.get(bestIndex);
    }
    
    public void runFromSpider(Spider closestSpider)
    {
        World world = getWorld();
        
        int spiderTurnAmount = 65; //the speed at which I turn when encountering the edge of the screen
        
        int wallBuffer = 50; //the range that counts as "close to the edge of the screen"
        
        int nestRange = 200;
        Nest closestNest = (Nest)(getClosestActorInRange(nestRange, this, Nest.class));
        
        //start facing away from the spider
        turnTowards(closestSpider.getX(), closestSpider.getY());
        turn(180);
        
        if(closestNest != null) //turn to the closest nest if I'm holding food and being chased
        {
            if(holdingFood())
            {
                turnTowards(closestNest.getX(), closestNest.getY());
            }
        }
        
        else
        {
            //if I'm close to a wall, turn accordingly
            if(getX() < wallBuffer)
            {
                if(getY() > world.getHeight()/2)
                {
                    turn(spiderTurnAmount); //make the ant gradually turn to the right
                }
                if(getY() <= world.getWidth()/2)
                {
                    turn(-1*spiderTurnAmount); //make the ant gradually turn to the left
                }
            }
            else if(getX() > world.getWidth()-wallBuffer)
            {
                if(getY() <= world.getHeight()/2)
                {
                    turn(spiderTurnAmount); //make the ant gradually turn to the right
                }
                if(getY() > world.getWidth()/2)
                {
                    turn(-1*spiderTurnAmount); //make the ant gradually turn to the left
                }
            }
        }
        move(getSpeed());
    }
    
    public void move()
    {
        Spider closestSpider = (Spider)(getClosestActorInRange(spiderChaseDistance, this, Spider.class));
        
        Ant closestAnt = (Ant)(getClosestActorInRange(35, this, Ant.class));
        
        //find the best crumb on the screen
        ArrayList<Crumb> closeCrumbs = (ArrayList<Crumb>)getObjectsInRange(99999, Crumb.class);
        Crumb bestCrumb = getBestCrumb(closeCrumbs);
        
        Nest closestNest = (Nest)(getClosestActorInRange(99999, this, Nest.class));
        
        if(closestSpider != null && closestSpider.timeToStrike() <= 0) //if I'm being chased by a spider
        {
            runFromSpider(closestSpider);
        }
        
        else if(closestAnt != null && canBump()) //chase down ants to bump them if they're in range and I'm not holding food
        {
            if(closestAnt.holdingFood() && !holdingFood())
            {
                turnTowards(closestAnt.getX(), closestAnt.getY());
                move(getSpeed());
            }
        }
        
        else if(!holdingFood()) //go to the best crumb
        {
            turnTowards(bestCrumb.getX(), bestCrumb.getY());
            move(getSpeed());
        }
        
        else //if I'm carrying a crumb, go to the closest nest
        {
            turnTowards(closestNest.getX(), closestNest.getY());
            move(getSpeed());
        }
    }
   
    public boolean wantsToPickUpFood() //I tried ignoring crumbs when a spider was near, but I found I got more points by picking it up
    {
        return true;
    }
    
    public boolean wantsToBump()
    {
        return true;
    }
    
    public boolean wantsToDropFood() //Holding on to food even while being chased resulted in more points for me in my testing
    {
        return false;
    } 
}
