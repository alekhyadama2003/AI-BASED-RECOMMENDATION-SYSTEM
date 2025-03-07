import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationEngine {

    public static void main(String[] args) throws IOException, TasteException {

        // --- Welcome Message ---
        System.out.println("--------------------------------------------------");
        System.out.println("  Welcome to the AI Phone Recommendation System!  ");
        System.out.println("--------------------------------------------------");
        System.out.println();

        // 1. Data Model: Load user preferences from the CSV file
        DataModel model = new FileDataModel(new File("ratings.csv"));

        // 2. User Similarity: Define how to calculate similarity between users
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // 3. User Neighborhood: Define how to select the neighborhood of similar users
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model); // Neighborhood size 3

        // 4. Recommender: Build the User-Based Recommender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Phone Metadata (Example)
        Map<String, Map<String, String>> phoneMetadata = new HashMap<>();
        phoneMetadata.put("1", createPhoneDetails("Samsung", "Galaxy Trend", "All-rounder", "799"));
        phoneMetadata.put("2", createPhoneDetails("Apple", "iPhone SE", "Compact & Powerful", "499"));
        phoneMetadata.put("3", createPhoneDetails("Google", "Pixel 7a", "Mid-range Excellence", "449"));
        phoneMetadata.put("4", createPhoneDetails("OnePlus", "Nord CE", "Budget Gaming", "349"));
        phoneMetadata.put("5", createPhoneDetails("Xiaomi", "Redmi Note", "Value King", "299"));
        phoneMetadata.put("6", createPhoneDetails("Motorola", "Moto G Power", "Battery Champion", "249"));
        phoneMetadata.put("7", createPhoneDetails("Nokia", "G400 5G", "Durable & Reliable", "199"));

        // Brand ID to Name mapping
        Map<String, String> brandIdToNameMap = new HashMap<>();
        brandIdToNameMap.put("1", "Samsung");
        brandIdToNameMap.put("2", "Apple");
        brandIdToNameMap.put("3", "Google");
        brandIdToNameMap.put("4", "OnePlus");
        brandIdToNameMap.put("5", "Xiaomi");
        brandIdToNameMap.put("6", "Motorola");
        brandIdToNameMap.put("7", "Nokia");

        // 5. Generate Recommendations for user 3 (You can change user ID here)
        long userId = 10;
        int numberOfRecommendations = 2;
        List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);

        // 6. Print the recommendations
        System.out.println("--- Phone Recommendations for User " + userId + " ---");
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found for user " + userId);
        } else {
            for (RecommendedItem recommendation : recommendations) {
                String brandId = String.valueOf(recommendation.getItemID());
                String brandName = brandIdToNameMap.get(brandId);
                if (phoneMetadata.containsKey(brandId)) {
                    Map<String, String> phoneDetails = phoneMetadata.get(brandId);
                    String phoneName = phoneDetails.get("name");
                    String phoneCategory = phoneDetails.get("category");
                    String phonePrice = phoneDetails.get("price");
                    System.out.println("Brand: " + brandName + ", Name: " + phoneName + ", Category: " + phoneCategory + ", Price: $" + phonePrice + ", Predicted Preference: " + recommendation.getValue());
                } else {
                    System.out.println("Brand ID: " + brandId + ", Preference Value: " + recommendation.getValue() + " (Phone details not found)");
                }
            }
        }
        System.out.println("--------------------------------------------------"); // End separator

    }

    // Helper function to create phone details map
    private static Map<String, String> createPhoneDetails(String brandName, String name, String category, String price) {
        Map<String, String> details = new HashMap<>();
        details.put("name", name);
        details.put("category", category);
        details.put("brand", brandName);
        details.put("price", price);
        return details;
    }
}