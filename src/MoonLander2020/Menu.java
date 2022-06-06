package MoonLander2020;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;


/**
 * A MoonLander játék menüjét megvalósító osztály.
 * @author Istenes Márton
 */
public class Menu extends JFrame {

    /**
     * A dicsőséglistában levő adatok tárolója.
     */
    private PlayerData data;

    /**
     * A menü kijelző osztálya.
     */
    private final MenuPanel mpanel;

    /**
     * A menüből megnyitható játék.
     */
    private boolean start;

    /**
     * Egy elindított játékban az eltelt játékidőt számolja.
     */
    private double elapsed_time;

    /**
     * Növeli az elindított játékban az eltelt játékidőt.
     * @param et A mennyiség amivel növeli az eltelt időt.
     */
    public void increaseElapsedtime(double et) {
        elapsed_time += et;
    }

    /**
     * Betölti az adatokat és inicializálja az osztályt. Fiygeli ha bezerul az ablak és ilyenkor elmenti az dataokat.
     */
    public Menu(){

        // Induláskor betöltjük az adatokat
        try {
            data = new PlayerData();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scoreboard.dat"));
            data.players = (List<Player>)ois.readObject();
            ois.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        //data.players.clear();

        // Bezáráskor mentjük az adatokat
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scoreboard.dat"));
                    oos.writeObject(data.players);
                    oos.close();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        start = false;

        mpanel = new MenuPanel(this);

        add(mpanel);

        setLocation(500,500);
        pack();
        setTitle("MoonLander");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("lunarmodule.png");
        setIconImage(icon);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Addig vár amig nem kap jelzést,hogy elindulhat a játék.
     */
    public void waitforstart()  {

         while(!start){
             try {
                 Thread.sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         start = false;
    }

    /**
     * Elindítja a menüt. Vár amíg nem indítjuk el. Ha létrehozza a játékot majd megszünteti ha vége. Ha a játék nyereséggel zárul beírja az adatokat a dicsőséglistába.
     */
    public void startmenu(){

         while(true){

             waitforstart();
             setVisible(false);
             elapsed_time = 0;
             MoonLander newgame = new MoonLander(this);
             newgame.startGame();

             if(newgame.isWin()) {
                 data.update(mpanel.getTfield().getText(),elapsed_time);
             }

             newgame.dispose();
             setVisible(true);

         }

    }

    public PlayerData getData() {
        return data;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public MenuPanel getMpanel() {
        return mpanel;
    }

    public double getElapsed_time() {
        return elapsed_time;
    }

    /**
     * Ez a főprogram. Létrehoz egy menüt és elindítja.
     * @param args main argumentuma.
     */
    public static void main(String[] args) {
        Menu jatek = new Menu();
        jatek.startmenu();
    }
}


