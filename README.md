# AI-Powered Phone Brand Recommendation System (Java & Apache Mahout)


## Project Overview

This project delivers a fully functional, AI-driven phone brand recommendation system built using Java and the robust Apache Mahout machine learning library. It leverages the power of **user-based collaborative filtering** to provide personalized phone brand suggestions to users based on their past preferences and the collective wisdom of similar users within the system.

Designed as a comprehensive learning endeavor, this project not only demonstrates the practical application of AI in recommendation systems but also provides a clear, step-by-step implementation in Java, utilizing industry-standard tools and libraries. It serves as an excellent foundation for understanding the intricacies of collaborative filtering and building intelligent, data-driven applications.

## Core Features

*   **Intelligent Phone Brand Recommendations:**  The system generates a ranked list of phone brands predicted to be of high interest to individual users.
*   **User-Based Collaborative Filtering:**  Employs a well-established and effective collaborative filtering algorithm to learn user preferences and make recommendations.
*   **Java-Based Recommendation Engine:**  The entire core logic of the recommendation engine is implemented in Java, ensuring portability, performance, and ease of integration into Java-based applications.
*   **Pearson Correlation Similarity:**  Utilizes Pearson Correlation to accurately measure the similarity between user taste profiles based on their phone brand ratings.
*   **Nearest N-User Neighborhood:**  Employs the Nearest N-User Neighborhood approach to identify a cohort of users most similar to a target user, forming the basis for recommendation generation.
*   **Sample User Rating Data:**  Includes a ready-to-use `ratings.csv` file populated with simulated user ratings for a selection of popular phone brands, allowing for immediate execution and experimentation.
*   **Phone Brand Metadata Integration:**  Incorporates phone brand metadata (name, category, price) within the Java code to present richer, more informative recommendations to the user.
*   **Clear Command-Line Output:**  Presents recommendation results in an easily understandable text-based format in the console, displaying brand names, relevant details, and predicted preference scores.
*   **Well-Structured and Documented Code:**  The Java codebase is designed with modularity and readability in mind, featuring comments and clear code organization to facilitate understanding and modification.

## Technologies and Libraries

This project is built using the following key technologies:

*   **Programming Language:** **Java (JDK 8+)**:  Chosen for its maturity, performance, cross-platform compatibility, and extensive ecosystem, making it ideal for building robust and scalable applications.
*   **Machine Learning Library:** **Apache Mahout Core (Version 0.9)**: A powerful, open-source machine learning library providing scalable algorithms for recommendation, classification, and clustering. Version 0.9 is selected for its stability and suitability for demonstrating core collaborative filtering concepts in this project.
*   **Build and Dependency Management (Optional but Recommended):** **Apache Maven**:  Maven is strongly recommended to streamline the build process, manage project dependencies (especially for Apache Mahout and SLF4j), and ensure project consistency.
*   **Logging Framework:** **SLF4j (Simple Binding 1.7.30)**:  The Simple Logging Facade for Java (SLF4j) is used by Apache Mahout for logging. The `slf4j-simple` binding provides a straightforward logging implementation for this project.
*   **Development Environment:** **IntelliJ IDEA (Recommended), Eclipse, or any Java IDE**:  An Integrated Development Environment (IDE) is essential for efficient Java development, providing features for code editing, debugging, running, and project management. IntelliJ IDEA Community Edition is a highly recommended free and powerful IDE.

## Recommendation Algorithm: User-Based Collaborative Filtering

The project implements the **User-Based Collaborative Filtering** algorithm, a widely adopted technique for personalized recommendations. This algorithm operates under the principle that users with similar taste profiles tend to have similar preferences for items.

**Algorithm Breakdown:**

1.  **Data Model Creation (`FileDataModel`):**
    *   The `FileDataModel` class from Apache Mahout is used to load user-item preference data from the `ratings.csv` file.
    *   This data model represents the core input to the recommendation engine, consisting of user IDs, phone brand IDs, and user-provided ratings.

    ```java
    DataModel model = new FileDataModel(new File("ratings.csv"));
    ```

