package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   public void add(Car car, long id) {
      sessionFactory.getCurrentSession().save(car);
      sessionFactory.getCurrentSession().get(User.class, id).setCar(car);
   }

   public User get(String model, int series) {
      TypedQuery<User> userQuery = sessionFactory.getCurrentSession().
              createQuery("from User where car.model= :model and car.series= :series");
      userQuery.setParameter("model", model);
      userQuery.setParameter("series", series);
      return (User) userQuery.getSingleResult();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

}
