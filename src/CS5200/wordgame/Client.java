package CS5200.wordgame;
import org.apache.log4j.Logger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;



public class Client {
    private static Logger myLogger = Logger.getLogger(Client.class);
    private Short gameId = null;
    static DatagramChannel client;
    private InetSocketAddress serverAddress;
    private InetAddress IPAddress;
    private Integer port;
    private HashMap response;
    public Client(){}

    public void run(){
        try {
            Thread.sleep(4000);
            myLogger.debug("Listening Heartbeat");
            retrieveHeartbeat(Client.client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client(String host, Integer port) {
        try {
            this.port = port;
            IPAddress = InetAddress.getByName(host);
        } catch (Exception ex) {}
    }

    public HashMap newGame(Short msgtype, String anum, String lname, String fname, String alias){

        myLogger.info("Sending New Game request");
        try {
            NewGameMessage obj  = new NewGameMessage(msgtype,anum,lname,fname,alias);
            ByteBuffer encodedByte = obj.Encode();
            serverAddress = new InetSocketAddress(IPAddress, port);
            client = DatagramChannel.open();
            client.bind(null);
            client.send(encodedByte, serverAddress);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            client.receive(buffer);
            buffer.flip();
            response = obj.Decode(buffer);

            gameId = (Short) response.get("gameid");
            msgtype = (Short) response.get("msgid");
            myLogger.info("Receiving Game definiton");
            myLogger.info("NewGame Response: MsgID = "+response.get("msgid")+" gameid = "+response.get("gameid")+" Hint = "+response.get("hint")+" Definition = "+response.get("definition"));
//            retrieveHeartbeat(client);

        }
        catch (Exception ex) {
            System.out.println("Exception"+ex.getMessage());
        }return response;
    }

    public HashMap guess(Short msgid,String guess){
        myLogger.info("Sending Guess request");
        try {
            Guess obj  = new Guess(msgid,gameId,guess);
            ByteBuffer encodedByte = obj.Encode();
            InetSocketAddress serverAddress = new InetSocketAddress(IPAddress, port);
            client = DatagramChannel.open();
            client.bind(null);
            int bytesSending = client.send(encodedByte, serverAddress);
            System.out.println("Total Bytes sent = "+bytesSending);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            client.receive(buffer);
            buffer.flip();
            response = obj.Decode(buffer);
            myLogger.info("Receiving Game Response");
            myLogger.info("Guess Response: MsgID = "+response.get("msgid")+" gameid = "+response.get("gameid")+" result = "+response.get("result")+" score = "+response.get("score")+" hint = "+response.get("hint"));
        }
        catch (Exception ex) {
            myLogger.info("Exception"+ex.getLocalizedMessage());
        }
        return response;
    }

    public void getHint(Short msgtype){
        myLogger.info("Sending Hint request");
        try {
            Gethint obj  = new Gethint(msgtype, gameId);
            ByteBuffer encodedByte = obj.Encode();
            InetSocketAddress serverAddress = new InetSocketAddress(IPAddress, port);
            client = DatagramChannel.open();
            client.bind(null);
            int bytesSending = client.send(encodedByte, serverAddress);
            System.out.println("Total Bytes for hint sent = "+bytesSending);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            client.receive(buffer);
            buffer.flip();
            response = obj.Decode(buffer);
            myLogger.info("Receiving Hint");
            myLogger.info("Hint Response: Msgid = "+response.get("msgid")+" gameid = "+response.get("gameid")+" hint = "+response.get("hint"));
        }
        catch (Exception ex) {
            myLogger.info("Exception"+ex.getLocalizedMessage());
        }
    }
    public void exitAck(Short msgtype){
        myLogger.info("Sending Exit request");
        try {
            Exit obj  = new Exit(msgtype, gameId);
            ByteBuffer encodedByte = obj.Encode();

            InetSocketAddress serverAddress = new InetSocketAddress(IPAddress, port);
            client = DatagramChannel.open();
            client.bind(null);

            int bytesSending = client.send(encodedByte, serverAddress);
            System.out.println("Total Bytes for Exit sent = "+bytesSending);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            client.receive(buffer);
            buffer.flip();
            HashMap newgameresponse = obj.Decode(buffer);
            myLogger.info("Exit Acknowledgement: MsgID = "+newgameresponse.get("msgid")+" gameid = "+newgameresponse.get("gameid"));
        }
        catch (Exception ex) {
            myLogger.info("Exception"+ex.getLocalizedMessage());
        }
    }

    public HashMap retrieveHeartbeat(DatagramChannel dc){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            myLogger.info("Receiving Heartbeat");
            dc.receive(buffer);
            buffer.flip();
            response = new Heartbeat().Decode(buffer);
            Ack obj = new Ack((short) 8, gameId);
            obj.Encode();
        }
        catch (Exception ex) {
            myLogger.info("Exception"+ex.getLocalizedMessage());
        }
        return response;
    }
    public Integer getPort() {
        return port;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public DatagramChannel getClient() {
        return client;
    }

    //    Thread th = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while (true){
//        }
//    }

//    public void Error(){
//        System.out.println("Error");
//        try {
//            Error obj  = new Error((short)9, gameId);
//            ByteBuffer encodedByte = obj.Encode();
//            InetSocketAddress serverAddress = new InetSocketAddress(IPAddress, port);
//            client = DatagramChannel.open();
//            client.bind(null);
//            int bytesSending = client.send(encodedByte, serverAddress);
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//            client.receive(buffer);
//            buffer.flip();
//            HashMap newgameresponse = obj.Decode(buffer);
//            myLogger.info("Response for Error request: "+newgameresponse.get("msgid"));
//        }
//        catch (Exception ex) {
//            System.out.println("Exception"+ex.getLocalizedMessage());
//        }
//    }


}
