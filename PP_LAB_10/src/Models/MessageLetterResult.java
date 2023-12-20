package Models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MessageLetterResult extends MessageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public MessageResult.Data data = new MessageResult.Data();
    protected MessageResult.Data getData() {
        return data;
    }

    public MessageLetterResult( String errorMessage ) { //Error
        super.setup( Protocol.CMD_LETTER, errorMessage );
    }

    public MessageLetterResult() { // No errors
        super.setup( Protocol.CMD_LETTER );
    }
}
