package ggc.core;

import java.io.Serializable;

public class Date implements Serializable{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = -3204287544652880782L;
	private int _date;

	/**
	 * 
	 * @return data atual.
	 */
	protected int getCurrentDate() {
        return _date;
    }

	/**
	 * 
	 * @param numberOfDays
	 * @return data atualizada.
	 */
    protected int advanceDays(int numberOfDays) {
        if (numberOfDays > 0) {
            _date += numberOfDays;
			return _date; }
		else
			return _date;
    }

	/**
	 * 
	 * @param days1
	 * @param days2
	 * @return data atualizada.
	 */
	protected int advanceDaysTwice(int days1, int days2) {
		if (days1 > 0 && days2 > 0) { 
            _date += days1;
			_date += days2;
			return _date; }
		else
			return _date;
	}

	/**
	 * 
	 * @param other
	 * @return data modificada.
	 */
	public int mudarDate(Date other){
		_date = other._date;
		return _date;
	}

}
