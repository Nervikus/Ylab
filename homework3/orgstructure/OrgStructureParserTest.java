package orgstructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {
    public static void main(String[] args) {
        OrgStructureParser parser = new OrgStructureParserImpl();
        try {
            Employee boss = parser.parseStructure(new File("src/main/resources/employees.csv"));
            System.out.println("Генеральный директор: " + boss.getName());
            System.out.println("Подчиненные: ");
            for (Employee subordinate : boss.getSubordinates()) {
                // выводим список прямых подчиненных
                System.out.println(subordinate.getName() + " - " + subordinate.getPosition());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
