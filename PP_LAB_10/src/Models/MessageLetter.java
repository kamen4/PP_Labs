package Models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MessageLetter extends Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public String txt;
    public String senderName;

    public Message.Data data = new Message.Data();
    protected Message.Data getData() {
        return data;
    }

    public MessageLetter(String txt, String senderName) {
//        super( Protocol.CMD_LETTER );
        super.setup(Protocol.CMD_LETTER);
        this.txt = txt;
        this.senderName = senderName;
    }

    public String toString() {
        return super.toString() + ", " + txt + ", " + senderName;
    }
}
