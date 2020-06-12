package com.speridian.benefits2.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.beans.HospitalBean;
import com.speridian.benefits2.model.pojo.Hospital;
import com.speridian.benefits2.model.pojo.IncomeTaxSlab;
import com.speridian.benefits2.model.pojo.Treatment;
import com.speridian.benefits2.service.HospitalService;
import com.speridian.benefits2.service.IncomeTaxSlabService;
import com.speridian.benefits2.service.TreatmentService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;

/**
 * @author minnu.john
 * 
 */

@Controller
public class MasterDataController {

	@Autowired
	IncomeTaxSlabService incomeTaxSlabService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	TreatmentService treatmentService;

	AppContext appContext = null;

	// IT Slabs
		@RequestMapping(value = "/home/controlPanel/masterData/itslabs")
		public ModelAndView showITSlabs(HttpServletRequest request,
				HttpServletResponse response) {

			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");

			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"itSlabs");
			List<IncomeTaxSlab> incomeTaxSlabs = incomeTaxSlabService.listAll();
			mav.addObject("taxSlabs", incomeTaxSlabs);
			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/itslabs/edit/{slabId}")
		public ModelAndView showEditTaxSlab(HttpServletRequest request,
				@PathVariable(value = "slabId") int slabId) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");

			ModelAndView mav = new ModelAndView();

			IncomeTaxSlab incomeTaxSlab = incomeTaxSlabService.get(slabId);

