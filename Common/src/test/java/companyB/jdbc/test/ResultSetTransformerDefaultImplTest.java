package companyB.jdbc.test;

import companyB.jdbc.JdbcUtils;
import companyB.jdbc.ResultSetTransformer;
import companyB.jdbc.ResultSetTransformerDefaultImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;


@Test
public class ResultSetTransformerDefaultImplTest extends TestBase
{
    private ResultSetTransformer<Map<String,Object>>resultSetTransformer;

    @BeforeMethod
    public void before()
    {
        super.before();
        resultSetTransformer = new ResultSetTransformerDefaultImpl();
        jdbcUtils = new JdbcUtils("sa", "", "jdbc:h2:~/test.db", "org.h2.Driver");
        final List<String> createStatements = new LinkedList<>();
        createStatements.add("DROP TABLE IF EXISTS TEST;" +
                "CREATE TABLE TEST(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(250) NOT NULL UNIQUE);");
        createStatements.forEach(this::runUpdate);
    }

    public void singleColumn()
    {
        runTest(1);
    }
    public void multipleColumns()
    {
        runTest(5);
    }

    private void runTest(Integer expectedRows)
    {
        final AtomicLong lastId = new AtomicLong(-1L);
        IntStream.range(0,expectedRows).forEach((value)->{
            final String sql = String.format("INSERT INTO TEST (NAME) VALUES('foo_%d')",value);
            final Long id = jdbcUtils.insert(sql);
            validateNotNull(id);
            validateTrue(id > lastId.get());
            lastId.set(id);
        });

        final List<Map<String,Object>>rows = jdbcUtils.query("SELECT * FROM TEST",resultSetTransformer);
        final AtomicInteger count = new AtomicInteger(0);
        validateEquality(expectedRows,rows.size());
        rows.forEach((row)->{
            validateTrue(row.containsKey("NAME"));
            validateEquality(String.format("foo_%d",count.getAndIncrement()),row.get("NAME"));
        });
    }
}
