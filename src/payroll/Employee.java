package payroll;

public class Employee {
    private String id;
    private String name;
    private String designation;
    private String department;
    private String pan;
    private String dateOfJoining;

    public Employee(String id, String name, String designation, String department, String pan, String dateOfJoining) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.pan = pan;
        this.dateOfJoining = dateOfJoining;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }

    public String getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(String dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    @Override
    public String toString() {
        return id + "," + name + "," + designation + "," + department + "," + pan + "," + dateOfJoining;
    }
}
