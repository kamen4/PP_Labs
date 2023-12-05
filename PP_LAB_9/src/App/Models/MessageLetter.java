package App.Models;

import java.io.Serializable;

public class MessageLetter extends Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public String txt;
    public String senderName;

    public MessageLetter(String txt, String senderName) {
        super( Protocol.CMD_LETTER );
        this.txt = txt;
        this.senderName = senderName;
    }
}
