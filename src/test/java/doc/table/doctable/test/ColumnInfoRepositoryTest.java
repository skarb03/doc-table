package doc.table.doctable.test;

import doc.table.doctable.entity.ColumnInfo;
import doc.table.doctable.entity.TableInfo;
import doc.table.doctable.repository.ColumnInfoRepository;
import doc.table.doctable.repository.TableInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
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



//    @After("save")
//    public void cleanUp(){
//        log.info("삭제중");
//        columnInfoRepository.deleteAll();
//        tableInfoRepository.deleteAll();
//    }


    @Test
    public void save(){
//        test1();
//        listTest();
        joinTest();
    }
    private void test1(){
        String physicalName = "test";
        String logicalName = "테스트";

        TableInfo tb1= tableInfoRepository.save(TableInfo.builder().physicalName(physicalName).logicalName(logicalName).build());

        String cpname = "tet";
        String clname = "테슷";

        columnInfoRepository.save(ColumnInfo.builder().physicalName(cpname).logicalName(clname).build());



        List<ColumnInfo> cl =  columnInfoRepository.findAll();
        ColumnInfo columnInfo = cl.get(0);
        assertThat(columnInfo.getPhysicalName()).isEqualTo(cpname);
        assertThat(columnInfo.getLogicalName()).isEqualTo(clname);

    }

    private void listTest(){
        String physicalName = "test";
        String logicalName = "테스트";

        TableInfo tb1= tableInfoRepository.save(TableInfo.builder().physicalName(physicalName).logicalName(logicalName).build());
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        for(int i = 0 ; i<5000;i++){
            columnInfoList.add(ColumnInfo.builder().physicalName("test"+i).logicalName("테스트+1").build());
        }
        columnInfoRepository.saveAll(columnInfoList);

        List<ColumnInfo> cl =  columnInfoRepository.findAll();

        assertThat(cl.size()).isEqualTo(5000);
    }

    private void joinTest(){
        List<ColumnInfo> cl =  columnInfoRepository.findAll();
        ColumnInfo ci = cl.get(0);
        assertThat(ci.getTableInfo()).isNotNull();
        assertThat(ci.getStandardDict()).isNotNull();
    }
}
