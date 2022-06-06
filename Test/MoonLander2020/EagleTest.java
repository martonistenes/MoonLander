package MoonLander2020;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EagleTest {

    private MoonLander moonLander;
    private Eagle eagle;

    @Before
    public void setUp() throws Exception {
        Menu menu = new Menu();
        moonLander = new MoonLander(menu);
        eagle=new Eagle(moonLander);
    }

    @Test
    public void testupdate() {
        double startvelx = eagle.getVelx();
        double startvely = eagle.getVely();
        eagle.a_x(100);
        eagle.a_y(100);
        eagle.update();
        Assert.assertEquals(String.valueOf(eagle.getVelx()),String.valueOf(startvelx+0.25));
        Assert.assertEquals(String.valueOf(eagle.getVely()),String.valueOf(startvely+0.25+eagle.getGravity()));

    }

    @Test
    public void testcheckcollide() {
        eagle.setX(moonLander.getTerrain().getSik_x()+10);
        eagle.setY(moonLander.getTerrain().getSik_y() - moonLander.getMoonLanderPanel().getPanel_width()/60 +1);
        eagle.setVely(0);
        eagle.setVelx(0);
        eagle.setEngine_cutoff(true);
        eagle.checkcollide();
        Assert.assertEquals(moonLander.isWin(),true);

    }

    @Test
    public void accelerate() {
        int startfuel=eagle.getFuel();
        eagle.accelerate();
        Assert.assertEquals(eagle.getFuel(),startfuel-1);

    }

}