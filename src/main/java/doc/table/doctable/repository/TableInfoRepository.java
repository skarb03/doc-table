package doc.table.doctable.repository;

import doc.table.doctable.entity.TableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TableInfoRepository extends JpaRepository<TableInfo, Long> {
    List<TableInfo> findAllByLogicalName(String LogicalName);

    @Query("select b from TableInfo b where b.logicalName = :logicalName")
    TableInfo findTableInfoByLogicalNameForUpdate(String logicalName);
}