2.  **User Similarity Calculation (`PearsonCorrelationSimilarity`):**
    *   The `PearsonCorrelationSimilarity` class is employed to compute the similarity between users.
    *   Pearson Correlation measures the linear relationship between two users' rating vectors, quantifying how closely their preferences align.

    ```java
    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
    ```

3.  **User Neighborhood Definition (`NearestNUserNeighborhood`):**
    *   The `NearestNUserNeighborhood` class is used to define the neighborhood for each user.
    *   For a given user, the neighborhood consists of the `N` (e.g., N=3 in this project) users who are most similar to them, as determined by the Pearson Correlation Similarity.

    ```java
    UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
    ```

4.  **User-Based Recommender Construction (`GenericUserBasedRecommender`):**
    *   The `GenericUserBasedRecommender` class is the core recommender implementation.
    *   It combines the `DataModel`, `UserSimilarity`, and `UserNeighborhood` components to generate recommendations.
    *   For a target user, the recommender predicts preferences for unrated items by aggregating the preferences of users in their neighborhood, weighted by their similarity to the target user.

    ```java
    UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
    ```

5.  **Recommendation Generation (`recommender.recommend()`):**
    *   The `recommender.recommend(userId, numberOfRecommendations)` method is called to generate recommendations for a specific user (`userId`).
    *   It returns a `List<RecommendedItem>` containing the top `numberOfRecommendations` (e.g., 2 in this project) phone brands predicted to be of highest interest to the user, along with their estimated preference values.

    ```java
    List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);
    ```

## Data Sources

The recommendation system relies on two primary data sources:

*   **`ratings.csv` - User Rating Data:**
    *   **File Format:** Comma Separated Values (CSV)
    *   **Location:** Project root directory (`ratings.csv`)
    *   **Data Structure:** Each line in the file represents a user rating and follows the format: `userID,brandID,preferenceValue`
        *   `userID` (long): A unique numeric identifier for each user.
        *   `brandID` (long): A numeric identifier representing a phone brand (mapping provided in `RecommendationEngine.java`).
        *   `preferenceValue` (float/double): A numerical rating assigned by the user to the brand, indicating their preference level (e.g., 1.0 to 5.0).
    *   **Example Data Snippet (`ratings.csv`):**
        ```csv
        1,1,4.5
        1,2,5.0
        1,3,4.0
        2,1,4.0
        2,3,4.8
        ...
        ```

*   **Phone Brand Metadata - In-Code `HashMap`:**
    *   **Data Structure:** `HashMap<String, Map<String, String>> phoneMetadata` within `RecommendationEngine.java`
    *   **Purpose:** Stores descriptive information about each phone brand to enhance the recommendation output.
    *   **Metadata Fields:**
        *   `name` (String): Example phone model name for the brand (e.g., "Galaxy Trend").
        *   `category` (String):  General category or focus of the brand (e.g., "All-rounder").
        *   `brand` (String): Brand name (e.g., "Samsung").
        *   `price` (String): Example price point for phones of this brand (e.g., "799").
    *   **Code Snippet (`RecommendationEngine.java` - Metadata Definition):**
        ```java
        Map<String, Map<String, String>> phoneMetadata = new HashMap<>();
        phoneMetadata.put("1", createPhoneDetails("Samsung", "Galaxy Trend", "All-rounder", "799"));
        phoneMetadata.put("2", createPhoneDetails("Apple", "iPhone SE", "Compact & Powerful", "499"));
        // ... more brand metadata ...
        ```

## Setup and Installation Guide

Follow these steps to set up and run the project:

