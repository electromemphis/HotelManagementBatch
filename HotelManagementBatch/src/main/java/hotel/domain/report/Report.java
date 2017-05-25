package hotel.domain.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hotel.domain.Room;

public class Report {
	

	private int id;
	private String reportTitle;
	private Date date;
	private double totalCheckinPrice;
	private double totalCheckoutPrice;
//	private int totalCheckins;
//	private int totalCheckouts;
	
	private List<RoomReport> checkedOutRooms;
	private List<RoomReport> checkedInRooms;
	
	
	
	public Report() {
		super();
		checkedOutRooms = new ArrayList<>();
		checkedInRooms = new ArrayList<>();
	}
	
	
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public double getTotalCheckinPrice() {
		for (RoomReport roomReport : checkedInRooms) {
			totalCheckinPrice+=roomReport.getTotalCost();
		}
		return totalCheckinPrice;
	}
	public void setTotalCheckinPrice(double totalCheckinPrice) {
		this.totalCheckinPrice = totalCheckinPrice;
	}
	public double getTotalCheckoutPrice() {
		for (RoomReport roomReport : checkedOutRooms) {
			totalCheckoutPrice+=roomReport.getTotalCost();
		}
		return totalCheckoutPrice;
	}
	public void setTotalCheckoutPrice(double totalCheckoutPrice) {
		this.totalCheckoutPrice = totalCheckoutPrice;
	}
	public int getTotalCheckouts() {
		
		return getCheckedOutRooms().size();
	}
	public List<RoomReport> getCheckedOutRooms() {
		return checkedOutRooms;
	}
	public void setCheckedOutRooms(List<RoomReport> checkedOutRooms) {
		this.checkedOutRooms = checkedOutRooms;
	}
	public List<RoomReport> getCheckedInRooms() {
		return checkedInRooms;
	}
	public void setCheckedInRooms(List<RoomReport> checkedInRooms) {
		this.checkedInRooms = checkedInRooms;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalCheckins() {
		return getCheckedInRooms().size();
	}

}
