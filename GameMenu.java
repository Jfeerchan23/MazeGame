import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FilenameFilter;
import javax.swing.filechooser.*;
public class GameMenu extends JFrame implements ActionListener
{

    public static void main(String[] args)
    {
        GameMenu game = new GameMenu();
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public GameMenu()
    {
        super("Maze, a game of wondering"); //call super to initilize title bar of G.U.I.
        cp=getContentPane();
        shagLabel = new JLabel("",new ImageIcon("yeababyyea.jpg"),JLabel.LEFT);//GUI background for initial load
        cp.add(shagLabel);
        //Add Exit & New Game Menu Items
        itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, KeyEvent.CTRL_MASK));//press CTRL+X to exit if you want
        itemSaveScore = new JMenuItem("Save High Score");
        itemSaveScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S, KeyEvent.CTRL_MASK));//press CTRL+S to save high score if you want
        itemHighScore=new JMenuItem("High Score");
        itemHighScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, KeyEvent.CTRL_MASK));//press CTRL+H to view high score if you want
        newGameItem = new JMenuItem("New Game");
        openFileItem = new JMenuItem("Open Maze File.");
        openFileItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_O, KeyEvent.CTRL_MASK));//press CTRL+O to open a level if you want
        newGameItem.setActionCommand("New Game");
        newGameItem.addActionListener(this);
        itemSaveScore.setActionCommand("SaveScore");
        itemSaveScore.addActionListener(this);
        itemHighScore.setActionCommand("HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        openFileItem.setActionCommand("Open");
        openFileItem.addActionListener(this);
        newMenu = new JMenu("Options");
        newMenu.add(newGameItem);
        newMenu.add(openFileItem);
        newMenu.add(itemHighScore);
        newMenu.add(itemSaveScore);
        newMenu.add(itemExit);
        
        //Add Exit Menu Item
        //Add Menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);
        //Add Menu Bar     
        newPanel = new JPanel();
        hs = new ScoreSystem();
        tk=new TimeSystem();
        pack();
        setVisible (true);//show our menu bar and shagLabel.. Yea baby Yea! Whoa.. to much java.
    }//end constructor
     
    private class MyKeyHandler extends KeyAdapter //captures arrow keys movement
    {
        public void keyPressed (KeyEvent theEvent)
       {         
           switch (theEvent.getKeyCode())
           {
               case KeyEvent.VK_UP:
               {
                 control.playerMove(-1,0,scrapMatrix,control.dimondCount());//let the Architect know we moved, along with the current matrix
                 break;
              }
              case KeyEvent.VK_DOWN:
              {
                 control.playerMove(1,0,scrapMatrix,control.dimondCount());//see above
                 break;
             }
             case KeyEvent.VK_LEFT:
             {
                control.playerMove(0,-1,scrapMatrix,control.dimondCount());//see above
                break;
             }
             case KeyEvent.VK_RIGHT:
             { 
                control.playerMove(0,1,scrapMatrix,control.dimondCount()); //see above
                break;   
             }
           }//end switch
           loadMatrixGui("updateLoad");//reload the gui to show the move
            if (control.getLevel()==true)
            {
                nextLevelLoad();//if the player hit an exit door, load the next level
                }
           JLabel mainLabel=new JLabel("Total Dimonds Left to Collect"+control.getDimondsLeft(), JLabel.CENTER);//show how many dimonds are left to collect on the gui!
           JPanel dimondsPanel = new JPanel();
           dimondsPanel.add(mainLabel);
           cp.add(dimondsPanel,BorderLayout.SOUTH);
       }//end method
   }//end inner class
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Exit"))//exit on the menu bar
        {
             new Timer(1000, updateCursorAction).stop();
             System.exit(0); //exit the system.   
        }
        else if (e.getActionCommand().equals("New Game"))//new game on the menu bar
        {
            requestPlayerName();
            if(playerName != null){
                control.loadFile("level1.maz");
                control.setExit(control.ExitXCord(),control.ExitYCord());
                loadMatrixGui("newLoad");
            }
        }//end New Game Command
        else if(e.getActionCommand().equals("HighScore"))//Displays the high scores
        {
            ScoreSystem sg = new ScoreSystem();
            sg.ScoreGui();   
        }
        else if(e.getActionCommand().equals("SaveScore"))//allows the user to save their score at any time.
        {
            if(playerName !=null){
                hs.addHighScore(playerName,tk.getMinutes(),tk.getSeconds(),levelNum);
            }else{
                notificateUser.notificationAlert("Alert", "Start a game before save a score");
            }
        }
        else if(e.getActionCommand().equals("Open"))//to start the game you have to open a maze file. this is on the menu
        {
            JFileChooser chooser = new JFileChooser(".");
            FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("Maze Map", "maz");
            chooser.setFileFilter(mapFilter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                control.loadFile(chooser.getSelectedFile().getName());//load the file we need
                control.setExit(control.ExitXCord(),control.ExitYCord());
                if(playerName==null){
                    requestPlayerName();
                }
                if(playerName==null){
                    loadMatrixGui("newLoad");
                } 
            }
         }
     }//end actionPerformed method
     
     public void requestPlayerName(){
        optionPane = new JOptionPane();
        playerName=optionPane.showInputDialog("Please Enter your Earth Name");    
     }

     public void loadMatrixGui(String event)
     {
        if (event == "newLoad")
         {   
             newLoad();
        }//end if
        else if(event =="updateLoad")//every time the player moves the gui must be updated.
        {
            updateLoad();
        }
          for (int i = 0; i < labelMatrix.length; i++){
              for (int j = 0; j < labelMatrix[i].length; j++){
                  labelMatrix[i][j]= new mazeObject(scrapMatrix[i][j]);//add our maze images into the gui
              }}//end double for loop
         cp.add(newPanel);
         remove(shagLabel);//remove the constructors initial background
         System.gc();//force java to clean up memory use.
         pack();
         setVisible (true);
         newPanel.grabFocus();  
     }//end loadMatrixGui method
 
    public class mazeObject extends JLabel//inner class for each maze object, aka wall, player etc
    {
    public mazeObject(String fileName)
        {
            fileName+=".png";
            JLabel fancyLabel= new JLabel("",new ImageIcon(fileName),JLabel.LEFT);
            newPanel.add(fancyLabel);
        }
    }//end inner class
        
    public void nextLevelLoad()
    {
        levelNum+=1;
        tk.TimeKeeper(timeLeft,ix);//The TimeKeeper object keeps a running tab of the total time the player has used.(for high score)
        timely.stop();//dont count while we are loading the next level.
        control = new Matrix();//controlush everything from TheArchitect so we dont get goffee results
        catFileName+=01;//the next file to be loaded (number)
        String fileName="level"+catFileName+".maz";
        System.gc();
        control.loadFile(fileName);//load the file we need
        scrapMatrix=control.getGameMatrix();//get the new matrix from the fileloader for the next level.
        control.setExit(control.ExitXCord(),control.ExitYCord());
        loadMatrixGui("newLoad");         
    }
 
     Action updateCursorAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e)
    {
        ix-=1;
        jx+=1;
        if(ix<0)
        {
            ix=60;
            timeLeft-=1;
        }
    if(timeLeft==0 && ix==0)
    {
        timely.stop();
        JLabel yousuckLabel = new JLabel("",new ImageIcon("yousuck.jpg"),JLabel.LEFT);
        cp.add(yousuckLabel);
        remove(newPanel);
        remove(progBarPanel);
        pack();
        setVisible (true);
        timely.stop();
        catFileName-=01;
    if(catFileName<01){
        //the game is over, here we must tell our high score method to recond the details.
        hs.addHighScore(playerName,tk.getMinutes(),tk.getSeconds(),levelNum);
        notificateUser.notificationAlert("Warning", "You Stupid Ass, Did you eat to much for dinner?  Move Faster!");
    }else
        loadMatrixGui("newLoad");
    }//end first if
        progressBar.setValue(jx);
        progressBar.setString(timeLeft+":"+ix);
    }//end actionPerformed
};//end class

