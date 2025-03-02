/* -------------------------------------------------------------------------- */
/*                                //For gitHub                                */
/* -------------------------------------------------------------------------- */

import java.util.*;

// Custom Exceptions for the Pet Adoption System

class AdoptionLimitExceededException extends Exception {
    // This exception is thrown when an adopter tries to adopt more pets than the allowed limit.
    public AdoptionLimitExceededException(String message) {
        super(message);
    }
}

class InvalidAgeException extends Exception {
    // This exception is thrown when a pet's age is invalid (e.g., negative).
    public InvalidAgeException(String message) {
        super(message);
    }
}

class AdopterAgeException extends Exception {
    // This exception is thrown when an adopter's age is below the minimum required age.
    public AdopterAgeException(String message) {
        super(message);
    }
}

class PetNotFitForAdoptionException extends Exception {
    // This exception is thrown when a pet is not suitable for adoption due to health reasons (e.g., not vaccinated or microchipped).
    public PetNotFitForAdoptionException(String message) {
        super(message);
    }
}

class AdoptionFormNotFilledException extends Exception {
    // This exception is thrown when an adopter tries to adopt a pet without filling out the required adoption form.
    public AdoptionFormNotFilledException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    // This exception is thrown when an adopter does not have enough funds to cover the adoption fee.
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class LivingSituationNotSuitableException extends Exception {
    // This exception is thrown when an adopter's living situation is not suitable for a pet (e.g., too small living space).
    public LivingSituationNotSuitableException(String message) {
        super(message);
    }
}

// ============================== Pet Class ==============================
class Pet {
    // Instance variables (attributes) representing the properties of a pet
    private String petId, name, species, breed, size, gender, temperament, healthStatus;
    private int age;
    private boolean isAdopted;
    private double adoptionFee;

    // Constructor for creating a new Pet object
    public Pet(String petId, String name, String species, String breed, String size, String gender, String temperament, int age, String healthStatus)
            throws InvalidAgeException, PetNotFitForAdoptionException {

        // Input validation: Check for invalid age (negative)
        if (age < 0) throw new InvalidAgeException("Age cannot be negative for " + name);

        // Input validation: Check if the pet is fit for adoption (vaccinated and microchipped)
        if (!healthStatus.equalsIgnoreCase("Vaccinated") && !healthStatus.equalsIgnoreCase("Microchipped"))
            throw new PetNotFitForAdoptionException(name + " is not fit for adoption.");

        // Initialize the pet's attributes with the provided values
        this.petId = petId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.size = size;
        this.gender = gender;
        this.temperament = temperament;
        this.age = age;
        this.healthStatus = healthStatus;
        this.isAdopted = false; // Initially, the pet is not adopted
        this.adoptionFee = calculateAdoptionFee(size); // Calculate adoption fee based on size
    }

    // Method to calculate the adoption fee based on the pet's size
    private double calculateAdoptionFee(String size) {
        // Switch statement to determine the fee based on size (case-insensitive)
        return switch (size.toLowerCase()) {
            case "small" -> 50.0;
            case "medium" -> 100.0;
            case "large" -> 150.0;
            default -> 0.0; // Default fee if size is not recognized
        };
    }

    // Getter method to retrieve the pet's ID
    public String getPetId() {
        return petId;
    }

    // Getter method to retrieve the pet's name
    public String getName() {
        return name;
    }

    // Getter method to retrieve the pet's breed
    public String getBreed() {
        return breed;
    }

    // Getter method to check if the pet is adopted
    public boolean isAdopted() {
        return isAdopted;
    }

    // Getter method to retrieve the adoption fee
    public double getAdoptionFee() {
        return adoptionFee;
    }

    // Setter method to set the adoption status of the pet
    public void setAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    // Override the toString() method to provide a string representation of the Pet object
    @Override
    public String toString() {
        return "Pet ID: " + petId + ", Name: " + name + ", Adopted: " + isAdopted;
    }
}

// ============================== Adopter Class ==============================
class Adopter {
    // Instance variables (attributes) representing the properties of an adopter
    private String adopterId, name, contactInfo, livingSituation, petRestrictions;
    private int age;
    private boolean hasFilledAdoptionForm;
    private double balance;
    private List<Pet> adoptedPets;

