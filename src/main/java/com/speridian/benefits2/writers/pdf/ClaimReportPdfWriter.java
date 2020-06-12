package com.speridian.benefits2.writers.pdf;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanClaimDetail;
import com.speridian.benefits2.model.util.BenefitsConstants;

public class ClaimReportPdfWriter {
	
	public ClaimReportPdfWriter(){
		this.fileName = "ClaimReport" + new Date()+".pdf";
		this.dir = BenefitsConstants.PDF_REPORT_DIR;
	}

	String fileName = null;
	String dir = null;
	SimpleDateFormat sdf = null;

	public String createPdfFile(BenefitPlanClaim benefitPlanClaim) {
		
		List<BenefitPlanClaimDetail> list = benefitPlanClaim.getDetails();
		
		PDDocument document = null;
		
			document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);
			DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
			
			
			//image display
			/*String fileName = "pdfWithImage.pdf";*/
	        //String imageName = "https://www.speridian.com/images/Speridian%20Logo.svg";

	        try {

	            /*PDXObjectImage image = new PDJpeg(document, new FileInputStream(imageName));

	            PDPageContentStream content = new PDPageContentStream(document, page);

	            content.drawImage(image, 180, 700);

	            content.close();

	            document.save(fileName);

	            document.close();*/

	       //image
			
			PDPageContentStream contentStream = new PDPageContentStream(
					document, page);
			
			float maxX = page.getMediaBox().getWidth();
			
			contentStream.drawLine(10, 750, maxX-30, 750);
			contentStream.beginText();

			contentStream.moveTextPositionByAmount(100, 700);
			PDFont headingFont = PDType1Font.HELVETICA_BOLD;
			PDFont contentFont = PDType1Font.HELVETICA;
			PDFont tableHeaderFont = PDType1Font.TIMES_BOLD;
			PDFont tableContentFont = PDType1Font.TIMES_ROMAN;
			PDFont quotedText = PDType1Font.TIMES_ITALIC;

			contentStream.setFont(headingFont, 19);

			contentStream.drawString(new StringBuffer(
					"Employee Claim Report - Customer copy").toString());
			contentStream.endText();

			contentStream.setFont(contentFont, 10);
			// header box

			contentStream.beginText();
			contentStream.moveTextPositionByAmount(35, 610);
			contentStream.drawString(new StringBuffer(" Name of employee:")
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(" Designation:")
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(" Claim Ref No:")
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(" Requested Date:")
					.toString());
			contentStream.setFont(tableHeaderFont, 10);
			contentStream.moveTextPositionByAmount(100, 72);
			contentStream.drawString(new StringBuffer(benefitPlanClaim
					.getPlanEmployee().getEmployee().getFirstName()
					+ " "
					+ benefitPlanClaim.getPlanEmployee().getEmployee()
							.getLastName()).toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(benefitPlanClaim
					.getPlanEmployee().getEmployee().getDesignationName())
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(benefitPlanClaim
					.getClaimRefNo()).toString());
			contentStream.moveTextPositionByAmount(0, -24);
			if ((benefitPlanClaim.getSubmittedDate() != null)
					&& !(benefitPlanClaim.getSubmittedDate().equals(""))) {
				contentStream.drawString(df.format(benefitPlanClaim
						.getSubmittedDate()));
			}
			
			contentStream.setFont(contentFont, 10);
			contentStream.moveTextPositionByAmount(180, 72);
			contentStream.drawString(new StringBuffer(" Employee code:")
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(" Location:").toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(" Status:").toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(" Claim Period:")
					.toString());
			contentStream.setFont(tableHeaderFont, 10);
			contentStream.moveTextPositionByAmount(100, 72);
			contentStream.drawString(new StringBuffer(benefitPlanClaim
					.getPlanEmployee().getEmployee().getEmployeeCode())
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			contentStream.drawString(new StringBuffer(benefitPlanClaim
					.getPlanEmployee().getEmployee().getParentOffice())
					.toString());
			contentStream.moveTextPositionByAmount(0, -24);
			if (benefitPlanClaim.getStatus().equals(
					BenefitsConstants.CLAIM_STATUS_HR_APPR)
					|| benefitPlanClaim.getStatus().equals(
							BenefitsConstants.CLAIM_STATUS_FIN_APPR)) {
				contentStream.drawString(new StringBuffer("Approved")
						.toString());
			} else {
				contentStream.drawString(new StringBuffer("Submitted")
						.toString());
			}
			contentStream.moveTextPositionByAmount(0, -24);
			if ((benefitPlanClaim.getPeriodFrom() != null)
					&& !(benefitPlanClaim.getPeriodFrom()
							.equals(""))) {
				contentStream.drawString(new StringBuffer(
						df.format(benefitPlanClaim
								.getPeriodFrom()) + " to ").toString());
			} else {
				contentStream.drawString(new StringBuffer("Not Available")
						.toString());
			}
			if ((benefitPlanClaim.getPeriodTo() != null)
					&& !(benefitPlanClaim.getPeriodTo()
							.equals(""))) {
				contentStream.drawString(new StringBuffer(
						df.format(benefitPlanClaim.getPeriodTo())).toString());
			} else {
				contentStream.drawString(new StringBuffer("Not Available")
						.toString());
			}
			contentStream.endText();

			// table body

			// header cells
			contentStream.addRect(30, 630, 560, -110);
			contentStream.beginText();
			drawCell(document, contentStream, 30, 430, 590, 470);
			drawCell(document, contentStream, 30, 430, 50, 470);
			drawCell(document, contentStream, 50, 430, 155, 470);
			drawCell(document, contentStream, 155, 430, 240, 470);
			drawCell(document, contentStream, 240, 430, 320, 470);
			drawCell(document, contentStream, 320, 430, 460, 470);
			drawCell(document, contentStream, 460, 430, 535, 470);
			drawCell(document, contentStream, 535, 430, 590, 470);

			// header data
			
			contentStream.setFont(tableHeaderFont, 10);
			
			contentStream.moveTextPositionByAmount(33, 450);
			contentStream.drawString(new StringBuffer("No.").toString());
			
			contentStream.moveTextPositionByAmount(40, 0);
			contentStream.drawString(new StringBuffer("Dependent").toString());
			contentStream.moveTextPositionByAmount(83, 0);
			contentStream.drawString(new StringBuffer(" Relationship With")
					.toString());
			contentStream.moveTextPositionByAmount(20, -10);
			contentStream.drawString(new StringBuffer("Employee").toString());
			contentStream.moveTextPositionByAmount(85, 0);
			contentStream.drawString(new StringBuffer(" Bill No.").toString());
			contentStream.moveTextPositionByAmount(92, 0);
			contentStream.drawString(new StringBuffer(" Shop/Hospital")
					.toString());
			contentStream.moveTextPositionByAmount(120, 0);
			contentStream.drawString(new StringBuffer(" Bill Date").toString());
			contentStream.moveTextPositionByAmount(65, 0);
			contentStream.drawString(new StringBuffer(" Amount").toString());
			
			contentStream.setFont(tableContentFont, 8);
			int tx = 410, ty = 430, rowCount = 1;
			for (BenefitPlanClaimDetail benefitPlanClaimDetail : list) {
				// body cells and data
				if (rowCount%8==0) {
					contentStream.endText();
					contentStream.close();
					page = new PDPage();
					document.addPage(page);
					tx = 600;
					ty = 640;
					contentStream = new PDPageContentStream(document, page);
					contentStream.beginText();
				}
				drawCell(document, contentStream, 30, tx, 50, ty);
				drawCell(document, contentStream, 50, tx, 155, ty);
				drawCell(document, contentStream, 155, tx, 240, ty);
				drawCell(document, contentStream, 240, tx, 320, ty);
				drawCell(document, contentStream, 320, tx, 460, ty);
				drawCell(document, contentStream, 460, tx, 535, ty);
				drawCell(document, contentStream, 535, tx, 590, ty);
				
				contentStream.setFont(tableContentFont, 8);
				contentStream.moveTextPositionByAmount(36, ty - 10);
				contentStream.drawString(new StringBuffer(" " + rowCount + " ").toString());
				
				contentStream.moveTextPositionByAmount(0, 0);
				contentStream.drawString(new StringBuffer().toString());
				contentStream.moveTextPositionByAmount(20, 0);
				contentStream.drawString(new StringBuffer("Not Applicable").toString());
				/*contentStream.drawString(new StringBuffer(
						benefitPlanClaimDetail.getDependent()
								.getDependentName()).toString());*/
				contentStream.moveTextPositionByAmount(130, 0);
				contentStream.drawString(new StringBuffer("Not Applicable      ").toString());
				/*contentStream
						.drawString(new StringBuffer(benefitPlanClaimDetail
								.getDependent().getRelationship()).toString());*/
				contentStream.moveTextPositionByAmount(70, 0);
				contentStream.drawString(new StringBuffer(
						benefitPlanClaimDetail.getBillNo()).toString());
				contentStream.moveTextPositionByAmount(75, 0);
				
				contentStream.drawString(new StringBuffer(
						benefitPlanClaimDetail.getBillIssuer()).toString());
				
				if(benefitPlanClaimDetail.getBillDate() != null)
				{
				contentStream.moveTextPositionByAmount(135, 0);
				contentStream.drawString(new StringBuffer(df
						.format(benefitPlanClaimDetail.getBillDate()))
						.toString());
				}
				else {
					contentStream.moveTextPositionByAmount(135, 0);
					contentStream.drawString(new StringBuffer(" ")
							.toString());
				}
				contentStream.moveTextPositionByAmount(78, 0);
				contentStream.drawString(benefitPlanClaimDetail.getRequestedAmount().toString());
				
				ty = tx;
				tx -= 20;
				rowCount++;
			}
			// quoted text

			// Setting the font to the Content stream
			contentStream.endText();
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(15, tx - 50);

			contentStream.setFont(PDType1Font.TIMES_ITALIC, 11);
			
			
			contentStream
					.drawString(new StringBuffer(
							"By this submission I am certifying and agreeing to the above.This action takes the place of my signature.\"")
							.toString());
			contentStream.moveTextPositionByAmount(0, 10);
			contentStream
					.drawString(new StringBuffer(
							" information on this application may result in the failure of acceptance of these bills or subject to punitive or corrective actions as"
									)
							.toString());

			contentStream.moveTextPositionByAmount(0, 10);

			contentStream
					.drawString(new StringBuffer(
							"\"I  hereby certify that the bills submitted  are  true and  correct to the best of my knowledge.Any misrepresentation"
									+ ","
									+ "falsification of ")
							.toString());
			contentStream.setFont(PDType1Font.TIMES_BOLD, 11);
			contentStream.endText();
			contentStream.beginText();

			contentStream.moveTextPositionByAmount(15, tx - 70);

			contentStream.drawString(new StringBuffer(benefitPlanClaim
					.getPlanEmployee().getEmployee().getFirstName()
					+ " "
					+ benefitPlanClaim.getPlanEmployee().getEmployee()
							.getLastName()).toString());

			contentStream.endText();
			contentStream.close();

			document.save(dir+"/"+fileName);

			document.close();
			System.out.print("Saved to" + dir+"/"+fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}

	/**
	 * Used to draw a line in the given PD Document
	 * 
	 * @param document
	 * @param contentStream
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @throws IOException
	 */
	private void drawCell(PDDocument document,
			PDPageContentStream contentStream, float startX, float startY,
			float endX, float endY) throws IOException {
		contentStream.endText();

		contentStream.drawLine(startX, startY, endX, startY);
		contentStream.drawLine(endX, startY, endX, endY);
		contentStream.drawLine(endX, endY, startX, endY);
		contentStream.drawLine(startX, endY, startX, startY);

		contentStream.beginText();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
