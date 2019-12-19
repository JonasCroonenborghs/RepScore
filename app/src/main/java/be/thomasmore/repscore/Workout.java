package be.thomasmore.repscore;


import java.io.Serializable;

public class Workout implements Serializable {
    private long id;
    private double weight;
    private String date;
    private long compoundId;

    private String coumpound;

    public Workout() {

    }

    public Workout(long id, double weight, String date, long compoundId) {
        this.id = id;
        this.weight = weight;
        this.date = date;
        this.compoundId = compoundId;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", weight=" + weight +
                ", date='" + date + '\'' +
                ", compoundId=" + compoundId +
                ", coumpound='" + coumpound + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(long compoundId) {
        this.compoundId = compoundId;
    }

    public String getCoumpound() {
        return coumpound;
    }

    public void setCoumpound(String coumpound) {
        this.coumpound = coumpound;
    }
}
