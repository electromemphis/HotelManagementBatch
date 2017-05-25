package hotel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import hotel.domain.Reservation;
import hotel.domain.Room;
import hotel.domain.report.Report;
import hotel.domain.report.RoomReport;
import hotel.domain.status.RoomStatus;
import hotel.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	
	private int getDaysDifference(Date date1, Date date2){
		
        long diff = date2.getTime() - date1.getTime();
        diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return (int) diff;
	}
	@Override
	public Report getReport(Date date){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateCheckin = null;
		Date dateCheckout = null;
		Date dateCheckin2 = null;
		Date dateCheckout2 = null;
		try{
			dateCheckin = dateFormat.parse("24/05/2017");
			dateCheckout = dateFormat.parse("28/05/2017");
			dateCheckin2 = dateFormat.parse("23/05/2017");
			dateCheckout2 = dateFormat.parse("25/05/2017");
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		//static data only
		List<Room> checkedInRooms = new ArrayList<Room>();
		List<Room> checkedOutRooms = new ArrayList<Room>();
		
		Room room1 = new Room();
		room1.setId(new Long(1));
		room1.setRoomNumber("110");
		room1.setPrice(25.23);
		room1.setRoomStatus(RoomStatus.CHECK_IN);
		
		Room room2 = new Room();
		room2.setId(new Long(1));
		room2.setRoomNumber("115");
		room2.setPrice(30.23);
		room2.setRoomStatus(RoomStatus.CHECK_IN);
		
		checkedInRooms.add(room1);
		checkedInRooms.add(room2);
		
		Room room10 = new Room();
		room10.setId(new Long(10));
		room10.setRoomNumber("214");
		room10.setPrice(30.23);
		room10.setRoomStatus(RoomStatus.CHECK_OUT);
		
		checkedInRooms.add(room1);
		checkedOutRooms.add(room10);
				
		Reservation reservation1 = new Reservation();
		reservation1.setCheckInDate(dateCheckin);
		reservation1.setCheckOutDate(dateCheckout);
		reservation1.setRooms(checkedInRooms);
		
		Reservation reservation6 = new Reservation();
		reservation6.setCheckInDate(dateCheckin2);
		reservation6.setCheckOutDate(dateCheckout2);
		reservation6.setRooms(checkedOutRooms);
		

		RoomReport roomReport1 = new RoomReport();
		roomReport1.setRoom(room1);
		roomReport1.setCheckinDate(reservation1.getCheckInDate());
		roomReport1.setCheckoutDate(reservation1.getCheckOutDate());
		
		int totalDays = getDaysDifference(roomReport1.getCheckinDate(), roomReport1.getCheckoutDate());
		roomReport1.setTotalDays(totalDays);
		
		double totalCost = roomReport1.getRoom().getPrice() * totalDays;
		roomReport1.setTotalCost(totalCost);
		roomReport1.setGuestName("Jon Snow");
		
		
		RoomReport roomReport2 = new RoomReport();
		roomReport2.setRoom(room2);
		roomReport2.setCheckinDate(reservation1.getCheckInDate());
		roomReport2.setCheckoutDate(reservation1.getCheckOutDate());
		
		
		totalDays = getDaysDifference(roomReport2.getCheckinDate(), roomReport2.getCheckoutDate());
		roomReport2.setTotalDays(totalDays);
		
		totalCost = roomReport2.getRoom().getPrice() * totalDays;
		roomReport2.setTotalCost(totalCost);
		roomReport2.setGuestName("Daenerys Targeryen");
		
		RoomReport roomReport6 = new RoomReport();
		roomReport6.setRoom(room10);
		roomReport6.setCheckinDate(reservation6.getCheckInDate());
		roomReport6.setCheckoutDate(reservation6.getCheckOutDate());
		
		totalDays = getDaysDifference(roomReport6.getCheckinDate(), roomReport6.getCheckoutDate());
		roomReport6.setTotalDays(totalDays);
		
		totalCost = roomReport6.getRoom().getPrice() * totalDays;
		roomReport6.setTotalCost(totalCost);
		roomReport6.setGuestName("Richard Lionheart");
		

		
		
		Report report = new Report();
		report.getCheckedInRooms().add(roomReport1);
		report.getCheckedInRooms().add(roomReport2);
		report.getCheckedOutRooms().add(roomReport6);
		report.setId(311);
		report.setDate(new Date());
		report.setReportTitle("Hotel Management Daily Report for "+dateFormat.format(new Date()));
		return report;
	}
	
}
