package asmirza.uniherts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

import asmirza.uniherts.lib.BarCodeEncoder;


public class CardView extends Activity {


    public final static String FULL_BARCODE_BITMAP = "asmirza.uniherts.FULL_BARCODE_BITMAP";


    String student_id;
    String firstName;
    String lastName;

    String cardBarcode;

    Bitmap barcode_bitmap;
    Bitmap full_barcode_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        prepareCard();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    }


    public void prepareCard() {

        // barcode data
        student_id = ParseUser.getCurrentUser().get("studentId").toString();
        firstName = ParseUser.getCurrentUser().get("firstName").toString();
        lastName = ParseUser.getCurrentUser().get("lastName").toString();
        cardBarcode = ParseUser.getCurrentUser().get("cardBarcode").toString();

        String name_on_card = capitalize(firstName.substring(0, 1)) + "." + capitalize(lastName);

        TextView name_view = (TextView) findViewById(R.id.name);
        name_view.setText(name_on_card);

        TextView student_num_view = (TextView) findViewById(R.id.student_number);
        student_num_view.setText(student_id);
        barcode_bitmap = null;
        ImageView barcode_image_view = (ImageView) findViewById(R.id.bardcode);

        TextView cardBarcode_code_view = (TextView) findViewById(R.id.card_barcode);
        cardBarcode_code_view.setText("*" + cardBarcode + "*");

        try {

            barcode_bitmap = BarCodeEncoder.encodeAsBitmap(cardBarcode, BarcodeFormat.CODE_39, 950, 80);
            barcode_image_view.setImageBitmap(barcode_bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }


    public void openFullBarcode(View view) {
        Intent intent = new Intent(this, Barcode.class);

        try {

            full_barcode_bitmap = BarCodeEncoder.encodeAsBitmap(cardBarcode, BarcodeFormat.CODE_39, 1000, 650);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        full_barcode_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        intent.putExtra(FULL_BARCODE_BITMAP, bytes);

        startActivity(intent);
    }

    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}