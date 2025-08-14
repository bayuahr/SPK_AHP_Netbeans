package application.dao;

import application.models.AlternativeModel;
import java.util.List;

public interface AlternativeDao {
    int insertOne(AlternativeModel alternative);
    List<AlternativeModel> findAll();
    List<AlternativeModel> findByProductId(int productId);
    int update(AlternativeModel alternative);
    int delete(int id);
}
