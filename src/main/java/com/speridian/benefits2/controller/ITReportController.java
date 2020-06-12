
package com.speridian.benefits2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.speridian.benefits2.model.pojo.EmpRentDetail;
import com.speridian.benefits2.model.pojo.ITInvestmentField;

import com.speridian.benefits2.model.pojo.ITEmployee;
import com.speridian.benefits2.model.pojo.ITEmployeeHouseLoan;
import com.speridian.benefits2.model.pojo.ITEmployeeInvestment;
import com.speridian.benefits2.model.pojo.ItHouseLoanField;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.ITReportService;
import com.speridian.benefits2.util.AppContext;

@Controller
public class ITReportController {

	@Autowired
	ITReportService iTReportService;

	AppContext appContext;

	@RequestMapping(value = "/home/test/report/declare", method = RequestMethod.POST)
	public void downloadReportDeclare(HttpServletRequest request, HttpServletResponse response) throws Exception {

		appContext = (AppContext) request.getSession().getAttribute("appContext");

		List<ITEmployee> emplists = iTReportService.listAllYearlyEmployeeDetails(appContext.getCurrentFiscalYear(),
				BenefitsConstants.IT_MODE_DECLARATION);

		downloadReports(emplists, response);

	}

	@RequestMapping(value = "/home/test/report/proof", method = RequestMethod.POST)
	public void downloadReportProof(HttpServletRequest request, HttpServletResponse response) throws Exception {

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		List<ITEmployee> emplists = iTReportService.listAllYearlyEmployeeDetails(appContext.getCurrentFiscalYear(),
				BenefitsConstants.IT_MODE_PROOF_SUBMISSION);

		downloadReports(emplists, response);

	}

