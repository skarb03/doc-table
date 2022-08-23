package doc.table.doctable.enumType;

public enum StandardColumnType {

    NO(0),
    STANDARD_PHYSICS_LANG(2),
    STANDARD_LOGICS_LANG(1),
    STANDARD_DOMAIN_NAME(3),
    DATA_TYPE(9),
    LENGTH(10),
    ORIGIN_LOGICAL_NAME(11),
    READ_SHEET_LOCATION(4);

    private final int value;

    StandardColumnType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
