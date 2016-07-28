import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.PdfFont;

public interface Main {

	public static void main(String[] args) throws JRException {
		Connection connection = null;

		/*
		 * String reportName =
		 * "C:\\Users\\HRD\\git\\HRD_SHORTCOURSE\\Certificate"; Map<String,
		 * Object> parameters = new HashMap<String, Object>(); //connection =
		 * new ConnectionFactory().getConnection(); // opens a jdbc connection
		 * 
		 * // compiles jrxml JasperCompileManager.compileReportToFile(reportName
		 * + ".jrxml"); // fills compiled report with parameters and a
		 * connection JasperPrint print =
		 * JasperFillManager.fillReport(reportName + ".jasper", parameters,
		 * connection);
		 * 
		 * HashMap fontMap = new HashMap(); FontKey key = new FontKey(
		 * "Kh Bokor", false, false); PdfFont font = new PdfFont("Kh Bokor",
		 * "UTF-8", true); fontMap.put(key, font);
		 * 
		 * // exports report to pdf JRPdfExporter exporter = new
		 * JRPdfExporter();
		 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new
		 * FileOutputStream(reportName + ".pdf")); // your output goes here
		 * //exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);
		 * 
		 * exporter.exportReport();
		 */

		/*
		 * HashMap hm = null; // System.out.println(
		 * "Usage: ReportGenerator ....");
		 * 
		 * System.out.println("Start ...."); // Get jasper report String
		 * jrxmlFileName = "C:/Users/HRD/git/HRD_SHORTCOURSE/Certificate.jrxml";
		 * String jasperFileName =
		 * "C:/Users/HRD/git/HRD_SHORTCOURSE/Certificate.jasper"; String
		 * pdfFileName = "C:/Users/HRD/git/HRD_SHORTCOURSE/Certificate.pdf";
		 * 
		 * JasperCompileManager.compileReportToFile(jrxmlFileName,
		 * jasperFileName);
		 * 
		 * // String dbUrl = props.getProperty("jdbc.url"); String dbUrl =
		 * "jdbc:oracle:thin:@localhost:1521:mydbname"; // String dbDriver =
		 * props.getProperty("jdbc.driver"); String dbDriver =
		 * "oracle.jdbc.driver.OracleDriver"; // String dbUname =
		 * props.getProperty("db.username"); String dbUname = "mydb"; // String
		 * dbPwd = props.getProperty("db.password"); String dbPwd = "mydbpw";
		 * 
		 * // Load the JDBC driver //Class.forName(dbDriver); // Get the
		 * connection Connection conn = null
		 * ;//DriverManager.getConnection(dbUrl, dbUname, dbPwd);
		 * 
		 * // Create arguments // Map params = new HashMap(); hm = new
		 * HashMap(); hm.put("ID", "123"); hm.put("DATENAME", "April 2006"); //
		 * Generate jasper print
		 * 
		 * JRProperties.setProperty(
		 * "net.sf.jasperreports.default.pdf.font.name", defaultPDFFont);
		 * JasperPrint jprint = (JasperPrint)
		 * JasperFillManager.fillReport(jasperFileName, hm, conn);
		 * 
		 * // Export pdf file JasperExportManager.exportReportToPdfFile(jprint,
		 * pdfFileName);
		 * 
		 * System.out.println("Done exporting reports to pdf");
		 * 
		 * } catch (Exception e) { throw new RuntimeException(
		 * "It's not possible to generate the pdf report.", e); } finally { //
		 * it's your responsibility to close the connection, don't forget // it!
		 * }
		 */

		/*
		 * JasperReport is the object that holds our compiled jrxml file
		 */
		JasperReport jasperReport;

		/*
		 * JasperPrint is the object contains report after result filling
		 * process
		 */
		JasperPrint jasperPrint;
		// connection is the data source we used to fetch the data from
		// Connection connection = null;
		// jasperParameter is a Hashmap contains the parameters
		// passed from application to the jrxml layout
		HashMap jasperParameter = new HashMap();

		// jrxml compiling process
		jasperReport = JasperCompileManager.compileReport("C:\\Users\\HRD\\git\\HRD_SHORTCOURSE\\Certificate.jrxml");

		// filling report with data from data source
		
		jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, new JREmptyDataSource());

		// exporting process
		// 1- export to PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, "C://sample_report.pdf");

		// 2- export to HTML
		JasperExportManager.exportReportToHtmlFile(jasperPrint, "C://sample_report.html");

/*		HashMap fontMap = new HashMap();
		net.sf.jasperreports.engine.export.FontKey key = new net.sf.jasperreports.engine.export.FontKey("Kh-Bokor", false, false);
		PdfFont font = new PdfFont("Kh-Bokor", "UTF-8", true);
		fontMap.put(key, font);*/

		// 3- export to Excel sheet
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://simple_report.xls");
		//exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);

		exporter.exportReport();
	}
}
