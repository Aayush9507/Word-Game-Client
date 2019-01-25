package CS5200.wordgame;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;


public class NewGameMessage extends Message {
    private Short msgID;
    private String aNumber;
    private String lName;
    private String fName;
    private String alias;
    private ByteArrayOutputStream outputStream;
    public NewGameMessage(Short msgID,String aNumber, String lName, String fName, String alias){
        this.msgID = msgID;
        this.aNumber = aNumber;
        this.lName = lName;
        this.fName = fName;
        this.alias = alias;
    }

    public NewGameMessage() {}

    public ByteBuffer Encode() throws IOException {
        outputStream = new ByteArrayOutputStream();
        encodeShort(msgID);
        encodeString(aNumber);
        encodeString(lName);
        encodeString(fName);
        encodeString(alias);
        return ByteBuffer.wrap(outputStream.toByteArray());

    }
    public HashMap Decode(ByteBuffer bytes) throws IOException{
        bytes.order(ByteOrder.BIG_ENDIAN);
        HashMap res = new HashMap();

        if (msgID==9) {
            Short gameID =decodeShort(bytes);
            outputStream = new ByteArrayOutputStream();
            Error obj = new Error(msgID,gameID);
            ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());
            res = obj.Decode(buff);
            return res;
        }
        else {
            Short msgID = decodeShort(bytes);
            Short gameID =decodeShort(bytes);
            String hint = decodeString(bytes);
            String definition = decodeString(bytes);
            res.put("msgid",msgID);
            res.put("gameid",gameID);
            res.put("hint",hint);
            res.put("definition",definition);
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
}
