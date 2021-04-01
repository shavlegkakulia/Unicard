package ge.unicard.pos.di.component;

import android.content.Context;

import com.nexgo.oaf.apiv3.DeviceEngine;

import javax.inject.Singleton;

import dagger.Component;
import ge.unicard.pos.App;
import ge.unicard.pos.di.module.ApplicationModule;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.presentation.alert_dialogs.RewersFragment;
import ge.unicard.pos.presentation.cards.BalanceFragment;
import ge.unicard.pos.presentation.cards.BonusAccumulationFragment;
import ge.unicard.pos.presentation.cards.MakePaymentFragment;
import ge.unicard.pos.presentation.cards.PurchaseFragment;
import ge.unicard.pos.presentation.launcher.LauncherFragment;
import ge.unicard.pos.presentation.cards.RewardEnterFragment;
import ge.unicard.pos.presentation.report_action.ActionReportFragment;
import ge.unicard.pos.presentation.reward_preview.RewardRealizeFragment;
import ge.unicard.pos.presentation.send_mrz.SendMRZFragment2;
import ge.unicard.pos.presentation.send_otp.SendOtpFragment;
import ge.unicard.pos.presentation.transactions.TransactionsFragment;
import ge.unicard.pos.presentation.transactions.TransactionsPresenter;

/**
 * Created by Akaki on 10/23/18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    App application();

    DeviceEngine deviceEngine();

    QrScannerInteractor getQrScannerInteractor();

    void inject(BonusAccumulationFragment fragment);

    void inject(MakePaymentFragment fragment);

    void inject(SendOtpFragment fragment);

    void inject(PurchaseFragment fragment);

    void inject(BalanceFragment fragment);

    void inject(RewardEnterFragment fragment);

    void inject(LauncherFragment fragment);

    void inject(RewardRealizeFragment fragment);

    void inject(SendMRZFragment2 fragment);

    void inject(ActionReportFragment fragment);

    void inject(TransactionsFragment transactionsFragment);
}