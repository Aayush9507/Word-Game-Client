import CS5200.wordgame.Client;
import org.junit.Test;

import java.nio.channels.DatagramChannel;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestHeartbeat {


    Client nc = new Client("127.0.0.1", 12001);
    HashMap res = null;
    @Test
    public void testHeartbeat(){
        try {
            nc.newGame((short) 1, "A02259952", "Goyal", "Aayush", "AG");
            DatagramChannel client = nc.getClient();
            res = nc.retrieveHeartbeat(client);
            Short msgid = (Short) res.get("msgid");
            assertEquals("10",msgid.toString());

        }catch (Exception e){}
    }
}
