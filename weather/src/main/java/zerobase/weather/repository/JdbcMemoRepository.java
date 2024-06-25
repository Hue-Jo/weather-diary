package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //저장
    public Memo save(Memo memo) {
        String sql = "insert into memo values(?,?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    //조회
    private RowMapper<Memo> memoRowMapper() {
        //가져온 데이터는 ResultSet 형태로 들어옴
        return (rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        );
    }

    public List<Memo> findAll() {
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

            //혹시 모를 null 값 유의하라고 optional
    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }
}
