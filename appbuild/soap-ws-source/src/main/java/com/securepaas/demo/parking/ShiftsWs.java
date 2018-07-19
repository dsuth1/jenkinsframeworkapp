package com.securepaas.demo.parking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

@WebService(targetNamespace = SoapServiceInterface.TARGET_NAMESPACE, serviceName="ShiftWs")
public class ShiftsWs extends HttpServlet implements SoapServiceInterface {
	private static final long serialVersionUID = -8394586004604850009L;
	private static final int RANDOM_SHIFT_COUNT = 150;
	private static final int HOUR_IN_MILLIS = 1000 * 60 * 60;
	private static final List<String> NAMES = Arrays.asList("Saul Cook",
			"Alison Casey",
			"Elbert Doyle",
			"Olive Jenkins",
			"Constance Higgins",
			"Erin Conner",
			"Michelle Thornton",
			"Jennifer Allison",
			"Lowell Bradley",
			"Terry Tate",
			"Leo Horton",
			"Manuel	Owens",
			"Luke Townsend",
			"Elmer Martinez",
			"Isabel Haynes",
			"Noah Zimmerman",
			"Sergio	Webb",
			"Ernesto Gonzales",
			"Tonya Fowler",
			"Jodi Reese",
			"Kendra Francis",
			"Rickey Boone",
			"Irma Baldwin",
			"Shawna Rodgers",
			"Marion Mckenzie",
			"William Stokes",
			"Sophie Maldonado",
			"Doug Copeland",
			"Trevor Stone",
			"Misty Osborne");
	private static final List<Shift> resultA=ShiftsWs.generateResults('A');
	private static final List<Shift> resultB=ShiftsWs.generateResults('B');
	private static final List<Shift> resultC=ShiftsWs.generateResults('C');
	
	public static List<Shift> generateResults(char zone)
	{
		Random random = new Random();

        List<Shift> result = new ArrayList<Shift>();
        for (int i = 0; i < RANDOM_SHIFT_COUNT; i++) {
            Shift shift = new Shift();

            shift.setArea(zone + String.valueOf(random.nextInt(4) + 1));

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, random.nextInt(1000));
            cal.set(Calendar.MINUTE, 0);
            shift.setDate(cal.getTime());

            shift.setDurationMillis(HOUR_IN_MILLIS + random.nextInt(8) * HOUR_IN_MILLIS);

            shift.setName(NAMES.get(random.nextInt(NAMES.size())));

            result.add(shift);
        }
        return result;
	}

	@Override
	public List<Shift> getAShifts() {
		return resultA;
	}

	@Override
	public List<Shift> getBShifts() {
		return resultB;
	}

	@Override
	public List<Shift> getCShifts() {
		return resultC;
	}

}
