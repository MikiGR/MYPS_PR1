/*
@author Miguel Galdeano Rodríguez
@author Pablo León Vazquez
*/
package clubdeportivo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubExceptionTest {
    
    public void probarExcepcionVacia() throws ClubException {
        throw new ClubException();
    }

    @Test
    @DisplayName("Al lanzar una excepción con un mensaje se muestra el mensaje")
    public void ClubException_throwExceptionWithMessage_returnTrue() {
        try {
            throw new ClubException("Mensaje de prueba");
        } catch (ClubException e) {
            assert(e.getMessage().equals("Mensaje de prueba"));
        }
    }

    @Test
    @DisplayName("Al lanzar una excepción sin mensaje se muestra sin mensaje")
    public void ClubException_throwExceptionWithoutMessage_returnTrue() {
        try {
            probarExcepcionVacia();
        } catch (ClubException e) {
            assert(e.getMessage() == null);
        }
    }
}
