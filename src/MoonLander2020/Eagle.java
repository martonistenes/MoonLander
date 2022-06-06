package MoonLander2020;

/**
 * A holdkomp adataiért felelős osztály.
 * @author Istenes Márton
 */
public class Eagle  {

    /**
     * A játék amiben a holdkomp szerepel.
     */
    private final MoonLander game;

    /**
     * A holdkomp x koordinátája.
     */
    private double x;

    /**
     * A holdkomp y koordinátája.
     */
    private double y;

    /**
     * A hold gravitációja.
     */
    private final double gravity = 0.00005;

    /**
     * A hold x irányú sebessége.
     */
    private double velx;

    /**
     * A hold y irányú sebessége.
     */
    private  double vely;

    /**
     * A holdkomp üzemanyagmennyisége.
     */
    private int fuel;

    /**
     * A holdkomp magassága az alatta levő terephez viszonyítva.
     */
    private int altitude;

    /**
     * Jelzi a a holdkomp leszálló üzemmódban van.
     */
    private boolean engine_cutoff;


    /**
     * Inicializálja a változókat.
     * @param m a Játék amihez a holdkomp tartozik.
     */
     public Eagle(MoonLander m) {

        game = m;

        x = 0;

        y = 0;

        velx = 0.15;

        vely = 0;

        fuel = 1000;

        altitude = 5;

        engine_cutoff = false;

    }

    /**
     * <ul>
     * <li>Gravitációt elvégzi a holdkompon.</li>
     * <li>Gyosítások esetén megfelelően növeli a sebességeket.</li>
     * <li>Ha a holdkomp pályán belül van megnézi, hogy ütközik e a holdkomp a talajjal.</li>
     * <li>Ha a holdkomp pályán kívül van a magassága -1 lesz.</li>
     * </ul>
     */
    public void update() {

        vely += gravity;
        y += vely;
        x += velx;
        if(x >= 0 && x <= game.getMoonLanderPanel().getPanel_width()-game.getMoonLanderPanel().getPanel_width()/60) {
            checkcollide();
            altitude=(game.getTerrain().getCoordinates().get((int) x).y - (int)y)/(game.getMoonLanderPanel().getPanel_width()/300);
        }
        else altitude=-1;

    }

    /**
     * Megnézi,hogy a holdkomp valamelyik lába hozzáér-e a terephez.
     * Ha igen és az alábbi feltételek teljesülnek akkor a játék nyereséggel zárul:
     * <ul>
     *     <li>A holdkomp minden lábával a terepen van(csak akkor történhet meg ha síkra száll.</li>
     *     <li>Ha be van kapcsolva a leszállás üzemmód</li>
     *     <li>Nem túl nagy sem a vízszintes sem a függőleges sebesség sem</li>
     *</ul>
     * Ha igen de a fent leírtak nem teljesülnek akkor a játék vereséggel zárul.
     * Ha nem akkor nem történik semmi.
     */
    public void checkcollide(){
             int terep_bal_y  = game.getTerrain().getCoordinates().get((int) x).y;
             int terep_jobb_y = game.getTerrain().getCoordinates().get((int) x+(game.getMoonLanderPanel().getPanel_width()/60)).y;
             int holdkomp_alja = (int) y+(game.getMoonLanderPanel().getPanel_width()/60-2);


             if ((terep_bal_y-2<holdkomp_alja && terep_bal_y+2>holdkomp_alja) || (terep_jobb_y-2<holdkomp_alja && terep_jobb_y+2>holdkomp_alja)) {

                 if(terep_bal_y==terep_jobb_y && engine_cutoff &&  vely*-100 > -3 && velx*100<1.5 && velx*100>-1.5){
                     //System.out.println("Nyertél!");
                     game.setIsGameOver(true);
                     game.setWin(true);
                     game.setPaused(true);
                 }

                 else {
                     //System.out.println("Vesztettél!");
                     game.setIsGameOver(true);
                     game.setWin(false);
                     game.setPaused(true);
                 }

             }
    }

    /**
     * Növeli vagy csökkenti a paramétertől függően a vízszintes sebességet.
     * @param velx A változás előjele.
     */
    public void a_x(double velx) {
        this.velx += velx*0.0025;
    }

    /**
     * Növeli vagy csökkenti a paramétertől függően a függőleges sebességet.
     * @param vely A változás előjele.
     */
    public void a_y(double vely) {
        this.vely += vely*0.0025;
    }

    /**
     * Csökkenti a holdkomp üzemanyagmennyiségét
     */
    public  void accelerate(){
        this.fuel--;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public double getVelx() {
        return velx;
    }

    public double getVely() {
         return vely;
    }

    public int getFuel() {
        return fuel;
    }

    public int getAltitude() {
        return altitude;
    }

    public boolean isEngine_cutoff() {
        return engine_cutoff;
    }

    public void setEngine_cutoff(boolean engine_cutoff) {
        this.engine_cutoff = engine_cutoff;
    }

    public double getGravity() {
        return gravity;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelx(double velx) {
        this.velx = velx;
    }

    public void setVely(double vely) {
        this.vely = vely;
    }
}
