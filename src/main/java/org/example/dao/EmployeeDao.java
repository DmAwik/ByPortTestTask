package org.example.dao;

import org.example.Utils.HibernateUtil;
import org.example.model.Employee;
import org.example.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class EmployeeDao implements Dao<Employee> {
    private SessionFactory sessionFactory;

    public EmployeeDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    public List<String> getName() {
        try (Session session = sessionFactory.openSession()) {
            Query<String> query = session.createQuery("SELECT e.fullName FROM org.example.model.Employee e",String.class);
            return query.getResultList();
        }
    }
    public List<Task> getTasksForEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            employee = session.get(Employee.class, employee.getEmployeeId()); // Обновляем состояние сотрудника
            return employee.getTasks();
        }
    }
    public Employee getEmployeeById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Employee.class, employeeId);
        }
    }
    public Employee getEmployeeByName(String fullName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery("FROM Employee WHERE full_name = :fullName", Employee.class);
            query.setParameter("fullName", fullName);
            return query.uniqueResult();
        }
    }

}
