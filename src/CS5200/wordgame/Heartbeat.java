package CS5200.wordgame;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Heartbeat extends Message {
    private ByteArrayOutputStream outputStream;


    public Heartbeat(){}
    public ByteBuffer  Encode()
    {return null;}
    public HashMap Decode(ByteBuffer bytes)throws Exception{
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
            Short msgID = decodeShort(bytes);
            Short gameID =decodeShort(bytes);
            System.out.println(msgID+" "+gameID);
            res.put("msgid",msgID);
            res.put("gameid",gameID);
            return res;
        }
}

