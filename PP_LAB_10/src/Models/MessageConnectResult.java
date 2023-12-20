package Models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MessageConnectResult extends MessageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public MessageResult.Data data = new MessageResult.Data();
    protected MessageResult.Data getData() {
        return data;
    }

    public MessageConnectResult( String errorMessage ) { // Error
//        super( Protocol.CMD_CONNECT, errorMessage );
        super.setup(Protocol.CMD_CONNECT, errorMessage);
    }

    public MessageConnectResult() { // No error
//        super( Protocol.CMD_CONNECT );
        super.setup(Protocol.CMD_CONNECT);
    }
}
