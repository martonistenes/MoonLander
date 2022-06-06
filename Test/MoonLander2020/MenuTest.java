package MoonLander2020;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MenuTest {

    private Menu menu;

    @Before
    public void setUp() throws Exception {
        menu = new Menu();
    }

    @Test
    public void testincreaseElapsedtime() {
        double temp = menu.getElapsed_time();
        menu.increaseElapsedtime(1.0);
        Assert.assertEquals(String.valueOf(menu.getElapsed_time()),String.valueOf(temp+1));
    }


}