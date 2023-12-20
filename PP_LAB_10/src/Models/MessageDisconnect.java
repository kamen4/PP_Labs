package Models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MessageDisconnect extends Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public Message.Data data = new Message.Data();
    protected Message.Data getData() {
        return data;
    }

    public MessageDisconnect() {
        super.setup( Protocol.CMD_DISCONNECT );
    }
}