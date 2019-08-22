package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.dto.DesignDTO;
import infraestructure.acl.DesignMapper;
import infraestructure.acl.DesignValidator;
import infraestructure.repository.DesignRepository;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import play.Logger;
import play.libs.Files.TemporaryFile;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static play.mvc.Http.Context.Implicit.request;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

public class DesignsController {

    private DesignRepository repository;

    @Inject
    public DesignsController(DesignRepository repository) {
        this.repository = repository;
    }

    public Result index() {
        return ok(Json.toJson(repository.listAll().map(DesignMapper::designToDTO)));
    }

    public Result download(int index) {
        return repository.find(index)
          .map(DesignMapper::designToJsonDTO)
          .toEither(getNotFoundMessage(index))
          .fold(Results::notFound, Results::ok);
    }

        public Result create() {
            Http.MultipartFormData<TemporaryFile> body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<TemporaryFile> picture = body.getFile("picture");
            Map<String, String[]> stringMap = body.asFormUrlEncoded();

            TemporaryFile file = picture.getRef();
            String fileName = picture.getFilename();
            String nombre = Option.of(stringMap.get("nombre")).map(List::of).map(List::peek).getOrNull();
            String autor = Option.of(stringMap.get("autor")).map(List::of).map(List::peek).getOrNull();
        String precio = Option.of(stringMap.get("precio")).map(List::of).map(List::peek).getOrNull();

        return createDesignDTO(file, fileName, nombre, autor, precio)
          .flatMap(DesignValidator::validate)
          .mapLeft(this::getValidationErrorMessage)
          .mapLeft(Results::badRequest)
          .map(design -> repository.save(design))
          .flatMap(future -> future.onFailure(throwable -> Logger.error("Error creating!", throwable))
            .toEither(internalServerError(getErrorMessage("Error creating!")))
          ).map(DesignMapper::designToJsonDTO)
          .fold(result -> result, Results::ok);
    }

    private Either<List<String>, DesignDTO> createDesignDTO(TemporaryFile file, String fileName, String nombre, String autor, String precio) {
        return Try.of(() -> {
            Path path = file.copyTo(Paths.get(fileName), true); // TODO check
            Logger.info(path.toString());
            return new DesignDTO( nombre, autor, precio, fileName, path.toString());
        }).onFailure(throwable -> Logger.error("invalid", throwable))
          .toEither(List.of("invalid"));
    }

    private ObjectNode getNotFoundMessage(int index) {
        return Json.newObject().put("message", "id " + index + " not found");
    }

    private ObjectNode getValidationErrorMessage(List<String> errors) {
        return Json.newObject()
          .put("message", "Validation errors!")
          .put("fields", String.join(", ", errors));
    }

    private ObjectNode getErrorMessage(String message) {
        return Json.newObject().put("message", message);
    }

}
