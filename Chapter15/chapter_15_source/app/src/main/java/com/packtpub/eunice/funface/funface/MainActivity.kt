package com.packtpub.eunice.funface.funface

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.FaceDetector
import com.google.android.gms.vision.face.Landmark.NOSE_BASE
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            detectFace()
        }
    }

    private fun detectFace() {
        // Load the image
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inMutable = true
        val myBitmap = BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.children_2_1,
                bitmapOptions)

        // Get a Paint instance
        val myRectPaint = Paint()
        myRectPaint.strokeWidth = 5F
        myRectPaint.color = Color.RED
        myRectPaint.style = Paint.Style.STROKE

        // Create a canvas using the dimensions from the image's bitmap
        val tempBitmap = Bitmap.createBitmap(myBitmap.width, myBitmap.height, Bitmap.Config.RGB_565)
        val eyePatchBitmap = BitmapFactory.decodeResource(resources, R.drawable.beard_2,
                bitmapOptions)
        val tempCanvas = Canvas(tempBitmap)
        tempCanvas.drawBitmap(myBitmap, 0F, 0F, null)

        // Create a FaceDetector
        val faceDetector = FaceDetector.Builder(applicationContext)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build()
        if (!faceDetector.isOperational) {
            AlertDialog.Builder(this)
                    .setMessage("Could not set up the face detector!")
                    .show()
            return
        }

        // Detect the faces
        val frame = Frame.Builder().setBitmap(myBitmap).build()
        val faces = faceDetector.detect(frame)

        // Mark out the identified face
        for (i in 0 until faces.size()) {
            val thisFace = faces.valueAt(i)
            val left = thisFace.position.x
            val top = thisFace.position.y
            val right = left + thisFace.width
            val bottom = top + thisFace.height
            tempCanvas.drawRoundRect(RectF(left, top, right, bottom), 2F, 2F, myRectPaint)

            for (landmark in thisFace.landmarks) {
                val x = landmark.position.x
                val y = landmark.position.y

                when (landmark.type) {
                    NOSE_BASE -> {
                        val scaledWidth = eyePatchBitmap.getScaledWidth(tempCanvas)
                        val scaledHeight = eyePatchBitmap.getScaledHeight(tempCanvas)
                        tempCanvas.drawBitmap(eyePatchBitmap,
                                x - scaledWidth / 2,
                                y - scaledHeight / 2,
                                null)
                    }
                }
            }
        }
        imageView.setImageDrawable(BitmapDrawable(resources, tempBitmap))

        // Release the FaceDetector
        faceDetector.release()
    }
}
