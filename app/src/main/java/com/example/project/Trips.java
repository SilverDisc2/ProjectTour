package com.example.project;

public class Trips {
        private String tripName;
        private String startDate;
        private String endDate;
        private String tripId;
        private String description;
        private double budget;
        private int month;
        private  int monthEnd;

        public int getMonth() {
                return month;
        }

        public double getBudget() {
                return budget;
        }

        public Trips(String tripName, String startDate, String endDate, String description, double budget, int month,int monthEnd) {
                this.tripName = tripName;
                this.startDate = startDate;
                this.endDate = endDate;
                this.monthEnd=monthEnd;
                this.description = description;
                this.budget = budget;
                this.month = month;
        }

        public Trips(String tripName, String startDate, String endDate, String tripId, String description, double budget) {
                this.tripName = tripName;
                this.startDate = startDate;
                this.endDate = endDate;
                this.tripId = tripId;
                this.description=description;
                this.budget=budget;

        }

        public Trips(String tripName, String startDate, String endDate, String description,double budget) {
                this.tripName = tripName;
                this.startDate = startDate;
                this.endDate = endDate;
                this.description = description;
                this.budget=budget;
        }
        public Trips(){

        }
        public Trips(String tripId, String tripName){
                this.tripName = tripName;
                this.tripId=tripId;

        }



        public Trips(String startDate, String endDate, String tripName) {
                this.startDate = startDate;
                this.endDate = endDate;
                this.tripName = tripName;
        }

        public String getTripName() {
                return tripName;
        }

        public String getStartDate() {
                return startDate;
        }

        public String getEndDate() {
                return endDate;
        }

        public String getTripId() {
                return tripId;
        }

        public String getDescription() {
                return description;
        }

        public void setTripId(String tripId) {
                this.tripId = tripId;
        }
}
