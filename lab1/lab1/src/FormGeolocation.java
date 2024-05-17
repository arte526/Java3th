
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormGeolocation extends JFrame {
    private JPanel mainPanel;
    private JLabel Text_one;
    private JTextField InputXX;
    private JTextField InputXY;
    private JLabel Text_two;
    private JTextField InputYX;
    private JTextField InputYY;
    private JButton Calculate;
    private JButton ClearData;
    private JTextField Result;

    public FormGeolocation (){
        JFrame Frame = new JFrame();
        Frame.setContentPane(mainPanel);
        Frame.setTitle("Distance Calculator");
        Frame.setSize(600,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Result.setEditable(false);
        Frame.setVisible(true);

        Frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(2);
            }
        });


        ClearData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputXY.setText("");
                InputXX.setText("");
                InputYY.setText("");
                InputYX.setText("");
                Result.setText("");
            }
        });
        Calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double lat1 = Double.parseDouble(InputXY.getText());
                    double lon1 = Double.parseDouble(InputXX.getText());
                    double lat2 = Double.parseDouble(InputYY.getText());
                    double lon2 = Double.parseDouble(InputYX.getText());
                    double R = 6371e3; // Earth radius in meters

                    double phi1 = Math.toRadians(lat1);
                    double phi2 = Math.toRadians(lat2);
                    double deltaPhi = Math.toRadians(lat2 - lat1);
                    double deltaLambda = Math.toRadians(lon2 - lon1);

                    double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                            Math.cos(phi1) * Math.cos(phi2) *
                                    Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

                    double distance = (R * c);

                    Result.setText(String.format("%.2f m", distance));
                }catch (NumberFormatException ex) { Result.setText("Invalid input");}}

        });
    }

    public static void main(String[] args) {
        FormGeolocation obj = new FormGeolocation();
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
