import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class notificationSystem {
    public notificationSystem() {
    }

    public void notificationAlert(String notificationTitle, String notificationMessage){
        JFrame frame = new JFrame(notificationTitle);
        JOptionPane.showMessageDialog(frame, notificationMessage);
    }
}
