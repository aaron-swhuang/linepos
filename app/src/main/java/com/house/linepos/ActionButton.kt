import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(img: ImageVector, description: String? = null, onClick: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    // Get width of the screen（dp）
    val screenWidth = configuration.screenWidthDp.dp

    // Set the button size to 10% of the width of screen size.
    val buttonSize = screenWidth * 0.10f
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize)
            .clip(CircleShape)
            // Set the background color of the button
            .background(Color.LightGray)
    ) {
        Icon(
            imageVector = img,
            contentDescription = description,
            // Set the icon's color
            tint = Color.Black
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ActionButtonPreview() {
    ActionButton(Icons.Default.Home)
}
