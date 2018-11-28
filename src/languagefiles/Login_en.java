package languagefiles;

import java.util.ListResourceBundle;

public class Login_en extends ListResourceBundle {
    protected String[][] getContents() {
        return new String[][] {
                { "loginTitle", "Please Log In!"},
                { "loginLabel", "Username:" },
                { "passwordLabel", "Password:" },
                { "loginSubmit", "Log In" }
        };
    }
}
