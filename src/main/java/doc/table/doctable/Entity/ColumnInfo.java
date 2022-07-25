package doc.table.doctable.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
//@Audited
public class ColumnInfo {

    @Id
//    @GeneratedValue(generator = "USER_GENERATOR")
//    @GenericGenerator(name="USER_GENERATOR",strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long columnId;

    @ManyToOne
    @JoinColumn(name="table_id")
    private TableInfo tableInfo;

    private String physicalName;

    private String logicalName;

    private String dataType;

    private String length;

    private String precision;

    private String scale;

    private String nullable;

    private String defaultValue;

    private String displayOrder;

}