    // Constructor for creating a new Adopter object
    public Adopter(String adopterId, String name, String contactInfo, int age, boolean hasFilledAdoptionForm, double balance, String livingSituation, double livingSpace, String petRestrictions)
            throws AdopterAgeException, LivingSituationNotSuitableException {
        // Input validation: Check if the adopter is at least 18 years old
        if (age < 18) throw new AdopterAgeException("Adopter must be at least 18 years old.");

        // Input validation: Check if the living space is suitable (at least 300 units)
        if (livingSpace < 300) throw new LivingSituationNotSuitableException("Living space is too small for a pet.");

        // Initialize the adopter's attributes with the provided values
        this.adopterId = adopterId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.age = age;
        this.hasFilledAdoptionForm = hasFilledAdoptionForm;
        this.balance = balance;
        this.livingSituation = livingSituation;
        this.petRestrictions = petRestrictions;
        this.adoptedPets = new ArrayList<>(); // Initialize the list of adopted pets
    }

    // Getter method to retrieve the adopter's ID
    public String getAdopterId() {
        return adopterId;
    }

    // Getter method to retrieve the adopter's name
    public String getAdopterName() {
        return name;
    }

    // Getter method to retrieve the adopter's balance
    public double getBalance() {
        return balance;
    }

    // Getter method to retrieve the list of adopted pets
    public List<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    // Method to allow the adopter to adopt a pet
    public void adoptPet(Pet pet) throws AdoptionLimitExceededException, AdoptionFormNotFilledException, InsufficientFundsException {

        // Check if the adopter has filled the adoption form
        if (!hasFilledAdoptionForm) throw new AdoptionFormNotFilledException("Adoption form not filled.");

        // Check if the adopter has reached the adoption limit (3 pets)
        if (adoptedPets.size() >= 3) throw new AdoptionLimitExceededException("Adoption limit exceeded.");

        // Check if the adopter has sufficient funds to adopt the pet
        if (balance < pet.getAdoptionFee()) throw new InsufficientFundsException("Insufficient funds to adopt " + pet.getName());

        // Deduct the adoption fee from the adopter's balance
        balance -= pet.getAdoptionFee();

        // Add the adopted pet to the adopter's list of adopted pets
        adoptedPets.add(pet);

        // Set the pet's adoption status to adopted
        pet.setAdopted(true);

        // Print a confirmation message
        System.out.println(name + " adopted " + pet.getName() + " for $" + pet.getAdoptionFee());
    }
}

// ============================== Shelter Class ==============================
class Shelter {
    // Instance variables (attributes) representing the properties of a shelter
    private String shelterName;
    private List<Pet> petList;
    private double totalDonations;
    private Map<String, Double> donorRecords; // Stores donor names and their total donation amounts
    private Map<String, List<Integer>> adopterRatings; // Stores adopter IDs and their ratings
    private Map<String, String> adopterReviews; // Stores adopter IDs and their reviews
    private List<Integer> shelterRatings; // Stores shelter ratings
    private List<String> shelterReviews; // Stores shelter reviews

    // Constructor for creating a new Shelter object
    public Shelter(String shelterName) {
        this.shelterName = shelterName;
        this.petList = new ArrayList<>(); // Initialize the pet list
        this.totalDonations = 0.0; // Initialize total donations to zero
        this.donorRecords = new HashMap<>(); // Initialize the donor records map
        this.adopterRatings = new HashMap<>(); // Initialize the adopter ratings map
        this.adopterReviews = new HashMap<>(); // Initialize the adopter reviews map
        this.shelterRatings = new ArrayList<>(); // Initialize the shelter ratings list
        this.shelterReviews = new ArrayList<>(); // Initialize the shelter reviews list
    }

    // Method to add a pet to the shelter
    public void addPet(Pet pet) {
        petList.add(pet); // Add the pet to the pet list
        System.out.println(pet.getName() + " added to the shelter.");
    }

    // Method to accept a donation
    public void acceptDonation(String donorName, double amount) {
        // Input validation: Check if the donation amount is positive
        if (amount <= 0) {
            System.out.println("Donation must be greater than zero.");
            return;
        }

        totalDonations += amount; // Update the total donations
        donorRecords.put(donorName, donorRecords.getOrDefault(donorName, 0.0) + amount); // Update donor records (add new or increment existing)
        System.out.println(donorName + " donated ₹" + amount + ".");
    }

    // Method to rate an adopter
    public void rateAdopter(String adopterId, int rating, String review) {
        adopterRatings.putIfAbsent(adopterId, new ArrayList<>()); // Create a new list for the adopter if it doesn't exist
        adopterRatings.get(adopterId).add(rating); // Add the rating
        adopterReviews.put(adopterId, review); // Add the review
        System.out.println("Adopter " + adopterId + " received a rating of " + rating + " stars.");
    }

