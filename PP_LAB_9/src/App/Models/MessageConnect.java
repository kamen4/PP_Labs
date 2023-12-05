package App.Models;

import java.io.Serializable;

public class MessageConnect extends Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public String userName;

    public MessageConnect( String userName) {
        super( Protocol.CMD_CONNECT );
        this.userName = userName;
    }
}
