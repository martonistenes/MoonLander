package MoonLander2020;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerDataTest {

    PlayerData playerData;

    @Before
    public void setUp() throws Exception {
        playerData = new PlayerData();
        playerData.addPlayer("Marci","23","2020-11-11");
        playerData.addPlayer("Bobó","18","2020-11-13");
    }

    @Test
    public void testaddPlayer() {
        playerData.players.clear();
        playerData.addPlayer("Marci","23","2020-11-11");
        Assert.assertEquals(playerData.players.size(),1);
        Assert.assertEquals(playerData.players.get(0).getName(),"Marci");
        Assert.assertEquals(playerData.players.get(0).getTime(),"23");
        Assert.assertEquals(playerData.players.get(0).getDate(),"2020-11-11");
    }

    @Test
    public void testupdate() {
        playerData.update("Béla",10);
        for(int i=0; i<playerData.players.size()-1; i++) {
            Assert.assertTrue(playerData.players.get(i).getRank() < playerData.players.get(i+1).getRank());
        }
    }

    @Test
    public void testgetRowCount() {
        Assert.assertEquals(playerData.getRowCount(),playerData.players.size());
    }

    @Test
    public void getColumnCount() {
        Assert.assertEquals(playerData.getColumnCount(),4);
    }

    @Test
    public void testgetValueAt() {
        for (int i = 0; i < playerData.players.size(); i++) {
            for (int j = 0; j < 4; j++) {
                Assert.assertNotNull(playerData.getValueAt(i, j));
            }
        }
    }

    @Test
    public void testgetColumnName() {
        Assert.assertEquals(playerData.getColumnName(0),"Rank");
        Assert.assertEquals(playerData.getColumnName(1),"Name");
        Assert.assertEquals(playerData.getColumnName(2),"Time");
        Assert.assertEquals(playerData.getColumnName(3),"Date");
    }
}