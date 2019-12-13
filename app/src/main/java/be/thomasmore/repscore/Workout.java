package be.thomasmore.repscore;


public class Workout {
    private long id;
    private String weight;
    private String date;
    private long compoundId;

    public Workout(){

    }

    public Workout(long id, String weight, String date, long compoundId) {
        this.id = id;
        this.weight = weight;
        this.date = date;
        this.compoundId = compoundId;
    }

    @Override
    public String toString() {
        return weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
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
}
