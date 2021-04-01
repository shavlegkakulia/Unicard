package ge.unicard.pos.urlGenerator;

import java.util.Date;

public class UrlGenerator {


    public static Date date = new Date();
    public static final String string = date.toString();

    public static final  String URL = "UniProcessingPrivate.UniPosSVC.svc/GetDeviceInfo?" + string;
}
