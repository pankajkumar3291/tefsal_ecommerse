package com.tefsalkw.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.cameraview.CameraView;
import com.tefsalkw.R;
import com.tefsalkw.activity.EditProfileActivity;
import com.tefsalkw.app.TefalApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomCameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener{


    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    private ImageView captureImage;
    private int cameraId;
    boolean previewing = false;
    private boolean flashmode = false;
    private int rotation;
    float mDist = 0;
    private RelativeLayout relativeLayout;
    private FrameLayout fragmentContainer;
    Bitmap bmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

        captureImage = (ImageView) findViewById(R.id.captureImage);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        captureImage.setOnClickListener(this);
        relativeLayout=(RelativeLayout)findViewById(R.id.rel);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Camera.getNumberOfCameras() > 1) {
            //        flipCamera.setVisibility(View.VISIBLE);
        }
        if (!getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            //  flashCameraButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!openCamera(Camera.CameraInfo.CAMERA_FACING_BACK)) {
            alertCameraDialog();
        }
    }
    private boolean openCamera(int id) {
        boolean result = false;
        cameraId = id;
        releaseCamera();
        try {
            camera = Camera.open(cameraId);
            flashOnButton();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (camera != null) {
            try {
                setUpCamera(camera);
                camera.setErrorCallback(new Camera.ErrorCallback() {

                    @Override
                    public void onError(int error, Camera camera) {

                    }
                });
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
                releaseCamera();
            }
        }
        return result;
    }

    private void setUpCamera(Camera c) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 270;
                break;

            default:
                break;
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // frontFacing
            rotation = (info.orientation + degree) % 330;
            rotation = (360 - rotation) % 360;
        } else {
            // Backfacing
            rotation = (info.orientation - degree + 360) % 360;
        }
        c.setDisplayOrientation(rotation);
        Camera.Parameters params = c.getParameters();

        showFlashButton(params);

        List<String> focusModes = params.getSupportedFlashModes();
       /* if (focusModes != null) {
            if (focusModes
                    .contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                params.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }*/
        if(focusModes.contains( Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        }
        params.setRotation(rotation);

    }
    private void showFlashButton(Camera.Parameters params) {
        boolean showFlash = (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH) && params.getFlashMode() != null)
                && params.getSupportedFlashModes() != null
                && params.getSupportedFocusModes().size() > 1;



    }


    private void releaseCamera() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.setErrorCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
                previewing = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
           // System.out.println("SARFACE CHANGED");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //     case R.id.flash:
            //    flashOnButton();
            //        break;
            //  case R.id.flipCamera:
            //       flipCamera();
            //    break;
            case R.id.captureImage:
                captureImage.setEnabled(true);
                captureImage.setVisibility(View.GONE);
                camera.autoFocus(myAutoFocusCallback);

                takeImage();

                break;
            default:
                break;
        }
    }

    Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback()
    {

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            captureImage.setEnabled(true);
        }
    };



    private class CaptureImage extends AsyncTask<byte[], String, String> {
        File imageFile;
        byte[] data;
        Bitmap loadedImage = null;
        Bitmap rotatedBitmap = null;
        ProgressDialog dailog = new ProgressDialog(CustomCameraActivity.this);

        public CaptureImage(byte[] data) {
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            dailog.setMessage("Loading...");
            dailog.show();
        }

        @Override
        protected String doInBackground(byte[]... bytes) {

            try {

                //This block of code to hold the click image in surface

                /*// convert byte array into bitmap
                loadedImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                // rotate Image
                Matrix rotateMatrix = new Matrix();
                rotateMatrix.postRotate(rotation);
                rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
                        loadedImage.getWidth(), loadedImage.getHeight(), rotateMatrix, false);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                rotatedBitmap.recycle();*/
                //   rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 50/50 , stream);

                try {



                   saveImage(data);

                    System.out.println("PICTUTRE CAPYURE");



                } catch (Exception e) {
                    System.out.println("ERROR=="+e.toString());
                }

           } catch (Exception e) {
                System.out.println("ERROR=="+e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dailog.dismiss();
           // System.out.println("======" + loadedImage.toString());
        }
    }

    private void saveImage( byte[] data)
    {
        File imageFile = null;
        try {
                String state = Environment.getExternalStorageState();
                File folder = null;
                if (state.contains(Environment.MEDIA_MOUNTED))
                {
                        folder = new File(Environment.getExternalStorageDirectory() + "/tefsal");
                }
                else
                {
                        folder = new File(Environment.getExternalStorageDirectory() + "/tefsal");
                }

                        boolean success = true;
                        if (!folder.exists()) {
                        success = folder.mkdirs();
                        }
                    if (success)
                    {

                    java.util.Date date = new java.util.Date();
                    imageFile = new File(folder.getAbsolutePath() + File.separator
                            + new Timestamp(date.getTime()).toString() + "Image.jpg");
                    imageFile.createNewFile();

                    FileOutputStream fos = new FileOutputStream(imageFile);
                    fos.write(data);
                    fos.flush();
                    fos.close();







                    Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    //setOrientation(myBitmap,imageFile.getPath());

                //=====================================================================================


                //========================================================================

                    System.out.println("Image File Name======" + myBitmap.toString());
                    System.out.println("Image File Name======" + imageFile.getPath());
                    System.out.println("Image File Name======" + imageFile.getAbsolutePath());

                    //Write your code here===================

                    Intent intent = new Intent();
                    intent.putExtra("image_path",imageFile.getPath());
                    setResult(Activity.RESULT_OK,intent);
                    finish();


            } else {
                //Toast.makeText(getBaseContext(), "Image Not saved", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch(Exception ex)
        {
            System.out.println("ERROR=="+ex);
        }

    }

    private void setOrientation(Bitmap myBitmap, String patn) {
        try {
                ExifInterface exif = new ExifInterface(patn);
                int exifOrientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

                    int rotate = 0;

                    switch (exifOrientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                    break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
            }

            if (rotate != 0) {
                int w = myBitmap.getWidth();
                int h = myBitmap.getHeight();

// Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, w, h, mtx, false);
                System.out.println("BITMAP==="+myBitmap);
                //myBitmap = myBitmap.copy(Bitmap.Config.ARGB_8888, true);

                TefalApp.getInstance().setBitmap(myBitmap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setResult(1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the pointer ID
        Camera.Parameters params = camera.getParameters();
        int action = event.getAction();

        if (event.getPointerCount() > 1) {
            // handle multi-touch events
            if (action == MotionEvent.ACTION_POINTER_DOWN) {
                mDist = getFingerSpacing(event);
            } else if (action == MotionEvent.ACTION_MOVE
                    && params.isZoomSupported()) {
                camera.cancelAutoFocus();
                handleZoom(event, params);
            }
        } else {
            // handle single touch events
            if (action == MotionEvent.ACTION_UP) {
                handleFocus(event, params);
            }
        }
        return true;
    }

    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            // zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            // zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;
        params.setZoom(zoom);
        camera.setParameters(params);
    }


    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null
                && supportedFocusModes
                .contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }


    /** Determine the space between the first two fingers */
    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }


    private void takeImage() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            File imageFile;
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                System.out.println("IM HERE");
                new CaptureImage(data).execute();

            }
        });
    }
    private void alertCameraDialog() {
        AlertDialog.Builder dialog = createAlert(CustomCameraActivity.this,
                "Camera info", "error to open camera");
        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        dialog.show();
    }

    private AlertDialog.Builder createAlert(Context context, String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(
                new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog));
        //    dialog.setIcon(R.drawable.ic_launcher);
        if (title != null)
            dialog.setTitle(title);
        else
            dialog.setTitle("Information");
            dialog.setMessage(message);
            dialog.setCancelable(false);
            return dialog;

    }

    private void flashOnButton() {
        if (camera != null) {
            try {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode ? Camera.Parameters.FLASH_MODE_ON : Camera.Parameters.FLASH_MODE_OFF);
                param.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                flashmode = !flashmode;

               /* List<Camera.Size> sizes = param.getSupportedPictureSizes();
                Camera.Size size = sizes.get(0);
                for(int i=0;i<sizes.size();i++)
                {
                    if(sizes.get(i).width > size.width)
                        size = sizes.get(i);
                }
                param.setPictureSize(size.width, size.height);*/
                camera.setParameters(param);
            } catch (Exception e) {
            }

        }
    }


}
