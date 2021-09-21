import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class ScoreSystem extends JDialog implements ActionListener
{

    public ScoreSystem()
    {
        super();
    }
    public void ScoreGui()//the ScoreGui Method displays the scores in order from lowest to highest.
    {
        Container cp = getContentPane();
        JButton ok = new JButton("OK");
        ok.setActionCommand("OK");
        ok.addActionListener(this);
        cp.add(ok,BorderLayout.SOUTH);
             try{
                    String line = "";
                    String[] myScoreArray = new String[100];
                    for(int i=0; i<myScoreArray.length;i++)
                        myScoreArray[i]=" ";
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("scores.txt")));//read in the scores data
                    int recordsCount=0;
                    while((line = br1.readLine()) != null) 
                    {
                        line = br1.readLine();
                        if(line!="")
                        {
                            recordsCount+=1;
                            int tempPOS = line.indexOf("*");//use the star to indicate the next charator is going to be the maze level which we will sort by.
                            String pos=line.substring(tempPOS+1);
                            int index = Integer.parseInt(pos);
                            if(myScoreArray[index] == " ")
                                myScoreArray[index]=line;//add in the score to the array.
                          else
                          {
                               for(int i=0; i<myScoreArray.length;i++)
                               {
                                   if(index+i<myScoreArray.length)//prevent array out of bounds errors.
                                   {
                                   if(myScoreArray[index+i].equals(" "))
                                   {
                                       myScoreArray[index+1]=line;//add in a score to the next available area of the array
                                   }
                                   }//end first if
                               }//end for loop
                           }//end else
                             JPanel scorePanel = new JPanel();
                             scorePanel.setLayout(new GridLayout(recordsCount,recordsCount));
                                 for(int i=0; i<myScoreArray.length;i++)
                                 {
                                     if(myScoreArray[i]!=" ")
                                     {
                                         mainLabel=new JLabel(myScoreArray[i], JLabel.LEFT);//display the score on the screen
                                         scorePanel.add(mainLabel);
                                     }
                                 }//end for loop
                                cp.add(scorePanel); 
                           }//end very first if
                     }//end first while loop            
                }//end try
                catch(IOException ex) {
                    notificateUser.notificationAlert("Alert", "Problem with scores.txt file.  Cant load high Scores");
                }//end catch
        pack();
        setVisible (true);
    }//end constructor
    
    public void actionPerformed(ActionEvent e)
    {
        dispose();
    }
    public void addHighScore(String name, int min, int sec,int level)
    {
        try{
               String outData="PlayerName: "+name+" Total Time for Levels:"+min+":"+sec+ "(Minutes:Seconds)"+ "Level Reached:*" + level;
               PrintWriter out = new PrintWriter(new FileOutputStream("scores.txt",true));
               out.println("");
               out.println(outData);
               out.close();
    }//prints the highscore data to scores.txt
        catch(Exception ex){
            System.out.println(ex);
       }//end catch
	    
    }//end addHighScore   


    
private JLabel mainLabel;

notificationSystem notificateUser = new notificationSystem();
}//end class