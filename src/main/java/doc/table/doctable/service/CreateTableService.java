package doc.table.doctable.service;

import doc.table.doctable.entity.ColumnInfo;
import doc.table.doctable.entity.StandardDict;
import doc.table.doctable.entity.TableInfo;
import doc.table.doctable.repository.TableInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class CreateTableService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private TableInfoRepository tableInfoRepository;

    public void snapshotTable(String ownerPlace){
        List<TableInfo> tableInfoList = tableInfoRepository.findAll();

        List<String> ddlQuery = createDDL(tableInfoList,ownerPlace);
        List<String> ddlDropQueryList = ddlDropQuery(ownerPlace);
        List<String> commentQueryList = commentQuery(tableInfoList,ownerPlace);
        List<String> pkQueryList = pkQuery(tableInfoList,ownerPlace);

        for(String ddlDropQuery : ddlDropQueryList){
            jdbcTemplate.execute(ddlDropQuery);
        }
        log.info("====drop table ======");
        for(String ddl : ddlQuery){
            jdbcTemplate.execute(ddl);
        }
        log.info("======create table ====");
        for(String comment : commentQueryList){
            jdbcTemplate.execute(comment);
        }
        log.info("======comment finish");

        for(String pkQuery : pkQueryList){
            jdbcTemplate.execute(pkQuery);
        }
        log.info("=======create pk ");
    }

    private List<String> createDDL(List<TableInfo> tableInfoList,String ownerPlace){
        List<String> ddlQuery = new LinkedList<>();
        StringBuilder sb= null;
        for(TableInfo tableInfo : tableInfoList){
            sb= new StringBuilder();
            sb.append("create table ").append(ownerPlace).append(".").append(tableInfo.getTobePhysicalName()).append("(");
            List<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
            columnInfoList.sort(Comparator.comparing(ColumnInfo::getDisplayOrder));
            for(ColumnInfo columnInfo : tableInfo.getColumnInfoList()){

                StandardDict standardDict = columnInfo.getStandardDict();

                if(standardDict==null)
                    continue;

                sb.append(standardDict.getStandardPhysicsLang())
                        .append(" ")
                        .append(columnInfo.getDataType());

                if(columnInfo.getDataType().equals("NUMBER")){
                    String precision = removeComma(columnInfo.getPrecision());
                    sb.append("(")
                            .append(precision);

                    if(columnInfo.getScale() != null){
                        String scale = removeComma(columnInfo.getScale());
                        sb.append(",")
                                .append(scale);
                    }
                    sb.append(")");
                } else if(columnInfo.getDataType().equals("VARCHAR")){

                    String length = removeComma(columnInfo.getLength());

                    sb.append("(")
                            .append(length)
                            .append(")");

                }

                if(columnInfo.getDefaultValue()!=null){
                    sb.append(" ")
                            .append(" default ")
                            .append(columnInfo.getDefaultValue());
                }

                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            ddlQuery.add(sb.toString());
        }
        return ddlQuery;
    }

    private List<String> ddlDropQuery(String ownerPlace){

        List<String> ddlDropQuery= jdbcTemplate.query(
                "select table_name from all_tables where owner = '" +  ownerPlace.toUpperCase() + "' and table_name like 'BD0%'",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        StringBuilder sb = new StringBuilder();
                        sb.append("drop table ")
                                .append(ownerPlace)
                                .append(".")
                                .append(rs.getString("table_name"))
                                .append(" cascade constraints ");
                        return sb.toString();
                    }
                }
        );

        return ddlDropQuery;
    }

    private List<String> commentQuery(List<TableInfo> tableInfoList,String ownerPlace){
        List<String> commentQuery = new LinkedList<>();
        StringBuilder tableComment = null;
        StringBuilder columnComment = null;

        for(TableInfo tableInfo : tableInfoList) {
            tableComment = new StringBuilder();
            tableComment.append("comment on table ")
                    .append(ownerPlace)
                    .append(".")
                    .append(tableInfo.getTobePhysicalName())
                    .append(" is ")
                    .append("'")
                    .append(tableInfo.getLogicalName())
                    .append("'");
            commentQuery.add(tableComment.toString());

            for(ColumnInfo columnInfo : tableInfo.getColumnInfoList()){
                StandardDict standardDict = columnInfo.getStandardDict();
                columnComment = new StringBuilder();
                columnComment.append("comment on column ")
                        .append(ownerPlace)
                        .append(".")
                        .append(tableInfo.getTobePhysicalName())
                        .append(".")
                        .append(standardDict.getStandardPhysicsLang())
                        .append(" is ")
                        .append("'")
                        .append(standardDict.getStandardLogicsLang())
                        .append("'");
                commentQuery.add(columnComment.toString());
            }
        }
        return commentQuery;
    }

    public List<String> pkQuery(List<TableInfo> tableInfoList,String ownerPlace){
        List<String> pkQueryList = new LinkedList<>();
        StringBuilder pkQuery = null;
        for(TableInfo tableInfo : tableInfoList){
            pkQuery = new StringBuilder();
            pkQuery.append("alter table ")
                    .append(ownerPlace)
                    .append(".")
                    .append(tableInfo.getTobePhysicalName())
                    .append(" add primary key (");
            for(ColumnInfo columnInfo : tableInfo.getColumnInfoList()){
                StandardDict standardDict = columnInfo.getStandardDict();
                if("Y".equals(columnInfo.getPk())){
                    pkQuery.append(standardDict.getStandardPhysicsLang())
                            .append(",");
                }
            }
            pkQuery.deleteCharAt(pkQuery.length()-1)
                    .append(") ");
            pkQueryList.add(pkQuery.toString());
        }

        return pkQueryList;
    }

    private String removeComma(String targetValue){

        if(targetValue.contains(".")){
            return targetValue.substring(0,targetValue.indexOf("."));
        }
        return targetValue;
    }
}
