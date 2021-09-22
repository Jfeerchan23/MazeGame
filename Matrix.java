import java.io.File;

import javax.swing.*;
//Your life is the sum of a remainder of an unbalanced equation inherent to the programming
//of the matrix

public class Matrix extends JFrame

{
   public void setExit(int x, int y)//records the location of the exit so we can show it when its time
   {
       WallXCord=x;
       WallYCord=y;  
   } 
   public void showWall()//used when its time to show the exit.  
   {
       updatedMatrix[WallXCord][WallYCord]="E";  
   }

    public void playerMove(int xScale, int yScale, String[][] currentMatrix,int totalDimonds)
    {
       int x=0;
       int y=0;
       globalTotalDimonds=totalDimonds; //use this later for the gui dimond count
       nextLevel(false); //dont go to the next level yet.
        for (int i = 0; i < currentMatrix.length; i++) //for loop will find were the player is now
        {
        for (int j = 0; j < currentMatrix[i].length; j++) 
        {
           if(currentMatrix[i][j].equals("P"))//we found the player
           {
            x=i;//record the players position
            y=j;
            break;
           }
        }}//end both for loops
            if(currentMatrix[x+xScale][y+yScale].equals("H"))//its a hidden dimond
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P";
                currentMatrix[x][y]="N";
                collected+=1;//we got a hidden dimond! wow!
            }
            else if(currentMatrix[x+xScale][y+yScale].equals("D"))//its a dimond
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P";
                collected+=1;//we got a dimond
            }
            else if(currentMatrix[x+xScale][y+yScale].equals("M") && currentMatrix[x+(xScale*2)][y+(yScale*2)].equals("N"))//move a moveable wall
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P"; 
                currentMatrix[x+(xScale*2)][y+(yScale*2)]="M";
            }
            else if (currentMatrix[x+xScale][y+yScale].equals("N"))//normal move foward onto nothing
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P"; 
            }
            else if (currentMatrix[x+xScale][y+yScale].equals("E"))//its an exit
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P"; 
                nextLevel(true);//allow the next level to be loaded.
            }
            else
               notificateUser.notificationAlert("Warning", "You Stupid Ass, Ran into something did you?");
                
            if(collected==totalDimonds)//if we have all the dimonds give the player the exit
            showWall();
               
            updatedMatrix=currentMatrix;  //we will return updatedMatrix for the gui                     
        }//end method

    public void nextLevel(boolean tOrF)//true we go to next level, false we update current level's gui 
    {
        level=tOrF;
    }
    
    public boolean getLevel()//returs level true or false
    {
        return level;
    }
        
    public int getDimondsLeft()
    {
        return globalTotalDimonds-collected;//for GUI JLabel, show how many dimonds are left to be collected
    }
    
    public String[][] getUpdatedMatrix()//returns the updated matrix for the gui to display
    {
        return updatedMatrix;    
    }
    
    static public void MatrixLoader(String fileTextLine, int lineNum)
    {
       // exitCount=0;//we must reset our variables to zero for the next level.              
       
        int sum=0;
        char textVar;
        if(lineNum == 0)//it is the first line of the maze file, create The Matrix based on first line of the maze file
        { 
            for(int i=0; i<fileTextLine.length();i++)
            {
                if(fileTextLine.charAt(i) ==' ')//find blank area on first line number
                sum+=1;//how many blank spaces between the size of the matrix aka 4 6 or 5  7
            } 
            int locationOfSpace = fileTextLine.indexOf(" ");//still handling that possible blank space in the matrix size in the file
            String c1=fileTextLine.substring(0,locationOfSpace);//see above
            String r1=fileTextLine.substring(locationOfSpace+sum,fileTextLine.length());//see above
            column = Integer.parseInt(c1);
            row = Integer.parseInt(r1);
            GameMatrix=new String[row][column];//create new matrix based on the size from the file       
         }//end if 
         else
            for(int i=0; i< fileTextLine.length();i++)//it is not the first line of the maze file
            {
                textVar = fileTextLine.charAt(i); //grab the individual charaters from the string.
                if(textVar == '.')//change . to N, so we dont have any goofy file system problems
                   textVar='N';
                String textVar1= "" + textVar;
                if(textVar == 'E')//log the position of the exit for later use
                {
                 
                    exitXCord = lineNum-1;
                    exitYCord =i;
                   // textVar='W';
                    textVar1= "" + textVar;//turn the exit into a wall
                }
                     GameMatrix[lineNum-1][i]=textVar1; //load the matrix with values, aka N,W, D, H, etc
              }//end for loop
              
       
    }//end matrixloader method
    
    public String[][] getGameMatrix()
    { int exitCount=0;
        int i1=0;
        int j1=0;
         //  playerCount=0;//we must reset our variables to zero for the next level.
         //before we will return the matrix we will quick do some error checking
                      int playerCount=0;
       for (int i = 0; i < GameMatrix.length; i++) {
           for (int j = 0; j < GameMatrix[i].length; j++) {
               if(GameMatrix[i][j].equals("P"))
               {                   playerCount+=1;
                 
               }
               else if(GameMatrix[i][j].equals("E"))
               {
                 exitCount+=1;   
                 i1=i;
                 j1=j;
               }
    System.out.println(playerCount + "playerCount");
       System.out.println(exitCount + "playerCount");

         }}//end double for loop
            if(playerCount >1 || exitCount>1)
            {
               notificationSystem notificateUser = new notificationSystem();
                notificateUser.notificationAlert("Alert", "Your maze file ether had more than one player, or more than one exit.");
            }
            else
            GameMatrix[i1][j1]="W";
            

        
        return GameMatrix;
    }//end getGameMatrix method
    
    public int getMatrixSizeColumn()//return the matrixsize-column
    {
        return column;
    }
    
    public int getMatrixSizeRow()//return the matrix size-row
    {
        return row;
        
    }
    
  public int ExitXCord() //return the X cordinates for the Exit
  {
     return exitXCord;
  }
  
  public int ExitYCord()//return the Y cordinates for the Exit
  {
     return exitYCord; 
  }
  
  public int dimondCount()
  {
      int totalDimonds=0;
       for (int i = 0; i < GameMatrix.length; i++){
           for(int j = 0; j < GameMatrix[i].length; j++){
           if(GameMatrix[i][j].equals("D") || GameMatrix[i][j].equals("H"))
               totalDimonds+=1;
       }}//end double for loop
    return totalDimonds;//return the total number of dimonds in the level
   }
   
   public void loadFile(String fileName){
   
    fl.readFile(fileName);

   }


FileSystem fl = new FileSystem();
int foundPlayer=0;
String[][] updatedMatrix;
int WallXCord;
int WallYCord;
int collected=0;
boolean level;
int globalTotalDimonds=0;


private static int exitXCord=0;
private static int exitYCord=0;
private static String[][] GameMatrix;
private static int column;
private static int row;

notificationSystem notificateUser = new notificationSystem();
}//end class
