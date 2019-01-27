package CS5200.wordgame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Error {

    private short msgID;
    private short gameID;
    private String errorMsg;
    private ByteArrayOutputStream outputStream;


    public Error(short msgID, short gameID) {
        this.msgID = msgID;
        this.gameID = gameID;
        errorMsg = " Error encountered";
    }
    public ByteBuffer Encode() throws IOException{
        outputStream = new ByteArrayOutputStream();
        Common.encodeShort(msgID,outputStream);
        Common.encodeShort(gameID,outputStream);
        Common.encodeString(errorMsg,outputStream);
        return ByteBuffer.wrap(outputStream.toByteArray());

    }
    public HashMap Decode(ByteBuffer bytes){
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
        Short msgID = Common.decodeShort(bytes);
        Short gameID =Common.decodeShort(bytes);
        String errmsg = Common.decodeString(bytes);
        System.out.println(msgID+" "+gameID+" "+errmsg);
        res.put("msgid",msgID);
        res.put("gameid",gameID);
        res.put("errmsg",errmsg);
        return res;
    }
}