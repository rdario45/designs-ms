package infraestructure.repository;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(DesignMapperDAO.class)
public interface DesignDAO {

    @SqlQuery("SELECT * FROM designs")
    List<DesignRecord> listAll();

    @SqlQuery("SELECT * FROM designs WHERE id = :id")
    DesignRecord find(@Bind("id") int id);

    @SqlQuery("INSERT INTO designs " +
      "(nombre, " +
      "autor, " +
      "precio, " +
      "filename," +
      "original, " +
      "procesado," +
      "estado," +
      "fecha) " +
      "VALUES(" +
      ":r.nombre, " +
      ":r.autor, " +
      ":r.precio, " +
      ":r.fileName," +
      ":r.original," +
      ":r.procesado," +
      ":r.estado, " +
      ":r.fecha) RETURNING *")
    DesignRecord insert(@BindBean("r") DesignRecord record);

    @SqlQuery("UPDATE designs SET " +
      "nombre = :r.nombre, " +
      "autor = :r.autor, " +
      "precio = :r.precio, " +
      "filename = :r.fileName, " +
      "original = :r.original, " +
      "procesado = :r.procesado, " +
      "estado = :r.estado, " +
      "fecha = :r.fecha " +
      "WHERE id = :r.id RETURNING *")
    DesignRecord update(@BindBean("r") DesignRecord record);

}
