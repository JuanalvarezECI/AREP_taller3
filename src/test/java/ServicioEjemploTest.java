import edu.escuelaing.arem.ASE.app.ServicioEjemplo;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServicioEjemploTest {

    @Test
    public void testHello() {
        assertEquals("hello", ServicioEjemplo.hello());
    }

    @Test
    public void testMoto() {
        assertEquals("susuki_gsxr_600", ServicioEjemplo.moto());
    }

    @Test
    public void testNombre() {
        assertEquals("juan_jose", ServicioEjemplo.nombre());
    }

    @Test
    public void testDocumento() {
        assertEquals("1000241337", ServicioEjemplo.documento());
    }

    @Test
    public void testPi() {
        assertEquals(String.valueOf(Math.PI), ServicioEjemplo.pi());
    }

    @Test
    public void testIndex() {
        assertEquals("Greetings from Spring Boot!", ServicioEjemplo.index());
    }
}