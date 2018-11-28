package languagefiles;

import java.util.ListResourceBundle;

public class Login_es extends ListResourceBundle {
    protected String[][] getContents() {
        return new String[][] {
                { "loginLabel", "Nombre de usuario:" },
                { "passwordLabel", "Contraseña:" },
                { "loginSubmit", "Iniciar sesión" }
        };
    }
}
