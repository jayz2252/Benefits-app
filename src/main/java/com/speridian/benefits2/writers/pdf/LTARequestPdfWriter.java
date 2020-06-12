package com.speridian.benefits2.writers.pdf;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.speridian.benefits2.model.pojo.LTAEmployee;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;
import com.speridian.benefits2.model.util.BenefitsConstants;

public class LTARequestPdfWriter {

	public LTARequestPdfWriter() {
		this.fileName = "LtaReport" + new Date() + ".pdf";
		this.dir = BenefitsConstants.PDF_REPORT_DIR;
	}

	String fileName = null;
	String dir = null;
	SimpleDateFormat sdf = null;

	public String createLtaPdfFile(LTAEmployee ltaEmployee) {

		List<LTAEmployee> ltaEmployees = new ArrayList<LTAEmployee>();
		List<LTAEmployeeDependent> dependents = ltaEmployee.getDependents();

		PDDocument document = null;

		document = new PDDocument();
		PDPage page1 = new PDPage();
		document.addPage(page1);

		PDPage page2 = new PDPage();
		document.addPage(page2);
		DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");

		try {

			// PAGE 1

			PDPageContentStream contentStream = new PDPageContentStream(
					document, page1);

			contentStream.beginText();
			contentStream.moveTextPositionByAmount(70, 710);
			PDFont headingFont1 = PDType1Font.TIMES_BOLD;
			PDFont headingFont2 = PDType1Font.TIMES_ROMAN;
			PDFont contentFont = PDType1Font.HELVETICA;
			PDFont tableHeaderFont = PDType1Font.TIMES_BOLD;
			PDFont tableContentFont = PDType1Font.TIMES_ROMAN;
			PDFont quotedText = PDType1Font.TIMES_ITALIC;

			// for headings
			contentStream.setFont(headingFont1, 11);

			contentStream
					.drawString(new StringBuffer(
							"Declaration for Leave Travel Allowance (LTA) - Financial Year ____________ ")
							.toString());
			contentStream.endText();

			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(70, 685);
			contentStream
					.drawString(new StringBuffer(
							"LTA EXEMPT IS TWO JOURNEYS IN A BLOCK OF FOUR CALENDAR YEARS ")
							.toString());
			contentStream.endText();

			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(70, 673);
			contentStream.drawString(new StringBuffer(
					"(Current Block is calendar year ").toString());
			contentStream.drawString(new StringBuffer(ltaEmployee.getBlock())
					.toString());
			contentStream.drawString(new StringBuffer(")").toString());
			contentStream.endText();

			// table body

			// header cell 1
			contentStream.setFont(headingFont1, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 644, 117, 658);
			contentStream.moveTextPositionByAmount(70, 647);
			contentStream.drawString(new StringBuffer("  Sr. No.").toString());
			contentStream.endText();

			// header cell 2
			contentStream.setFont(headingFont1, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 644, 423, 658);
			contentStream.moveTextPositionByAmount(230, 647);
			contentStream.drawString(new StringBuffer(" Particulars ")
					.toString());
			contentStream.endText();

			// header cell 3
			contentStream.setFont(headingFont1, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 644, 538, 658);
			contentStream.moveTextPositionByAmount(455, 647);
			contentStream.drawString(new StringBuffer(" Remarks ").toString());
			contentStream.endText();

			// row 2 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 588, 117, 644);
			contentStream.moveTextPositionByAmount(90, 634);
			contentStream.drawString(new StringBuffer("1").toString());
			contentStream.endText();

			// row 2 cell 2

			contentStream.beginText();
			contentStream.setFont(headingFont2, 11);
			drawCell(document, contentStream, 117, 588, 423, 644);
			contentStream.moveTextPositionByAmount(119, 634);
			contentStream.drawString(new StringBuffer(" Period of Leave:  ")
					.toString());

			contentStream.setFont(contentFont, 12);
			drawCell(document, contentStream, 117, 588, 423, 644);
			contentStream.moveTextPositionByAmount(119, 620);
			contentStream.drawString(new StringBuffer(
					"  [Note: To claim LTA exemption for tax purposes a  ")
					.toString());

			contentStream.setFont(contentFont, 12);
			drawCell(document, contentStream, 117, 588, 423, 644);
			contentStream.moveTextPositionByAmount(119, 605);
			contentStream
					.drawString(new StringBuffer(
							"  minimum leave of 5 days is required to be availed by   ")
							.toString());

			contentStream.setFont(contentFont, 12);
			drawCell(document, contentStream, 117, 588, 423, 644);
			contentStream.moveTextPositionByAmount(119, 591);
			contentStream.drawString(new StringBuffer("  the employee]  ")
					.toString());
			contentStream.endText();

			// row 2 cell 3
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 588, 538, 644);
			contentStream.moveTextPositionByAmount(455, 634);
			if ((ltaEmployee.getNoOfLeave() != null)) {
				contentStream.drawString((ltaEmployee.getNoOfLeave())
						.toString());
			} else {
				contentStream.drawString(new StringBuffer("0").toString());
			}
			System.out.println("***************************"
					+ ltaEmployee.getNoOfLeave() + "***************");
			contentStream.endText();

			// row3 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 574, 117, 588);
			contentStream.moveTextPositionByAmount(90, 577);
			contentStream.drawString(new StringBuffer("  2").toString());
			contentStream.endText();

			// row3 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 574, 423, 588);
			contentStream.moveTextPositionByAmount(119, 577);
			contentStream.drawString(new StringBuffer(
					" LTA Amount received by the employee:  ").toString());
			contentStream.endText();

			// row3 cell 3
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 574, 538, 588);
			contentStream.moveTextPositionByAmount(455, 577);
			if (ltaEmployee.getApprovedAmt() != null) {
				contentStream.drawString((ltaEmployee.getActualFare())
						.toString());
			} else {
				contentStream.drawString(new StringBuffer("0").toString());
			}
			contentStream.endText();

