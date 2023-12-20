package Models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MessageConnect extends Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public String userName;

    public Message.Data data = new Message.Data();
    protected Message.Data getData() {
        return data;
    }

    public MessageConnect(String userName) {
//        super( Protocol.CMD_CONNECT );
        super.setup(Protocol.CMD_CONNECT);
        this.userName = userName;
    }

    public String toString() {
        return super.toString() + ", " + userName;
    }
}
