package com.company;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;

public class MainTest extends TestCase {

    private static final ArrayList<Listing> listings = new ArrayList<>();
    private static final ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    public void setUp() throws Exception {

        listings.add(new Listing(1000,"Audi",49717,6500, "\"private\""));
        listings.add(new Listing(1001,"Mazda",22031,7000, "\"private\""));
        listings.add(new Listing(1002,"BWM",17742,6000, "\"dealer\""));
        listings.add(new Listing(1003,"Toyota",11768,0, "\"dealer\""));
        listings.add(new Listing(1004,"Mazda",25219,3000, "\"other\""));
        listings.add(new Listing(1005,"Audi",43667,500, "\"private\""));
        listings.add(new Listing(1006,"Renault",47446,7500, "\"other\""));
        listings.add(new Listing(1007,"VW",25633,8000, "\"private\""));
        listings.add(new Listing(1008,"VW",26350,500, "\"private\""));
        listings.add(new Listing(1009,"Audi",40070,2500, "\"dealer\""));

        Main.listings = listings;

        contacts.add(new Contact(1000, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1000, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1000, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1000, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1000, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1001, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1001, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1001, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1001, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1002, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1002, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1003, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1003, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1004, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1004, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1005, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1006, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1007, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1008, new java.util.Date(Long.parseLong("1592498493000"))));
        contacts.add(new Contact(1009, new java.util.Date(Long.parseLong("1592498493000"))));

        Main.contacts = contacts;

        Collections.sort(listings);
        Collections.sort(contacts);

    }

    public void testCalcAvgPricePerSellerType() {

        ArrayList<Double> result = Main.calcAvgPricePerSellerType();
        assertEquals(result.get(0), (49717 + 22031 + 43667 + 25633 + 26350) / 5.0); // private
        assertEquals(result.get(1), (17742 + 11768 + 40070) / 3.0); // dealer
        assertEquals(result.get(2),(25219 + 47446) / 2.0); // other
    }

    public void testCalPercentualDistributionOfAvailableCarsByMake() {

        ArrayList<Pair<Double,String>> result = Main.calPercentualDistributionOfAvailableCarsByMake();
        for (Pair<Double, String> res : result) {
            if (res.second.equals("Audi")) assertEquals(res.first, 3/10.0);
            if (res.second.equals("Mazda")) assertEquals(res.first, 2/10.0);
            if (res.second.equals("VW")) assertEquals(res.first, 2/10.0);
            if (res.second.equals("BWM")) assertEquals(res.first, 1/10.0);
            if (res.second.equals("Toyota")) assertEquals(res.first, 1/10.0);
            if (res.second.equals("Renault")) assertEquals(res.first, 1/10.0);
        }
    }

    public void testCalcAvgPriceOf30MostConLis() {
        assertEquals((49717 + 22031 + 17742)/3.0,Main.calcAvgPriceOf30MostConLis());
    }

    public void testCalcTop5MostContactedListingsPerMonth() {

        ArrayList<Pair<Pair<Integer,Integer>,ArrayList<Pair<Integer,Integer>>>> result = Main.calcTop5MostContactedListingsPerMonth();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).first.first.intValue(), 5); // month
        assertEquals(result.get(0).first.second.intValue(), 2020); // year

        assertEquals(result.get(0).second.get(0).first.intValue(), 1000); // check listing id
        assertEquals(result.get(0).second.get(0).second.intValue(), 5);  // check total amount of contacts

        assertEquals(result.get(0).second.get(1).first.intValue(), 1001); // check listing id
        assertEquals(result.get(0).second.get(1).second.intValue(), 4);  // check total amount of contacts

        assertEquals(result.get(0).second.get(2).first.intValue(), 1002); // check listing id
        assertEquals(result.get(0).second.get(2).second.intValue(), 2); // check total amount of contacts

        assertEquals(result.get(0).second.get(3).first.intValue(), 1003); // check listing id
        assertEquals(result.get(0).second.get(3).second.intValue(), 2); // check total amount of contacts

        assertEquals(result.get(0).second.get(4).first.intValue(), 1004); // check listing id
        assertEquals(result.get(0).second.get(4).second.intValue(), 2); // check total amount of contacts

    }
}