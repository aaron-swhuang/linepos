import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(img: ImageVector,
                 description: String? = null,
                 spacing: Int = 8,
                 textVisible: Boolean = true,
                 label: @Composable() (() -> Unit)? = null,
                 onClick: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    // Get width of the screen（dp）
    val screenWidth = configuration.screenWidthDp.dp

    // Set the button size to 20% of the width of screen size.
    //val buttonSize = screenWidth * 0.2f
    val iconSize = screenWidth * 0.15f
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(64.dp)
                //.size(buttonSize)
                .clip(CircleShape)
                // Set the background color of the button
                .background(Color.LightGray)
                .padding(spacing.dp)
        ) {
                Icon(
                imageVector = img,
                contentDescription = description,
                // Set the icon's color
                tint = Color.Black,
                modifier = Modifier
                    .size(iconSize)
            )
        }
        if (textVisible) {
            if (label != null) {
                label()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    ActionButton(Icons.Default.Home, label = { Text(text = "Home") })
}
