import java.sql.*;

public class DatabaseExport {

    public static void main(String[] args) throws Exception {
        String driver = "com.mysql.cj.jdbc.Driver";
        String host = "192.168.194.31";
        String port = "3306";
        String database = "renshi_test";
        String user = "root";
        String password = "root";
        //&nullCatalogMeansCurrent=true参数确保返回指定库涉及表，否则会返回数据库的所有表
        String url = String.format("jdbc:mysql://%s:%s/%s?charset=utf8&nullCatalogMeansCurrent=true",host,port,database);
        jdbcConnect(driver,url,user,password);


    }

    private static void jdbcConnect(String driver,String url,String user,String password) throws Exception{
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            //获取所有表
            ResultSet tableResultSet = metaData.getTables(null,null,null,null);
            while(tableResultSet.next()){
                String tablename = tableResultSet.getString("TABLE_NAME");
                System.out.println("table:"+tablename);
                //获取此表的所有列
                ResultSet columnResultSet = metaData.getColumns(null,null,tablename,null);
                while (columnResultSet.next()){
                    //字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    //字段类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    //字段能否为空，若能，则为1，若不能，则为0
                    int nullable = columnResultSet.getInt("NULLABLE");
                    //描述
                    String remarks = columnResultSet.getString("REMARKS");
                    System.out.println("columnName:"+columnName+" columnType:"+columnType+" nullable:"+nullable+" remarks:"+remarks);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
