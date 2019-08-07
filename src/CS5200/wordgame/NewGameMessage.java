package CS5200.wordgame;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
        Common.encodeShort(msgID,outputStream);
        Common.encodeString(aNumber,outputStream);
        Common.encodeString(lName,outputStream);
        Common.encodeString(fName,outputStream);
        Common.encodeString(alias,outputStream);
        return ByteBuffer.wrap(outputStream.toByteArray());

    }
    public HashMap Decode(ByteBuffer bytes) throws IOException{
        bytes.order(ByteOrder.BIG_ENDIAN);
        HashMap res = new HashMap();

        if (msgID==9) {
            Short gameID =Common.decodeShort(bytes);
            outputStream = new ByteArrayOutputStream();
            Error obj = new Error(msgID,gameID);
            ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());
            res = obj.Decode(buff);
            return res;
        }
        else {
            Short msgID = Common.decodeShort(bytes);
            Short gameID =Common.decodeShort(bytes);
            String hint = Common.decodeString(bytes);
            String definition = Common.decodeString(bytes);
            res.put("msgid",msgID);
            res.put("gameid",gameID);
            res.put("hint",hint);
            res.put("definition",definition);
            return res;}

    }
}
