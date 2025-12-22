# Compile
javac -cp "lib/mysql-connector-java-8.0.33.jar" -d out src/payroll/*.java

# Run
java -cp "out;lib/mysql-connector-java-8.0.33.jar" payroll.Main