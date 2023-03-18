import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.*;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener {
    static int height=600;
    static int width=1200;
    static int unit=50;
    //to specify time in which new frame displayed
    Timer timer;
    //deciding food location randomly
    Random random;
    int foodx,foody,score,length=3;
    char dir='R';
    //to specify if the game has ended or not
    boolean flag=false;
    static int delay=200;
    int xsnake[]=new int[288];
    int ysnake[]=new int[288];
    Panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        //this command allows keyboard input to be processed
        this.setFocusable(true);
        random=new Random();
        //creating abstract class for input processing
        this.addKeyListener(new MyKey());
        gameStart();
    }
    public void gameStart(){
        sponFood();
        flag=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void sponFood()
    {
        foodx=random.nextInt(width/unit)*unit;
        foody=random.nextInt(height/unit)*unit;
//        for(int i=length-1;i>=0;i--)
//        {
//            if(ysnake[i]==foody&&xsnake[i]==foodx)
//            {
//                foodx=random.nextInt(width/unit)*unit;
//                foody=random.nextInt(height/unit)*unit;
//            }
//        }
    }
    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);

    }
    public void draw(Graphics graphic)
    {
        if(flag==true)
        {
            graphic.setColor(Color.red);
            graphic.fillOval(foodx,foody,unit,unit);
            for(int i=0;i<length;i++)
            {
                if(i==0)
                {
                    graphic.setColor(Color.ORANGE);
                }
                else
                {
                    graphic.setColor(Color.GREEN);
                }
                graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
            }
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics f=getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+score,(width-f.stringWidth("Score:"+score))/2,graphic.getFont().getSize());
        }
        else
        {
            gameOver(graphic);
        }
    }
    public void gameOver(Graphics graphic)
    {
        //to display the score
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f=getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+score,(width-f.stringWidth("Score:"+score))/2,graphic.getFont().getSize());

        //to display the gameOver text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics f2=getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over!",(width-f2.stringWidth("Game Over!"))/2,height/2);

        //to display the replay prompt
        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        graphic.drawString("press R to replay",(width-f.stringWidth("press R to replay"))/2,height/2+150);


    }
    public void checkHit()
    {
        if(xsnake[0]<0)
            flag=false;
        else if(xsnake[0]>width)
            flag=false;
        else if(ysnake[0]<0)
            flag=false;
        else if(ysnake[0]>height)
            flag=false;
        for(int i=length;i>0;i--)
        {
            if((xsnake[0]==xsnake[i])&&(ysnake[0]==ysnake[i]))
                flag=false;
        }
        if(!flag)
            timer.stop();
    }

    public void eat()
    {
        if((xsnake[0]==foodx)&&(ysnake[0]==foody))
        {
            length++;
            score++;
            sponFood();
        }
    }
    public void move()
    {
        //updating all body parts of snake except head
        for(int i=length;i>0;i--)
        {
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        switch (dir)
        {
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
        }
    }
    public class MyKey extends KeyAdapter
    {
        public void keyPressed(KeyEvent evt)
        {
            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if(dir!='D')
                        dir='U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U')
                        dir='D';
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir!='R')
                        dir='L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L')
                        dir='R';
                    break;
                case KeyEvent.VK_R:
                    if(!flag)
                    {
                        score=0;
                        length=3;
                        dir='R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        gameStart();
                    }
                    break;
            }
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        if(flag)
        {
            move();
            eat();
            checkHit();
        }
        repaint();


    }


}
