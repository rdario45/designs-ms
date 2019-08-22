package infraestructure.repository;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DesignMapperDAO implements ResultSetMapper<DesignRecord> {
    @Override
    public DesignRecord map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new DesignRecord(
          r.getInt("id"),
          r.getString("nombre"),
          r.getString("autor"),
          r.getBigDecimal("precio"),
          r.getString("filename"),
          r.getBytes("original"),
          r.getBytes("procesado"),
          r.getString("estado"),
          r.getTimestamp("fecha")
        );
    }
}
