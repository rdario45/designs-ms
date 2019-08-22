package infraestructure.acl;

import domain.Design;
import domain.Estado;
import org.joda.time.DateTime;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DesignBuilder {

  public static Design build(String nombre,
                             String autor,
                             BigDecimal precio,
                             String fileName,
                             File original,
                             File procesado,
                             Estado estado,
                             DateTime fecha) {
    return new Design(0, nombre, autor, precio, fileName, original, procesado, estado, fecha);
  }

  public static Design build(int id,
                             String nombre,
                             String autor,
                             BigDecimal precio,
                             String fileName,
                             String original,
                             String procesado,
                             Estado estado,
                             DateTime fecha) {
    Path pathOriginal = Paths.get(original);
    return new Design(id, nombre, autor, precio, fileName, pathOriginal.toFile(), null, estado, fecha);
  }
}