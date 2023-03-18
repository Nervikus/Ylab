package orgstructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrgStructureParserImpl implements OrgStructureParser {

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        List<String[]> dataList = readCsvFile(csvFile);
        Map<Long, Employee> employees = getEmployees(dataList);
        findEmployeesAndTheirBosses(employees);
        return findBoss(employees);
    }

    private List<String[]> readCsvFile(File csvFile) throws IOException {
        List<String[]> dataList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                dataList.add(line.split(";"));
            }
        }
        return dataList;
    }

    private Map<Long, Employee> getEmployees(List<String[]> dataList) {
        Map<Long, Employee> employees = new HashMap<>();
        for (String[] data : dataList) {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(data[0]));
            employee.setBossId(data[1].isEmpty() ? null : Long.parseLong(data[1]));
            employee.setName(data[2]);
            employee.setPosition(data[3]);
            employees.put(employee.getId(), employee);
        }
        return employees;
    }

    private void findEmployeesAndTheirBosses(Map<Long, Employee> employees) {
        for (Employee employee : employees.values()) {
            Long bossId = employee.getBossId();
            if (bossId != null) {
                Employee boss = employees.get(bossId);
                employee.setBoss(boss);
                boss.getSubordinates().add(employee);
            }
        }
    }

    private Employee findBoss(Map<Long, Employee> employees) {
        for (Employee employee : employees.values()) {
            if (employee.getBossId() == null) {
                return employee;
            }
        }
        throw new RuntimeException("Генеральный директор не найден.");
    }
}
