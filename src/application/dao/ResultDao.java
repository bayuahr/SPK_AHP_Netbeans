package application.dao;

import application.models.ResultModel;
import java.util.List;

public interface ResultDao {
    int insertOne(ResultModel result);
    List<ResultModel> findByEvaluationId();
    int deleteByEvaluationId();
}
