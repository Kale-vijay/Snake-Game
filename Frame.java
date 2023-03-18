import javax.swing.*;
public class Frame extends JFrame {
    Frame()
    {
        //adding the pannel to frame
        this.add(new Panel());
        //naming the frames window
        this.setTitle("Snake Game");
        //make sure uniform(constant)frame size
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }
}
