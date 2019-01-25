import CS5200.wordgame.Client;
import org.junit.Test;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestClient {

    Client nc = new Client("127.0.0.1", 12001);

    @Test
    public void testnewGameEncode()
    {
        try {
            HashMap res = nc.newGame((short) 1, "A02259952", "Goyal", "Aayush", "AG");
            Short msgid = (Short) res.get("msgid");
            assertEquals("2", msgid.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPort(){
        assertEquals(Integer.valueOf(12001), nc.getPort());
    }

    @Test
    public void testIp(){
        assertEquals(("/127.0.0.1"), nc.getIPAddress().toString());
    }


}
