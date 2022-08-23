package doc.table.doctable.test;

import doc.table.doctable.entity.StandardDict;
import doc.table.doctable.repository.StandardDictRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class StandardDictRepositoryTest {

    @Autowired
    StandardDictRepository standardDictRepository;

    @Test
    public void save(){
        test();
    }

    private void test(){
        long no = 1L;
        String standardLogicsLang = "standlogics";
        String standardPhysicsLang = "standphysics";
        String standardDomainName = "domainName";
        String dataType = "dataType";
        String length = "length";
        String originLogicalName = "origin";

        standardDictRepository.save(StandardDict.builder()
                                                    .no(no)
                                                    .standardLogicsLang(standardLogicsLang)
                                                    .standardPhysicsLang(standardPhysicsLang)
                                                    .standardDomainName(standardDomainName)
                                                    .dataType(dataType)
                                                    .length(length)
                                                    .originLogicalName(originLogicalName)
                                                    .build());
        Optional<StandardDict> standardDictOptional = standardDictRepository.findById(no);

        StandardDict standardDict = null;
        if(standardDictOptional.isPresent()){
            standardDict = standardDictOptional.get();
        }

        assert standardDict != null;
        assertThat(standardDict.getStandardLogicsLang()).isEqualTo(standardLogicsLang);
        assertThat(standardDict.getStandardPhysicsLang()).isEqualTo(standardPhysicsLang);
        assertThat(standardDict.getStandardDomainName()).isEqualTo(standardDomainName);
        assertThat(standardDict.getDataType()).isEqualTo(dataType);
        assertThat(standardDict.getLength()).isEqualTo(length);
        assertThat(standardDict.getOriginLogicalName()).isEqualTo(originLogicalName);

    }

    @AfterEach
    public void afterTest(){
        standardDictRepository.deleteAll();
        log.info("delete complete");
    }


}
