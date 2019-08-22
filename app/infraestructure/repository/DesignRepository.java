package infraestructure.repository;

import com.google.inject.Inject;
import domain.Design;
import infraestructure.acl.DesignMapper;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import org.skife.jdbi.v2.DBI;
import play.api.db.Database;

public class DesignRepository {

  private DBI db;

  @Inject
  public DesignRepository(Database db) {
    this.db = new DBI(db.dataSource());
  }

  public List<Design> listAll() {
    return List.ofAll(db.onDemand(DesignDAO.class).listAll())
      .map(DesignMapper::recordToDesign);
  }

  public Option<Design> find(int id) {
    return Option.of(db.onDemand(DesignDAO.class).find(id))
      .map(DesignMapper::recordToDesign);
  }

  public Future<Design> save(Design design) {
    DesignRecord record = DesignMapper.designToRecord(design);
    return Future.of(() -> db.onDemand(DesignDAO.class).insert(record))
      .map(DesignMapper::recordToDesign);
  }

  public Future<Option<Design>> update(Design design) {
    DesignRecord record = DesignMapper.designToRecord(design);
    return Future.of(() -> Option.of(db.onDemand(DesignDAO.class).update(record))
      .map(DesignMapper::recordToDesign));
  }

}
