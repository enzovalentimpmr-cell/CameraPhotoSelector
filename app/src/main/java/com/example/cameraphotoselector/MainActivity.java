package com.example.cameraphotoselector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final int REQUEST_GALLERY = 1001;
    private static final int REQUEST_CAMERA = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 60, 40, 40);

        TextView title = new TextView(this);
        title.setText("Filtro Facial");
        title.setTextSize(28);
        title.setPadding(0, 0, 0, 40);

        TextView description = new TextView(this);
        description.setText(
                "Escolha uma foto para usar como referência " +
                "ou abra a câmera frontal."
        );
        description.setTextSize(18);
        description.setPadding(0, 0, 0, 30);

        Button galleryButton = new Button(this);
        galleryButton.setText("🖼️ Escolher foto de referência");

        Button cameraButton = new Button(this);
        cameraButton.setText("📷 Abrir câmera frontal");

        layout.addView(title);
        layout.addView(description);
        layout.addView(galleryButton);
        layout.addView(cameraButton);

        setContentView(layout);

        galleryButton.setOnClickListener(v -> openGallery());

        cameraButton.setOnClickListener(v -> openCamera());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        if (requestCode == REQUEST_GALLERY) {

            Uri selectedImage = data.getData();

            if (selectedImage != null) {
                Intent result = new Intent();
                result.setData(selectedImage);
                result.addFlags(
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );

                setResult(Activity.RESULT_OK, result);
                finish();
            }
        }
    }
}