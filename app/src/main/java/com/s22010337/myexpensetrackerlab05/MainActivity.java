package com.s22010337.myexpensetrackerlab05;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Declare variables for database helper and UI elements
    DatabaseHelper myDb;
    EditText editDate, editCategory, editAmount, editWeekNumber, editIdupdatenDelete;
    Button btnAddData, btnViewData, btnUpdateData, btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the database helper
        myDb = new DatabaseHelper(this);

        // Initialize UI elements by finding them using their IDs
        editDate = findViewById(R.id.editDateText);
        editCategory = findViewById(R.id.editCategoryText);
        editAmount = findViewById(R.id.editAmountText);
        editWeekNumber = findViewById(R.id.editWeekNumberText);
        editIdupdatenDelete = findViewById(R.id.editIdupdatenDelete);
        btnAddData = findViewById(R.id.insertBtn);
        btnViewData = findViewById(R.id.viewBtn);
        btnUpdateData = findViewById(R.id.updateBtn);
        btnDeleteData = findViewById(R.id.deleteBtn);

        // Call methods to perform actions (add, view, update, delete)
        addData();
        viewAll();
        updateData();
        deleteData();
    }

    // Method to add data to the database
    public void addData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from EditText fields
                String date = editDate.getText().toString();
                String category = editCategory.getText().toString();
                String amountText = editAmount.getText().toString();
                String weekNumberText = editWeekNumber.getText().toString();

                // Check if any field is empty
                if (date.isEmpty() || category.isEmpty() || amountText.isEmpty() || weekNumberText.isEmpty()) {
                    // Show a toast message if any field is empty
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse amount and week number from text to numeric types
                    double amount = Double.parseDouble(amountText);
                    int weekNumber = Integer.parseInt(weekNumberText);

                    // Call the insertData method of DatabaseHelper to add data to the database
                    boolean isInserted = myDb.insertData(date, category, amount, weekNumber);
                    if (isInserted) {
                        // Show success message and clear EditText fields if data is inserted successfully
                        Toast.makeText(MainActivity.this, "Data inserted Successfully", Toast.LENGTH_LONG).show();

                        // Clear the EditText fields
                        editDate.setText("");
                        editCategory.setText("");
                        editAmount.setText("");
                        editWeekNumber.setText("");
                    } else {
                        // Show error message if data is not inserted correctly
                        Toast.makeText(MainActivity.this, "Data not inserted correctly", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    // Show error message if there's an invalid number format
                    Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to view all data from the database
    public void viewAll() {
        // Set a click listener for the "View" button
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get all data from the database using getAllData method of DatabaseHelper
                Cursor results = myDb.getAllData();
                // Check if the result set is empty
                if (results.getCount() == 0) {
                    // Show an error message if no data is available
                    showMessage("Error message: ", "No data available in the database");
                    return;
                }

                // Create a StringBuffer to store the data
                StringBuffer buffer = new StringBuffer();
                // Loop through the result set and append data to the buffer
                while(results.moveToNext()) {
                    buffer.append("\nID          : " + results.getString(0) + "\n");
                    buffer.append("Date        : " + results.getString(1) + "\n");
                    buffer.append("Category    : " + results.getString(2) + "\n");
                    buffer.append("Amount      : " + results.getString(3) + "\n");
                    buffer.append("Week Number : " + results.getString(4) + "\n");
                }

                // Show the dialog with the list of data retrieved from the database
                showMessage("List of data: ", buffer.toString());
            }
        });
    }

    // Method to show the message in an AlertDialog
    public void showMessage(String title, String message) {
        // Create an AlertDialog.Builder instance with the current activity context
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Set the dialog to be cancelable (i.e: can be dismissed by tapping outside)
        builder.setCancelable(true);
        // Set the title of the dialog
        builder.setTitle(title);
        // Set the message content of the dialog
        builder.setMessage(message);
        // Show the AlertDialog
        builder.show();
    }

    // Method to update data in the database
    public void updateData() {
        // Set a click listener for the "Update" button
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from EditText fields for amount and week number
                String amountText = editAmount.getText().toString();
                String weekNumberText = editWeekNumber.getText().toString();

                // Check if amount or week number fields are empty
                if (amountText.isEmpty() || weekNumberText.isEmpty()) {
                    // Show a toast message if any field is empty
                    Toast.makeText(MainActivity.this, "Please fill in all fields above", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse amount and week number from text to numeric types
                    double amount = Double.parseDouble(amountText);
                    int weekNumber = Integer.parseInt(weekNumberText);

                    // Call the updateData method of DatabaseHelper to update data in the database
                    boolean isUpdate = myDb.updateData(editIdupdatenDelete.getText().toString(), editDate.getText().toString(),
                            editCategory.getText().toString(), amount, weekNumber);

                    if (isUpdate) {
                        // Show success message and clear EditText fields if data is updated successfully
                        Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        editDate.setText("");
                        editCategory.setText("");
                        editAmount.setText("");
                        editWeekNumber.setText("");
                    } else {
                        // Show error message if data is not updated
                        Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    // Show error message if there's an invalid number format
                    Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to delete data from the database
    public void deleteData() {
        // Set a click listener for the "Delete" button
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the ID of the data to be deleted from the EditText field
                Integer deleteDataRows = myDb.deleteData(editIdupdatenDelete.getText().toString());
                if(deleteDataRows > 0) {
                    // Show success message and clear the EditText field if data is deleted
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    editIdupdatenDelete.setText("");
                } else {
                    // Show error message if data is not deleted
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}