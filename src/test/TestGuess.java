import CS5200.wordgame.Client;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class TestGuess {
    Client nc = new Client("127.0.0.1", 12001);
    @Test
    public void TestGuess(){
        try {
            Short num = nc.guess((short)3,"ABCDEFGH");
            assertEquals("4",num.toString());
        }catch (Exception e){}
    }
}
