package doc.table.doctable.enumType;

public enum TobeColumnType {
    DISPLAY_ORDER(0),
    LOGIC_COL_NAME(5),
    TOBE_TABLE_NAME(4),
    ;

    private final int value;

    TobeColumnType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
