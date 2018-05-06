package graph;



/** Custom implementation of a hash table using open hashing, separate chaining.
 *  Each key is a String (name of the city), each value is an integer (node id). */

public class HashTable {

    private City[] cities;
    private int id;

    /**
     * Hash table represented as an array of the City node
     */
    public HashTable() {

        this.cities = new City[20];
        this.id = 0;

    }

    /**
     * Adds a new City node into the Hashtable implementation
     * @param cityName
     */
    public void add(String cityName) {

        int index = (int) (hashcode(cityName) % cities.length);

        City newNode = new City(cityName, this.id, null);

        if (cities[index] == null) {

            cities[index] = newNode;
        } else {

            City existing = cities[index];
            newNode.setNext(existing);
            cities[index] = newNode;
        }
        this.id++;

    }

    /**
     * Gets a new City node from the Hashtable implementation
     * @param cityName
     * @return
     */
    public int get(String cityName) {

        int index = (int) (hashcode(cityName) % cities.length);

        City current = this.cities[index];

        if (current.getCity() != cityName) {
            while (current != null && !current.getCity().equals(cityName)) {
                current = current.getNext();
            }
        }
        return current.getId();
    }

    /**
     * Constructs the hashcode before storing and getting the City node
     * @param s
     * @return
     */
    private long hashcode(String s) {

        int a = 33;
        long total = 0;
        int topPower = s.length();
        char character;

        for (int i = 0; i < s.length(); i++) {


            character = s.charAt(i);
            total += ((int) character) * (Math.pow(a, topPower - i - 1));

        }
        return total;
    }







}