package doc.table.doctable.enumType;

public enum TableColumnType {
    LOGICAL_TAB_NAME(0),
    PHY_TAB_NAME(1),
    PHY_COL_NAME(2),
    LOGIC_COL_NAME(3),
    DATA_TYPE(4),
    LENGTH(5),
    SCALE(6),
    PK_YN(7),
    NULLABLE(8),
    DEFAULT(9),
    ORDER(10),
    PRECISION(11),
    READ_SHEET_LOCATION(0)
    ;

    private final int value;

    TableColumnType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
