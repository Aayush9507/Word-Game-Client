package CS5200.wordgame;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Gethint extends Message {
    static Logger mylogger = Logger.getLogger(Gethint.class);
    private Short msgID;
    private Short gameId;
    private ByteArrayOutputStream outputStream;


    public Gethint(Short msgID, Short gameId) {
        this.msgID = msgID;
        this.gameId = gameId;
    }

    public Gethint() {
    }


    public ByteBuffer Encode() throws IOException {
        outputStream = new ByteArrayOutputStream();
        Common.encodeShort(msgID,outputStream);
        Common.encodeShort(gameId,outputStream);
        return ByteBuffer.wrap(outputStream.toByteArray());
    }
    public HashMap Decode(ByteBuffer bytes){
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
        if (msgID==9) {
            Short gameID =Common.decodeShort(bytes);
            outputStream = new ByteArrayOutputStream();
            Error obj = new Error(msgID,gameID);
            ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());
            res = obj.Decode(buff);
            mylogger.info("Error in Get hint request");
            return res;
        }
        else {Short msgID = Common.decodeShort(bytes);
            Short gameID =Common.decodeShort(bytes);
            String hint = Common.decodeString(bytes);

            System.out.println(msgID+" "+gameID+" "+hint);
            res.put("msgid", msgID);
            res.put("gameid", gameID);
            res.put("hint", hint);
            return res;}

    }

}