package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;

import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.reader.CardInfoEntity;
import com.nexgo.oaf.apiv3.device.reader.CardReader;
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum;
import com.nexgo.oaf.apiv3.device.reader.OnCardInfoListener;

import java.util.HashSet;

import javax.inject.Inject;

import ge.unicard.pos.interactors.base.BaseDeviceEngineInteractor;
import ge.unicard.pos.interactors.CardSearchInteractor;

public class CardSearchInetractorImpl
        extends BaseDeviceEngineInteractor
        implements CardSearchInteractor {

    private static final HashSet<CardSlotTypeEnum> sSlotTypes = new HashSet<>();

    static {
        sSlotTypes.add(CardSlotTypeEnum.SWIPE);
    }

    @Inject
    public CardSearchInetractorImpl(DeviceEngine deviceEngine) {
        super(deviceEngine);
    }

    @Override
    public void starSearch(@NonNull final Callback callback) {
        final CardReader cardReader = getDeviceEngine().getCardReader();
        callback.onSearchStarted();
        cardReader.searchCard(sSlotTypes, 60, new OnCardInfoListener() {
            @Override
            public void onCardInfo(int retCode,
                                   CardInfoEntity cardInfo) {
                switch (retCode) {
                    case SdkResult.Success:
                        final String cardNo = cardInfo.getCardNo();
                        if(cardNo!=null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResult(cardNo);
                                }
                            });
                        }
                        break;
                    case SdkResult.TimeOut:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onScannerTimeout();
                            }
                        });
                        break;
                    case SdkResult.Scanner_Customer_Exit:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onCustomerExit();
                            }
                        });
                        break;
                    default:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailed();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSwipeIncorrect() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSwipeAgain();
                    }
                });
            }

            @Override
            public void onMultipleCards() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onTooManyCards();
                    }
                });
            }
        });
    }

    @Override
    public void finishSearch() {
       getDeviceEngine().getCardReader().stopSearch();
    }
}
