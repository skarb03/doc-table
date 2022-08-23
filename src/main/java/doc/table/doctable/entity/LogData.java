package doc.table.doctable.entity;

import doc.table.doctable.enumType.ExcelType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class LogData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ExcelType excelType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regTimestamp;

    private String ipAddress;

}
