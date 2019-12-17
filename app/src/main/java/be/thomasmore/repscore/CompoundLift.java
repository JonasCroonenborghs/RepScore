package be.thomasmore.repscore;

public class CompoundLift {
    private long id;
    private String name;
    private String description;

    private long totalAmounts;

    public CompoundLift(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CompoundLift(long id, long totalAmounts) {
        this.id = id;
        this.totalAmounts = totalAmounts;
    }

    public CompoundLift() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(long totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    @Override
    public String toString() {
        return name;
    }
}
