package infraestructure.acl;

import controllers.dto.DesignDTO;
import domain.Design;
import domain.Estado;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class DesignValidator {

    public static Either<List<String>, Design> validate(DesignDTO dto) {
        return Validation.combine(
          validateNombre(dto.getNombre()),
          validateAutor(dto.getAutor()),
          validatePrecio(dto.getPrecio()),
          validateFileName(dto.getFileName()),
          Validation.valid(dto.getOriginal()),
          Validation.valid(dto.getProcesado()),
          validateEstado(dto.getEstado()),
          validateFecha(dto.getFecha())
        ).ap((nombre, autor, precio, fileName, original, procesado, estado, fecha) ->
          DesignBuilder.build(
            dto.getId(),
            nombre,
            autor,
            precio,
            fileName,
            original,
            procesado,
            estado,
            fecha
          )
        ).toEither()
          .mapLeft(List::ofAll);
    }

    static Validation<String, String> validateNombre(String nombre) {
        return StringUtils.isNotEmpty(nombre) && nombre.length() <= 100 ? Validation.valid(nombre) : Validation.invalid("nombre");
    }

    static Validation<String, String> validateAutor(String autor) {
        return StringUtils.isEmpty(autor) || autor.length() <= 100 ? Validation.valid(autor) : Validation.invalid("autor");
    }

    static Validation<String, BigDecimal> validatePrecio(String precio) {
        return Try.of(() -> new BigDecimal(precio))
          .toEither("precio")
          .toValidation();
    }

    static Validation<String, String> validateFileName(String fileName) {
        return StringUtils.isNotEmpty(fileName) && fileName.length() <= 200 ? Validation.valid(fileName) : Validation.invalid("fileName");
    }

    static Validation<String, Estado> validateEstado(String estado) {
        Estado value = Estado.of(estado);
        return value != Estado.DESCONOCIDO ? Validation.valid(value) : Validation.invalid("estado");
    }

    static Validation<String, DateTime> validateFecha(String fecha) {
        return Try.of(() -> DateTime.parse(fecha))
          .toEither("fecha")
          .toValidation();
    }
}
