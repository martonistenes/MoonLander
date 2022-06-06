package MoonLander2020;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A dicsőséglistában tárolt adatokért felelős osztály.
 * @author Istenes Márton
 */
public class PlayerData extends AbstractTableModel implements TableModel {


    /**
     * A dicsőséglistában tárolt adatokat tároló lista.
     */
    List<Player> players = new ArrayList<Player>();

    /**
     * Új playereket ad hozzá a players listához.
     * @param name Az új játékos neve.
     * @param time Az új játékos ideje.
     * @param date A játék időpontja.
     */
    public void addPlayer(String name, String time, String date) {
        Player uj =new Player(name,time,date);
        players.add(uj);
        fireTableDataChanged();
    }

    /**
     * Frissití a lista adatait, (hozzáadja az újat) és rendezi őket (rank,time).
     * @param nev Az új játékos neve.
     * @param time Az új játékos ideje.
     */
    public void update(String nev, double time){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        addPlayer(nev, (Math.round(time * 100.0) / 100.0) + " s",strDate);

        Collections.sort(players,new Sortbytime());
        fireTableDataChanged();

        for(int i=0; i<players.size(); i++){
            players.get(i).setRank(i+1);
            fireTableDataChanged();
        }
    }

    /**
     * A játékosok sorrendjét megvalósító osztály.
     */
    private static class Sortbytime implements Comparator<Player>
    {
        /**
         * Rendez két játékost az elért idejük szerint.
         */
        public int compare(Player a, Player b)
        {
            return a.getTime().compareTo(b.getTime());
        }
    }


    /**
     * Lekérdezi hány sor van a táblázatban.
     * @return Játékosok száma
     */
    @Override
    public int getRowCount() {
        return players.size();
    }

    /**
     * Lekérdezi hányféle adatot tárol a táblázat.
     * @return Tárolt adatok száma /játékos
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Visszaadaja,hogy a táblázat adott cellájában milyen adat van.
     * @return Cella tartalma.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player player = players.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return player.getRank();
            case 1:
                return player.getName();
            case 2:
                return player.getTime();
            default:
                return player.getDate();
        }
    }

    /**
     * Visszaadaja,hogy a táblázat által tárolt adatok milyen néven jelenjenek meg.
     * @return String formátumban a kiirandó oszlopnév.
     */
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Rank";
            case 1:
                return "Name";
            case 2:
                return "Time";
            default:
                return "Date";
        }
    }


}

