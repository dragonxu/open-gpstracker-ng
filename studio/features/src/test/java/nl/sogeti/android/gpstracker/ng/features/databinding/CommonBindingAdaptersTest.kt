package nl.sogeti.android.gpstracker.ng.features.databinding

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatSpinner
import android.webkit.WebView
import android.widget.ImageView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CommonBindingAdaptersTest {

    lateinit var sut: CommonBindingAdapters

    @Before
    fun setUp() {
        sut = CommonBindingAdapters()
    }

    @Test
    fun setBitmap() {
        // Arrange
        val bitmap = mock(Bitmap::class.java)
        val view = mock(ImageView::class.java)
        // Act
        sut.setBitmap(view, bitmap)
        //
        verify(view).setImageBitmap(bitmap)
    }

    @Test
    fun setSrcCompat() {
        // Arrange
        val view = mock(ImageView::class.java)
        // Act
        sut.setImageSource(view, 5)
        // Assert
        verify(view).setImageResource(5)
    }

    @Test
    fun setUrl() {
        // Arrange
        val webView = mock(WebView::class.java)
        val url = "someurl"
        // Act
        sut.setUrl(webView, url)
        // Assert
        verify(webView).loadUrl(url)
    }
}
