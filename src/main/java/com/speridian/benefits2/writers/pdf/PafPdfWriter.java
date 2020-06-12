package com.speridian.benefits2.writers.pdf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.hibernate.HibernateException;

import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimPafDetail;
import com.speridian.benefits2.model.util.BenefitsConstants;

public class PafPdfWriter {

	public PafPdfWriter() {
		super();
		this.filename = filename;
		this.dir = dir;
	}

	String filename = "PAFReport" + new Date() + ".pdf";
	String dir = BenefitsConstants.PAF_PDF_REPORT_DIR;
	SimpleDateFormat sdf = null;

	public String createPAFPdf(InsPlanEmployeeClaimPafDetail claimPafDetail) {
		PDDocument document = null;
		document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");

		try {

			PDPageContentStream contentStream1 = new PDPageContentStream(
					document, page);

			PDFont headingFont1 = PDType1Font.TIMES_BOLD;
			PDFont headingFont2 = PDType1Font.TIMES_ROMAN;

			// HEADING

			contentStream1.setFont(headingFont1, 15);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(210, 672);
			contentStream1
					.drawString(new StringBuffer("PRE AUTHORIZATION FORM")
							.toString());
			contentStream1.endText();

			// DETAILS

			// EMPLOYEE DETAILS

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 642);
			contentStream1.drawString(new StringBuffer("Employee Name")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 642);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 642);

			contentStream1.setFont(headingFont1, 10);

			contentStream1.drawString(new StringBuffer(claimPafDetail
					.getClaim().getPlanEmployee().getEmployee().getFirstName()
					+ " "
					+ claimPafDetail.getClaim().getPlanEmployee().getEmployee()
							.getLastName()).toString());

			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 642);
			contentStream1.drawString(new StringBuffer("Employee Code")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 642);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 642);

			contentStream1.setFont(headingFont1, 10);

			contentStream1.drawString(new StringBuffer(claimPafDetail
					.getClaim().getPlanEmployee().getEmployee()
					.getEmployeeCode()).toString());
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 616);
			contentStream1.drawString(new StringBuffer("Patient Name")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 616);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 616);

			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getDependent().getDependentName() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail
						.getDependent().getDependentName()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 616);
			contentStream1.drawString(new StringBuffer("Hospital Details")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 616);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 616);

			contentStream1.setFont(headingFont1, 10);

			if (claimPafDetail.getHospital()!=null && claimPafDetail.getHospital().getHospitalName() != null
					&& claimPafDetail.getHospital().getCity() != null
					&& claimPafDetail.getHospital().getState() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail
						.getHospital().getHospitalName()
						+ ","
						+ claimPafDetail.getHospital().getCity()
						+ ","
						+ claimPafDetail.getHospital().getState()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 586);
			contentStream1.drawString(new StringBuffer("Treatment  ")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 586);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 586);
			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getTreatment().getTreatmentName() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getTreatment().getTreatmentName()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 586);
			contentStream1.drawString(new StringBuffer("Illness Type")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 586);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 586);

			contentStream1.setFont(headingFont1, 10);

			if (claimPafDetail.getIllnessType() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getIllnessType()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 556);
			contentStream1
					.drawString(new StringBuffer("Prescriber Name").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 556);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 556);
			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getPrescriberName() != null) {				
					contentStream1.drawString(claimPafDetail.getPrescriberName().toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 556);
			contentStream1.drawString(new StringBuffer("Prescriber Ph. No.")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 556);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 556);

			contentStream1.setFont(headingFont1, 10);

			if (claimPafDetail.getPrescriberContactNo() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getPrescriberContactNo()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 526);
			contentStream1.drawString(new StringBuffer("Prescriber Email")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 526);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 526);
			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getPrescriberEmail() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getPrescriberEmail()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 496);
			contentStream1.drawString(new StringBuffer("Specialist Name")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 496);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 496);

			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getSpecialistName() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getSpecialistName()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 496);
			contentStream1.drawString(new StringBuffer("Specialist Ph. No.")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 496);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 496);
			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getSpecialistContactNo() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getSpecialistContactNo()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 466);
			contentStream1.drawString(new StringBuffer("Specialist Email ")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 466);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 466);

			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getSpecialistEmail() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getSpecialistEmail()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 436);
			contentStream1.drawString(new StringBuffer("PRO Name")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 436);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 436);
			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getProName() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getProName()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 436);
			contentStream1.drawString(new StringBuffer("PRO Ph. No. ")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 436);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 436);

			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getProContactNo() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getProContactNo()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 406);
			contentStream1.drawString(new StringBuffer("PRO Email")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 406);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 406);
			contentStream1.setFont(headingFont1, 10);
			if (claimPafDetail.getProEmail() != null) {
				contentStream1.drawString(new StringBuffer(claimPafDetail.getProEmail()).toString());
			}
			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 376);
			contentStream1.drawString(new StringBuffer("Status ")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 376);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 376);

			contentStream1.setFont(headingFont1, 10);
			
				contentStream1.drawString(new StringBuffer("Submitted").toString());
			contentStream1.endText();
			
			
			contentStream1.close();
			document.save(dir + "/" + filename);
			document.close();
			System.out.println("Saved to" + dir + "/" + filename);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}
}
