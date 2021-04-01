package ge.unicard.pos.bus;

import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;

public  class MessageEvent {

    public final PrinterInteractor printerInteractor;
    public final TransactionResponse transactionResponse;
    public final int TYPE_TRANSACTION;

    public MessageEvent(PrinterInteractor printerInteractor, TransactionResponse transactionResponse, int type) {
        this.printerInteractor = printerInteractor;
        this.transactionResponse = transactionResponse;
        this.TYPE_TRANSACTION = type;
    }
}
