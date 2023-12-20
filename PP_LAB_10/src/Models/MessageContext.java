package Models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MessageContext extends Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public Byte classID;

    public Message.Data data = new Message.Data();
    protected Message.Data getData() {
        return data;
    }

    public MessageContext() {
        super.setup( Protocol.CMD_CONTEXT );
        classID = 0;
    }

    public MessageContext( Message msg ) {
        super.setup( Protocol.CMD_CONTEXT );
        classID = msg.getID();
    }

    public Class<? extends Message> getXmlClass()
            throws ClassNotFoundException {
        return MessageXml.classById(classID);
    }
}
