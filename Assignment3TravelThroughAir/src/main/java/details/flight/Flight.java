package details.flight;
public class Flight {

	public Integer flightnumber;
	public String departureCity;
	public String arrivalCity;
//	public LocalDate arrivalDate;
//	public LocalDate departureDate;
	public String time;
	public Integer legs;
	public Integer cost;
	public String traveltime;//in minutes
	public Integer offer;//(in %)

    public Flight(Integer flightnumber,String departureCity, String arrivalCity, String time, Integer legs, Integer cost, String traveltime, Integer offer) {
        this.flightnumber=flightnumber;
    	this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.cost = cost;
        this.legs = legs;
        this.time=time;
        this.traveltime = traveltime;
        this.offer=offer;

    }

    public Integer getFlightnum() {
        return flightnumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getLegs() {
    	return legs;
    }

    public String getTime() {
    	return time;
    }

    public String getTraveltime() {
    	return traveltime;
    }

    public Integer getOffer() {
    	return offer;
    }



}
