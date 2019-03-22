package main.java.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.StringUtils;

import main.java.bean.WorkTask;
import main.java.util.DateUtils;
import main.java.util.HibernateUtils;
import main.java.util.PaginationResult;

@SuppressWarnings("deprecation")
public class WorkTaskDAO {

	/**
	 * This method saves a work task object in database
	 * 
	 * @param workName
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return id
	 */
	public int saveWorkTask(String workName, String startDate, String endDate, int status) {
		WorkTask work = new WorkTask();
		work.setWorkName(workName);
		work.setStartDate(DateUtils.convertStringToDate(startDate));
		work.setEndDate(DateUtils.convertStringToDate(endDate));
		work.setStatus(status);
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		int id = (Integer) session.save(work);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	/**
	 * This method returns list of all work task objects from
	 * @param sort ASC|DESC
	 * @param page
	 * @param maxResult
	 * @param maxNavigationResult
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<WorkTask> getAllWorkTask(String sort, int page, int maxResult) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.getTransaction().begin();
		String sql = "Select w from " + WorkTask.class.getName() + " w";
		if ("asc".equalsIgnoreCase(sort)) {
			sql = sql + " ORDER BY id ASC";
		} else if ("desc".equalsIgnoreCase(sort)) {
			sql = sql + " ORDER BY id DESC";
		}
		Query<WorkTask> query = session.createQuery(sql);
		PaginationResult<WorkTask> result = new PaginationResult<WorkTask>(query, page, maxResult);
		List<WorkTask> workTaskList = result.getList();
		session.getTransaction().commit();
		session.close();
		return workTaskList;
	}

	/**
	 * This method updates a specific work task object
	 * 
	 * @param id
	 * @param workName
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	public void updateWorkTask(int id, String workName, String startDate, String endDate, int status) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		WorkTask task = (WorkTask) session.get(WorkTask.class, id);
		if (!StringUtils.isEmpty(workName)) {
			task.setWorkName(workName);
		}
		if (!StringUtils.isEmpty(startDate)) {
			task.setStartDate(DateUtils.convertStringToDate(startDate));
		}
		if (!StringUtils.isEmpty(endDate)) {
			task.setEndDate(DateUtils.convertStringToDate(endDate));
		}
		if (status != 0) {
			task.setStatus(status);
		}
		session.update(task);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * This method deletes a specific WorkTask object
	 * 
	 * @param id
	 */
	public void deleteWorkTask(int id) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		WorkTask task = (WorkTask) session.get(WorkTask.class, id);
		session.delete(task);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Count work task with id
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long countWorkTask(int id) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.getTransaction().begin();
		String sql = "Select count(*) from " + WorkTask.class.getName() + " w where id=" + id;
		Query<Long> query = session.createQuery(sql);
		Long count = query.getSingleResult();
		session.getTransaction().commit();
		session.close();
		return count;
	}
}
