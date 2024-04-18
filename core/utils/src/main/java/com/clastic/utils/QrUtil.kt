package com.clastic.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

object QrUtil {
    private const val QR_BITMAP_SIZE = 600
    fun createQrBitmap(qrText: String): ImageBitmap? {
        try {
            val writer = QRCodeWriter()
            val bitMatrix: BitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, QR_BITMAP_SIZE, QR_BITMAP_SIZE)
            val pixels = IntArray(QR_BITMAP_SIZE * QR_BITMAP_SIZE)
            for (y in 0 until QR_BITMAP_SIZE) {
                for (x in 0 until QR_BITMAP_SIZE) {
                    pixels[y * QR_BITMAP_SIZE + x] = if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                }
            }
            val imageBitmap = Bitmap.createBitmap(pixels, QR_BITMAP_SIZE, QR_BITMAP_SIZE, Bitmap.Config.RGB_565)
            return imageBitmap.asImageBitmap()
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}