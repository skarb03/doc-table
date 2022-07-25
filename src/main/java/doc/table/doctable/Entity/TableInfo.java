package doc.table.doctable.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
//@Audited
public class TableInfo {

    @Id
//    @GeneratedValue(generator = "USER_GENERATOR")
//    @GenericGenerator(name="USER_GENERATOR",strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tableId;

    private String physicalName;

    private String logicalName;

    private String displayOrder;

    private String tobePhysicalName;

}
