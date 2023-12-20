package Models;

interface CMD {
    static final byte CMD_CONTEXT 	 = 1;
    static final byte CMD_CONNECT 	 = 2;
    static final byte CMD_DISCONNECT = 3;
    static final byte CMD_LETTER	 = 4;
}

interface RESULT {
    static final int RESULT_CODE_OK 	= 0;
    static final int RESULT_CODE_ERROR 	= -1;
}

interface PORT {
    static final int PORT = 8071;
}

public class Protocol implements CMD, RESULT, PORT {
    private static final byte CMD_MIN = CMD_CONTEXT;
    private static final byte CMD_MAX = CMD_LETTER;
    public static boolean validID( byte id ) {
        return id >= CMD_MIN && id <= CMD_MAX;
    }
}