	public void downloadReports(List<ITEmployee> emplists, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
			List<String> headers = new ArrayList<String>();
			List<ITEmployeeHouseLoan> dataValues = new ArrayList<ITEmployeeHouseLoan>();
			List<String> houseloanValues = new ArrayList<String>();
			List<String> investmentValues = new ArrayList<String>();
			List<ITEmployeeInvestment> investmentDataValues = new ArrayList<ITEmployeeInvestment>();
			List<String> headers2 = new ArrayList<String>();

			headers2.add("Employee Code");
			headers2.add("Employee Name");
			headers2.add("PAN No.");
			headers2.add("Gender");
			headers2.add("Mobile No.");
			headers2.add("Exten.");
			headers2.add("Phone No.");
			headers2.add("Email Id");
			headers2.add("Location of Posting");

			List<ItHouseLoanField> hlfieldlists = iTReportService.listAllHouseLoanFieldsData();
			List<ITInvestmentField> investmentlist = iTReportService.listAllInvestmentFieldsData();

			for (ItHouseLoanField listhlfield : hlfieldlists) {
				if (!listhlfield.getFieldLabel().equals(BenefitsConstants.IT_SECTION_LOAN_FIELD_TYPE_DOC)) {
					headers2.add(listhlfield.getFieldLabel());
					// headers2.add(listhlfield.getFieldLabel() + "- PROOF");
				}
			}
			for (ITInvestmentField listinvestfield : investmentlist) {
				headers2.add(listinvestfield.getDescription());
				// headers2.add(listinvestfield.getDescription() + "- PROOF");
			}
			/*
			 * if (headers == null) {
			 * System.out.println("headers sql list is null");
			 * 
			 * } else { headers2.addAll(headers); }
			 */

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadSheet = workbook.createSheet("sheet1");
			int count = 0;
			System.out.println("*******First sheet **********n");
			XSSFRow row1 = spreadSheet.createRow(0);
			for (String header : headers2) {

				XSSFCell empCodeCellHead = row1.createCell(count);
				empCodeCellHead.setCellValue(header);
				spreadSheet.autoSizeColumn(count);
				count++;

			}

			int i = count;
			int rentNumber = 1;
			while (i <= count + 30) {

				XSSFCell rentPeriodCell = row1.createCell(i);
				rentPeriodCell.setCellValue("rent period " + rentNumber);
				spreadSheet.autoSizeColumn(i);
				i++;

				XSSFCell monthlyRentCell = row1.createCell(i);
				monthlyRentCell.setCellValue("Monthly Rent " + rentNumber);
				spreadSheet.autoSizeColumn(i);
				i++;

				XSSFCell noOfMonthsCell = row1.createCell(i);
				noOfMonthsCell.setCellValue("No. Of Months " + rentNumber);
				spreadSheet.autoSizeColumn(i);
				i++;

				XSSFCell metroCityCell = row1.createCell(i);
				metroCityCell.setCellValue("Metro City " + rentNumber);
				spreadSheet.autoSizeColumn(i);
				i++;

				XSSFCell totalRentCell = row1.createCell(i);
				totalRentCell.setCellValue("Total Rent " + rentNumber);
				spreadSheet.autoSizeColumn(i);
				i++;

				/*
				 * XSSFCell totalRentCellProof = row1.createCell(i);
				 * totalRentCellProof.setCellValue("Total Rent at Proof " +
				 * rentNumber); spreadSheet.autoSizeColumn(i); i++;
				 */

				XSSFCell landlordCell = row1.createCell(i);
				landlordCell.setCellValue("Landlord " + rentNumber);
				spreadSheet.autoSizeColumn(i);
				i++;

				rentNumber++;
			}

			System.out.println("*********Headers printed*******");
			// Giving values from the pojo

			int rowIndex = 1;

			for (ITEmployee listemp : emplists) {
				System.out.println("empname:" + listemp.getEmployeeName());
				XSSFRow row2 = spreadSheet.createRow(rowIndex);

				XSSFCell empCodeCell = row2.createCell(0);
				try{
				empCodeCell.setCellValue(listemp.getEmployee().getEmployeeCode());
				}catch (Exception e) {
					empCodeCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(0);

				XSSFCell empNameCell = row2.createCell(1);
				try{
				empNameCell.setCellValue(listemp.getEmployeeName());
				}catch (Exception e) {
					empNameCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(1);

				XSSFCell panNoCell = row2.createCell(2);
				try{
				panNoCell.setCellValue(listemp.getPan());
				}catch (Exception e) {
					panNoCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(2);

				XSSFCell genderCell = row2.createCell(3);
				try{
				genderCell.setCellValue(listemp.getEmployee().getGender());
				}catch (Exception e) {
					genderCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(3);

				XSSFCell mobileNoCell = row2.createCell(4);
				try{
				mobileNoCell.setCellValue(listemp.getPersonalMobileNumber());
				}catch (Exception e) {
					mobileNoCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(4);

				XSSFCell extenCell = row2.createCell(5);
				try{
				extenCell.setCellValue(listemp.getOfficePhoneNumberExt());
				}catch (Exception e) {
					extenCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(5);
				XSSFCell phoneNoCell = row2.createCell(6);
				try{
				phoneNoCell.setCellValue(listemp.getOfficePhoneNumber());
				}catch (Exception e) {
					phoneNoCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(6);

				XSSFCell emailIdCell = row2.createCell(7);
				try{
				emailIdCell.setCellValue(listemp.getEmployee().getEmail());
				}catch (Exception e) {
					emailIdCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(7);

				XSSFCell locationCell = row2.createCell(8);
				try{
				locationCell.setCellValue(listemp.getEmployee().getParentOffice());
				}catch (Exception e) {
					locationCell.setCellValue("");
				}
				spreadSheet.autoSizeColumn(8);

				int columnSize = 9;

				for (ItHouseLoanField listhlfield : hlfieldlists) {
					int hlfieldId = listhlfield.getHouseLoadFieldId();
					dataValues = iTReportService.listAllHouseLoanDataValues(hlfieldId, listemp.getItEmployeeId());

					XSSFCell empcell1 = row2.createCell(columnSize);
					XSSFCell empcell2 = row2.createCell(columnSize + 1);
					for (ITEmployeeHouseLoan houseloanData : dataValues) {
						/*
						 * add the loan fields to the file( not with type doc)
						 */
						try {
							if (!houseloanData.getItHouseLoanField().getType()
									.equals(BenefitsConstants.IT_SECTION_LOAN_FIELD_TYPE_DOC)) {
								
								if(houseloanData.getItHouseLoanField().getType()
									.equals(BenefitsConstants.IT_SECTION_LOAN_FIELD_TYPE_DATE)){
									Date date;
									
									empcell1.setCellValue(dateFormat.format(houseloanData.getInvestmentsFieldDateValue()));
								}else{
								empcell1.setCellValue(houseloanData.getInvestmentsFieldValue().toString());
								}
								// empcell2.setCellValue(houseloanData.getInvestmentsFieldValueProof().toString());
							}
						} catch (Exception e) {
						}
					}
					spreadSheet.autoSizeColumn(columnSize);
					columnSize++;
					/*
					 * spreadSheet.autoSizeColumn(columnSize); columnSize++;
					 */
				}
				int columnindex = columnSize;

				for (ITInvestmentField listinvestfield : investmentlist) {
					int investmentId = listinvestfield.getInvestmentId();
					investmentDataValues = iTReportService.listAllInvestmentDataValues(investmentId,
							listemp.getItEmployeeId());

					XSSFCell empdatacell1 = row2.createCell(columnindex);
					XSSFCell empdatacell2 = row2.createCell(columnindex + 1);
					for (ITEmployeeInvestment investmentData : investmentDataValues) {
						/*
						 * add investment data of employee to the file
						 */
						try {
							empdatacell1.setCellValue(investmentData.getInvestmentAmount().toString());
							// empdatacell2.setCellValue(investmentData.getInvestmentAmountProof().toString());
						} catch (NullPointerException e) {
						}
					}
					spreadSheet.autoSizeColumn(columnindex);
					columnindex++;
					/*
					 * spreadSheet.autoSizeColumn(columnindex); columnindex++;
					 */
				}

				rowIndex++;
				List<EmpRentDetail> empRentDetails = iTReportService
						.listAllRentDetailsByEmpIdAndFY(listemp.getItEmployeeId(), appContext.getCurrentFiscalYear());

				if (empRentDetails != null) {
					int j = columnindex;

					BigDecimal total = new BigDecimal(0);

					for (EmpRentDetail rentDetails : empRentDetails) {
						try {
							String mnthfrom = rentDetails.getFromPeriod().split("_")[0];
							String mnthTo = rentDetails.getToPeriod().split("_")[0];
							mnthfrom = getMonthBasedOnIndex(mnthfrom);
							mnthTo = getMonthBasedOnIndex(mnthTo);
							XSSFCell rentPeriodCell = row2.createCell(j);
							rentPeriodCell.setCellValue(mnthfrom + "_" + rentDetails.getFromPeriod().split("_")[1]
									+ " - " + mnthTo + "_" + rentDetails.getToPeriod().split("_")[1]);
							spreadSheet.autoSizeColumn(j);
							j++;

							XSSFCell monthlyRentCell = row2.createCell(j);
							monthlyRentCell.setCellValue(rentDetails.getMonthlyRent());
							spreadSheet.autoSizeColumn(j);
							j++;

							XSSFCell noOfMonthsCell = row2.createCell(j);
							noOfMonthsCell.setCellValue(rentDetails.getNoOfMonths());
							spreadSheet.autoSizeColumn(j);
							j++;

							XSSFCell metroCityCell = row2.createCell(j);
							metroCityCell.setCellValue(rentDetails.getMetroCity());
							spreadSheet.autoSizeColumn(j);
							j++;
							/*
							 * add employee rent total amounts to the file in
							 * below 2 cells
							 */
							XSSFCell totalRentCell = row2.createCell(j);
							totalRentCell.setCellValue(rentDetails.getTotalRent());
							spreadSheet.autoSizeColumn(j);
							j++;

							/*
							 * XSSFCell totalRentCellProof = row2.createCell(j);
							 * try {
							 * totalRentCellProof.setCellValue(rentDetails.
							 * getTotalRentProof()); } catch (Exception e) {
							 * totalRentCellProof.setCellValue(0); }
							 * spreadSheet.autoSizeColumn(j); j++;
							 */

							XSSFCell landlordCell = row2.createCell(j);
							landlordCell.setCellValue(rentDetails.getPan());
							spreadSheet.autoSizeColumn(j);
							j++;

							total = total.add(new BigDecimal(rentDetails.getTotalRent()));

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					XSSFCell totalRent = row2.createCell(j);
					totalRent.setCellValue("");
					spreadSheet.autoSizeColumn(j);

				}

			}
			
			File file = new File(BenefitsConstants.XLS_REPORT_DIR + "/ITDeclaration"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".xlsx");
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();

			response.setContentType("application/vnd.ms-excel");
			// response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=ITDeclaration.xlsx");
			response.setContentLength((int) file.length());

			try {
				// response.reset();
				FileInputStream fileInputStream = new FileInputStream(file);
				OutputStream responseOutputStream = response.getOutputStream();
				int bytes;
				while ((bytes = fileInputStream.read()) != -1) {
					responseOutputStream.write(bytes);
				}
				fileInputStream.close();
				responseOutputStream.flush();
				responseOutputStream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMonthBasedOnIndex(String index) {

		switch (Integer.parseInt(index))

		{
		case 1:
			return "Jan";

		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "Aug";
		case 9:
			return "Sept";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";

		}
		return "";
	}
}