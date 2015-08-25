package charlyn23.c4q.nyc.projectx.shames;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.Marker;
import com.parse.ParseObject;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import charlyn23.c4q.nyc.projectx.R;


public class  MaterialDialogs {
    private static final String GROUP_COLUMN = "Group";
    private static final String SHAME_TYPE_COLUMN = "shameType";
    private static final String SHAME_FEEL_COLUMN = "shameFeel";
    private static final String SHAME_DOING_COLUMN = "shameDoing";
    private static final String VERBAL_SHAME_COLUMN = "verbalShame";
    private static final String PHYSICAL_SHAME_COLUMN = "physicalShame";
    private static final String OTHER_SHAME_COLUMN = "otherShame";

    private String shameType;
    private String verbalShame;
    private String physicalShame;
    private String otherShame;
    private String shameFeel;
    private String shameDoing;
    private String group;
    private String timestamp;
    private double latitude;
    private double longitude;
    private Shame newShame;
    private Marker new_marker;
    private FloatingActionButton addShame;

    public void initialDialog(final Context context, double latitude, double longitude, final Marker new_marker, final FloatingActionButton addShame) {
        ParseObject.registerSubclass(Shame.class);
        this.latitude = latitude;
        this.longitude = longitude;
        this.new_marker = new_marker;
        this.addShame = addShame;

        new MaterialDialog.Builder(context)
                .title(R.string.new_shame_type)
                .items(R.array.shame_types)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text.equals("verbal")) {
                            shameTypeDialog(context, "verbal");
                            shameType = "verbal";
                        } else if (text.equals("physical")) {
                            shameTypeDialog(context, "physical");
                            shameType = "physical";
                        } else {
                            shameTypeDialog(context, "other");
                            shameType = "other";
                        }
                        return true;
                    }
                })
                .positiveText(R.string.next)
                .negativeText(R.string.cancel)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.cancel();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.cancel();
                        new_marker.remove();
                        addShame.setVisibility(View.INVISIBLE);
                    }
                })
                .show();
    }

    public void shameTypeDialog(final Context context, final String type) {
        int content, items;

        switch (type) {
            case "verbal":
                content = R.string.verbal_shame;
                items = R.array.verbal_types;
                break;
            case "physical":
                content = R.string.physical_shame;
                items = R.array.physical_types;
                break;
            case "other":
                content = R.string.other_shame;
                items = R.array.other_types;
                break;
            default:
                content = R.string.other_shame;
                items = R.array.other_types;
                break;
        }

        new MaterialDialog.Builder(context)
                .title(content)
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence type_choice) {
                        feelDialog(context, type, type_choice.toString());
                        if (type.equals("verbal") && which == 0){
                            verbalShame = "body comment";
                        } else if (type.equals("verbal") && which == 1) {
                            verbalShame = "vulgar";
                        } else if (type.equals("verbal") && which == 2) {
                            verbalShame = "creepy";
                        } else if (type.equals("verbal") && which == 3) {
                            verbalShame = "threatened";
                        } else if (type.equals("physical") && which == 0) {
                            physicalShame = "touch";
                        } else if (type.equals("physical") && which == 1) {
                            physicalShame = "hit";
                        } else if (type.equals("physical") && which == 2) {
                            physicalShame = "throw something";
                        } else if (type.equals("physical") && which == 3) {
                            physicalShame = "spit";
                        } else if (type.equals("physical") && which == 4) {
                            physicalShame = "pull at clothing";
                        } else if (type.equals("physical") && which == 5) {
                            physicalShame = "sexual assaulted";
                        } else if (type.equals("other") && which == 0){
                            otherShame = "follow";
                        } else if (type.equals("other") && which == 1){
                            otherShame = "expose themselves";
                        } else if (type.equals("other") && which == 2){
                            otherShame = "masturbate";
                        } else if (type.equals("other") && which == 3){
                            otherShame = "other";
                        }

                        return true;
                    }
                })
                .autoDismiss(false)
                .positiveText(R.string.next)
                .negativeText(R.string.back)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.cancel();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        initialDialog(context, latitude, longitude, new_marker, addShame);
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void feelDialog(final Context context, final String type, final String type_choice) {
        new MaterialDialog.Builder(context)
                .title(R.string.shame_feel)
                .items(R.array.feel_types)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence feel) {
                        doingDialog(context, type, type_choice, feel.toString());
                        if (which == 0) {
                            shameFeel = "barely noticed";
                        } else if (which == 1){
                            shameFeel = "angry";
                        } else if (which == 2) {
                            shameFeel = "annoyed";
                        }else if (which == 3) {
                            shameFeel = "uneasy";
                        }else if (which == 4) {
                            shameFeel = "scared";
                        }
                        return true;
                    }
                })
                .autoDismiss(false)
                .positiveText(R.string.next)
                .negativeText(R.string.back)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.cancel();
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        shameTypeDialog(context, type);
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void doingDialog(final Context context, final String type, final String type_choice, final String feel) {
        new MaterialDialog.Builder(context)
                .title(R.string.shame_doing)
                .items(R.array.doing_types)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence doing) {
                        whenDialog(context, type, type_choice, feel, doing.toString());
                        if (which == 0) {
                            shameDoing = "walking";
                        }if (which == 1) {
                            shameDoing = "jogging";
                        }if (which == 2) {
                            shameDoing = "biking";
                        }if (which == 3) {
                            shameDoing = "waiting";
                        }if (which == 4) {
                            shameDoing = "driving";
                        }if (which == 5) {
                            shameDoing = "other";
                        }
                        return true;
                    }
                })
                .autoDismiss(false)
                .positiveText(R.string.next)
                .negativeText(R.string.back)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.cancel();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        feelDialog(context, type, type_choice);
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void whenDialog(final Context context, final String type, final String type_choice, final String feel, final String doing) {
        new MaterialDialog.Builder(context)
                .title(R.string.shame_when)
                .customView(R.layout.time_picker, true)
                .autoDismiss(false)
                .positiveText(R.string.next)
                .negativeText(R.string.back)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        DatePicker date_picker = (DatePicker) dialog.findViewById(R.id.date);
                        TimePicker time_picker = (TimePicker) dialog.findViewById(R.id.time);
                        timestamp = getDateFromDatePicker(date_picker, time_picker);
                        Log.d("DATE__", timestamp);

                        whyDialog(context, type, type_choice, feel, doing);
                        dialog.cancel();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        doingDialog(context, type, type_choice, feel);
                        dialog.cancel();
                    }
                })
                .show();
    }

    public static String getDateFromDatePicker(DatePicker datePicker, TimePicker timePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        Date date = calendar.getTime();

        return new SimpleDateFormat("yyyyMMdd_HHmm").format(date);
    }

    public void whyDialog(final Context context, final String type, final String type_choice, final String feel, final String doing) {
        new MaterialDialog.Builder(context)
                .title(R.string.shame_why)
                .items(R.array.why_types)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence why) {
                        if (which == 0) {
                            group = "woman";
                        }
                        if (which == 1) {
                            group = "POC";
                        }
                        if (which == 2) {
                            group = "LGBTQ";
                        }
                        if (which == 3) {
                            group = "minor";
                        }

                        newShame = new Shame();
                        newShame.put("latitude", latitude);
                        newShame.put("longitude", longitude);
                        try {
                            String zipcode = getZipcode(context, latitude, longitude);
                            newShame.put("zipCode", zipcode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        switch (shameType) {
                            case "verbal":
                                newShame.put(SHAME_TYPE_COLUMN, "verbal");
                                newShame.saveInBackground();
                                if (verbalShame.equals("body comment")) {
                                    newShame.put(VERBAL_SHAME_COLUMN, "body comment");
                                    newShame.saveInBackground();
                                } else if (verbalShame.equals("vulgar")) {
                                    newShame.put(VERBAL_SHAME_COLUMN, "vulgar");
                                    newShame.saveInBackground();
                                } else if (verbalShame.equals("creepy")) {
                                    newShame.put(VERBAL_SHAME_COLUMN, "creepy");
                                    newShame.saveInBackground();
                                } else {
                                    newShame.put(VERBAL_SHAME_COLUMN, "threatened");
                                    newShame.saveInBackground();
                                }
                                break;
                            case "physical":
                                newShame.put(SHAME_TYPE_COLUMN, "physical");
                                newShame.saveInBackground();
                                if (physicalShame.equals("touch")) {
                                    newShame.put(PHYSICAL_SHAME_COLUMN, "touch");
                                } else if (physicalShame.equals("hit")) {
                                    newShame.put(PHYSICAL_SHAME_COLUMN, "hit");
                                    newShame.saveInBackground();
                                } else if (physicalShame.equals("throw something")) {
                                    newShame.put(PHYSICAL_SHAME_COLUMN, "throw something");
                                    newShame.saveInBackground();
                                } else if (physicalShame.equals("spit")) {
                                    newShame.put(PHYSICAL_SHAME_COLUMN, "spit");
                                    newShame.saveInBackground();
                                } else if (physicalShame.equals("pull at clothing")) {
                                    newShame.put(PHYSICAL_SHAME_COLUMN, "pull at clothing");
                                    newShame.saveInBackground();
                                } else {
                                    newShame.put(PHYSICAL_SHAME_COLUMN, "sexual assaulted");
                                    newShame.saveInBackground();
                                }
                                break;
                            case "other":
                                newShame.put(SHAME_TYPE_COLUMN, "other");
                                newShame.saveInBackground();
                                if (otherShame.equals("follow")) {
                                    newShame.put(OTHER_SHAME_COLUMN, "follow");
                                    newShame.saveInBackground();
                                } else if (otherShame.equals("expose themselves")) {
                                    newShame.put(OTHER_SHAME_COLUMN, "expose themselves");
                                    newShame.saveInBackground();
                                } else if (otherShame.equals("masturbate")) {
                                    newShame.put(OTHER_SHAME_COLUMN, "masturbate");
                                    newShame.saveInBackground();
                                } else {
                                    newShame.put(OTHER_SHAME_COLUMN, "other");
                                    newShame.saveInBackground();
                                }
                                break;
                        }
                        switch (shameFeel) {
                            case "barely noticed":
                                newShame.put(SHAME_FEEL_COLUMN, "barely noticed");
                                newShame.saveInBackground();
                                break;
                            case "angry":
                                newShame.put(SHAME_FEEL_COLUMN, "angry");
                                newShame.saveInBackground();
                                break;
                            case "annoyed":
                                newShame.put(SHAME_FEEL_COLUMN, "annoyed");
                                newShame.saveInBackground();
                                break;
                            case "uneasy":
                                newShame.put(SHAME_FEEL_COLUMN, "uneasy");
                                newShame.saveInBackground();
                                break;
                            case "scared":
                                newShame.put(SHAME_FEEL_COLUMN, "scared");
                                newShame.saveInBackground();
                                break;
                        }

                        switch (shameDoing) {
                            case "walking":
                                newShame.put(SHAME_DOING_COLUMN, "walking");
                                newShame.saveInBackground();
                                break;
                            case "jogging":
                                newShame.put(SHAME_DOING_COLUMN, "jogging");
                                newShame.saveInBackground();
                                break;
                            case "biking":
                                newShame.put(SHAME_DOING_COLUMN, "biking");
                                newShame.saveInBackground();
                                break;
                            case "waiting":
                                newShame.put(SHAME_DOING_COLUMN, "waiting");
                                newShame.saveInBackground();
                                break;
                            case "driving":
                                newShame.put(SHAME_DOING_COLUMN, "driving");
                                newShame.saveInBackground();
                                break;
                            case "other":
                                newShame.put(SHAME_DOING_COLUMN, "other");
                                newShame.saveInBackground();
                                break;
                        }

                        switch (group) {
                            case "woman":
                                newShame.put(GROUP_COLUMN, "woman");
                                newShame.saveInBackground();
                                break;
                            case "POC":
                                newShame.put(GROUP_COLUMN, "POC");
                                newShame.saveInBackground();
                                break;
                            case "LGBTQ":
                                newShame.put(GROUP_COLUMN, "LGBTQ");
                                newShame.saveInBackground();
                                break;
                            case "minor":
                                newShame.put(GROUP_COLUMN, "minor");
                                newShame.saveInBackground();
                                break;
                        }
                        return true;
                    }
                })
                .autoDismiss(false)
                .positiveText(R.string.submit)
                .negativeText(R.string.back)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.cancel();
                        Toast.makeText(context, "Shame successfully submitted!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        whenDialog(context, type, type_choice, feel, doing);
                        dialog.cancel();
                    }
                })
                .show();
    }

    private String getZipcode (Context context, double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        addresses = geocoder.getFromLocation(latitude, longitude, 1); // 1 represents max location result to returned, by documents it recommended 1 to 5
        String postalCode = addresses.get(0).getPostalCode();
        return postalCode;
    }
}
