package infraestructure.acl;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.dto.DesignDTO;
import domain.Design;
import domain.Estado;
import infraestructure.repository.DesignRecord;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import play.Logger;
import play.libs.Json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
          Option.of(design.getProcesado()).map(File::getAbsolutePath).getOrNull(),
          design.getEstado().name(),
          design.getFecha().toString()
        );
    }

    public static JsonNode designToJsonDTO(Design design) {
        return Json.toJson(DesignMapper.designToDTO(design));
    }

    public static DesignRecord designToRecord(Design design) {
        Try<byte[]> original = getBytesFromFile(design.getOriginal());
        Try<byte[]> procesado = design.getProcesado() == null ?
          Try.failure(new Error()) :
          getBytesFromFile(design.getProcesado());

        return new DesignRecord(
          design.getId(),
          design.getNombre(),
          design.getAutor(),
          design.getPrecio(),
          design.getFileName(),
          original.getOrNull(),
          procesado.getOrNull(),
          design.getEstado().name(),
          new Timestamp(design.getFecha().getMillis())
        );
    }

    private static Try<byte[]> getBytesFromFile(File file) {
        return  Try.of(() -> IOUtils.toByteArray(new FileInputStream(file))
        ).onFailure(throwable -> Logger.warn("hey,", throwable));
    }

    public static Design recordToDesign(DesignRecord record) {
        Try<File> original = getFileFromBytes(record.getFileName(), record.getOriginal());
        Try<File> procesado = record.getProcesado() == null ?
          Try.failure(new Error()) :
          getFileFromBytes(record.getFileName(), record.getProcesado());
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
            File temp = File.createTempFile(fileName, ".tmp");
            FileOutputStream fos = new FileOutputStream(temp);
            if (bytes != null) {
                fos.write(bytes);
            }
            fos.close();
            return temp;
        }).onFailure(throwable -> Logger.error("error", throwable));
    }
}
