package languagefiles;

import java.util.ListResourceBundle;

public class Login_es extends ListResourceBundle {
    protected String[][] getContents() {
        return new String[][] {
                { "loginTitle", "Por favor Iniciar sesión!"},
                { "loginLabel", "Nombre de usuario:" },
                { "passwordLabel", "Contraseña:" },
                { "loginSubmit", "Iniciar sesión" },
                { "loginError", "¡Nombre de usuario o contraseña incorrecta!"}
        };
    }
}
