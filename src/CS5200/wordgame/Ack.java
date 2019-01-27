package CS5200.wordgame;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Ack {
    private short msgID;
    private short gameID;
    private ByteArrayOutputStream outputStream;

    public Ack(){}

    public Ack(short msgID, short gameID){
        this.msgID=msgID;
        this.gameID=gameID;
    }
    public ByteBuffer Encode() throws IOException {
        outputStream = new ByteArrayOutputStream();
//        Guess obj = new Guess();
        Common.encodeShort(msgID,outputStream);
        Common.encodeShort(gameID,outputStream);
        return ByteBuffer.wrap(outputStream.toByteArray());
    }

}
