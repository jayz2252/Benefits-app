package com.speridian.benefits2.writers.pdf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.PFEmployeeService;

public class ProvidentFundPDFWriter {

	public ProvidentFundPDFWriter() {
		this.fileName = "PFReport" + new Date() + ".pdf";
		this.dir = BenefitsConstants.PDF_REPORT_DIR;
	}

	String fileName = null;
	String dir = null;
	SimpleDateFormat sdf = null;

	public String createPfPdfFile(PFEmployee pfEmployee) {

		PDDocument document = null;

		document = new PDDocument();
		PDPage page1 = new PDPage();
		document.addPage(page1);

		DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");

		try {

			// PAGE 1

			PDPageContentStream contentStream1 = new PDPageContentStream(
					document, page1);

			PDFont headingFont1 = PDType1Font.TIMES_BOLD;
			PDFont headingFont2 = PDType1Font.TIMES_ROMAN;

			// HEADING

			contentStream1.setFont(headingFont1, 15);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(220, 672);
			contentStream1.drawString(new StringBuffer("PF  DECLARATION  FORM")
					.toString());
			contentStream1.endText();

			// EMPLOYEE DETAILS

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 642);
			contentStream1.drawString(new StringBuffer("1. Name as per Aadhar")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 642);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 642);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormEmpName() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormEmpName()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 616);
			contentStream1.drawString(new StringBuffer(
					"2. Father's/Husband's Name").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 616);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 616);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormGuardianName() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormGuardianName()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 586);
			contentStream1.drawString(new StringBuffer("3. Date of Birth  ")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 586);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getEmployee().getDateOfBirth() != null) {
				contentStream1.drawString(df.format(pfEmployee.getEmployee()
						.getDateOfBirth()));
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 556);
			contentStream1
					.drawString(new StringBuffer("4. Gender ").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 556);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getEmployee().getGender() != null) {

				if (pfEmployee.getEmployee().getGender().equalsIgnoreCase("M")) {
					contentStream1.drawString(new StringBuffer("Male")
							.toString());
				} else {
					contentStream1.drawString(new StringBuffer("Female")
							.toString());
				}
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 526);
			contentStream1.drawString(new StringBuffer("5. Marital Status")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 526);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormMaritalStatus() != null) {

				if (pfEmployee.getFormMaritalStatus().equalsIgnoreCase("M")) {
					contentStream1.drawString(new StringBuffer("Married")
							.toString());
				} else if (pfEmployee.getFormMaritalStatus().equalsIgnoreCase(
						"S")) {
					contentStream1.drawString(new StringBuffer("Single")
							.toString());
				}

				else {
					if (pfEmployee.getEmployee().getGender()
							.equalsIgnoreCase("M")
							&& pfEmployee.getFormMaritalStatus()
									.equalsIgnoreCase("W")) {
						contentStream1.drawString(new StringBuffer("Widower")
								.toString());
					}

					else {
						contentStream1.drawString(new StringBuffer("Widow")
								.toString());
					}
				}
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 496);
			contentStream1.drawString(new StringBuffer("6. Mobile No ")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 496);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormMobile() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormMobile()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 466);
			contentStream1.drawString(new StringBuffer("7. Email Id")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 466);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormEmail() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormEmail()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(336, 630);
			contentStream1
					.drawString(new StringBuffer("  Address").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 642);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 642);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormPermanentAddress() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormPermanentAddress()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(333, 642);
			contentStream1.drawString(new StringBuffer("8. Permanent")
					.toString());
			contentStream1.endText();

			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(333, 616);
			contentStream1.drawString(new StringBuffer("9. Voluntary PF")
					.toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 616);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();
			
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 616);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormVoluntaryPF() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormVoluntaryPF()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(333, 586);
			contentStream1.drawString(new StringBuffer("10. Temporary")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 586);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 586);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormCurrentAddress() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormCurrentAddress()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 526);
			contentStream1.drawString(new StringBuffer(" 11. Account No.")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 526);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 526);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getPfAcNo() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getPfAcNo()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 496);
			contentStream1.drawString(new StringBuffer(" 12. Aadhar No.")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 496);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 496);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormAadharNo() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormAadharNo()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 466);
			contentStream1.drawString(new StringBuffer(" 13. Pan Card No.")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 466);
			contentStream1.drawString(new StringBuffer(":").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 466);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormPan() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormPan()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			for (int ty1 = 586; ty1 >= 466; ty1 = ty1 - 30) {
				contentStream1.beginText();
				contentStream1.moveTextPositionByAmount(177, ty1);
				contentStream1.drawString(new StringBuffer(":").toString());
				contentStream1.endText();
			}

			// FOR PREVIOUS PF

			contentStream1.setFont(headingFont1, 12);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(220, 430);
			contentStream1.drawString(new StringBuffer(
					"DETAILS  OF  PREVIOUS  PF").toString());
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 400);
			contentStream1.drawString(new StringBuffer("1. UAN NO").toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 400);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getUan() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee.getUan())
						.toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 400);
			contentStream1.drawString(new StringBuffer(" 2. Previous PF No.")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 400);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormPrevPfAccNo() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormPrevPfAccNo()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 370);
			contentStream1.drawString(new StringBuffer("3. Name & Address")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 370);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormPrevEstablishment() != null) {
				contentStream1.drawString(new StringBuffer(pfEmployee
						.getFormPrevEstablishment()).toString());
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(63, 358);
			contentStream1.drawString(new StringBuffer("   (Previous ")
					.toString());
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(63, 346);
			contentStream1.drawString(new StringBuffer("    Establishment)")
					.toString());
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(60, 330);
			contentStream1.drawString(new StringBuffer(" 4. Date of Joining")
					.toString());
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(177, 330);
			contentStream1.drawString(new StringBuffer(":")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(185, 330);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormPrevDOJ() != null) {
				contentStream1
						.drawString(df.format(pfEmployee.getFormPrevDOJ()));
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(330, 330);
			contentStream1.drawString(new StringBuffer(" 5. Date of Leaving")
					.toString());
			contentStream1.endText();
			
			contentStream1.setFont(headingFont2, 10);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(410, 330);
			contentStream1.drawString(new StringBuffer(":")
					.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(418, 330);

			contentStream1.setFont(headingFont1, 10);
			if (pfEmployee.getFormPrevDOL() != null) {
				contentStream1
						.drawString(df.format(pfEmployee.getFormPrevDOL()));
			}

			else {
				contentStream1.drawString(new StringBuffer(" ").toString());
			}
			contentStream1.endText();

			for (int ty1 = 400; ty1 >= 370; ty1 = ty1 - 30) {
				contentStream1.beginText();
				contentStream1.moveTextPositionByAmount(177, ty1);
				contentStream1.drawString(new StringBuffer(":").toString());
				contentStream1.endText();
			}

			for (int ty2 = 400; ty2 >= 360; ty2 = ty2 - 30) {
				contentStream1.beginText();
				contentStream1.moveTextPositionByAmount(410, ty2);
				contentStream1.drawString(new StringBuffer(":").toString());
				contentStream1.endText();
			}

			float maxX = page1.getMediaBox().getWidth();

			contentStream1.drawLine(70, 315, maxX - 70, 315);

			contentStream1.setFont(PDType1Font.TIMES_ITALIC, 11);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(75, 280);
			contentStream1
					.drawString(new StringBuffer(
							"I hereby declare that the details furnished above are true and correct to the best of my knowledge")
							.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(75, 268);
			contentStream1
					.drawString(new StringBuffer(
							"and belief and I undertake to inform you of any changes therein, immediately. In case any of the above")
							.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(75, 256);
			contentStream1
					.drawString(new StringBuffer(
							"information is found to be false or untrue or misleading or misrepresenting, I am aware that I may be")
							.toString());
			contentStream1.endText();

			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(75, 244);
			contentStream1.drawString(new StringBuffer("held liable for it.")
					.toString());
			contentStream1.endText();

			contentStream1.setFont(headingFont1, 12);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(450, 190);
			contentStream1.drawString(new StringBuffer(pfEmployee.getEmployee()
					.getFirstName()
					+ " "
					+ pfEmployee.getEmployee().getLastName()).toString());
			contentStream1.endText();

			/*
			 * I hereby declare that the details furnished above are true and
			 * correct to the best of my knowledge and belief and I undertake to
			 * inform you of any changes therein, immediately. In case any of
			 * the above information is found to be false or untrue or
			 * misleading or misrepresenting, I am aware that I may be held
			 * liable for it
			 */

			contentStream1.close();
			document.save(dir + "/" + fileName);
			document.close();
			System.out.println("Saved to" + dir + "/" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
