import java.sql.Statementimport java.util.regex.Matcherimport java.util.regex.Patternimport java.sql.ResultSetimport java.sql.Driverimport java.sql.DriverManagerimport java.sql.Connectionimport java.util.zip.ZipFileimport java.util.zip.ZipEntryimport TigerDefinitionimport TigerTableimport groovy.sql.Sqlimport org.apache.commons.lang.StringUtils//return; //comment out to run/** * the raw output has total=2627282 = 576M * with sort | uniq will cut it down to 2588852 = 566M */Config config = new Config()//return;  //comment out to runDriverManager.registerDriver((Driver)getClass().getClassLoader().loadClass(config.driverClass).newInstance())//this is calling a private method of DriverManager :D //because the maven classloader does not have the driver classConnection conn = DriverManager.getConnection(config.connectionString,     new Properties(), getClass().getClassLoader())    PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(/tiger_ca.csv/)))    //t2 starts at long1Statement stmt = conn.createStatement()ResultSet rs = stmt.executeQuery("""select     TLID,    FEDIRP,    FENAME,    FETYPE,    FEDIRS,    FRADDL,    TOADDL,    FRADDR,    TOADDR,    ZIPL,    ZIPR,    FRLONG,    FRLAT,    TOLONG,    TOLAT,    LONG1,    LAT1,    LONG2,    LAT2,    LONG3,    LAT3,    LONG4,    LAT4,    LONG5,    LAT5,    LONG6,    LAT6,    LONG7,    LAT7,    LONG8,    LAT8,    LONG9,    LAT9,    LONG10,    LAT10     from tiger_main     """)int i =0;while(rs.next()){  i++    if(i%1000==0) println i  int j=1  ps.println([           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++),           rs.getString(j++)].join(','))}stmt?.close()conn?.close()rs?.close()ps?.close()println('total='+i)