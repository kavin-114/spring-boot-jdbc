package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;
import com.jdbc.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {

	Response res = new Response();
	String url = "jdbc:mysql://127.0.0.1:3306/crud";
	String username = "root";
	String password = "Kavin@11";

	@Override
	public Response createUser(SignUpModel value) {

		String uuid = UUID.randomUUID().toString();
		value.setsNo(uuid);

		value.setCreatedBy(uuid);
		value.setUpdatedBy(uuid);
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		value.setCreatedDate(date);
		value.setUpdatedDate(date);

		value.setIsActive(1);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				if (value.getEmail().contains("@gmail.com")
						&& Pattern.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$", value.getEmail())) {

//					 Pattern.compile(regex).matcher(input).matches()

					if (String.valueOf(value.getPhNo()).length() == 10 && value.getPhNo() >= 6000000000L
							&& value.getPhNo() <= 9999999999L
							&& Pattern.matches("[6789][0-9]{9}", String.valueOf(value.getPhNo()))) {

//						&& Pattern.matches("[6789][0-9]{9}",String.valueOf(value.getPhNo()))

						String createQuery = "INSERT INTO crud.signup(sno,firstname,lastname,email,phno,password,age,dob,gender,created_by,updated_by,created_date,updated_date,is_active)VALUES"
								+ "('" + value.getsNo() + "','" + value.getFirstName() + "','" + value.getLastName()
								+ "','" + value.getEmail() + "'," + value.getPhNo() + ",'" + value.getPassword() + "',"
								+ "" + value.getAge() + ",'" + value.getDob() + "','" + value.getGender() + "','"
								+ value.getCreatedBy() + "','" + value.getUpdatedBy() + "','" + value.getCreatedDate()
								+ "'," + "'" + value.getUpdatedDate() + "','" + value.getIsActive() + "')";

						System.out.println(createQuery);

						st.executeUpdate(createQuery);

						res.setResponseMsg("SUCCESS");
						res.setResponseCode(200);
						res.setData("User created successfully");
					} else {
						res.setResponseMsg("ERROR");
						res.setResponseCode(500);
						res.setData("Invalid Phone Number");
					}
				} else {
					res.setResponseMsg("ERROR");
					res.setResponseCode(500);
					res.setData("Invalid Email");
				}
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot create user");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public Response updateUser(SignUpModel value) {

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		value.setUpdatedDate(date);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				if (value.getEmail().contains("@gmail.com")
						&& Pattern.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$", value.getEmail())) {

//					 Pattern.compile(regex).matcher(input).matches()

					if (String.valueOf(value.getPhNo()).length() == 10 && value.getPhNo() >= 6000000000L
							&& value.getPhNo() <= 9999999999L
							&& Pattern.matches("[6789][0-9]{9}", String.valueOf(value.getPhNo()))) {

//						&& Pattern.matches("[6789][0-9]{9}",String.valueOf(value.getPhNo()))
						String primaryKey = value.getsNo();
						System.out.println(primaryKey);

						String createQuery = "UPDATE crud.signup SET email = '" + value.getEmail() + "', phno = '"
								+ value.getPhNo() + "', updated_by =  '" + value.getsNo() + "', updated_date = '"
								+ value.getUpdatedDate() + "' WHERE sno = '" + value.getsNo() + "';";

						System.out.println(createQuery);

						st.executeUpdate(createQuery);

						res.setResponseMsg("SUCCESS");
						res.setResponseCode(200);
						res.setData("User updated successfully");
					} else {
						res.setResponseMsg("ERROR");
						res.setResponseCode(500);
						res.setData("Invalid Phone Number");
					}
				} else {
					res.setResponseMsg("ERROR");
					res.setResponseCode(500);
					res.setData("Invalid Email");
				}

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot update user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response deleteUser(String sNo) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				if (!(sNo.equals(""))) {

					String createQuery = "DELETE FROM crud.signup WHERE (sno = '" + sNo + "');";

					System.out.println(createQuery);

					st.executeUpdate(createQuery);

					res.setResponseMsg("SUCCESS");
					res.setResponseCode(200);
					res.setData("User deleted successfully");
				} else {
					res.setResponseMsg("ERROR");
					res.setResponseCode(500);
					res.setData("Invalid UserID");
				}

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot delete user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response getUser() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String createQuery = "SELECT * FROM signup WHERE is_active = 1;";
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(createQuery);) {

				JSONArray jsonArray = new JSONArray();

				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();

					jsonObject.put("sNo", rs.getString("sno"));
					jsonObject.put("firstName", rs.getString("firstname"));
					jsonObject.put("lastName", rs.getString("lastname"));
					jsonObject.put("email", rs.getString("email"));
					jsonObject.put("phNo", rs.getLong("phno"));
					jsonObject.put("password", rs.getString("password"));
					jsonObject.put("age", rs.getInt("age"));
					jsonObject.put("dob", rs.getDate("dob"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("createdBy", rs.getString("created_by"));
					jsonObject.put("updatedBy", rs.getString("updated_by"));
					jsonObject.put("createdDate", rs.getDate("created_date"));
					jsonObject.put("updatedDate", rs.getDate("updated_date"));

					jsonArray.add(jsonObject);

				}

				res.setResponseMsg("SUCCESS");
				res.setResponseCode(200);
				res.setData("User details retrieved successfully");
				res.setjData(jsonArray);

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot get user details");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response getOneUser(String sNo) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String createQuery = "SELECT * FROM signup WHERE sno='" + sNo + "' and is_active = 1;";
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(createQuery);) {

				JSONObject jsonObject = new JSONObject();

				while (rs.next()) {

					jsonObject.put("sNo", rs.getString("sno"));
					jsonObject.put("firstName", rs.getString("firstname"));
					jsonObject.put("lastName", rs.getString("lastname"));
					jsonObject.put("email", rs.getString("email"));
					jsonObject.put("phNo", rs.getLong("phno"));
					jsonObject.put("password", rs.getString("password"));
					jsonObject.put("age", rs.getInt("age"));
					jsonObject.put("dob", rs.getDate("dob"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("createdBy", rs.getString("created_by"));
					jsonObject.put("updatedBy", rs.getString("updated_by"));
					jsonObject.put("createdDate", rs.getDate("created_date"));
					jsonObject.put("updatedDate", rs.getDate("updated_date"));

					res.setResponseMsg("SUCCESS");
					res.setResponseCode(200);
					res.setData("User details retrieved successfully");
					res.setjData(jsonObject);
				}

				res.setResponseMsg("SUCCESS");
				res.setResponseCode(200);
				res.setData("No Such User Found!");
				res.setjData(jsonObject);

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot get user details");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response scamUser(String sNo) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				if (!(sNo.equals(""))) {

					String createQuery = "UPDATE crud.signup SET is_active = 0 WHERE (sno = '" + sNo + "');";

					System.out.println(createQuery);

					st.executeUpdate(createQuery);

					res.setResponseMsg("SUCCESS");
					res.setResponseCode(200);
					res.setData("User deleted successfully");
				} else {
					res.setResponseMsg("ERROR");
					res.setResponseCode(500);
					res.setData("Invalid UserID");
				}

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot delete user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Response userLogin(SignUpModel value) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();){
				if (value.getsNo().equals("")) {
					res.setResponseMsg("ERROR");
					res.setResponseCode(500);
					res.setData("Cannot find user");
				} else {
					String createQuery = "SELECT email, password FROM signup WHERE is_active = 1;";
					ResultSet rs = st.executeQuery(createQuery);
					JSONArray jsonArray = new JSONArray();
					JSONObject jsonObject =  new JSONObject();
					while(rs.next()) {
						jsonObject.put("email", rs.getString("email"));
						jsonObject.put("password", rs.getString("password"));
						jsonArray.add(jsonObject);
					}
					for (Object obj : jsonArray) {
						System.out.println(obj);
							if(obj.toString().equals(value.getEmail()) && obj.toString().equals(value.getPassword())) {
								res.setResponseCode(200);
								res.setResponseMsg("SUCCESS");
								res.setData("User logged in succesfully");
							}else {
								res.setResponseCode(500);
								res.setResponseMsg("ERROR");
								res.setData("Invalid Username and Password");
						}
							
						}
					}
					
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("ERROR");
				res.setResponseCode(500);
				res.setData("Cannot find user");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return res;
	}
}
