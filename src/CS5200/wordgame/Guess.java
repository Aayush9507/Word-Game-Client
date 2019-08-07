package CS5200.wordgame;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Guess extends Message {
    static Logger myLogger = Logger.getLogger(Guess.class);
    private Short msgID;
    private Short gameId;
    private String guess;
    private ByteArrayOutputStream outputStream;


    public Guess(Short msgType,Short gameId, String guess){
        this.msgID=msgType;
        this.gameId=gameId;
        this.guess=guess;
    }
    public Guess(){}

    public ByteBuffer Encode() throws IOException {
        outputStream = new ByteArrayOutputStream();
        Guess obj = new Guess();
        Common.encodeShort(msgID,outputStream);
        Common.encodeShort(gameId,outputStream);
        Common.encodeString(guess,outputStream);
        return ByteBuffer.wrap(outputStream.toByteArray());
    }
    public HashMap Decode(ByteBuffer bytes){
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
        if (msgID==9) {
            Short gameID = Common.decodeShort(bytes);
            outputStream = new ByteArrayOutputStream();
            Error obj = new Error(msgID,gameID);
            ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());
            res = obj.Decode(buff);
            myLogger.info("Error in Guess request");
            return res;
        }
        else {
            Short msgID = Common.decodeShort(bytes);
            Short gameID =Common.decodeShort(bytes);
            Byte result = decodeByte(bytes);
            Short score = Common.decodeShort(bytes);
            String hint = Common.decodeString(bytes);
            System.out.println(msgID+" "+gameID+" "+result+" "+score+" "+hint);
            res.put("msgid",msgID);
            res.put("gameid",gameID);
            res.put("result",result);
            res.put("score",score);
            res.put("hint",hint);

            return res;
        }

    }

    static byte decodeByte(ByteBuffer bytes){
        return bytes.get();
    }



}