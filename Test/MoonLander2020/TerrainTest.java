package MoonLander2020;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TerrainTest {

    private Terrain terrain;
    private MoonLander moonLander;

    @Before
    public void setUp() throws Exception {
        Menu menu = new Menu();
        moonLander = new MoonLander(menu);
        terrain = new Terrain(moonLander);
    }

    @Test
    public void testrandomdouble() {
        double n = terrain.randomdouble(10,20);
        Assert.assertTrue(n >= 10);
        Assert.assertTrue(n <= 20);
    }

    @Test
    public void testrandomint() {
        int n = terrain.randomint(10,20);
        Assert.assertTrue(n >= 10);
        Assert.assertTrue(n <= 20);
    }

    @Test
    public void testsorsol_a_f_o() {
        terrain.sorsol_a_f_o();
        Assert.assertNotNull(terrain.getAmpl());
        Assert.assertNotNull(terrain.getFr());
        Assert.assertNotNull(terrain.getOffset());
    }

    @Test
    public void testy_keres() {
        terrain.y_keres();
        Assert.assertNotEquals(terrain.getTemp1(),0);
    }

    @Test
    public void testgenerelaziation() {
        terrain.getCoordinates().clear();
        terrain.generelaziation();
        Assert.assertEquals(terrain.getCoordinates().size(),moonLander.getMoonLanderPanel().getPanel_width());
    }

}