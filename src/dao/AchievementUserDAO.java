package dao;

import entity.Achievement;
import entity.AchievementUser;
import entity.AchievementUserPK;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Donggu on 2016/12/5.
 */
public class AchievementUserDAO extends BaseDAO<AchievementUser> {
    private static AchievementUserDAO instance = new AchievementUserDAO();
    private AchievementUserDAO(){}

    public static AchievementUserDAO getInstance(){
        return instance;
    }

    public void delete(AchievementUserPK pk) {
        super.delete(AchievementUser.class, pk);
    }

    public AchievementUser findById(AchievementUserPK pk) {
        return super.findById(AchievementUser.class, pk);
    }

    /**
     * get Achievements got by user, id only.
     * @param username username
     * @return ArrayList of Integer.
     */
    public ArrayList<Integer> findByUser(String username){
        Session session = BaseDAO.getSession();
        Query query = session.createQuery("from AchievementUser where user = :username");
        query.setParameter("username", username);
        List list = query.getResultList();
        session.close();

        ArrayList<Integer> result = new ArrayList<>();
        for(Iterator it = list.iterator();it.hasNext();){
            AchievementUser a = (AchievementUser)it.next();
            result.add(a.getAchievement());
        }
        return result;
    }

    /**
     * get all Achievements which the user has got, in time order.
     * @param username username
     * @return ArrayList of AchievementUser, sorted by time (latest first)
     */
    public ArrayList<AchievementUser> getUserAchievements(String username){
        Session session = BaseDAO.getSession();
        Query query = session.createQuery("from AchievementUser where user = :username");
        query.setParameter("username", username);
        List list = query.getResultList();
        ArrayList<AchievementUser> result = new ArrayList<>();
        for(Iterator it = list.iterator();it.hasNext();){
            AchievementUser a = (AchievementUser) it.next();
            result.add(a);
        }
        result.sort(new AUComparator());
        return result;
    }

    /**
     * AchievementUser comparator, compare by time.
     */
    static class AUComparator implements Comparator<AchievementUser>{
        @Override
        public int compare(AchievementUser o1, AchievementUser o2) {
            return o2.compareTo(o1);

        }
    }
}
