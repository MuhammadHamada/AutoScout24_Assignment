package com.company;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

class Pair<F,S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

public class Main {


    public static ArrayList<Listing> listings = new ArrayList<>();
    public static ArrayList<Contact> contacts = new ArrayList<>();

    private static int binarySearchOnListings(int listing_id) {
        int l = 0, r = listings.size()-1;
        while (l < r) {

            int mid = (l + r)/2;
            if(listings.get(mid).getId() >= listing_id)
                r = mid;
            else
                l = mid + 1;

        }
        return l;
    }

    private static void printAvgPricePerSellerType(ArrayList<Double> arrayList) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        System.out.println("[1] Average Listing Selling Price per Seller Type");
        System.out.println("Seller Type || Average in Euro ");
        System.out.println("Private     ||   € " + numberFormat.format(arrayList.get(0)));
        System.out.println("dealer      ||   € " + numberFormat.format(arrayList.get(1)));
        System.out.println("other       ||   € " + numberFormat.format(arrayList.get(2)));
        System.out.println("################################");
    }

    public static ArrayList<Double> calcAvgPricePerSellerType() {

        long privateSellerPrices = 0;
        long dealerSellerPrices = 0;
        long otherSellerPrices = 0;

        double privateSize = 0;
        double dealerSize = 0;
        double otherSize = 0;

        for (Listing listing : listings) {

            if (listing.getSellerType().equals("\"private\"")) {
                privateSellerPrices += listing.getPrice();
                privateSize++;
            } else if (listing.getSellerType().equals("\"dealer\"")) {
                dealerSellerPrices += listing.getPrice();
                dealerSize++;
            } else {
                otherSellerPrices += listing.getPrice();
                otherSize++;
            }
        }
        ArrayList<Double> res = new ArrayList<>();
        res.add(privateSellerPrices / privateSize);
        res.add(dealerSellerPrices / dealerSize);
        res.add(otherSellerPrices / otherSize);
        return res;
    }

    private static void printPercentualDistributionOfAvailableCarsByMake(ArrayList<Pair<Double,String>> distribution) {

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        System.out.println("[2] Percentual distribution of available cars by Make");
        System.out.println("Make  ---> Distribution");
        for (Pair<Double, String> doubleStringPair : distribution) {
            System.out.println(doubleStringPair.second + "  --->  " + numberFormat.format(doubleStringPair.first * 100) + " %");
        }
        System.out.println("################################");

    }

    public static ArrayList<Pair<Double,String>> calPercentualDistributionOfAvailableCarsByMake() {

        HashMap<String,Integer> makeCount = new HashMap<>();
        ArrayList<Pair<Double,String>> distribution = new ArrayList<>();

        for (Listing listing : listings) {
            String key = listing.getMake();
            Integer curVal = 0;
            if (makeCount.containsKey(key)) {
                curVal = makeCount.get(key);
            }
            makeCount.put(key, curVal + 1);
        }

        makeCount.forEach((k,v) -> {
            distribution.add(new Pair<>(v / (listings.size() * 1.0), k));
        });

        distribution.sort((o1, o2) -> Double.compare(o2.first, o1.first));

        return distribution;
    }

    private static void printAvgPriceOf30MostConLis(Double result) {

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        System.out.println("[3] Average price of the 30% most contacted listings");
        System.out.println("Average Price = € " +  numberFormat.format(result));
        System.out.println("################################");

    }

    public static Double calcAvgPriceOf30MostConLis() {

        HashMap<Integer,Integer> listingContactsCount = new HashMap<>();
        ArrayList<Pair<Integer,Integer>> listingContactsCountArray = new ArrayList<>();

        for (Contact contact : contacts) {

            int key = contact.getId();
            int curVal = 0;
            if (listingContactsCount.containsKey(key)) {
                curVal = listingContactsCount.get(key);
            }
            listingContactsCount.put(key, curVal + 1);
        }

        listingContactsCount.forEach((k,v) -> {
            listingContactsCountArray.add(new Pair<>(k,v));
        });

        listingContactsCountArray.sort(((o1, o2) -> Integer.compare(o2.second, o1.second)));

        int sizeOf30MostContactedListings = (int)(listingContactsCount.size() * 0.3);
        long sumOfPrices = 0;
        for (int i = 0; i < sizeOf30MostContactedListings; ++i) {

            int listing_id = listingContactsCountArray.get(i).first;

            // binary search to find the index of the current listing_id in listings array
            int index = binarySearchOnListings(listing_id);

            sumOfPrices += listings.get(index).getPrice();

        }

        return sumOfPrices/(sizeOf30MostContactedListings*1.0);
    }

    private static void printTop5MostContactedListingsPerMonth(ArrayList<Pair<Pair<Integer,Integer>,ArrayList<Pair<Integer,Integer>>>> resultPerMonth) {

        System.out.println("[4] The Top 5 most contacted listings per Month");

        for (int i = 0; i < resultPerMonth.size(); ++i) {
            int currentMonth = resultPerMonth.get(i).first.first;
            int currentYear = resultPerMonth.get(i).first.second;
            System.out.println("Month: " + (currentMonth + 1 <= 9 ? "0":"") + (currentMonth + 1) + "." + currentYear);
            System.out.println("Ranking ||  Listing id  ||      Make     ||  Selling Price  ||  Mileage  ||  Total Amount of Contacts");
            for (int j = 0; j < 5; ++j) {

                int listing_id = resultPerMonth.get(i).second.get(j).first;

                // binary search to find the index of the current listing_id in listings array
                int index = binarySearchOnListings(listing_id);
                System.out.println(j + 1 + "       || " + listing_id + "          || " +
                        listings.get(index).getMake() + "       || € " +
                        listings.get(index).getPrice() + "       || " +
                        listings.get(index).getMileage() + " KM    || " +
                        resultPerMonth.get(i).second.get(j).second);
            }

        }

    }

    public static ArrayList<Pair<Pair<Integer,Integer>,ArrayList<Pair<Integer,Integer>>>> calcTop5MostContactedListingsPerMonth() {

        HashMap<Integer,Integer> listingContactsCount = new HashMap<>();
        ArrayList<Pair<Integer,Integer>> listingContactsCountArray = new ArrayList<>();
        ArrayList<Pair<Pair<Integer,Integer>,ArrayList<Pair<Integer,Integer>>>> result = new ArrayList<>();

        Date date = contacts.get(0).getDate();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        for (int i = 0; i < contacts.size(); ++i) {

            int key = contacts.get(i).getId();
            int curVal = 0;
            if(listingContactsCount.containsKey(key)) {
                curVal = listingContactsCount.get(key);
            }
            listingContactsCount.put(key, curVal + 1);

            date = contacts.get(i).getDate();
            cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(date);

            if((currentMonth != cal.get(Calendar.MONTH) || currentYear != cal.get(Calendar.YEAR)) || i == contacts.size()-1){

                listingContactsCount.forEach((k,v) -> {
                    listingContactsCountArray.add(new Pair<>(k,v));
                });

                listingContactsCountArray.sort(((o1, o2) -> Integer.compare(o2.second, o1.second)));

                ArrayList<Pair<Integer,Integer>> tmpArray = new ArrayList<>();
                for (int j = 0; j < 5; ++j) {
                    tmpArray.add(listingContactsCountArray.get(j));
                }

                result.add(new Pair<>(new Pair<>(currentMonth,currentYear),tmpArray));

                currentMonth = cal.get(Calendar.MONTH);
                currentYear = cal.get(Calendar.YEAR);
                listingContactsCount.clear();
                listingContactsCountArray.clear();

                listingContactsCount.put(key, 1);
            }
        }
        return result;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        BufferedReader csvReader = null;
        try {
            String row, path;
            System.out.println("Please Enter the path of listings.csv file : ");
            path = scanner.nextLine();
            csvReader = new BufferedReader(new FileReader(path));
            row = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // do something with the data
                int id = Integer.parseInt(data[0]);
                String make = data[1];
                int price =  Integer.parseInt(data[2]);
                int mileage = Integer.parseInt(data[3]);
                String sellerType = data[4];
                listings.add(new Listing(id, make, price, mileage, sellerType));
            }
            System.out.println("Please Enter the path of contacts.csv file : ");
            path = scanner.nextLine();
            csvReader = new BufferedReader(new FileReader(path));
            row = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // do something with the data
                int listing_id = Integer.parseInt(data[0]);
                Date contact_date = new java.util.Date(Long.parseLong(data[1]));
                contacts.add(new Contact(listing_id, contact_date));
            }

            csvReader.close();

            Collections.sort(listings);
            Collections.sort(contacts);

            ArrayList<Double> AvgPricePerSellerType = calcAvgPricePerSellerType();
            printAvgPricePerSellerType(AvgPricePerSellerType);


            ArrayList<Pair<Double,String>> distribution = calPercentualDistributionOfAvailableCarsByMake();
            printPercentualDistributionOfAvailableCarsByMake(distribution);

            double averagePrice = calcAvgPriceOf30MostConLis();
            printAvgPriceOf30MostConLis(averagePrice);

            ArrayList<Pair<Pair<Integer,Integer>,ArrayList<Pair<Integer,Integer>>>> resultPerMonth = calcTop5MostContactedListingsPerMonth();
            printTop5MostContactedListingsPerMonth(resultPerMonth);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
