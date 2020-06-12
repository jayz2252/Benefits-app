package com.speridian.benefits2.writers.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimPafDetail;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.writers.BenefitsWriter;
import com.speridian.benefits2.writers.ReportTypes;

public class INSClaimReportPdfWriter implements BenefitsWriter<InsPlanEmployeeClaimPafDetail> {
	private String filename;

	DateFormat dateFormat;

	InsPlanEmployeeClaimPafDetail paf;

	Map<String, Object> details;

	public INSClaimReportPdfWriter() {
		dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		details = new HashMap<String, Object>();
	}

	@Override
	public String write(InsPlanEmployeeClaimPafDetail paf) throws FileNotFoundException, DocumentException {

		this.paf = paf;
		Document document = new Document();
		document.setPageCount(10);
		filename = PDFUtils.createPdf(ReportTypes.INS_CLAIM_PDF_REPORT);
		File file = new File(filename);
		OutputStream outputStream = new FileOutputStream(file);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
		document.open();
		try {
			Image img = Image.getInstance(
					getClass().getClassLoader().getResource("/com/speridian/benefits2/model/image/speridian.png"));
			img.scaleAbsolute(80, 40);
			document.add(img);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		document.addTitle("EMPLOYEE CLAIM REPORT ");
		if (paf.getClaim().getClaimType().equals(BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION)) {
			PDFUtils.addMainHeading(document,
					"                                               Insurance Claim Report " + "\n\n\n");
		} else {
			PDFUtils.addMainHeading(document,
					"                                      Insurance Pre Authorization Report " + "\n\n\n");
		}

		PdfPTable masterTable = PDFUtils.createTable(2);

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Employee Name" });
		PDFUtils.addRow(masterTable, new String[] { paf.getClaim().getPlanEmployee().getEmployee().getFirstName() + " "
				+ paf.getClaim().getPlanEmployee().getEmployee().getLastName() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Employee Code" });
		PDFUtils.addRow(masterTable, new String[] { paf.getClaim().getPlanEmployee().getEmployee().getEmployeeCode() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Designation" });
		PDFUtils.addRow(masterTable,
				new String[] { paf.getClaim().getPlanEmployee().getEmployee().getDesignationName() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Department" });
		PDFUtils.addRow(masterTable,
				new String[] { paf.getClaim().getPlanEmployee().getEmployee().getDepartmentName() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Mobile No." });
		PDFUtils.addRow(masterTable, new String[] { paf.getClaim().getPlanEmployee().getEmployee().getMobileNo() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Email" });
		PDFUtils.addRow(masterTable, new String[] { paf.getClaim().getPlanEmployee().getEmployee().getEmail() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Parent Office" });
		PDFUtils.addRow(masterTable, new String[] { paf.getClaim().getPlanEmployee().getEmployee().getParentOffice() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Manager" });
		PDFUtils.addRow(masterTable,
				new String[] { paf.getClaim().getPlanEmployee().getEmployee().getManagerFullName() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Claimed Amount" });
		PDFUtils.addRow(masterTable, new String[] { paf.getClaim().getTotalReqAmount().toString() });
		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Status" });
		if(paf.getClaim().getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED)){
			PDFUtils.addRow(masterTable, new String[] { "SUBMITTED"});
		}else if(paf.getClaim().getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED)){
			PDFUtils.addRow(masterTable, new String[] { "HR APPROVED"});
		}else if(paf.getClaim().getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED)){
			PDFUtils.addRow(masterTable, new String[] { "APPROVED"});
		}else if(paf.getClaim().getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID)){
			PDFUtils.addRow(masterTable, new String[] { "PAYED"});
		}else if(paf.getClaim().getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_MNGMNT_APPROVED)){
			PDFUtils.addRow(masterTable, new String[] { "APPROVED"});
		}else{
			PDFUtils.addRow(masterTable, new String[] { "REJECTED"});
		}
		
		
		document.add(masterTable);

		PDFUtils.addNotesText(document, " ");
		PDFUtils.addCategoryName(document, "Patient Details" + "\n\n");

		PdfPTable patientTable = PDFUtils.createTable(2);
		PDFUtils.addHeaderDataRow(patientTable, new String[] { "Patient Name" });
		PDFUtils.addRow(patientTable, new String[] { paf.getDependent().getDependentName() });
		PDFUtils.addHeaderDataRow(patientTable, new String[] { "Patient Dob" });
		PDFUtils.addRow(patientTable, new String[] { dateFormat.format(paf.getDependent().getDateOfBirth()) });
		PDFUtils.addHeaderDataRow(patientTable, new String[] { "Relationship With Employee" });
		PDFUtils.addRow(patientTable, new String[] { paf.getDependent().getRelationship() });
		PDFUtils.addHeaderDataRow(patientTable, new String[] { "Treatment Category" });
		try {
			if (paf.getTreatment().getTreatmentId() != 1) {
				PDFUtils.addRow(patientTable, new String[] { paf.getTreatment().getTreatmentName() });
			} else {
				PDFUtils.addRow(patientTable, new String[] { "Others" });
			}
		} catch (Exception e) {
			PDFUtils.addRow(patientTable, new String[] { paf.getOtherTreatment() });
		}
		PDFUtils.addHeaderDataRow(patientTable, new String[] { "Illness Type" });
		PDFUtils.addRow(patientTable, new String[] { paf.getIllnessType() });
		document.add(patientTable);
		PDFUtils.addNotesText(document, " ");
		PDFUtils.addCategoryName(document, "Hospital Details" + "\n\n");
		PdfPTable hospitalTable = PDFUtils.createTable(2);
		try {
			if(paf.getHospital()!=null){
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Hospital Name" });
			PDFUtils.addRow(hospitalTable, new String[] { paf.getHospital().getHospitalName() });
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "City" });
			PDFUtils.addRow(hospitalTable, new String[] { paf.getHospital().getCity() });
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "State" });
			PDFUtils.addRow(hospitalTable, new String[] { paf.getHospital().getState() });
			}else{
				PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Hospital Name" });
				PDFUtils.addRow(hospitalTable, new String[] { paf.getOtherHospital()});
				PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "City" });
				PDFUtils.addRow(hospitalTable, new String[] { paf.getOtherHospitalCity()});
				PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "State" });
				PDFUtils.addRow(hospitalTable, new String[] { paf.getOtherHospitalState()});
			}
			
			
		} catch (Exception e) {
			PDFUtils.addNotesText(document, " ");
		}
		PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Prescriber Name" });
		PDFUtils.addRow(hospitalTable, new String[] { paf.getPrescriberName() });
		PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Prescriber  Contact Number" });
		PDFUtils.addRow(hospitalTable, new String[] { paf.getPrescriberContactNo() });
		PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Prescriber  Email" });
		try {
			PDFUtils.addRow(hospitalTable, new String[] { paf.getPrescriberEmail() });
		} catch (Exception e) {
			PDFUtils.addRow(hospitalTable, new String[] { "" });
		}
		if(paf.getIsSpecialistServicesAvailed()){
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Specialist Name" });
			PDFUtils.addRow(hospitalTable, new String[] { paf.getSpecialistName() });
			
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Specialist  Contact Number" });
			try{
			PDFUtils.addRow(hospitalTable, new String[] { paf.getSpecialistContactNo()});
			}catch (Exception e) {
				PDFUtils.addRow(hospitalTable, new String[] { ""});
			}
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "Specialist  Email" });
			try {
				PDFUtils.addRow(hospitalTable, new String[] { paf.getSpecialistEmail() });
			} catch (Exception e) {
				PDFUtils.addRow(hospitalTable, new String[] { "" });
			}
		}
		if(paf.getProName()!=null){
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "PRO Name" });
			PDFUtils.addRow(hospitalTable, new String[] { paf.getProName() });
			
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "PRO  Contact Number" });
			try{
			PDFUtils.addRow(hospitalTable, new String[] { paf.getProContactNo()});
			}catch (Exception e) {
				PDFUtils.addRow(hospitalTable, new String[] { ""});
			}
			PDFUtils.addHeaderDataRow(hospitalTable, new String[] { "PRO  Email" });
			try {
				PDFUtils.addRow(hospitalTable, new String[] { paf.getProEmail() });
			} catch (Exception e) {
				PDFUtils.addRow(hospitalTable, new String[] { "" });
			}
		}
		
		
		document.add(hospitalTable);
		
		
		
		
		PDFUtils.addNotesText(document, " ");
		

		PdfPTable PrescriberTable = PDFUtils.createTable(2);
		
		document.add(PrescriberTable);
		
		
		document.close();

		try {
			outputStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return filename;
	}

	@Override
	public void addDetails(String key, List<?> value) {
		details.put(key, value);

	}

	@Override
	public String write(InsPlanEmployeeClaimPafDetail master, Boolean passWordRequired)
			throws FileNotFoundException, DocumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilename() {
		return filename;
	}

}