			if (incomeTaxSlab != null) {
				mav = AuthorizationUtil.authorizeAdmin(appContext, "editSlab");
				mav.addObject("slab", incomeTaxSlab);
				mav.addObject("mode", "edit");
			} else {
				mav = AuthorizationUtil.authorizeAdmin(appContext, "itSlabs");
				List<IncomeTaxSlab> incomeTaxSlabs = incomeTaxSlabService.listAll();
				mav.addObject("taxSlabs", incomeTaxSlabs);
			}

			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/itslabs/edit/save", method = RequestMethod.POST)
		public ModelAndView editTaxSlab(HttpServletRequest request,
				HttpServletResponse response) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");

			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"itSlabs");

			Integer slabId = Integer.parseInt(request.getParameter("slabId"));

			IncomeTaxSlab incomeTaxSlab = incomeTaxSlabService.get(slabId);

			incomeTaxSlab.setCtgName(request.getParameter("categoryName"));
			incomeTaxSlab.setCtgValue(request.getParameter("categoryValue"));

			if (incomeTaxSlabService.update(incomeTaxSlab)) {
				mav.addObject("saveStatus", true);
			} else {
				mav.addObject("saveStatus", false);
			}

			List<IncomeTaxSlab> incomeTaxSlabs = incomeTaxSlabService.listAll();
			mav.addObject("taxSlabs", incomeTaxSlabs);

			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/itslabs/add", method = RequestMethod.GET)
		public ModelAndView showAddTaxSlab(HttpServletRequest request,
				HttpServletResponse response) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");
			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"editSlab");
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editSlab");
			mav.addObject("mode", "add");
			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/itslabs/save", method = RequestMethod.POST)
		public ModelAndView addTaxSlab(HttpServletRequest request,
				HttpServletResponse response) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");
			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"itSlabs");

			IncomeTaxSlab incomeTaxSlab = new IncomeTaxSlab();

			incomeTaxSlab.setCtgName(request.getParameter("categoryName"));
			incomeTaxSlab.setCtgValue(request.getParameter("categoryValue"));
			incomeTaxSlab.setFiscalYear(request.getParameter("fiscalYear"));

			incomeTaxSlabService.insert(incomeTaxSlab);

			List<IncomeTaxSlab> incomeTaxSlabs = incomeTaxSlabService.listAll();
			mav.addObject("taxSlabs", incomeTaxSlabs);

			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/itslabs/delete/{slabId}")
		public ModelAndView deleteTaxSlab(HttpServletRequest request,
				@PathVariable(value = "slabId") Integer slabId) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");

			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/masterData/itslabs");

			if (incomeTaxSlabService.remove(slabId)) {
				mav.addObject("saveStatus", true);
			} else {
				mav.addObject("saveStatus", false);
			}
			return mav;
		}

	// Treatment
		@RequestMapping(value = "/home/controlPanel/masterData/viewtreatment")
		public ModelAndView listAllHospital(HttpServletRequest request,
				HttpServletResponse response) {

			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");
			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"viewTreatmentList");

			List<Treatment> treatments = treatmentService.listAll();
			mav.addObject("treatments", treatments);
			// mav.setViewName("viewTreatmentList");
			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/viewtreatment/edit/{treatmentId}")
		public ModelAndView showEditTreatmentList(HttpServletRequest request,
				@PathVariable(value = "treatmentId") int treatmentId) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");
			ModelAndView mav = new ModelAndView();

			Treatment treatment = treatmentService.get(treatmentId);

			if (treatment != null) {
				mav = AuthorizationUtil.authorizeAdmin(appContext, "editTreatment");
				mav.addObject("treatment", treatment);
				mav.addObject("mode", "edit");
			} else {
				mav = AuthorizationUtil.authorizeAdmin(appContext,
						"viewTreatmentList");
				List<Treatment> treatments = treatmentService.listAll();
				mav.addObject("treatments", treatments);
			}
			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/viewtreatment/edit/save", method = RequestMethod.POST)
		public ModelAndView saveTreatmentList(HttpServletRequest request,
				HttpServletResponse response) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");

			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"viewTreatmentList");

			Integer treatmentId = Integer.parseInt(request
					.getParameter("treatmentId"));

			Treatment treatment = treatmentService.get(treatmentId);

			treatment.setTreatmentName(request.getParameter("treatmentName"));
			treatment.setDescription(request.getParameter("treatmentDescription"));
			treatment.setAverageAmount(new BigDecimal(request
					.getParameter("averageAmount")));

			if (treatmentService.update(treatment)) {
				mav.addObject("saveStatus", true);
			} else {
				mav.addObject("saveStatus", false);
			}

			List<Treatment> treatments = treatmentService.listAll();
			mav.addObject("treatments", treatments);
			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/viewtreatment/add", method = RequestMethod.GET)
		public ModelAndView showAddTreatmentList(HttpServletRequest request,
				HttpServletResponse response) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");
			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"editTreatment");
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editTreatment");
			mav.addObject("mode", "add");
			return mav;
		}

		@RequestMapping(value = "/home/controlPanel/masterData/viewtreatment/save", method = RequestMethod.POST)
		public ModelAndView addTreatmentList(HttpServletRequest request,
				HttpServletResponse response) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");
			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"viewTreatmentList");

			Treatment treatment = new Treatment();

			treatment.setTreatmentName(request.getParameter("treatmentName"));
			treatment.setDescription(request.getParameter("treatmentDescription"));
			treatment.setAverageAmount(new BigDecimal(request
					.getParameter("averageAmount")));

			treatmentService.insert(treatment);

			List<Treatment> treatments = treatmentService.listAll();
			mav.addObject("treatments", treatments);
			return mav;
		}
		
		@RequestMapping(value = "/home/controlPanel/masterData/viewtreatment/delete/{treatmentId}")
		public ModelAndView deleteTreatmentList(HttpServletRequest request,
				@PathVariable(value = "treatmentId") Integer treatmentId) {
			appContext = (AppContext) request.getSession().getAttribute(
					"appContext");

			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/masterData/viewtreatment");

			if (treatmentService.remove(treatmentId)) {
				mav.addObject("saveStatus", true);
			} else {
				mav.addObject("saveStatus", false);
			}
			return mav;
		}

