package com.cgy.mycollections.functions.jetpack.camerax.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.jetpack.camerax.CameraXDemo;
import com.cgy.mycollections.functions.jetpack.camerax.utils.AutoFitPreviewBuilder;
import com.cgy.mycollections.utils.FileUtil;

import org.web3j.abi.datatypes.Int;

import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlinx.coroutines.Dispatchers;

import static android.provider.Telephony.Mms.Part.FILENAME;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.cgy.mycollections.functions.jetpack.camerax.CameraXDemo.KEY_EVENT_ACTION;
import static com.cgy.mycollections.functions.jetpack.camerax.CameraXDemo.KEY_EVENT_EXTRA;
import static com.cgy.mycollections.functions.jetpack.camerax.utils.ViewExtensionsKt.ANIMATION_FAST_MILLIS;
import static com.cgy.mycollections.functions.jetpack.camerax.utils.ViewExtensionsKt.ANIMATION_SLOW_MILLIS;

/**
 * Description :
 * Author :cgy
 * Date :2019/11/21
 */
public class CameraFragment extends Fragment {

    /**
     * Helper type alias used for analysis use case callbacks
     */
//    typealias LumaListener = (luma: Double) -> Unit

    private ConstraintLayout container;
    private TextureView viewFinder;
    private File outputDirectory;
    private LocalBroadcastManager broadcastManager;

    private int displayId = -1;
    private CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;
    private Preview preview = null;
    private ImageCapture imageCapture = null;
    private ImageAnalysis imageAnalyzer = null;

