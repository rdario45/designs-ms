package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.dto.DesignDTO;
import infraestructure.acl.DesignMapper;
import infraestructure.acl.DesignValidator;
import infraestructure.repository.DesignRepository;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.joda.time.DateTime;
import play.Logger;
import play.libs.Files.TemporaryFile;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.nio.file.Path;
import java.nio.file.Paths;

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

        String fileName = picture.getFilename();
        long fileSize = picture.getFileSize();
        String contentType = picture.getContentType();
        TemporaryFile file = picture.getRef();
        Logger.info("POST /create :" + picture.getFilename());
        Logger.info("fileSize :" + fileSize);
        Logger.info("contentType :" + contentType);
//        return ok();
        return createDesignDTO(fileName, file)
          .flatMap(DesignValidator::validate)
          .mapLeft(this::getValidationErrorMessage)
          .mapLeft(Results::badRequest)
          .map(design -> repository.save(design))
          .flatMap(future -> future.onFailure(throwable -> Logger.error("Error creating!", throwable))
            .toEither(internalServerError(getErrorMessage("Error creating!")))
          ).map(DesignMapper::designToJsonDTO)
          .fold(result -> result, Results::ok);
    }


    private Either<List<String>, DesignDTO> createDesignDTO(String fileName, TemporaryFile file) {
        return Try.of(() -> {
            Path path = file.copyTo(Paths.get(fileName), true);
            return new DesignDTO(
              0,
              "paisaje",
              "rdario",
              "20000",
              fileName,
              path.toString(),
              null,
              "EN_PROCESO",
              DateTime.now().toString()
            );
        }).onFailure(throwable -> Logger.error("invalid", throwable))
          .toEither(List.of("invalid"));
    }

    private ObjectNode getValidationErrorMessage(List<String> errors) {
        return Json.newObject()
          .put("message", "Validation errors!")
          .put("fields", String.join(", ", errors));
    }

    private ObjectNode getNotFoundMessage(int index) {
        return Json.newObject().put("message", "id " + index + " not found");
    }

    private ObjectNode getErrorMessage(String message) {
        return Json.newObject().put("message", message);
    }

}
