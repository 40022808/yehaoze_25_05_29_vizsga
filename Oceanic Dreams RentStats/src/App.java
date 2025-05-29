import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.DecimalFormat;

public class App {
    public static void main(String[] args) {
        List<Berles> berlesek = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("yacht_berlesek_2024.csv"));
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                int uid = Integer.parseInt(parts[0]);
                int yachtId = Integer.parseInt(parts[1]);
                String startDate = parts[2];
                String endDate = parts[3];
                double dailyPrice = Double.parseDouble(parts[4]);
                String name = parts[5];

                berlesek.add(new Berles(uid, yachtId, startDate, endDate, dailyPrice, name));
            }
        } catch (IOException e) {
            System.out.println("Hiba a fájl beolvasásakor: " + e.getMessage());
            return;
        }

        

        Scanner scanner = new Scanner(System.in);
        System.out.print("Adjon meg egy hónapot (1-12): ");
        int honap = scanner.nextInt();
        scanner.close();

        DecimalFormat df = new DecimalFormat("#,###.##");

        System.out.println("A(z) " + honap + ". hónap bevétele: " + df.format(haviBevetel(berlesek, honap)) + " euró");
        System.out.println("A teljes 2024-es éves bevétel: " + df.format(teljesEvesBevetel(berlesek)) + " euró");
        System.out.println("A legdrágább bérlés az " + legdragabbBerles(berlesek).getName() +
                           " yacht volt, teljes ár: " + df.format(legdragabbBerles(berlesek).getTotalPrice()) + " euró");
        System.out.println("Összesen " + kulonbozoYachtokSzama(berlesek) + " különböző yachtot béreltek ki.");
        System.out.println("A legtöbbször bérelt yacht: " + legtobbszorBereltYacht(berlesek));
        System.out.println("Átlagos bérlési időtartam: " + String.format("%.2f", atlagosBerlesiIdotartam(berlesek)) + " nap");
    }

    private static double haviBevetel(List<Berles> berlesek, int honap) {
        double osszBevetel = 0;
        for (Berles berles : berlesek) {
            LocalDate start = LocalDate.parse(berles.getStartDate());
            LocalDate end = LocalDate.parse(berles.getEndDate());
            if (start.getMonthValue() <= honap && end.getMonthValue() >= honap) {
                osszBevetel += berles.getTotalPrice();
            }
        }
        return osszBevetel;
    }

    private static double teljesEvesBevetel(List<Berles> berlesek) {
        return berlesek.stream().mapToDouble(Berles::getTotalPrice).sum();
    }

    private static Berles legdragabbBerles(List<Berles> berlesek) {
        return Collections.max(berlesek, Comparator.comparingDouble(Berles::getTotalPrice));
    }

    private static int kulonbozoYachtokSzama(List<Berles> berlesek) {
        Set<Integer> egyediYachtok = new HashSet<>();
        berlesek.forEach(berles -> egyediYachtok.add(berles.getYachtId()));
        return egyediYachtok.size();
    }

    private static String legtobbszorBereltYacht(List<Berles> berlesek) {
        Map<String, Integer> yachtSzamlalo = new HashMap<>();
        for (Berles berles : berlesek) {
            yachtSzamlalo.put(berles.getName(), yachtSzamlalo.getOrDefault(berles.getName(), 0) + 1);
        }
        return Collections.max(yachtSzamlalo.entrySet(), Map.Entry.comparingByValue()).getKey() + " (" +
               yachtSzamlalo.get(Collections.max(yachtSzamlalo.entrySet(), Map.Entry.comparingByValue()).getKey()) + " bérlés)";
    }

    private static double atlagosBerlesiIdotartam(List<Berles> berlesek) {
        return berlesek.stream()
                       .mapToDouble(b -> ChronoUnit.DAYS.between(LocalDate.parse(b.getStartDate()), LocalDate.parse(b.getEndDate())) + 1)
                       .average().orElse(0);
    }
}
