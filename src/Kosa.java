//STEP 1. Import required packages
import java.sql.*;


public class Kosa {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/hi";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Raketa4";

    private Connection connect()
    {
        Connection conn = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    private void doSelect(Connection conn)
    {
        Statement stmt = null;
        try{
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT goroda.idgoroda,goroda.gorodaname,ludi.idludi,ludi.ludiname,ludi.ludisurname,ludi.idcity FROM hi.goroda JOIN hi.ludi ON (ludi.idcity=goroda.idgoroda)";
        ResultSet rs = stmt.executeQuery(sql);

        //STEP 5: Extract data from result set
        while(rs.next()){
            //Retrieve by column name
            int idgoroda  = rs.getInt("idgoroda");
            String gorodname = rs.getString("gorodaname");
            String ludiname = rs.getString("ludiname");
            String ludisurn = rs.getString("ludisurname");

            //Display values
            System.out.print("IDgoroda: " + idgoroda + " ");
            System.out.print("gorodname: " + gorodname);
            System.out.print("Namepeople: " + ludiname);
            System.out.println(", Surnamepeople: " + ludisurn);
        }
        //STEP 6: Clean-up environment
        rs.close();
        stmt.close();
        } catch(SQLException se2){
        }// nothing we can do
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            close(conn);
        }
    }
    private void doInsert(Connection conn) {
        Statement stmt = null;
        try {
            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = "INSERT INTO hi.goroda (gorodaname) " +
                    "VALUES ('Riga')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO hi.goroda (gorodaname) " +
                    "VALUES ('Mahachkala')";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
            close(conn);
        }
    }
    private void doUpdate (Connection conn)  {
        Statement stmt = null;
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "UPDATE goroda " +
                    "SET gorodaname = 'Yral' WHERE gorodaname ='Riga'";
            stmt.executeUpdate(sql);
        }catch(SQLException se2){
            // nothing we can do
            se2.
                    printStackTrace();
            close(conn);
        }
    }
    private void doDelete (Connection conn) {
        Statement stmt = null;
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "DELETE FROM goroda WHERE gorodaname='Mahachkala'";
            stmt.executeUpdate(sql);

            // Now you can extract all the records
            // to see the remaining records

        } catch (SQLException se3) {
            se3.printStackTrace();
            close(conn);
        }
    }
    private void close(Connection conn){
        try {
            //STEP 6: Clean-up environment
            if(conn!= null) {
                conn.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void doCreate(Connection conn) {
        Statement stmt = null;
        try {
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE STUDENT (" +
                    "id INTEGER not NULL," +
                    " NameStudent VARCHAR(255)," +
                    " SurnameStudent VARCHAR(255), " +
                    " idCity INTEGER);";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch(SQLException ex){
            ex.printStackTrace();
            close(conn);
        }
    }
    private void doAlterTable(Connection conn){
        Statement stmt = null;
        try {
            System.out.println("Alter table in given database...");
            stmt = conn.createStatement();

            String sql = "ALTER TABLE student ADD  FOREIGN KEY (idCity) REFERENCES goroda (idgoroda)";

            stmt.executeUpdate(sql);
            System.out.println("Alter table in given database...");
        } catch(SQLException ex2){
            ex2.printStackTrace();
            close(conn);
        }
    }

    public static void main(String[] args) {

        //STEP 4: Execute a query
            //finally block used to close resources
        Kosa ks=new Kosa();
        Connection c=ks.connect();
        //ks.doInsert(c);
        //ks.doUpdate(c);
        //ks.doDelete(c);
        //ks.doSelect(c);
        //ks.doCreate(c);
        ks.doAlterTable(c);
        ks.close(c);
        System.out.println("Goodbye!");
    }//end main
}