    // Method to rate the shelter
    public void rateShelter(int rating, String review) {
        shelterRatings.add(rating); // Add the rating
        shelterReviews.add(review); // Add the review
        System.out.println("Shelter received a rating of " + rating + " stars.");
    }

    // Method to calculate the average shelter rating
    public double getAverageShelterRating() {
        return shelterRatings.stream().mapToInt(Integer::intValue).average().orElse(0.0); // Calculate average using streams
    }

    // Method to calculate the average rating for a specific adopter
    public double getAverageAdopterRating(String adopterId) {
        return adopterRatings.getOrDefault(adopterId, new ArrayList<>()) // Get ratings for the adopter or an empty list if not found
                .stream().mapToInt(Integer::intValue).average().orElse(0.0); // Calculate average using streams
    }

    // Method to display shelter reviews
    public void displayShelterReviews() {
        System.out.println("\nShelter Reviews:");
        shelterReviews.forEach(System.out::println); // Print each review
    }

    // Method to display adopter reviews
    public void displayAdopterReviews() {
        System.out.println("\nAdopter Reviews:");
        adopterReviews.forEach((id, review) -> System.out.println("Adopter " + id + ": " + review)); // Print each review with adopter ID
    }

    // Method to find a pet by name
    public Pet findPetByName(String name) {
        return petList.stream()
                .filter(pet -> pet.getName().equalsIgnoreCase(name)) // Filter pets by name (case-insensitive)
                .findFirst() // Get the first matching pet
                .orElse(null); // Return null if no pet is found
    }

    // Method to find pets by breed
    public List<Pet> findPetsByBreed(String breed) {
        return petList.stream()
                .filter(pet -> pet.getBreed().equalsIgnoreCase(breed)) // Filter pets by breed (case-insensitive)
                .toList(); // Collect the matching pets into a list
    }

    // Method to display available pets (not adopted)
    public void showAvailablePets() {
        System.out.println("\nAvailable Pets:");
        petList.stream()
                .filter(pet -> !pet.isAdopted()) // Filter pets that are not adopted
                .forEach(System.out::println); // Print each available pet
    }

