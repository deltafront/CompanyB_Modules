package companyB.jdbc.test;

import companyB.jdbc.JdbcUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
@Test
public class TestBase
{
    protected JdbcUtils jdbcUtils;
    public void before()
    {
        this.jdbcUtils = new JdbcUtils("sa", "", "jdbc:h2:~/test.db", "org.h2.Driver");
    }
    @AfterMethod
    public void after()
    {
        final List<String> dropStatements = new LinkedList<>();
        dropStatements.add("DROP TABLE IF EXISTS TEST;");
        dropStatements.forEach(this::runUpdate);
    }
    static void validateEquality(Object expected, Object actual)
    {
        assertThat(actual,is(equalTo(expected)));
    }
    static void validateNotNull(Object instance)
    {
        assertThat(instance,is(not(nullValue())));
    }
    static void validateTrue(Boolean condition)
    {
        assertThat(condition,is(true));
    }

    void runUpdate(String sql)
    {
        final Integer result = jdbcUtils.update(sql);
        validateNotNull(result);
        validateTrue(result > -1L);
    }
}