1.  **Install Java Development Kit (JDK):**
    *   Download and install JDK 8 or a later version from [Oracle's Java Downloads](https://www.oracle.com/java/technologies/javase-downloads.html) or an open-source distribution like [OpenJDK](https://openjdk.java.net/).
    *   Ensure that the `java` and `javac` commands are accessible in your system's PATH environment variable.

2.  **Install Apache Maven (Recommended):**
    *   Download Apache Maven from [Apache Maven Downloads](https://maven.apache.org/download.cgi).
    *   Follow the installation instructions for your operating system to set up Maven and ensure the `mvn` command is available in your PATH.

3.  **Clone the Project Repository:**
    *   Open your terminal or command prompt.
    *   Navigate to the directory where you want to clone the project.
    *   Execute the following command, replacing `YOUR_GITHUB_USERNAME/YOUR_REPOSITORY_NAME` with the actual repository URL:
        ```bash
        git clone https://github.com/YOUR_GITHUB_USERNAME/YOUR_REPOSITORY_NAME.git
        cd YOUR_REPOSITORY_NAME
        ```

4.  **Open the Project in IntelliJ IDEA (or your IDE):**
    *   **Using IntelliJ IDEA (Recommended):**
        *   Launch IntelliJ IDEA.
        *   Click on "Open" or "Import Project".
        *   Browse to the cloned project directory and select the root folder or the `pom.xml` file.
        *   IntelliJ IDEA will automatically detect it as a Maven project (if `pom.xml` is present) and configure the project.
    *   **Using Eclipse or other Java IDEs:**
        *   Import the project as a Maven project (if using Maven) or as a general Java project.
        *   If not using Maven, you'll need to manually add the Apache Mahout Core and SLF4j JAR files to your project's build path (classpath).

5.  **Maven Dependency Resolution (If using Maven):**
    *   IntelliJ IDEA should automatically download and resolve the Maven dependencies (Apache Mahout and SLF4j) defined in `pom.xml`.
    *   If dependencies are not loaded, right-click on `pom.xml` and select "Maven" -> "Reload Project" or "Download Dependencies."

6.  **Run the Recommendation Engine:**
    *   In IntelliJ IDEA's Project view, expand `src/main/java`.
    *   Locate and open the `RecommendationEngine.java` file.
    *   Right-click within the `RecommendationEngine.java` editor window or in the Project view on the file name.
    *   Select "Run 'RecommendationEngine.main()'" or a similar run option provided by your IDE.

7.  **View Recommendations in Console:**
    *   After execution, the program's output, including phone brand recommendations for User 3 (by default), will be displayed in the "Run" console or terminal window of your IDE.

## Project Usage Instructions

1.  **Experiment with `ratings.csv` Data:**
    *   **Modify Ratings:** Open `ratings.csv` in a plain text editor (e.g., Notepad, TextEdit, VS Code). You can adjust the `preferenceValue` for existing ratings to observe how it influences recommendations.
    *   **Add/Remove Ratings:** You can add new user-brand ratings or remove existing ones. Ensure you maintain the CSV format: `userID,brandID,preferenceValue` per line.
    *   **Note:** Ensure you save `ratings.csv` as a plain text file with UTF-8 encoding after making changes.

2.  **Change the Target User for Recommendations:**
    *   Open `RecommendationEngine.java` in your IDE.
    *   Locate the line that sets the `userId` variable:
        ```java
        long userId = 3; // Change this user ID
        ```
    *   Modify the value of `userId` to any user ID present in your `ratings.csv` file (e.g., `1`, `2`, `4`, `5`, ..., `10`) to generate recommendations for that specific user.

3.  **Adjust Neighborhood Size (Optional):**
    *   To experiment with the neighborhood size, find this line in `RecommendationEngine.java`:
        ```java
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model); // Neighborhood size is 3
        ```
    *   Change the value `3` to a different number (e.g., `5`, `10`, or even larger). A larger neighborhood might consider more users as "similar," potentially affecting the recommendations.

## Code Snippets - Key Implementation Sections

**1. Data Model Loading:**

```java
DataModel model = new FileDataModel(new File("ratings.csv"));
UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
long userId = 3; // Target user ID
int numberOfRecommendations = 2; // Number of recommendations to generate
List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);
System.out.println("--- Phone Recommendations for User " + userId + " ---");
if (recommendations.isEmpty()) {
    System.out.println("No recommendations found for user " + userId);
} else {
    for (RecommendedItem recommendation : recommendations) {
        String brandId = String.valueOf(recommendation.getItemID());
        String brandName = brandIdToNameMap.get(brandId);
        // ... print recommendation details ...
    }
}
