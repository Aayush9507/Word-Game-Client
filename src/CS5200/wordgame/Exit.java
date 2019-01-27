package CS5200.wordgame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Exit extends Message {
    private Short msgID;
    private Short gameId;
    private ByteArrayOutputStream outputStream;


    public Exit(Short msgID, Short gameId) {
        this.msgID = msgID;
        this.gameId = gameId;
    }

    public Exit() {
    }

    public ByteBuffer Encode() throws IOException {
        outputStream = new ByteArrayOutputStream();
        Exit obj = new Exit();
        Common.encodeShort(msgID,outputStream);
        Common.encodeShort(gameId,outputStream);
        return ByteBuffer.wrap(outputStream.toByteArray());
    }

    public HashMap Decode(ByteBuffer bytes) {
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
        if (msgID == 9) {
            Short gameID = Common.decodeShort(bytes);
            outputStream = new ByteArrayOutputStream();
            Error obj = new Error(msgID, gameID);
            ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());
            res = obj.Decode(buff);
            System.out.println("Error in Exit request");
            return res;
        } else {
            Short msgID = Common.decodeShort(bytes);
            Short gameID = Common.decodeShort(bytes);
            System.out.println(msgID + " " + gameID);
            res.put("msgid", msgID);
            res.put("gameid", gameID);
            return res;
        }

    }
}
