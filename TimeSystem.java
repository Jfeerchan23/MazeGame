public class TimeSystem
{
    public void TimeKeeper(int min, int sec)//a class to keep track of the total seconds and minuntes the player has used to get to a level
	{ 
	   if(sec + seconds <=60)
	    {
	        minutes+=min;
	        seconds=sec+seconds;
    	}
	   else
	   {
	       minutes+=min;
	       minutes+=1*((sec+seconds)/60);
	       seconds=(sec+seconds)%60;
	   }
	}//end TimeKeeper
	

    public void calcTimeforMaze(int totalDimonds, int xSize, int ySize)//some kinda method that determines the time a player has for each level based on the level size and dimonds.
    {
        if(xSize/ySize < 1)//this method should be changed in order to provide a more relistic time system.
        {
            minutes+=(ySize/xSize)*1+1;
        }
        else
            minutes+=(ySize/xSize)*1+1;
        if(totalDimonds >6 && totalDimonds*.10 + seconds <= 60)
            minutes+=(ySize/xSize)*1+1;
        else
        {
            minutes+=1;          
        }
        if(minutes ==0)
           minutes=2;
     }//end method
    

	public int getMinutes()
	{
	    return minutes;
	}
	
	public int getSeconds()
	{
	    return seconds;
	}
	
int minutes=0; 
int seconds=0;
}//end class
