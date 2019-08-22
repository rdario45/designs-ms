package infraestructure.acl;

import domain.Design;
import domain.Estado;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DesignBuilder {

  public static Design build(String nombre,
                             String autor,
                             BigDecimal precio,
                             String fileName,
                             String original) {
    Path pathOriginal = Paths.get(original);
    return new Design(0, nombre, autor, precio, fileName, pathOriginal.toFile(), null, Estado.EN_PROCESO, DateTime.now());
  }
}