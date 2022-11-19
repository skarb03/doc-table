package doc.table.doctable.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class TableInfo {

    @Id
    private Long tableId;

    private String physicalName;

    private String logicalName;

    private Integer displayOrder;

    private String tobePhysicalName;

    private String intfPhsicslName;

    private String createYn;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY , mappedBy = "tableId")
    private List<ColumnInfo> columnInfoList;

}
