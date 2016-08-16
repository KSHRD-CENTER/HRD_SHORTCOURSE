package kh.com.kshrd.shortcourse;

import java.util.HashMap;

/*import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRProperties;*/

public class Main {

	public static final String REPORT_DIRECTORY = "reports";

	/*public static void main(String[] args) throws JRException {

		String reportName = "Certificate";
		
		//File file = new File(REPORT_DIRECTORY + "/Certificate.jrprint");
		JasperReport jasperReport = JasperCompileManager.compileReport(REPORT_DIRECTORY + "/Certificate.jrxml");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), new JREmptyDataSource());
		//jasperPrint.setProperty(propName, value);
		jasperPrint.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
		jasperPrint.setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
		//jasperPrint.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
		//jasperPrint.setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
		JRProperties.setProperty("net.sf.jasperreports.export.character.encoding", "UTF-8");
		JRPdfExporter pdfExporter = new JRPdfExporter();
		pdfExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,REPORT_DIRECTORY + "/Certificate.pdf");
		//InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(myBundle), Charset.forName("UTF-8"));
		//ResourceBundle bundle = new PropertyResourceBundle(reader);
		pdfExporter.exportReport();
		
		//JasperExportManager.exportReportToPdfFile(jasperPrint, REPORT_DIRECTORY + "/Certificate.pdf");


		//File file = new File(REPORT_DIRECTORY + "/Certificate.jrprint");

		// TODO: EXPORT TO PDF
		// try {
		// JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(file);
		// JRPdfExporter pdfExporter = new JRPdfExporter();
		// pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,
		// jasperPrint);
		// pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		// REPORT_DIRECTORY + "/Certificate.pdf");
		// System.out.println("Exporting report...");
		// pdfExporter.exportReport();
		// System.out.println("Done!");
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }

		// try {
		// JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(file);
		// JExcelApiExporter xlsExporter = new JExcelApiExporter();
		// xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT,
		// jasperPrint);
		// xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		// REPORT_DIRECTORY + "/" + reportName + ".xls");
		// System.out.println("Exporting report...");
		// xlsExporter.exportReport();
		// System.out.println("Done!");
		// } catch (JRException e) {
		// e.printStackTrace();
		// }

		// try {
		// JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(file);
		// JRHtmlExporter htmlExporter = new JRHtmlExporter();
		// htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT,
		// jasperPrint);
		// htmlExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		// REPORT_DIRECTORY + "/" + reportName + ".html");
		// System.out.println("Exporting report...");
		// htmlExporter.exportReport();
		// System.out.println("Done!");
		// } catch (JRException e) {
		// e.printStackTrace();
		// }
	}*/
}
