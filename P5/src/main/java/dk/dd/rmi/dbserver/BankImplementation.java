package dk.dd.rmi.dbserver;

/**
 * @author Dora Di
 */

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BankImplementation extends UnicastRemoteObject implements BankInterface {
    public static String url = "jdbc:h2:file:" + System.getProperty("user.dir") + "\\src\\main\\resources\\db\\bank";
    public static String user = "sa";
    public static String password = "";
    public static String driver = "org.h2.Driver";

    BankImplementation() throws RemoteException {
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @RequestMapping(value = "/fileSaver", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("myfile") MultipartFile file) throws IOException, SQLException {

        int count = 0;
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement prepStmt = con.prepareStatement(
                    "insert into CUSTOMER(ACCNUM,NAME,AMOUNT) values (?,?,?)");

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                prepStmt.setInt(1, Integer.parseInt(csvRecord.get("ACCNUM")));
                prepStmt.setString(2, csvRecord.get("NAME"));
                prepStmt.setDouble(3, Double.parseDouble(csvRecord.get("AMOUNT")));
                prepStmt.execute();
            }

            PreparedStatement prepStmtCount = con.prepareStatement(
                    "SELECT COUNT(*) FROM CUSTOMER");
            ResultSet rs = prepStmtCount.executeQuery();
            rs.next();
            count = rs.getInt("COUNT(*)");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failure" + e;
        }
        return "Success \n current size of database:" + count;
    }

    @GetMapping("/bank")
    public List<Customer> getMillionaires() {

        List<Customer> list = new ArrayList<Customer>();
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = con.prepareStatement("select * from Customer where amount >= 100000;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setAccnum(rs.getLong(1));
                c.setName(rs.getString(2));
                c.setAmount(rs.getDouble(3));
                System.out.println(c);
                list.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}  



