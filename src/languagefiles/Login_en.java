package languagefiles;

import java.util.*;

public class Login_en extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                { "loginLabel", "Username:" },
                { "passwordLabel", "Password:" },
                { "loginSubmit", "Submit" }
        };
    }
}
