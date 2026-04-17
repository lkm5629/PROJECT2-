package P11_masterdata.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import P11_masterdata.DAO.VendorDAO;
import P11_masterdata.DTO.VendorDTO;

@WebServlet("/vendorUpdate")
public class VendorUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/vendor");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setVendor_id(trimToEmpty(request.getParameter("vendor_id")));
		vendorDTO.setVendor_type(trimToEmpty(request.getParameter("vendor_type")));
		vendorDTO.setVendor_name(trimToEmpty(request.getParameter("vendor_name")));
		vendorDTO.setPhone_no(trimToEmpty(request.getParameter("phone_no")));
		vendorDTO.setAddr(trimToEmpty(request.getParameter("addr")));

		String emp_id = trimToEmpty(request.getParameter("emp_id"));

		if (emp_id.equals("")) {
			emp_id = findSessionEmpId(request);
		}

		// emp_id 없으면 null 허용
		if (emp_id.equals("")) {
			emp_id = null;
		}

		vendorDTO.setEmp_id(emp_id);

		VendorDAO vendorDAO = new VendorDAO();
		vendorDAO.updateVendor(vendorDTO);

		response.sendRedirect(request.getContextPath() + "/vendor");
	}

	private String trimToEmpty(String value) {
		if (value == null) {
			return "";
		}
		return value.trim();
	}

	private String findSessionEmpId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return "";
		}

		String[] directAttrNames = { "emp_id", "empId", "loginEmpId" };

		for (String attrName : directAttrNames) {
			Object attrValue = session.getAttribute(attrName);
			String empId = trimToEmpty(attrValue == null ? null : String.valueOf(attrValue));

			if (!empId.equals("")) {
				return empId;
			}
		}

		String[] objectAttrNames = { "dto", "loginDTO", "loginInfo", "user" };

		for (String attrName : objectAttrNames) {
			String empId = extractEmpId(session.getAttribute(attrName));

			if (!empId.equals("")) {
				return empId;
			}
		}

		Enumeration<String> attrNames = session.getAttributeNames();

		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			Object attrValue = session.getAttribute(attrName);

			String directEmpId = "";
			String lowerAttrName = attrName == null ? "" : attrName.toLowerCase();

			if (lowerAttrName.contains("emp")) {
				directEmpId = trimToEmpty(attrValue == null ? null : String.valueOf(attrValue));
			}

			if (!directEmpId.equals("")) {
				return directEmpId;
			}

			String objectEmpId = extractEmpId(attrValue);
			if (!objectEmpId.equals("")) {
				return objectEmpId;
			}
		}

		return "";
	}

	private String extractEmpId(Object sourceObject) {
		if (sourceObject == null) {
			return "";
		}

		if (sourceObject instanceof Map<?, ?>) {
			Map<?, ?> sourceMap = (Map<?, ?>) sourceObject;
			String[] mapKeys = { "empid", "empId", "emp_id" };

			for (String mapKey : mapKeys) {
				Object value = sourceMap.get(mapKey);
				String empId = trimToEmpty(value == null ? null : String.valueOf(value));

				if (!empId.equals("")) {
					return empId;
				}
			}
		}

		String[] getterNames = { "getEmpid", "getEmpId", "getEmp_id" };

		for (String getterName : getterNames) {
			try {
				Method getter = sourceObject.getClass().getMethod(getterName);
				Object value = getter.invoke(sourceObject);
				String empId = trimToEmpty(value == null ? null : String.valueOf(value));

				if (!empId.equals("")) {
					return empId;
				}
			} catch (Exception e) {
			}
		}

		String[] fieldNames = { "empid", "empId", "emp_id" };

		for (String fieldName : fieldNames) {
			try {
				Field field = sourceObject.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				Object value = field.get(sourceObject);
				String empId = trimToEmpty(value == null ? null : String.valueOf(value));

				if (!empId.equals("")) {
					return empId;
				}
			} catch (Exception e) {
			}
		}

		return "";
	}
}