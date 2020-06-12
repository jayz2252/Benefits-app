package com.speridian.benefits2.controller;

/**
 * @author minnu.john
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.INSPlanEmployee;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeDetails;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.re.BenefitPlanRE;
import com.speridian.benefits2.service.BenefitPlanService;
import com.speridian.benefits2.service.EmployeeService;
import com.speridian.benefits2.service.INSPlanEmployeeService;
import com.speridian.benefits2.service.InsuranceService;
import com.speridian.benefits2.service.PFEmployeeService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.ws.client.mirror.soap.MirrorDataService;
import com.speridian.benefits2.ws.client.mirror.soap.ObjectFactory;
import com.speridian.benefits2.ws.client.mirror.types.GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020List;
import com.speridian.benefits2.ws.client.mirror.types.GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResponse;
import com.speridian.benefits2.ws.client.mirror.types.ServiceAuthenticationHeader;


@Controller
public class PlanReportController {

	ServiceAuthenticationHeader header;

	MirrorDataService mirrorDataService = new MirrorDataService();

	@Autowired
	BenefitPlanService benefitPlanService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	BenefitPlanRE benefitPlanRE;

	@Autowired
	PFEmployeeService pfEmployeeService;

	@Autowired
	INSPlanEmployeeService insPlanEmployeeService;

	@Autowired
	InsuranceService insuranceService;

	AppContext appContext = null;

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees/report", method = RequestMethod.POST)
	public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response)

			throws IOException, FileNotFoundException {
		// create a blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("sheet1");

		// creating rows and cells for headings
		XSSFRow row1 = spreadsheet.createRow(0);

		XSSFCell fyCellHead = row1.createCell(0);
		fyCellHead.setCellValue("FY");

		XSSFCell empCodeCellHead = row1.createCell(1);
		empCodeCellHead.setCellValue("Employee Code");

		XSSFCell empNameCellHead = row1.createCell(2);
		empNameCellHead.setCellValue("Employee Name");

		XSSFCell optedDateCellHead = row1.createCell(3);
		optedDateCellHead.setCellValue("Opted Date");

		XSSFCell categoryCellHead = row1.createCell(4);
		categoryCellHead.setCellValue("Category");

		XSSFCell statusCellHead = row1.createCell(5);
		statusCellHead.setCellValue("Status");

		ModelAndView mav = new ModelAndView("benefitOptedEmployees");
		List<BenefitPlanEmployee> planEmployees = new ArrayList<BenefitPlanEmployee>();
		String planEmployeeId = request.getParameter("docIds");
		if (planEmployeeId != null && !planEmployeeId.isEmpty()) {
			planEmployeeId = planEmployeeId.substring(0, planEmployeeId.length() - 1);
			System.out.println("----------------inside report--------");
			System.out.println("----------------docIds are--------" + planEmployeeId);
			String[] planEmployeeIds = planEmployeeId.split(",");
			for (String id : planEmployeeIds) {
				BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getPlanEmployee(Integer.parseInt(id));
				planEmployees.add(benefitPlanEmployee);
			}
		}
		try {
			// inserting values to cell
			int rowIndex = 1;
			appContext = (AppContext) request.getSession().getAttribute("appContext");

			for (BenefitPlanEmployee planEmployee : planEmployees) {

				XSSFRow row = spreadsheet.createRow(rowIndex);

				XSSFCell fyCell = row.createCell(0);
				fyCell.setCellValue(planEmployee.getFiscalYear());

				spreadsheet.autoSizeColumn(0);

				XSSFCell empCodeCell = row.createCell(1);
				empCodeCell.setCellValue(planEmployee.getEmployee().getEmployeeCode());
				spreadsheet.autoSizeColumn(1);

				XSSFCell empNameCell = row.createCell(2);
				empNameCell.setCellValue(
						planEmployee.getEmployee().getFirstName() + " " + planEmployee.getEmployee().getLastName());
				spreadsheet.autoSizeColumn(2);

				XSSFCell optedDateCell = row.createCell(3);

				DateFormat df = new SimpleDateFormat("dd-MMMM-yyy");
				String sdt = df.format(planEmployee.getOptedDate());

				optedDateCell.setCellValue(sdt);
				spreadsheet.autoSizeColumn(3);

				XSSFCell categoryCell = row.createCell(4);
				if (planEmployee.getPlanCategory() == null) {
					categoryCell.setCellValue("Nil");
					spreadsheet.autoSizeColumn(4);
				} else {
					categoryCell.setCellValue(planEmployee.getPlanCategory().getCategoryName());
					spreadsheet.autoSizeColumn(4);
				}

				XSSFCell statusCell = row.createCell(5);
				if (planEmployee.getStatus().equals("NO_APPR")) {
					statusCell.setCellValue("Not Approved");
					spreadsheet.autoSizeColumn(5);
				} else {
					statusCell.setCellValue("Approved");
					spreadsheet.autoSizeColumn(5);
				}

				rowIndex++;
			}
		} catch (HibernateException he) {
			he.printStackTrace();
		}

		File file = new File(BenefitsConstants.XLS_REPORT_DIR + "/OptedEmployees"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();

		response.setContentType("application/vnd.ms-excel");
		// response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addHeader("Content-Disposition", "attachment; filename=optedemployees.xlsx");
		response.setContentLength((int) file.length());

		try {
			response.reset();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/reports/employee/search/download/{employeeId}", method = RequestMethod.GET)
	public ModelAndView downloadEmployeePlans(@PathVariable(value = "employeeId") Integer employeeId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		String fiscalYear = appContext.getCurrentFiscalYear();

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "searchEmployee");
		Employee employee = employeeService.get(employeeId);
		List<BenefitPlanEmployee> benefitPlanEmployees = new ArrayList<BenefitPlanEmployee>();
		List<BenefitPlanEmployee> newbenefitPlanEmployees = new ArrayList<BenefitPlanEmployee>();

		benefitPlanEmployees = employeeService.listPlanEmployee(employeeId, fiscalYear, true);
		for (BenefitPlanEmployee benefitPlanEmployee : benefitPlanEmployees) {
			benefitPlanEmployee = benefitPlanRE.calculateAmount(benefitPlanEmployee);
			newbenefitPlanEmployees.add(benefitPlanEmployee);
		}
		// create a blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// SHEET 1
		// create a blank sheet
		XSSFSheet spreadsheet0 = workbook.createSheet("Overview");

		// creating rows and cells for headings
		XSSFRow row0 = spreadsheet0.createRow(0);
		XSSFCell empCodeRowHead = row0.createCell(0);
		empCodeRowHead.setCellValue("Employee ID");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell empCodeValue = row0.createCell(1);
		empCodeValue.setCellValue(employee.getEmployeeId());
		spreadsheet0.autoSizeColumn(1);

		XSSFRow row1 = spreadsheet0.createRow(1);
		XSSFCell empNameRowHead = row1.createCell(0);
		empNameRowHead.setCellValue("Employee Name");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell empNameValue = row1.createCell(1);
		empNameValue.setCellValue(employee.getFirstName() + " " + employee.getLastName());
		spreadsheet0.autoSizeColumn(1);

		XSSFRow row2 = spreadsheet0.createRow(2);
		XSSFCell departmentRowHead = row2.createCell(0);
		departmentRowHead.setCellValue("Department");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell departmentValue = row2.createCell(1);
		departmentValue.setCellValue(employee.getDepartmentName());
		spreadsheet0.autoSizeColumn(1);

		XSSFRow row3 = spreadsheet0.createRow(3);
		XSSFCell designationRowHead = row3.createCell(0);
		designationRowHead.setCellValue("Designation");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell designationValue = row3.createCell(1);
		designationValue.setCellValue(employee.getDesignationName());
		spreadsheet0.autoSizeColumn(1);

		XSSFRow row4 = spreadsheet0.createRow(4);
		XSSFCell bandRowHead = row4.createCell(0);
		bandRowHead.setCellValue("Band");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell bandValue = row4.createCell(1);
		bandValue.setCellValue(employee.getBand());
		spreadsheet0.autoSizeColumn(1);

		XSSFRow row5 = spreadsheet0.createRow(5);
		XSSFCell mobileRowHead = row5.createCell(0);
		mobileRowHead.setCellValue("Mobile No.");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell mobileValue = row5.createCell(1);
		mobileValue.setCellValue(employee.getMobileNo());
		spreadsheet0.autoSizeColumn(1);

		XSSFRow row6 = spreadsheet0.createRow(6);
		XSSFCell emailRowHead = row6.createCell(0);
		emailRowHead.setCellValue("Email Id");
		spreadsheet0.autoSizeColumn(0);
		XSSFCell emailValue = row6.createCell(1);
		emailValue.setCellValue(employee.getEmail());
		spreadsheet0.autoSizeColumn(1);

		// PLAN SHEETS
		int i = 2;
		for (BenefitPlanEmployee benefitPlanEmployee : benefitPlanEmployees) {

			XSSFSheet spreadsheet1 = workbook.createSheet(benefitPlanEmployee.getBenefitPlan().getPlanName());

			XSSFRow row7 = spreadsheet1.createRow(0);
			XSSFCell planNameHead1 = row7.createCell(0);
			planNameHead1.setCellValue("Plan Name");
			spreadsheet1.autoSizeColumn(0);
			XSSFCell planNameValue1 = row7.createCell(1);
			planNameValue1.setCellValue(benefitPlanEmployee.getBenefitPlan().getPlanName());
			spreadsheet1.autoSizeColumn(1);

			XSSFRow row8 = spreadsheet1.createRow(1);
			XSSFCell planDescHead = row8.createCell(0);
			planDescHead.setCellValue("Plan Desciption");
			spreadsheet1.autoSizeColumn(0);
			XSSFCell planDescValue = row8.createCell(1);
			planDescValue.setCellValue(benefitPlanEmployee.getBenefitPlan().getPlanDesc());
			spreadsheet1.autoSizeColumn(1);

			XSSFRow row9 = spreadsheet1.createRow(2);
			XSSFCell claimTypeHead = row9.createCell(0);
			claimTypeHead.setCellValue("Claim Type");
			spreadsheet1.autoSizeColumn(0);
			XSSFCell claimTypeValue = row9.createCell(1);
			claimTypeValue.setCellValue(benefitPlanEmployee.getBenefitPlan().getClaimType());
			spreadsheet1.autoSizeColumn(1);

			List<BenefitPlanClaim> benefitPlanClaims = benefitPlanService
					.listClaims(benefitPlanEmployee.getPlanEmployeeId());

			// Headers Amount
			XSSFRow row9A = spreadsheet1.createRow(4);
			XSSFCell claimRefNoHead = row9A.createCell(0);
			claimRefNoHead.setCellValue("Claim Ref: No.");
			spreadsheet1.autoSizeColumn(0);

			XSSFCell subDateHead = row9A.createCell(1);
			subDateHead.setCellValue("Submitted Date");
			spreadsheet1.autoSizeColumn(1);

			XSSFCell appDateHead = row9A.createCell(2);
			appDateHead.setCellValue("Approved Date");
			spreadsheet1.autoSizeColumn(2);

			XSSFCell reqAmtHead = row9A.createCell(3);
			reqAmtHead.setCellValue("Total Requested Amount");
			spreadsheet1.autoSizeColumn(3);

			XSSFCell appAmtHead = row9A.createCell(4);
			appAmtHead.setCellValue("Total Approved Amount");
			spreadsheet1.autoSizeColumn(4);

			// Values
			int j = 1;
			for (BenefitPlanClaim benefitPlanClaim : benefitPlanClaims) {
				XSSFRow row9B = spreadsheet1.createRow(4 + j);
				XSSFCell claimRefNoValue = row9B.createCell(0);
				claimRefNoValue.setCellValue(benefitPlanClaim.getClaimRefNo());
				spreadsheet1.autoSizeColumn(0);

				DateFormat df = new SimpleDateFormat("dd-MMMM-yyy");
				String sdt = df.format(benefitPlanClaim.getSubmittedDate());
				XSSFCell subDateValue = row9B.createCell(1);
				subDateValue.setCellValue(sdt);
				spreadsheet1.autoSizeColumn(1);

				String sdt1 = df.format(benefitPlanClaim.getApprovedDate());
				XSSFCell appDateValue = row9B.createCell(2);
				appDateValue.setCellValue(sdt1);
				spreadsheet1.autoSizeColumn(2);

				XSSFCell appAmtValue = row9B.createCell(3);
				appAmtValue.setCellValue(String.valueOf(benefitPlanClaim.getTotalApprovedAmount()));
				spreadsheet1.autoSizeColumn(3);

				XSSFCell reqAmtValue = row9B.createCell(4);
				reqAmtValue.setCellValue(String.valueOf(benefitPlanClaim.getTotalRequestedAmount()));
				spreadsheet1.autoSizeColumn(4);
				j++;

			}

			XSSFRow row10 = spreadsheet1.createRow(13);
			XSSFCell totalHead = row10.createCell(0);
			totalHead.setCellValue("Total Amount");
			spreadsheet1.autoSizeColumn(0);

			XSSFCell amtDedValue = row10.createCell(3);
			amtDedValue.setCellValue(String.valueOf(benefitPlanEmployee.getAmountDeducted()));
			spreadsheet1.autoSizeColumn(3);

			XSSFCell amtClaimValue = row10.createCell(4);
			amtClaimValue.setCellValue(String.valueOf(benefitPlanEmployee.getAmountClaimed()));
			spreadsheet1.autoSizeColumn(4);

			i++;

		}

		// Saving into the file
		File file = new File(BenefitsConstants.XLS_REPORT_DIR + "/EmployeeReport"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=Employee Report.xls");
		response.setContentLength((int) file.length());

		try {
			response.reset();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}

	// INSURANCE PLAN REPORT

	@RequestMapping(value = "/home/controlPanel/insurancePlans/viewPlanReport/download/{insPlanId}")
	public ModelAndView downloadInsPlanReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanId") Integer insPlanId) throws IOException {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insPlanReportDownlaod");
		String fiscalYear = appContext.getCurrentInsuranceFiscalYear();
		DateFormat df = new SimpleDateFormat("dd MMM yyyy");
		DateFormat df1 = new SimpleDateFormat("dd/MMM/yyyy");

		// create a blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// SHEET 1
		// create a blank sheet
		XSSFSheet spreadsheet0 = workbook.createSheet("Enrolled Employees");

		// creating rows and cells for headings
		XSSFRow row0 = spreadsheet0.createRow(0);
		XSSFCell numRowHead = row0.createCell(0);
		numRowHead.setCellValue("#");
		spreadsheet0.autoSizeColumn(0);

		XSSFCell empNameRowHead = row0.createCell(1);
		empNameRowHead.setCellValue("Name");
		spreadsheet0.autoSizeColumn(1);

		XSSFCell empCodeRowHead = row0.createCell(2);
		empCodeRowHead.setCellValue("Employee Code");
		spreadsheet0.autoSizeColumn(2);

		XSSFCell empDOJRowHead = row0.createCell(3);
		empDOJRowHead.setCellValue("DOJ");
		spreadsheet0.autoSizeColumn(3);

		XSSFCell parentOffRowHead = row0.createCell(4);
		parentOffRowHead.setCellValue("Parent Office");
		spreadsheet0.autoSizeColumn(4);

		XSSFCell depenNameRowHead = row0.createCell(5);
		depenNameRowHead.setCellValue("Dependent Name");
		spreadsheet0.autoSizeColumn(5);

		XSSFCell depentRelRowHead = row0.createCell(6);
		depentRelRowHead.setCellValue("Relationship");
		spreadsheet0.autoSizeColumn(6);

		XSSFCell dobRowHead = row0.createCell(7);
		dobRowHead.setCellValue("DOB");
		spreadsheet0.autoSizeColumn(7);

		XSSFCell yourContrRowHead = row0.createCell(8);
		yourContrRowHead.setCellValue("Employee Contribution(Yearly)");
		spreadsheet0.autoSizeColumn(8);

		XSSFCell compContrRowHead = row0.createCell(9);
		compContrRowHead.setCellValue("Company Contribution(Yearly)");
		spreadsheet0.autoSizeColumn(9);

		XSSFCell employeeMonthlyContrRowHead = row0.createCell(10);
		employeeMonthlyContrRowHead.setCellValue("Employee Contribution(Monthly)");
		spreadsheet0.autoSizeColumn(10);

		XSSFCell compMonthlyContrRowHead = row0.createCell(11);
		compMonthlyContrRowHead.setCellValue("Company Contribution(Monthly)");
		spreadsheet0.autoSizeColumn(11);

		XSSFCell accCoverageRowHead = row0.createCell(12);
		accCoverageRowHead.setCellValue("Accident Coverage");
		spreadsheet0.autoSizeColumn(12);

		XSSFCell enrolledDateRowHead = row0.createCell(13);
		enrolledDateRowHead.setCellValue("Enrolled Date");
		spreadsheet0.autoSizeColumn(13);

		XSSFCell relievedDateRowHead = row0.createCell(14);
		relievedDateRowHead.setCellValue("Relieved Date");
		spreadsheet0.autoSizeColumn(14);

		XSSFCell buRowHead = row0.createCell(15);
		buRowHead.setCellValue("Business Unit");
		spreadsheet0.autoSizeColumn(15);

		// Dynamic Values

		List<INSPlanEmployee> planEmployees = insPlanEmployeeService.listAllEmp(insPlanId, fiscalYear);

		GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020List buRequest = null;
		header = new ObjectFactory().createServiceAuthenticationHeader();

		int row = 1, count = 1;
		for (INSPlanEmployee planEmployee : planEmployees) {

			XSSFRow rowDetails = spreadsheet0.createRow(row);

			XSSFCell numRowValue = rowDetails.createCell(0);
			numRowValue.setCellValue(count);
			spreadsheet0.autoSizeColumn(0);

			try {

				XSSFCell empNameRowValue = rowDetails.createCell(1);
				empNameRowValue.setCellValue(
						planEmployee.getEmployee().getFirstName() + " " + planEmployee.getEmployee().getLastName());
				spreadsheet0.autoSizeColumn(1);

				XSSFCell empCodeRowValue = rowDetails.createCell(2);
				empCodeRowValue.setCellValue(planEmployee.getEmployee().getEmployeeCode());
				spreadsheet0.autoSizeColumn(2);

				XSSFCell empDOJRowValue = rowDetails.createCell(3);
				String sdf = df.format(planEmployee.getEmployee().getDateOfJoin());
				empDOJRowValue.setCellValue(sdf);
				spreadsheet0.autoSizeColumn(3);

				XSSFCell parentOffRowValue = rowDetails.createCell(4);
				parentOffRowValue.setCellValue(planEmployee.getEmployee().getParentOffice());
				spreadsheet0.autoSizeColumn(4);

				XSSFCell relieveDateRowValue = rowDetails.createCell(14);
				if (planEmployee.getEmployee().getRelievingDate() != null) {
					relieveDateRowValue.setCellValue(df.format(planEmployee.getEmployee().getRelievingDate()));
				} else {
					relieveDateRowValue.setCellValue("Active");
				}
				spreadsheet0.autoSizeColumn(14);

				// For bu value - earlier we were calling Web service to get BU.
				// Now getting from DB.

				/*
				 * buRequest = new
				 * GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020List
				 * (); buRequest.setEmployeeCode(planEmployee.getEmployee()
				 * .getEmployeeCode());
				 * 
				 * GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResponse
				 * buResponse = mirrorDataService
				 * .getMirrorDataServiceSoap().getEmpBuAndProjectDetails(
				 * header, buRequest);
				 * 
				 * String buValue = null; if (buResponse
				 * .getGetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResult
				 * () != null) { buValue = buResponse
				 * .getGetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResult
				 * () .getBOEmployeeDetails().get(0).getBusinessUnitName(); }
				 */

				XSSFCell buValueCell = rowDetails.createCell(15);
				buValueCell.setCellValue(planEmployee.getEmployee().getBusinessUnit());
				spreadsheet0.autoSizeColumn(15);

				BigDecimal totalYC = new BigDecimal(0);
				BigDecimal totalCC = new BigDecimal(0);

				int rowDepns = row + 1;
				List<INSPlanEmployeeDetails> planEmployeeDetails = insPlanEmployeeService
						.listAllPlanEmp(planEmployee.getInsPlanEmployeeId());

				for (INSPlanEmployeeDetails planEmpDetails : planEmployeeDetails) {
					/*
					 * Calculating employee & company deduction
					 */
					BigDecimal depEmpYearlyDeduction = planEmpDetails.getYearlyEmpDeduction();
					if (planEmpDetails.getEaicEnrolled()) {
						/*
						 * checking EAIX condition
						 */
						depEmpYearlyDeduction = depEmpYearlyDeduction
								.add(planEmployee.getInsPlan().getEaicYearlyDeduction());
					}
					totalYC = totalYC.add(depEmpYearlyDeduction);
					totalCC = totalCC.add(planEmpDetails.getYearlyCmpDeduction());

					XSSFRow rowDep = spreadsheet0.createRow(rowDepns);
					XSSFCell depenNameRowValue = rowDep.createCell(5);
					if (planEmpDetails.getDependent() != null) {
						depenNameRowValue.setCellValue(planEmpDetails.getDependent().getDependentName());
					}
					spreadsheet0.autoSizeColumn(5);

					XSSFCell depentRelRowValue = rowDep.createCell(6);
					if (planEmpDetails.getDependent() != null) {
						depentRelRowValue.setCellValue(planEmpDetails.getDependent().getRelationship());
					}
					spreadsheet0.autoSizeColumn(6);

					XSSFCell depentDOBValue = rowDep.createCell(7);
					if (planEmpDetails.getDependent() != null) {
						String sdf1 = df1.format(planEmpDetails.getDependent().getDateOfBirth());
						depentDOBValue.setCellValue(sdf1);
					}
					spreadsheet0.autoSizeColumn(7);

					XSSFCell yourContribution = rowDep.createCell(8);
					yourContribution.setCellValue(depEmpYearlyDeduction.toString());
					spreadsheet0.autoSizeColumn(8);

					XSSFCell compContribution = rowDep.createCell(9);
					compContribution.setCellValue(planEmpDetails.getYearlyCmpDeduction().toString());
					spreadsheet0.autoSizeColumn(9);

					XSSFCell employeeMonthlyContribution = rowDep.createCell(10);
					BigDecimal ded1 = BigDecimal.ZERO;
					if (planEmpDetails.getEaicEnrolled() == true) {
						ded1 = planEmpDetails.getYearlyEaicDeduction().divide(new BigDecimal(12),
								RoundingMode.HALF_EVEN);
					}

					employeeMonthlyContribution.setCellValue(
							depEmpYearlyDeduction.divide(new BigDecimal(12), RoundingMode.HALF_EVEN).toString());

					spreadsheet0.autoSizeColumn(10);

					XSSFCell companyMonthlyContribution = rowDep.createCell(11);
					companyMonthlyContribution.setCellValue(planEmpDetails.getYearlyCmpDeduction()
							.divide(new BigDecimal(12), RoundingMode.HALF_EVEN).toString());
					spreadsheet0.autoSizeColumn(11);

					XSSFCell eaicValue = rowDep.createCell(12);

					if (planEmpDetails.getEaicEnrolled() == true) {
						eaicValue.setCellValue("Yes");
					} else if (planEmpDetails.getEaicEnrolled() == false) {
						eaicValue.setCellValue("No");
					}

					spreadsheet0.autoSizeColumn(12);

					if (planEmployee.getEnrolledDate() != null) {
						XSSFCell enrolledDate = rowDep.createCell(13);
						String enrolDate = df.format(planEmployee.getEnrolledDate());
						enrolledDate.setCellValue(enrolDate);
						spreadsheet0.autoSizeColumn(13);
					} else {
						XSSFCell enrolledDate = rowDep.createCell(13);
					}

					/*
					 * CellStyle styleBody = workbook.createCellStyle(); Font
					 * fontBody = workbook.createFont();
					 * fontBody.setItalic(true);
					 * fontBody.setFontHeightInPoints((short)10);
					 * styleBody.setFont(fontBody);
					 * 
					 * for(int j = 4; j < 11; j++){//For each cell in the row
					 * rowDep.getCell(j).setCellStyle(styleBody);//Set the style
					 * }
					 */

					rowDepns++;
				}

				XSSFCell totalYCCell = rowDetails.createCell(8);
				totalYCCell.setCellValue(totalYC.toString());
				spreadsheet0.autoSizeColumn(8);

				XSSFCell totalCCCell = rowDetails.createCell(9);
				totalCCCell.setCellValue(totalCC.toString());
				spreadsheet0.autoSizeColumn(9);

				BigDecimal ded = totalYC.divide(new BigDecimal(12), RoundingMode.HALF_EVEN);
				XSSFCell totalMonthlyYCCell = rowDetails.createCell(10);
				totalMonthlyYCCell.setCellValue(ded.toString());
				spreadsheet0.autoSizeColumn(10);

				XSSFCell totalMonthlyCCCell = rowDetails.createCell(11);
				totalMonthlyCCCell.setCellValue(totalCC.divide(new BigDecimal(12), RoundingMode.HALF_EVEN).toString());
				spreadsheet0.autoSizeColumn(11);

				XSSFCellStyle styleContent = workbook.createCellStyle();
				styleContent.setFillForegroundColor(new XSSFColor(new java.awt.Color(187, 188, 188)));
				styleContent.setFillPattern(CellStyle.SOLID_FOREGROUND);
				rowDetails.setRowStyle(styleContent);

				row = rowDepns--;
				row++;
				count++;
			} catch (Exception he) {
				he.printStackTrace();
			} finally {

			}
		}

		/*
		 * CellStyle styleHead = workbook.createCellStyle(); Font fontHead =
		 * workbook.createFont(); fontHead.setBoldweight(Font.BOLDWEIGHT_BOLD);
		 * fontHead.setFontHeightInPoints((short) 10);
		 * styleHead.setFont(fontHead);
		 * 
		 * for (int i = 0; i < row0.getLastCellNum(); i++) {
		 * row0.getCell(i).setCellStyle(styleHead); }
		 */

		// Saving into the file

		File file = new File(BenefitsConstants.INS_PLAN_XLS_REPORT_DIR + "/InsurancePlan"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=InsurancePlan.xlsx");
		response.setContentLength((int) file.length());

		try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mav;
	}

	// INSURANCE MONTHLY REPORT

	@RequestMapping(value = "home/controlPanel/insurancePlans/insMonthlyEmployeeList", method = RequestMethod.POST)
	public ModelAndView viewInsMonthlyReportSearch(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insMonthlyReport");

		String monthValue = request.getParameter("insEmployeeMonth");
		String yearValue = request.getParameter("insEmployeeYear");

		Integer insPlanId = Integer.parseInt(request.getParameter("planName"));

		DateFormat df = new SimpleDateFormat("dd MMM yyyy");
		DateFormat df1 = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat fullMonthFormat = new SimpleDateFormat("MMMM,yyyy");

		// create a blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// SHEET 1
		// create a blank sheet
		XSSFSheet spreadsheet0 = workbook.createSheet("Insurance-Monthly Report");

		// creating rows and cells for headings
		XSSFRow row0 = spreadsheet0.createRow(2);
		XSSFCell numRowHead = row0.createCell(0);
		numRowHead.setCellValue("#");
		spreadsheet0.autoSizeColumn(0);

		XSSFCell empNameRowHead = row0.createCell(1);
		empNameRowHead.setCellValue("Name");
		spreadsheet0.autoSizeColumn(1);

		XSSFCell empCodeRowHead = row0.createCell(2);
		empCodeRowHead.setCellValue("Employee Code");
		spreadsheet0.autoSizeColumn(2);

		XSSFCell empDOJRowHead = row0.createCell(3);
		empDOJRowHead.setCellValue("DOJ");
		spreadsheet0.autoSizeColumn(3);

		XSSFCell parentOffRowHead = row0.createCell(4);
		parentOffRowHead.setCellValue("Parent Office");
		spreadsheet0.autoSizeColumn(4);

		XSSFCell depenNameRowHead = row0.createCell(5);
		depenNameRowHead.setCellValue("Dependent Name");
		spreadsheet0.autoSizeColumn(5);

		XSSFCell depentRelRowHead = row0.createCell(6);
		depentRelRowHead.setCellValue("Relationship");
		spreadsheet0.autoSizeColumn(6);

		XSSFCell dobRowHead = row0.createCell(7);
		dobRowHead.setCellValue("DOB");
		spreadsheet0.autoSizeColumn(7);

		XSSFCell yourContrRowHead = row0.createCell(8);
		yourContrRowHead.setCellValue("Employee Contribution(Yearly)");
		spreadsheet0.autoSizeColumn(8);

		XSSFCell compContrRowHead = row0.createCell(9);
		compContrRowHead.setCellValue("Company Contribution(Yearly)");
		spreadsheet0.autoSizeColumn(9);

		XSSFCell employeeMonthlyContrRowHead = row0.createCell(10);
		employeeMonthlyContrRowHead.setCellValue("Employee Contribution(Monthly)");
		spreadsheet0.autoSizeColumn(10);

		XSSFCell compMonthlyContrRowHead = row0.createCell(11);
		compMonthlyContrRowHead.setCellValue("Company Contribution(Monthly)");
		spreadsheet0.autoSizeColumn(11);

		XSSFCell accCoverageRowHead = row0.createCell(12);
		accCoverageRowHead.setCellValue("Accident Coverage");
		spreadsheet0.autoSizeColumn(12);

		XSSFCell enrolledDateRowHead = row0.createCell(13);
		enrolledDateRowHead.setCellValue("Enrolled Date");
		spreadsheet0.autoSizeColumn(13);

		XSSFCell relievedDateRowHead = row0.createCell(14);
		relievedDateRowHead.setCellValue("Relieved Date");
		spreadsheet0.autoSizeColumn(14);

		XSSFCell buRowHead = row0.createCell(15);
		buRowHead.setCellValue("Business Unit");
		spreadsheet0.autoSizeColumn(15);

		// Dynamic Values
		List<INSPlanEmployee> planEmployees = insPlanEmployeeService.listMonthlyEmpList(monthValue, insPlanId,
				yearValue);

		String month = null;

		Integer intMonthValue = Integer.parseInt(monthValue);

		switch (intMonthValue) {
		case 01:
			month = "JANUARY";
			break;
		case 02:
			month = "FEBRUARY";
			break;
		case 03:
			month = "MARCH";
			break;
		case 04:
			month = "APRIL";
			break;
		case 05:
			month = "MAY";
			break;
		case 06:
			month = "JUNE";
			break;
		case 07:
			month = "JULY";
			break;
		case 8:
			month = "AUGUST";
			break;
		case 9:
			month = "SEPTEMBER";
			break;
		case 10:
			month = "OCTOBER";
			break;
		case 11:
			month = "NOVEMBER";
			break;
		case 12:
			month = "DECEMBER";
			break;

		default:
			break;
		}

		String currentReportDate = month + "-" + yearValue;

		// int noOfColumns = spreadsheet0.getRow(0).getPhysicalNumberOfCells();

		XSSFRow rowHeader = spreadsheet0.createRow(0);
		XSSFCell cellHeader = rowHeader.createCell(6);
		cellHeader.setCellValue(currentReportDate);
		spreadsheet0.autoSizeColumn(0);

		// calling web service for Business Unit
		GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020List buRequest = null;
		header = new ObjectFactory().createServiceAuthenticationHeader();

		int row = 3, count = 1;
		for (INSPlanEmployee planEmployee : planEmployees) {

			System.out.println("!!!!!!!!!!!!!!!!Employee Name : " + planEmployee.getEmployee().getUserName()
					+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			XSSFRow rowDetails = spreadsheet0.createRow(row);

			XSSFCell numRowValue = rowDetails.createCell(0);
			numRowValue.setCellValue(count);
			spreadsheet0.autoSizeColumn(0);

			XSSFCell empNameRowValue = rowDetails.createCell(1);
			empNameRowValue.setCellValue(
					planEmployee.getEmployee().getFirstName() + " " + planEmployee.getEmployee().getLastName());
			spreadsheet0.autoSizeColumn(1);

			XSSFCell empCodeRowValue = rowDetails.createCell(2);
			empCodeRowValue.setCellValue(planEmployee.getEmployee().getEmployeeCode());
			spreadsheet0.autoSizeColumn(2);

			XSSFCell empDOJRowValue = rowDetails.createCell(3);
			String sdf = df.format(planEmployee.getEmployee().getDateOfJoin());
			empDOJRowValue.setCellValue(sdf);
			spreadsheet0.autoSizeColumn(3);

			XSSFCell parentOffRowValue = rowDetails.createCell(4);
			parentOffRowValue.setCellValue(planEmployee.getEmployee().getParentOffice());
			spreadsheet0.autoSizeColumn(4);

			XSSFCell relieveDateRowValue = rowDetails.createCell(14);
			if (planEmployee.getEmployee().getRelievingDate() != null) {
				relieveDateRowValue.setCellValue(df.format(planEmployee.getEmployee().getRelievingDate()));
			} else {
				relieveDateRowValue.setCellValue("Active");
			}
			spreadsheet0.autoSizeColumn(14);

			// For bu value

			buRequest = new GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020List();
			buRequest.setEmployeeCode(planEmployee.getEmployee().getEmployeeCode());

			GetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResponse buResponse = mirrorDataService
					.getMirrorDataServiceSoap().getEmpBuAndProjectDetails( buRequest, header);

			String buValue = null;
			if (buResponse
					.getGetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResult() != null
					&& buResponse
							.getGetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResult()
							.getBOEmployeeDetails() != null
					&& buResponse
							.getGetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResult()
							.getBOEmployeeDetails().size() > 0) {

				try {
					buValue = buResponse
							.getGetX0020TheX0020EmployeeX0020BusinessX0020UnitX002CX0020TechnologyX0020NameX0020AndX0020ProjectX0020ListResult()
							.getBOEmployeeDetails().get(0).getBusinessUnitName();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			XSSFCell buValueCell = rowDetails.createCell(15);
			buValueCell.setCellValue(buValue);
			spreadsheet0.autoSizeColumn(15);

			BigDecimal totalYC = new BigDecimal(0);
			BigDecimal totalCC = new BigDecimal(0);

			int rowDepns = row + 1;
			List<INSPlanEmployeeDetails> planEmployeeDetails = insPlanEmployeeService
					.listAllPlanEmp(planEmployee.getInsPlanEmployeeId());

			for (INSPlanEmployeeDetails planEmpDetails : planEmployeeDetails) {
				/*
				 * Calculating employee & company deduction
				 */
				try {

					BigDecimal depEmpYearlyDeduction = planEmpDetails.getYearlyEmpDeduction();
					if (planEmpDetails.getEaicEnrolled()) {
						/*
						 * checking EAIX condition
						 */
						depEmpYearlyDeduction = depEmpYearlyDeduction
								.add(planEmployee.getInsPlan().getEaicYearlyDeduction());
					}
					totalYC = totalYC.add(depEmpYearlyDeduction);
					totalCC = totalCC.add(planEmpDetails.getYearlyCmpDeduction());

					XSSFRow rowDep = spreadsheet0.createRow(rowDepns);
					XSSFCell depenNameRowValue = rowDep.createCell(5);
					depenNameRowValue.setCellValue(planEmpDetails.getDependent().getDependentName());
					spreadsheet0.autoSizeColumn(5);

					XSSFCell depentRelRowValue = rowDep.createCell(6);
					depentRelRowValue.setCellValue(planEmpDetails.getDependent().getRelationship());
					spreadsheet0.autoSizeColumn(6);

					XSSFCell depentDOBValue = rowDep.createCell(7);
					String sdf1 = df1.format(planEmpDetails.getDependent().getDateOfBirth());
					depentDOBValue.setCellValue(sdf1);
					spreadsheet0.autoSizeColumn(7);

					XSSFCell yourContribution = rowDep.createCell(8);
					yourContribution.setCellValue(depEmpYearlyDeduction.toString());
					spreadsheet0.autoSizeColumn(8);

					XSSFCell compContribution = rowDep.createCell(9);
					compContribution.setCellValue(planEmpDetails.getYearlyCmpDeduction().toString());
					spreadsheet0.autoSizeColumn(9);

					XSSFCell employeeMonthlyContribution = rowDep.createCell(10);
					BigDecimal ded1 = BigDecimal.ZERO;
					if (planEmpDetails.getEaicEnrolled() == true) {
						ded1 = planEmpDetails.getYearlyEaicDeduction()
								.divide(new BigDecimal(planEmployee.getTotalEffMonths()), RoundingMode.HALF_EVEN);
					}

					employeeMonthlyContribution.setCellValue(depEmpYearlyDeduction
							.divide(new BigDecimal(planEmployee.getTotalEffMonths()), RoundingMode.HALF_EVEN)
							.toString());

					spreadsheet0.autoSizeColumn(10);

					XSSFCell companyMonthlyContribution = rowDep.createCell(11);
					companyMonthlyContribution.setCellValue(planEmpDetails.getYearlyCmpDeduction()
							.divide(new BigDecimal(planEmployee.getTotalEffMonths()), RoundingMode.HALF_EVEN)
							.toString());
					spreadsheet0.autoSizeColumn(11);

					XSSFCell eaicValue = rowDep.createCell(12);

					if (planEmpDetails.getEaicEnrolled() == true) {
						eaicValue.setCellValue("Yes");
					} else if (planEmpDetails.getEaicEnrolled() == false) {
						eaicValue.setCellValue("No");
					}

					spreadsheet0.autoSizeColumn(12);

					if (planEmployee.getEnrolledDate() != null) {
						XSSFCell enrolledDate = rowDep.createCell(13);
						String enrolDate = df.format(planEmployee.getEnrolledDate());
						enrolledDate.setCellValue(enrolDate);
						spreadsheet0.autoSizeColumn(13);
					} else {
						XSSFCell enrolledDate = rowDep.createCell(13);
					}

					/*
					 * CellStyle styleBody = workbook.createCellStyle(); Font
					 * fontBody = workbook.createFont();
					 * fontBody.setItalic(true);
					 * fontBody.setFontHeightInPoints((short)10);
					 * styleBody.setFont(fontBody);
					 * 
					 * for(int j = 4; j < 11; j++){//For each cell in the row
					 * rowDep.getCell(j).setCellStyle(styleBody);//Set the style
					 * }
					 */

				} catch (Exception e) {
					e.printStackTrace();
				}
				rowDepns++;
			}

			XSSFCell totalYCCell = rowDetails.createCell(8);
			totalYCCell.setCellValue(totalYC.toString());
			spreadsheet0.autoSizeColumn(8);

			XSSFCell totalCCCell = rowDetails.createCell(9);
			totalCCCell.setCellValue(totalCC.toString());
			spreadsheet0.autoSizeColumn(9);

			BigDecimal ded = totalYC.divide(new BigDecimal(planEmployee.getTotalEffMonths()), RoundingMode.HALF_EVEN);
			XSSFCell totalMonthlyYCCell = rowDetails.createCell(10);
			totalMonthlyYCCell.setCellValue(ded.toString());
			spreadsheet0.autoSizeColumn(10);

			XSSFCell totalMonthlyCCCell = rowDetails.createCell(11);
			totalMonthlyCCCell.setCellValue(totalCC
					.divide(new BigDecimal(planEmployee.getTotalEffMonths()), RoundingMode.HALF_EVEN).toString());
			spreadsheet0.autoSizeColumn(11);

			XSSFCellStyle styleContent = workbook.createCellStyle();
			styleContent.setFillForegroundColor(new XSSFColor(new java.awt.Color(187, 188, 188)));
			styleContent.setFillPattern(CellStyle.SOLID_FOREGROUND);
			rowDetails.setRowStyle(styleContent);

			row = rowDepns--;
			row++;
			count++;
		}

		// FOR BOLD VALUES

		CellStyle styleHead = workbook.createCellStyle();
		Font fontHead = workbook.createFont();
		fontHead.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontHead.setFontHeightInPoints((short) 10);
		styleHead.setFont(fontHead);

		for (int i = 0; i < row0.getLastCellNum(); i++) {

			row0.getCell(i).setCellStyle(styleHead);
		}

		rowHeader.getCell(6).setCellStyle(styleHead);

		// Saving into file
		File file = new File(BenefitsConstants.INS_PLAN_MONTHLY_XLS_REPORT_DIR + "/InsPlanMonthlyReport"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=InsPlanMonthlyReport.xlsx");
		response.setContentLength((int) file.length());

		try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mav;
	}

}