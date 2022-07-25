package doc.table.doctable.test;

import doc.table.doctable.Entity.TableInfo;
import doc.table.doctable.repository.TableInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class TableInfoRepositoryTest {

    @Autowired
    TableInfoRepository tableInfoRepository;

//    @After("")
//    public void cleanUp(){
//        tableInfoRepository.deleteAll();
//        log.info("삭제중");
//    }

    @Test
    public void save(){
        String physicalName = "test";
        String logicalName = "테스트";

        TableInfo tb1= tableInfoRepository.save(TableInfo.builder().physicalName(physicalName).logicalName(logicalName).build());

        Optional<TableInfo> oTableInfo = tableInfoRepository.findById(tb1.getTableId());
        TableInfo tableInfo = null;
        if(oTableInfo.isPresent()){
            tableInfo = oTableInfo.get();
        }

        assertThat(tableInfo.getLogicalName()).isEqualTo(logicalName);
        assertThat(tableInfo.getPhysicalName()).isEqualTo(physicalName);

        tableInfoRepository.deleteAll();
    }



}
