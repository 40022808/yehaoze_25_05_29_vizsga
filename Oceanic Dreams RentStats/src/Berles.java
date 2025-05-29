import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Berles {
    private int uid;
    private int yachtId;
    private String startDate;
    private String endDate;
    private double dailyPrice;
    private String name;
    private double totalPrice; 

    public Berles(int uid, int yachtId, String startDate, String endDate, double dailyPrice, String name) {
        this.uid = uid;
        this.yachtId = yachtId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyPrice = dailyPrice;
        this.name = name;
        this.totalPrice = calculateTotalPrice(); 
    }

    private double calculateTotalPrice() {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        int days = (int) ChronoUnit.DAYS.between(start, end) + 1; 
        return days * dailyPrice;
    }

    public double getTotalPrice() { return totalPrice; } 
    public String getName() { return name; }
    public int getYachtId() { return yachtId; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getDailyPrice() { return dailyPrice; }
}
