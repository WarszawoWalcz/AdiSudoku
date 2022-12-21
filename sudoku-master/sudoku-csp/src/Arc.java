public class Arc {
    // The two Fields that the arc connects
    private Field firstField;
    private Field secondField;

    public Arc(Field firstField, Field secondField) {
        this.firstField = firstField;
        this.secondField = secondField;
    }

    // Getters for the Fields in the arc
    public Field getFirstField() {
        return firstField;
    }

    public Field getSecondField() {
        return secondField;
    }
}
