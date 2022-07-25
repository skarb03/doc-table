package doc.table.doctable.test;

import doc.table.doctable.Entity.ColumnInfo;
import doc.table.doctable.Entity.TableInfo;
import doc.table.doctable.repository.ColumnInfoRepository;
import doc.table.doctable.repository.TableInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class ColumnInfoRepositoryTest {

    @Autowired
    ColumnInfoRepository columnInfoRepository;

    @Autowired
    TableInfoRepository tableInfoRepository;

    @After("save")
    public void cleanUp(){
        log.info("삭제중");
        tableInfoRepository.deleteAll();
        columnInfoRepository.deleteAll();
    }

    @Test
    public void save(){

        String physicalName = "test";
        String logicalName = "테스트";

        TableInfo tb1= tableInfoRepository.save(TableInfo.builder().physicalName(physicalName).logicalName(logicalName).build());

        String cpname = "tet";
        String clname = "테슷";

        columnInfoRepository.save(ColumnInfo.builder().tableInfo(tb1).physicalName(cpname).logicalName(clname).build());

        List<ColumnInfo> cl =  columnInfoRepository.findAll();
        ColumnInfo columnInfo = cl.get(0);
        assertThat(columnInfo.getPhysicalName()).isEqualTo(cpname);
        assertThat(columnInfo.getLogicalName()).isEqualTo(clname);

    }
}
