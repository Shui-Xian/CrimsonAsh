import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class crimson extends JPanel implements MouseListener, ActionListener, KeyListener, Runnable {
    Image mother,deadMom, menu, background, background2, background3, background4;
    static JFrame myFrame;
    Image [] mcRight = new Image [3];
    Image [] mcLeft = new Image [3];
    Image [] villain = new Image[2];
    Image [] text = new Image[4];
    Image [] skills = new Image [4];
    Image []slime = new Image [2];
    boolean airborne = true;
    boolean villainDirection = true;
    boolean jump, left, right;
    double jumpSpeed = 6;
    double yVel = 0, xVel = 0;
    int mcY = 250;
    int mcX = 10;
    Thread thread;
    int villainNo = 0;
    double gravity = 0.2;
    int screenHeight = 500, screenWidth = 600;
    int FPS = 60;
    int stage = 1;
    double speed = 4;
    int spriteno = 0;
    Timer timer;  // Controls the animation speed
    Boolean walking = false;
    Image [] currentDirection = mcRight;
    int ghostx = 550;
    int ghosty = 450;
    int skill;
    boolean action;
    int fireX;
    int slimeHp = 2;
    int alive = 0;
    int slimex = 0;
    int slimeframelocation = 326;
    int slimedirection = 50;

    Clip backgroundMusic, dead,  water, fire, lightning, wind;




    public crimson() {
        initialize();

        mcRight [0] = new ImageIcon("MCStandBy1.png").getImage();
        mcRight [1] = new ImageIcon("MCRun3.png").getImage();
        mcRight [2] = new ImageIcon("MCRun4.png").getImage();

        mcLeft [0] = new ImageIcon("MCStandBy2.png").getImage();
        mcLeft [1] = new ImageIcon("MCRun1.png").getImage();
        mcLeft [2] = new ImageIcon("MCRun2.png").getImage();

        villain [0] = new ImageIcon ("evilMeLeft.png").getImage();
        villain [1] = new ImageIcon ("evilMeRight.png").getImage();

        text[0] = new ImageIcon ("mom!.png").getImage();
        text[1] = new ImageIcon ("morn...png").getImage();
        text[2] = new ImageIcon ("MOMMY.png").getImage();
        text[3] = new ImageIcon ("revenge.png").getImage();

        skills [0] = new ImageIcon ("fire.png").getImage();
        skills [1] = new ImageIcon ("water.png").getImage();
        skills [2] = new ImageIcon ("lightning.png").getImage();
        skills [3] = new ImageIcon ("wind.png").getImage();

        slime [0] = new ImageIcon ("fireSlime.png").getImage();
        slime [1] = new ImageIcon ("deadFireSlime.png").getImage();

        deadMom = new ImageIcon ("deadMom.png").getImage();

        mother = new ImageIcon ("mother.png").getImage();

        menu = new ImageIcon ("skills.png").getImage();

        setPreferredSize(new Dimension(600, 600)); // width, height
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);

        thread = new Thread(this);
        thread.start();

        timer = new Timer(100, this);
        timer.start();
        //backgroundMusic, dead,  water, goofyAhhMusic;
        try {

            AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("background.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(sound);
            sound = AudioSystem.getAudioInputStream(new File ("deadSound.wav"));
            dead = AudioSystem.getClip();
            dead.open(sound);
            sound = AudioSystem.getAudioInputStream(new File ("spell.wav"));
            water = AudioSystem.getClip();
            water.open(sound);
            sound = AudioSystem.getAudioInputStream(new File ("fire.wav"));
            fire = AudioSystem.getClip();
            fire.open(sound);
            sound = AudioSystem.getAudioInputStream(new File ("wind.wav"));
            wind = AudioSystem.getClip();
            wind.open(sound);
            sound = AudioSystem.getAudioInputStream(new File ("lightning.wav"));
            lightning = AudioSystem.getClip();
            lightning.open(sound);




        }



        catch (Exception e) {

        }

        myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            //close window
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        backgroundMusic.setFramePosition (0); //<-- play sound file again from beginning
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void paintComponent(Graphics g) { // DO NOT MODIFY THIS LINE
        super.paintComponent(g);
        System.out.println(mcX);
        //System.out.println(stage);
//      if (background != null) {
        if (stage == 1){
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX,mcY,null);
        }
        else if (stage == 2){
            g.drawImage(background2, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX,mcY,null);
        }else if (stage == 3) {
            g.drawImage(background3, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX, mcY, null);
            g.drawImage(mother, 430, 330, null);
        }else if (stage == 4) {
            g.drawImage(background3, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX, mcY, null);
            g.drawImage(mother, 430, 330, null);
            g.drawImage(text[0], 0, -10, null);
        }else if (stage == 5) {
            g.drawImage(background3, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX, mcY, null);
            g.drawImage(mother, 430, 330, null);
            g.drawImage(text[1], 0, 0, null);
        }else if (stage == 6){
            g.drawImage(background3, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX, mcY, null);
            g.drawImage(deadMom, 400, 340, null);
            g.drawImage(villain[villainNo], ghostx, ghosty, null);
        }else if (stage == 7) {
            g.drawImage(background3, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX, mcY, null);
            g.drawImage(deadMom, 400, 340, null);
            g.drawImage(text[2], 20, -200, null);
        }else if (stage == 8) {
            g.drawImage(background3, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX, mcY, null);
            g.drawImage(deadMom, 400, 340, null);
            g.drawImage(text[3], 20, -200, null);
        }else if (stage == 9) {
            g.drawImage (deadMom, 200,200, null);
        }else if (stage == 10){
            g.drawImage(background4, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX,mcY,null);
            g.drawImage (menu, 0 ,0, null);
            if (action)
                if (skill == 0 || skill == 1) {
                    g.drawImage(skills[skill], fireX, 390, null);

                }else{
                    g.drawImage (skills[skill], mcX+100, 300, null);

                }            //add the qwer effects
        }else if (stage == 11){
            g.drawImage(background4, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX,mcY,null);
            g.drawImage (menu, 0 ,0, null);
            g.drawImage(slime[alive], slimex,0,null);

            if (action)
                if (skill == 0 || skill == 1) {
                    g.drawImage(skills[skill], fireX, 400, null);

                }else{
                    fireX = mcX + 100;
                    g.drawImage (skills[skill], fireX, 300, null);

                }
        }
        else if (stage == 12){
            g.drawImage(background4, 0, 0, this.getWidth(), this.getHeight(), null);
            g.drawImage(currentDirection[spriteno], mcX,mcY,null);
            g.drawImage (menu, 0 ,0, null);
            g.drawImage(slime[alive], slimex,0,null);

            if (action)
                if (skill == 0 || skill == 1) {
                    g.drawImage(skills[skill], fireX, 400, null);

                }else{
                    fireX = mcX + 100;
                    g.drawImage (skills[skill], fireX, 300, null);

                }
        }
        else {
            System.out.println("Background is null");
        }
    }

    public void initialize() {
        try {
            background = ImageIO.read(new File("bedRoom.png"));
            background2 = ImageIO.read(new File("bedRoom2.png"));
            background3 = ImageIO.read(new File("livingRoom.png"));
            background4 = ImageIO.read(new File("outside.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    //runs the game, (no parameters)
    public void run() {
        initialize();

        while (true) {
            //main game loop
            move();

            keepInBound();
            this.repaint();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (walking == true) {
            spriteno = (spriteno + 1) % mcRight.length;
            repaint();
        }
        if (walking == false){
            spriteno = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(key);
        if (key == KeyEvent.VK_LEFT) {
            left = true;
            right = false;
            walking = true;
            currentDirection = mcLeft;
        } else if (key == KeyEvent.VK_RIGHT) {
                right = true;
                left = false;
                walking = true;
                currentDirection = mcRight;
        } else if (key == KeyEvent.VK_UP) {
            jump = true;
        }else if  (key == KeyEvent.VK_DOWN){
            if (stage >2 && stage < 10) {
                stage++;
            }
        }else if (key == KeyEvent.VK_Q){
            fire.setFramePosition(0); //<-- play sound file again from beginning
            fire.start();
            action = true;
            fireX = mcX + 100;
            skill =0;
        }
        else if (key == KeyEvent.VK_W){
            water.setFramePosition(0); //<-- play sound file again from beginning
            water.start();
            action = true;
            fireX = mcX + 100;
            skill =1;
        }
        else if (key == KeyEvent.VK_E){
            lightning.setFramePosition(0); //<-- play sound file again from beginning
            lightning.start();
            action = true;
            skill =2;
        }
        else if (key == KeyEvent.VK_R){
            wind.setFramePosition(0); //<-- play sound file again from beginning
            wind.start();
            action = true;
            skill =3;

        }
    }

    //gravity
    void move() {
        if (stage == 6){
        if (villainDirection && stage == 6) {
            ghostx += 8;
            ghosty-=1.7;// Move right
        } else if (stage == 6){
            dead.setFramePosition (0); //<-- play sound file again from beginning
            dead.start ();
            ghostx -= 8; // Move left
            ghosty-=1.7;
        }
        }

        if (action) {
            fireX +=8;
        }

        if(left)
            xVel = -speed;
        else if (right)
            xVel = speed;
        else
            xVel = 0;

        if(airborne) {
            yVel -= gravity;
        }else {
            if(jump) {
                airborne = true;
                yVel = jumpSpeed;
            }
        }

        mcX += xVel;
        mcY -= yVel;

    }

    void keepInBound() {
        if (stage == 11 && slimex >= 274){
            slimex = 274;
        }
        if (fireX == slimeframelocation && stage == 11){
            if (skill != 0) {
                slimeHp--;
            }
            if (skill == 3){
                slimeframelocation += 20;
                slimex += 100;
            }
            if (slimeHp <= 0){
                alive = 1;
            }
        }
        if (ghostx <= 0) {
            villainDirection = true;
            villainNo = 1;// Start moving right
        } else if (ghostx >= screenWidth - 50) { // Adjust for villain width
            villainDirection = false;
            villainNo = 0;// Start moving left
        }
        if(mcX < 0) {
            if (stage ==1 )
                mcX = 0;
            else if (stage == 3) {
                mcX = 0;
            }
            if (stage == 11){
                stage --;
                mcX =400;
                slimex = 0;
            }
            else if (stage > 1){
                mcX = 400;
                stage--;
            }
        }
        else if (stage == 2){
            if (mcX > 370 ){
                stage++;
                mcX = 10;
            }
        }
        else if(mcX > screenWidth - mcY/2) {
            //mcX = screenWidth - mcY / 2;
            stage++;
            mcX = 10;
            if (stage == 11){
                slimeHp=2;
                alive=0;
            }
            // Remember to collision check the wall for last panel
        }

        if(mcY < 0) {
            mcY = 0;
            yVel = 0;
        }else if(mcY > screenHeight - mcY/2 ) {
            mcY = screenHeight - mcY/2;
            airborne = false;
            yVel = 0;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
            walking = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
            walking = false;
        } else if (key == KeyEvent.VK_UP) {
            jump = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {

        myFrame = new JFrame("Crimson Ash");
        //Create a JPanel
        crimson myPanel = new crimson(); // called constructor

        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setVisible(true);

    }


    //self explanatory variables

}

