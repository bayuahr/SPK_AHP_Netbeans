package application.models;

public class ResultModel {
    private Integer id;
    private Integer evaluationId;
    private Integer alternativeId;
    private Double score;
    private Integer rank;

    private AlternativeModel alternative;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getEvaluationId() { return evaluationId; }
    public void setEvaluationId(Integer evaluationId) { this.evaluationId = evaluationId; }

    public Integer getAlternativeId() { return alternativeId; }
    public void setAlternativeId(Integer alternativeId) { this.alternativeId = alternativeId; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public AlternativeModel getAlternative() { return alternative; }
    public void setAlternative(AlternativeModel alternative) { this.alternative = alternative; }
}
