package ge.unicard.pos.utils;

import android.content.Context;
import android.content.Intent;

import ge.unicard.pos.presentation.register_new_user.RegisterCardActivity;
import ge.unicard.pos.presentation.register_new_user.RegisterMRZActivity;
import ge.unicard.pos.presentation.register_new_user.RegisterNFSActivity;
import ge.unicard.pos.presentation.register_new_user.RegisterPhoneActivity;
import ge.unicard.pos.presentation.send_mrz.SendMRZActivity;

public class RegisterHelper {

    // block count for 4 activities form func ""Registration New Customer"
    int CR_ScanMRZ = 0, CR_ScanMyFare = 0, CR_ReceivePhone = 0, CR_SwipeCard = 0;
    Context mContext;

   public  RegisterHelper(int CR_ScanMRZ , int CR_ScanMyFare , int CR_ReceivePhone , int CR_SwipeCard, Context context){
        this.CR_ScanMRZ = CR_ScanMRZ;
        this.CR_ScanMyFare = CR_ScanMyFare;
        this.CR_ReceivePhone = CR_ReceivePhone;
        this.CR_SwipeCard = CR_SwipeCard;
        this.mContext  = context;
    }




    public void filterActivitiesRegNewCustomer(){
        if(CR_ScanMRZ != 0){
            CR_ScanMRZ = 0;
            Intent i = new Intent(mContext, RegisterMRZActivity.class);
            i.putExtra("CR_ScanMRZ",CR_ScanMRZ);
            i.putExtra("CR_ScanMyFare", CR_ScanMyFare);
            i.putExtra("CR_ReceivePhone",CR_ReceivePhone);
            i.putExtra("CR_SwipeCard",CR_SwipeCard);
            mContext.startActivity(i);
        }
        else  if(CR_ScanMyFare != 0){
            CR_ScanMyFare = 0;
            Intent i = new Intent(mContext, RegisterNFSActivity.class);
            i.putExtra("CR_ScanMRZ",CR_ScanMRZ);
            i.putExtra("CR_ScanMyFare", CR_ScanMyFare);
            i.putExtra("CR_ReceivePhone",CR_ReceivePhone);
            i.putExtra("CR_SwipeCard",CR_SwipeCard);
            mContext.startActivity(i);
        }
        else  if(CR_ReceivePhone != 0){
            CR_ReceivePhone = 0;
            Intent i = new Intent(mContext, RegisterPhoneActivity.class);
            i.putExtra("CR_ScanMRZ",CR_ScanMRZ);
            i.putExtra("CR_ScanMyFare", CR_ScanMyFare);
            i.putExtra("CR_ReceivePhone",CR_ReceivePhone);
            i.putExtra("CR_SwipeCard",CR_SwipeCard);
            mContext.startActivity(i);
        }
        else  if(CR_SwipeCard != 0){
            CR_SwipeCard = 0;
            Intent i = new Intent(mContext, RegisterCardActivity.class);
            i.putExtra("CR_ScanMRZ",CR_ScanMRZ);
            i.putExtra("CR_ScanMyFare", CR_ScanMyFare);
            i.putExtra("CR_ReceivePhone",CR_ReceivePhone);
            i.putExtra("CR_SwipeCard",CR_SwipeCard);
            mContext.startActivity(i);
        }
        else{
            Intent myIntent = new Intent(mContext, SendMRZActivity.class);
            mContext.startActivity(myIntent);
        }
    }

}
