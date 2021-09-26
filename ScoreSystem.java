import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ScoreSystem extends JDialog implements ActionListener {

    public ScoreSystem() {
        super();
    }

    public void ScoreGui()// the ScoreGui Method displays the scores in order from lowest to highest.
    {
        Container cp = getContentPane();
        JButton ok = new JButton("OK");
        ok.setActionCommand("OK");
        ok.addActionListener(this);
        cp.add(ok, BorderLayout.SOUTH);
        try {
            String line = "";
            ArrayList<String> myScoreList = new ArrayList<>();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("scores.txt")));
            while ((line = br1.readLine()) != null) {
                if (line != "") {
                    myScoreList.add(line);
                }
            }
            if (myScoreList.size() != 0) {
                JScrollPane jScrollPane = new JScrollPane();
                JPanel scorePanel = new JPanel();
                jScrollPane.setViewportView(scorePanel);
                scorePanel.setLayout(new GridLayout(myScoreList.size(), myScoreList.size()));
                for (int i = 0; i < myScoreList.size(); i++) {
                    mainLabel = new JLabel(myScoreList.get(i), JLabel.LEFT);// display the score on the screen
                    scorePanel.add(mainLabel);
                } // end for loop
                cp.add(jScrollPane);
                pack();
                setVisible(true);
            } else {
                notificateUser.notificationAlert("New player", "No score, new player found.");
            }
        } // end try
        catch (IOException ex) {
            notificateUser.notificationAlert("Alert", "Problem with scores.txt file.  Cant load high Scores");
        } // end catch
    }// end constructor

    public void actionPerformed(ActionEvent e) {
        dispose();
    }

    public void addHighScore(String name, int min, int sec, int level) {
        try {
            String outData = "PlayerName: " + name + " Total Time for Levels:" + min + ":" + sec + "(Minutes:Seconds)"
                    + "Level Reached:*" + level;
            PrintWriter out = new PrintWriter(new FileOutputStream("scores.txt", true));
            out.println("");
            out.println(outData);
            out.close();
        } // prints the highscore data to scores.txt
        catch (Exception ex) {
            System.out.println(ex);
        } // end catch

    }// end addHighScore

    private JLabel mainLabel;

    notificationSystem notificateUser = new notificationSystem();
}// end class