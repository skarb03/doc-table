package doc.table.doctable.test;

import doc.table.doctable.entity.ColumnInfo;
import doc.table.doctable.entity.TableInfo;
import doc.table.doctable.repository.TableInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class TableInfoRepositoryTest {

    @Autowired
    TableInfoRepository tableInfoRepository;

    @After("")
    public void cleanUp(){
        tableInfoRepository.deleteAll();
        log.info("삭제중");
    }

    @Test
    @Transactional
    public void save(){
        selectTableList();
    }

    private void test(){
        String physicalName = "test";
        String logicalName = "테스트";

        TableInfo tb1= tableInfoRepository.save(TableInfo.builder().physicalName(physicalName).logicalName(logicalName).build());

        Optional<TableInfo> oTableInfo = tableInfoRepository.findById(tb1.getTableId());
        TableInfo tableInfo = null;
        if(oTableInfo.isPresent()){
            tableInfo = oTableInfo.get();
        }

        assert tableInfo != null;
        assertThat(tableInfo.getLogicalName()).isEqualTo(logicalName);
        assertThat(tableInfo.getPhysicalName()).isEqualTo(physicalName);

        tableInfoRepository.deleteAll();
    }

    private void testList(){
        String physicalName = "test";
        String logicalName = "테스트";

        List<ColumnInfo> columnInfoList = new LinkedList<>();
        long id = 1L;
        for(int i = 0 ; i<100;i++){
            columnInfoList.add(ColumnInfo.builder().tableId(id).physicalName("test"+i).logicalName("테스트+1").build());
        }
        TableInfo tableInfo=TableInfo.builder().tableId(id).physicalName(physicalName).logicalName(logicalName).columnInfoList(columnInfoList).build();

//        tableInfoRepository.save(tableInfo);

        List<TableInfo> list = tableInfoRepository.findAll();
        int tableSize = list.size();
        assertThat(1).isEqualTo(tableSize);

    }


    private void selectTableList(){
        List<TableInfo> list = tableInfoRepository.findAllByLogicalName("주택대장_입주자_모집공고_승인");

        log.info("list {} ",list.get(0).getColumnInfoList().toString());
        assertThat(list.get(0).getColumnInfoList()).isNotEmpty();
    }

}
