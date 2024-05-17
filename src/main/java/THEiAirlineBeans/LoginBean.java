/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package THEiAirlineBeans;

import DBconnecter.dBConnection;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author lamyu
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password; // Corrected spelling here

    public String getUsername() { // Corrected getter name
        return username;
    }

    public void setUsername(String username) { // Corrected setter name
        this.username = username;
    }

    public String getPassword() { // Corrected getter name
        return password;
    }

    public void setPassword(String password) { // Corrected setter name
        this.password = password;
    }

    public String validateUsernamePassword() {
        boolean valid = checkLogin(username, password); // Use correct variable names
        if (valid) {
            System.out.println("Login successful!"); // Print success message
            return "welcome"; //
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Password",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    private boolean checkLogin(String username, String password) {
        boolean isValid = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dBConnection.getConnection();
            String sql = "SELECT * FROM stuff WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            isValid = rs.next(); // Check if result set has any rows

        } catch (SQLException e) {
            e.printStackTrace();
            // Additional error handling: print SQL exception message or error code
            System.out.println("SQL Exception Message: " + e.getMessage());
            System.out.println("SQL Exception Error Code: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isValid;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
}
