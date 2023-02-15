package server.threads;
import java.util.List;
import java.util.ArrayList;


public class MessageList {
    private List<String> messageList  = new ArrayList<String>();

    synchronized public void saveMessage(String message){

        messageList.add(message);

    }

    public List<String> getMessage(){
        return messageList;
    }


}
