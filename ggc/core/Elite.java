package ggc.core;

import java.io.Serializable;

public class Elite implements Serializable, StatuteOfPartner {

	/** Serial number for serialization. */
    private static final long serialVersionUID = 1713873628034722300L;

    @Override
    public Double updateCostSale(int currentDate, int limitDate, PaymentPeriod paymentPeriod, int actualCost) {
        if (paymentPeriod == PaymentPeriod.P1) {
			return 0.9 * actualCost;
		}
		else if (paymentPeriod == PaymentPeriod.P2) {
			return Double.valueOf(actualCost);
		}
		else if (paymentPeriod == PaymentPeriod.P3) {
			int interval = currentDate - limitDate;
			return ((0.05 * actualCost) * interval ) + actualCost;
		}
		else if (paymentPeriod == PaymentPeriod.P4) {
			int interval = currentDate - limitDate;
			return ((0.1 * actualCost) * interval ) + actualCost;
		}
        return null;
    }
    public String toString() {
		return "NORMAL";
	}
}
