package part2;

import java.util.Locale;
import java.util.ResourceBundle;

public class AppLocale {
    private static final String strMsg = "Msg";
    private static Locale loc = Locale.getDefault();
    private static ResourceBundle res =
            ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );

    static Locale get() {
        return AppLocale.loc;
    }

    static void set( Locale loc ) {
        AppLocale.loc = loc;
        res = ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
    }

    static ResourceBundle getBundle() {
        return AppLocale.res;
    }

    static String getString( String key ) {
        return AppLocale.res.getString(key);
    }

    // Resource keys:

//    public static final String loading="Loading data...";
//    public static final String loadFailure="Cannot load data";
//    public static final String header1="Enter name (or";
//    public static final String header2="to exit):";
//    public static final String entercommand="Enter command";
//    public static final String orders_post=" orders:";
//    public static final String not="not ";
//    public static final String paid="paid)";
//    public static final String name="Name";
//    public static final String clients404="Clients not found!";
//    public static final String command404="Unknown command";
//    public static final String Hello="Hello";
//    public static final String newuser="you are a new user!";
//    public static final String product404="This product does not exist";
//    public static final String saveFailure="Writing error";

    public static final String loading="loading";
    public static final String loadFailure="loadFailure";
    public static final String header1="header1";
    public static final String header2="header2";
    public static final String entercommand="entercommand";
    public static final String orders_post="orders_post";
    public static final String not="not";
    public static final String paid="paid";
    public static final String name="name";
    public static final String clients404="clients404";
    public static final String command404="command404";
    public static final String Hello="Hello";
    public static final String newuser="newuser";
    public static final String product404="product404";
    public static final String saveFailure="saveFailure";
    public static final String date="date";
}