			// row4 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 560, 117, 574);
			contentStream.moveTextPositionByAmount(90, 563);
			contentStream.drawString(new StringBuffer("  3").toString());
			contentStream.endText();

			// row4 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 560, 423, 574);
			contentStream.moveTextPositionByAmount(119, 563);
			contentStream.drawString(new StringBuffer(
					" Place of Origin of travel within India:   ").toString());
			contentStream.endText();

			// row4 cell 3
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 560, 538, 574);
			contentStream.moveTextPositionByAmount(455, 563);
			contentStream.drawString(new StringBuffer(ltaEmployee.getOrigin())
					.toString());
			contentStream.endText();

			// row5 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 546, 117, 560);
			contentStream.moveTextPositionByAmount(90, 549);
			contentStream.drawString(new StringBuffer("  4").toString());
			contentStream.endText();

			// row5 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 546, 423, 560);
			contentStream.moveTextPositionByAmount(119, 549);
			contentStream.drawString(new StringBuffer(
					" Final Destination of travel within India:    ")
					.toString());
			contentStream.endText();

			// row5 cell 3
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 546, 538, 560);
			contentStream.moveTextPositionByAmount(455, 549);
			contentStream.drawString(new StringBuffer(ltaEmployee
					.getDestination1()).toString());
			contentStream.endText();

			// row6 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 510, 117, 546);
			contentStream.moveTextPositionByAmount(90, 534);
			contentStream.drawString(new StringBuffer("  5").toString());
			contentStream.endText();

			// row6 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 510, 423, 546);
			contentStream.moveTextPositionByAmount(119, 534);
			contentStream.drawString(new StringBuffer(
					" Short description of route used:     ").toString());
			contentStream.endText();

			// row6 cell 3
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 510, 538, 546);
			contentStream.moveTextPositionByAmount(455, 534);
			contentStream.drawString(new StringBuffer(ltaEmployee
					.getRouteDescription()).toString());
			contentStream.endText();

			// row7 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 450, 117, 510);
			contentStream.moveTextPositionByAmount(90, 498);
			contentStream.drawString(new StringBuffer("  6").toString());
			contentStream.endText();

			// row7 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 450, 423, 510);
			contentStream.moveTextPositionByAmount(119, 498);
			contentStream.drawString(new StringBuffer(
					" Whether the journey is performed by Air      ")
					.toString());

			drawCell(document, contentStream, 117, 450, 423, 510);
			contentStream.moveTextPositionByAmount(119, 486);
			contentStream.drawString(new StringBuffer(
					" a) Actual to and fro fare incurred :       ").toString());

			drawCell(document, contentStream, 117, 450, 423, 510);
			contentStream.moveTextPositionByAmount(119, 474);
			contentStream
					.drawString(new StringBuffer(
							" b) Air economy fare of the National carrier to and fro to final        ")
							.toString());

			drawCell(document, contentStream, 117, 450, 423, 510);
			contentStream.moveTextPositionByAmount(119, 462);
			contentStream.drawString(new StringBuffer(
					" destination by the shortest route:        ").toString());
			contentStream.endText();

			contentStream.beginText();
			drawCell(document, contentStream, 423, 450, 538, 510);
			contentStream.endText();

			// row7 cell 3
			/*
			 * contentStream.setFont(headingFont2, 11);
			 * contentStream.beginText(); drawCell(document, contentStream, 423,
			 * 450, 538, 510); contentStream.moveTextPositionByAmount(455, 486);
			 * if ((ltaEmployee.getActualFare() != null)) {
			 * contentStream.drawString((ltaEmployee.getActualFare())
			 * .toString()); } else { contentStream.drawString(new
			 * StringBuffer("0").toString()); }
			 * 
			 * drawCell(document, contentStream, 423, 450, 538, 510);
			 * contentStream.moveTextPositionByAmount(455, 462); if
			 * ((ltaEmployee.getApprovedAmt() != null)) {
			 * contentStream.drawString((ltaEmployee.getApprovedAmt())
			 * .toString()); } else { contentStream.drawString(new
			 * StringBuffer("0").toString()); } contentStream.endText();
			 */
			// row8 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 350, 117, 450);
			contentStream.moveTextPositionByAmount(90, 438);
			contentStream.drawString(new StringBuffer("  7").toString());
			contentStream.endText();

			// row8 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 438);
			contentStream
					.drawString(new StringBuffer(
							" Whether the places between origin of journey and final destination")
							.toString());

			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 426);
			contentStream
					.drawString(new StringBuffer(
							" are fully connected by rail and the journey is performed by the")
							.toString());

			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 414);
			contentStream.drawString(new StringBuffer(
					" mode of transport other than by Air.  ").toString());

			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 390);
			contentStream.drawString(new StringBuffer(
					" a) Mode of transport used:  ").toString());

			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 378);
			contentStream.drawString(new StringBuffer(
					" b) Actual to and fro fare incurred:  ").toString());

			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 366);
			contentStream
					.drawString(new StringBuffer(
							" c) First Class A/C rail fare to and fro to final destination by the ")
							.toString());

			drawCell(document, contentStream, 117, 350, 423, 450);
			contentStream.moveTextPositionByAmount(119, 354);
			contentStream.drawString(new StringBuffer(" shortest route:  ")
					.toString());
			contentStream.endText();

			// row8 cell 3
			contentStream.beginText();
			drawCell(document, contentStream, 423, 350, 538, 450);
			contentStream.endText();

			// row9 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 120, 117, 350);
			contentStream.moveTextPositionByAmount(90, 338);
			contentStream.drawString(new StringBuffer("  8").toString());
			contentStream.endText();

			// row9 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 338);
			contentStream
					.drawString(new StringBuffer(
							" Whether the places between origin of journey and final destination")
							.toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 326);
			contentStream
					.drawString(new StringBuffer(
							" are not connected by rail or partly connected by rail and the")
							.toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 314);
			contentStream
					.drawString(new StringBuffer(
							" journey is performed by a mode of transport other than by Air, ")
							.toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 302);
			contentStream.drawString(new StringBuffer(" Rail").toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 294);
			contentStream.drawString(new StringBuffer(
					" a) Mode of transport used: ").toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 280);
			contentStream.drawString(new StringBuffer(
					" b) Actual to and fro fare incurred: ").toString());

			contentStream.setFont(quotedText, 11);
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 254);
			contentStream
					.drawString(new StringBuffer(
							" If a recognized public transport system (train, bus, plane, steamer,  ")
							.toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 242);
			contentStream.drawString(new StringBuffer(" taxi) exists   ")
					.toString());

			contentStream.setFont(headingFont2, 11);
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 218);
			contentStream.drawString(new StringBuffer(
					" c) First Class / Deluxe Class:  ").toString());

			contentStream.setFont(contentFont, 11);
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 206);
			contentStream.drawString(new StringBuffer(
					" (fare to and fro by shortest route to final destination")
					.toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 194);
			contentStream.drawString(new StringBuffer(" by such transport) ")
					.toString());

			contentStream.setFont(quotedText, 11);
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 170);
			contentStream.drawString(new StringBuffer(
					" If no recognized public transport system exists   ")
					.toString());

			contentStream.setFont(headingFont2, 11);
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 146);
			contentStream.drawString(new StringBuffer(
					" d) First Class A/C rail:   ").toString());

			contentStream.setFont(contentFont, 11);
			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 134);
			contentStream.drawString(new StringBuffer(
					" (fare to and fro to the final destination by shortest")
					.toString());

			drawCell(document, contentStream, 117, 120, 423, 350);
			contentStream.moveTextPositionByAmount(119, 122);
			contentStream.drawString(new StringBuffer(
					" route as if the journey has been undertaken by rail)")
					.toString());
			contentStream.endText();

			// row9 cell 3
			contentStream.beginText();
			drawCell(document, contentStream, 423, 120, 538, 350);
			contentStream.endText();

			// row10 cell 1
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 65, 92, 117, 120);
			contentStream.moveTextPositionByAmount(90, 108);
			contentStream.drawString(new StringBuffer("  9").toString());
			contentStream.endText();

			// row10 cell 2
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 117, 92, 423, 120);
			contentStream.moveTextPositionByAmount(119, 108);
			contentStream
					.drawString(new StringBuffer(
							" Details of to and fro fare for self and family actually incurred as ")
							.toString());

			drawCell(document, contentStream, 117, 92, 423, 120);
			contentStream.moveTextPositionByAmount(119, 96);
			contentStream.drawString(new StringBuffer(" under: ").toString());
			contentStream.endText();

			// row10 cell 3
			contentStream.setFont(headingFont2, 11);
			contentStream.beginText();
			drawCell(document, contentStream, 423, 92, 538, 120);
			contentStream.moveTextPositionByAmount(455, 96);
			contentStream.endText();

			// PAGE 2

			PDPageContentStream contentStream2 = new PDPageContentStream(
					document, page2);

			// row1 cell 1
			contentStream2.setFont(headingFont2, 11);
			contentStream2.beginText();
			drawCell(document, contentStream2, 65, 528, 117, 720);
			contentStream2.moveTextPositionByAmount(70, 708);
			contentStream2.drawString(new StringBuffer(" ").toString());
			contentStream2.endText();

			// row1 cell 2
			contentStream2.setFont(contentFont, 11);
			contentStream2.beginText();
			drawCell(document, contentStream2, 117, 528, 423, 720);
			contentStream2.moveTextPositionByAmount(119, 708);
			contentStream2.drawString(new StringBuffer(
					" [Note: Family includes spouse, children (maximum ")
					.toString());

			drawCell(document, contentStream2, 117, 528, 423, 720);
			contentStream2.moveTextPositionByAmount(119, 696);
			contentStream2.drawString(new StringBuffer(
					" two in respect of children born after 1 October 1998,")
					.toString());

			drawCell(document, contentStream2, 117, 528, 423, 720);
			contentStream2.moveTextPositionByAmount(119, 684);
			contentStream2.drawString(new StringBuffer(
					" except in case of multiple births after one child) and")
					.toString());

			drawCell(document, contentStream2, 117, 528, 423, 720);
			contentStream2.moveTextPositionByAmount(119, 672);
			contentStream2.drawString(new StringBuffer(
					" the following persons if they are wholly or mainly ")
					.toString());

			drawCell(document, contentStream2, 117, 528, 423, 720);
			contentStream2.moveTextPositionByAmount(119, 660);
			contentStream2
					.drawString(new StringBuffer(
							" dependent on the employee i.e. the parents, ")
							.toString());

			drawCell(document, contentStream2, 117, 528, 423, 720);
			contentStream2.moveTextPositionByAmount(119, 648);
			contentStream2.drawString(new StringBuffer(
					" brothers and sisters of the employee.] ").toString());

			// inner table

			// row1 column1
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 121, 598, 208, 636);
			contentStream2.moveTextPositionByAmount(123, 624);
			contentStream2.drawString(new StringBuffer("Name of the")
					.toString());

			drawCell(document, contentStream2, 121, 598, 208, 636);
			contentStream2.moveTextPositionByAmount(123, 612);
			contentStream2.drawString(new StringBuffer("Person").toString());

			// drawCell(document, contentStream2, 121, 598, 200, 636);
			// contentStream2.moveTextPositionByAmount(123, 600);
			// contentStream2.drawString(new StringBuffer("Person").toString());

			// row1 column2
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 208, 598, 269, 636);
			contentStream2.moveTextPositionByAmount(210, 624);
			contentStream2.drawString(new StringBuffer("Relationship")
					.toString());

			// row1 column3
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 269, 598, 325, 636);
			contentStream2.moveTextPositionByAmount(271, 624);
			contentStream2.drawString(new StringBuffer("Destination")
					.toString());

			// row1 column4
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 325, 598, 378, 636);
			contentStream2.moveTextPositionByAmount(328, 624);
			contentStream2.drawString(new StringBuffer("Class of").toString());

			drawCell(document, contentStream2, 325, 598, 378, 636);
			contentStream2.moveTextPositionByAmount(328, 612);
			contentStream2.drawString(new StringBuffer("Travel").toString());

			// row1 column5
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 378, 598, 419, 636);
			contentStream2.moveTextPositionByAmount(381, 624);
			contentStream2.drawString(new StringBuffer("Cost").toString());

			drawCell(document, contentStream2, 378, 598, 419, 636);
			contentStream2.moveTextPositionByAmount(381, 612);
			contentStream2.drawString(new StringBuffer("of").toString());

			drawCell(document, contentStream2, 378, 598, 419, 636);
			contentStream2.moveTextPositionByAmount(381, 600);
			contentStream2.drawString(new StringBuffer("Fare").toString());

			int tx = 584, ty = 598, rowCount = 1;

			for (LTAEmployeeDependent ltaDependent : dependents) {

				// row2 column1
				contentStream2.setFont(headingFont2, 11);
				drawCell(document, contentStream2, 121, tx, 208, ty);
				contentStream2.moveTextPositionByAmount(123, (tx + 3));
				contentStream2.drawString(new StringBuffer(ltaDependent
						.getDependent().getDependentName()).toString());

				// row2 column2
				contentStream2.setFont(headingFont2, 11);
				drawCell(document, contentStream2, 208, tx, 269, ty);
				contentStream2.moveTextPositionByAmount(210, (tx + 3));
				contentStream2.drawString(new StringBuffer(ltaDependent
						.getDependent().getRelationship()).toString());

				// row2 column3
				contentStream2.setFont(headingFont2, 11);
				drawCell(document, contentStream2, 269, tx, 325, ty);
				contentStream2.moveTextPositionByAmount(271, (tx + 3));
				contentStream2.drawString(new StringBuffer(ltaDependent
						.getDestination()).toString());

				// row2 column4
				contentStream2.setFont(headingFont2, 11);
				drawCell(document, contentStream2, 325, tx, 378, ty);
				contentStream2.moveTextPositionByAmount(328, (tx + 3));
				contentStream2.drawString(ltaDependent
						.getTravellingClass());

				// row2 column5
				contentStream2.setFont(headingFont2, 11);
				drawCell(document, contentStream2, 378, tx, 419, ty);
				contentStream2.moveTextPositionByAmount(381, (tx + 3));
				contentStream2.drawString(ltaDependent.getFare().toString());

				tx -= 14;
				ty -= 14;

				rowCount++;

			}

			// for column3 of row6, row7 and row8
			for (LTAEmployeeDependent ltaDependent : dependents) {
				System.out.println("::::::::::::::::::"
						+ ltaDependent.getModeOfTransport()
						+ ":::::::::::::::::");
				/*
				 * if (!ltaDependent.getModeOfTransport().equals("null"))
				 * System.out.println("?????????????????? MODE OF TRANSPORT" +
				 * ltaDependent.getModeOfTransport() + "?????????????????????");
				 */
				if (dependents.size() == 1) {

					if (ltaDependent.getDependent().getRelationship()
							.equals("Self")) {

						if (ltaDependent.getModeOfTransport() != null) {

							// row6 column3
							if (ltaDependent.getModeOfTransport().equals("Air")) {

								contentStream.setFont(headingFont2, 11);
								contentStream.beginText();
								drawCell(document, contentStream, 423, 450,
										538, 510);
								contentStream
										.moveTextPositionByAmount(455, 486);

								if ((ltaEmployee.getActualFare() != null)) {
									contentStream.drawString((ltaEmployee
											.getActualFare()).toString());
								} else {
									contentStream.drawString(new StringBuffer(
											"0").toString());
								}

								drawCell(document, contentStream, 423, 450,
										538, 510);
								contentStream
										.moveTextPositionByAmount(455, 462);
								if ((ltaEmployee.getApprovedAmt() != null)) {
									contentStream.drawString((ltaEmployee
											.getApprovedAmt()).toString());
								} else {
									contentStream.drawString(new StringBuffer(
											"0").toString());
								}
								contentStream.endText();

								// row7 column3
							} else if (ltaDependent.getModeOfTransport()
									.equals("Rail")) {
								contentStream.setFont(headingFont2, 11);
								contentStream.beginText();
								drawCell(document, contentStream, 423, 350,
										538, 450);
								contentStream
										.moveTextPositionByAmount(455, 394);

								contentStream.drawString(ltaDependent
										.getModeOfTransport().toString());

								drawCell(document, contentStream, 423, 350,
										538, 450);
								contentStream
										.moveTextPositionByAmount(455, 380);
								if ((ltaEmployee.getActualFare() != null)) {
									contentStream.drawString((ltaEmployee
											.getActualFare()).toString());
								} else {
									contentStream.drawString(new StringBuffer(
											"0").toString());
								}

								drawCell(document, contentStream, 423, 350,
										538, 450);
								contentStream
										.moveTextPositionByAmount(455, 352);
								if ((ltaEmployee.getApprovedAmt() != null)) {
									contentStream.drawString((ltaEmployee
											.getApprovedAmt()).toString());
								} else {
									contentStream.drawString(new StringBuffer(
											"0").toString());
								}

								contentStream.endText();
							}

							// row8 column3
							else {
								contentStream.beginText();
								drawCell(document, contentStream, 423, 120,
										538, 350);
								contentStream
										.moveTextPositionByAmount(455, 294);
								contentStream.drawString(ltaDependent
										.getModeOfTransport().toString());

								drawCell(document, contentStream, 423, 120,
										538, 350);
								contentStream
										.moveTextPositionByAmount(455, 280);
								if ((ltaEmployee.getActualFare() != null)) {
									contentStream.drawString((ltaEmployee
											.getActualFare()).toString());

								drawCell(document, contentStream, 423, 120,
										538, 350);
								contentStream
										.moveTextPositionByAmount(455, 212);
								if (ltaDependent.getModeOfTransport().equals(
										"Rail")) {
									if ((ltaEmployee.getApprovedAmt() != null)) {
										contentStream.drawString((ltaEmployee
												.getApprovedAmt()).toString());
									} else {
										contentStream
												.drawString(new StringBuffer(
														"0").toString());
									}
								}

								else {
									if ((ltaEmployee.getApprovedAmt() != null)) {

										drawCell(document, contentStream, 423,
												120, 538, 350);
										contentStream.moveTextPositionByAmount(
												455, 148);
										contentStream.drawString((ltaEmployee
												.getApprovedAmt()).toString());
									}

									else {
										drawCell(document, contentStream, 423,
												120, 538, 350);
										contentStream.moveTextPositionByAmount(
												455, 148);
										contentStream
												.drawString(new StringBuffer(
														"0").toString());
									}
								}
							}

						}

					} else {
						System.out
								.println("?????????????????? MODE OF TRANSPORT"
										+ ltaDependent.getModeOfTransport()
										+ "?????????????????????");
					}

				}
			}

			/* } */
			/* } */

			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 528, 538, 720);
			contentStream2.moveTextPositionByAmount(425, 648);
			contentStream2.endText();

			contentStream2.setFont(headingFont1, 11);
			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(75, 495);
			contentStream2.drawString(new StringBuffer(
					"COMPUTATION OF RELIEF FROM TAX  ").toString());
			contentStream2.endText();

			// Table 2

			// row1 column1
			contentStream2.setFont(headingFont1, 11);
			contentStream2.beginText();
			drawCell(document, contentStream2, 65, 461, 117, 475);
			contentStream2.moveTextPositionByAmount(70, 464);
			contentStream2.drawString(new StringBuffer("  Sr. No.").toString());

			// row1 column2
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 117, 461, 423, 475);
			contentStream2.moveTextPositionByAmount(230, 464);
			contentStream2.drawString(new StringBuffer("  Particulars")
					.toString());

			// row1 column3
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 423, 461, 538, 475);
			contentStream2.moveTextPositionByAmount(455, 464);
			contentStream2
					.drawString(new StringBuffer("  Amount()").toString());

			// row2 column1
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 65, 447, 117, 461);
			contentStream2.moveTextPositionByAmount(67, 450);
			contentStream2.drawString(new StringBuffer("  I").toString());

			// row2 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 447, 423, 461);
			contentStream2.moveTextPositionByAmount(119, 450);
			contentStream2.drawString(new StringBuffer(" LTA amount received:")
					.toString());

			// row2 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 447, 538, 461);
			/*contentStream2.moveTextPositionByAmount(455, 450);
			contentStream2.drawString(ltaEmployee.getApprovedAmt().toString());*/

			// row3 column1
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 65, 433, 117, 447);
			contentStream2.moveTextPositionByAmount(67, 436);
			contentStream2.drawString(new StringBuffer("  II ").toString());

			// row3 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 433, 423, 447);
			contentStream2.moveTextPositionByAmount(119, 436);
			contentStream2.drawString(new StringBuffer(
					" Benefit under Income-tax (Rule 2B) ").toString());

			// row3 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 433, 538, 447);

			// row4 column1
			drawCell(document, contentStream2, 65, 419, 117, 433);

			// row4 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 419, 423, 433);
			contentStream2.moveTextPositionByAmount(119, 422);
			contentStream2.drawString(new StringBuffer(
					" a) Actual fare incurred: ").toString());

			// row4 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 419, 538, 433);
			/*contentStream2.moveTextPositionByAmount(455, 422);
			contentStream2.drawString(ltaEmployee.getActualFare().toString());*/

			// row5 column1
			drawCell(document, contentStream2, 65, 405, 117, 419);

			// row5 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 405, 423, 419);
			contentStream2.moveTextPositionByAmount(119, 408);
			contentStream2
					.drawString(new StringBuffer(
							" b) Amount under 6(b) / 7(c) / 8(c) / 8(d) as applicable: ")
							.toString());

			// row5 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 405, 538, 419);
			/*contentStream2.moveTextPositionByAmount(455, 408);
			contentStream2.drawString(ltaEmployee.getShortestFare().toString());*/

			// row6 column1
			drawCell(document, contentStream2, 65, 391, 117, 405);

			// row6 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 391, 423, 405);
			contentStream2.moveTextPositionByAmount(119, 394);
			contentStream2.drawString(new StringBuffer(
					" c) Income Tax benefit: ").toString());

			// row6 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 391, 538, 405);
			contentStream2.moveTextPositionByAmount(455, 394);

			// row7 column1
			drawCell(document, contentStream2, 65, 377, 117, 391);

			// row7 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 377, 423, 391);
			contentStream2.moveTextPositionByAmount(119, 380);
			contentStream2.drawString(new StringBuffer(
					" [Lower of (a) & (b)]  ").toString());

			// row7 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 377, 538, 391);
			contentStream2.moveTextPositionByAmount(455, 380);
			
			/*BigDecimal actualFare = ltaEmployee.getActualFare();
			BigDecimal shortestFare = ltaEmployee.getShortestFare();
			
			if(actualFare.compareTo(shortestFare) == -1)
			{
				contentStream2.drawString(ltaEmployee.getActualFare().toString());
			}
			
			else
			{
				contentStream2.drawString(ltaEmployee.getShortestFare().toString());
			}*/

			// row8 column1
			contentStream2.setFont(headingFont1, 11);
			drawCell(document, contentStream2, 65, 363, 117, 377);
			contentStream2.moveTextPositionByAmount(67, 366);
			contentStream2.drawString(new StringBuffer("  III  ").toString());

			// row8 column2
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 117, 363, 423, 377);
			contentStream2.moveTextPositionByAmount(119, 366);
			contentStream2.drawString(new StringBuffer(
					" Taxable LTA [I - II(c)]:  ").toString());

			// row8 column3
			contentStream2.setFont(headingFont2, 11);
			drawCell(document, contentStream2, 423, 363, 538, 377);
			
			contentStream2.moveTextPositionByAmount(455, 366);
			
			/*BigDecimal approvedAmount = ltaEmployee.getApprovedAmt();
			BigDecimal taxable = approvedAmount.subtract(shortestFare);
			contentStream2.drawString(taxable.toString());
*/
			contentStream2.endText();

			contentStream2.drawLine(70, 349, 533, 349);

			contentStream2.setFont(headingFont1, 11);
			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(210, 337);
			contentStream2.drawString(new StringBuffer(
					"DECLARATION BY EMPLOYEE  ").toString());
			contentStream2.endText();

			contentStream2.setFont(headingFont2, 11);
			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 305);
			contentStream2
					.drawString(new StringBuffer(
							"I declare that the expenditure mentioned under (9) above has been actually incurred by me and I am in")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 291);
			contentStream2
					.drawString(new StringBuffer(
							"possession of the proof of having travelled (this includes applicable tickets, invoices, boarding passes,")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 277);
			contentStream2.drawString(new StringBuffer("etc.).").toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 249);
			contentStream2
					.drawString(new StringBuffer(
							"I confirm that the exemption under section 10(5) of the Income-tax Act, 1961 (the Act) claimed by me is ")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 235);
			contentStream2
					.drawString(new StringBuffer(
							"in accordance with the sub-rule (2) of Rule 2B of the Income-tax Rules, 1962 (the Rules) as per which the ")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 221);
			contentStream2
					.drawString(new StringBuffer(
							"exemption can be claimed only for two journeys in a block of four calendar years commencing from")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 207);
			contentStream2.drawString(new StringBuffer("1986.").toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 179);
			contentStream2
					.drawString(new StringBuffer(
							"I undertake to provide any further evidence of the journey performed when called upon to do so by the")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 165);
			contentStream2
					.drawString(new StringBuffer(
							"Income-Tax Authorities at the time of my / Company Tax Assessment .")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 137);
			contentStream2
					.drawString(new StringBuffer(
							"I confirm that I shall be fully responsible to the Company for any income-tax liability including interest,")
							.toString());
			contentStream2.endText();

			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 123);
			contentStream2
					.drawString(new StringBuffer(
							"penalty etc. that may arise on account of non-fulfillment of this undertaking in-toto or in part by me. ")
							.toString());
			contentStream2.endText();

			contentStream2.setFont(headingFont1, 11);
			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(70, 80);
			contentStream2.drawString(new StringBuffer("Date: ").toString());
			contentStream2.endText();

			contentStream2.setFont(headingFont1, 11);
			contentStream2.beginText();
			contentStream2.moveTextPositionByAmount(470, 80);
			contentStream2.drawString(new StringBuffer("SIGNATURE").toString());
			contentStream2.endText();

			contentStream.close();
			contentStream2.close();
			dir=BenefitsConstants.PDF_REPORT_DIR;
			fileName="LTA_Employee_"+ltaEmployee.getLtaEmployeeId();
			document.save(dir + "/" + fileName);
			document.close();
			System.out.println("Saved to" + dir + "/" + fileName);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}

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
