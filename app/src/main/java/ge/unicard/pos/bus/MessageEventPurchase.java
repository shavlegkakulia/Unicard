package ge.unicard.pos.bus;

import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;

public class MessageEventPurchase {

    public final PrinterInteractor printerInteractor;
    public final PurchaseResponse purchaseResponse;

    public MessageEventPurchase(PrinterInteractor printerInteractor, PurchaseResponse purchaseResponse) {
        this.printerInteractor = printerInteractor;
        this.purchaseResponse = purchaseResponse;
    }
}
