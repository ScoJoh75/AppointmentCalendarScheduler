package languagefiles;

import java.util.*;

public class Login_en extends ListResourceBundle {
    protected String[][] getContents() {
        return new String[][] {
                { "loginLabel", "Username:" },
                { "passwordLabel", "Password:" },
                { "loginSubmit", "Submit" }
        };
    }
}
