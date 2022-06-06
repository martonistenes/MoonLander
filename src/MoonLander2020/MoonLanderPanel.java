package MoonLander2020;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.round;


/**
 * A MoonLander osztály megjelenítésért felelős osztály.
 * @author Istenes Márton
 */
public class MoonLanderPanel extends JPanel {

    /**
     * A panelhez tartozó játék.
     */
    private final MoonLander game;

    /**
     * A panelhez hossza.
     */
    private int panel_width;

    /**
     * A panel magassága.
     */
    private int panel_height;

    /**
     * A panel háttérképe.
     */
    protected Image background = Toolkit.getDefaultToolkit().getImage("background.png");

    /**
     * A holdkkomp "tüze".
     */
    Image fire = Toolkit.getDefaultToolkit().getImage("fire.png");

    /**
     * A holdkomp.
     */
    Image holdkomp = Toolkit.getDefaultToolkit().getImage("lunarmodule.png");

    /**
     * Holdkomp felrobbanása.
     */
    Image explosion = Toolkit.getDefaultToolkit().getImage("explosion.png");

    /**
     * Incializálja a panelt.
     * @param m A panelhez tartozó játék
     */
    public MoonLanderPanel(MoonLander m){

        game = m;
        panel_width =  (int) m.getMenu().getMpanel().getComboBox().getSelectedItem();
        panel_height=2*panel_width/3;
        setPreferredSize(new Dimension(panel_width,panel_height));

    }

    /**
     * Kirajzolja a megjelenítendő dolgokat:
     * <ul>
     * <li>Háttérkép.</li>
     * <li>Terep.</li>
     * <li>Egy "X" a leszállóhely megjelölésére.</li>
     * <li>Repülési adatok.</li>
     * <li>Holdkomp.</li>
     * <li>Felfele gyorsítás elején a főhajtómű tüzét.</li>
     * <li>Holdkomp.</li>
     * </ul>
     * Ha a játék vége van, Kirajzolja:
     * <ul>
     *     <li>Nyereség esetén a Game Win feliratot.</li>
     *     <li>Vereség esetén a Game Over feliratot, és egy robbanást a holdkomp köré. </li>
     * </ul>
     * A kontakfény:
     * <ul>
     * <li>Ha a magasság kissebb, mint 10m zöld fénnyel.</li>
     * <li>Ha a magasság nagyobb, mint 10m piros fénnyel.</li>
     * </ul>
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //háttér
        g.drawImage(background, 0, 0, panel_width, panel_height,this);

        //hegyek
        g.setColor(Color.white);
        for(int i = 0; i < game.getTerrain().getCoordinates().size(); i++){
            g.drawLine(game.getTerrain().getCoordinates().get(i).x,game.getTerrain().getCoordinates().get(i).y,game.getTerrain().getCoordinates().get(i).x,panel_height);
        }

        //leszállóhely
        g.setColor(Color.black);
        g.setFont(new Font("Monospaced",Font.BOLD,20));
        g.drawString("X",game.getTerrain().getSik_x()+(game.getTerrain().getSik_hossz()/2),game.getTerrain().getSik_y()+20);

        //control panel data
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced",Font.BOLD,15));
        g.drawString("Altitude: "+game.getEagle().getAltitude()+" m", panel_width-235, 30);
        g.drawString("Vertical speed: "+ (round(game.getEagle().getVely()*-100*100.00)/100.00)+" m/s", panel_width-235, 45);
        g.drawString("Horizontal speed: "+ (round(game.getEagle().getVelx()*100*100.00)/100.00)+" m/s", panel_width-235, 60);
        g.drawString("Remaining Fuel: "+game.getEagle().getFuel()+" l", panel_width-235, 75);

        //holdkomp
        g.drawImage(holdkomp, game.getEagle().getX(), game.getEagle().getY(),panel_width/60,panel_width/60, this);

        //tűz
        if (game.isBoost()==true && !game.isGameOver()){
                g.drawImage(fire, game.getEagle().getX(), game.getEagle().getY()+panel_width/60,panel_width/60,panel_width/60,this);

        }

        //game over
        if(game.isGameOver() ) if (!game.isWin()) {
            g.setFont(new Font("Monospaced", Font.BOLD, panel_width/15));
            g.drawString("Game over!", (panel_width / 2) - panel_width/5, panel_height/2);
            g.drawImage(explosion, game.getEagle().getX()-5, game.getEagle().getY()-5,panel_width/40,panel_width/40, this);
        } else {
            g.setFont(new Font("Monospaced", Font.BOLD, panel_width/15));
            g.drawString("Game Win!", (panel_width / 2) - panel_width/5, panel_height/2);
        }

        //kontakt fény
        if(game.getEagle().getAltitude()<10){
            g.setColor(Color.green);
            g.fillOval(panel_width-275,35,30,30);
        }
        else{
            g.setColor(Color.red);
            g.fillOval(panel_width-275,35,30,30);
        }
    }

    public int getPanel_height() {
        return panel_height;
    }

    public int getPanel_width() {
        return panel_width;
    }
}
