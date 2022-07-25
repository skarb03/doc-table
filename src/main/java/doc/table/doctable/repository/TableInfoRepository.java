package doc.table.doctable.repository;

import doc.table.doctable.Entity.TableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface TableInfoRepository extends JpaRepository<TableInfo, Long> {
    //        , RevisionRepository<TableInfo,Long,Integer> {
}
