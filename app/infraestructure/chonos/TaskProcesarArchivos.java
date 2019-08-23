package infraestructure.chonos;

import domain.Design;
import domain.Estado;
import infraestructure.repository.DesignRepository;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;
import play.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TaskProcesarArchivos {

    private final DesignRepository repository;

    public TaskProcesarArchivos(DesignRepository repository) {
        this.repository = repository;
    }

    public void execute(){
        Logger.info("********** INICIA LA TAREA PROGRAMADA ************");
        List<Design> enProceso = repository.listAll().filter(design -> design.getEstado() == Estado.EN_PROCESO);
        List<Future<Option<Design>>> map = enProceso.map(this::procesarImagen).map(repository::update);
        Logger.info("**********  FINALIZA LA TAREA PROGRAMADA ********** ");
    }

    private Design procesarImagen(Design design) {
        return Try.of(() -> {
            final BufferedImage image = ImageIO.read(design.getOriginal());
            Graphics g = image.getGraphics();
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString(design.getAutor(), 100, 100);
            g.dispose();
            File output = File.createTempFile(design.getFileName(),".tmp");
            ImageIO.write(image, "jpeg", output);
            return output;
        }).onFailure(throwable -> Logger.error("Error procesando imagen", throwable))
          .map(design::asignarProcesado)
          .getOrNull();
    }
}
