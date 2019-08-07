package CS5200.wordgame;
import java.nio.ByteBuffer;
import java.util.HashMap;


public abstract class Message
{

    public abstract ByteBuffer Encode() throws Exception;
    public abstract HashMap Decode(ByteBuffer bytes) throws Exception;


}