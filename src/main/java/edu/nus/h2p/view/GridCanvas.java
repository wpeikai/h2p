package edu.nus.h2p.view;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component("gridCanvas")
public class GridCanvas extends Canvas{

    @Value("0")
    private int x0;
    @Value("0")
    private int y0;
    @Value("${param.grid.divided}")
    private int x1;
    @Value("${param.grid.divided}")
    private int y1;

    private Graphics2D context;
    
    public void init(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    public void addNotify() {
        super.addNotify();
        context = (Graphics2D) this.getGraphics().create();
    } // End addNotify.

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, x1 - x0, y1 - y0);
    }
}
