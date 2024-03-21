package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoAltoRendimientoTest {
    @Test
    @DisplayName("Al crear un club de alto rendimiento con valor incremento negativo o 0 lanza excepción")
    public void ClubDeportivoAltoRendimiento_TresParametrosYValorIncrementoNegativo_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new ClubDeportivoAltoRendimiento("c1", 10, 0);
        });
    }

    @Test
    @DisplayName("Al crear un club de alto rendimiento con valor maximo negativo o 0 lanza excepción")
    public void ClubDeportivoAltoRendimiento_TresParametrosYValorMaximoNegativo_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new ClubDeportivoAltoRendimiento("c1", 0, 2.0);
        });
    }

    @Test
    @DisplayName("Al crear un club de alto rendimiento con 4 parámetros y valores correctos se crea correctamente")
    public void ClubDeportivoAltoRendimiento_CuatroParametrosYValoresCorrecto_returnTrue() {
        try {
            ClubDeportivoAltoRendimiento c = new ClubDeportivoAltoRendimiento("c1", 10,  10, 10);
            assertTrue(null != c);
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al crear un club de alto rendimiento con 3 parámetros y valores correctos se crea correctamente")
    public void ClubDeportivoAltoRendimiento_TresParametrosYValoresCorrecto_returnTrue() {
        try {
            ClubDeportivoAltoRendimiento c = new ClubDeportivoAltoRendimiento("c1",  10, 10);
            assertTrue(null != c);
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al crear un club de alto rendimiento con maximo cero lanza excepción")
    public void ClubDeportivoAltoRendimiento_CuatroParametrosYValorMaximoCero_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new ClubDeportivoAltoRendimiento("c1",10,  0, 2.0);
        });
    }

    @Test
    @DisplayName("Al crear un club de alto rendimiento con tamaño cero lanza excepción")
    public void ClubDeportivoAltoRendimiento_CuatroParametrosYValorIncrementoCero_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new ClubDeportivoAltoRendimiento("c1",10,  10, 0);
        });
    }

    @Test
    @DisplayName("Al añadir una actividad a la que le faltan datos lanza excepción")
    public void anyadirActividad_faltanDatos_returnClubException() {
        String [] datos = {"d1", "d2", "3"};
        try {
            ClubDeportivoAltoRendimiento c = new ClubDeportivoAltoRendimiento("c1", 10,  10, 10);    
            assertThrows(ClubException.class, () -> c.anyadirActividad(datos));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al añadir una actividad con maximo mayor que permitido se establece al maximo permitido por grupo para el club")
    public void anyadirActividad_todoCorrecto_returnGrupo() {
        String [] datos = {"d1", "d2", "10", "3", "10"};
        String [] datos2 = {"d1", "d2", "5", "3", "10"};
        try {
            ClubDeportivoAltoRendimiento c = new ClubDeportivoAltoRendimiento("c1", 10,  5, 10);    
            ClubDeportivoAltoRendimiento c2 = new ClubDeportivoAltoRendimiento("c1", 10,  5, 10);    
            c.anyadirActividad(datos);
            c2.anyadirActividad(datos2);
            assertEquals(c.toString(), c2.toString());
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al añadir una actividad con valores no numericos salta excepcion")
    public void anyadirActividad_todoCorrecto_returnNumberFormatException() {
        String [] datos = {"d1", "d2", "10fjk", "3", "10"};
        try {
            ClubDeportivoAltoRendimiento c = new ClubDeportivoAltoRendimiento("c1", 10,  5, 10);    
            
            assertThrows(ClubException.class, () -> c.anyadirActividad(datos));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Los ingresos del club de alto rendimiento tienen sumado el incremento")
    public void ingresos_todoCorrecto_returnIngresosConIncremento() {
        String [] datos = {"d1", "d2", "10", "3", "10"};
        try {
            ClubDeportivoAltoRendimiento c = new ClubDeportivoAltoRendimiento("c1", 10,  5, 10);    
            c.anyadirActividad(datos);
            assertEquals(((30) + (30)*(0.1)), c.ingresos());
        } catch (ClubException e) {
            assertFalse(true);
        }
    }
}
