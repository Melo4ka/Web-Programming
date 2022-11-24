package ru.meldren.weblab4.dao;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.meldren.weblab4.entity.User;
import ru.meldren.weblab4.util.TransactionUtil;

import java.util.List;

@Singleton
@Startup
@LocalBean
public class UserRepository implements IRepository<User> {

    @Override
    public void save(User dto) {
        TransactionUtil.init(manager -> manager.persist(dto));
    }

    @Override
    public void update(User dto) {
        TransactionUtil.init(manager -> manager.merge(dto));
    }

    @Override
    public List<User> find(String criterion, Object value) {
        return TransactionUtil.initWithCallback(manager -> {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);

            Root<User> user = cq.from(User.class);
            Predicate predicate = cb.equal(user.get(criterion), value);
            cq.where(predicate);

            TypedQuery<User> query = manager.createQuery(cq);
            return query.getResultList();
        });
    }

    @Override
    public User findByKey(Object value) {
        return find("username", value).stream().findFirst().orElse(null);
    }

}
