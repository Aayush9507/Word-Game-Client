package CS5200.wordgame;
import org.apache.log4j.BasicConfigurator;

import java.nio.channels.DatagramChannel;

public class Main {

    public static void main(String[] args) {

        BasicConfigurator.configure();
        System.out.println("Calling Server");

        Client obj = new Client("127.0.0.1",12001);
        DatagramChannel client =  new Client("127.0.0.1",12001).getClient();

        obj.newGame((short)1,"A02259952","Goyal","Aayush","AG");
        obj.guess((short)3,"ABCD");
        obj.getHint((short)5);
        obj.exitAck((short)7);
        //I have called the heartbeat in Heartbeat method in Client



    }
}
