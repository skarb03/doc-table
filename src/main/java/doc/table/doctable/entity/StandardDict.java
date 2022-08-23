package doc.table.doctable.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(indexes = @Index(name = "idx_standardDict",columnList = "originLogicalName"))
public class StandardDict implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long no;

    private String standardPhysicsLang;

    private String standardLogicsLang;

    private String standardDomainName;

    private String dataType;

    private String length;

    private String originLogicalName;

}
