package companyB.jdbc.test;

import companyB.jdbc.JdbcUtils;
import companyB.jdbc.ResultSetTransformer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@Test(groups = {"unit", "jdbc"})
public class JdbcTest extends TestBase
{
    private final Boolean SUCCESS_EXPECTED = true;
    private final Boolean SUCCESS_NOT_EXPECTED = false;
    private final Boolean USE_PREPARED_STATEMENT = true;
    private final Boolean DO_NOT_USE_PREPARED_STATEMENT = false;
    private final Boolean RUN_FAILED_CASE = true;
    private final Boolean DO_NOT_RUN_FAILED_CASE = false;

    protected JdbcUtils jdbcUtils;
    private ResultSetTransformer<TestObj> resultSetTransformer;

    @BeforeMethod
    public void before()
    {
        resultSetTransformer = resultSet -> {
            TestObj out = null;
            try
            {
                out = new TestObj();
                out.name = resultSet.getString("NAME");
                out.id = resultSet.getLong(("ID"));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            return out;
        };
        jdbcUtils = new JdbcUtils("sa", "", "jdbc:h2:~/test.db", "org.h2.Driver");
        final List<String> createStatements = new LinkedList<>();
        createStatements.add("DROP TABLE IF EXISTS TEST;" +
                "CREATE TABLE TEST(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(250) NOT NULL UNIQUE);");
        createStatements.forEach(this::runUpdate);
    }

    @AfterMethod
    public void after()
    {
        final List<String>dropStatements = new LinkedList<>();
        dropStatements.add("DROP TABLE IF EXISTS TEST;");
        dropStatements.forEach(this::runUpdate);
    }


    public void insertSqlHappyPath()
    {
        doInsert(SUCCESS_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void insertSqlFailure()
    {
        doInsert(SUCCESS_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
        doInsert(SUCCESS_NOT_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void insertPreparedStatement()
    {
        doInsert(SUCCESS_EXPECTED, USE_PREPARED_STATEMENT);
    }

    public void insertPreparedStatementFailure()
    {
        doInsert(SUCCESS_EXPECTED, USE_PREPARED_STATEMENT);
        doInsert(SUCCESS_NOT_EXPECTED, USE_PREPARED_STATEMENT);
    }

    public void updateSqlHappyPath()
    {
        doUpdate(DO_NOT_RUN_FAILED_CASE,DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void updateSqlFailure()
    {
        doUpdate(RUN_FAILED_CASE, DO_NOT_USE_PREPARED_STATEMENT);
    }
    public void updatePreparedStatementHappyPath()
    {
        doUpdate(DO_NOT_RUN_FAILED_CASE,USE_PREPARED_STATEMENT);
    }
    public void updatePreparedStatementFailure()
    {
        doUpdate(RUN_FAILED_CASE,USE_PREPARED_STATEMENT);
    }

    public void querySqlHappyPath()
    {
        doQuery(SUCCESS_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void queryPreparedStatementHappyPath()
    {
        doQuery(SUCCESS_EXPECTED, USE_PREPARED_STATEMENT);
    }
    public void querySqlFailure()
    {
        doQuery(SUCCESS_NOT_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void queryPreparedStatementFailure()
    {
        doQuery(SUCCESS_NOT_EXPECTED, USE_PREPARED_STATEMENT);
    }

    private void runUpdate(String sql)
    {
        final Integer result = jdbcUtils.update(sql);
        validateNotNull(result);
        validateTrue(result > -1L);
    }
    private void doUpdate(Boolean runFailedCase, Boolean usePreparedStatement)
    {
        //insert first row
        final Long id = doInsert(true, false);
        //insert second row
        final String re_update_sql = "INSERT INTO TEST(NAME) VALUES('BAR')";
        final Long new_id = jdbcUtils.insert(re_update_sql);
        assertTrue(new_id > id);
        //update first row
        final Object[] update_values = (usePreparedStatement) ?
                new Object[]{"BAZ",id} :
                new Object[0];
        final String update_sql = (usePreparedStatement) ?
                "UPDATE TEST SET NAME=? WHERE ID=?" :
                "UPDATE TEST SET NAME='BAZ' WHERE ID=" + id;
        final Integer updated = jdbcUtils.update(update_sql, update_values);
        validateTrue(updated > 0);
        if(runFailedCase)
        {
            //update first row to match the second
            final Object[] failed_update_values = (usePreparedStatement) ?
                    new Object[]{"BAR",id} :
                    new Object[0];
            final String failed_update_sql = (usePreparedStatement) ?
                    "UPDATE TEST SET NAME=? WHERE ID=?" :
                    "UPDATE TEST SET NAME='BAR' WHERE ID=" + id;
            final Integer failed_update_count = jdbcUtils.update(failed_update_sql,failed_update_values);
            validateTrue(failed_update_count.equals(-1));
        }
    }

    private Long doInsert(Boolean successExpected, Boolean usePreparedStatement)
    {
        String sql = (usePreparedStatement) ?
                "INSERT INTO TEST(NAME) VALUES(?)" :
                "INSERT INTO TEST(NAME) VALUES('FOO')";
        Long id = (usePreparedStatement) ?
                jdbcUtils.insert(sql, "FOO") :
                jdbcUtils.insert(sql);
        if (successExpected)
        {
            validateTrue(id > -1L);
        }
        else
        {
            validateTrue(id.equals(-1L));
        }
        return id;
    }

    private void doQuery(Boolean successExpected, Boolean usePreparedStatement)
    {
        Long id = doInsert(true, usePreparedStatement);
        validateTrue(id > -1L);
        final String tableName = (successExpected) ?
                "TEST" : "FOO";
        final String sql = (usePreparedStatement) ?
                "SELECT NAME, ID FROM {} WHERE NAME=?" :
                "SELECT NAME, ID FROM {} WHERE NAME='FOO'";
        final List<TestObj> results = (usePreparedStatement) ?
                jdbcUtils.query(sql.replace("{}",tableName), resultSetTransformer, "FOO") :
                jdbcUtils.query(sql.replace("{}",tableName), resultSetTransformer);
        if (successExpected)
        {
            validateTrue(results.size() == 1);
            final TestObj result = results.get(0);
            final Long fromDb = result.id;
            validateNotNull(fromDb);
            validateEquality(id, fromDb);
        }
        else
        {
            validateTrue(results.isEmpty());
        }
    }

    private class TestObj
    {
        String name;
        Long id;
    }

}
