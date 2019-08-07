import CS5200.wordgame.Client;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class TestGethint {
    Client nc = new Client("127.0.0.1", 12001);
    @Test
    public void TestGetHint(){
        try {
            Short num = nc.getHint((short)5);
            assertEquals("6",num.toString());
        }catch (Exception e){}
    }
}


