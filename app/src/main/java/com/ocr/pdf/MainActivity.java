package com.ocr.pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static String path;
    EditText name, email, searchname;
    Button btnpdf, btnview;
    FloatingActionButton add, about, pdf;
    boolean aBoolean = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        about = findViewById(R.id.about);
        pdf = findViewById(R.id.pdf);


        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        btnpdf = (Button) findViewById(R.id.btnpdf);
        btnview = (Button) findViewById(R.id.btnview);
        searchname = (EditText) findViewById(R.id.searchname);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(aBoolean){
                    about.show();
                    pdf.show();
                    aBoolean = false;
                }
                else
                {
                    about.hide();
                    pdf.hide();
                    aBoolean = true;
                }
            }
        });

    pdf.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = name.getText().toString();
            String useremail = email.getText().toString();

            String savePDfname = searchname.getText().toString();
            String path = getExternalFilesDir(null).toString()+savePDfname+".pdf";
            File file = new File(path);

            if(!file.exists())
            {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Document document = new Document(PageSize.A4);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }
            document.open();

            Font myfont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Paragraph("User Name : "+username, myfont));
            paragraph.add(new Paragraph("\n"));
            paragraph.add(new Paragraph("User email : "+useremail, myfont));

            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            document.close();
            Toast.makeText(MainActivity.this, "PDF Created", Toast.LENGTH_SHORT).show();
        }
    });

    }

    public void snd(View view) {
        Intent i = new Intent(MainActivity.this,secondAct.class);
        startActivity(i);
    }
}