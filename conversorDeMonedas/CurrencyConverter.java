import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {

    // Tu API Key
    private static final String API_KEY = "f3bec76f97dfbe6da2559a1a";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Conversor de Monedas ===");
        System.out.println("Monedas disponibles: USD (Dólar), EUR (Euro), GBP (Libra), JPY (Yen), PEN (Sol Peruano)");

        System.out.print("Ingrese la moneda base (ejemplo USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese la moneda destino (ejemplo PEN): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese el monto a convertir: ");
        double amount = scanner.nextDouble();

        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonResponse.has("conversion_rates")) {
                JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");

                if (rates.has(targetCurrency)) {
                    double rate = rates.get(targetCurrency).getAsDouble();
                    double convertedAmount = amount * rate;
                    System.out.printf("\n%.2f %s equivalen a %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("La moneda destino no está disponible.");
                }
            } else {
                System.out.println("Error en la respuesta de la API: " + response.body());
            }

        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }

        scanner.close();
    }
}
