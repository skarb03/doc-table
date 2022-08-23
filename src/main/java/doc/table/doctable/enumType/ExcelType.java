package doc.table.doctable.enumType;

public enum ExcelType {
    STANDARD(0),
    TABLE_INFO(1),
    TOBE_LIST(2)
    ;

    private final int value;

    ExcelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ExcelType{" +
                "value=" + value +
                '}';
    }
}
