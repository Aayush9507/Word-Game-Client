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
        encodeShort(msgID);
        encodeShort(gameID);
        return ByteBuffer.wrap(outputStream.toByteArray());
    }

    void encodeShort(short value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(value);
        outputStream.write(buffer.array());
    }
}
