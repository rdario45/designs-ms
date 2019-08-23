package infraestructure.chonos;

import akka.actor.ActorSystem;
import infraestructure.repository.DesignRepository;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class ChronProcesarArchivos {

    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;
    private final DesignRepository repository;

    @Inject
    public ChronProcesarArchivos(ActorSystem actorSystem,
                                 ExecutionContext executionContext,
                                 DesignRepository repository) {
        this.actorSystem = actorSystem;
        this.executionContext = executionContext;
        this.repository = repository;
        this.initialize();
    }

    private void initialize() {
        actorSystem.scheduler().schedule(
          Duration.create(10, TimeUnit.SECONDS),
          Duration.create(1, TimeUnit.DAYS),
          () -> new TaskProcesarArchivos(this.repository).execute(),
          executionContext
        );
    }

}
