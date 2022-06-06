package MoonLander2020;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * A játék belső működéséért felelős osztály.
 * @author Istenes Márton
 */
public class MoonLander extends JFrame {

    /**
     * A játékhoz tartozó menü.
     */
    private final Menu menu;

    /**
     * A játékhoz kijelzőosztálya.
     */
    private final MoonLanderPanel moonLanderPanel;

    /**
     * A játékhoz tartozó holdkomp.
     */
    private Eagle eagle;

    /**
     * A játékhoz tartozó "talaj".
     */
    private final Terrain terrain;

    /**
     * A játék végét jelző változó.
     */
    private boolean isGameOver;

    /**
     * A játék nyereségét jelző változó.
     */
    private boolean isWin;

    /**
     * A játék szüneteétetését jelző változó.
     */
    private boolean isPaused;

    /**
     * A játék megszüntetését jelző változó.
     */
    private boolean delete;

    /**
     * A játék végét jelző változó.
     */
    private boolean boost;

    /**
     * Incicializálja a játékhoz tartozó adatokat:
     * <ul>
     * <li>Inicializálja/Értéket ad a változóknak.</li>
     * <li>Inicializálja a framet.</li>
     * </ul>
     * @param m A játékhoz tartozó főmenü.
     */
    public MoonLander(Menu m){

        //értékek beállítása
        delete=false;

        isPaused =true;

        menu = m;

        moonLanderPanel = new MoonLanderPanel(this);

        terrain = new Terrain(this);

        eagle = new Eagle(this);

        boost = false;

        //frame inicializálása
        EventHandler eh = new EventHandler();
        addKeyListener(eh);


        setTitle("MoonLander");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("lunarmodule.png");
        setIconImage(icon);

        add(moonLanderPanel,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Elindítja a játékot:
     * Egy ciklus adott időzítéssel fut folyamatosan.
     * Ha a játék nincs megállítva,nincs vége, és a holdkompnak van elegendő üzemanyaga akkor:
     * <ul>
     * <li>növeli az eltelt időt a menüben</li>
     * <li>upadte-eli a holdkompot</li>
     * </ul>
     * Ujra rajzolja a képernyőt.
     * Ha a játéknak teljesen vége kilép a ciklusból
     */
    public void startGame(){

        while(true) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!isPaused && !isGameOver && eagle.getFuel()>0) {
                menu.increaseElapsedtime(0.002);
                eagle.update();
            }
            moonLanderPanel.repaint();


            if(delete) {
                break;
            }
        }
    }

    /**
     * A billentyűzetet figyelő osztály
     */
    class EventHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        /**
         * Figyeli a lenyomott betűket:
         * <ul>
         * <li>FEL,LE,JOBBRA,BALRA nyilak esetén a megfelelő irányba gyorsítja a holdkompot és csökkenti az üzemanyag mennyiségét.</li>
         * <li>FEL gomb esetén jelzi,hogy a főhajtomű aktív (Ez a grafikus rajzolás miatt kell)</li>
         * <li>P esetén megállítja a játékot.</li>
         * <li>Enter esetén:</li>
         * <li>Ha játék eleje van elindítja a játékot, ha vége akkor kilép</li>
         * <li>SPACE esetén ha a holdkomp magassága kevesebb, mint 10 m be lehet kapcsolni a leszálló üzemmódot.</li>
         * </ul>
         * @param e Lenyomott gomb.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {

                case KeyEvent.VK_UP:
                    if(!isPaused && !isGameOver && eagle.getFuel()>0 && !eagle.isEngine_cutoff()) {
                        boost=true;
                        eagle.a_y(-1);
                        eagle.accelerate();
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if(!isPaused && !isGameOver && eagle.getFuel()>0 && !eagle.isEngine_cutoff()) {
                        boost=false;
                        eagle.a_y(1);
                        eagle.accelerate();
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if(!isPaused && !isGameOver && eagle.getFuel()>0 && !eagle.isEngine_cutoff()) {
                        boost=false;
                        eagle.a_x(-1);
                        eagle.accelerate();
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(!isPaused && !isGameOver && eagle.getFuel()>0 && !eagle.isEngine_cutoff()) {
                        boost=false;
                        eagle.a_x(1);
                        eagle.accelerate();
                    }
                    break;

                case KeyEvent.VK_P:
                    if(!isGameOver) {
                        isPaused = !isPaused;
                    }
                    break;

                case KeyEvent.VK_ENTER:

                    if(isGameOver){
                        delete=true;
                    }
                    else  {
                        isGameOver = false;
                        isPaused = false;;
                        break;
                    }
                    break;

                case KeyEvent.VK_SPACE:
                    if(!isGameOver&& eagle.getAltitude()<10){
                        eagle.setEngine_cutoff(true);
                    }
            }

        }

        /**
         * Ha felengedjuk a FEL nyilat a főhajtómű kikapcsol.
         * @param e Lenyomott gomb
         */
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_UP){
                boost=false;
            }
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Eagle getEagle() {
        return eagle;
    }

    public MoonLanderPanel getMoonLanderPanel() {
        return moonLanderPanel;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setIsGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isBoost() {
        return boost;
    }
}