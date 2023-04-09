package ru.cherepanov;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import java.nio.file.Path;

public class ImageProcessing {
    private boolean isFace;
    private String pathToFile;
    private String filename;

    public ImageProcessing(String pathToFile) {
        this.pathToFile = pathToFile;
        run();
    }

    public boolean isFace() {
        return isFace;
    }

    public String getFilename() {
        return filename;
    }

    public void run() {

        System.out.println("\nRunning DetectFaceDemo");

        // Create a face detector from the cascade file in the resources
        // directory.
        CascadeClassifier faceDetector = new CascadeClassifier("src\\test\\resources\\lbpcascade_frontalface.xml");
        Mat image = Imgcodecs.imread(pathToFile);

        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        if (faceDetections.toArray().length == 1) {
            isFace = true;
            // Save the visualized detection.
            filename = Path.of(pathToFile).getFileName().toString().split("\\.")[0] + pathToFile.hashCode() + ".png";
            System.out.println(String.format("Writing %s", filename));
            Imgcodecs.imwrite(filename, image);
        }
    }
}
