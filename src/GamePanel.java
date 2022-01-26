import java.awt.*;
import javax.swing.JPanel;

import astar.Grid;
import astar.PathFinding;

import java.util.List;
import basic_components.Map;
import basic_components.Vector2D;
import controls.KeyHandler;
import controls.MouseHandler;



public class GamePanel extends JPanel implements Runnable {
    final int ORIGINAL_TILE_SIZE = 16;// 16x16 px
    final int SCALE = 4;
    final int TILESIZE = ORIGINAL_TILE_SIZE * SCALE; //48x48 px
    final int MAX_SCREEN_COL =16;
    final int MAX_SCREEN_ROW =9;
    final int SCREEN_WIDTH = TILESIZE * MAX_SCREEN_COL;//total X screen size
    final int SCREEN_HEIGHT = TILESIZE * MAX_SCREEN_ROW;// total y screen size
    final int FRAMES_PRES_SECOND = 10; 

    
    Thread gameThread;

    //set player1 position
    Vector2D player1= new Vector2D(1,1);
    Vector2D player1LastPosition = new Vector2D();
    Vector2D mause= new Vector2D(3,8);
    Vector2D lastmause= new Vector2D(3,8);
    KeyHandler keyH = new KeyHandler();
    MouseHandler MouseH = new MouseHandler();
    Grid gameGrid= new Grid(MAX_SCREEN_COL, MAX_SCREEN_ROW, Map.mapBin);
    List<Vector2D> path;
    int playeronpath=0;
    


    //init gamepanel
    public GamePanel(){
        this.addMouseListener(MouseH);
        this.addKeyListener(keyH);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        Map.TranslateBinary();
 
    }

    //starting game thread
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        
    }

    //gameloop
    @Override
    public void run() {

        //time delay
        double drawInterval =1000000000/FRAMES_PRES_SECOND;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            
            currentTime= System.nanoTime();
            delta+=(currentTime- lastTime)/drawInterval;
            lastTime = currentTime;
            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    //game info update
    public void update(){

        mause.x=MouseHandler.lastMauseX/TILESIZE;
        mause.y=MouseHandler.lastMauseY/TILESIZE;



        if (mause.x!=lastmause.x||mause.y!=lastmause.y){
            path=null;
            lastmause.x=mause.x;
            lastmause.y=mause.y;
        }else{
            if(mause.x==player1.x&&player1.y==mause.y){
                playeronpath=0;
            }else{
                player1.x=path.get(playeronpath).x;
                player1.y=path.get(playeronpath).y;
                playeronpath++;
            }
        }
        if(path==null){
            path = PathFinding.findPath(gameGrid, player1, mause, true);
            for (Vector2D point : path) System.out.println(point);
            System.out.println();
        }
        //System.out.println("Mouse x="+mauseSelected.x+" Mouse y="+mauseSelected.y);
        /*
        if(player1.x>mause.x){
            player1.x--;
        }
        if(player1.x<mause.x){
            player1.x++; 
        }
        if(player1.y>mause.y){
            player1.y--; 
        }
        if(player1.y<mause.y){
            player1.y++;
        }
        //*/
        
        
        /*
        //keyboard control
        if(KeyHandler.upPressed==true){
            player1.y--;
        }else if(KeyHandler.downPressed==true){
            player1.y++;
        }else if(KeyHandler.leftPressed==true){
            player1.x--;
        }else if(KeyHandler.rightPressed==true){
            player1.x++;
        } 
         
        //wallblock
        if(player1.x>=MAX_SCREEN_COL){
            player1.x--;
        }
        if(player1.x<0){
            player1.x++; 
        }
        if(player1.y>=MAX_SCREEN_ROW){
            player1.y--; 
        }
        if(player1.y<0){
            player1.y++;
        }
        
        if(Map.mapBin[player1.x][player1.y]){
            player1LastPosition.x=player1.x;
            player1LastPosition.y=player1.y;
            
        }else{
            player1.x=player1LastPosition.x;
            player1.y=player1LastPosition.y;
        }
        */     
    }
    //paint components
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        //mapa
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {
                if(Map.mapBin[j][i]==true){
                    g2D.setColor(Color.black);
                    g2D.fillRect(j*TILESIZE, i*TILESIZE, TILESIZE, TILESIZE);
                }
                else{
                    g2D.setColor(Color.red);
                    g2D.fillRect(j*TILESIZE, i*TILESIZE, TILESIZE, TILESIZE);
                }
            }
        }
        //board
        g2D.setColor(Color.DARK_GRAY);
        for (int i = 0; i < SCREEN_WIDTH/TILESIZE; i++) {
            g.drawLine(i*TILESIZE, 0, i*TILESIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*TILESIZE, SCREEN_WIDTH, i*TILESIZE);
        }
        //ścieżka
        g2D.setColor(Color.darkGray);
        if(player1.x!=mause.x||player1.y!=mause.y){
            if(path!=null){
                for (Vector2D point : path){
                    g2D.fillRect(point.x*TILESIZE, point.y*TILESIZE, TILESIZE, TILESIZE);
                }
            }

        }

        //player
        g2D.setColor(Color.green);
        g2D.fillRect(player1.x*TILESIZE, player1.y*TILESIZE, TILESIZE, TILESIZE);

        //cursor
        if ((player1.x!=mause.x)||(player1.y!=mause.y)){
            g2D.setColor(Color.CYAN);
            g2D.fillRect(mause.x*TILESIZE, mause.y*TILESIZE, TILESIZE, TILESIZE);
        }
        g2D.dispose();
    }  

}
