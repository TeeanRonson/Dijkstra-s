package graph;

public class City {

        private String city;
        private int id;
        private City next;

        public City(String city, int id, City next) {

            this.city = city;
            this.id = id;
            this.next = next;

        }

        public City getNext() {
            return this.next;
        }

        public int getId() {
            return this.id;
        }

        public String getCity() {
            return this.city;
        }

        public void setNext(City node) {
            this.next = node;
        }


}
