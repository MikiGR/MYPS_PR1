package clubdeportivo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoTest {
    ClubDeportivo cd;

    @BeforeEach
    void setup(){
        try{
            cd = new ClubDeportivo("CD_1");
        }catch(Exception e){};
    }

    @Test
    @DisplayName("Si creo un club deportivo sin el segundo parametro se creará correctamente.")
    void setup_noParameters(){
        try{
            cd = new ClubDeportivo("CD_1");
            assertTrue(cd!=null);
        }catch(Exception e){
            assertFalse(true);
        };
    }

    @Test
    @DisplayName("Si creo un club deportivo con tamaño de grupo negativo salta una excepcion")
    void setup_NegativeGroups_returnClubException(){
        assertThrows(ClubException.class,()->{
            new ClubDeportivo("CD_1",-1);
        });
       
    }

    @Test
    @DisplayName("Si anyado una actividad y no se envian todos los argumentos se lanza una excepcion")
    void anyadirActividad_ArgumentosInvalidos_returnException(){
        String[] args = {"","",""};
        assertThrows(ClubException.class,()->{
            cd.anyadirActividad(args);
        });
       
    }

    @Test
    @DisplayName("Si anyado una actividad y no se envian los argumentos correctos se lanza una excepcion")
    void anyadirActividad_numerosArgumentosInvalidos_returnException(){
        String[] args = {"Juan","Maritima","MANOLO","3","5.0"};
        assertThrows(ClubException.class,()->{
            cd.anyadirActividad(args);
        });
       
    }

    @Test
    @DisplayName("Si anyado una actividad y el numero de plazas es menor que 0 devuelve una excepcion")
    void anyadirActividad_plazasNegativas_returnException(){
        String[] args = {"Juan","Maritima","-1","3","5.0"};
        assertThrows(ClubException.class,()->{
            cd.anyadirActividad(args);
        });
       
    }

    @Test
    @DisplayName("Si anyado una actividad y el numero de matriculados es menor que 0 devuelve una excepcion")
    void anyadirActividad_matriculadosNegativo_returnException(){
        String[] args = {"Juan","Maritima","1","-3","5.0"};
        assertThrows(ClubException.class,()->{
            cd.anyadirActividad(args);
        });
       
    }

    @Test
    @DisplayName("Si anyado una actividad y se envian los argumentos correctos se crea correctamente.")
    void anyadirActividad_ArgumentosValidos_equals() throws ClubException{
        String[] args = {"Juan","Maritima","5","3","5.0"};
        cd.anyadirActividad(args);
        String salida = "CD_1 --> [ (Juan - Maritima - 5.0 euros - P:5 - M:3) ]";
       assertEquals(cd.toString(),salida);
    }

    @Test
    @DisplayName("Si anyado una actividad y se envia un grupo nulo se lanza una excepcion")
    void anyadirActividad_grupoNulo_returnException() throws ClubException{
        Grupo g = null;
        assertThrows(ClubException.class,()->{
            cd.anyadirActividad(g);
        });
    }

    @Test
    @DisplayName("Si anyado una actividad y se envia un grupo existente se actualizan las plazas")
    void anyadirActividad_grupoNuevo_equals() throws ClubException{
        String[] args = {"Juan","Maritima","5","3","5.0"};
        cd.anyadirActividad(args);

        Grupo g = new Grupo("Juan","Maritima",15,5,5.0);
        cd.anyadirActividad(g);

        String salida = "CD_1 --> [ (Juan - Maritima - 5.0 euros - P:15 - M:3) ]";
        assertEquals(cd.toString(),salida);
    }

    @Test
    @DisplayName("Si anyado una actividad y se envia un grupo nuevo se anyade la actividad a ese grupo")
    void anyadirActividad_grupoExistente_notEquals() throws ClubException{
        String beforeAdd = cd.toString();
        Grupo g = new Grupo("Juan","Maritima",15,5,5.0);
        cd.anyadirActividad(g);
        assertNotEquals(cd.toString(),beforeAdd);
      
    }

    @Test
    @DisplayName("Si la actividad no existe se devuelven 0 plazas libres")
    void plazasLibres_actividadNoExistente_equals() throws ClubException{
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan","Maritima",15,5,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),0);
    }

    @Test
    @DisplayName("Si la actividad existe se devuelven las plazas libres")
    void plazasLibres_actividadExistente_equals() throws ClubException{
        int nplazas = 15;
        int matriculados = 5;
        int plazasLibres = nplazas-matriculados;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),plazasLibres);
    }

    @Test
    @DisplayName("Si la actividad tiene 0 plazas libres se devuelve 0")
    void plazasLibres_actividadSinPlazas_equals() throws ClubException{
        int nplazas = 15;
        int matriculados = 15;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),0);
    }

    @Test
    @DisplayName("Si la actividad no tiene matriculados se devuelve el numero de plazas")
    void plazasLibres_actividadSinMatriculados_equals() throws ClubException{
        int nplazas = 15;
        int matriculados = 0;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),nplazas);
    }

}
