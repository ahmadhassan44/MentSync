package com.example.mentsync;

public class User {
    private static User instance;
    private String role;
    private String name;
    private String email;
    private String CMS;
    private String password;
    private String discipline;
    private int semester;
    private double CGPA;
    private User() {}
    public static User getInstance()
    {
        if(instance==null)
        {
            instance=new User();
            return instance;
        }
        return instance;
    }
    public static void dicardInstance()
    {
        instance=null;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCMS(String CMS) {
        this.CMS = CMS;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCGPA(double CGPA) {
        this.CGPA = CGPA;
    }
    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getEmail() {
        return email;
    }

    public String getCMS() {
        return CMS;
    }

    public String getName() {
        return name;
    }

    public double getCGPA() {
        return CGPA;
    }

    public int getSemester() {
        return semester;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
