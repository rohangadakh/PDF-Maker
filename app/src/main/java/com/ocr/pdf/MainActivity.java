package com.ocr.pdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
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

    EditText name, file_name;
    FloatingActionButton add, open, pdf, setting;

    boolean aBoolean = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);


        add = findViewById(R.id.add);
        open = findViewById(R.id.open);
        pdf = findViewById(R.id.pdf);
        setting = findViewById(R.id.setting);

        name = (EditText) findViewById(R.id.name);
        file_name = (EditText) findViewById(R.id.filename);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(aBoolean){
                    open.show();
                    pdf.show();
                    setting.show();
                    aBoolean = false;
                }
                else
                {
                    open.hide();
                    pdf.hide();
                    setting.hide();
                    aBoolean = true;
                }
            }
        });

    pdf.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String savePDfname = file_name.getText().toString();

            if(savePDfname.equals(""))
            {
                Toast.makeText(MainActivity.this, "Enter filename", Toast.LENGTH_SHORT).show();
            }
            else
            {

                String username = name.getText().toString();

                if(username.equals(""))
                {
                    username = " ";
                }

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

                Font myfont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL);
                Paragraph paragraph = new Paragraph();
                paragraph.add(new Paragraph(username, myfont));

                try {
                    document.add(paragraph);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                document.close();
                Toast.makeText(MainActivity.this, "PDF Created", Toast.LENGTH_SHORT).show();
            }
        }
    });

    open.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            opn();
        }
    });

    setting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setting();
        }
    });

    }

    public void setting()
    {
        Intent intent = new Intent(MainActivity.this,secondAct.class);
        startActivity(intent);
    }

    public void opn() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            intent = new Intent(Intent.ACTION_VIEW, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
        }
        assert intent != null;
        intent.setType("*/*");
        this.startActivity(intent);
    }
}