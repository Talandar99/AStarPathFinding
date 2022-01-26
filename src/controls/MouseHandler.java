package controls;
import java.awt.event.*;
public class MouseHandler implements MouseListener {

    public static int lastMauseX;
    public static int lastMauseY;

    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastMauseX=e.getX();
        lastMauseY=e.getY();
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

}