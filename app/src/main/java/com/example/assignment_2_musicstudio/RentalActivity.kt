import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String,
    val rating: Int,
    val attributes: List<String>,  // Create List of attributes
    val price: Int,
    val imageResId: Int
) : Parcelable
