/*
@author Miguel Galdeano Rodríguez
@author Pablo León Vazquez
*/
package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GrupoTest {
    
    @Test
    @DisplayName("Al crear un grupo con el numero de plazas negativo o igual a cero lanza una excepción")
    public void Grupo_numeroPlazasNegativo_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new Grupo("c1", "d1", 0, 0, 2.0);
        });
    }

    @Test
    @DisplayName("Al crear un grupo con el numero de matriculados negativo lanza una excepción")
    public void Grupo_numeroMatriculadosNegativo_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new Grupo("c1", "d1", 10, -1, 2.0);
        });
    }

    @Test
    @DisplayName("Al crear un grupo con la tarifa negativa o igual a cero lanza una excepción")
    public void Grupo_TarifaNegativo_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new Grupo("c1", "d1", 10, 0, 0);
        });
    }

    @Test
    @DisplayName("Al crear un grupo con mas matriculados que plazas lanza una excepción")
    public void Grupo_masMatriculadosQuePlazas_returnClubException() {
        assertThrows(ClubException.class, () ->  {
            new Grupo("c1", "d1", 5, 10, 2.0);
        });
    }

    @Test
    @DisplayName("Al crear un grupo con valores correctos se crea correctamente")
    public void Grupo_valoresCorrectos_returnTrue() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertEquals(g.getCodigo(), "c1");
            assertEquals(g.getActividad(), "d1");
            assertEquals(g.getPlazas(), 10);
            assertEquals(g.getMatriculados(), 5);
            assertEquals(g.getTarifa(), 2.0);
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al crear un grupo si hay menos matriculados que plazas sobran plaazas libres")
    public void plazasLibres_menosMatriculadosQuePlazas_returnTrue() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertEquals(g.plazasLibres(), 5);
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al actualizar las plazas de un grupo si es negativo o igual a cero lanza excepcion")
    public void actualizarPlazas_valorNegativoOCero_returnClubException() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertThrows(ClubException.class, () -> g.actualizarPlazas(0));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al actualizar las plazas de un grupo menor que el numero de matriculados lanza excepcion")
    public void actualizarPlazas_valorMenorQueMatriculados_returnClubException() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertThrows(ClubException.class, () -> g.actualizarPlazas(4));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al actualizar las plazas de un grupo si es mayor que el numero de matriculados se actualiza correctamente")
    public void actualizarPlazas_valorMayorQueMatriculados_returnTrue() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            g.actualizarPlazas(15);
            assertEquals(g.getPlazas(), 15);
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al matricular en un grupo si no hay plazas libres lanza una excepción")
    public void matricular_noHayPlazasLibres_returnClubException() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertThrows(ClubException.class, () -> g.matricular(6));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al matricular en un grupo si intento matricular 0 personas lanza una excepción")
    public void matricular_ceroPersonas_returnClubException() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertThrows(ClubException.class, () -> g.matricular(0));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al matricular en un grupo si intento matricular menos personas de las plazas disponibles se matriculan correctamente")
    public void matricular_numeroPersonasCorrecto_returnTrue() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            g.matricular(3);
            assertEquals(g.getMatriculados(), 8);
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al crear dos grupos diferentes con los mismos valores son iguales")
    public void equals_gruposIguales_returnTrue() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            Grupo g2 = new Grupo("c1", "d1", 10, 5, 2.0);
            assertTrue(g.equals(g2));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al comparar dos grupos si uno no es un grupo devuelve false")
    public void equals_noGrupo_returnFalse() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            assertFalse(g.equals(new Object()));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al comparar dos grupos si uno tiene mismo codigo pero diferente actividad al otro devuelve false")
    public void equals_gruposNoIguales_returnFalse() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            Grupo g2 = new Grupo("c1", "d2", 10, 5, 2.0);
            assertFalse(g.equals(g2));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Al comparar dos grupos si uno no tiene mismo codigo pero misma actividad al otro devuelve false")
    public void equals_gruposNoIguales2_returnFalse() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            Grupo g2 = new Grupo("c2", "d1", 10, 5, 2.0);
            assertFalse(g.equals(g2));
        } catch (ClubException e) {
            assertFalse(true);
        }
    }

    @Test
    @DisplayName("Si tengo dos grupos diferentes el hashcode es diferente")
    public void hashCode_gruposDiferentes_returnFalse() {
        try {
            Grupo g = new Grupo("c1", "d1", 10, 5, 2.0);
            Grupo g2 = new Grupo("c2", "d2", 10, 5, 2.0);
            assertFalse(g.hashCode() == g2.hashCode());
        } catch (ClubException e) {
            assertFalse(true);
        }
    }
}
