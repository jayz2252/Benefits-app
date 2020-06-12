package com.speridian.benefits2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.model.dao.PFEmployeeDaoImpl;
import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanClaimDetail;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimPafDetail;
import com.speridian.benefits2.model.pojo.LTAEmployee;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.BenefitPlanService;
import com.speridian.benefits2.service.INSPlanEmployeeService;
import com.speridian.benefits2.service.LTAService;
import com.speridian.benefits2.service.PFEmployeeService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.writers.pdf.ClaimReportPdfWriter;
import com.speridian.benefits2.writers.pdf.INSClaimReportPdfWriter;
import com.speridian.benefits2.writers.pdf.LTARequestPdfWriter;
import com.speridian.benefits2.writers.pdf.PafPdfWriter;
import com.speridian.benefits2.writers.pdf.ProvidentFundPDFWriter;
import com.speridian.benefits2.writers.pdf.VehicleEnrollmentPdfReport;

import javassist.expr.NewArray;

@Controller
public class PdfReportController {

	@Autowired
	BenefitPlanService benefitPlanService;

	@Autowired
	LTAService ltaService;

	@Autowired
	PFEmployeeService pfEmployeeService;

	@Autowired
	INSPlanEmployeeService insPlanEmployeeService;

	AppContext appContext;

	@RequestMapping(value = "/pdf/claim/{claimId}")
	public void approveEmployeeBenefitPlan(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("claimId") Integer claimId) throws IOException, Exception {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		if (appContext != null) {

			BenefitPlanClaim benefitPlanClaim = benefitPlanService.getByClaimId(claimId);

			List<BenefitPlanClaimDetail> benefitPlanClaimDetails = benefitPlanService.getBy(claimId);
			VehicleEnrollmentPdfReport writer = new VehicleEnrollmentPdfReport();
			writer.addDetails(writer.DETAIL_BENEFIT_PLAN_VEHICLE, benefitPlanClaimDetails);

			String fileName = writer.write(benefitPlanClaim);

			// download

			File file = new File(fileName);
			Path f = Paths.get(BenefitsConstants.PDF_REPORT_DIR, file.getName());

			if (Files.exists(f)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + "report.pdf");
				try {
					Files.copy(f, response.getOutputStream());

					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/home/controlPanel/lta/search/download/{ltaEmployeeId}")
	public void ltaReportDownload(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("ltaEmployeeId") Integer ltaEmployeeId) throws IOException, Exception {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		if (appContext != null) {

			LTAEmployee ltaEmployee = ltaService.getById(ltaEmployeeId);

			LTARequestPdfWriter ltaRequestPdfWriter = new LTARequestPdfWriter();

			String ltaFileName = ltaRequestPdfWriter.createLtaPdfFile(ltaEmployee);

			// download
			Path file = null;
			try {
				file = Paths.get(BenefitsConstants.PDF_REPORT_DIR, ltaFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Files.exists(file)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + "report.pdf");
				try {
					Files.copy(file, response.getOutputStream());

					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/home/controlPanel/pf/reportDownload/{pfEmployeeId}")
	public void PfPdfReportDownload(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) throws IOException, Exception {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		if (appContext != null) {
			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);

			ProvidentFundPDFWriter providentFundPDFWriter = new ProvidentFundPDFWriter();
			String pfFilename = providentFundPDFWriter.createPfPdfFile(pfEmployee);

			// download
			Path file = Paths.get(BenefitsConstants.PDF_REPORT_DIR, pfFilename);

			if (Files.exists(file)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + "report.pdf");
				try {
					Files.copy(file, response.getOutputStream());

					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/home/myInsurancePlan/viewClaims/pdfReport/paf/{insPlanEmployeeClaimId}")
	public void PafPdfReportDownload(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeClaimId") Integer insPlanEmployeeClaimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
try{
		if (appContext != null) {
			InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService.getPafDetail(insPlanEmployeeClaimId);
			INSClaimReportPdfWriter writer = new INSClaimReportPdfWriter();

			String fileName = writer.write(claimPafDetail);

			// download

			File file = new File(fileName);
			Path f = Paths.get(BenefitsConstants.PDF_REPORT_DIR, file.getName());

			if (Files.exists(f)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + "INSreport.pdf");
				try {
					Files.copy(f, response.getOutputStream());

					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
}catch (Exception e) {
	e.printStackTrace();
}
	}

	@RequestMapping(value = "/home/fileDownload", method = RequestMethod.GET)
	public void downloadStaticFile(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		if (appContext != null) {
			String filename = request.getParameter("filename");
			String extn = request.getParameter("ext");
			// Download
			Path file = Paths.get(BenefitsConstants.DOWNLOAD_DIRECTORY, filename);
			if (extn.equals("pdf")) {

				if (Files.exists(file)) {
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment; filename=" + "document.pdf");
					try {
						Files.copy(file, response.getOutputStream());
						response.getOutputStream().flush();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			} else if (extn.equals("xlsx")) {
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment; filename=datasheet.xlsx");

				try {
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
