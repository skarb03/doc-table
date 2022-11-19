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
    CONN_YN(11),
    TOBE_TABLE_NAME(12),
    INTF_NUMBER(13),
    CREATE_YN(14),
    PRECISION(300),//쓰레기값
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
