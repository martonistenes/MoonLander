package MoonLander2020;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.StrictMath.sin;

/**
 * A hold felszínének megvalósításáért felelős osztály. A felszín görbéjének generálásáról erről a linkről szereztem információt:
 * <a href="https://gamedev.stackexchange.com/questions/10984/what-is-the-simplest-method-to-generate-smooth-terrain-for-a-2d-game"></a>
 * @author Istenes Márton
 */
public class Terrain {

    /**
     * A terep felszínét alkotó koordinátákat tárolja.
     */
    private LinkedList<Point> coordinates;

    /**
     * A terep görbéjének meghatározásához kell.
     */
    private double[] fr;

    /**
     * A terep görbéjének meghatározásához kell.
     */
    private double[] ampl;

    /**
     * A terep görbéjének meghatározásához kell.
     */
    private  double[] offset;

    /**
     * A terep görbéjének meghatározásához kell.
     */
    private final double C;

    /**
     * A terep görbéjének meghatározásához kell.
     */
    private final double k;

    /**
     * A terep sík részének x koordinátája.
     */
    private int sik_x;

    /**
     * A terep sík részének y koordinátája.
     */
    private int sik_y;

    /**
     * A terep sík részének hossza.
     */
    private int sik_hossz;

    /**
     * A Generált terep azon koordinátáját tárolja ahol a terep magassága megeyezik a sík magasságával.
     */
    private  int temp1 = 0;

    /**
     * A terephez tartozó játék.
     */
    private MoonLander moonLander;

    /**
     * Inicializálja a változókat majd legenerálja a terepet.
     * @param m A terephez tartozó játék.
     */
    public Terrain(MoonLander m){
        moonLander=m;


        coordinates=new LinkedList<>();

        C=100;
        k=0.009;

        sik_x= (int) randomdouble(0.1*m.getMoonLanderPanel().getPanel_width(),m.getMoonLanderPanel().getPanel_width()*0.9);
        sik_y= (int) randomdouble( m.getMoonLanderPanel().getPanel_height()*0.7,m.getMoonLanderPanel().getPanel_height()*0.9);
        sik_hossz=randomint(50,70);

        sorsol_a_f_o();
        y_keres();
        generelaziation();

    }

    /**
     * Generál egy random valós számot l és r között
     * @param l alsó érték
     * @param r felső érték
     * @return l és r közötti valós szám.
     */
    public double randomdouble(double l,double r) {
        return l + new Random().nextDouble() * (r - l);
    }

    /**
     * Generál egy random egész számot l és r között
     * @param l alsó érték
     * @param r felső érték
     * @return l és r közötti egész szám.
     */
    public int randomint(int l,int r) {
        return (int) ( l + new Random().nextDouble() * (r - l));
    }

    /**
     * Random számogat generál az adott változókba.
     */
    public void sorsol_a_f_o(){

        fr= new double[3];
        ampl= new double[3];
        offset= new double[3];

        for(int i=0; i<fr.length; i++){
            if(i<2) {
                this.fr[i] = randomdouble(1, 2);
            }
            else {
                fr[i]= randomdouble(2,10);
            }
            this.ampl[i]=randomdouble(-C/fr[i],C/fr[i]);
            this.offset[i]=randomdouble(0,6.28);
        }
        //System.out.println("Fr:" +fr[0]+" "+fr[1]+" "+fr[2]);
        //System.out.println("Ampl:" +ampl[0]+" "+ampl[1]+" "+ampl[2]);
        //System.out.println("offset:" +offset[0]+" "+offset[1]+" "+offset[2]);
    }

    /**
     * Keres a terep görbéjében egy pontot ahol az y koordináta megegyezik a sík y koordinátájával.
     */
    public void y_keres(){
        for (int i=0; i<1000; i++){
            int yteszt1= (int) (sik_y-((ampl[0]* sin(fr[0]*(k*i + offset[0])) + ampl[1]* sin(fr[1]*(k*i + offset[1]))+ampl[2]* sin(fr[2]*(k*i + offset[2])))));

            if (yteszt1==sik_y){
                if(temp1==0) {
                    temp1 = i;
                }
            }
            if(temp1!=0){
                break;
            }
        }
    }

    /**
     * Legenerálja a terepet az inicializált változók értéke alapján. Elősször a síktól jobbra elhelyezkedő részt, utána a sík eészt és végül a jobb ooldalt.
     */
    public void generelaziation(){

        //bal
        for(int i=temp1-sik_x; i<temp1;){

            int yteszt= (int) (sik_y-((ampl[0]* sin(fr[0]*(k*i + offset[0])) + ampl[1]* sin(fr[1]*(k*i + offset[1]))+ampl[2]* sin(fr[2]*(k*i + offset[2])))));


            Point p = new Point(i+sik_x-temp1,yteszt);

            coordinates.add(p);

            i++;
        }

        //sík
        for (int i=sik_x; i<sik_x+sik_hossz; i++){
            Point p = new Point(i,sik_y);
            coordinates.add(p);
        }

        //jobb
        for(int i=temp1; i<temp1+moonLander.getMoonLanderPanel().getPanel_width()-(sik_x+sik_hossz);){

            int yteszt= (int) (sik_y-((ampl[0]* sin(fr[0]*(k*i + offset[0])) + ampl[1]* sin(fr[1]*(k*i + offset[1]))+ampl[2]* sin(fr[2]*(k*i + offset[2])))));

            Point p = new Point(i+sik_x+sik_hossz-temp1,yteszt);

            coordinates.add(p);

            i++;
        }
    }

    public LinkedList<Point> getCoordinates() {
        return coordinates;
    }

    public int getSik_x() {
        return sik_x;
    }

    public int getSik_y() {
        return sik_y;
    }

    public int getSik_hossz() {
        return sik_hossz;
    }

    public double getC() {
        return C;
    }

    public double getK() {
        return k;
    }

    public double[] getAmpl() {
        return ampl;
    }

    public double[] getFr() {
        return fr;
    }

    public double[] getOffset() {
        return offset;
    }

    public int getTemp1() {
        return temp1;
    }

}
