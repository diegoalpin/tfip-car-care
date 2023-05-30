package sg.edu.nus.iss.app.tfip_carcare.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.tfip_carcare.models.Customer;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String selectByEmailSQL = "SELECT * FROM customer where email = ?";
    private final String selectByNotIdSQL = "SELECT * FROM customer where NOT id = ?";
    private final String selectByIdSQL = "SELECT * FROM customer where id = ?";
    private final String selectIdByEmailSQL = "SELECT id FROM customer where email = ?";
    private final String insertSQL = "INSERT INTO customer(email,pwd,firstName,lastName,phoneNumber,address,role) values (?,?,?,?,?,?,?) ";

    public List<Customer> findById(int custId) {
        return jdbcTemplate.query(selectByIdSQL,
                BeanPropertyRowMapper.newInstance(Customer.class), custId);
    }

    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query(selectByEmailSQL,
                BeanPropertyRowMapper.newInstance(Customer.class), email);
    }
    
    public List<Customer> findAllExceptById(int custId) {
        return jdbcTemplate.query(selectByNotIdSQL,
                BeanPropertyRowMapper.newInstance(Customer.class), custId);
    }

    public int findIdByEmail(String email) {
        int result = jdbcTemplate.queryForObject(selectIdByEmailSQL, Integer.class, email);
        // System.out.println("id is "+result);
        return result;
    }


    public Customer save(Customer customer) {
        try {
            jdbcTemplate.update(insertSQL,
                    customer.getEmail(),
                    customer.getPwd(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPhoneNumber(),
                    customer.getAddress(),
                    customer.getRole());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
        return findByEmail(customer.getEmail()).get(0);
    }
}
