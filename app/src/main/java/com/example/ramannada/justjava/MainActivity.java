package com.example.ramannada.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View v) {
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View v) {
        if (quantity <= 1) {
            Toast.makeText(this, "You cannot order less than 1", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity -= 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View v) {
        if (quantity <= 0) {
            Toast.makeText(this, "Sorry minimum order is 1", Toast.LENGTH_SHORT).show();
            return;
        }

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_topping_check_box);
        Boolean hasWhippedCream = whippedCreamCheckbox.isChecked();


        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_topping_check_box);
        Boolean hasChocolate = chocolateCheckbox.isChecked();
        String message = createOrderSummary(hasWhippedCream, hasChocolate);
//
//        displayMessage(message);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order by Ahmad dari JustJava");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void displayQuantity(int numberOfOrder) {
//
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
//
        quantityTextView.setText(String.valueOf(numberOfOrder));
    }

    private int calculatePrice(int numberOfOrder, int pricePerOne) {
        return numberOfOrder * pricePerOne;

    }

    private int calculatePrice(int numberOfOrder, Boolean whippedCream, Boolean chocolate) {
        int basePrice = 5;

        if (whippedCream) {
            basePrice += 1;
        }

        if (chocolate) {
            basePrice += 2;
        }

        return numberOfOrder * basePrice;

    }

    private void displayMessage(String message) {
        TextView orderSummaryView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryView.setText(message);
    }

    private String createOrderSummary(Boolean whippedCream, Boolean chocolate) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String nama = nameEditText.getText().toString();

        int price = calculatePrice(quantity, whippedCream, chocolate);
        String message = getString(R.string.name) +": " + nama + "\n" + getString(R.string.whipped_cream_summary) + whippedCream + "\n" + getString(R.string.chocolate_summary)
                + chocolate + "\n" + getString(R.string.quantity).toString() + quantity + "\n" + getString(R.string.total) + price + "\n" + getString(R.string.thanks);
        return message;
    }
}
