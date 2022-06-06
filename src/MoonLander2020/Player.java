package MoonLander2020;

import java.io.Serializable;

/**
 * Egy adott játékos adatait tároló struktúra.
 * Szerializálható.
 * @author Istenes Márton
 */
public class Player implements Serializable {

    /**
     * A játékos helyezése.
     */
    private  int rank;

    /**
     * A játékos neve.
     */
    private  String name;

    /**
     * A játékos által elért idő.
     */
    private  String time;

    /**
     * A játékos játszásának ideje.
     */
    private  String date;

    public Player(String s2, String s3, String s4){
        this.name=s2;
        this.time=s3;
        this.date=s4;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getRank() {
        return rank;
    }

    public String getDate() {
        return date;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
