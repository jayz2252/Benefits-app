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
import com.itextpdf.text.pdf.PdfWriter;
import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanClaimDetail;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.writers.ReportTypes;
import com.speridian.benefits2.writers.BenefitsWriter;

public class VehicleEnrollmentPdfReport implements BenefitsWriter<BenefitPlanClaim> {

	private String filename;

	DateFormat dateFormat;

	BenefitPlanClaim benefitPlanClaim;

	Map<String, Object> details;

	public VehicleEnrollmentPdfReport() {
		dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		details = new HashMap<String, Object>();
	}

	public String write(BenefitPlanClaim benefitPlanClaim) throws FileNotFoundException, DocumentException {
		this.benefitPlanClaim = benefitPlanClaim;
		Document document = new Document();
		document.setPageCount(10);
		filename = PDFUtils.createPdf(ReportTypes.VEHICLE_ENROLLMENT_PDF_REPORT);
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
		PDFUtils.addMainHeading(document,
				"                                    Employee Claim Report - Customer copy " + "\n");
		PDFUtils.addImportantText(document,
				"                                                                 Vehicle Running and Maintenance\n\n ");

		PdfPTable masterTable = PDFUtils.createTable(2);

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Employee Name" });
		PDFUtils.addRow(masterTable, new String[] { benefitPlanClaim.getPlanEmployee().getEmployee().getFirstName()
				+ " " + benefitPlanClaim.getPlanEmployee().getEmployee().getLastName() });

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Employee Code" });
		PDFUtils.addRow(masterTable,
				new String[] { benefitPlanClaim.getPlanEmployee().getEmployee().getEmployeeCode() });

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Designation" });
		PDFUtils.addRow(masterTable,
				new String[] { benefitPlanClaim.getPlanEmployee().getEmployee().getDesignationName() });

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Location" });
		PDFUtils.addRow(masterTable,
				new String[] { benefitPlanClaim.getPlanEmployee().getEmployee().getParentOffice() });

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Claim Ref No" });
		PDFUtils.addRow(masterTable, new String[] { benefitPlanClaim.getClaimRefNo() });

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Status" });
		if (benefitPlanClaim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_HR_APPR)
				|| benefitPlanClaim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_FIN_APPR)) {
			PDFUtils.addRow(masterTable, new String[] { "Approved" });
		} else {
			PDFUtils.addRow(masterTable, new String[] { "Submitted" });
		}

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Requested Date" });
		PDFUtils.addRow(masterTable, new String[] { dateFormat.format(benefitPlanClaim.getSubmittedDate()) });

		PDFUtils.addHeaderDataRow(masterTable, new String[] { "Claim Period" });
		if ((benefitPlanClaim.getPeriodFrom() != null) && !(benefitPlanClaim.getPeriodFrom().equals(""))) {
			if ((benefitPlanClaim.getPeriodTo() != null) && !(benefitPlanClaim.getPeriodTo().equals(""))) {
				PDFUtils.addRow(masterTable, new String[] { dateFormat.format(benefitPlanClaim.getPeriodFrom())
						+ " to " + dateFormat.format(benefitPlanClaim.getPeriodTo()) });
			} else {
				PDFUtils.addRow(masterTable, new String[] { dateFormat.format(benefitPlanClaim.getPeriodFrom()) });
			}
		} else {
			PDFUtils.addRow(masterTable, new String[] { "Not Available" });
		}

		document.add(masterTable);
		PDFUtils.addNotesText(document, " ");

		List<BenefitPlanClaimDetail> benefitPlanClaimDetails = (List<BenefitPlanClaimDetail>) details
				.get(DETAIL_BENEFIT_PLAN_VEHICLE);

		if (benefitPlanClaimDetails != null && !benefitPlanClaimDetails.isEmpty()) {

			PdfPTable claimTable = PDFUtils.createTable(6);

			PDFUtils.addTableHeader(claimTable, new String[] { "Dependent", "Relationship With Employee", "Bill No.",
					"Shop/Hospital", "Bill Date", "Amount" });

			benefitPlanClaimDetails.forEach(claim -> {
				String dependentName = null;
				String relationShip = null;
				if (claim.getDependent() == null) {
					dependentName = "Not Applicable";

				} else {
					dependentName = claim.getDependent().getDependentName();
				}
				if (claim.getDependent() == null) {
					relationShip = "Not Applicable";
				} else {
					relationShip = claim.getDependent().getRelationship();

				}
				PDFUtils.addRow(claimTable,
						new String[] { dependentName, relationShip, claim.getBillNo(), claim.getBillIssuer(),
								dateFormat.format(claim.getBillDate()), claim.getRequestedAmount().toString() });
			});
			document.add(claimTable);
			PDFUtils.addNotesText(document,
					"\n" + '"'
							+ "I hereby certify that the bills submitted are true and correct to the best of my knowledge. Any misrepresentation, "
							+ "falsification of information on this application may result in the failure of acceptance of these bills or subject to punitive or corrective actions as "
							+ "by this submission I am certifying and agreeing to the above. This action takes the place of my signature."
							+ '"');
			PDFUtils.addNotesText(document, " ");
			PDFUtils.addImportantText(document, benefitPlanClaim.getPlanEmployee().getEmployee().getFirstName() + " "
					+ benefitPlanClaim.getPlanEmployee().getEmployee().getLastName());
		}

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
	public String getFilename() {
		return filename;
	}

	@Override
	public String write(BenefitPlanClaim master, Boolean passWordRequired)
			throws FileNotFoundException, DocumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
