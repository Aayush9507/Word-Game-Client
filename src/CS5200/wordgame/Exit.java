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
        encodeShort(msgID);
        encodeShort(gameId);
        return ByteBuffer.wrap(outputStream.toByteArray());
    }
    public HashMap Decode(ByteBuffer bytes){
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
        if (msgID==9) {
            Short gameID =decodeShort(bytes);
            outputStream = new ByteArrayOutputStream();
            Error obj = new Error(msgID,gameID);
            ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());
            res = obj.Decode(buff);
            System.out.println("Error in Exit request");
            return res;
        }
        else {Short msgID = decodeShort(bytes);
            Short gameID =decodeShort(bytes);
            System.out.println(msgID+" "+gameID);
            res.put("msgid", msgID);
            res.put("gameid", gameID);
            return res;}

    }
    void encodeShort(short value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(value);
        outputStream.write(buffer.array());
    }

    void encodeString(String value) throws IOException {
        if (value==null)
            value="";

        byte[] textBytes = value.getBytes(Charset.forName("UTF-16BE"));
        encodeShort((short) (textBytes.length));
        outputStream.write(textBytes);
    }

    static short decodeShort(ByteBuffer bytes) {
        return bytes.getShort();
    }

    }
