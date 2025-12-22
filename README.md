Employee Payroll Management System (MySQL Database)

Overview:
- Employee module: OOP + Encapsulation
- Payroll module: Inheritance + Polymorphism
- Database module: JDBC + MySQL
- Report module: Stream API + File writing
- Tax module: Slab-wise tax (Encapsulation)

## Complete MySQL Setup Guide

### Step 1: Install MySQL Server
1. Download MySQL Installer from: https://dev.mysql.com/downloads/installer/
2. Run the installer and select "Developer Default" or "Server only"
3. Set up root password (remember it!)
4. Complete installation

### Step 2: Set Up Database
1. Open MySQL Workbench
2. Connect to your MySQL server (localhost:3306)
3. Open `database_setup.sql` and run it
4. Verify tables are created

### Step 5: Migrate CSV Data (Optional)
If you have existing `employee.csv` and `salary.csv` files, migrate the data to MySQL:

```bash
# Compile the migrator
javac -cp "lib/mysql-connector-java-8.0.33.jar" -d out src/payroll/*.java

# Run the migration
java -cp "out;lib/mysql-connector-java-8.0.33.jar" payroll.CSVDataMigrator
```

This will:
- ✅ Clear existing database data
- ✅ Import employees from `employee.csv`
- ✅ Import salaries from `salary.csv` (skipping invalid references)
- ✅ Maintain foreign key relationships

### Step 4: Test Connection
```bash
# Compile
javac -cp "lib/mysql-connector-java-8.0.33.jar" -d out src/payroll/*.java

# Test connection
java -cp "out;lib/mysql-connector-java-8.0.33.jar" payroll.MySQLConnectionTest

# Run application
java -cp "out;lib/mysql-connector-java-8.0.33.jar" payroll.Main
```

### MySQL Workbench Usage
- **Connect**: localhost:3306, username: root, password: your_password
- **Database**: employee_payroll
- **View Data**: Right-click tables → "Select Rows - Limit 1000"
- **Query Data**: Use SQL Editor tab

### Troubleshooting

**"Access denied for user 'root'@'localhost'"**
- Your MySQL root password is not set correctly in the Java files
- Run `test_mysql.bat` to verify your password
- Update both `DatabaseManager.java` and `MySQLConnectionTest.java` with your actual password

**"Unknown database 'employee_payroll'"**
- Run `database_setup.sql` in MySQL Workbench
- Make sure the database was created successfully

**"Communications link failure"**
- MySQL Server is not running
- Start MySQL80 service in Windows Services
