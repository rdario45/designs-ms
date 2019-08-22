package infraestructure.acl;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.dto.DesignDTO;
import domain.Design;
import domain.Estado;
import infraestructure.repository.DesignRecord;
import io.vavr.control.Try;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import play.libs.Json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;

import java.sql.Timestamp;

public class DesignMapper {

    public static DesignDTO designToDTO(Design design) {
        return new DesignDTO(
          design.getId(),
          design.getNombre(),
          design.getAutor(),
          design.getPrecio().toString(),
          design.getFileName(),
          design.getOriginal().getAbsolutePath(),
          null,
          design.getEstado().name(),
          design.getFecha().toString()
        );
    }

    public static JsonNode designToJsonDTO(Design design) {
        return Json.toJson(DesignMapper.designToDTO(design));
    }

    public static DesignRecord designToRecord(Design design) {
        Try<byte[]> original = getBytesFromFile(design.getOriginal());
        Try<byte[]> procesado = getBytesFromFile(design.getProcesado());
        return new DesignRecord(
          design.getId(),
          design.getNombre(),
          design.getAutor(),
          design.getPrecio(),
          design.getFileName(),
          original.getOrNull(),
          null,
          design.getEstado().name(),
          new Timestamp(design.getFecha().getMillis())
        );
    }

    private static Try<byte[]> getBytesFromFile(File file) {
        return Try.of(() -> IOUtils.toByteArray(new FileInputStream(file)));
    }

    public static Design recordToDesign(DesignRecord record) {
        Try<File> original = getFileFromBytes(record.getFileName(), record.getOriginal());
        Try<File> procesado = getFileFromBytes(record.getFileName(), record.getProcesado());
        return new Design(
          record.getId(),
          record.getNombre(),
          record.getAutor(),
          record.getPrecio(),
          record.getFileName(),
          original.getOrNull(),
          procesado.getOrNull(),
          Estado.of(record.getEstado()),
          new DateTime(record.getFecha(), DateTimeZone.forID("America/Bogota"))
        );
    }

    private static Try<File> getFileFromBytes(String fileName, byte[] bytes) {
        return Try.of(() -> {
            File file = new File(fileName); // TODO check side effect.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            return file;
        });
    }
}
