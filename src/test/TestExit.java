import CS5200.wordgame.Client;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class TestExit {
    Client nc = new Client("127.0.0.1", 12001);
    @Test
    public void testExit(){
        try {
            Short num = nc.exitAck((short)7);
            assertEquals("8",num.toString());
        }catch (Exception e){}
    }
}