    /**
     * Volume down button receiver used to trigger shutter
     */
    private BroadcastReceiver volumeDownReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN) == KeyEvent.KEYCODE_VOLUME_DOWN) {
                // When the volume down button is pressed, simulate a shutter button click
//                    val shutter = container
//                            .findViewById<ImageButton>(R.id.camera_capture_button)
//                            shutter.simulateClick()
            }
        }
    };

    /**
     * Internal reference of the [DisplayManager]
     */
    private DisplayManager displayManager;

    /**
     * We need a display listener for orientation changes that do not trigger a configuration
     * change, for example if we choose to override config change in manifest or for 180-degree
     * orientation changes.
     */
    private DisplayListener displayListener = new DisplayListener() {
        @Override
        public void onDisplayAdded(int displayId) {

        }

        @Override
        public void onDisplayRemoved(int displayId) {

        }

        @Override
        public void onDisplayChanged(int displayId) {
            if (displayId == CameraFragment.this.displayId) {
                Log.d(TAG, "Rotation changed: ${view.display.rotation}");
                if (getView() != null) {
                    int rotation = getView().getDisplay().getRotation();
                    preview.setTargetRotation(rotation);
                    imageCapture.setTargetRotation(rotation);
                    imageAnalyzer.setTargetRotation(rotation);
                }
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mark this as a retain fragment, so the lifecycle does not get restarted on config change
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Make sure that all permissions are still present, since user could have removed them
        //  while the app was on paused state
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                    CameraFragmentDirections.actionCameraToPermissions());

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the broadcast receivers and listeners
        broadcastManager.unregisterReceiver(volumeDownReceiver);
        displayManager.unregisterDisplayListener(displayListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        return view;
    }

    private void setGalleryThumbnail(File file) {
        // Reference of the view that holds the gallery thumbnail
        ImageButton thumbnail = (ImageButton) container.findViewById(R.id.photo_view_button);

        // Run the operations in the view's thread
        thumbnail.post(new Runnable() {
            @Override
            public void run() {

                // Remove thumbnail padding
                int padding = (int) getResources().getDimension(R.dimen.stroke_small);
                thumbnail.setPadding(padding, padding, padding, padding);

                // Load thumbnail into circular button using Glide
                Glide.with(thumbnail)
                        .load(file)
                        .apply(RequestOptions.circleCropTransform())
                        .into(thumbnail);
            }
        });
    }


    /**
     * Define callback that will be triggered after a photo has been taken and saved to disk
     */
    private ImageCapture.OnImageSavedListener imageSavedListener = new ImageCapture.OnImageSavedListener() {
        @Override
        public void onImageSaved(@NonNull File photoFile) {
            Log.d(TAG, "Photo capture succeeded: ${photoFile.absolutePath}");

            // We can only change the foreground Drawable using API level 23+ API
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Update the gallery thumbnail with latest picture taken
                setGalleryThumbnail(photoFile);
            }

            // Implicit broadcasts will be ignored for devices running API
            // level >= 24, so if you only target 24+ you can remove this statement
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                requireActivity().sendBroadcast(new
                        Intent(Camera.ACTION_NEW_PICTURE, Uri.fromFile(photoFile)));
            }

            // If the folder selected is an external media directory, this is unnecessary
            // but otherwise other apps will not be able to access our images unless we
            // scan them using [MediaScannerConnection]
            String mimeType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(FileUtil.getFileExtensionName(photoFile.getName()));
            MediaScannerConnection.scanFile(
                    getContext(), new String[]{photoFile.getAbsolutePath()}, new String[]{mimeType}, null);
        }

        @Override
        public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
            Log.e(TAG, "Photo capture failed: $message");
            if (cause != null)
                cause.printStackTrace();
        }
    };


    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        container = (ConstraintLayout) view;
        viewFinder = container.findViewById(R.id.view_finder);
        broadcastManager = LocalBroadcastManager.getInstance(view.getContext());

        // Set up the intent filter that will receive events from our main activity
        IntentFilter filter = new IntentFilter(KEY_EVENT_ACTION);
        broadcastManager.registerReceiver(volumeDownReceiver, filter);

        // Every time the orientation of device changes, recompute layout
        displayManager = (DisplayManager) viewFinder.getContext()
                .getSystemService(Context.DISPLAY_SERVICE);
        displayManager.registerDisplayListener(displayListener, null);

        // Determine the output directory
        outputDirectory = CameraXDemo.getOutputDirectory(requireContext());

        // Wait for the views to be properly laid out
        viewFinder.post(new Runnable() {
            @Override
            public void run() {
                // Keep track of the display in which this view is attached
                displayId = viewFinder.getDisplay().getDisplayId();

                // Build UI controls and bind all camera use cases
                updateCameraUi();
                bindCameraUseCases();
//
//                // In the background, load latest photo taken (if any) for gallery thumbnail
//                lifecycleScope.launch(Dispatchers.IO) {
//                    outputDirectory.listFiles { file ->
//                            EXTENSION_WHITELIST.contains(file.extension.toUpperCase())
//                    }.sorted().reversed().firstOrNull()?.let { setGalleryThumbnail(it) }
//                };
            }
        });
    }


    /**
     * Declare and bind preview, capture and analysis use cases
     */
    private void bindCameraUseCases() {

        // Get screen metrics used to setup camera for full screen resolution
        DisplayMetrics metrics = new DisplayMetrics();
        viewFinder.getDisplay().getRealMetrics(metrics);
        Rational screenAspectRatio = new Rational(metrics.widthPixels, metrics.heightPixels);
        Log.d(TAG, "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}");

        // Set up the view finder use case to display camera preview
        PreviewConfig viewFinderConfig = new PreviewConfig.Builder().
                setLensFacing(lensFacing)
                // We request aspect ratio but no resolution to let CameraX optimize our use cases
//            .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(viewFinder.getDisplay().getRotation())
                .build();

        // Use the auto-fit preview builder to automatically handle size and orientation changes
        preview = AutoFitPreviewBuilder.Companion.build(viewFinderConfig, viewFinder);

        // Set up the capture use case to allow users to take photos
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().
                setLensFacing(lensFacing)
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                // We request aspect ratio but no resolution to match preview config but letting
                // CameraX optimize for whatever specific resolution best fits requested capture mode
//            .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(viewFinder.getDisplay().getRotation())
                .build();

        imageCapture = new ImageCapture(imageCaptureConfig);

        // Setup image analysis pipeline that computes average pixel luminance in real time
        ImageAnalysisConfig analyzerConfig = new ImageAnalysisConfig.Builder()
                .setLensFacing(lensFacing)
                // In our analysis, we care more about the latest image than analyzing *every* image
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(viewFinder.getDisplay().getRotation())
                .build();

        imageAnalyzer = new ImageAnalysis(analyzerConfig);

        final LuminosityAnalyzer luminosityAnalyzer = new LuminosityAnalyzer(null);
        luminosityAnalyzer.onFrameAnalyzed(new LumaListener() {

            @Override
            public void doSomething(double d) {
                // Values returned from our analyzer are passed to the attached listener
                // We log image analysis results here -- you should do something useful instead!
                Double fps = luminosityAnalyzer.framesPerSecond;
                Log.d(TAG, "Average luminosity: $luma. " +
                        "Frames per second:  " + String.format("%.01f", fps));
            }

        });
        imageAnalyzer.setAnalyzer(Executors.newFixedThreadPool(5), luminosityAnalyzer);

        // Apply declared configs to CameraX using the same lifecycle owner
        CameraX.bindToLifecycle(getViewLifecycleOwner(), preview, imageCapture, imageAnalyzer);
    }

    //    /** Method used to re-draw the camera UI controls, called every time configuration changes */
