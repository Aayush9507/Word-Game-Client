package CS5200.wordgame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
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
        encodeShort(msgID);
        encodeShort(gameID);
        encodeString(errorMsg);
        return ByteBuffer.wrap(outputStream.toByteArray());

    }
    public HashMap Decode(ByteBuffer bytes){
        HashMap res = new HashMap();
        bytes.order(ByteOrder.BIG_ENDIAN);
        Short msgID = decodeShort(bytes);
        Short gameID =decodeShort(bytes);
        String errmsg = decodeString(bytes);
        System.out.println(msgID+" "+gameID+" "+errmsg);
        res.put("msgid",msgID);
        res.put("gameid",gameID);
        res.put("errmsg",errmsg);
        return res;
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
}