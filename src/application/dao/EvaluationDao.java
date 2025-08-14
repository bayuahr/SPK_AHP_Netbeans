package application.dao;

import application.models.EvaluationModel;
import java.util.List;

public interface EvaluationDao {

    int insertOne(EvaluationModel selection);

    List<EvaluationModel> findAll();

    int delete(int id);
}
