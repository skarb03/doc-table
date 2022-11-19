package doc.table.doctable.enumType;

public enum StandardColumnType {

    NO(1),
    STANDARD_PHYSICS_LANG(3),
    STANDARD_LOGICS_LANG(2),
    STANDARD_DOMAIN_NAME(5),
    DATA_TYPE(10),
    LENGTH(11),
    ORIGIN_LOGICAL_NAME(14),
    READ_SHEET_LOCATION(3);

    private final int value;

    StandardColumnType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
