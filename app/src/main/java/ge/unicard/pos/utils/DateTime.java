package ge.unicard.pos.utils;


import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@JsonAdapter(DateTime.Adapter.class)
public class DateTime extends Date {

    public DateTime() {
        super();
    }

    public DateTime(long time) {
        setTime(time);
    }

    public DateTime(Date date) {
        setTime(date.getTime());
    }

    public static class Adapter extends TypeAdapter<DateTime> {

        private final ThreadLocal<SimpleDateFormat> mDateFormatter
                = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
            }
        };

        @Override
        public DateTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            final String jsonStr = in.nextString();
            try {
                final Date date = mDateFormatter.get().parse(jsonStr);
                return new DateTime(date);
            } catch (ParseException e) {
                //ignore
            }
            return null;
        }

        @Override
        public void write(JsonWriter out,
                          DateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            final String formatted = mDateFormatter.get().format(value);
            out.value(formatted);
        }
    }
}
