package doc.table.doctable.service;

import doc.table.doctable.entity.ColumnInfo;
import doc.table.doctable.entity.TableInfo;
import doc.table.doctable.repository.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SqlFileService {

    private final String FILE_NAME = "gmdmi";

    private final String OWNER = "gmdmi";

//    private final String

    @Autowired
    private TableInfoRepository tableInfoRepository;

    public List<String> getSqlDataList(){
        List<String> sqlQuery = new LinkedList<>();
        StringBuilder sb= null;
        StringBuilder tableComment = null;
        StringBuilder columnComment = null;
//        List<TableInfo> tableInfoList = tableInfoRepository.findAll();
        List<TableInfo> tableInfoList = tableInfoRepository.findAll().stream().filter(c -> "O".equals(c.getCreateYn())).collect(Collectors.toList());

        for(TableInfo tableInfo : tableInfoList) {

//            String tableName = tableInfo.getPhysicalName();
            String tableName= "TB_BD01I_"+tableInfo.getIntfPhsicslName()+"_"+tableInfo.getPhysicalName();
                String pks = "";
                sb = new StringBuilder();
                columnComment = new StringBuilder();
                sb.append("create table ")
                        .append(OWNER)
                        .append(".")
                        .append(tableName)
                        .append("(");
                ///gmdmi
                       sb.append(" INTF_TRNS_ID VARCHAR(43 CHAR)," +
                                " ROW_ID VARCHAR(16 CHAR)," +
                                " TRNS_DVSN_CODE VARCHAR(1 CHAR)," +
                                " TRNS_DTDT VARCHAR(20 CHAR)," +
                                " INTF_CMPL_DTTM DATE default  sysdate, " +
                                " TRNS_PK_CHNG_YN VARCHAR(1 CHAR) default 'N', " +
                                " MDM_STTS_CODE VARCHAR(1 CHAR) default 'N', " +
                                " MDM_CHNG_DTTM DATE, " +
                                " MDM_STTS_CNTN VARCHAR(200 CHAR), ");

                tableComment = new StringBuilder();
                tableComment.append("comment on table ")
                        .append(OWNER)
                        .append(".")
                        .append(tableName)
                        .append(" is ")
                        .append("'")
                        .append(tableInfo.getLogicalName())
                        .append("';");
                List<ColumnInfo> columnList = tableInfo.getColumnInfoList().stream().filter(c -> "O".equals(c.getConnYn())).collect(Collectors.toList());
                columnList.sort(Comparator.comparing(ColumnInfo::convertOrder));
                for (ColumnInfo columnInfo : columnList) {
                    sb.append(columnInfo.getPhysicalName())
                            .append(" ")
                            .append(columnInfo.getDataType());

                    if (columnInfo.getDataType().equals("NUMBER")) {
                        String precision = removeComma(columnInfo.getPrecision());
                        sb.append("(")
                                .append(precision);

                        if (columnInfo.getScale() != null) {
                            String scale = removeComma(columnInfo.getScale());
                            sb.append(",")
                                    .append(scale);
                        }
                        sb.append(")");
                    } else if (columnInfo.getDataType().equals("VARCHAR")) {
                        String length = removeComma(columnInfo.getLength());

                        sb.append("(")
                                .append(length)
                                .append(" CHAR )");
                    }

                    sb.append(",");

                    if ("Y".equals(columnInfo.getPk())) {
                        pks += columnInfo.getPhysicalName() + ",";
                    }


                    columnComment.append("comment on column ")
                            .append(OWNER)
                            .append(".")
                            .append(tableName)
                            .append(".")
                            .append(columnInfo.getPhysicalName())
                            .append(" is ")
                            .append("'")
                            .append(columnInfo.getLogicalName())
                            .append("';");
                }

                if (pks.length() != 0) {
                    pks = pks.substring(0, pks.length() - 1);
                } else {
                    System.out.println("nopks : " + tableInfo.getTableId());
                }



//            if(pks.length() !=0) {
//                sb.append(" CONSTRAINTS ")
//                        .append(tableName)
//                        .append(" primary key ( ")
//                        .append(pks)
//                        .append(")");
//            }
//            sb.append(");");

                            sb.append(" CDC_DTDT VARCHAR(20) , " +
                                " KAFKA_OFFSET_EXTRACT NUMBER, " +
                                " KAFKA_OFFSET_COLLECT NUMBER, " +
                                " CONSTRAINTS ")
                        .append(tableName)
                        .append(" primary key (INTF_TRNS_ID,ROW_ID) );");
//                index
                sb.append(" create index IDX_")
                        .append(tableName)
                        .append("_001 on gmdmi.")
                        .append(tableName)
                        .append("(MDM_STTS_CODE,TRNS_DVSN_CODE,TRNS_DTDT); ");
                if (!"".equals(pks)) {
                    sb.append(" create index IDX_")
                            .append(tableName)
                            .append("_002 on gmdmi.")
                            .append(tableName)
                            .append("(")
                            .append(pks)
                            .append("); ");
                }
                sqlQuery.add(sb.toString());
                sqlQuery.add(tableComment.toString());
                sqlQuery.add(columnComment.toString());

            }
//        }
        return sqlQuery;
    }

    public void createFile(List<String> dataList)  {
        File file = new File("/Users/kang/Documents/"+FILE_NAME+".sql");

        try {

            if(file.createNewFile()){
                System.out.println("createFile");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(String sql : dataList) {
                writer.append(" ");
                writer.newLine();
                writer.append(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String removeComma(String targetValue){

        if(targetValue.contains(".")){
            return targetValue.substring(0,targetValue.indexOf("."));
        }
        return targetValue;
    }
}