    // Method to display donation information
    public void showDonations() {
        System.out.println("\nTotal Donations: ₹" + totalDonations);
        System.out.println("Donor Records:");
        donorRecords.forEach((donor, amount) -> System.out.println(donor + ": ₹" + amount)); // Print each donor and their donation amount
    }
}

// Main Class 
public class PetAdoptionSystem{
    public static void main(String[] args) {
        System.out.println("============= PET ADOPTION SYSTEM TESTING =============\n");
        Shelter shelter = new Shelter("Happy Paws Shelter");

        // Test Case 1: Adding Pets with Various Scenarios
        System.out.println("TEST CASE 1: ADDING PETS");
        System.out.println("------------------------");
        try {
            // Valid pet additions
            shelter.addPet(new Pet("P001", "Buddy", "Dog", "Labrador", "Large", "Male", "Friendly", 2, "Vaccinated"));
            shelter.addPet(new Pet("P002", "Whiskers", "Cat", "Siamese", "Small", "Female", "Shy", 1, "Microchipped"));
            
            // Test invalid age
            try {
                shelter.addPet(new Pet("P003", "Max", "Dog", "Poodle", "Small", "Male", "Playful", -1, "Vaccinated"));
            } catch (InvalidAgeException e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }

            // Test invalid health status
            try {
                shelter.addPet(new Pet("P004", "Luna", "Cat", "Persian", "Medium", "Female", "Calm", 3, "Not Vaccinated"));
            } catch (PetNotFitForAdoptionException e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        // Test Case 2: Testing Adopter Registration with Various Scenarios
        System.out.println("\nTEST CASE 2: ADOPTER REGISTRATION");
        System.out.println("--------------------------------");
        try {
            // Valid adopter
            Adopter validAdopter = new Adopter("A001", "John Doe", "john@email.com", 25, true, 1000, 
                "Apartment", 500, "No Restrictions");
            System.out.println("Valid adopter registered: " + validAdopter.getAdopterName());

            // Test underage adopter
            try {
                new Adopter("A002", "Young Person", "young@email.com", 16, true, 500, 
                    "House", 400, "No Restrictions");
            } catch (AdopterAgeException e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }

            // Test small living space
            try {
                new Adopter("A003", "Small Space", "small@email.com", 30, true, 1000, 
                    "Studio", 200, "No Restrictions");
            } catch (LivingSituationNotSuitableException e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        // Test Case 3: Testing Pet Adoption Scenarios
        System.out.println("\nTEST CASE 3: PET ADOPTION SCENARIOS");
        System.out.println("----------------------------------");
        try {
            // Create adopters with different scenarios
            Adopter wealthyAdopter = new Adopter("A004", "Rich Person", "rich@email.com", 35, true, 
                5000, "House", 1000, "No Restrictions");
            Adopter poorAdopter = new Adopter("A005", "Poor Person", "poor@email.com", 28, true, 
                20, "Apartment", 400, "No Restrictions");
            Adopter multiPetAdopter = new Adopter("A006", "Multiple Pets", "multiple@email.com", 40, true, 
                10000, "House", 2000, "No Restrictions");

            // Test successful adoption
            Pet petToAdopt = shelter.findPetByName("Buddy");
            if (petToAdopt != null) {
                wealthyAdopter.adoptPet(petToAdopt);
            }

            // Test insufficient funds
            try {
                Pet expensivePet = shelter.findPetByName("Whiskers");
                if (expensivePet != null) {
                    poorAdopter.adoptPet(expensivePet);
                }
            } catch (InsufficientFundsException e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }

            // Test adoption limit
            try {
                // Add three pets to reach the limit
                shelter.addPet(new Pet("P007", "Pet1", "Dog", "Mixed", "Small", "Male", "Friendly", 1, "Vaccinated"));
                shelter.addPet(new Pet("P008", "Pet2", "Dog", "Mixed", "Small", "Female", "Friendly", 2, "Vaccinated"));
                shelter.addPet(new Pet("P009", "Pet3", "Dog", "Mixed", "Small", "Male", "Friendly", 1, "Vaccinated"));
                
                Pet pet1 = shelter.findPetByName("Pet1");
                Pet pet2 = shelter.findPetByName("Pet2");
                Pet pet3 = shelter.findPetByName("Pet3");
                
                if (pet1 != null && pet2 != null && pet3 != null) {
                    multiPetAdopter.adoptPet(pet1);
                    multiPetAdopter.adoptPet(pet2);
                    multiPetAdopter.adoptPet(pet3);
                    
                    // Try to adopt a fourth pet
                    shelter.addPet(new Pet("P010", "Pet4", "Dog", "Mixed", "Small", "Female", "Friendly", 2, "Vaccinated"));
                    Pet pet4 = shelter.findPetByName("Pet4");
                    if (pet4 != null) {
                        multiPetAdopter.adoptPet(pet4);
                    }
                }
            } catch (AdoptionLimitExceededException e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        // Test Case 4: Testing Search Functionality
        System.out.println("\nTEST CASE 4: SEARCH FUNCTIONALITY");
        System.out.println("--------------------------------");
        // Search by name
        Pet foundPet = shelter.findPetByName("NonExistentPet");
        System.out.println("Search for non-existent pet: " + (foundPet == null ? "Not found (as expected)" : "Unexpectedly found"));

        // Search by breed
        List<Pet> labradors = shelter.findPetsByBreed("Labrador");
        System.out.println("Number of Labradors found: " + labradors.size());

        // Test Case 5: Testing Donation System
        System.out.println("\nTEST CASE 5: DONATION SYSTEM");
        System.out.println("----------------------------");
        // Valid donation
        shelter.acceptDonation("Generous Donor", 1000);
        
        // Zero donation
        shelter.acceptDonation("Stingy Donor", 0);
        
        // Negative donation
        shelter.acceptDonation("Confused Donor", -100);
        
        // Show donation summary
        shelter.showDonations();

        // Test Case 6: Testing Rating System
        System.out.println("\nTEST CASE 6: RATING SYSTEM");
        System.out.println("--------------------------");
        // Add various ratings
        shelter.rateAdopter("A004", 5, "Excellent care of pets!");
        shelter.rateAdopter("A004", 4, "Very responsible adopter");
        shelter.rateShelter(5, "Great facility!");
        shelter.rateShelter(4, "Good service");
        
        // Display ratings and reviews
        System.out.println("Average Shelter Rating: " + shelter.getAverageShelterRating());
        System.out.println("Average Rating for Adopter A004: " + shelter.getAverageAdopterRating("A004"));
        
        shelter.displayShelterReviews();
        shelter.displayAdopterReviews();

        // Final Status
        System.out.println("\nFINAL SYSTEM STATUS");
        System.out.println("-------------------");
        shelter.showAvailablePets();
    }
}