//Hospital
	@RequestMapping(value = "/home/controlPanel/masterData/viewhospital")
	public ModelAndView showAllHospital(HttpServletRequest request,
			HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"viewHospitalList");

		List<Hospital> hospitals = hospitalService.listAll();
		mav.addObject("hospitals", hospitals);

		return mav;
	}


	@RequestMapping(value = "/home/controlPanel/masterData/viewhospital/edit/{hospitalId}", method = RequestMethod.GET)
	public ModelAndView showEditHospital(HttpServletRequest request,
			@PathVariable(value = "hospitalId") int hospitalId) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = null;

		Hospital hospital = hospitalService.get(hospitalId);

		if (hospital != null) {

			mav = AuthorizationUtil.authorizeAdmin(appContext, "editHospital");

			HospitalBean hospitalBean = new HospitalBean();
			hospitalBean.setHospitalId(hospitalId);

			hospitalBean.setHospitalName(hospital.getHospitalName());
			hospitalBean.setAddress(hospital.getAddress());
			hospitalBean.setCity(hospital.getCity());
			hospitalBean.setState(hospital.getState());
			hospitalBean.setEmail(hospital.getEmail());
			hospitalBean.setPhoneNo(hospital.getPhoneNo());

			mav.addObject("hospitalBean", hospitalBean);
		} else {
			mav = AuthorizationUtil.authorizeAdmin(appContext,
					"viewHospitalList");
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/masterData/viewhospital/edit/save", method = RequestMethod.POST)
	public String edithospitalDetails(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("hospitalBean") HospitalBean hospitalBean) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"viewHospitalList");

		Hospital hospital = hospitalService.get(hospitalBean.getHospitalId());
		hospital.setAddress(hospitalBean.getAddress());
		hospital.setCity(hospitalBean.getCity());
		hospital.setEmail(hospitalBean.getEmail());
		hospital.setHospitalName(hospitalBean.getHospitalName());
		hospital.setPhoneNo(hospitalBean.getPhoneNo());
		hospital.setState(hospitalBean.getState());

		hospital.setCreatedBy(appContext.getUserName());

		if (hospitalService.update(hospital)) {
			mav.addObject("saveStatus", true);
		} else {
			mav.addObject("saveStatus", false);
		}

		return ("redirect:/home/controlPanel/masterData/viewhospital");
	}

	@RequestMapping(value = "/home/controlPanel/masterData/addHospital", method = RequestMethod.GET)
	public ModelAndView addHospital(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("hospitalBean") HospitalBean hospitalBean) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"addHospital");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "addHospital");

		Hospital hospital = new Hospital();
		hospital.setAddress(hospitalBean.getAddress());
		hospital.setCity(hospitalBean.getCity());
		hospital.setEmail(hospitalBean.getEmail());
		hospital.setHospitalName(hospitalBean.getHospitalName());
		hospital.setPhoneNo(hospitalBean.getPhoneNo());

		hospital.setCreatedBy(appContext.getUserName());

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/masterData/saveHospital", method = RequestMethod.POST)
	public String saveHospital(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("hospitalBean") HospitalBean hospitalBean) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		Hospital hospital = new Hospital();
		hospital.setAddress(hospitalBean.getAddress());
		hospital.setState(hospitalBean.getState());
		hospital.setCity(hospitalBean.getCity());
		hospital.setEmail(hospitalBean.getEmail());
		hospital.setHospitalName(hospitalBean.getHospitalName());
		hospital.setPhoneNo(hospitalBean.getPhoneNo());

		hospitalService.insert(hospital);
		return "redirect:/home/controlPanel/masterData/viewhospital";

	}

	@RequestMapping(value = "/home/controlPanel/masterData/viewhospital/delete/{hospitalId}", method = RequestMethod.GET)
	public String showDeleteHospital(HttpServletRequest request,
			@PathVariable(value = "hospitalId") Integer hospitalId) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");


		hospitalService.delete(hospitalId);
		return "redirect:/home/controlPanel/masterData/viewhospital";
	}

}