//    @SuppressLint("RestrictedApi")
    private void updateCameraUi() {

        // Remove previous UI if any
        View cameraUi = container.findViewById(R.id.camera_ui_container);
        if (cameraUi != null) {
            container.removeView(cameraUi);
        }

        // Inflate a new view containing all UI for controlling the camera
        View controls = View.inflate(requireContext(), R.layout.camera_ui_container, container);

        // Listener for button used to capture photo
        controls.findViewById(R.id.camera_capture_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a stable reference of the modifiable image capture use case
                if (imageCapture != null) {

                    // Create output file to hold the image
                    File photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION);

                    // Setup image capture metadata
                    ImageCapture.Metadata metadata = new ImageCapture.Metadata();
                    // Mirror image when using the front camera
                    metadata.isReversedHorizontal = lensFacing == CameraX.LensFacing.FRONT;

                    // Setup image capture listener which is triggered after photo has been taken
                    imageCapture.takePicture(photoFile, metadata, Executors.newFixedThreadPool(5), imageSavedListener);

                    // We can only change the foreground Drawable using API level 23+ API
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        // Display flash animation to indicate that photo was captured
                        container.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                container.setForeground(new ColorDrawable(Color.WHITE));
                                container.postDelayed(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              container.setForeground(null);
                                                          }
                                                      }
                                        , ANIMATION_FAST_MILLIS);
                            }
                        }, ANIMATION_SLOW_MILLIS);
                    }
                }
            }
        });
        // Listener for button used to switch cameras
        controls.findViewById(R.id.camera_switch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraX.LensFacing.FRONT == lensFacing) {
                    lensFacing = CameraX.LensFacing.BACK;
                } else {
                    lensFacing = CameraX.LensFacing.FRONT;
                }
                try {
                    // Only bind use cases if we can query a camera with this orientation
//                CameraX.getCameraWithLensFacing(lensFacing);

                    // Unbind all use cases and bind them again with the new lens facing configuration
                    CameraX.unbindAll();
                    bindCameraUseCases();
                } catch (Exception exc) {
                    // Do nothing
                }
            }
        });

        // Listener for button used to view last photo
        controls.findViewById(R.id.photo_view_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_container);
                //NavController跳转之前 currentDestination是当前fragment即CameraFragment
                //跳转之后  currentDestination 就变成了 galleryFragment
                //此时按返回键回到 cameraFragment 此时NavController，currentDestination依然是galleryFragment
                //再点击跳转就会报错 action is unknown to this NavController。
                //因为当前destination就是目标fragment 所以需要popBackStack 然后再跳
                //考虑popBack操作是不是可以放在CameraXDemo 的onBackPressed中
                if (controller.getCurrentDestination() != null) {
                    switch (controller.getCurrentDestination().getId()) {
                        case R.id.gallery_fragment:
                            Log.d(TAG, " current destination is gallery_fragment id : " +
                                    R.id.gallery_fragment);
                            controller.popBackStack();
                            Log.d(TAG, " getCurrentDestination again: " +
                                    controller.getCurrentDestination().getId());
                            break;
                        case R.id.camera_fragment:
                            Log.d(TAG, " current destination is camera_fragment id : " +
                                    R.id.camera_fragment);
                            break;
                        default:
                            break;
                    }
                }
                //TODO:第二次调用的时候会报找不到action的错 ，待研究
                controller.navigate(
                        CameraFragmentDirections.actionCameraToGallery(outputDirectory.getAbsolutePath()));
            }
        });


    }


    interface LumaListener {
        void doSomething(double d);
    }

    /**
     * Our custom image analysis class.
     *
     * <p>All we need to do is override the function `analyze` with our desired operations. Here,
     * we compute the average luminosity of the image by looking at the Y plane of the YUV frame.
     */
    private class LuminosityAnalyzer implements ImageAnalysis.Analyzer {

        public LuminosityAnalyzer(LumaListener listener) {
            if (listener != null)
                listeners.add(listener);
        }

        private int frameRateWindow = 8;
        private ArrayDeque<Long> frameTimestamps = new ArrayDeque<Long>(5);
        private List<LumaListener> listeners = new ArrayList<LumaListener>();
        private long lastAnalyzedTimestamp = 0L;
        Double framesPerSecond = -1.0;
//        private set

        /**
         * Used to add listeners that will be called with each luma computed
         */
        void onFrameAnalyzed(LumaListener listener) {
            listeners.add(listener);
        }

        /**
         * Helper extension function used to extract a byte array from an image plane buffer
         */
        private byte[] toByteArray(ByteBuffer buffer) {
            buffer.rewind();    // Rewind the buffer to zero
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);  // Copy the buffer into a byte array
            return data;// Return the byte array
        }

        /**
         * 获取平均值
         *
         * @param pixels
         * @return
         */
        private double getAverage(List<Integer> pixels) {
            double sum = 0.0;
            int count = 0;
            for (Integer i : pixels) {
                sum += i;
                ++count;
            }
            if (count == 0)
                return Double.NaN;

            return sum / count;
        }

        /**
         * Analyzes an image to produce a result.
         *
         * <p>The caller is responsible for ensuring this analysis method can be executed quickly
         * enough to prevent stalls in the image acquisition pipeline. Otherwise, newly available
         * images will not be acquired and analyzed.
         *
         * <p>The image passed to this method becomes invalid after this method returns. The caller
         * should not store external references to this image, as these references will become
         * invalid.
         *
         * @param image image being analyzed VERY IMPORTANT: do not close the image, it will be
         *              automatically closed after this method returns
         * @return the image analysis result
         */
        @Override
        public void analyze(ImageProxy image, int rotationDegrees) {
            // If there are no listeners attached, we don't need to perform analysis
            if (listeners.isEmpty()) return;

            // Keep track of frames analyzed
            frameTimestamps.push(System.currentTimeMillis());

            // Compute the FPS using a moving average
            while (frameTimestamps.size() >= frameRateWindow) frameTimestamps.removeLast();
            framesPerSecond = 1.0 / ((frameTimestamps.peekFirst() - frameTimestamps.peekLast())
                    / (double) frameTimestamps.size()) * 1000.0;

            // Calculate the average luma no more often than every second
            if (frameTimestamps.getFirst() - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {
                lastAnalyzedTimestamp = frameTimestamps.getFirst();

                // Since format in ImageAnalysis is YUV, image.planes[0] contains the luminance
                //  plane
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();

                // Extract image data from callback object
                byte[] data = toByteArray(buffer);

                // Convert the data into an array of pixel values ranging 0-255
                List<Integer> pixels = new ArrayList<>();
                for (byte b : data) {
                    pixels.add(b & 0xFF);
                }


                // Compute average luminance for the image
                double luma = getAverage(pixels);

                // Call all listeners with new value
                for (LumaListener listener : listeners) {
                    listener.doSomething(luma);
                }
            }
        }

    }


    //---------------------------------------companion------------------------------------------
//    companion object {
    final String TAG = "CameraXBasic";
    final String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";
    final String PHOTO_EXTENSION = ".jpg";
//

    /**
     * Helper function used to create a timestamped file
     */
    private File createFile(File baseFolder, String format, String extension) {
        return new File(baseFolder, new SimpleDateFormat(format, Locale.US)
                .format(System.currentTimeMillis()) + extension);
    }
//    }
}
