import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.ArrayList;
import javax.swing.WindowConstants;

/**
 * Write a description of class Yard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Yard extends World
{
    private int maxCrumbs_;
    private JFrame results_;
    private JDialog d_;
    private List<JLabel> labels_;
    private int simCount_;
    private int duration_;
    
    /**
     * Constructor for objects of class Yard.
     * 
     */
    public Yard()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 500, 1); 
        simCount_ = 0;
        duration_ = 10000;
        maxCrumbs_ = 1;
        addFood(maxCrumbs_);
        
        addRandom(new Spider());
        addRandom(new Spider());
        addRandom(new Spider());
        
        addRandom(new Nest());
        addRandom(new Nest());
        addRandom(new Nest());
        addRandom(new Nest());
        addRandom(new Nest());
        
        addRandom(new Rock());

        addRandom(new Mushroom());
        
        addRandom(new Stone());
        
        addRandom(new Grass());
        
        addRandom(new Leaf());
        
        addRandom(new KeelanAnt()); 
        addRandom(new temiAnt()); 
        addRandom(new PauleneAnt()); 
        addRandom(new ShayAnt()); 
        addRandom(new AidanAnt()); 
        addRandom(new MitchellAnt()); 
        addRandom(new JackAnt()); 
        addRandom(new NathanielAnt()); 
        addRandom(new DominicAnt()); 
        addRandom(new noorsAnt());
        addRandom(new EthanAnt()); 
        addRandom(new MylesAnt()); 
        addRandom(new RheaAnt()); 
        addRandom(new RedAntT()); 
        addRandom(new AnhadAnt());
        addRandom(new ValleyAnt()); 
        addRandom(new hehehehehe());
        initializeScoreWindow();
    }
    
    public Yard(int spiders, int crumbs, int nests, int obstacles, int duration)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 500, 1); 
        simCount_ = 0;
        duration_ = duration;
        maxCrumbs_ = crumbs;
        addFood(maxCrumbs_);
        
        for(int n = 0; n < spiders; n++)
        {
            addRandom(new Spider());
        }
        
        for(int n = 0; n < nests; n++)
        {
            addRandom(new Nest());
        }
        
        for(int n = 0; n < obstacles; n++)
        {
            int choice = (int)(Math.random()*5);
            if(choice == 0)
                addRandom(new Rock());
            else if(choice == 1)
                addRandom(new Mushroom());
            else if(choice == 2)
                addRandom(new Stone());
            else if(choice == 3)
                addRandom(new Grass());
            else
                addRandom(new Leaf());
        }
        
        //add players
        addRandom(new SimpleAnt());        
        initializeScoreWindow();
    }
    
    public void checkCrumbs()
    {
        ArrayList<Ant> ants = (ArrayList<Ant>)getObjects(Ant.class);
        for(Ant a : ants)
        {
            ArrayList<Ant> ants2 = (ArrayList<Ant>)getObjects(Ant.class);
            for(Ant a2 : ants2)
            {
                if(a.getCrumb() == a2.getCrumb() && a2.getCrumb() != null && a2 != a)//already taken
                    System.out.println("broke");
            }
        }
    }
    
    public void act()
    {
        simCount_++;
        if(simCount_ >= duration_)
        {
            Greenfoot.stop();
        }
        
        List<Crumb> food = getObjects(Crumb.class);
        int crumbs = food.size();
        if(food.size() == 0)
            System.out.println("empty");
        if(crumbs < maxCrumbs_)
        {
            addFood(maxCrumbs_ - crumbs);
        }
        
        updateScoreWindow();
        checkCrumbs();
    }
    
    private Crumb randomCrumb()
    {
        int type = (int)(Math.random()*3);
        Crumb random = null;
        if(type == 0)
            random = new Crumb1();
        else if(type == 1)
            random = new Crumb3();
        else
            random = new Crumb5();
            
        return random;
    }
    
    public void replaceCrumb(Crumb replace)
    {
        ArrayList<Ant> ants = (ArrayList<Ant>)getObjects(Ant.class);
        for(Ant a : ants)
        {
            if(a.acting() && a.depositing())
            {
                super.removeObject(replace);
                addRandom( randomCrumb() );
                break;
            }
        }
    }
    
    private void addFood(int n)
    {
        for(int count = 0; count < n; count++)
        {
            addRandom( randomCrumb() );
        }
    }
    
    private void addRandom(PoliteActor a)
    {
        GreenfootImage img = a.getImage();
        int width = img.getWidth();
        int height = img.getHeight();
        int x = (int)(Math.random()*(getWidth() - width/2) + width/2);
        int y = (int)(Math.random()*(getHeight() - height/2) + height/2);
        super.addObject(a, x, y);
    }
    
    public void initializeScoreWindow()
    {
        List<Ant> ants = getObjects(Ant.class);
        int height = 25;
        results_ = new JFrame("Results");       
        d_ = new JDialog(results_);
        d_.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        labels_ = new ArrayList();
        d_.setSize(200, height * ants.size() + 50);
        d_.setTitle("Results");
        d_.setLayout(new GridLayout(ants.size(),1));
        List<Scores> results = new ArrayList();
        for(int n = 0; n < ants.size(); n++)
        {
           Ant a = ants.get(n);
           results.add(new Scores(a.getOwner(), a.getPoints()));
        }
        
        Collections.sort(results);
        Collections.reverse(results);
        
        for(int n = 0; n < results.size(); n++)
        {
            Scores s = results.get(n);
            labels_.add(new JLabel(s.getName() + "   " + s.getPoints()));
            d_.add(labels_.get(n));
        }
        
        d_.setVisible(true);
    }
    
    public void updateScoreWindow()
    {
        List<Ant> ants = getObjects(Ant.class);
        List<Scores> results = new ArrayList();
        for(int n = 0; n < ants.size(); n++)
        {
           Ant a = ants.get(n);
           results.add(new Scores(a.getOwner(), a.getPoints()));
        }
        
        Collections.sort(results);
        Collections.reverse(results);
        
        for(int n = 0; n < results.size(); n++)
        {
            Scores s = results.get(n);
            JLabel target = labels_.get(n);
            target.setText(s.getName() + "   " + s.getPoints());
        }
        
        d_.setVisible(true);
    }
    
    public final void addObject(Actor a, int x, int y)
    {
        //ignore
    }
    
    public final void removeObject(Actor a)
    {
        //ignore
    }
    
    public final void removeObjects(List<Actor> l)
    {
        //ignore
    }
}
