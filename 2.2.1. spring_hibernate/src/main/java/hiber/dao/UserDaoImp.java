package hiber.dao;

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

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model1, int i) {
      try {
         return sessionFactory.getCurrentSession()
                 .createQuery("from User where car.model = :model1 and car.series = :i", User.class)
                 .setParameter("model1", model1).setParameter("i", i)
                 .getResultList().get(0);
      } catch (IndexOutOfBoundsException e) {
         System.out.println("User not found");
         return null;
      }
   }

   @Override
   public void clearTable() {
      sessionFactory.getCurrentSession().createQuery("delete from User").executeUpdate();
      sessionFactory.getCurrentSession().createQuery("delete from Car ").executeUpdate();
   }
}
