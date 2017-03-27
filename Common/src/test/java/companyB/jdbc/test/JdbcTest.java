package companyB.jdbc.test;

import companyB.jdbc.ResultSetTransformer;
import companyB.jdbc.ResultSetTransformerDefaultImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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

    @DataProvider(name = "default")
    public static Object[][]data()
    {
        final BiConsumer<List<TestObj>,Long> verifyTestObj = (list,id)->
        {
            final TestObj result = list.get(0);
            final Long fromDb = result.id;
            validateNotNull(fromDb);
            validateEquality(id, fromDb);
        };
        final BiConsumer<List<Map<String,Object>>,Long> verifyMap = (list, id)->
        {
            final Map<String,Object> result = list.get(0);
            validateEquality(2,result.size());
            validateTrue(result.containsKey("ID"));
            final Object idFromDb = result.get("ID");
            validateNotNull(idFromDb);
            validateEquality(id, Long.valueOf(String.valueOf(idFromDb)));
        };
        final ResultSetTransformer<TestObj>resultSetTransformer = resultSet -> {
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
        return new Object[][]
                {
                        {resultSetTransformer, verifyTestObj},
                        {new ResultSetTransformerDefaultImpl(),verifyMap}
                };
    }

    @DataProvider(name = "combinations")
    public static Object[][] combinations()
    {
        return new Object[][]
                {
                        {true,true},
                        {true,false},
                        {false,true},
                        {false,false}
                };
    }

    @BeforeMethod
    public void before()
    {
        super.before();
        final List<String> createStatements = new LinkedList<>();
        createStatements.add("DROP TABLE IF EXISTS TEST;" +
                "CREATE TABLE TEST(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(250) NOT NULL UNIQUE);");
        createStatements.forEach(this::runUpdate);
    }


    @Test(dataProvider = "combinations")
    public void insert(Boolean successExpected, Boolean usePreparedStatement)
    {
        System.out.println(String.format("Success Expected: %b \tUse Prepared Statement: %b",successExpected,usePreparedStatement));
        if(successExpected) doInsert(successExpected,usePreparedStatement);
        else
        {
            doInsert(true,usePreparedStatement);
            doInsert(false,usePreparedStatement);
        }
    }


    @Test(dataProvider = "combinations")
    public void update(Boolean runFailedCate, Boolean usePreparedStatement)
    {
        System.out.println(String.format("Executng failed case: %b \tUse Prepared Statement: %b",runFailedCate,usePreparedStatement));
        doUpdate(runFailedCate,usePreparedStatement);
    }
    @Test(dataProvider = "combinations")
    public void delete(Boolean successExpected, Boolean usePreparedStatement)
    {
        System.out.println(String.format("Success Expected: %b \tUse Prepared Statement: %b",successExpected,usePreparedStatement));
        doDelete(successExpected,usePreparedStatement);
    }


    @Test(dataProvider = "default")
    public<T> void querySqlHappyPath(ResultSetTransformer<T>resultSetTransformer, BiConsumer<List<T>,Long>biConsumer)
    {
        doQuery(SUCCESS_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT,resultSetTransformer,biConsumer);
    }
    @Test(dataProvider = "default")
    public<T> void queryPreparedStatementHappyPath(ResultSetTransformer<T>resultSetTransformer, BiConsumer<List<T>,Long>biConsumer)
    {
        doQuery(SUCCESS_EXPECTED, USE_PREPARED_STATEMENT,resultSetTransformer,biConsumer);
    }
    @Test(dataProvider = "default")
    public<T> void querySqlFailure(ResultSetTransformer<T>resultSetTransformer, BiConsumer<List<T>,Long>biConsumer)
    {
        doQuery(SUCCESS_NOT_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT,resultSetTransformer,biConsumer);
    }
    @Test(dataProvider = "default")
    public<T> void queryPreparedStatementFailure(ResultSetTransformer<T>resultSetTransformer, BiConsumer<List<T>,Long>biConsumer)
    {
        doQuery(SUCCESS_NOT_EXPECTED, USE_PREPARED_STATEMENT,resultSetTransformer,biConsumer);
    }

    public void deleteSqlHappyPath()
    {
        doDelete(SUCCESS_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void deletePreparedStatementHappyPath()
    {
        doDelete(SUCCESS_EXPECTED, USE_PREPARED_STATEMENT);
    }

    public void deleteSqlFailure()
    {
        doDelete(SUCCESS_NOT_EXPECTED, DO_NOT_USE_PREPARED_STATEMENT);
    }

    public void deletePreparedStatementFailure()
    {
        doDelete(SUCCESS_NOT_EXPECTED, USE_PREPARED_STATEMENT);
    }

    private void doDelete(Boolean runFailedCase, Boolean usePreparedStatement)
    {
        final Long id = doInsert(true,false);
        final String sql = usePreparedStatement ?
                "DELETE FROM TEST WHERE ID=?" :
                String.format("DELETE FROM TEST WHERE ID=%s",id);
        final Object[]delete_values = usePreparedStatement ?
                new Object[]{id} :
                new Object[0];
        Integer deleted = usePreparedStatement ?
                jdbcUtils.delete(sql,delete_values) :
                jdbcUtils.delete(sql);
        validateEquality(1,deleted);
        if(runFailedCase)
        {
            deleted = usePreparedStatement ?
                    jdbcUtils.delete(sql,delete_values) :
                    jdbcUtils.delete(sql);
            validateEquality(0,deleted);
        }
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
        final Integer updated = usePreparedStatement ?
                jdbcUtils.update(update_sql,update_values) :
                jdbcUtils.update(update_sql);
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
        final String sql = (usePreparedStatement) ?
                "INSERT INTO TEST(NAME) VALUES(?)" :
                "INSERT INTO TEST(NAME) VALUES('FOO')";
        final Long id = (usePreparedStatement) ?
                jdbcUtils.insert(sql, "FOO") :
                jdbcUtils.insert(sql);
        if (successExpected)
            validateTrue(id > -1L);
        else
            validateTrue(id.equals(-1L));
        return id;
    }

    private<T> void doQuery(Boolean successExpected, Boolean usePreparedStatement,ResultSetTransformer<T>resultSetTransformer,BiConsumer<List<T>,Long>consumer)
    {
        Long id = doInsert(true, usePreparedStatement);
        validateTrue(id > -1L);
        final String tableName = (successExpected) ?
                "TEST" : "FOO";
        final String sql = (usePreparedStatement) ?
                "SELECT NAME, ID FROM {} WHERE NAME=?" :
                "SELECT NAME, ID FROM {} WHERE NAME='FOO'";
        final List<T> results = (usePreparedStatement) ?
                jdbcUtils.query(sql.replace("{}",tableName), resultSetTransformer, "FOO") :
                jdbcUtils.query(sql.replace("{}",tableName), resultSetTransformer);
        if (successExpected)
        {
            validateTrue(results.size() == 1);
            consumer.accept(results,id);
        }
        else
        {
            validateTrue(results.isEmpty());
        }
    }
}
