import java.io.*;

public class FileSystem
{
    public void readFile(String fileName)
    {  
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));            
            String x;
            int lineNum=0;
            while (( x = in.readLine()) != null) 
            { 
                Matrix.MatrixLoader(x,lineNum);//pass the Matrix loader method the line and the line number for parsing.
                lineNum++;//we will use the line number later in this class
            }
         }//end try
        catch (IOException e) 
        {  
            notificationSystem notificateUser = new notificationSystem();
            notificateUser.notificationAlert("Alert", "Ooops IOException error, i did it again!" + e.getMessage());
        }//end catch
     }//end load file method
     
    


    

}//end class