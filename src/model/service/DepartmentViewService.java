package model.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentViewService {

	DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department>findAll(){
		List<Department> list = dao.findAll();
		return list;
	}
}