public void activateTimer(){
    timeCalc = new TimeSystem();//create the time calculator used to determine how much time each level is given.
    timeCalc.calcTimeforMaze(control.dimondCount(),control.getMatrixSizeRow(),control.getMatrixSizeColumn());//let time calculator know the parameters of the game 
    timeLeft=timeCalc.getMinutes();//get the minutes allowed for the level
    ix=timeCalc.getSeconds();//get the seconds allowed for the level;
    jx=0;//reset the variable used for keeping time to zero since its a new level
    timely = new Timer(1000,updateCursorAction);//create a timer to update the progress bar
    timely.start();//start the timer
    progBarPanel = new JPanel();//panel for progress bar
    progressBar = new JProgressBar(0, timeCalc.getMinutes()*100);//minutes returns a single digit, we have to multiply it for Bar.
    progressBar.setStringPainted(true);
    progBarPanel.add(progressBar);
    cp.add(progBarPanel,BorderLayout.NORTH);        

}
    
public void newLoad(){
    
    remove(newPanel);//remove the previous level's game from the screen
    if(progBarPanel !=null)//remove the progress bar from the gui as long as its already been created.
    remove(progBarPanel);
    String[][] temp = control.getGameMatrix();
    scrapMatrix = new String[control.getMatrixSizeRow()][control.getMatrixSizeColumn()];   
    for (int i = 0; i < scrapMatrix.length; i++){
       for (int j = 0; j < scrapMatrix[i].length; j++){
           scrapMatrix[i][j]= temp[i][j];//create a new matrix so we dont have a refrence to another objects matrix!
     }}//end double for loop
   activateTimer();
    newPanel = new JPanel();
    newPanel.setLayout(new GridLayout(control.getMatrixSizeRow(),control.getMatrixSizeColumn()));//set our panel for the game to the size of the matrix      
    labelMatrix=new JLabel[control.getMatrixSizeRow()][control.getMatrixSizeColumn()];
    newPanel.addKeyListener( new MyKeyHandler() );
}

public void updateLoad(){
    scrapMatrix = control.getUpdatedMatrix();//get the new matrix to be displayed from the architect
    remove(newPanel);//remove the old game
    newPanel = new JPanel();
    newPanel.setLayout(new GridLayout(control.getMatrixSizeRow(),control.getMatrixSizeColumn()));
    newPanel.addKeyListener( new MyKeyHandler() );
    newPanel.grabFocus();  
}
private ScoreSystem hs;  
private int catFileName=01;
private Container cp;
//create menu items
private JMenuBar menuBar;
private JMenu newMenu;
private JMenuItem itemExit;
private JMenuItem newGameItem;
private JMenuItem openFileItem;
private JMenuItem itemHighScore;
private JMenuItem itemSaveScore;
//end create menu items
private JLabel shagLabel;
private int ix;
private int jx;
private int timeLeft;
private JPanel progBarPanel;
private JLabel[][] labelMatrix;
private TimeSystem timeCalc;
private JProgressBar progressBar;
private JPanel newPanel;// = new JPanel();
private Matrix control = new Matrix();
private String[][] scrapMatrix; 
private Timer timely; 
private TimeSystem tk;
private  String playerName;
private int levelNum=1;

notificationSystem notificateUser = new notificationSystem();
JOptionPane optionPane;
}//end class    