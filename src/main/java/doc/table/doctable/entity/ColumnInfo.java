package doc.table.doctable.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(indexes = @Index(name = "idx_columnInfo",columnList = "logicalName"))
public class ColumnInfo implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long columnId;

    private Long tableId;

    private String physicalName;

    private String logicalName;

    private String dataType;

    private String length;

    private String precision;

    private String scale;

    private String nullable;

    private String pk;

    private String defaultValue;

    private String displayOrder;

    @ManyToOne
    @JoinColumn(name = "tableId",updatable = false,insertable = false)
    private TableInfo tableInfo;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="logicalName",referencedColumnName = "originLogicalName",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),updatable = false,insertable = false)
    private StandardDict standardDict;

}
