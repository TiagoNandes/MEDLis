package com.example.medlis.Medicine;

public class MedicineClass {


        private String leaflet;
        private String name;
        private int box_quantity;


        public MedicineClass(int box_quantity, String leaflet, String name ) {
            this.box_quantity = box_quantity;
            this.leaflet = leaflet;
            this.name = name;
        }

        public String getLeaflet() {
            return leaflet;
        }

        public void setLeaflet(String leaflet) {
            this.leaflet = leaflet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBoxQuantity() {
            return box_quantity;
        }

        public void setBoxQuantity(int box_quantity) {
            this.box_quantity = box_quantity;
        }

}
