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
            mylogger.info("Error in Get hint request");
            return res;
        }
        else {Short msgID = decodeShort(bytes);
            Short gameID =decodeShort(bytes);
            String hint = decodeString(bytes);

            System.out.println(msgID+" "+gameID+" "+hint);
            res.put("msgid", msgID);
            res.put("gameid", gameID);
            res.put("hint", hint);
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

    static String decodeString(ByteBuffer bytes) {
        short textLength = decodeShort(bytes);
        if (bytes.remaining() < textLength) {
            return null;
        }
        byte[] textBytes = new byte[textLength];
        bytes.get(textBytes, 0, textLength);
        return new String(textBytes, Charset.forName("UTF-16BE"));
    }

    static byte decodeByte(ByteBuffer bytes){
        return bytes.get();
    }
}