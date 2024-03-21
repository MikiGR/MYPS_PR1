/*
@author Miguel Galdeano Rodríguez
@author Pablo León Vazquez
*/
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
    void anyadirActividad_ArgumentosValidos_returnTrue() throws ClubException{
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
    void anyadirActividad_grupoNuevo_returnTrue() throws ClubException{
        String[] args = {"Juan","Maritima","5","3","5.0"};
        cd.anyadirActividad(args);

        Grupo g = new Grupo("Juan","Maritima",15,5,5.0);
        cd.anyadirActividad(g);

        String salida = "CD_1 --> [ (Juan - Maritima - 5.0 euros - P:15 - M:3) ]";
        assertEquals(cd.toString(),salida);
    }

    @Test
    @DisplayName("Si anyado una actividad y se envia un grupo nuevo se anyade la actividad a ese grupo")
    void anyadirActividad_grupoExistente_returnFalse() throws ClubException{
        String beforeAdd = cd.toString();
        Grupo g = new Grupo("Juan","Maritima",15,5,5.0);
        cd.anyadirActividad(g);
        assertNotEquals(cd.toString(),beforeAdd);
      
    }

    @Test
    @DisplayName("Si la actividad no existe se devuelven 0 plazas libres")
    void plazasLibres_actividadNoExistente_returnTrue() throws ClubException{
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan","Maritima",15,5,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),0);
    }

    @Test
    @DisplayName("Si la actividad existe se devuelven las plazas libres")
    void plazasLibres_actividadExistente_returnTrue() throws ClubException{
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
    void plazasLibres_actividadSinPlazas_returnTrue() throws ClubException{
        int nplazas = 15;
        int matriculados = 15;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),0);
    }

    @Test
    @DisplayName("Si la actividad no tiene matriculados se devuelve el numero de plazas")
    void plazasLibres_actividadSinMatriculados_returnTrue() throws ClubException{
        int nplazas = 15;
        int matriculados = 0;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertEquals(cd.plazasLibres(actividad),nplazas);
    }

    @Test
    @DisplayName("Si la actividad no tiene plazas suficientes devuelve una excepcion.")
    void matricular_plazasSolicitadasNoDisponibles_exception() throws ClubException{
        int nplazas = 15;
        int matriculados = 10;
        int futurosAlumnos = 10;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertThrows(ClubException.class,()->{
            cd.matricular(actividad,futurosAlumnos);});
    }

    @Test
    @DisplayName("Si la actividad tiene plazas suficientes se matriculan correctamente.")
    void matricular_plazasSolicitadasDisponibles_returnTrue() throws ClubException{
        int nplazas = 25;
        int matriculados = 10;
        int futurosAlumnos = 5;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        cd.matricular(actividad, futurosAlumnos);
        assertEquals(nplazas-matriculados-futurosAlumnos,cd.plazasLibres(actividad));
    }

    @Test
    @DisplayName("Si la actividad tiene plazas suficientes se matriculan correctamente.")
    void matricular_plazasSolicitadasIgualesALasDisponibles_returnTrue() throws ClubException{
        int nplazas = 25;
        int matriculados = 10;
        int futurosAlumnos = 15;
        String actividad = "Pesquera";
        String actividad2 = "Natacion";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        Grupo g2 = new Grupo("Juan",actividad2,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        cd.anyadirActividad(g2);
        cd.matricular(actividad2, futurosAlumnos);
        assertEquals(nplazas-matriculados-futurosAlumnos,cd.plazasLibres(actividad2));
    }


    @Test
    @DisplayName("Si el numero de personas indicadas son negativas no se hace nada")
    void matricular_nPersonasNegativoNoSeHaceNada_returnTrue() throws ClubException{
        int nplazas = 25;
        int matriculados = 10;
        int futurosAlumnos = -5;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        cd.matricular(actividad, futurosAlumnos);
        assertEquals(nplazas-matriculados,cd.plazasLibres(actividad));
    }

    @Test
    @DisplayName("Si la actividad no existe se lanza una excepcion")
    void matricular_actividadNoExistente_returnTrue() throws ClubException{
        int nplazas = 25;
        int matriculados = 10;
        int futurosAlumnos = 5;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        assertThrows(ClubException.class,()->{cd.matricular("Running", futurosAlumnos);});
    }

    @Test
    @DisplayName("Se calculan correctamente los ingresos en un clubDeportivo de 2 grupos")
    void ingresos_ingresosConDosGrupos_returnTrue() throws ClubException{
        int nplazasG1 = 25;
        int matriculadosG1 = 10;
        int nplazasG2 = 30;
        int matriculadosG2 = 17;
        double tarifaG1 = 5.0;
        double tarifaG2 = 8.5;
        String actividad = "Pesquera";
        String actividad2 = "Montaña";
        Grupo g = new Grupo("Juan",actividad,nplazasG1,matriculadosG1,tarifaG1);
        cd.anyadirActividad(g);
        Grupo g2 = new Grupo("Manuel",actividad2,nplazasG2,matriculadosG2,tarifaG2);
        cd.anyadirActividad(g2);
        double cantidadEsperada = 0;
        cantidadEsperada += tarifaG1 * matriculadosG1;
        cantidadEsperada += tarifaG2 * matriculadosG2;
        assertEquals(cantidadEsperada,cd.ingresos());
    }
    @Test
    @DisplayName("Se calculan correctamente los ingresos en un clubDeportivo de 0 grupos")
    void ingresos_ingresosSinGrupos_returnTrue() throws ClubException{
        assertEquals(cd.ingresos(),0);
    }

    @Test
    @DisplayName("Se realiza correctamente el toString de 1 grupo")
    void toString_CorrectamenteConUnGrupo_returnTrue() throws ClubException{
        int nplazas = 15;
        int matriculados = 10;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        String salida = "CD_1 --> [ (Juan - Pesquera - 5.0 euros - P:15 - M:10) ]";
        assertEquals(cd.toString(),salida);
    }

    @Test
    @DisplayName("Se realiza correctamente el toString de 2 grupos")
    void toString_CorrectamenteConDosrupos_returnTrue() throws ClubException{
        int nplazas = 15;
        int matriculados = 10;
        String actividad = "Pesquera";
        Grupo g = new Grupo("Juan",actividad,nplazas,matriculados,5.0);
        cd.anyadirActividad(g);
        String[] args = {"Jose","Maritima","20","10","7.0"};
        cd.anyadirActividad(args);
        String salida = "CD_1 --> [ (Juan - Pesquera - 5.0 euros - P:15 - M:10), (Jose - Maritima - 7.0 euros - P:20 - M:10) ]";
        assertEquals(cd.toString(),salida);
    }

    @Test
    @DisplayName("Se realiza correctamente el toString de 0 grupos")
    void toString_CorrectamenteCeroGrupos_returnTrue() throws ClubException{
        String salida = "CD_1 --> [  ]";
        assertEquals(cd.toString(),salida);
    }

}
