package companyB.data_access;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by charlie on 1/24/15.
 */
@SuppressWarnings("unchecked")
public class GenericDataAccess<DataClass,IdClass extends Serializable>
{
    private static HibernateSessionFactory hibernateSessionFactory;
    private final Logger logger = Logger.getLogger(GenericDataAccess.class);
    private static Gson gson;
    private Session session;
    private Transaction transaction;
    private Class<DataClass>dataClass;

    public GenericDataAccess(Class<DataClass>dataClass)
    {
        this.dataClass = dataClass;
        gson = new Gson();
    }
    public IdClass save(DataClass data)
    {
        logger.info("In save.");
        start(true);
        logger.debug(String.format("Saving instance of %s\n%s", dataClass.getCanonicalName(), gson.toJson(data)));
        IdClass id  = (IdClass)session.save(data);
        end();
        logger.debug(String.format("Id = %s", String.valueOf(id)));
        return id;
    }

    public boolean delete(IdClass id)
    {
        logger.info("In delete.");
        start(true);
        boolean deleted = false;
        DataClass fromDb = (DataClass)session.get(dataClass, id);
        logger.debug(String.format("Deleting instance of %s with id %s.", dataClass.getCanonicalName(), String.valueOf(id)));
        if (fromDb != null)
        {
            logger.debug("Instance found. Deleting.");
            session.delete(fromDb);
            deleted = true;
        }
        end();
        logger.debug(String.format("Instance found and deleted? %b", deleted));
        return deleted;
    }
    public DataClass get(IdClass id)
    {
        logger.info("In get.");
        start(false);
        logger.debug(String.format("Attempting to fine instance of %s with id %s", dataClass.getCanonicalName(), String.valueOf(id)));
        DataClass fromDb = (DataClass)session.get(dataClass, id);
        if(fromDb !=null)
        {
            logger.debug(String.format("Found instance:\n%s", gson.toJson(fromDb)));
        }
        return fromDb;
    }
    public List<DataClass>listAll()
    {
        logger.info("In listAll.");
        start(false);
        List list = session.createCriteria(dataClass).list();
        List<DataClass>results = new LinkedList<>();
        for (Object object : list)
        {
            logger.debug(String.format("Adding instance of %s to list:\n%s", dataClass.getCanonicalName(), gson.toJson(object)));
            results.add((DataClass) object);
        }
        logger.debug(String.format("Returning %s instances of %s.", String.valueOf(results.size()),dataClass.getCanonicalName()));
        return results;
    }

    public List<DataClass>listByQuery(GdaQuery... queries)
    {
        logger.info("In listByQuery.");
        start(false);
        Criteria criteria = session.createCriteria(dataClass);
        for (GdaQuery query : queries)
        {
            logger.debug(String.format("Adding criterion:\n%s", gson.toJson(query)));
            switch (query.operator)
            {
                case eq:
                default:
                    criteria.add(Restrictions.eq(query.field, query.value));
                    break;
                case gt:
                    criteria.add(Restrictions.gt(query.field, query.value));
                    break;
                case gte:
                    criteria.add(Restrictions.ge(query.field, query.value));
                    break;
                case lt:
                    criteria.add(Restrictions.lt(query.field, query.value));
                    break;
                case lte:
                    criteria.add(Restrictions.le(query.field, query.value));
                    break;
            }
        }
        List list = criteria.list();
        List<DataClass>results = new LinkedList<>();
        for (Object object : list)
        {
            logger.debug(String.format("Adding instance of %s to list:\n%s", dataClass.getCanonicalName(), gson.toJson(object)));
            results.add((DataClass) object);
        }
        logger.debug(String.format("Returning %s instances of %s.", String.valueOf(results.size()),dataClass.getCanonicalName()));
        return results;
    }

    private void start(boolean startTransaction)
    {
        logger.trace(String.format("In start. Starting transaction? %s",startTransaction));
        session = hibernateSessionFactory.factory.getCurrentSession();
        transaction = session.getTransaction();
        transaction.begin();
    }
    private void end()
    {
        try
        {
            transaction.commit();
        }
        catch (Exception e)
        {
            transaction.rollback();
            logger.error(e.getMessage());
        }
        finally
        {
            session.close();
        }
    }


}
